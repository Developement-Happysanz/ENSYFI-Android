package com.palprotech.ensyfi.activity.loginmodule;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.AppValidator;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 25-05-2017.
 */

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener, IServiceListener, DialogClickListener {

    private static final String TAG = ResetPasswordActivity.class.getName();
    private Button btnReset;
    private EditText edtNewPassword, edtRetypePassword;
    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        PreferenceStorage.saveForgotPasswordStatusEnable(this, "no");
        initializeViews();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    // Initialize Views
    private void initializeViews() {
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        btnReset = (Button) findViewById(R.id.confirm);
        btnReset.setOnClickListener(this);
        edtNewPassword = (EditText) findViewById(R.id.edtPassword);
        edtRetypePassword = (EditText) findViewById(R.id.edtConfirmPassword);
    }


    @Override
    public void onClick(View v) {
        if (CommonUtils.isNetworkAvailable(this)) {
            if (v == btnReset) {
                if (validateFields()) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(EnsyfiConstants.PARAMS_FP_USER_ID, PreferenceStorage.getUserId(this));
                        jsonObject.put(EnsyfiConstants.PARAMS_PASSWORD, edtNewPassword.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
                    String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(this) + EnsyfiConstants.RESET_PASSWORD;
                    serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
                }
            }
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection available");
        }
    }

    private boolean validateFields() {
        int minChar = 8;
        if (!AppValidator.checkNullString(this.edtNewPassword.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, this.getResources().getString(R.string.enter_password));
            return false;
        } else if (!AppValidator.checkNullString(this.edtRetypePassword.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, this.getResources().getString(R.string.enter_password));
            return false;
        } else if (!AppValidator.checkStringMinLength(minChar, this.edtNewPassword.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, this.getResources().getString(R.string.min_pass_length));
            return false;
        } else if (!this.edtNewPassword.getText().toString().trim().contentEquals(this.edtRetypePassword.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, this.getResources().getString(R.string.password_mismatch));
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

    @Override
    public void onResponse(JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        if (validateForgotPasswordResponse(response)) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Password reset");
            alertDialogBuilder.setMessage("Password successfully reset. Perform Login in again");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            PreferenceStorage.saveForgotPasswordStatusEnable(getApplicationContext(), "yes");
                            doLogout();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private boolean validateForgotPasswordResponse(JSONObject response) {
        boolean forgotPasswordsuccess = false;
        if ((response != null)) {
            try {
                String status = response.getString("status");
                String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                Log.d(TAG, "status val" + status + "msg" + msg);

                if ((status != null)) {
                    if (((status.equalsIgnoreCase("notRegistered")) || (status.equalsIgnoreCase("error")))) {
                        forgotPasswordsuccess = false;
                        if (status.equalsIgnoreCase("notRegistered")) {

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                            alertDialogBuilder.setMessage(msg);
                            alertDialogBuilder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();

                        } else {
                            Log.d(TAG, "Show error dialog");
                            AlertDialogHelper.showSimpleAlertDialog(this, msg);
                        }

                    } else {
                        forgotPasswordsuccess = true;

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return forgotPasswordsuccess;
    }

    public void doLogout() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().clear().apply();

        Intent homeIntent = new Intent(this, SplashScreenActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        this.finish();
    }

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(this, error);
    }
}
