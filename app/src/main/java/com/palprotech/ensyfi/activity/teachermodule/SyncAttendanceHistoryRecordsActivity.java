package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

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
 * Created by Admin on 07-07-2017.
 */

public class SyncAttendanceHistoryRecordsActivity implements IServiceListener {

    private static final String TAG = "SyncAttendanceHistory";
    private Context context;
    String localAttendanceHistoryId, history_attend_id, history_server_attend_id, history_class_id, history_student_id,
            history_abs_date, history_a_status, history_attend_period, history_a_val, history_a_taken_by, history_created_at,
            history_status;
    SQLiteHelper db;
    private ServiceHelper serviceHelper;
    private ProgressDialogHelper progressDialogHelper;

    public SyncAttendanceHistoryRecordsActivity(Context context) {
        this.context = context;
    }

    public void syncAttendanceHistoryRecords(String latestAttendanceInsertedServerId) {
        serviceHelper = new ServiceHelper(context);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(context);

        db = new SQLiteHelper(context);
        try {
            Cursor c = db.getAttendanceHistoryList(latestAttendanceInsertedServerId);
            String newOk = "";
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        localAttendanceHistoryId = c.getString(0);
                        history_attend_id = c.getString(1);
                        history_server_attend_id = c.getString(2);
                        history_class_id = c.getString(3);
                        history_student_id = c.getString(4);
                        history_abs_date = c.getString(5);
                        history_a_status = c.getString(6);
                        history_attend_period = c.getString(7);
                        history_a_val = c.getString(8);
                        history_a_taken_by = c.getString(9);
                        history_created_at = c.getString(10);
                        history_status = c.getString(11);

                        JSONObject jsonObject = new JSONObject();
                        try {

                            jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_HISTORY_ATTEND_ID, history_server_attend_id);
                            jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_HISTORY_CLASS_ID, history_class_id);
                            jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_HISTORY_STUDENT_ID, history_student_id);
                            jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_HISTORY_ABS_DATE, history_abs_date);
                            jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_HISTORY_A_STATUS, history_a_status);
                            jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_HISTORY_ATTEND_PERIOD, history_attend_period);
                            jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_HISTORY_A_VAL, history_a_val);
                            jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_HISTORY_A_TAKEN_BY, history_a_taken_by);
                            jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_HISTORY_CREATED_AT, history_created_at);
                            jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_HISTORY_STATUS, history_status);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (!history_a_status.equalsIgnoreCase("P")) {
                            progressDialogHelper.showProgressDialog(context.getString(R.string.progress_loading));
                            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(context) + EnsyfiConstants.GET_TEACHERS_CLASS_ATTENDANCE_HISTORY_API;
                            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
                        } else {
                            db.updateAttendanceHistorySyncStatus(localAttendanceHistoryId);
                        }

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

                    } else if (status.equalsIgnoreCase("AlreadyAdded")) {
                        db.updateAttendanceSyncStatus(localAttendanceHistoryId);
                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
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
                String latestAttendanceHistoryInsertedServerId = response.getString("last_attendance_history_id");
                if (!latestAttendanceHistoryInsertedServerId.isEmpty()) {
                    db.updateAttendanceHistorySyncStatus(localAttendanceHistoryId);
                } else {
                    Toast.makeText(context, "Insert Error..", Toast.LENGTH_LONG).show();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(context, error);
    }
}
