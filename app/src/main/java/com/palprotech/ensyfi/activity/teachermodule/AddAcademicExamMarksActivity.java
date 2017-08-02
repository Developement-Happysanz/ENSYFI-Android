package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.StudentsAcademicExamMarksAddBaseAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.viewlist.StudentsClassTestMarks;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.utils.AppValidator;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Admin on 15-07-2017.
 */

public class AddAcademicExamMarksActivity extends AppCompatActivity implements View.OnClickListener, DialogClickListener {

    long hwId;
    SQLiteHelper db;
    String examMarksId, examId, teacherId, subjectId, studentId, classMasterId, internalMark, internalGrade,
            externalMark, externalGrade, totalMarks, totalGrade, createdBy, createdAt, updatedBy, updatedAt, syncStatus;
    String getExamId, examName, getClassMasterId, sectionName, className, fromDate, toDate, markStatus;
    ListView lvStudent;
    ImageView btnSave;
    Calendar c = Calendar.getInstance();
    String localExamId, formattedServerDate, storeClassId;
    ArrayList<StudentsClassTestMarks> myList = new ArrayList<StudentsClassTestMarks>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_academic_exam_marks);
        Intent intent = getIntent();
        hwId = getIntent().getExtras().getLong("id");
        db = new SQLiteHelper(getApplicationContext());
        localExamId = String.valueOf(hwId);

        lvStudent = (ListView) findViewById(R.id.listView_students);

        btnSave = (ImageView) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        SimpleDateFormat serverDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedServerDate = serverDF.format(c.getTime());

        GetAcademicExamInfo(localExamId);

        GetStudentsList(getClassMasterId);

        StudentsAcademicExamMarksAddBaseAdapter cadapter = new StudentsAcademicExamMarksAddBaseAdapter(AddAcademicExamMarksActivity.this, myList);
        lvStudent.setAdapter(cadapter);

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
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    private void GetStudentsList(String classSectionId) {

        myList.clear();
        try {
            Cursor c = db.getStudentsOfClassBasedOnClassId(classSectionId);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        StudentsClassTestMarks lde = new StudentsClassTestMarks();
                        lde.setId(Integer.parseInt(c.getString(0)));
                        lde.setEnrollId(c.getString(1));
                        lde.setAdmissionId(c.getString(2));
                        lde.setClassId(c.getString(3));
                        storeClassId = c.getString(3);
                        lde.setStudentName(c.getString(4));
                        lde.setClassSection(c.getString(5));

                        // Add this object into the ArrayList myList
                        myList.add(lde);

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
                        getClassMasterId = c.getString(3);
                        sectionName = c.getString(4);
                        className = c.getString(5);
                        fromDate = c.getString(6);
                        toDate = c.getString(7);
                        markStatus = c.getString(8);
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
        }
    }

    private boolean validateFields() {
        int getCount = 0;
        getCount = lvStudent.getCount();

        EditText edtInternalMarks, edtExternalMarks;
        TextView et, et1;
        int count = 0;
        int validInternalMark = 40;
        int validExternalMark = 60;
        for (int i = 0; i < lvStudent.getCount(); i++) {
            edtInternalMarks = (EditText) lvStudent.getChildAt(i).findViewById(R.id.internal_marks);
            int internalMark = Integer.parseInt(edtInternalMarks.getText().toString());
            edtExternalMarks = (EditText) lvStudent.getChildAt(i).findViewById(R.id.external_marks);
            int externalMark = Integer.parseInt(edtExternalMarks.getText().toString());

//            et = (TextView) lvStudent.getChildAt(i).findViewById(R.id.txt_studentId);
            et1 = (TextView) lvStudent.getChildAt(i).findViewById(R.id.txt_studentName);

            if (!AppValidator.checkNullString(edtInternalMarks.getText().toString().trim())) {
                AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid internal marks for student - " + String.valueOf(et1.getText()));
//                return false;
            }
            if (!AppValidator.checkNullString(edtExternalMarks.getText().toString().trim())) {
                AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid external marks for student - " + String.valueOf(et1.getText()));
//                return false;
            }
//            if (internalMark <= 0 || internalMark >= validInternalMark + 1) {
//                AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid internal marks for student - " + String.valueOf(et1.getText()) + " between 0 to " + validInternalMark);
//
//            }
//            if (externalMark <= 0 || externalMark >= validExternalMark + 1) {
//                AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid external marks for student - " + String.valueOf(et1.getText()) + " between 0 to " + validExternalMark);
//            }
            else {
                count++;
//                return true;
            }
        }

        if (getCount == count) {
            return true;
        } else {
            return false;
        }
    }

    private void SaveStudentsAcademicExamMarks() {
        View view;
        ArrayList<String> mannschaftsnamen = new ArrayList<String>();
        TextView et, et1;
        EditText edtInternalMarks, edtExternalMarks;
        if (validateFields()) {
//            Toast.makeText(getApplicationContext(), "Error while marks add...",
//                    Toast.LENGTH_LONG).show();
            for (int i = 0; i < lvStudent.getCount(); i++) {
                et = (TextView) lvStudent.getChildAt(i).findViewById(R.id.txt_studentId);
                et1 = (TextView) lvStudent.getChildAt(i).findViewById(R.id.txt_studentName);
                edtInternalMarks = (EditText) lvStudent.getChildAt(i).findViewById(R.id.internal_marks);
                edtExternalMarks = (EditText) lvStudent.getChildAt(i).findViewById(R.id.external_marks);
                if (et != null) {
                    mannschaftsnamen.add(String.valueOf(et.getText()));
                    String enrollId = String.valueOf(et.getText());
                    String studentName = String.valueOf(et1.getText());
                    String internalMarks = edtInternalMarks.getText().toString();
                    String externalMarks = edtExternalMarks.getText().toString();
                    if (internalMarks.isEmpty()) {
                        internalMarks = "0";
                    }
                    if (externalMarks.isEmpty()) {
                        externalMarks = "0";
                    }

                    examId = getExamId;
                    teacherId = PreferenceStorage.getTeacherId(this);
                    subjectId = PreferenceStorage.getTeacherSubject(this);
                    studentId = enrollId;
                    classMasterId = getClassMasterId;
                    internalMark = internalMarks;
                    internalGrade = "A";
                    externalMark = externalMarks;
                    externalGrade = "A";
                    totalMarks = "0";
                    totalGrade = "A";
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
                    //** you can try to log your values EditText *//*
                    Log.v("ypgs", String.valueOf(et.getText()));
                }
            }
            db.updateAcademicExamMarksStatus(getExamId, getClassMasterId);
            finish();
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}
