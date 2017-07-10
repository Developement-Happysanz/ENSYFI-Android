package com.palprotech.ensyfi.activity.loginmodule;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.studentmodule.FeeStatusActivity;
import com.palprotech.ensyfi.bean.student.support.SaveStudentData;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Narendar on 05/04/17.
 */

public class ProfileActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, View.OnClickListener {

    private static final String TAG = ProfileActivity.class.getName();
    private ImageView mProfileImage = null, btnBack;
    private TextView txtUsrName, txtUserType, txtPassword;

    private TextView Name, Address, Mail, Occupation, Income, Mobile, OfficePhone, HomePhone;

    private TextView GName, GAddress, GMail, GOccupation, GIncome, GMobile, GOfficePhone, GHomePhone;
    private TextView teacherId, teacherName, teacherGender, teacherAge, teacherNationality, teacherReligion, teacherCaste,
            teacherCommunity, teacherAddress, teacherSubject, classTeacher, teacherMobile, teacherSecondaryMobile, teacherMail,
            teacherSecondaryMail, teacherSectionName, teacherClassName, teacherClassTaken;

    private TextView studentAdmissionId, studentAdmissionYear, studentAdmissionNumber, studentEmsiNumber, studentAdmissionDate,
            studentName, studentGender, studentDateOfBirth, studentAge, studentNationality, studentReligion, studentCaste,
            studentCommunity, studentParentOrGuardian, studentParentOrGuardianId, studentMotherTongue, studentLanguage,
            studentMobile, studentSecondaryMobile, studentMail, studentSecondaryMail, studentPreviousSchool,
            studentPreviousClass, studentPromotionStatus, studentTransferCertificate, studentRecordSheet, studentStatus,
            studentParentStatus, studentRegistered;

    private TextView ParentProfile, GuardianProfile, StudentProfile, FeeStatusView, TeacherProfile;

