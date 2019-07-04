package com.palprotech.ensyfi.activity.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.general.EventsActivity;

public class CircularMasterListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_master_list);

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        ImageView addCircular = (ImageView) findViewById(R.id.add_circular);

        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addCircular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigationIntent = new Intent(getApplicationContext(), CircularMasterCreationActivity.class);
                navigationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(navigationIntent);
            }
        });
    }
}
