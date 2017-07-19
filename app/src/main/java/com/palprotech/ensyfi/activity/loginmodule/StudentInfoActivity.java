package com.palprotech.ensyfi.activity.loginmodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
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

/**
 * Created by Narendar on 19/07/17.
 */

public class StudentInfoActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, View.OnClickListener {

    private static final String TAG = StudentInfoActivity.class.getName();

    protected ProgressDialogHelper progressDialogHelper;

    private ServiceHelper serviceHelper;
    private SaveStudentData studentData;

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
        callGetStudentInfoService();
        callStudentInfoPreferences();

    }

    private void SetUI() {
        studentImg = (ImageView) findViewById(R.id.img_student_profile);

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        studentData = new SaveStudentData(this);

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
            Picasso.with(this).load(url).placeholder(R.drawable.profile_pic).error(R.drawable.profile_pic).into(studentImg);
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

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}
