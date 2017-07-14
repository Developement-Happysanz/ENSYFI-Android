package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTestHomeWork;

/**
 * Created by Admin on 13-07-2017.
 */

public class ClassTestHomeWorkDetailPageActivity extends AppCompatActivity {

    SQLiteHelper db;
    String homeWorkId, serverHomeWorkId, yearId, classId, teacherId, homeWorkType, subjectId, subjectName, title, testDate, dueDate, homeWorkDetails, status, markStatus, createdBy, createdAt, updatedBy, updatedAt, syncStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_test_homework_detail_page);
        Intent intent = getIntent();
        long id = getIntent().getExtras().getLong("id");
//        TextView txtId = (TextView) findViewById(R.id.txtId);
        db = new SQLiteHelper(getApplicationContext());
//        txtId.setText("" + id);
        String homeWorkId = String.valueOf(id);
        GetHomeWorkClassTestDetails(homeWorkId);
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

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
