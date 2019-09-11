package com.palprotech.ensyfi.activity.general;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.general.viewlist.Event;
import com.palprotech.ensyfi.interfaces.DialogClickListener;

import java.util.Locale;

/**
 * Created by Admin on 18-05-2017.
 */

public class EventDetailActivity extends AppCompatActivity implements DialogClickListener {

    private Event event;
    private TextView txtEventName, txtEventDate, txtEventDetails;
    private Button btnevent;
    private RelativeLayout rlMapRoute;
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
        rlMapRoute = findViewById(R.id.rl_mapview);
        btnevent = (Button) findViewById(R.id.eventorg);
        eventoran = event.getSub_event_status();
        if (eventoran.equalsIgnoreCase("1")) {
            btnevent.setVisibility(View.VISIBLE);
        } else {
            btnevent.setVisibility(View.GONE);
        }

        btnevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EventOrganiserActivity.class);
                intent.putExtra("eventId", event.getEvent_id());
                startActivity(intent);
            }
        });

        rlMapRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 19.0760, 72.8777);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);*/
                /*Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
                startActivity(intent);*/
                //11.013488, 76.944050
//                String strUri = "http://maps.google.c?om/maps?q=loc:" + 11.013488 + "," + 76.944050 + " (" + "Label which you want" + ")";
//                String strUri = "http://maps.google.com/maps?q=loc:" + event.getLatitude() + "," + event.getLongitude() + " (" + event.getEvent_name() + ")";
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
//
//                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//
//                startActivity(intent);
            }
        });
    }

    private void populateData() {

        txtEventName.setText(event.getEvent_name());
        txtEventDate.setText(event.getEvent_date());
        txtEventDetails.setText(event.getEvent_details());
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}
