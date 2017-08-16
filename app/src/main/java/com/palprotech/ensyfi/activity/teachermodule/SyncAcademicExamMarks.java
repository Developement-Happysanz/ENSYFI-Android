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
 * Created by Admin on 15-07-2017.
 */

public class SyncAcademicExamMarks implements IServiceListener {

    private static final String TAG = "SyncAcademicExamMarks";
    private Context context;
    SQLiteHelper db;
    private ServiceHelper serviceHelper;
    private ProgressDialogHelper progressDialogHelper;
    String examMarksId, examId, teacherId, subjectId, studentId, classMasterId, internalMark, internalGrade,
            externalMark, externalGrade, totalMarks, totalGrade, createdBy, createdAt, updatedBy, updatedAt, syncStatus;

    public SyncAcademicExamMarks(Context context) {
        this.context = context;
    }

    public void SyncAcademicMarks() {
        serviceHelper = new ServiceHelper(context);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(context);
        db = new SQLiteHelper(context);
        try {
            Cursor c = db.getAcademicExamMarksList();
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        examMarksId = c.getString(0);
                        examId = c.getString(1);
                        teacherId = c.getString(2);
                        subjectId = c.getString(3);
                        studentId = c.getString(4);
                        classMasterId = c.getString(5);
                        internalMark = c.getString(6);
                        internalGrade = c.getString(7);
                        externalMark = c.getString(8);
                        externalGrade = c.getString(9);
                        totalMarks = c.getString(10);
                        totalGrade = c.getString(11);
                        createdBy = c.getString(12);
                        createdAt = c.getString(13);
                        updatedBy = c.getString(14);
                        updatedAt = c.getString(15);
                        syncStatus = c.getString(16);
                        String internalExternal = db.isInternalExternalFlag(examId);

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_EXAM_ID, examId);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_TEACHER_ID, teacherId);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_SUBJECT_ID, subjectId);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_STUDENT_ID, studentId);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_CLASS_MASTER_ID, classMasterId);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_INTERNAL_MARK, internalMark);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_EXTERNAL_MARK, externalMark);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_TOTAL_MARK, totalMarks);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_INTERNAL_EXTERNAL_MARK, internalExternal);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_CREATED_BY, createdBy);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialogHelper.showProgressDialog(context.getString(R.string.progress_loading));
                        String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(context) + EnsyfiConstants.GET_ACADEMIC_EXAM_MARK_API;
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
                String academicExamMarksServerId = response.getString("last_id");
                if (!academicExamMarksServerId.isEmpty()) {
                    db.updateAcademicExamMarksSyncStatus(examMarksId);
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
