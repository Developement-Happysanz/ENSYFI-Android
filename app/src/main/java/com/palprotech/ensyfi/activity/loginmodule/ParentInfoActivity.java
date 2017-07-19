package com.palprotech.ensyfi.activity.loginmodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.utils.PreferenceStorage;
import com.squareup.picasso.Picasso;

/**
 * Created by Narendar on 19/07/17.
 */

public class ParentInfoActivity extends AppCompatActivity implements DialogClickListener, View.OnClickListener {

    private static final String TAG = ProfileActivity.class.getName();
    private ImageView btnBack;

    private TextView Name, Address, Mail, Occupation, Income, Mobile, OfficePhone, HomePhone;


    private ServiceHelper serviceHelper;
    private EditText txtUsrID;
    ImageView fatherInfo, motherInfo;
    protected ProgressDialogHelper progressDialogHelper;
    LinearLayout ParentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile_info);
        SetUI();
    }

    private void SetUI() {

        findViewById();

        String url = PreferenceStorage.getUserPicture(this);

//        if (((url != null) && !(url.isEmpty()))) {
//            Picasso.with(this).load(url).placeholder(R.drawable.profile_pic).error(R.drawable.profile_pic).into(mProfileImage);
//        }

        callMotherInfoPreferences();
        callFatherInfoPreferences();
    }

    private void findViewById() {
//        mProfileImage = (ImageView) findViewById(R.id.image_profile_pic);
//        mProfileImage.setOnClickListener(this);

        btnBack = (ImageView) findViewById(R.id.back_res);
        btnBack.setOnClickListener(this);

        progressDialogHelper = new ProgressDialogHelper(this);
        fatherInfo = (ImageView) findViewById(R.id.img_father_profile);
        fatherInfo.setOnClickListener(this);

        motherInfo = (ImageView) findViewById(R.id.img_mother_profile);
        motherInfo.setOnClickListener(this);


        //////For Parent and Guardian///////
        Name = (TextView) findViewById(R.id.txtfathername);
        Address = (TextView) findViewById(R.id.txtfatheraddress);
        Mail = (TextView) findViewById(R.id.txtfathermail);
        Occupation = (TextView) findViewById(R.id.txtfatheroccupation);
        Income = (TextView) findViewById(R.id.txtincome);
        Mobile = (TextView) findViewById(R.id.txtfathermobile);
        OfficePhone = (TextView) findViewById(R.id.txtfatherofficephone);
        HomePhone = (TextView) findViewById(R.id.txtfatherhomephone);

    }

    private void callFatherInfoPreferences() {
        Name.setText(PreferenceStorage.getFatherName(getApplicationContext()));
        Address.setText(PreferenceStorage.getFatherAddress(getApplicationContext()));
        Mail.setText(PreferenceStorage.getFatherEmail(getApplicationContext()));
        Occupation.setText(PreferenceStorage.getFatherOccupation(getApplicationContext()));
        Income.setText(PreferenceStorage.getFatherIncome(getApplicationContext()));
        Mobile.setText(PreferenceStorage.getFatherMobile(getApplicationContext()));
        OfficePhone.setText(PreferenceStorage.getFatherOfficePhone(getApplicationContext()));
        HomePhone.setText(PreferenceStorage.getFatherHomePhone(getApplicationContext()));
        String url = PreferenceStorage.getFatherImg(this);

        if (((url != null) && !(url.isEmpty()))) {
            Picasso.with(this).load(url).placeholder(R.drawable.profile_pic).error(R.drawable.profile_pic).into(fatherInfo);
        }
    }

    private void callMotherInfoPreferences() {
        Name.setText(PreferenceStorage.getMotherName(getApplicationContext()));
        Address.setText(PreferenceStorage.getMotherAddress(getApplicationContext()));
        Mail.setText(PreferenceStorage.getMotherEmail(getApplicationContext()));
        Occupation.setText(PreferenceStorage.getMotherOccupation(getApplicationContext()));
        Income.setText(PreferenceStorage.getMotherIncome(getApplicationContext()));
        Mobile.setText(PreferenceStorage.getMotherMobile(getApplicationContext()));
        OfficePhone.setText(PreferenceStorage.getMotherOfficePhone(getApplicationContext()));
        HomePhone.setText(PreferenceStorage.getMotherHomePhone(getApplicationContext()));
        String url = PreferenceStorage.getMotherImg(this);

        if (((url != null) && !(url.isEmpty()))) {
            Picasso.with(this).load(url).placeholder(R.drawable.profile_pic).error(R.drawable.profile_pic).into(motherInfo);
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    @Override
    public void onClick(View v) {

        if (v == btnBack) {
            finish();
        }

        if (v == fatherInfo) {
            callFatherInfoPreferences();
        }

        if (v == motherInfo) {
            callMotherInfoPreferences();
        }

    }
}