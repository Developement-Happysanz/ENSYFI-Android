package com.palprotech.ensyfi.activity.adminmodule;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

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

public class CircularMasterCreationActivity extends AppCompatActivity implements View.OnClickListener, IServiceListener, DialogClickListener {

    private static final String TAG = CircularMasterCreationActivity.class.getName();
    private ImageView btnBack;
    private ImageView btnSave;
    private EditText etCircularTitle;
    private EditText etCircularDetails;
    private TextView tvAttachedFileName;
    private LinearLayout llAttachedFile;
    private Switch swStatus;

    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_master_creation);

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);

        btnBack = findViewById(R.id.back_res);
        btnBack.setOnClickListener(this);

        btnSave = findViewById(R.id.save_checked);
        btnSave.setOnClickListener(this);

        etCircularTitle = findViewById(R.id.edtCircularTitle);
        etCircularDetails = findViewById(R.id.edtCircularDetails);

        llAttachedFile = findViewById(R.id.llAttachFile);
        llAttachedFile.setOnClickListener(this);

        tvAttachedFileName = findViewById(R.id.txtAttachedFile);

        setupUI(findViewById(R.id.scrollID));

        swStatus = findViewById(R.id.swStatus);
        swStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    swStatus.setText("Active");
                } else {
                    swStatus.setText("Inactive");
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(CircularMasterCreationActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnBack) {
            finish();
        } else if (v == btnSave) {
            if (CommonUtils.isNetworkAvailable(this)) {
                if (validateFields()) {
                    String CircularTitle = "";
                    String CircularDetails = "";
                    String UserID = PreferenceStorage.getUserId(this);
                    boolean Status = false;

                    CircularTitle = etCircularTitle.getText().toString();
                    CircularDetails = etCircularDetails.getText().toString();
                    Status = swStatus.isChecked();
                    String CircularStatus = "";
                    if (Status) {
                        CircularStatus = "Active";
                    } else {
                        CircularStatus = "Inactive";
                    }
                    String AllValue = "";

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(EnsyfiConstants.PARAMS_FP_USER_ID, PreferenceStorage.getUserId(getApplicationContext()));
                        jsonObject.put(EnsyfiConstants.PARAMS_CIRCULAR_TITLE, CircularTitle);
                        jsonObject.put(EnsyfiConstants.PARAMS_CIRCUALR_DETAILS, CircularDetails);
                        jsonObject.put(EnsyfiConstants.PARAMS_CIRCULAR_STATUS, CircularStatus);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
                    String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_CIRCULAR_MASTER_ADD;
                    serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

                }
            } else {
                AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
            }

        } else if (v == llAttachedFile) {

        }
    }

    private boolean validateFields() {
        if (!AppValidator.checkNullString(this.etCircularTitle.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid title");
            return false;
        } else if (!AppValidator.checkNullString(this.etCircularDetails.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid details");
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
        try {
            if (validateSignInResponse(response)) {
            } else {
                Log.d(TAG, "Error while adding the data");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String error) {

    }
}
