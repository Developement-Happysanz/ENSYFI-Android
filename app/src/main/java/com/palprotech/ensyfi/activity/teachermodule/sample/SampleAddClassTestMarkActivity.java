package com.palprotech.ensyfi.activity.teachermodule.sample;

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
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.StudentListClassTestMarkBaseAdapter;
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
 * Created by Admin on 08-08-2017.
 */

public class SampleAddClassTestMarkActivity extends AppCompatActivity implements View.OnClickListener, DialogClickListener {

    long hwId;
    TextView txtTitle, txtTestDate;
    SQLiteHelper db;
    String serverHomeWorkId, yearId, classId, teacherId, homeWorkType, subjectId, subjectName, title,
            testDate, dueDate, homeWorkDetails, status, markStatus, createdBy, createdAt, updatedBy, updatedAt, syncStatus;
    ListView lvStudent;
    ArrayList<StudentsClassTestMarks> myList = new ArrayList<StudentsClassTestMarks>();
    ImageView btnSave;
    private String storeClassId;
    Calendar c = Calendar.getInstance();
    String homeWorkId, formattedServerDate;
    StudentListClassTestMarkBaseAdapter cadapter;
    LinearLayout layout_all;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class_test_mark_sample);

        Intent intent = getIntent();
        hwId = getIntent().getExtras().getLong("hw_id");
        db = new SQLiteHelper(getApplicationContext());
        homeWorkId = String.valueOf(hwId);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTestDate = (TextView) findViewById(R.id.txtTestDate);

        btnSave = (ImageView) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        layout_all = (LinearLayout) findViewById(R.id.layout_timetable);

        SimpleDateFormat serverDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedServerDate = serverDF.format(c.getTime());

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        GetHomeWorkClassTestDetails(homeWorkId);

        GetStudentsList(classId);

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

                       /* StudentsClassTestMarks lde = new StudentsClassTestMarks();
                        lde.setId(Integer.parseInt(c.getString(0)));
                        lde.setEnrollId(c.getString(1));
                        lde.setAdmissionId(c.getString(2));
                        lde.setClassId(c.getString(3));
                        storeClassId = c.getString(3);
                        lde.setStudentName(c.getString(4));
                        lde.setClassSection(c.getString(5));

                        // Add this object into the ArrayList myList
                        myList.add(lde);*/

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

                            TextView t2 = new TextView(this);
                            t2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.50f));

                            EditText b = new EditText(this);
                            b.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.30f));
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
                            b.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub

                                }
                            });
                            b.setPressed(true);
                            b.setHeight(120);
                            b.setWidth(50);
                            b.setPadding(1, 0, 2, 0);

                            t1.setText(c.getString(1));
                            t1.setTextColor(Color.parseColor("#FF68358E"));
                            t1.setHeight(120);
                            t1.setWidth(80);
                            t1.setPadding(1, 0, 2, 0);
                            t1.setId(R.id.my_text_1);

                            t2.setText(c.getString(4));
                            t2.setTextColor(Color.parseColor("#FF68358E"));
                            t2.setHeight(120);
                            t2.setWidth(120);
                            t2.setPadding(1, 0, 2, 0);
                            t2.setId(R.id.my_text_2);


                            cell.addView(t1);
                            cell.addView(t2);
                            cell.addView(b);

                            layout_all.addView(cell);
                        }
                        i++;
                    } while (c.moveToNext());
                }
            }

            db.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean validateFields() {
        int getCount = 0;
        getCount = layout_all.getChildCount();

        int count = 0;
        int validMark = 100;

        int position = 0;

        int nViews = layout_all.getChildCount();

        for (int i = 0; i < nViews; i++) {

            View view = layout_all.getChildAt(i);

            EditText ed_marks = (EditText) view.findViewById(R.id.my_edit_text_1);
            TextView tv_studentName = (TextView) view.findViewById(R.id.my_text_2);

            String getValue = ed_marks.getText().toString().trim();

            int newOk = 0;

            System.out.println(getValue);

            String Marks = ed_marks.getText().toString().trim();

            boolean check = Marks.matches("\\d+");

            if (!AppValidator.checkNullString(ed_marks.getText().toString().trim())) {
                AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid marks for student - " + String.valueOf(tv_studentName.getText()));
            }
            if (check) {
                int mark = Integer.parseInt(ed_marks.getText().toString());
                if (mark <= 0 || mark >= validMark + 1) {
                    AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid marks for student - " + String.valueOf(tv_studentName.getText()) + " between 0 to " + validMark);
                }
            }
            if (!check) {
                String charString = ed_marks.getText().toString();
                if (!charString.contentEquals("A")) {
                    AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid leave character as 'A' for student - " + String.valueOf(tv_studentName.getText()));
                }
            }
            else {
                count++;
            }
//            }
            position++;
        }

        if (getCount == count) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            SaveStudentsClasstestMarks();
        }
    }

    private void SaveStudentsClasstestMarks() {

        try {
            if (validateFields()) {

                int nViews = layout_all.getChildCount();

                for (int i = 0; i < nViews; i++) {

                    View view = layout_all.getChildAt(i);

                    EditText ed_marks = (EditText) view.findViewById(R.id.my_edit_text_1);
                    TextView tv_studentName = (TextView) view.findViewById(R.id.my_text_2);
                    TextView tv_studentId = (TextView) view.findViewById(R.id.my_text_1);

                    if (tv_studentId != null) {
                        String enrollId = String.valueOf(tv_studentId.getText().toString().trim());
                        String studentName = String.valueOf(tv_studentName.getText().toString().trim());
                        String marks = ed_marks.getText().toString().trim();
                        String remarks = "";
                        if (marks.isEmpty()) {
                            marks = "0";
                        }

                        long c = db.class_test_mark_insert(enrollId, homeWorkId, serverHomeWorkId, marks, remarks, "Active", PreferenceStorage.getUserId(getApplicationContext()), formattedServerDate, PreferenceStorage.getUserId(getApplicationContext()), formattedServerDate, "NS");
                        if (c == -1) {
                            Toast.makeText(getApplicationContext(), "Error while marks add...",
                                    Toast.LENGTH_LONG).show();
                        }
                        Log.v("ypgs", String.valueOf(tv_studentId.getText()));
                    }
                }

                db.updateClassTestHomeWorkMarkStatus(homeWorkId);
                Toast.makeText(getApplicationContext(), "Class Test - " + title + ".\n Marks Updated Successfully...",
                        Toast.LENGTH_LONG).show();

                finish();

            } else {
                Toast.makeText(getApplicationContext(), "Marks not updated...", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private void GetHomeWorkClassTestDetails(String homeWorkId) {

        try {
            Cursor c = db.getClassTestHomeWorkDetails(homeWorkId);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        String homeWorkIdLocal = c.getString(0);
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


                    } while (c.moveToNext());
                }
            } else {
                Toast.makeText(getApplicationContext(), "No records", Toast.LENGTH_LONG).show();
            }

            db.close();

            txtTitle.setText(title);
            txtTestDate.setText(testDate);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}
