package com.palprotech.ensyfi.activity.loginmodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.utils.PreferenceStorage;
import com.squareup.picasso.Picasso;

/**
 * Created by Narendar on 05/04/17.
 */

public class ProfileActivity extends AppCompatActivity implements DialogClickListener {

    private ImageView mProfileImage = null;
    private TextView txtUsrName, txtUserType;
    private EditText txtUsrID, txtMail, txtPassword, numPhone, txtAddress;
    private ImageView ParentProfile, GuardianProfile, StudentProfile, motherInfo, fatherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //new LoadProfile().execute();
        SetUI();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void SetUI() {

        mProfileImage = (ImageView) findViewById(R.id.image_profile_pic);
        txtUsrID = (EditText) findViewById(R.id.userid);
        txtUsrID.setEnabled(false);
        txtPassword = (EditText) findViewById(R.id.txtpassword);
        txtPassword.setEnabled(false);
        txtUsrName = (TextView) findViewById(R.id.name);
        txtUserType = (TextView) findViewById(R.id.typename);
        txtUsrID.setText(PreferenceStorage.getUserName(getApplicationContext()));
//        txtMail.setText(PreferenceStorage.getEmail(getApplicationContext()));
//        txtPassword.setText(PreferenceStorage.get(getApplicationContext()));
//        txtAddress.setText(PreferenceStorage.getAddress(getApplicationContext()));
//        numPhone.setText(PreferenceStorage.getHomePhone(getApplicationContext()));
        txtUsrName.setText(PreferenceStorage.getName(getApplicationContext()));
        txtUserType.setText(PreferenceStorage.getUserTypeName(getApplicationContext()));
        fatherInfo = (ImageView) findViewById(R.id.img_father_profile);
        motherInfo = (ImageView) findViewById(R.id.img_mother_profile);
        ParentProfile = (ImageView) findViewById(R.id.ic_parentprofile);
        GuardianProfile = (ImageView) findViewById(R.id.ic_guardianprofile);
        StudentProfile = (ImageView) findViewById(R.id.ic_studentprofile);

        final RelativeLayout parentinfo = (RelativeLayout) findViewById(R.id.popup_parent);

        String url = PreferenceStorage.getUserPicture(this);
        if ((url == null) || (url.isEmpty())) {
           /* if ((loginMode == 1) || (loginMode == 3)) {
                url = PreferenceStorage.getSocialNetworkProfileUrl(this);
            } */
        }
        if (((url != null) && !(url.isEmpty()))) {
            Picasso.with(this).load(url).placeholder(R.drawable.profile_pic).error(R.drawable.profile_pic).into(mProfileImage);
        }


        final TextView fatherName = (TextView) findViewById(R.id.txtfathername);
        final TextView fatherAddress = (TextView) findViewById(R.id.txtfatheraddress);
        final TextView fatherMail = (TextView) findViewById(R.id.txtfathermail);
        final TextView fatherOccupation = (TextView) findViewById(R.id.txtfatheroccupation);
        final TextView fatherIncome = (TextView) findViewById(R.id.txtincome);
        final TextView fatherMobile = (TextView) findViewById(R.id.txtfathermobile);
        final TextView fatherOfficePhone = (TextView) findViewById(R.id.txtfatherofficephone);
        final TextView fatherHomePhone = (TextView) findViewById(R.id.txtfatherhomephone);
        final Button btnCancel = (Button) findViewById(R.id.cancel);

        fatherName.setText(PreferenceStorage.getFatherName(getApplicationContext()));
        fatherAddress.setText(PreferenceStorage.getFatherAddress(getApplicationContext()));
        fatherMail.setText(PreferenceStorage.getFatherEmail(getApplicationContext()));
        fatherOccupation.setText(PreferenceStorage.getFatherOccupation(getApplicationContext()));
        fatherIncome.setText(PreferenceStorage.getFatherID(getApplicationContext()));
        fatherMobile.setText(PreferenceStorage.getFatherMobile(getApplicationContext()));
        fatherOfficePhone.setText(PreferenceStorage.getFatherOfficePhone(getApplicationContext()));
        fatherHomePhone.setText(PreferenceStorage.getFatherHomePhone(getApplicationContext()));
        ParentProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentinfo.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentinfo.setVisibility(View.INVISIBLE);
                btnCancel.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}