    private ServiceHelper serviceHelper;
    private EditText txtUsrID;
    private ImageView fatherInfo, motherInfo;
    private Uri outputFileUri;
    static final int REQUEST_IMAGE_GET = 1;
    protected ProgressDialogHelper progressDialogHelper;
    private SaveStudentData studentData;
    RelativeLayout TeacherInfo, parentInfoPopup, guardianInfoPopup, studentInfoPopup, teacherInfoPopup;
    LinearLayout ParentInfo;
    Button btnCancel, GbtnCancel, tbtnCancel, SbtnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SetUI();
    }

    private void SetUI() {

        findViewById();

        String url = PreferenceStorage.getUserPicture(this);

        if (((url != null) && !(url.isEmpty()))) {
            Picasso.with(this).load(url).placeholder(R.drawable.profile_pic).error(R.drawable.profile_pic).into(mProfileImage);
        }

        callFatherInfoPreferences();

        String userTypeString = PreferenceStorage.getUserType(getApplicationContext());
        int userType = Integer.parseInt(userTypeString);
        if (userType == 2) {
            TeacherInfo.setVisibility(View.VISIBLE);
            ParentInfo.setVisibility(View.GONE);
        } else if (userType == 3) {
            ParentInfo.setVisibility(View.VISIBLE);
        } else {
            ParentInfo.setVisibility(View.VISIBLE);
            FeeStatusView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        if (v == btnBack) {
            finish();
        }
        if (v == mProfileImage) {
//            openImageIntent();
        }
        if (v == txtPassword) {
            AlertDialogHelper.showCompoundAlertDialog(ProfileActivity.this, "Change Password", "Password will be Reset. Do you still wish to continue...", "OK", "CANCEL", 1);
        }
        if (v == fatherInfo) {
            callFatherInfoPreferences();
        }
        if (v == motherInfo) {
            callMotherInfoPreferences();
        }
        if (v == FeeStatusView) {
            Intent intent = new Intent(getApplicationContext(), FeeStatusActivity.class);
            startActivity(intent);
        }
        if (v == ParentProfile) {
            parentInfoPopup.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
        }
        if (v == btnCancel) {
            parentInfoPopup.setVisibility(View.INVISIBLE);
            btnCancel.setVisibility(View.INVISIBLE);
        }
        if (v == GuardianProfile) {
            callGuardianInfoPreferences();
        }
        if (v == GbtnCancel) {
            guardianInfoPopup.setVisibility(View.INVISIBLE);
            GbtnCancel.setVisibility(View.INVISIBLE);
        }
        if (v == StudentProfile) {
            studentInfoPopup.setVisibility(View.VISIBLE);
            SbtnCancel.setVisibility(View.VISIBLE);

            String userTypeString = PreferenceStorage.getUserType(getApplicationContext());
            int userType = Integer.parseInt(userTypeString);

            if (userType == 3) {
                callStudentInfoPreferences();
            } else {
                callGetStudentInfoService();
                callStudentInfoPreferences();
            }
        }
        if (v == SbtnCancel) {
            studentInfoPopup.setVisibility(View.INVISIBLE);
            SbtnCancel.setVisibility(View.INVISIBLE);
        }
        if (v == TeacherProfile) {
            callTeacherInfoPreferences();
        }
        if (v == tbtnCancel) {
            teacherInfoPopup.setVisibility(View.INVISIBLE);
            tbtnCancel.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {
            try {

                JSONArray getData = response.getJSONArray("studentProfile");
                studentData.saveStudentProfile(getData);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(this, error);
    }

    private void callGetStudentInfoService() {

        if (CommonUtils.isNetworkAvailable(this)) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.STUDENT_ADMISSION_ID, PreferenceStorage.getStudentAdmissionIdPreference(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(this) + EnsyfiConstants.GET_STUDENT_INFO_DETAILS_API;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
        } else {

            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }

    }

    private boolean validateSignInResponse(JSONObject response) {
        boolean signInSuccess = false;
        if ((response != null)) {
            try {
                String status = response.getString("status");
                String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                Log.d(TAG, "status val" + status + "msg" + msg);

                if ((status != null)) {
                    if (((status.equalsIgnoreCase("activationError")) || (status.equalsIgnoreCase("alreadyRegistered")) ||
                            (status.equalsIgnoreCase("notRegistered")) || (status.equalsIgnoreCase("error")))) {
                        signInSuccess = false;
                        Log.d(TAG, "Show error dialog");
                        AlertDialogHelper.showSimpleAlertDialog(this, msg);

                    } else {
                        signInSuccess = true;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return signInSuccess;
    }

    private void callFatherInfoPreferences() {
        Name.setText(PreferenceStorage.getFatherName(getApplicationContext()));
        Address.setText(PreferenceStorage.getFatherAddress(getApplicationContext()));
        Mail.setText(PreferenceStorage.getFatherEmail(getApplicationContext()));
        Occupation.setText(PreferenceStorage.getFatherOccupation(getApplicationContext()));
        Income.setText(PreferenceStorage.getFatherID(getApplicationContext()));
        Mobile.setText(PreferenceStorage.getFatherMobile(getApplicationContext()));
        OfficePhone.setText(PreferenceStorage.getFatherOfficePhone(getApplicationContext()));
        HomePhone.setText(PreferenceStorage.getFatherHomePhone(getApplicationContext()));
    }

    private void callMotherInfoPreferences() {
        Name.setText(PreferenceStorage.getMotherName(getApplicationContext()));
        Address.setText(PreferenceStorage.getMotherAddress(getApplicationContext()));
        Mail.setText(PreferenceStorage.getMotherEmail(getApplicationContext()));
        Occupation.setText(PreferenceStorage.getMotherOccupation(getApplicationContext()));
        Income.setText(PreferenceStorage.getMotherID(getApplicationContext()));
        Mobile.setText(PreferenceStorage.getMotherMobile(getApplicationContext()));
        OfficePhone.setText(PreferenceStorage.getMotherOfficePhone(getApplicationContext()));
        HomePhone.setText(PreferenceStorage.getMotherHomePhone(getApplicationContext()));
    }

    private void callGuardianInfoPreferences() {
        guardianInfoPopup.setVisibility(View.VISIBLE);
        GbtnCancel.setVisibility(View.VISIBLE);
        GName.setText(PreferenceStorage.getGuardianName(getApplicationContext()));
        GAddress.setText(PreferenceStorage.getGuardianAddress(getApplicationContext()));
        GMail.setText(PreferenceStorage.getGuardianEmail(getApplicationContext()));
        GOccupation.setText(PreferenceStorage.getGuardianOccupation(getApplicationContext()));
        GIncome.setText(PreferenceStorage.getGuardianID(getApplicationContext()));
        GMobile.setText(PreferenceStorage.getGuardianMobile(getApplicationContext()));
        GOfficePhone.setText(PreferenceStorage.getGuardianOfficePhone(getApplicationContext()));
        GHomePhone.setText(PreferenceStorage.getGuardianHomePhone(getApplicationContext()));
    }

    private void callTeacherInfoPreferences() {
        teacherInfoPopup.setVisibility(View.VISIBLE);
        tbtnCancel.setVisibility(View.VISIBLE);
        teacherId.setText(PreferenceStorage.getTeacherId(getApplicationContext()));
        teacherName.setText(PreferenceStorage.getTeacherName(getApplicationContext()));
        teacherGender.setText(PreferenceStorage.getTeacherGender(getApplicationContext()));
        teacherAge.setText(PreferenceStorage.getTeacherAge(getApplicationContext()));
        teacherNationality.setText(PreferenceStorage.getTeacherNationality(getApplicationContext()));
        teacherReligion.setText(PreferenceStorage.getTeacherReligion(getApplicationContext()));
        teacherCaste.setText(PreferenceStorage.getTeacherCaste(getApplicationContext()));
        teacherCommunity.setText(PreferenceStorage.getTeacherCommunity(getApplicationContext()));
        teacherAddress.setText(PreferenceStorage.getTeacherAddress(getApplicationContext()));
        teacherSubject.setText(PreferenceStorage.getTeacherSubject(getApplicationContext()));
        classTeacher.setText(PreferenceStorage.getClassTeacher(getApplicationContext()));
        teacherMobile.setText(PreferenceStorage.getTeacherMobile(getApplicationContext()));
        teacherSecondaryMobile.setText(PreferenceStorage.getTeacherSecondaryMobile(getApplicationContext()));
        teacherMail.setText(PreferenceStorage.getTeacherMail(getApplicationContext()));
        teacherSecondaryMail.setText(PreferenceStorage.getTeacherSecondaryMail(getApplicationContext()));
        teacherSectionName.setText(PreferenceStorage.getTeacherSectionName(getApplicationContext()));
        teacherClassName.setText(PreferenceStorage.getTeacherClassName(getApplicationContext()));
        teacherClassTaken.setText(PreferenceStorage.getTeacherClassTaken(getApplicationContext()));
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
    }

    private void findViewById() {
        mProfileImage = (ImageView) findViewById(R.id.image_profile_pic);
        mProfileImage.setOnClickListener(this);

        btnBack = (ImageView) findViewById(R.id.back_res);
        btnBack.setOnClickListener(this);

        txtUsrID = (EditText) findViewById(R.id.userid);
        txtUsrID.setEnabled(false);

        txtPassword = (TextView) findViewById(R.id.password);
        txtPassword.setOnClickListener(this);

        txtUsrName = (TextView) findViewById(R.id.name);
        txtUserType = (TextView) findViewById(R.id.typename);
        progressDialogHelper = new ProgressDialogHelper(this);
        txtUsrID.setText(PreferenceStorage.getUserName(getApplicationContext()));
        txtUsrName.setText(PreferenceStorage.getName(getApplicationContext()));
        txtUserType.setText(PreferenceStorage.getUserTypeName(getApplicationContext()));

        fatherInfo = (ImageView) findViewById(R.id.img_father_profile);
        fatherInfo.setOnClickListener(this);

        motherInfo = (ImageView) findViewById(R.id.img_mother_profile);
        motherInfo.setOnClickListener(this);

        ParentProfile = (TextView) findViewById(R.id.ic_parentprofile);
        ParentProfile.setOnClickListener(this);

        GuardianProfile = (TextView) findViewById(R.id.ic_guardianprofile);
        GuardianProfile.setOnClickListener(this);

        TeacherProfile = (TextView) findViewById(R.id.ic_teacherprofile);
        TeacherProfile.setOnClickListener(this);

        StudentProfile = (TextView) findViewById(R.id.ic_studentprofile);
        StudentProfile.setOnClickListener(this);

        FeeStatusView = (TextView) findViewById(R.id.ic_feestatus);
        FeeStatusView.setOnClickListener(this);

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        studentData = new SaveStudentData(this);

        ParentInfo = (LinearLayout) findViewById(R.id.parentStudentView);
        TeacherInfo = (RelativeLayout) findViewById(R.id.teacherprofile);

        parentInfoPopup = (RelativeLayout) findViewById(R.id.popup_parent);
        guardianInfoPopup = (RelativeLayout) findViewById(R.id.popup_guardian);
        studentInfoPopup = (RelativeLayout) findViewById(R.id.popup_student);
        teacherInfoPopup = (RelativeLayout) findViewById(R.id.popup_teacher);

        //////For Parent and Guardian///////
        Name = (TextView) findViewById(R.id.txtfathername);
        Address = (TextView) findViewById(R.id.txtfatheraddress);
        Mail = (TextView) findViewById(R.id.txtfathermail);
        Occupation = (TextView) findViewById(R.id.txtfatheroccupation);
        Income = (TextView) findViewById(R.id.txtincome);
        Mobile = (TextView) findViewById(R.id.txtfathermobile);
        OfficePhone = (TextView) findViewById(R.id.txtfatherofficephone);
        HomePhone = (TextView) findViewById(R.id.txtfatherhomephone);
        btnCancel = (Button) findViewById(R.id.cancel);
        btnCancel.setOnClickListener(this);

        //////For Parent and Guardian///////
        GName = (TextView) findViewById(R.id.txtmothername);
        GAddress = (TextView) findViewById(R.id.txtmotheraddress);
        GMail = (TextView) findViewById(R.id.txtmothermail);
        GOccupation = (TextView) findViewById(R.id.txtmotheroccupation);
        GIncome = (TextView) findViewById(R.id.txtmotherincome);
        GMobile = (TextView) findViewById(R.id.txtmothermobile);
        GOfficePhone = (TextView) findViewById(R.id.txtmotherofficephone);
        GHomePhone = (TextView) findViewById(R.id.txtmotherhomephone);
        GbtnCancel = (Button) findViewById(R.id.cancel1);
        GbtnCancel.setOnClickListener(this);

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
        tbtnCancel = (Button) findViewById(R.id.cancel0);
        tbtnCancel.setOnClickListener(this);

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
        SbtnCancel = (Button) findViewById(R.id.cancel2);
        SbtnCancel.setOnClickListener(this);
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}

