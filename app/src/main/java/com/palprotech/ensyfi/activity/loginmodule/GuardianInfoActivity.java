package com.palprotech.ensyfi.activity.loginmodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.utils.PreferenceStorage;
import com.squareup.picasso.Picasso;

/**
 * Created by Narendar on 19/07/17.
 */

public class GuardianInfoActivity extends AppCompatActivity implements DialogClickListener, View.OnClickListener {

    private TextView GName, GAddress, GMail, GOccupation, GIncome, GMobile, GOfficePhone, GHomePhone;
    private ImageView guardianImg;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_profile_info);
        SetUI();
        callGuardianInfoPreferences();
    }

    private void SetUI() {

        btnBack = (ImageView) findViewById(R.id.back_res);
        btnBack.setOnClickListener(this);

        guardianImg = (ImageView) findViewById(R.id.img_guardian_profile);
        GName = (TextView) findViewById(R.id.txtmothername);
        GAddress = (TextView) findViewById(R.id.txtmotheraddress);
        GMail = (TextView) findViewById(R.id.txtmothermail);
        GOccupation = (TextView) findViewById(R.id.txtmotheroccupation);
        GIncome = (TextView) findViewById(R.id.txtmotherincome);
        GMobile = (TextView) findViewById(R.id.txtmothermobile);
        GOfficePhone = (TextView) findViewById(R.id.txtmotherofficephone);
        GHomePhone = (TextView) findViewById(R.id.txtmotherhomephone);

    }

    private void callGuardianInfoPreferences() {

        GName.setText("");
        GAddress.setText("");
        GMail.setText("");
        GOccupation.setText("");
        GIncome.setText("");
        GMobile.setText("");
        GOfficePhone.setText("");
        GHomePhone.setText("");

        GName.setText(PreferenceStorage.getGuardianName(getApplicationContext()));
        GAddress.setText(PreferenceStorage.getGuardianAddress(getApplicationContext()));
        GMail.setText(PreferenceStorage.getGuardianEmail(getApplicationContext()));
        GOccupation.setText(PreferenceStorage.getGuardianOccupation(getApplicationContext()));
        GIncome.setText(PreferenceStorage.getGuardianIncome(getApplicationContext()));
        GMobile.setText(PreferenceStorage.getGuardianMobile(getApplicationContext()));
        GOfficePhone.setText(PreferenceStorage.getGuardianOfficePhone(getApplicationContext()));
        GHomePhone.setText(PreferenceStorage.getGuardianHomePhone(getApplicationContext()));

        String url = PreferenceStorage.getGuardianImg(this);

        if (((url != null) && !(url.isEmpty()))) {
            Picasso.get().load(url).placeholder(R.drawable.profile_pic).error(R.drawable.profile_pic).into(guardianImg);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnBack) {
            finish();
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}
