package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.viewlist.ExamResult;
import com.palprotech.ensyfi.bean.teacher.viewlist.ExamResultList;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Admin on 12-08-2017.
 */

public class AddAcademicExamMarksOnlyTotalActivity extends AppCompatActivity implements View.OnClickListener, DialogClickListener, IServiceListener {

    long hwId;
    String classSubjectId;
    String classId;
    String examsId;
    SQLiteHelper db;
    String examMarksId, examId, teacherId, subjectId, studentId, classMasterId, mark, grade,
            externalMark, externalGrade, totalMarks, totalGrade, createdBy, createdAt, updatedBy, updatedAt, syncStatus;
    String getExamId, examName, getClassMasterId, sectionName, className, fromDate, toDate, markStatus, Pagetype, classIdSend, examsIdSend;
    ImageView btnSave;
    Calendar c = Calendar.getInstance();
    String localExamId, formattedServerDate;
    LinearLayout layout_all;
    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;

    private String resString;
    ArrayList<ExamResult> examResultArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_academic_exam_marks_only_total);

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);

        hwId = getIntent().getExtras().getLong("id");
        classSubjectId = getIntent().getExtras().getString("subject_id");
        classIdSend = getIntent().getExtras().getString("classMasterId");
        examsIdSend = getIntent().getExtras().getString("examId");
        Pagetype = getIntent().getExtras().getString("type");
        db = new SQLiteHelper(getApplicationContext());
        localExamId = String.valueOf(hwId);
        layout_all = (LinearLayout) findViewById(R.id.layout_timetable);
        btnSave = (ImageView) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        SimpleDateFormat serverDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedServerDate = serverDF.format(c.getTime());

        if (Pagetype != null && Pagetype.equalsIgnoreCase("edit")) {
            GetClassTestMarkData();
        } else {
            GetAcademicExamInfo(localExamId);
            GetStudentsList(getClassMasterId);
        }


        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);

        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
        return true;
    }

    private void GetClassTestMarkData() {

        resString = "markData";
        if (examResultArrayList != null)
            examResultArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID_NEW, classIdSend);
                jsonObject.put(EnsyfiConstants.PARAM_EXAM_ID, examsIdSend);
                jsonObject.put(EnsyfiConstants.PARAMS_SUBJECT_ID_SHOW, PreferenceStorage.getTeacherSubject(getApplicationContext()));
                jsonObject.put(EnsyfiConstants.PARAM_IS_INTERNAL_EXTERNAL, "0");
                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.GET_ACADEMIC_EXAM_MARK;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private void loadStudentsList(ArrayList<ExamResult> classSectionId) {

        try {

            TableLayout layout = new TableLayout(this);
            layout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            layout_all.setScrollbarFadingEnabled(false);
            layout.setPadding(0, 50, 0, 50);

            TableRow.LayoutParams cellLp = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            cellLp.setMargins(2, 2, 2, 2);
            int i = 1;
            for (int c1 = 0; c1 < classSectionId.size(); c1++) {
                LinearLayout cell = new LinearLayout(this);
                cell.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                cell.setOrientation(LinearLayout.HORIZONTAL);
                cell.setPadding(20, 5, 20, 5);
                cell.setBackgroundColor(Color.parseColor("#FFFFFF"));

                TextView t1 = new TextView(this);
                t1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 0.10f));
                t1.setGravity(Gravity.CENTER);
                t1.setVisibility(View.GONE);
//                t1.setText(c.getString(1));
                t1.setText(classSectionId.get(c1).getEnroll_id());
                t1.setTextColor(Color.parseColor("#FF68358E"));
                t1.setHeight(120);
                t1.setWidth(100);
                t1.setPadding(1, 0, 2, 0);
                t1.setId(R.id.my_text_1);

                TextView t3 = new TextView(this);
                t3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 0.10f));
                t3.setGravity(Gravity.CENTER);

                t3.setText("" + i);
                t3.setTextColor(Color.parseColor("#FF68358E"));
                t3.setHeight(120);
                t3.setWidth(30);
                t3.setPadding(0, 0, 1, 0);

                TextView t2 = new TextView(this);
                t2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 0.40f));

                t2.setText(classSectionId.get(c1).getName() + " - " + classSectionId.get(c1).getSubjectName());
                t2.setTextColor(Color.parseColor("#FF68358E"));
                t2.setHeight(120);
                t2.setWidth(200);
                t2.setPadding(20, 0, 2, 0);
                t2.setId(R.id.my_text_2);

                EditText b = new EditText(this);
                b.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 0.20f));
                b.setGravity(Gravity.CENTER);

                String Marks = "";
                Marks = classSectionId.get(c1).getTotalMarks();
                b.setText(Marks);
                b.setId(R.id.my_edit_text_1);
                b.requestFocusFromTouch();
                b.setTextSize(13.0f);
                b.setTypeface(null, Typeface.BOLD);
                b.setKeyListener(DigitsKeyListener.getInstance("0123456789AB"));
                b.setInputType(InputType.TYPE_CLASS_TEXT);
                b.setAllCaps(true);
                b.setSingleLine(true);
                b.setTextColor(Color.parseColor("#FF68358E"));
                b.setPressed(true);
                b.setHeight(120);
                b.setWidth(100);
                b.setPadding(1, 0, 2, 0);

                cell.addView(t1);
                cell.addView(t3);
                cell.addView(t2);
                cell.addView(b);

                layout_all.addView(cell);
                i++;
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG)
                    .show();
            e.printStackTrace();
        }
    }

    private void GetStudentsList(String classSectionId) {

        try {

            TableLayout layout = new TableLayout(this);
            layout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            layout_all.setScrollbarFadingEnabled(false);
            layout.setPadding(0, 50, 0, 50);

            TableRow.LayoutParams cellLp = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            cellLp.setMargins(2, 2, 2, 2);
            int i = 1;
            Cursor c = db.getStudentsOfClassBasedOnClassId(classSectionId);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        for (int c1 = 0; c1 <= 0; c1++) {
                            LinearLayout cell = new LinearLayout(this);
                            cell.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                            cell.setOrientation(LinearLayout.HORIZONTAL);
                            cell.setPadding(20, 5, 20, 5);
                            cell.setBackgroundColor(Color.parseColor("#FFFFFF"));

                            TextView t1 = new TextView(this);
                            t1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.10f));
                            t1.setGravity(Gravity.CENTER);
                            t1.setVisibility(View.GONE);
                            t1.setText(c.getString(1));
                            t1.setTextColor(Color.parseColor("#FF68358E"));
                            t1.setHeight(120);
                            t1.setWidth(100);
                            t1.setPadding(1, 0, 2, 0);
                            t1.setId(R.id.my_text_1);

                            TextView t3 = new TextView(this);
                            t3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.10f));
                            t3.setGravity(Gravity.CENTER);

                            t3.setText("" + i);
                            t3.setTextColor(Color.parseColor("#FF68358E"));
                            t3.setHeight(120);
                            t3.setWidth(30);
                            t3.setPadding(0, 0, 1, 0);

                            TextView t2 = new TextView(this);
                            t2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.40f));

                            t2.setText(c.getString(4) + " - " + c.getString(6));
                            t2.setTextColor(Color.parseColor("#FF68358E"));
                            t2.setHeight(120);
                            t2.setWidth(200);
                            t2.setPadding(20, 0, 2, 0);
                            t2.setId(R.id.my_text_2);

                            EditText b = new EditText(this);
                            b.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.20f));
                            b.setGravity(Gravity.CENTER);

                            String name = "";

                            b.setText(name);
                            b.setId(R.id.my_edit_text_1);
                            b.requestFocusFromTouch();
                            b.setTextSize(13.0f);
                            b.setTypeface(null, Typeface.BOLD);
                            b.setKeyListener(DigitsKeyListener.getInstance("0123456789AB"));
                            b.setInputType(InputType.TYPE_CLASS_TEXT);
                            b.setAllCaps(true);
                            b.setSingleLine(true);
                            b.setTextColor(Color.parseColor("#FF68358E"));
                            b.setPressed(true);
                            b.setHeight(120);
                            b.setWidth(100);
                            b.setPadding(1, 0, 2, 0);

                            cell.addView(t1);
                            cell.addView(t3);
                            cell.addView(t2);
                            cell.addView(b);

                            layout_all.addView(cell);
                        }
                        i++;
                    } while (c.moveToNext());
                }
            }
            db.close();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG)
                    .show();
            e.printStackTrace();
        }
    }

    private void GetAcademicExamInfo(String examIdLocal) {
        try {
            Cursor c = db.getAcademicExamInfo(examIdLocal);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        getExamId = c.getString(1);
                        examName = c.getString(2);
                        getClassMasterId = c.getString(4);
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

    @Override
    public void onClick(View v) {
        try {
            SaveStudentsAcademicExamMarks();
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "Try again...", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateFields() {
        int getCount = 0;
        getCount = layout_all.getChildCount();
        EditText edtMarks;
        TextView et, et1;
        int count = 0;
        int validMark ;
//        if (Pagetype.equalsIgnoreCase("edit")) {
            validMark = Integer.parseInt(db.totalMark(classIdSend, examsIdSend, classSubjectId));
//        } else {
//            validMark = Integer.parseInt(db.totalMark(classId, examsId, classSubjectId));
//        }
        int nViews = layout_all.getChildCount();

        for (int i = 0; i < nViews; i++) {

            View view = layout_all.getChildAt(i);

            edtMarks = (EditText) view.findViewById(R.id.my_edit_text_1);

            et1 = (TextView) view.findViewById(R.id.my_text_2);

            String Marks = edtMarks.getText().toString().trim();

            if (!AppValidator.checkNullString(edtMarks.getText().toString().trim())) {
                AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid marks for student - " + String.valueOf(et1.getText()));
            } else if ((AppValidator.checkEditTextValid100AndA(Marks, validMark)).equalsIgnoreCase("NotValidMark") || (AppValidator.checkEditTextValid100AndA(Marks, validMark)).equalsIgnoreCase("NotValidAbsent")) {
                if (((AppValidator.checkEditTextValid100AndA(Marks, validMark)).equalsIgnoreCase("NotValidMark"))) {
                    AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid marks for student - " + String.valueOf(et1.getText()) + " between 0 to " + validMark);
                }
                if (((AppValidator.checkEditTextValid100AndA(Marks, validMark)).equalsIgnoreCase("NotValidAbsent"))) {
                    AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid leave character as 'AB' for student - " + String.valueOf(et1.getText()));
                }
            } else {
                count++;
            }
        }

        if (getCount == count) {
            return true;
        } else {
            return false;
        }
    }

    private void SaveStudentsAcademicExamMarks() {

        TextView et, et1;
        EditText edtMarks;
        if (validateFields()) {
            int nViews = layout_all.getChildCount();

            for (int i = 0; i < nViews; i++) {

                View view = layout_all.getChildAt(i);


                et = (TextView) view.findViewById(R.id.my_text_1);
//                et1 = (TextView) view.findViewById(R.id.my_text_2);
                edtMarks = (EditText) view.findViewById(R.id.my_edit_text_1);

                if (et != null) {
                    String enrollId = String.valueOf(et.getText());
//                    String studentName = String.valueOf(et1.getText());
                    String Marks = edtMarks.getText().toString();

                    if (Marks.isEmpty()) {
                        Marks = "0";
                    }

                    examId = getExamId;
                    teacherId = PreferenceStorage.getTeacherId(this);
                    subjectId = PreferenceStorage.getTeacherSubject(this);
                    studentId = enrollId;
                    classMasterId = getClassMasterId;
                    String internalMark = "0";
                    String internalGrade = "AB";
                    externalMark = "0";
                    externalGrade = "AB";
                    totalMarks = Marks;
                    totalGrade = "AB";
                    createdBy = PreferenceStorage.getUserId(this);
                    createdAt = formattedServerDate;
                    updatedBy = PreferenceStorage.getUserId(this);
                    updatedAt = formattedServerDate;
                    syncStatus = "NS";

                    long c = db.academic_exam_marks_insert(examId, teacherId, subjectId, studentId, classMasterId, internalMark,
                            internalGrade, externalMark, externalGrade, totalMarks, totalGrade, createdBy, createdAt,
                            updatedBy, updatedAt, syncStatus);
                    if (c == -1) {
                        Toast.makeText(getApplicationContext(), "Error while marks add...", Toast.LENGTH_LONG).show();
                    }
                    Log.v("ypgs", String.valueOf(et.getText()));
                    if (Pagetype != null && Pagetype.equalsIgnoreCase("edit")) {
//                        updateStudentsAcademicExamMarks();
                        resString = "sendMark";
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_EXAM_ID, examsIdSend);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_TEACHER_ID, teacherId);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_SUBJECT_ID, subjectId);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_STUDENT_ID, studentId);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_CLASS_MASTER_ID, classIdSend);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_INTERNAL_MARK, internalMark);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_EXTERNAL_MARK, externalMark);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_TOTAL_MARK, totalMarks);
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_INTERNAL_EXTERNAL_MARK_STATUS, "0");
                            jsonObject.put(EnsyfiConstants.PARAMS_ACADEMIC_EXAM_MARKS_CREATED_BY, createdBy);
                            jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
                        String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.EDIT_ACADEMIC_EXAM_MARK_API;
                        serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
                    }
                }
            }
//            db.updateAcademicExamMarksStatus(getExamId, getClassMasterId);
            db.academic_exam_subject_marks_status_insert(examId, teacherId, subjectId);
            finish();
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
//                Log.d(TAG, "status val" + status + "msg" + msg);

                if ((status != null)) {
                    if (((status.equalsIgnoreCase("activationError")) || (status.equalsIgnoreCase("alreadyRegistered")) ||
                            (status.equalsIgnoreCase("notRegistered")) || (status.equalsIgnoreCase("error")))) {
                        signInsuccess = false;
//                        Log.d(TAG, "Show error dialog");
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
        if (validateSignInResponse(response)) {
            if (resString.equalsIgnoreCase("markData")) {
                Gson gson = new Gson();
                ExamResultList examResultList = gson.fromJson(response.toString(), ExamResultList.class);
                if (examResultList.getExamResult() != null && examResultList.getExamResult().size() > 0) {
//                    updateListAdapter(examResultList.getExamResult());
                    examResultArrayList = examResultList.getExamResult();
                    loadStudentsList(examResultArrayList);
                }
            }
//            Toast.makeText(this, "Marks updated!", Toast.LENGTH_SHORT).show();
//            finish();
        }
    }

    @Override
    public void onError(String error) {

    }
}
