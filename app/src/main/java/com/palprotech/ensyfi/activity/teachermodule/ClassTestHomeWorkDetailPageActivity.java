package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.teachermodule.sample.SampleAddClassTestMarkActivity;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;

/**
 * Created by Admin on 13-07-2017.
 */

public class ClassTestHomeWorkDetailPageActivity extends AppCompatActivity implements View.OnClickListener {

    SQLiteHelper db;
    String homeWorkId, serverHomeWorkId, yearId, classId, teacherId, homeWorkType, subjectId, subjectName, title,
            testDate, dueDate, homeWorkDetails, status, markStatus, createdBy, createdAt, updatedBy, updatedAt, syncStatus;
    TextView txtSubjectName, clsTitle, txtHomeWorkDtails, txtTestDate, txtDueDate, txtPageTitle, txtDueDateTitle, txtTestDateTitle;
    ImageView addMarks, viewMarks;
    long id;
    String homeWorkIdLocal;
    int checkMarkStatus = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_test_homework_detail_page);

        Intent intent = getIntent();
        id = getIntent().getExtras().getLong("id");
        db = new SQLiteHelper(getApplicationContext());
        homeWorkIdLocal = String.valueOf(id);

        txtPageTitle = (TextView) findViewById(R.id.tvtitletext);
        txtSubjectName = (TextView) findViewById(R.id.txthomeworksubject);
        clsTitle = (TextView) findViewById(R.id.clstitle);
        txtHomeWorkDtails = (TextView) findViewById(R.id.txthomeworkdetails);
        txtTestDate = (TextView) findViewById(R.id.txtTestDate);
        txtDueDate = (TextView) findViewById(R.id.txtDueDate);
        txtDueDateTitle = (TextView) findViewById(R.id.txtDueDateTitle);
        txtTestDateTitle = (TextView) findViewById(R.id.txtTestDateTitle);

        addMarks = (ImageView) findViewById(R.id.addMarks);
        addMarks.setOnClickListener(this);
        viewMarks = (ImageView) findViewById(R.id.viewMarks);
        viewMarks.setOnClickListener(this);

        GetHomeWorkClassTestDetails(homeWorkIdLocal);
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetHomeWorkClassTestDetails(String homeWorkId) {

        try {
            Cursor c = db.getClassTestHomeWorkDetails(homeWorkId);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        homeWorkId = c.getString(0);
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

            if (homeWorkType.equalsIgnoreCase("HT")) {
                txtPageTitle.setText("Class Test");
                checkMarkStatus = Integer.parseInt(markStatus);
                txtTestDateTitle.setText("Test Date : ");
                ((LinearLayout) findViewById(R.id.dueDateLayout)).setVisibility(View.GONE);
                ((TextView) findViewById(R.id.underlineDueDate)).setVisibility(View.GONE);
                if (checkMarkStatus == 0) {
                    addMarks.setVisibility(View.VISIBLE);
                    viewMarks.setVisibility(View.GONE);
                } else {
                    addMarks.setVisibility(View.GONE);
                    viewMarks.setVisibility(View.VISIBLE);
                }

            } else {
                txtPageTitle.setText("Homework");
                addMarks.setVisibility(View.GONE);
                viewMarks.setVisibility(View.GONE);
                txtDueDateTitle.setText("Due Date : ");
                ((LinearLayout) findViewById(R.id.testDateLayout)).setVisibility(View.GONE);
                ((TextView) findViewById(R.id.underlineTestDate)).setVisibility(View.GONE);
            }
            txtSubjectName.setText(subjectName);
            clsTitle.setText(title);
            txtHomeWorkDtails.setText(homeWorkDetails);
            txtTestDate.setText(testDate);
            txtDueDate.setText(dueDate);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == addMarks) {
            Intent intent = new Intent(getApplicationContext(), SampleAddClassTestMarkActivity.class);
            intent.putExtra("hw_id", id);
            startActivity(intent);
        }

        if (v == viewMarks) {
            Intent intent = new Intent(getApplicationContext(), ViewClassTestMarkActivity.class);
            long ids = Long.parseLong(serverHomeWorkId);
            intent.putExtra("hw_id", ids);
            startActivity(intent);
        }
    }
}
