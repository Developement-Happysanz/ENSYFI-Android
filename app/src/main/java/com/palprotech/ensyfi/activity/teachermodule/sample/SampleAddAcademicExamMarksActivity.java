package com.palprotech.ensyfi.activity.teachermodule.sample;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.teachermodule.AddAcademicExamMarksActivity;
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
 * Created by Admin on 11-08-2017.
 */

public class SampleAddAcademicExamMarksActivity extends AppCompatActivity implements View.OnClickListener, DialogClickListener {

    long hwId;
    SQLiteHelper db;
    String examMarksId, examId, teacherId, subjectId, studentId, classMasterId, internalMark, internalGrade,
            externalMark, externalGrade, totalMarks, totalGrade, createdBy, createdAt, updatedBy, updatedAt, syncStatus;
    String getExamId, examName, getClassMasterId, sectionName, className, fromDate, toDate, markStatus;
    //    ListView lvStudent;
    ImageView btnSave;
    Calendar c = Calendar.getInstance();
    String localExamId, formattedServerDate, storeClassId;
    LinearLayout layout_all;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_academic_exam_marks_sample);

        hwId = getIntent().getExtras().getLong("id");
        db = new SQLiteHelper(getApplicationContext());
        localExamId = String.valueOf(hwId);
        layout_all = (LinearLayout) findViewById(R.id.layout_timetable);
        btnSave = (ImageView) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        SimpleDateFormat serverDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedServerDate = serverDF.format(c.getTime());
        GetAcademicExamInfo(localExamId);
        GetStudentsList(getClassMasterId);

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

        try {

            TableLayout layout = new TableLayout(this);
            layout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            layout_all.setScrollbarFadingEnabled(false);
            layout.setPadding(0, 50, 0, 50);

            TableLayout.LayoutParams rowLp = new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            TableRow.LayoutParams cellLp = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            cellLp.setMargins(2, 2, 2, 2);

            Cursor c = db.getStudentsOfClassBasedOnClassId(classSectionId);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    int i = 1;
                    do {

                        for (int c1 = 0; c1 <= 0; c1++) {

                            LinearLayout cell = new LinearLayout(this);
                            cell.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
//                            cell.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            cell.setOrientation(LinearLayout.HORIZONTAL);
                            cell.setPadding(20, 5, 20, 5);
                            cell.setBackgroundColor(Color.parseColor("#FFFFFF"));


                            TextView t1 = new TextView(this);
                            t1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.10f));
                            t1.setGravity(Gravity.CENTER);

                            t1.setText(c.getString(1));
                            t1.setTextColor(Color.parseColor("#FF68358E"));
                            t1.setHeight(120);
                            t1.setWidth(100);
                            t1.setPadding(1, 0, 2, 0);
                            t1.setId(R.id.my_text_1);

                            TextView t2 = new TextView(this);
                            t2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.40f));

                            t2.setText(c.getString(4));
                            t2.setTextColor(Color.parseColor("#FF68358E"));
                            t2.setHeight(120);
                            t2.setWidth(100);
                            t2.setPadding(1, 0, 2, 0);
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
                            b.setKeyListener(DigitsKeyListener.getInstance("0123456789A"));
                            b.setInputType(InputType.TYPE_CLASS_TEXT);
                            b.setAllCaps(true);
                            b.setSingleLine(true);
                            b.setTextColor(Color.parseColor("#FF68358E"));
                            b.setPressed(true);
                            b.setHeight(120);
                            b.setWidth(100);
                            b.setPadding(1, 0, 2, 0);

                            EditText b1 = new EditText(this);
                            b1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.20f));
                            b1.setGravity(Gravity.CENTER);

                            String name1 = "";

                            b1.setText(name1);
                            b1.setId(R.id.my_edit_text_2);
                            b1.requestFocusFromTouch();
                            b1.setTextSize(13.0f);
                            b1.setTypeface(null, Typeface.BOLD);
                            b1.setKeyListener(DigitsKeyListener.getInstance("0123456789A"));
                            b1.setInputType(InputType.TYPE_CLASS_TEXT);
                            b1.setAllCaps(true);
                            b1.setSingleLine(true);
                            b1.setTextColor(Color.parseColor("#FF68358E"));
                            b1.setPressed(true);
                            b1.setHeight(120);
                            b1.setWidth(100);
                            b1.setPadding(1, 0, 2, 0);

                            cell.addView(t1);
                            cell.addView(t2);
                            cell.addView(b);
                            cell.addView(b1);

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

    public static boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    public static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            SaveStudentsAcademicExamMarks();
        }
    }

    private boolean validateFields() {
        int getCount = 0;
        getCount = layout_all.getChildCount();


        EditText edtInternalMarks, edtExternalMarks;
        TextView et, et1;
        int count = 0;
        int validInternalMark = 40;
        int validExternalMark = 60;


        int nViews = layout_all.getChildCount();

        for (int i = 0; i < nViews; i++) {

            View view = layout_all.getChildAt(i);

            edtInternalMarks = (EditText) view.findViewById(R.id.my_edit_text_1);
//            int internalMark = Integer.parseInt(edtInternalMarks.getText().toString());
            edtExternalMarks = (EditText) view.findViewById(R.id.my_edit_text_2);
//            int externalMark = Integer.parseInt(edtExternalMarks.getText().toString());
            et1 = (TextView) view.findViewById(R.id.my_text_2);

//            isInteger(edtInternalMarks.getText().toString().trim());

            String InternalMarks = edtInternalMarks.getText().toString().trim();

            boolean checkInternalMarks = InternalMarks.matches("\\d+");

            String ExternalMarks = edtExternalMarks.getText().toString().trim();

            boolean checkExternalMarks = ExternalMarks.matches("\\d+");

            if (!AppValidator.checkNullString(edtInternalMarks.getText().toString().trim())) {
                AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid internal marks for student - " + String.valueOf(et1.getText()));
            }
            if (checkInternalMarks) {
                int mark = Integer.parseInt(edtInternalMarks.getText().toString());
                if (mark <= -1 || mark >= validInternalMark + 1) {
                    AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid internal marks for student - " + String.valueOf(et1.getText()) + " between 0 to " + validInternalMark);
                }
            }
            if (!checkInternalMarks) {
                String charString = edtInternalMarks.getText().toString();
                if (!charString.contentEquals("A")) {
                    AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid leave character as 'A' for student - " + String.valueOf(et1.getText()) + " in internal mark");
                }
            }
            if (!AppValidator.checkNullString(edtExternalMarks.getText().toString().trim())) {
                AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid external marks for student - " + String.valueOf(et1.getText()));
            }
            if (checkExternalMarks) {
                int mark = Integer.parseInt(edtExternalMarks.getText().toString());
                if (mark <= -1 || mark >= validExternalMark + 1) {
                    AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid external marks for student - " + String.valueOf(et1.getText()) + " between 0 to " + validExternalMark);
                }
            }
            if (!checkExternalMarks) {
                String charString = edtExternalMarks.getText().toString();
                if (!charString.contentEquals("A")) {
                    AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid leave character as 'A' for student - " + String.valueOf(et1.getText()) + " in external mark");
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
        EditText edtInternalMarks, edtExternalMarks;
        try {
            if (validateFields()) {
                int nViews = layout_all.getChildCount();

                for (int i = 0; i < nViews; i++) {

                    View view = layout_all.getChildAt(i);


                    et = (TextView) view.findViewById(R.id.my_text_1);
                    et1 = (TextView) view.findViewById(R.id.my_text_2);
                    edtInternalMarks = (EditText) view.findViewById(R.id.my_edit_text_1);
                    edtExternalMarks = (EditText) view.findViewById(R.id.my_edit_text_2);
                    if (et != null) {
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
                        Log.v("ypgs", String.valueOf(et.getText()));
                    }
                }
//                db.updateAcademicExamMarksStatus(getExamId, getClassMasterId);
                db.academic_exam_subject_marks_status_insert(examId, teacherId, subjectId);
                finish();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "Try again...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}
