package com.palprotech.ensyfi.activity.loginmodule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.parentsmodule.ParentDashBoardActivity;
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

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener, IServiceListener, DialogClickListener {

    private static final String TAG = ChangePasswordActivity.class.getName();
    EditText edtCurrentName, edtNewPassword, edtConfirmPassword;
    Button btnConfirm;
    private ServiceHelper serviceHelper;
    private ProgressDialogHelper progressDialogHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        edtCurrentName = (EditText) findViewById(R.id.edtcurrentpassword);
        edtNewPassword = (EditText) findViewById(R.id.edtnewpassword);
        edtConfirmPassword = (EditText) findViewById(R.id.edtconfirmpassword);
        btnConfirm = (Button) findViewById(R.id.confirm);
        btnConfirm.setOnClickListener(this);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setupUI(findViewById(R.id.scrollID));

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
                    hideSoftKeyboard(ChangePasswordActivity.this);
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
        if (CommonUtils.isNetworkAvailable(this)) {
            if (v == btnConfirm) {
                if (validateFields()) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(EnsyfiConstants.PARAMS_FP_USER_ID, PreferenceStorage.getUserId(this));
                        jsonObject.put(EnsyfiConstants.PARAMS_CP_CURRENT_PASSWORD, edtCurrentName.getText().toString());
                        jsonObject.put(EnsyfiConstants.PARAMS_PASSWORD, edtNewPassword.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
                    String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.CHANGE_PASSWORD;
                    serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
                }
            } else {
                AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
            }
        }
    }

    private boolean validateFields() {
        int minChar = 8;
        if (!AppValidator.checkNullString(this.edtCurrentName.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, this.getResources().getString(R.string.enter_password));
            return false;
        } else if (!AppValidator.checkNullString(this.edtNewPassword.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, this.getResources().getString(R.string.enter_password));
            return false;
        } else if (!AppValidator.checkNullString(this.edtConfirmPassword.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, this.getResources().getString(R.string.enter_password));
            return false;
        } else if (!AppValidator.checkStringMinLength(minChar, this.edtNewPassword.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, this.getResources().getString(R.string.min_pass_length));
            return false;
        } else if (!this.edtNewPassword.getText().toString().trim().contentEquals(this.edtConfirmPassword.getText().toString().trim())) {
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
            alertDialogBuilder.setTitle("Password changed");
            alertDialogBuilder.setMessage("Password changed successfully");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            Intent intent = new Intent(getApplicationContext(), ParentDashBoardActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
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

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(this, error);
    }
}
