package com.palprotech.ensyfi.activity.loginmodule;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.AppValidator;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.FirstTimePreference;
import com.palprotech.ensyfi.utils.PermissionUtil;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 22-03-2017.
 */

public class SchoolIdLoginActivity extends AppCompatActivity implements View.OnClickListener, IServiceListener, DialogClickListener {

    private static final String TAG = SchoolIdLoginActivity.class.getName();

    private ServiceHelper serviceHelper;
    private ProgressDialogHelper progressDialogHelper;

    private EditText inputInstituteId;
    private ImageView btnSubmit;

    private static final int REQUEST_PERMISSION_All = 111;
    private static String[] PERMISSIONS_ALL = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    //Creating a broadcast receiver for gcm registration
//    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_id_login);

        FirstTimePreference prefFirstTime = new FirstTimePreference(getApplicationContext());

        if (prefFirstTime.runTheFirstTime("FirstTimePermit")) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                requestAllPermissions();
            }
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        inputInstituteId = (EditText) findViewById(R.id.inputInsId);
        btnSubmit = (ImageView) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);

    }

    private void requestAllPermissions() {

        boolean requestPermission = PermissionUtil.requestAllPermissions(this);

        if (requestPermission == true) {

            Log.i(TAG,
                    "Displaying contacts permission rationale to provide additional context.");

            // Display a SnackBar with an explanation and a button to trigger the request.

            ActivityCompat
                    .requestPermissions(SchoolIdLoginActivity.this, PERMISSIONS_ALL,
                            REQUEST_PERMISSION_All);
        } else {

            ActivityCompat.requestPermissions(this, PERMISSIONS_ALL, REQUEST_PERMISSION_All);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
        return true;
    }


    @Override
    public void onClick(View v) {
        if (CommonUtils.isNetworkAvailable(this)) {
            if (v == btnSubmit) {
                if (validateFields()) {
                    JSONObject jsonObject = new JSONObject();
                    try {
//                        jsonObject.put(EnsyfiConstants.PARAMS_FUNC_NAME, EnsyfiConstants.SIGN_IN_PARAMS_FUNC_NAME);
//                        jsonObject.put(EnsyfiConstants.PARAMS_INSTITUTE_ID, inputInstituteId.getText().toString());
                        jsonObject.put(EnsyfiConstants.KEY_INSTITUTE_CODE, inputInstituteId.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
                    String url = EnsyfiConstants.INSTITUTE_LOGIN_API;
                    serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
                }
            }
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private boolean validateFields() {
        if (!AppValidator.checkNullString(this.inputInstituteId.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, this.getResources().getString(R.string.institute_Id));
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    private boolean validateSignInResponse(JSONObject response) {
        boolean signInsuccess = false;
        if ((response != null)) {
            try {
                String status = response.getString("status");
                String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                Log.d(TAG, "status val" + status + "msg" + msg);

                if ((status != null)) {
                    if (((status.equalsIgnoreCase("activationError")) || (status.equalsIgnoreCase("alreadyRegistered")) ||
                            (status.equalsIgnoreCase("notRegistered")) || (status.equalsIgnoreCase("error")))) {
                        signInsuccess = false;
                        Log.d(TAG, "Show error dialog");
                        AlertDialogHelper.showSimpleAlertDialog(this, msg);

                    } else {
                        signInsuccess = true;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return signInsuccess;
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {
            try {
                JSONObject userData = response.getJSONObject("instituteData");
//                JSONObject userData = response.getJSONArray("userData").getJSONObject(0);
                String ins_id = null;
                String dynamicDB = null;
//                if(instData != null) {
//                    dynamicDB = instData.getString("dynamic_db");
//                    PreferenceStorage.saveUserDynamicDB(this, dynamicDB);
//                }
                Log.d(TAG, "userData dictionary" + userData.toString());
                if (userData != null) {
//                    Log.d(TAG, "created user id" + ins_id);
                    dynamicDB = userData.getString("dynamic_db");
                    PreferenceStorage.saveUserDynamicDB(this, dynamicDB);

                    //need to re do this
                    Log.d(TAG, "sign in response is" + response.toString());
//                    JSONObject instData = response.getJSONObject("0");
                    String instituteName = userData.getJSONObject("0").getString("institute_name");
                    String instituteCode = userData.getJSONObject("0").getString("institute_code");
                    String ensyfiInstituteCode = userData.getJSONObject("0").getString("enc_institute_code");
                    String instituteLogo = userData.getJSONObject("0").getString("institute_logo");
                    String instituteLogoPicUrl = EnsyfiConstants.GET_SCHOOL_LOGO + instituteLogo;
//                    String userDynamicAPI = EnsyfiConstants.BASE_URL + instituteCode;

                    if ((instituteName != null) && !(instituteName.isEmpty()) && !instituteName.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveInstituteName(this, instituteName);
                    }
                    if ((ensyfiInstituteCode != null) && !(ensyfiInstituteCode.isEmpty()) && !ensyfiInstituteCode.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveEnsyfiInstituteCode(this, ensyfiInstituteCode);
                    }
                    if ((instituteCode != null) && !(instituteCode.isEmpty()) && !instituteCode.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveInstituteCode(this, instituteCode);
                    }
                    if ((instituteLogoPicUrl != null) && !(instituteLogoPicUrl.isEmpty()) && !instituteLogoPicUrl.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveInstituteLogoPic(this, instituteLogoPicUrl);
                    }
//                    if ((userDynamicAPI != null) && !(userDynamicAPI.isEmpty()) && !userDynamicAPI.equalsIgnoreCase("null")) {
//                        PreferenceStorage.saveUserDynamicAPI(this, userDynamicAPI);
//                    }
//                    if ((dynamicDB != null) && !(dynamicDB.isEmpty()) && !dynamicDB.equalsIgnoreCase("null")){
//                        PreferenceStorage.saveUserDynamicDB(this, dynamicDB);
//                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, UserLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(this, error);
    }
}
