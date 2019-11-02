package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.ExamResultListAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.support.SaveTeacherData;
import com.palprotech.ensyfi.bean.teacher.viewlist.ExamResult;
import com.palprotech.ensyfi.bean.teacher.viewlist.ExamResultList;
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

import java.util.ArrayList;

/**
 * Created by Admin on 19-07-2017.
 */

public class AcademicExamResultView extends AppCompatActivity implements IServiceListener, DialogClickListener {

    long hwId;
    String classSubjectId;
    String classId;
    String examsId;
    String homeWorkId;
    private static final String TAG = ViewClassTestMarkActivity.class.getName();
    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    ListView loadMoreListView;
    ExamResultListAdapter examResultListAdapter;
    ArrayList<ExamResult> examResultArrayList;
    int totalCount = 0;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();
    SQLiteHelper db;
    String examId, examName, isInternalExternal, classMasterId, sectionName, className, fromDate, toDate, markStatus;
    String resString = "";
    SaveTeacherData tchDat = new SaveTeacherData(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.academic_exam_result_view);

        hwId = getIntent().getExtras().getLong("id");
        classSubjectId = getIntent().getExtras().getString("subject_id");
        classId = getIntent().getExtras().getString("classMasterId");
        examsId = getIntent().getExtras().getString("examId");
        homeWorkId = String.valueOf(hwId);
        String examId = String.valueOf(hwId);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        db = new SQLiteHelper(getApplicationContext());
        progressDialogHelper = new ProgressDialogHelper(this);
        loadMoreListView = (ListView) findViewById(R.id.listView_events);
        examResultArrayList = new ArrayList<>();
        GetAcademicExamInfo(examId);

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.edit_mark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddAcademicExamMarksActivity.class);
                intent.putExtra("id", hwId);
                intent.putExtra("subject_id", classSubjectId);
                intent.putExtra("classMasterId", classId);
                intent.putExtra("examId", examsId);
                intent.putExtra("type", "edit");
                startActivity(intent);
                finish();
            }
        });

        GetMarkStatus();

    }

    private void GetAcademicExamInfo(String examIdLocal) {
        try {
            Cursor c = db.getAcademicExamInfo(examIdLocal);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        examId = c.getString(1);
                        examName = c.getString(2);
                        isInternalExternal = c.getString(3);
                        classMasterId = c.getString(4);
                        sectionName = c.getString(5);
                        className = c.getString(6);
                        fromDate = c.getString(7);
                        toDate = c.getString(8);
                        markStatus = c.getString(9);
                    } while (c.moveToNext());
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void GetMarkStatus() {
        resString = "status";
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.KEY_USER_ID, classMasterId);
                jsonObject.put(EnsyfiConstants.PARAM_EXAM_ID, examId);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_ACADEMIC_EXAM_MARK_STATUS;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private void GetMarkDataDB() {
        resString = "markDataDB";
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.KEY_USER_ID, PreferenceStorage.getUserId(this));
                jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_CLASS_MASTER_ID, classMasterId);
                jsonObject.put(EnsyfiConstants.PARAM_EXAM_ID, examId);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_ACADEMIC_EXAM_MARK_DETAILS;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private void GetClassTestMarkData() {
        resString = "data";

        if (examResultArrayList != null)
            examResultArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID_NEW, classMasterId);
                jsonObject.put(EnsyfiConstants.PARAM_EXAM_ID, examId);
                jsonObject.put(EnsyfiConstants.PARAMS_SUBJECT_ID_SHOW, PreferenceStorage.getTeacherSubject(getApplicationContext()));
                jsonObject.put(EnsyfiConstants.PARAM_IS_INTERNAL_EXTERNAL, isInternalExternal);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_ACADEMIC_EXAM_MARK;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
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
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    @Override
    public void onResponse(final JSONObject response) {
        progressDialogHelper.hideProgressDialog();

        if (validateSignInResponse(response)) {
            if (resString.equalsIgnoreCase("status")) {
                findViewById(R.id.edit_mark).setVisibility(View.VISIBLE);
                GetClassTestMarkData();
            } else if (resString.equalsIgnoreCase("data")) {
                Gson gson = new Gson();
                ExamResultList examResultList = gson.fromJson(response.toString(), ExamResultList.class);
                if (examResultList.getExamResult() != null && examResultList.getExamResult().size() > 0) {
                    totalCount = examResultList.getCount();
                    isLoadingForFirstTime = false;
                    updateListAdapter(examResultList.getExamResult());
                }
                GetMarkDataDB();
            } else if (resString.equalsIgnoreCase("markDataDB")) {
                try {
                    tchDat.saveExamsDetails(response.getJSONArray("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
                AlertDialogHelper.showSimpleAlertDialog(getApplicationContext(), error);
            }
        });
    }

    protected void updateListAdapter(ArrayList<ExamResult> examResultArrayList) {
        this.examResultArrayList.addAll(examResultArrayList);
        if (examResultListAdapter == null) {
            examResultListAdapter = new ExamResultListAdapter(this, this.examResultArrayList);
            loadMoreListView.setAdapter(examResultListAdapter);
        } else {
            examResultListAdapter.notifyDataSetChanged();
        }
    }
}
