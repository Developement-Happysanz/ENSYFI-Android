package com.palprotech.ensyfi.activity.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.studentmodule.AttendanceActivity;
import com.palprotech.ensyfi.activity.studentmodule.ClassTestHomeworkActivity;
import com.palprotech.ensyfi.activity.studentmodule.ExamsResultActivity;
import com.palprotech.ensyfi.activity.studentmodule.FeeStatusActivity;
import com.palprotech.ensyfi.bean.admin.viewlist.ClassStudent;
import com.palprotech.ensyfi.bean.student.support.SaveStudentData;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 18-07-2017.
 */

public class ClassStudentDetailsActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, View.OnClickListener {

    private static final String TAG = ClassStudentDetailsActivity.class.getName();
    private ClassStudent classStudent;
    private Button btnClassTestHomework, btnExams, btnFees, btnAttendance;
    ImageView btnBack, studentImg;
    private SaveStudentData studentData;
    private ServiceHelper serviceHelper;
    private ProgressDialogHelper progressDialogHelper;

    private TextView studentAdmissionId, studentAdmissionYear, studentAdmissionNumber, studentEmsiNumber, studentAdmissionDate,
            studentName, studentGender, studentDateOfBirth, studentAge, studentNationality, studentReligion, studentCaste,
            studentCommunity, studentParentOrGuardian, studentParentOrGuardianId, studentMotherTongue, studentLanguage,
            studentMobile, studentSecondaryMobile, studentMail, studentSecondaryMail, studentPreviousSchool,
            studentPreviousClass, studentPromotionStatus, studentTransferCertificate, studentRecordSheet, studentStatus,
            studentParentStatus, studentRegistered;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_student_details);

        classStudent = (ClassStudent) getIntent().getSerializableExtra("eventObj");

        btnClassTestHomework = (Button) findViewById(R.id.btnClassTestHomework);
        btnClassTestHomework.setOnClickListener(this);

        btnExams = (Button) findViewById(R.id.btnExams);
        btnExams.setOnClickListener(this);

        btnFees = (Button) findViewById(R.id.btnFees);
        btnFees.setOnClickListener(this);

        btnAttendance = (Button) findViewById(R.id.btnAttendance);
        btnAttendance.setOnClickListener(this);

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        studentData = new SaveStudentData(this);
        progressDialogHelper = new ProgressDialogHelper(this);

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

        populateData();
    }

    private void populateData() {

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_STUDENT_ID_SHOW, classStudent.getEnrollId());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_STUDENT_INFO;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {
            try {
                JSONArray getStudentData = response.getJSONArray("studentData");

                studentData.saveStudentProfile(getStudentData);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            getStudentInfo();

        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(this, error);
    }

    private boolean validateSignInResponse(JSONObject response) {
        boolean signInsuccess = false;
        if ((response != null)) {
            try {
                String status = response.getString("status");
                String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                Log.d(TAG, "status val" + status + "msg" + msg);

                if ((status != null)) {
                    if (((status.equalsIgnoreCase("activationError")) || (status.equalsIgnoreCase("alreadyRegistered")) ||
                            (status.equalsIgnoreCase("notRegistered")) || (status.equalsIgnoreCase("error")))) {
                        signInsuccess = false;
                        Log.d(TAG, "Show error dialog");
                        AlertDialogHelper.showSimpleAlertDialog(this, msg);

                    } else {
                        signInsuccess = true;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return signInsuccess;
    }

    @Override
    public void onClick(View v) {

        if (v == btnBack) {
            finish();
        }
        if (v == btnClassTestHomework) {

            String ClassId = classStudent.getClassId();
            // Student Preference - ClassId
            if ((ClassId != null) && !(ClassId.isEmpty()) && !ClassId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentClassIdPreference(getApplicationContext(), ClassId);
            }

            String StudentId = classStudent.getEnrollId();
            // Student Preference - EnrollId
            if ((StudentId != null) && !(StudentId.isEmpty()) && !StudentId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentRegisteredIdPreference(getApplicationContext(), StudentId);
            }
            Intent intent = new Intent(this, ClassTestHomeworkActivity.class);
            startActivity(intent);
        }
        if (v == btnExams) {

            String ClassId = classStudent.getClassId();
            // Student Preference - ClassId
            if ((ClassId != null) && !(ClassId.isEmpty()) && !ClassId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentClassIdPreference(getApplicationContext(), ClassId);
            }

            String StudentId = classStudent.getEnrollId();
            // Student Preference - EnrollId
            if ((StudentId != null) && !(StudentId.isEmpty()) && !StudentId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentRegisteredIdPreference(getApplicationContext(), StudentId);
            }

            Intent intent = new Intent(this, ExamsResultActivity.class);
            startActivity(intent);
        }
        if (v == btnFees) {

            String StudentId = classStudent.getEnrollId();
            // Student Preference - EnrollId
            if ((StudentId != null) && !(StudentId.isEmpty()) && !StudentId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentRegisteredIdPreference(getApplicationContext(), StudentId);
            }

            Intent intent = new Intent(getApplicationContext(), FeeStatusActivity.class);
            startActivity(intent);
        }
        if (v == btnAttendance) {

            String ClassId = classStudent.getClassId();
            // Student Preference - ClassId
            if ((ClassId != null) && !(ClassId.isEmpty()) && !ClassId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentClassIdPreference(getApplicationContext(), ClassId);
            }

            String StudentId = classStudent.getEnrollId();
            // Student Preference - EnrollId
            if ((StudentId != null) && !(StudentId.isEmpty()) && !StudentId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentRegisteredIdPreference(getApplicationContext(), StudentId);
            }
            Intent intent = new Intent(this, AttendanceActivity.class);
            startActivity(intent);
        }
    }

    private void getStudentInfo() {
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
        if (PreferenceStorage.getStudentPromotionStatus(getApplicationContext()).equalsIgnoreCase("1")) {
            studentPromotionStatus.setText("Yes");
        } else {
            studentPromotionStatus.setText("No");
        }
        if (PreferenceStorage.getStudentTransferCertificate(getApplicationContext()).equalsIgnoreCase("1")) {
            studentTransferCertificate.setText("Yes");
        } else {
            studentTransferCertificate.setText("No");
        }
        if (PreferenceStorage.getStudentRecordSheet(getApplicationContext()).equalsIgnoreCase("1")) {
            studentRecordSheet.setText("Yes");
        } else {
            studentRecordSheet.setText("No");
        }
        if (PreferenceStorage.getStudentStatus(getApplicationContext()).equalsIgnoreCase("1")) {
            studentStatus.setText("Active");
        } else {
            studentStatus.setText("Deactivated");
        }
        if (PreferenceStorage.getStudentParentStatus(getApplicationContext()).equalsIgnoreCase("1")) {
            studentParentStatus.setText("Active");
        } else {
            studentParentStatus.setText("Deactivated");
        }
        studentRegistered.setText(PreferenceStorage.getStudentRegistered(getApplicationContext()));
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}
