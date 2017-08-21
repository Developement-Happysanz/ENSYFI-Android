package com.palprotech.ensyfi.bean.teacher.support;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.loginmodule.ForgotPasswordActivity;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 21-08-2017.
 */

public class RefreshHomeWorkClassTestData implements IServiceListener {

    private ServiceHelper serviceHelper;
    private SaveTeacherData teacherData;
    private ProgressDialogHelper progressDialogHelper;
    SQLiteHelper database;
    private Context context;
    private static final String TAG = "LoadHomeWorkClassTest";

    public RefreshHomeWorkClassTestData(Context context) {
        this.context = context;
    }

    public void ReloadHomeWorkClassTest() {

        serviceHelper = new ServiceHelper(context);
        serviceHelper.setServiceListener(this);
        database = new SQLiteHelper(context);
        progressDialogHelper = new ProgressDialogHelper(context);
        teacherData = new SaveTeacherData(context);

        if (CommonUtils.isNetworkAvailable(context)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CTHW_TEACHER_ID, PreferenceStorage.getTeacherId(context));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(context.getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(context) + EnsyfiConstants.LOAD_STUDENT_CLASSTEST_AND_HOMEWORK;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
        } else {
            AlertDialogHelper.showSimpleAlertDialog(context, "No Network connection");
        }
    }


    private boolean validateSignInResponse(JSONObject response) {
        boolean signInSuccess = false;
        if ((response != null)) {
            try {
                String status = response.getString("status");
                String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                Log.d(TAG, "status val" + status + "msg" + msg);

                if ((status != null)) {
                    if (((status.equalsIgnoreCase("activationError")) || (status.equalsIgnoreCase("alreadyRegistered")) ||
                            (status.equalsIgnoreCase("notRegistered")) || (status.equalsIgnoreCase("error")))) {
                        signInSuccess = false;
                        Log.d(TAG, "Show error dialog");
                        AlertDialogHelper.showSimpleAlertDialog(context, msg);

                    } else {
                        signInSuccess = true;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return signInSuccess;
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {
            try {
                Log.d(TAG, "Error while sign In");
                JSONArray getHomeWorkClassTest = response.getJSONArray("homeworkDetails");
                teacherData.saveHomeWorkClassTest(getHomeWorkClassTest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(context, error);
    }
}
