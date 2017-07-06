package com.palprotech.ensyfi.activity.teachermodule;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.loginmodule.UserLoginActivity;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 06-07-2017.
 */

public class SyncRecordsActivity extends AppCompatActivity implements IServiceListener, View.OnClickListener, DialogClickListener {

    private static final String TAG = SyncRecordsActivity.class.getName();
    private ServiceHelper serviceHelper;
    private Button btnSyncAttendanceRecords;
    private ProgressDialogHelper progressDialogHelper;
    SQLiteHelper db;
    String localAttendanceId, ac_year, class_id, class_total, no_of_present, no_of_absent, attendance_period, created_by, created_at, status;
    String localAttendanceHistoryId, history_attend_id, history_server_attend_id, history_class_id, history_student_id, history_abs_date, history_a_status, history_attend_period, history_a_val, history_a_taken_by, history_created_at, history_status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_records);
        db = new SQLiteHelper(getApplicationContext());
        btnSyncAttendanceRecords = (Button) findViewById(R.id.btnSyncAttendanceRecords);
        btnSyncAttendanceRecords.setOnClickListener(this);

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
    }

    @Override
    public void onClick(View v) {
        if (CommonUtils.isNetworkAvailable(this)) {
            if (v == btnSyncAttendanceRecords) {
                try {
                    Cursor c = db.getAttendanceList();
                    if (c.getCount() > 0) {
                        if (c.moveToFirst()) {
                            do {
                                localAttendanceId = c.getString(0);
                                ac_year = c.getString(1);
                                class_id = c.getString(2);
                                class_total = c.getString(3);
                                no_of_present = c.getString(4);
                                no_of_absent = c.getString(5);
                                attendance_period = c.getString(6);
                                created_by = c.getString(7);
                                created_at = c.getString(8);
                                status = c.getString(9);

                            } while (c.moveToNext());

                            JSONObject jsonObject = new JSONObject();
                            try {

                                jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_AC_YEAR, ac_year);
                                jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_CLASS_ID, class_id);
                                jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_CLASS_TOTAL, class_total);
                                jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_NO_OF_PRESSENT, no_of_present);
                                jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_NO_OF_ABSENT, no_of_absent);
                                jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_PERIOD, attendance_period);
                                jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_CREATED_BY, created_by);
                                jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_CREATED_AT, created_at);
                                jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_STATUS, status);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
                            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(this) + EnsyfiConstants.GET_TEACHERS_CLASS_ATTENDANCE_API;
                            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

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
                        AlertDialogHelper.showSimpleAlertDialog(this, msg);

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
                String latestAttendanceInsertedServerId = response.getString("last_attendance_id");
                if (!latestAttendanceInsertedServerId.isEmpty()) {
                    db.updateAttendanceId(latestAttendanceInsertedServerId, localAttendanceId);
                    db.updateAttendanceSyncStatus(localAttendanceId);
                    db.updateAttendanceHistoryServerId(latestAttendanceInsertedServerId, localAttendanceId);
                    callAttendanceHistoryUpdate(latestAttendanceInsertedServerId);
                } else {
                    Toast.makeText(getApplicationContext(), "Insert Error..", Toast.LENGTH_LONG).show();
                }

                String latestAttendanceHistoryInsertedServerId = response.getString("last_attendance_history_id");
                if (!latestAttendanceHistoryInsertedServerId.isEmpty()) {

                } else {
                    Toast.makeText(getApplicationContext(), "Insert Error..", Toast.LENGTH_LONG).show();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onError(String error) {

        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(this, error);
    }

    private void callAttendanceHistoryUpdate(String latestAttendanceInsertedServerId) {

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

                    } while (c.moveToNext());

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

                    progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
                    String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(this) + EnsyfiConstants.GET_TEACHERS_CLASS_ATTENDANCE_HISTORY_API;
                    serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
