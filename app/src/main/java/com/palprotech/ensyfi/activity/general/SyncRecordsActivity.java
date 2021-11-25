package com.palprotech.ensyfi.activity.general;

import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.teachermodule.SyncAcademicExamMarks;
import com.palprotech.ensyfi.activity.teachermodule.SyncAttendanceHistoryRecordsActivity;
import com.palprotech.ensyfi.activity.teachermodule.SyncClassTestHomeWork;
import com.palprotech.ensyfi.activity.teachermodule.SyncClassTestMark;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.support.RefreshExamAndExamDetails;
import com.palprotech.ensyfi.bean.teacher.support.RefreshHomeWorkClassTestData;
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
    private LinearLayout btnSyncAttendanceRecords;
    private LinearLayout btnSyncClassTestHomeworkRecords;
    private LinearLayout btnSyncExamMarks;
    private LinearLayout btnRefreshClassTestHomeworkRecords;
    private LinearLayout btnRefreshSyncExamMarks, btnSyncClassTestMarksRecords;
    private SyncAttendanceHistoryRecordsActivity syncAttendanceHistoryRecordsActivity;
    private SyncClassTestHomeWork syncClassTestHomeWork;
    private SyncAcademicExamMarks syncAcademicExamMarks;
    private SyncClassTestMark syncClassTestMark;
    private RefreshExamAndExamDetails refreshExamAndExamDetails;
    private ProgressDialogHelper progressDialogHelper;
    private RefreshHomeWorkClassTestData refreshHomeWorkClassTestData;
    SQLiteHelper db;
    String localAttendanceId, ac_year, class_id, class_total, no_of_present, no_of_absent,
            attendance_period, created_by, created_at, status;
    TextView attendRecords, assignRecords, ctMarksRecords, examMarkRecords;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_records);
        db = new SQLiteHelper(getApplicationContext());
        btnSyncAttendanceRecords = (LinearLayout) findViewById(R.id.btnSyncAttendanceRecords);
        btnSyncAttendanceRecords.setOnClickListener(this);

        btnSyncClassTestHomeworkRecords = (LinearLayout) findViewById(R.id.btnSyncClassTestHomeworkRecords);
        btnSyncClassTestHomeworkRecords.setOnClickListener(this);

        btnSyncExamMarks = (LinearLayout) findViewById(R.id.btnSyncExamMarks);
        btnSyncExamMarks.setOnClickListener(this);

        btnRefreshClassTestHomeworkRecords = (LinearLayout) findViewById(R.id.btnRefreshClassTestHomeworkRecords);
        btnRefreshClassTestHomeworkRecords.setOnClickListener(this);

        btnSyncClassTestMarksRecords = (LinearLayout) findViewById(R.id.btnSyncClassTestMarksRecords);
        btnSyncClassTestMarksRecords.setOnClickListener(this);

//        btnRefreshSyncExamMarks = (LinearLayout) findViewById(R.id.btnRefreshSyncExamMarks);
//        btnRefreshSyncExamMarks.setOnClickListener(this);

        attendRecords = findViewById(R.id.text_attendance_count);
        attendRecords.setText(db.isAttendanceSynced());

        assignRecords = findViewById(R.id.text_ct_hw_count);
        assignRecords.setText(db.isClassTestHomeWorkStatusFlag());

        ctMarksRecords = findViewById(R.id.text_ct_marks_count);
        ctMarksRecords.setText(db.isClassTestMarkStatusFlag());

        examMarkRecords = findViewById(R.id.text_exam_marks_count);
        examMarkRecords.setText(db.isExamMarkStatusFlag());

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        syncAttendanceHistoryRecordsActivity = new SyncAttendanceHistoryRecordsActivity(this);
        syncClassTestHomeWork = new SyncClassTestHomeWork(this);
        syncAcademicExamMarks = new SyncAcademicExamMarks(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        syncClassTestMark = new SyncClassTestMark(this);
        refreshHomeWorkClassTestData = new RefreshHomeWorkClassTestData(this);
        refreshExamAndExamDetails = new RefreshExamAndExamDetails(this);
    }

    public void syncAttendancePreRecord() {
        int checkSync = Integer.parseInt(db.isAttendanceSynced());
        if (checkSync != 0) {
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
                                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
                            String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.GET_TEACHERS_CLASS_ATTENDANCE_API;
                            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

                        } while (c.moveToNext());
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "Nothing to sync");
        }
    }

    @Override
    public void onClick(View v) {
        if (CommonUtils.isNetworkAvailable(this)) {
            if (v == btnSyncAttendanceRecords) {
                syncAttendancePreRecord();
            }

            if (v == btnSyncClassTestHomeworkRecords) {

                int ClassTestHomeWorkCount = Integer.parseInt(db.isClassTestHomeWorkStatusFlag());
                if (ClassTestHomeWorkCount > 0) {
                    syncClassTestHomeWork.syncClassTestHomeWorkRecords();
                } else {
                    AlertDialogHelper.showSimpleAlertDialog(this, "Nothing to sync");
                }
            }
            if (v == btnSyncClassTestMarksRecords) {
                int ClassTestMark = Integer.parseInt(db.isClassTestMarkStatusFlag());
                if (ClassTestMark > 0) {
                    syncClassTestMark.syncClassTestMarkToServer();
                } else {
                    AlertDialogHelper.showSimpleAlertDialog(this, "Nothing to sync");
                }
//                syncClassTestMark.syncClassTestMarkToServer();
            }
            if (v == btnSyncExamMarks) {
                int ExamTestMark = Integer.parseInt(db.isExamMarkStatusFlag());
                if (ExamTestMark > 0) {
                    syncAcademicExamMarks.SyncAcademicMarks();
                } else {
                    AlertDialogHelper.showSimpleAlertDialog(this, "Nothing to sync");
                }
            }
            if (v == btnRefreshClassTestHomeworkRecords) {
                String count = db.isClassTestHomeWorkStatusFlag();
                int convertCount = Integer.parseInt(count);
                if (convertCount > 0) {
                    AlertDialogHelper.showSimpleAlertDialog(this, "Sync all homework and class test records !!!");
                } else {
                    refreshHomeWorkClassTestData.ReloadHomeWorkClassTest();
                }
            }
//            if (v == btnRefreshSyncExamMarks) {
////                String count = db.isClassTestHomeWorkStatusFlag();
////                int convertCount = Integer.parseInt(count);
//                refreshExamAndExamDetails.ReloadExamAndExamDetails();
//            }
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
                    } else if (status.equalsIgnoreCase("AlreadyAdded")) {
                        String latestAttendanceInsertedServerId = response.getString("last_attendance_id");
                        if (!latestAttendanceInsertedServerId.isEmpty()) {
                            db.updateAttendanceId(latestAttendanceInsertedServerId, localAttendanceId);
                            db.updateAttendanceSyncStatus(localAttendanceId);
                            db.updateAttendanceHistoryServerId(latestAttendanceInsertedServerId, localAttendanceId);
                            syncAttendanceHistoryRecordsActivity.syncAttendanceHistoryRecords(latestAttendanceInsertedServerId);
                        }
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
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
                    syncAttendanceHistoryRecordsActivity.syncAttendanceHistoryRecords(latestAttendanceInsertedServerId);
                    Toast.makeText(getApplicationContext(), "Attendance synced to the server...", Toast.LENGTH_LONG).show();
                    startActivity(getIntent());
                    finish();
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
}
