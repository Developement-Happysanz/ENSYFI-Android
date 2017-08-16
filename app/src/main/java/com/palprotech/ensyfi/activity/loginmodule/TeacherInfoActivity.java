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

public class TeacherInfoActivity extends AppCompatActivity implements DialogClickListener, View.OnClickListener {

    private ImageView teacherImg, btnBack;

    private TextView teacherId, teacherName, teacherGender, teacherAge, teacherNationality, teacherReligion, teacherCaste,
            teacherCommunity, teacherAddress, teacherSubject, classTeacher, teacherMobile, teacherSecondaryMobile, teacherMail,
            teacherSecondaryMail, teacherSectionName, teacherClassName, teacherClassTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile_info);
        SetUI();
        callTeacherInfoPreferences();
    }

    private void SetUI() {

        btnBack = (ImageView) findViewById(R.id.back_res);
        btnBack.setOnClickListener(this);

        teacherImg = (ImageView) findViewById(R.id.img_teacher_profile);

        // Teacher's Info view
        teacherId = (TextView) findViewById(R.id.txtTeacherid);
        teacherName = (TextView) findViewById(R.id.txtTeacherName);
        teacherGender = (TextView) findViewById(R.id.txtTeacherGender);
        teacherAge = (TextView) findViewById(R.id.txtTeacherAge);
        teacherNationality = (TextView) findViewById(R.id.txtTeacherNationality);
        teacherReligion = (TextView) findViewById(R.id.txtTeacherReligion);
        teacherCaste = (TextView) findViewById(R.id.txtTeacherCaste);
        teacherCommunity = (TextView) findViewById(R.id.txtTeacherCommunity);
        teacherAddress = (TextView) findViewById(R.id.txtTeacherAddress);
        teacherSubject = (TextView) findViewById(R.id.txtTeacherSubject);
        classTeacher = (TextView) findViewById(R.id.txtClassTeacher);
        teacherMobile = (TextView) findViewById(R.id.txtTeacherMobile);
        teacherSecondaryMobile = (TextView) findViewById(R.id.txtTeacherSecondaryMobile);
        teacherMail = (TextView) findViewById(R.id.txtTeacherMail);
        teacherSecondaryMail = (TextView) findViewById(R.id.txtTeacherSecondaryMail);
        teacherSectionName = (TextView) findViewById(R.id.txtTeacherSectionName);
        teacherClassName = (TextView) findViewById(R.id.txtTeacherClassName);
        teacherClassTaken = (TextView) findViewById(R.id.txtTeacherClassTaken);
    }

    private void callTeacherInfoPreferences() {

        teacherId.setText(PreferenceStorage.getTeacherId(getApplicationContext()));
        teacherName.setText(PreferenceStorage.getTeacherName(getApplicationContext()));
        teacherGender.setText(PreferenceStorage.getTeacherGender(getApplicationContext()));
        teacherAge.setText(PreferenceStorage.getTeacherAge(getApplicationContext()));
        teacherNationality.setText(PreferenceStorage.getTeacherNationality(getApplicationContext()));
        teacherReligion.setText(PreferenceStorage.getTeacherReligion(getApplicationContext()));
        teacherCaste.setText(PreferenceStorage.getTeacherCaste(getApplicationContext()));
        teacherCommunity.setText(PreferenceStorage.getTeacherCommunity(getApplicationContext()));
        teacherAddress.setText(PreferenceStorage.getTeacherAddress(getApplicationContext()));
        teacherSubject.setText(PreferenceStorage.getTeacherSubjectName(getApplicationContext()));
        classTeacher.setText(PreferenceStorage.getClassTeacher(getApplicationContext()));
        teacherMobile.setText(PreferenceStorage.getTeacherMobile(getApplicationContext()));
        teacherSecondaryMobile.setText(PreferenceStorage.getTeacherSecondaryMobile(getApplicationContext()));
        teacherMail.setText(PreferenceStorage.getTeacherMail(getApplicationContext()));
        teacherSecondaryMail.setText(PreferenceStorage.getTeacherSecondaryMail(getApplicationContext()));
        teacherSectionName.setText(PreferenceStorage.getTeacherSectionName(getApplicationContext()));
        teacherClassName.setText(PreferenceStorage.getTeacherClassName(getApplicationContext()));
        teacherClassTaken.setText(PreferenceStorage.getTeacherClassTaken(getApplicationContext()));
        String url = PreferenceStorage.getTeacherPic(this);

        if (((url != null) && !(url.isEmpty()))) {
            Picasso.with(this).load(url).placeholder(R.drawable.profile_pic).error(R.drawable.profile_pic).into(teacherImg);
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
