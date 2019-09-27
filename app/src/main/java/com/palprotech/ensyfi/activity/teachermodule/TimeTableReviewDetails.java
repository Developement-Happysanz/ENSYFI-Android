package com.palprotech.ensyfi.activity.teachermodule;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.teacher.viewlist.TTReview;

/**
 * Created by Admin on 21-07-2017.
 */

public class TimeTableReviewDetails extends AppCompatActivity {

    private TTReview ttReview;
    private TextView txttime_date, txtday, txtclass_id, txtclass_name, txtsec_name, txtsubject_name, txtcomments, txtremarks, txtstatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_table_review_details);
        ttReview = (TTReview) getIntent().getSerializableExtra("eventObj");
        txttime_date = (TextView) findViewById(R.id.txttime_date);
        txtday = (TextView) findViewById(R.id.txtday);
        txtclass_id = (TextView) findViewById(R.id.txtclass_id);
        txtclass_name = (TextView) findViewById(R.id.txtclass_name);
        txtsec_name = (TextView) findViewById(R.id.txtsec_name);
        txtsubject_name = (TextView) findViewById(R.id.txtsubject_name);
        txtcomments = (TextView) findViewById(R.id.txtcomments);
        txtremarks = (TextView) findViewById(R.id.txtremarks);
        txtstatus = (TextView) findViewById(R.id.txtstatus);

        txttime_date.setText(ttReview.getTimeDate());
        txtday.setText(ttReview.getDay());
        txtclass_id.setText(ttReview.getClassId());
        txtclass_name.setText(ttReview.getClassName());
        txtsec_name.setText(ttReview.getSectionName());
        txtsubject_name.setText(ttReview.getSubjectName());
        txtcomments.setText(ttReview.getComments());
        txtremarks.setText(ttReview.getRemarks());
        txtstatus.setText(ttReview.getStatus());

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
