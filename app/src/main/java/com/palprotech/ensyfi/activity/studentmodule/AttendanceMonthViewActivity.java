package com.palprotech.ensyfi.activity.studentmodule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.student.viewlist.ClassTest;
import com.palprotech.ensyfi.bean.student.viewlist.MonthView;

/**
 * Created by Admin on 12-07-2017.
 */

public class AttendanceMonthViewActivity extends AppCompatActivity {

    private MonthView monthView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        monthView = (MonthView) getIntent().getSerializableExtra("eventObj");
        String okNew = "";
    }
}
