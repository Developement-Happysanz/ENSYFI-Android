package com.palprotech.ensyfi.activity.loginmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.studentmodule.StudentInfoActivity;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.AppValidator;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Admin on 22-03-2017.
 */

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener, IServiceListener, DialogClickListener {

    private static final String TAG = UserLoginActivity.class.getName();

    private ServiceHelper serviceHelper;
    private ProgressDialogHelper progressDialogHelper;

    private EditText inputUsername, inputPassword;
    private Button btnLogin;
    private TextView txtInsName, txtForgotPassword;
    private ImageView mProfileImage = null;
    SQLiteHelper database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
//        mProfileImage = (ImageView) findViewById(R.id.image_institute_pic);
        SetUI();
    }

    private void SetUI() {

        inputUsername = (EditText) findViewById(R.id.inputUsername);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        mProfileImage = (ImageView) findViewById(R.id.image_institute_pic);
        txtInsName = (TextView) findViewById(R.id.txtInstituteName);
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        txtForgotPassword.setOnClickListener(this);
        txtInsName.setText(PreferenceStorage.getInstituteName(getApplicationContext()));
        database = new SQLiteHelper(getApplicationContext());
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);

        String url = PreferenceStorage.getInstituteLogoPicUrl(this);
        if ((url == null) || (url.isEmpty())) {

        }
        if (((url != null) && !(url.isEmpty()))) {
            Picasso.with(this).load(url).placeholder(R.drawable.profile_pic).error(R.drawable.profile_pic).into(mProfileImage);
        }
    }

    @Override
    public void onClick(View v) {

        if (CommonUtils.isNetworkAvailable(this)) {
            if (v == btnLogin) {
                if (validateFields()) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(EnsyfiConstants.PARAMS_USER_NAME, inputUsername.getText().toString());
                        jsonObject.put(EnsyfiConstants.PARAMS_PASSWORD, inputPassword.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
                    String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(this) + EnsyfiConstants.USER_LOGIN_API;
                    serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
                }
            }
            if (v == txtForgotPassword) {
                Intent intent = new Intent(this, ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }

        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private boolean validateFields() {
        if (!AppValidator.checkNullString(this.inputUsername.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(getApplicationContext(), this.getResources().getString(R.string.enter_user_name));
            return false;
        } else if (!AppValidator.checkNullString(this.inputPassword.getText().toString())) {
            AlertDialogHelper.showSimpleAlertDialog(getApplicationContext(), this.getResources().getString(R.string.enter_password));
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

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

    public static void longInfo(String str) {
        if (str.length() > 4000) {
            Log.d("Data From", str.substring(0, 4000));
            longInfo(str.substring(4000));
        } else
            Log.d("Data To", str);
        String New;
    }

    @Override
    public void onResponse(JSONObject response) {

        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {

//            String repo = response.toString();
//            longInfo(repo);

            try {
//                JSONArray getData = response.getJSONArray("userData");
                JSONObject userData = response.getJSONObject("userData");
                String user_id = null;

                int userType = Integer.parseInt(userData.getString("user_type"));

                if (userType == 1) {
                    saveUserData(userData);
                } else if (userType == 2) {
                    saveUserData(userData);
                } else if (userType == 3) {
                    saveUserData(userData);
                    JSONObject getStudentInfo = response.getJSONObject("studentProfile");
                    JSONObject getStudentProfile = getStudentInfo.getJSONObject("0");

                    //Student Details
                    String StudentAdmissionId = "";
                    String StudentAdmissionYear = "";
                    String StudentAdmissionNumber = "";
                    String StudentEmsiNumber = "";
                    String StudentAdmissionDate = "";
                    String StudentName = "";
                    String StudentGender = "";
                    String StudentDateOfBirth = "";
                    String StudentAge = "";
                    String StudentNationaity = "";
                    String StudentReligion = "";
                    String StudentCaste = "";
                    String StudentCommunity = "";
                    String StudentParentOrGuardian = "";
                    String StudentParentOrGuardianId= "";
                    String StudentMotherTongue = "";
                    String StudentLanguage = "";
                    String StudentMobile = "";
                    String StudentSecondaryMobile = "";
                    String StudentMail = "";
                    String StudentSecondaryMail = "";
                    String StudentPic = "";
                    String StudentPreviousSchool = "";
                    String StudentPreviousClass = "";
                    String StudentPromotionStatus = "";
                    String StudentTransferCertificate = "";
                    String StudentRecordSheet = "";
                    String StudentStatus = "";
                    String StudentParentStatus = "";
                    String StudentRegistered = "";


                    //Father Details
                    String FatherId = "";
                    String FatherName = "";
                    String FatherAddress = "";
                    String FatherMail = "";
                    String FatherOccupation = "";
                    String FatherIncome = "";
                    String FatherMobile = "";
                    String FatherHomePhone = "";
                    String FatherOfficePhone = "";
                    String FatherRelationship = "";
                    String FatherPic = "";

                    //Mother Details
                    String MotherId = "";
                    String MotherName = "";
                    String MotherAddress = "";
                    String MotherMail = "";
                    String MotherOccupation = "";
                    String MotherIncome = "";
                    String MotherMobile = "";
                    String MotherHomePhone = "";
                    String MotherOfficePhone = "";
                    String MotherRelationship = "";
                    String MotherPic = "";

                    //Guardian Details
                    String GuardianId = "";
                    String GuardianName = "";
                    String GuardianAddress = "";
                    String GuardianMail = "";
                    String GuardianOccupation = "";
                    String GuardianIncome = "";
                    String GuardianMobile = "";
                    String GuardianHomePhone = "";
                    String GuardianOfficePhone = "";
                    String GuardianRelationship = "";
                    String GuardianPic = "";


                    StudentAdmissionId = getStudentProfile.getString("admission_id");
                    StudentAdmissionYear = getStudentProfile.getString("admisn_year");
                    StudentAdmissionNumber = getStudentProfile.getString("admisn_no");
                    StudentEmsiNumber = getStudentProfile.getString("emsi_num");
                    StudentAdmissionDate = getStudentProfile.getString("admisn_date");
                    StudentName = getStudentProfile.getString("name");
                    StudentGender = getStudentProfile.getString("sex");
                    StudentDateOfBirth = getStudentProfile.getString("dob");
                    StudentAge = getStudentProfile.getString("age");
                    StudentNationaity = getStudentProfile.getString("nationality");
                    StudentReligion = getStudentProfile.getString("religion");
                    StudentCaste = getStudentProfile.getString("community_class");
                    StudentCommunity = getStudentProfile.getString("community");
                    StudentParentOrGuardian = getStudentProfile.getString("parnt_guardn");
                    StudentParentOrGuardianId= getStudentProfile.getString("parnt_guardn_id");
                    StudentMotherTongue = getStudentProfile.getString("mother_tongue");
                    StudentLanguage = getStudentProfile.getString("language");
                    StudentMobile = getStudentProfile.getString("mobile");
                    StudentSecondaryMobile = getStudentProfile.getString("sec_mobile");
                    StudentMail = getStudentProfile.getString("email");
                    StudentSecondaryMail = getStudentProfile.getString("sec_email");
                    StudentPic = getStudentProfile.getString("student_pic");
                    StudentPreviousSchool = getStudentProfile.getString("last_sch_name");
                    StudentPreviousClass = getStudentProfile.getString("last_studied");
                    StudentPromotionStatus = getStudentProfile.getString("qualified_promotion");
                    StudentTransferCertificate = getStudentProfile.getString("transfer_certificate");
                    StudentRecordSheet = getStudentProfile.getString("record_sheet");
                    StudentStatus = getStudentProfile.getString("status");
                    StudentParentStatus = getStudentProfile.getString("parents_status");
                    StudentRegistered = getStudentProfile.getString("enrollment");





                    JSONObject getParentData = response.getJSONObject("parentProfile");
                    JSONObject fatherData = getParentData.getJSONObject("fatherProfile");
                    JSONObject motherData = getParentData.getJSONObject("motherProfile");
                    JSONObject guardianData = getParentData.getJSONObject("guardianProfile");

                    ///////////     FATHER      //////////

                    FatherId = fatherData.getString("id");
                    FatherName = fatherData.getString("name");
                    FatherHomePhone = fatherData.getString("home_phone");
                    FatherMail = fatherData.getString("email");
                    FatherAddress = fatherData.getString("home_address");
                    FatherOccupation = fatherData.getString("occupation");
                    FatherIncome = fatherData.getString("income");
                    FatherMobile = fatherData.getString("mobile");
                    FatherOfficePhone = fatherData.getString("office_phone");
                    FatherRelationship = fatherData.getString("relationship");
                    FatherPic = fatherData.getString("user_pic");


                    // Parents Preference - Father's Id
                    if ((FatherId != null) && !(FatherId.isEmpty()) && !FatherId.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherID(this, FatherId);
                    }

                    // Parents Preference - Father's Name
                    if ((FatherName != null) && !(FatherName.isEmpty()) && !FatherName.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherName(this, FatherName);
                    }

                    // Parents Preference - Father's Mail
                    if ((FatherMail != null) && !(FatherMail.isEmpty()) && !FatherMail.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherEmail(this, FatherMail);
                    }

                    // Parents Preference - Address
                    if ((FatherAddress != null) && !(FatherAddress.isEmpty()) && !FatherAddress.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherAddress(this, FatherAddress);
                    }

                    // Parents Preference - Father's Occupation
                    if ((FatherOccupation != null) && !(FatherOccupation.isEmpty()) && !FatherOccupation.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherOccupation(this, FatherOccupation);
                    }

                    // Parents Preference - Father's Income
                    if ((FatherIncome != null) && !(FatherIncome.isEmpty()) && !FatherIncome.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherIncome(this, FatherIncome);
                    }

                    // Parents Preference - Father's Home Phone
                    if ((FatherHomePhone != null) && !(FatherHomePhone.isEmpty()) && !FatherHomePhone.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherHomePhone(this, FatherHomePhone);
                    }

                    // Parents Preference - Father's Mobile
                    if ((FatherMobile != null) && !(FatherMobile.isEmpty()) && !FatherMobile.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherMobile(this, FatherMobile);
                    }

                    // Parents Preference - Father's Office Phone
                    if ((FatherOfficePhone != null) && !(FatherOfficePhone.isEmpty()) && !FatherOfficePhone.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherOfficePhone(this, FatherOfficePhone);
                    }

                    // Parents Preference - Father's Relationship
                    if ((FatherRelationship!= null) && !(FatherRelationship.isEmpty()) && !FatherRelationship.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherRelationship(this, FatherRelationship);
                    }

                    // Parents Preference - Father's Pic
                    if ((FatherPic!= null) && !(FatherPic.isEmpty()) && !FatherPic.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherImg(this, FatherPic);
                    }

                    ///////////     MOTHER      //////////

                    MotherId = motherData.getString("id");
                    MotherName = motherData.getString("name");
                    MotherHomePhone = motherData.getString("home_phone");
                    MotherMail = motherData.getString("email");
                    MotherAddress = motherData.getString("home_address");
                    MotherOccupation = motherData.getString("occupation");
                    MotherIncome = motherData.getString("income");
                    MotherMobile = motherData.getString("mobile");
                    MotherOfficePhone = motherData.getString("office_phone");
                    MotherRelationship = motherData.getString("relationship");
                    MotherPic = motherData.getString("user_pic");


                    // Parents Preference - Mother's Id
                    if ((MotherId != null) && !(MotherId.isEmpty()) && !MotherId.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherID(this, MotherId);
                    }

                    // Parents Preference - Mother's Name
                    if ((MotherName != null) && !(MotherName.isEmpty()) && !MotherName.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherName(this, MotherName);
                    }

                    // Parents Preference - Mother's Phone
                    if ((MotherHomePhone != null) && !(MotherHomePhone.isEmpty()) && !MotherHomePhone.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherHomePhone(this, MotherHomePhone);
                    }

                    // Parents Preference - Mother's Mail
                    if ((MotherMail != null) && !(MotherMail.isEmpty()) && !MotherMail.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherEmail(this, MotherMail);
                    }

                    // Parents Preference - Address
                    if ((MotherAddress != null) && !(MotherAddress.isEmpty()) && !MotherAddress.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherAddress(this, MotherAddress);
                    }

                    // Parents Preference - Mother's Occupation
                    if ((MotherOccupation != null) && !(MotherOccupation.isEmpty()) && !MotherOccupation.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherOccupation(this, MotherOccupation);
                    }

                    // Parents Preference - Mother's Income
                    if ((MotherIncome != null) && !(MotherIncome.isEmpty()) && !MotherIncome.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherIncome(this, MotherIncome);
                    }

                    // Parents Preference - Mother's Home Phone
                    if ((MotherHomePhone != null) && !(MotherHomePhone.isEmpty()) && !MotherHomePhone.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherHomePhone(this, MotherHomePhone);
                    }

                    // Parents Preference - Mother's Mobile
                    if ((MotherMobile != null) && !(MotherMobile.isEmpty()) && !MotherMobile.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherMobile(this, MotherMobile);
                    }

                    // Parents Preference - Mother's Office Phone
                    if ((MotherOfficePhone != null) && !(MotherOfficePhone.isEmpty()) && !MotherOfficePhone.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherOfficePhone(this, MotherOfficePhone);
                    }

                    // Parents Preference - Mother's Relationship
                    if ((MotherRelationship!= null) && !(MotherRelationship.isEmpty()) && !MotherRelationship.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherRelationship(this, MotherRelationship);
                    }

                    // Parents Preference - Mother's Pic
                    if ((MotherPic!= null) && !(MotherPic.isEmpty()) && !MotherPic.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherImg(this, MotherPic);
                    }

                    ///////////     GUARDIAN      //////////

                    GuardianId = guardianData.getString("id");
                    GuardianName = guardianData.getString("name");
                    GuardianHomePhone = guardianData.getString("home_phone");
                    GuardianMail = guardianData.getString("email");
                    GuardianAddress = guardianData.getString("home_address");
                    GuardianOccupation = guardianData.getString("occupation");
                    GuardianIncome = guardianData.getString("income");
                    GuardianMobile = guardianData.getString("mobile");
                    GuardianOfficePhone = guardianData.getString("office_phone");
                    GuardianRelationship = guardianData.getString("relationship");
                    GuardianPic = guardianData.getString("user_pic");


                    // Parents Preference - Guardian's Id
                    if ((GuardianId != null) && !(GuardianId.isEmpty()) && !GuardianId.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianID(this, GuardianId);
                    }

                    // Parents Preference - Guardian's Name
                    if ((GuardianName != null) && !(GuardianName.isEmpty()) && !GuardianName.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianName(this, GuardianName);
                    }

                    // Parents Preference - Guardian's Phone
                    if ((GuardianHomePhone != null) && !(GuardianHomePhone.isEmpty()) && !GuardianHomePhone.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianHomePhone(this, GuardianHomePhone);
                    }

                    // Parents Preference - Guardian's Mail
                    if ((GuardianMail != null) && !(GuardianMail.isEmpty()) && !GuardianMail.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianEmail(this, GuardianMail);
                    }

                    // Parents Preference - Address
                    if ((GuardianAddress != null) && !(GuardianAddress.isEmpty()) && !GuardianAddress.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianAddress(this, GuardianAddress);
                    }

                    // Parents Preference - Guardian's Occupation
                    if ((GuardianOccupation != null) && !(GuardianOccupation.isEmpty()) && !GuardianOccupation.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianOccupation(this, GuardianOccupation);
                    }

                    // Parents Preference - Guardian's Income
                    if ((GuardianIncome != null) && !(GuardianIncome.isEmpty()) && !GuardianIncome.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianIncome(this, GuardianIncome);
                    }

                    // Parents Preference - Guardian's Home Phone
                    if ((GuardianHomePhone != null) && !(GuardianHomePhone.isEmpty()) && !GuardianHomePhone.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianHomePhone(this, GuardianHomePhone);
                    }

                    // Parents Preference - Guardian's Mobile
                    if ((GuardianMobile != null) && !(GuardianMobile.isEmpty()) && !GuardianMobile.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianMobile(this, GuardianMobile);
                    }

                    // Parents Preference - Guardian's Office Phone
                    if ((GuardianOfficePhone != null) && !(GuardianOfficePhone.isEmpty()) && !GuardianOfficePhone.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianOfficePhone(this, GuardianOfficePhone);
                    }

                    // Parents Preference - Guardian's Relationship
                    if ((GuardianRelationship!= null) && !(GuardianRelationship.isEmpty()) && !GuardianRelationship.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianRelationship(this, GuardianRelationship);
                    }

                    // Parents Preference - Guardian's Pic
                    if ((GuardianPic!= null) && !(GuardianPic.isEmpty()) && !GuardianPic.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianImg(this, GuardianPic);
                    }


                } else {
                    saveUserData(userData);

                    //Father Details
                    String FatherId = "";
                    String FatherName = "";
                    String FatherAddress = "";
                    String FatherMail = "";
                    String FatherOccupation = "";
                    String FatherIncome = "";
                    String FatherMobile = "";
                    String FatherHomePhone = "";
                    String FatherOfficePhone = "";
                    String FatherRelationship = "";
                    String FatherPic = "";

                    //Mother Details
                    String MotherId = "";
                    String MotherName = "";
                    String MotherAddress = "";
                    String MotherMail = "";
                    String MotherOccupation = "";
                    String MotherIncome = "";
                    String MotherMobile = "";
                    String MotherHomePhone = "";
                    String MotherOfficePhone = "";
                    String MotherRelationship = "";
                    String MotherPic = "";

                    //Guardian Details
                    String GuardianId = "";
                    String GuardianName = "";
                    String GuardianAddress = "";
                    String GuardianMail = "";
                    String GuardianOccupation = "";
                    String GuardianIncome = "";
                    String GuardianMobile = "";
                    String GuardianHomePhone = "";
                    String GuardianOfficePhone = "";
                    String GuardianRelationship = "";
                    String GuardianPic = "";

                    JSONObject getParentData = response.getJSONObject("parentProfile");
                    JSONObject fatherData = getParentData.getJSONObject("fatherProfile");
                    JSONObject motherData = getParentData.getJSONObject("motherProfile");
                    JSONObject guardianData = getParentData.getJSONObject("guardianProfile");

                    FatherId = fatherData.getString("id");
                    FatherName = fatherData.getString("name");
                    FatherHomePhone = fatherData.getString("home_phone");
                    FatherMail = fatherData.getString("email");
                    FatherAddress = fatherData.getString("home_address");
                    FatherOccupation = fatherData.getString("occupation");
                    FatherIncome = fatherData.getString("income");
                    FatherMobile = fatherData.getString("mobile");
                    FatherOfficePhone = fatherData.getString("office_phone");
                    FatherRelationship = fatherData.getString("relationship");
                    FatherPic = fatherData.getString("user_pic");

                    ///////////     FATHER      //////////

                    // Parents Preference - Father's Id
                    if ((FatherId != null) && !(FatherId.isEmpty()) && !FatherId.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherID(this, FatherId);
                    }

                    // Parents Preference - Father's Name
                    if ((FatherName != null) && !(FatherName.isEmpty()) && !FatherName.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherName(this, FatherName);
                    }

                    // Parents Preference - Father's Mail
                    if ((FatherMail != null) && !(FatherMail.isEmpty()) && !FatherMail.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherEmail(this, FatherMail);
                    }

                    // Parents Preference - Address
                    if ((FatherAddress != null) && !(FatherAddress.isEmpty()) && !FatherAddress.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherAddress(this, FatherAddress);
                    }

                    // Parents Preference - Father's Occupation
                    if ((FatherOccupation != null) && !(FatherOccupation.isEmpty()) && !FatherOccupation.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherOccupation(this, FatherOccupation);
                    }

                    // Parents Preference - Father's Income
                    if ((FatherIncome != null) && !(FatherIncome.isEmpty()) && !FatherIncome.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherIncome(this, FatherIncome);
                    }

                    // Parents Preference - Father's Home Phone
                    if ((FatherHomePhone != null) && !(FatherHomePhone.isEmpty()) && !FatherHomePhone.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherHomePhone(this, FatherHomePhone);
                    }

                    // Parents Preference - Father's Mobile
                    if ((FatherMobile != null) && !(FatherMobile.isEmpty()) && !FatherMobile.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherMobile(this, FatherMobile);
                    }

                    // Parents Preference - Father's Office Phone
                    if ((FatherOfficePhone != null) && !(FatherOfficePhone.isEmpty()) && !FatherOfficePhone.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherOfficePhone(this, FatherOfficePhone);
                    }

                    // Parents Preference - Father's Relationship
                    if ((FatherRelationship!= null) && !(FatherRelationship.isEmpty()) && !FatherRelationship.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherRelationship(this, FatherRelationship);
                    }

                    // Parents Preference - Father's Pic
                    if ((FatherPic!= null) && !(FatherPic.isEmpty()) && !FatherPic.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherImg(this, FatherPic);
                    }

                    ///////////     MOTHER      //////////

                    MotherId = motherData.getString("id");
                    MotherName = motherData.getString("name");
                    MotherHomePhone = motherData.getString("home_phone");
                    MotherMail = motherData.getString("email");
                    MotherAddress = motherData.getString("home_address");
                    MotherOccupation = motherData.getString("occupation");
                    MotherIncome = motherData.getString("income");
                    MotherMobile = motherData.getString("mobile");
                    MotherOfficePhone = motherData.getString("office_phone");
                    MotherRelationship = motherData.getString("relationship");
                    MotherPic = motherData.getString("user_pic");


                    // Parents Preference - Mother's Id
                    if ((MotherId != null) && !(MotherId.isEmpty()) && !MotherId.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherID(this, MotherId);
                    }

                    // Parents Preference - Mother's Name
                    if ((MotherName != null) && !(MotherName.isEmpty()) && !MotherName.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherName(this, MotherName);
                    }

                    // Parents Preference - Mother's Phone
                    if ((MotherHomePhone != null) && !(MotherHomePhone.isEmpty()) && !MotherHomePhone.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherHomePhone(this, MotherHomePhone);
                    }

                    // Parents Preference - Mother's Mail
                    if ((MotherMail != null) && !(MotherMail.isEmpty()) && !MotherMail.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherEmail(this, MotherMail);
                    }

                    // Parents Preference - Address
                    if ((MotherAddress != null) && !(MotherAddress.isEmpty()) && !MotherAddress.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherAddress(this, MotherAddress);
                    }

                    // Parents Preference - Mother's Occupation
                    if ((MotherOccupation != null) && !(MotherOccupation.isEmpty()) && !MotherOccupation.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveFatherOccupation(this, MotherOccupation);
                    }

                    // Parents Preference - Mother's Income
                    if ((MotherIncome != null) && !(MotherIncome.isEmpty()) && !MotherIncome.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherIncome(this, MotherIncome);
                    }

                    // Parents Preference - Mother's Home Phone
                    if ((MotherHomePhone != null) && !(MotherHomePhone.isEmpty()) && !MotherHomePhone.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherHomePhone(this, MotherHomePhone);
                    }

                    // Parents Preference - Mother's Mobile
                    if ((MotherMobile != null) && !(MotherMobile.isEmpty()) && !MotherMobile.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherMobile(this, MotherMobile);
                    }

                    // Parents Preference - Mother's Office Phone
                    if ((MotherOfficePhone != null) && !(MotherOfficePhone.isEmpty()) && !MotherOfficePhone.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherOfficePhone(this, MotherOfficePhone);
                    }

                    // Parents Preference - Mother's Relationship
                    if ((MotherRelationship!= null) && !(MotherRelationship.isEmpty()) && !MotherRelationship.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherRelationship(this, MotherRelationship);
                    }

                    // Parents Preference - Mother's Pic
                    if ((MotherPic!= null) && !(MotherPic.isEmpty()) && !MotherPic.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveMotherImg(this, MotherPic);
                    }

                    ///////////     GUARDIAN      //////////

                    GuardianId = guardianData.getString("id");
                    GuardianName = guardianData.getString("name");
                    GuardianHomePhone = guardianData.getString("home_phone");
                    GuardianMail = guardianData.getString("email");
                    GuardianAddress = guardianData.getString("home_address");
                    GuardianOccupation = guardianData.getString("occupation");
                    GuardianIncome = guardianData.getString("income");
                    GuardianMobile = guardianData.getString("mobile");
                    GuardianOfficePhone = guardianData.getString("office_phone");
                    GuardianRelationship = guardianData.getString("relationship");
                    GuardianPic = guardianData.getString("user_pic");


                    // Parents Preference - Guardian's Id
                    if ((GuardianId != null) && !(GuardianId.isEmpty()) && !GuardianId.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianID(this, GuardianId);
                    }

                    // Parents Preference - Guardian's Name
                    if ((GuardianName != null) && !(GuardianName.isEmpty()) && !GuardianName.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianName(this, GuardianName);
                    }

                    // Parents Preference - Guardian's Phone
                    if ((GuardianHomePhone != null) && !(GuardianHomePhone.isEmpty()) && !GuardianHomePhone.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianHomePhone(this, GuardianHomePhone);
                    }

                    // Parents Preference - Guardian's Mail
                    if ((GuardianMail != null) && !(GuardianMail.isEmpty()) && !GuardianMail.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianEmail(this, GuardianMail);
                    }

                    // Parents Preference - Address
                    if ((GuardianAddress != null) && !(GuardianAddress.isEmpty()) && !GuardianAddress.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianAddress(this, GuardianAddress);
                    }

                    // Parents Preference - Guardian's Occupation
                    if ((GuardianOccupation != null) && !(GuardianOccupation.isEmpty()) && !GuardianOccupation.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianOccupation(this, GuardianOccupation);
                    }

                    // Parents Preference - Guardian's Income
                    if ((GuardianIncome != null) && !(GuardianIncome.isEmpty()) && !GuardianIncome.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianIncome(this, GuardianIncome);
                    }

                    // Parents Preference - Guardian's Home Phone
                    if ((GuardianHomePhone != null) && !(GuardianHomePhone.isEmpty()) && !GuardianHomePhone.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianHomePhone(this, GuardianHomePhone);
                    }

                    // Parents Preference - Guardian's Mobile
                    if ((GuardianMobile != null) && !(GuardianMobile.isEmpty()) && !GuardianMobile.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianMobile(this, GuardianMobile);
                    }

                    // Parents Preference - Guardian's Office Phone
                    if ((GuardianOfficePhone != null) && !(GuardianOfficePhone.isEmpty()) && !GuardianOfficePhone.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianOfficePhone(this, GuardianOfficePhone);
                    }

                    // Parents Preference - Guardian's Relationship
                    if ((GuardianRelationship!= null) && !(GuardianRelationship.isEmpty()) && !GuardianRelationship.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianRelationship(this, GuardianRelationship);
                    }

                    // Parents Preference - Guardian's Pic
                    if ((GuardianPic!= null) && !(GuardianPic.isEmpty()) && !GuardianPic.equalsIgnoreCase("null")) {
                        PreferenceStorage.saveGuardianImg(this, GuardianPic);
                    }

                    JSONArray getStudentData = response.getJSONArray("registeredDetails");
                    JSONObject studentData = getStudentData.getJSONObject(0);

                    try {
                        database.deleteLocal();

                        for (int i = 0; i < getStudentData.length(); i++) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            JSONObject jsonobj = getStudentData.getJSONObject(i);


                            System.out.println("registered_id : " + i + " = " + jsonobj.getString("registered_id"));
                            System.out.println("admission_id : " + i + " = " + jsonobj.getString("admission_id"));
                            System.out.println("admission_no : " + i + " = " + jsonobj.getString("admission_no"));
                            System.out.println("class_id : " + i + " = " + jsonobj.getString("class_id"));
                            System.out.println("name : " + i + " = " + jsonobj.getString("name"));
                            System.out.println("class_name : " + i + " = " + jsonobj.getString("class_name"));
                            System.out.println("sec_name : " + i + " = " + jsonobj.getString("sec_name"));

                            String v1 = jsonobj.getString("registered_id"),
                                    v2 = jsonobj.getString("admission_id"),
                                    v3 = jsonobj.getString("admission_no"),
                                    v4 = jsonobj.getString("class_id"),
                                    v5 = jsonobj.getString("name"),
                                    v6 = jsonobj.getString("class_name"),
                                    v7 = jsonobj.getString("sec_name");

                            database.student_details_insert(v1, v2, v3, v4, v5, v6, v7);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (PreferenceStorage.getForgotPasswordStatus(getApplicationContext()).contentEquals("1")) {
                Intent intent = new Intent(this, StudentInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, ResetPasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    // Globally save userData using this method
    private void saveUserData(JSONObject userData) {
        Log.d(TAG, "userData dictionary" + userData.toString());
        //User Data variables
        String Name = "";
        String UserName = "";
        String UserImage = "";
        String UserPicUrl = "";
        String UserType = "";
        String UserTypeName = "";
        String forgotPasswordStatus = "";

        try {
            String user_id = null;
            if (userData != null) {
                user_id = userData.getString("user_id") + "";

                PreferenceStorage.saveUserId(this, user_id);

                Log.d(TAG, "created user id" + user_id);

                //need to re do this
//                Log.d(TAG, "sign in response is" + response.toString());

                Name = userData.getString("name"); //Get user's name
                UserName = userData.getString("user_name"); //Get userName
                UserImage = userData.getString("user_pic"); //Get user image
                int userType = Integer.parseInt(userData.getString("user_type")); //Get userType for generate user image url
                String imageURL = "";
                if (userType == 1) {
                    imageURL = EnsyfiConstants.USER_IMAGE_API_ADMIN; // Admin user image url
                } else if (userType == 2) {
                    imageURL = EnsyfiConstants.USER_IMAGE_API_TEACHERS; // Teacher user image url
                } else if (userType == 3) {
                    imageURL = EnsyfiConstants.USER_IMAGE_API_STUDENTS; // Student user image url
                } else {
                    imageURL = EnsyfiConstants.USER_IMAGE_API_PARENTS; // Parents user image url
                }
                UserPicUrl = PreferenceStorage.getUserDynamicAPI(this) + imageURL + UserImage; // Generate user image url
                UserType = userData.getString("user_type"); // Get userType
                UserTypeName = userData.getString("user_type_name"); // Get userTypeName
                forgotPasswordStatus = userData.getString("password_status"); // Get password status

                // User Preference - Name
                if ((Name != null) && !(Name.isEmpty()) && !Name.equalsIgnoreCase("null")) {
                    PreferenceStorage.saveName(this, Name);
                }

                // User Preference - Username
                if ((UserName != null) && !(UserName.isEmpty()) && !UserName.equalsIgnoreCase("null")) {
                    PreferenceStorage.saveUserName(this, UserName);
                }

                // User Preference - ProfilePic
                if ((UserPicUrl != null) && !(UserPicUrl.isEmpty()) && !UserPicUrl.equalsIgnoreCase("null")) {
                    PreferenceStorage.saveUserPicture(this, UserPicUrl);
                }

                // User Preference - Usertype
                if ((UserType != null) && !(UserType.isEmpty()) && !UserType.equalsIgnoreCase("null")) {
                    PreferenceStorage.saveUserType(this, UserType);
                }

                // User Preference - UsertypeName
                if ((UserTypeName != null) && !(UserTypeName.isEmpty()) && !UserTypeName.equalsIgnoreCase("null")) {
                    PreferenceStorage.saveUserTypeName(this, UserTypeName);
                }

                // Forgot Password Reset Status
                if ((forgotPasswordStatus != null) && !(forgotPasswordStatus.isEmpty()) && !forgotPasswordStatus.equalsIgnoreCase("null")) {
                    PreferenceStorage.saveForgotPasswordStatus(this, forgotPasswordStatus);
                }
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(this, error);
    }
}