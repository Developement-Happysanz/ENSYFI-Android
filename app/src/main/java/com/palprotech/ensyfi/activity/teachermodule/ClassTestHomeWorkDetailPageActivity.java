package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.palprotech.ensyfi.R;

/**
 * Created by Admin on 13-07-2017.
 */

public class ClassTestHomeWorkDetailPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_test_homework_detail_page);
        Intent intent = getIntent();
        long id = getIntent().getExtras().getLong("id");
        TextView txtId = (TextView) findViewById(R.id.txtId);
        txtId.setText("" + id);
        String newOk = "";
    }
}
