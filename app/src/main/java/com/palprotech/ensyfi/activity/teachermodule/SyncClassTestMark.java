package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 14-07-2017.
 */

public class SyncClassTestMark implements IServiceListener {

    private static final String TAG = "SyncClassTestMark";
    private Context context;
    String classTestMarkId, studentId, localHwId, serverHwId, marks, remarks, status, createdBy, createdAt,
            updatedBy, updatedAt, syncStatus;

    SQLiteHelper db;
    private ServiceHelper serviceHelper;
    private ProgressDialogHelper progressDialogHelper;

    public SyncClassTestMark(Context context) {
        this.context = context;
    }

    public void syncClassTestMark(String ServerHomeWorkId) {
        serviceHelper = new ServiceHelper(context);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(context);
        db = new SQLiteHelper(context);
        try {
            Cursor c = db.getClassTestMarkList(ServerHomeWorkId);
            String newOk = "";
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        classTestMarkId = c.getString(0);
                        studentId = c.getString(1);
                        localHwId = c.getString(2);
                        serverHwId = c.getString(3);
                        marks = c.getString(4);
                        remarks = c.getString(5);
                        status = c.getString(6);
                        createdBy = c.getString(7);
                        createdAt = c.getString(8);
                        updatedBy = c.getString(9);
                        updatedAt = c.getString(10);
                        syncStatus = c.getString(11);

                        JSONObject jsonObject = new JSONObject();
                        try {

                            jsonObject.put(EnsyfiConstants.PARAMS_CTMARKS_HW_SERVER_MASTER_ID, serverHwId);
                            jsonObject.put(EnsyfiConstants.PARAMS_CTMARKS_STUDENT_ID, studentId);
                            jsonObject.put(EnsyfiConstants.PARAMS_CTMARKS_MARKS, marks);
                            jsonObject.put(EnsyfiConstants.PARAMS_CTMARKS_REMARKS, remarks);
                            jsonObject.put(EnsyfiConstants.PARAMS_CTMARKS_CREATED_BY, createdBy);
                            jsonObject.put(EnsyfiConstants.PARAMS_CTMARKS_CREATED_AT, createdAt);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressDialogHelper.showProgressDialog(context.getString(R.string.progress_loading));
                        String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(context) + EnsyfiConstants.GET_CLASS_TEST_MARK_API;
                        serviceHelper.makeGetServiceCall(jsonObject.toString(), url);


                    } while (c.moveToNext());
                }
            }
        } catch (
                Exception ex)

        {
            ex.printStackTrace();
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
                String classTestMarksServerId = response.getString("last_id");
                if (!classTestMarksServerId.isEmpty()) {
                    db.updateClassTestSyncStatus(classTestMarkId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onError(String error) {

    }
}
