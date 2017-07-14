package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.StudentListClassTestMarkBaseAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.viewlist.StudentsClassTestMarks;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Admin on 14-07-2017.
 */

public class AddClassTestMarkActivity extends AppCompatActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class_test_mark);
        Intent intent = getIntent();
        hwId = getIntent().getExtras().getLong("hw_id");
        db = new SQLiteHelper(getApplicationContext());
        homeWorkId = String.valueOf(hwId);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTestDate = (TextView) findViewById(R.id.txtTestDate);

        lvStudent = (ListView) findViewById(R.id.listView_students);

        btnSave = (ImageView) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        SimpleDateFormat serverDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedServerDate = serverDF.format(c.getTime());

        GetHomeWorkClassTestDetails(homeWorkId);

        GetStudentsList(classId);

        StudentListClassTestMarkBaseAdapter cadapter = new StudentListClassTestMarkBaseAdapter(AddClassTestMarkActivity.this, myList);
        lvStudent.setAdapter(cadapter);
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
    public void onClick(View v) {
        SaveStudentsClasstestMarks();
    }

    private void SaveStudentsClasstestMarks() {
/** get all values of the EditText-Fields */
        View view;
        ArrayList<String> mannschaftsnamen = new ArrayList<String>();
        TextView et, et1;
        EditText edtMarks;
        for (int i = 0; i < lvStudent.getCount(); i++) {
            et = (TextView) lvStudent.getChildAt(i).findViewById(R.id.txt_studentId);
            et1 = (TextView) lvStudent.getChildAt(i).findViewById(R.id.txt_studentName);
            edtMarks = (EditText) lvStudent.getChildAt(i).findViewById(R.id.class_test_marks);
//            edtRemarks = (EditText) lvStudent.getChildAt(i).findViewById(R.id.class_test_marks_remarks);
            if (et != null) {
                mannschaftsnamen.add(String.valueOf(et.getText()));
                String enrollId = String.valueOf(et.getText());
                String studentName = String.valueOf(et1.getText());
                String marks = edtMarks.getText().toString();
                String remarks = "";
//                        = edtRemarks.getText().toString();
                if (marks.isEmpty()) {
                    marks = "0";
                }

                long c = db.class_test_mark_insert(enrollId, homeWorkId, "", marks, remarks, "Active", PreferenceStorage.getUserId(getApplicationContext()), formattedServerDate, PreferenceStorage.getUserId(getApplicationContext()), formattedServerDate, "NS");
                if (c == -1) {
                    Toast.makeText(getApplicationContext(), "Error while marks add...",
                            Toast.LENGTH_LONG).show();
                }
                /** you can try to log your values EditText */
                Log.v("ypgs", String.valueOf(et.getText()));
            }
        }
    }
}
