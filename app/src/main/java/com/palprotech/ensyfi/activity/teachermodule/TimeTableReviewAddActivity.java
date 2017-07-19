package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.student.viewlist.MonthView;

/**
 * Created by Admin on 19-07-2017.
 */

public class TimeTableReviewAddActivity extends AppCompatActivity {

    String getTimeTableValue;
    TextView txtClassName, txtSubjectName, txtPeriodId;
    EditText edtTimetableReviewDetails;
    Button btnSubmit;
    String sClassName, sClassId, sSubjectName, sSubjectId, sPeriodId;
//    ClassName:X-A,ClassId:4,SubjectName:Tamil,SubjectId:1,PeriodId:2

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_review_add);
        Intent intent = getIntent();
        getTimeTableValue = intent.getStringExtra("timeTableValue");
        txtClassName = (TextView) findViewById(R.id.txtClassName);
        txtSubjectName = (TextView) findViewById(R.id.txtSubjectName);
        txtPeriodId = (TextView) findViewById(R.id.txtPeriodId);
        edtTimetableReviewDetails = (EditText) findViewById(R.id.edtTimetableReviewDetails);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        String[] animalsArray = getTimeTableValue.split(",");
        sClassName = animalsArray[0];
        txtClassName.setText(sClassName);
        sClassId = animalsArray[1];
        sSubjectName = animalsArray[2];
        txtSubjectName.setText(sSubjectName);
        sSubjectId = animalsArray[3];
        sPeriodId = animalsArray[4];
        txtPeriodId.setText(sPeriodId);

        String newOk = "";

    }
}
