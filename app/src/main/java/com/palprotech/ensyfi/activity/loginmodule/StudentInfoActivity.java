package com.palprotech.ensyfi.activity.loginmodule;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.utils.PreferenceStorage;
import com.squareup.picasso.Picasso;

/**
 * Created by Narendar on 19/07/17.
 */

public class StudentInfoActivity extends AppCompatActivity implements DialogClickListener, View.OnClickListener {

    private ImageView btnBack;

    protected ProgressDialogHelper progressDialogHelper;

    private ImageView studentImg;

    private TextView studentAdmissionId, studentAdmissionYear, studentAdmissionNumber, studentEmsiNumber, studentAdmissionDate,
            studentName, studentGender, studentDateOfBirth, studentAge, studentNationality, studentReligion, studentCaste,
            studentCommunity, studentParentOrGuardian, studentParentOrGuardianId, studentMotherTongue, studentLanguage,
            studentMobile, studentSecondaryMobile, studentMail, studentSecondaryMail, studentPreviousSchool,
            studentPreviousClass, studentPromotionStatus, studentTransferCertificate, studentRecordSheet, studentStatus,
            studentParentStatus, studentRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_info);
        SetUI();
        callStudentInfoPreferences();
    }

    private void SetUI() {
        btnBack = (ImageView) findViewById(R.id.back_res);
        btnBack.setOnClickListener(this);

        studentImg = (ImageView) findViewById(R.id.img_student_profile);

        ////// For Student ///////
        studentAdmissionId = (TextView) findViewById(R.id.txtstudentadminid);
        studentAdmissionYear = (TextView) findViewById(R.id.txtstudentadminyear);
        studentAdmissionNumber = (TextView) findViewById(R.id.txtstudentadminnum);
        studentEmsiNumber = (TextView) findViewById(R.id.txtstudentemsinum);
        studentAdmissionDate = (TextView) findViewById(R.id.txtStudentAdmissionDate);
        studentName = (TextView) findViewById(R.id.txtStudentName);
        studentGender = (TextView) findViewById(R.id.txtStudentGender);
        studentDateOfBirth = (TextView) findViewById(R.id.txtStudentDateOfBirth);
        studentAge = (TextView) findViewById(R.id.txtStudentAge);
        studentNationality = (TextView) findViewById(R.id.txtStudentNationality);
        studentReligion = (TextView) findViewById(R.id.txtStudentReligion);
        studentCaste = (TextView) findViewById(R.id.txtStudentCaste);
        studentCommunity = (TextView) findViewById(R.id.txtStudentCommunity);
        studentParentOrGuardian = (TextView) findViewById(R.id.txtStudentParentOrGuardian);
        studentParentOrGuardianId = (TextView) findViewById(R.id.txtStudentParentOrGuardianId);
        studentMotherTongue = (TextView) findViewById(R.id.txtStudentMotherTongue);
        studentLanguage = (TextView) findViewById(R.id.txtStudentLanguage);
        studentMobile = (TextView) findViewById(R.id.txtStudentMobile);
        studentSecondaryMobile = (TextView) findViewById(R.id.txtStudentSecondaryMobile);
        studentMail = (TextView) findViewById(R.id.txtStudentMail);
        studentSecondaryMail = (TextView) findViewById(R.id.txtStudentSecondaryMail);
        studentPreviousSchool = (TextView) findViewById(R.id.txtStudentPreviousSchool);
        studentPreviousClass = (TextView) findViewById(R.id.txtStudentPreviousClass);
        studentPromotionStatus = (TextView) findViewById(R.id.txtStudentPromotionStatus);
        studentTransferCertificate = (TextView) findViewById(R.id.txtStudentTransferCertificate);
        studentRecordSheet = (TextView) findViewById(R.id.txtStudentRecordSheet);
        studentStatus = (TextView) findViewById(R.id.txtStudentStatus);
        studentParentStatus = (TextView) findViewById(R.id.txtStudentParentStatus);
        studentRegistered = (TextView) findViewById(R.id.txtStudentRegistered);

        progressDialogHelper = new ProgressDialogHelper(this);
    }

    private void callStudentInfoPreferences() {
        studentAdmissionId.setText(PreferenceStorage.getStudentAdmissionID(getApplicationContext()));
        studentAdmissionYear.setText(PreferenceStorage.getStudentAdmissionYear(getApplicationContext()));
        studentAdmissionNumber.setText(PreferenceStorage.getStudentAdmissionNumber(getApplicationContext()));
        studentEmsiNumber.setText(PreferenceStorage.getStudentEmsiNumber(getApplicationContext()));
        studentAdmissionDate.setText(PreferenceStorage.getStudentAdmissionDate(getApplicationContext()));
        studentName.setText(PreferenceStorage.getStudentName(getApplicationContext()));
        studentGender.setText(PreferenceStorage.getStudentGender(getApplicationContext()));
        studentDateOfBirth.setText(PreferenceStorage.getStudentDateOfBirth(getApplicationContext()));
        studentAge.setText(PreferenceStorage.getStudentAge(getApplicationContext()));
        studentNationality.setText(PreferenceStorage.getStudentNationality(getApplicationContext()));
        studentReligion.setText(PreferenceStorage.getStudentReligion(getApplicationContext()));
        studentCaste.setText(PreferenceStorage.getStudentCaste(getApplicationContext()));
        studentCommunity.setText(PreferenceStorage.getStudentCommunity(getApplicationContext()));
        studentParentOrGuardian.setText(PreferenceStorage.getStudentParentOrGuardian(getApplicationContext()));
        studentParentOrGuardianId.setText(PreferenceStorage.getStudentParentOrGuardianID(getApplicationContext()));
        studentMotherTongue.setText(PreferenceStorage.getStudentMotherTongue(getApplicationContext()));
        studentLanguage.setText(PreferenceStorage.getStudentLanguage(getApplicationContext()));
        studentMobile.setText(PreferenceStorage.getStudentMobile(getApplicationContext()));
        studentSecondaryMobile.setText(PreferenceStorage.getStudentSecondaryMobile(getApplicationContext()));
        studentMail.setText(PreferenceStorage.getStudentMail(getApplicationContext()));
        studentSecondaryMail.setText(PreferenceStorage.getStudentSecondaryMail(getApplicationContext()));
        studentPreviousSchool.setText(PreferenceStorage.getStudentPreviousSchool(getApplicationContext()));
        studentPreviousClass.setText(PreferenceStorage.getStudentPreviousClass(getApplicationContext()));
        studentPromotionStatus.setText(PreferenceStorage.getStudentPromotionStatus(getApplicationContext()));
        studentTransferCertificate.setText(PreferenceStorage.getStudentTransferCertificate(getApplicationContext()));
        studentRecordSheet.setText(PreferenceStorage.getStudentRecordSheet(getApplicationContext()));
        studentStatus.setText(PreferenceStorage.getStudentStatus(getApplicationContext()));
        studentParentStatus.setText(PreferenceStorage.getStudentParentStatus(getApplicationContext()));
        studentRegistered.setText(PreferenceStorage.getStudentRegistered(getApplicationContext()));
        String url = PreferenceStorage.getStudentImg(this);

        if (((url != null) && !(url.isEmpty()))) {
            Picasso.get().load(url).placeholder(R.drawable.profile_pic).error(R.drawable.profile_pic).into(studentImg);
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
