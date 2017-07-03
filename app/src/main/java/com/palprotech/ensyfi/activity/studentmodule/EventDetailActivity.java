package com.palprotech.ensyfi.activity.studentmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.student.Event;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;

import org.json.JSONObject;

/**
 * Created by Admin on 18-05-2017.
 */

public class EventDetailActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, View.OnClickListener {

    private Event event;
    private TextView txtEventName, txtEventDate, txtEventDetails;
    private Button btnevent;
    String eventoran = "0";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        event = (Event) getIntent().getSerializableExtra("eventObj");
        initializeViews();
        populateData();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializeViews() {
        txtEventName = (TextView) findViewById(R.id.eventname);
        txtEventDate = (TextView) findViewById(R.id.eventdate);
        txtEventDetails = (TextView) findViewById(R.id.eventdetail);
        btnevent = (Button) findViewById(R.id.eventorg);
        eventoran = event.getSub_event_status();
        if (eventoran.equalsIgnoreCase("1")) {
            btnevent.setVisibility(View.VISIBLE);
        }
        else {
            btnevent.setVisibility(View.GONE);
        }

        btnevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EventOrganiserActivity.class);
                intent.putExtra("eventId", event.getEvent_id());
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    private void populateData() {

        txtEventName.setText(event.getEvent_name());
        txtEventDate.setText(event.getEvent_date());
        txtEventDetails.setText(event.getEvent_details());
//        btnevent.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(EventDetailActivity.this, EventOrganiserActivity.class);
//                intent.putExtra("eventObj", event);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    @Override
    public void onClick(View v) {

    }
}
