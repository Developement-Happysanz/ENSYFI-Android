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

public class SyncClassTestHomeWork implements IServiceListener {

    private static final String TAG = "SyncClassTestHomeWork";
    private Context context;
    String homeWorkId, serverHomeWorkId, yearId, classId, teacherId, homeWorkType, subjectId, subjectName, title,
            testDate, dueDate, homeWorkDetails, status, markStatus, createdBy, createdAt, updatedBy, updatedAt, syncStatus;

    SQLiteHelper db;
    private ServiceHelper serviceHelper;
    private SyncClassTestMark syncClassTestMark;
    private ProgressDialogHelper progressDialogHelper;

    public SyncClassTestHomeWork(Context context) {
        this.context = context;
    }

    public void syncClassTestHomeWorkRecords() {

        serviceHelper = new ServiceHelper(context);
        serviceHelper.setServiceListener(this);
        syncClassTestMark = new SyncClassTestMark(context);
        progressDialogHelper = new ProgressDialogHelper(context);

        db = new SQLiteHelper(context);

        try {
            Cursor c = db.getClassTestHomeWorkList();
            String newOk = "";
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {

                        homeWorkId = c.getString(0);
                        serverHomeWorkId = c.getString(1);
                        yearId = c.getString(2);
                        classId = c.getString(3);
                        teacherId = c.getString(4);
                        homeWorkType = c.getString(5);
                        subjectId = c.getString(6);
                        subjectName = c.getString(7);
                        title = c.getString(8);
                        testDate = c.getString(9);
                        dueDate = c.getString(10);
                        homeWorkDetails = c.getString(11);
                        status = c.getString(12);
                        markStatus = c.getString(13);
                        createdBy = c.getString(14);
                        createdAt = c.getString(15);
                        updatedBy = c.getString(16);
                        updatedAt = c.getString(17);
                        syncStatus = c.getString(18);

                        JSONObject jsonObject = new JSONObject();
                        try {

                            jsonObject.put(EnsyfiConstants.PARAMS_CTHW_CLASS_ID, classId);
                            jsonObject.put(EnsyfiConstants.PARAMS_CTHW_TEACHER_ID, teacherId);
                            jsonObject.put(EnsyfiConstants.PARAMS_CTHW_HOMEWORK_TYPE, homeWorkType);
                            jsonObject.put(EnsyfiConstants.PARAMS_CTHW_SUBJECT_ID, subjectId);
                            jsonObject.put(EnsyfiConstants.PARAMS_CTHW_TITLE, title);
                            jsonObject.put(EnsyfiConstants.PARAMS_CTHW_TEST_DATE, testDate);
                            jsonObject.put(EnsyfiConstants.PARAMS_CTHW_DUE_DATE, dueDate);
                            jsonObject.put(EnsyfiConstants.PARAMS_CTHW_HOMEWORK_DETAILS, homeWorkDetails);
                            jsonObject.put(EnsyfiConstants.PARAMS_CTHW_CREATED_BY, createdBy);
                            jsonObject.put(EnsyfiConstants.PARAMS_CTHW_CREATED_AT, createdAt);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressDialogHelper.showProgressDialog(context.getString(R.string.progress_loading));
                        String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(context) + EnsyfiConstants.GET_CLASS_TEST_HOMEWORK_API;
                        serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

                    } while (c.moveToNext());
                }
            }
        } catch (Exception ex) {
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
                String classTestHomeworkServerId = response.getString("last_id");
                db.updateClassTestHomeWorkServerId(classTestHomeworkServerId, homeWorkId);
                db.updateClassTestHomeWorkSyncStatus(homeWorkId);

                if (homeWorkType.equalsIgnoreCase("HT")) {
                    db.updateClassTestMarkServerId(classTestHomeworkServerId, homeWorkId);
                    syncClassTestMark.syncClassTestMark(classTestHomeworkServerId);
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
