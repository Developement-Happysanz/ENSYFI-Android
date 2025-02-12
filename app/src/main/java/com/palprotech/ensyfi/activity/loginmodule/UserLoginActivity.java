package com.palprotech.ensyfi.activity.loginmodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.adminmodule.AdminDashBoardActivity;
import com.palprotech.ensyfi.activity.teachermodule.TeacherDashBoardActivity;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.parents.Support.SaveParentData;
import com.palprotech.ensyfi.bean.student.support.SaveStudentData;
import com.palprotech.ensyfi.bean.teacher.support.SaveTeacherData;
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

/**
 * Created by Admin on 22-03-2017.
 */

public class UserLoginActivity extends AppCompatActivity implements View.OnClickListener, IServiceListener, DialogClickListener {

    private static final String TAG = UserLoginActivity.class.getName();

    private ServiceHelper serviceHelper;
    private SaveStudentData studentData;
    private SaveParentData parentData;
    private SaveTeacherData teacherData;
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
        SetUI();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
        return true;
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
        studentData = new SaveStudentData(this);
        parentData = new SaveParentData(this);
        teacherData = new SaveTeacherData(this);
        progressDialogHelper = new ProgressDialogHelper(this);

        String url = PreferenceStorage.getInstituteLogoPicUrl(this);
        if ((url == null) || (url.isEmpty())) {
        }
        if (((url != null) && !(url.isEmpty()))) {
            Picasso.get().load(url).placeholder(R.drawable.profile_pic).error(R.drawable.profile_pic).into(mProfileImage);
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
                        String GCMKey = PreferenceStorage.getGCM(getApplicationContext());
                        jsonObject.put(EnsyfiConstants.GCM_KEY, GCMKey);
                        jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));
                        jsonObject.put(EnsyfiConstants.MOBILE_TYPE, "1");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
                    String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.USER_LOGIN_API;
                    serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
                }
            }
            if (v == txtForgotPassword) {
                Intent intent = new Intent(this, ForgotPasswordActivity.class);
                startActivity(intent);
//                finish();
            }

        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private boolean validateFields() {
        if (!AppValidator.checkNullString(this.inputUsername.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, this.getResources().getString(R.string.enter_user_name));
            return false;
        } else if (!AppValidator.checkNullString(this.inputPassword.getText().toString())) {
            AlertDialogHelper.showSimpleAlertDialog(this, this.getResources().getString(R.string.enter_password));
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

    private boolean validateResponse(JSONObject response) {
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

    @Override
    public void onResponse(JSONObject response) {

        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {
            try {

                JSONObject userData = response.getJSONObject("userData");

                String academicYearId = response.getString("year_id");
                // User Preference - Academic Year Id
                if ((academicYearId != null) && !(academicYearId.isEmpty()) && !academicYearId.equalsIgnoreCase("null")) {
                    PreferenceStorage.saveAcademicYearId(this, academicYearId);
                }

                int userType = Integer.parseInt(userData.getString("user_type"));


                if (userType == 1) {

                    saveUserData(userData);

                } else if (userType == 2) {

                    saveUserData(userData);

                    JSONArray getTeacherProfile = response.getJSONArray("teacherProfile");
                    if (getTeacherProfile != null && getTeacherProfile.length() > 0) {
                        teacherData.saveTeacherProfile(getTeacherProfile);
                    }

                    JSONObject getClassSubject = response.getJSONObject("classSubject");
                    if (validateResponse(getClassSubject)) {
                        JSONArray getClassSubjectArray = getClassSubject.getJSONArray("data");
                        if (getClassSubjectArray != null && getClassSubjectArray.length() > 0) {
                            teacherData.saveTeacherHandlingSubject(getClassSubjectArray);
                        }
                    }

//
                    JSONObject getTimeTableDays = response.getJSONObject("timeTabledays");
                    if (validateResponse(getTimeTableDays)) {
                        JSONArray getTimeTableDaysArray = getTimeTableDays.getJSONArray("data");
                        if (getTimeTableDaysArray != null && getTimeTableDaysArray.length() > 0) {
                            teacherData.saveTimeTableDays(getTimeTableDaysArray);
                        }
                    }
//
                    JSONObject getTimeTable = response.getJSONObject("timeTable");
                    if (validateResponse(getTimeTable)) {
                        JSONArray getTimeTableArray = getTimeTable.getJSONArray("data");
                        if (getTimeTableArray != null && getTimeTableArray.length() > 0) {
                            teacherData.saveTeacherTimeTable(getTimeTableArray);
                        }
                    }

                    JSONObject getTeacherClassStudentsDetails = response.getJSONObject("studDetails");
                    if (validateResponse(getTeacherClassStudentsDetails)) {
                        JSONArray getTeacherClassStudentsDetailsArray = getTeacherClassStudentsDetails.getJSONArray("data");
                        if (getTeacherClassStudentsDetailsArray != null && getTeacherClassStudentsDetailsArray.length() > 0) {
                            teacherData.saveStudentDetails(getTeacherClassStudentsDetailsArray);
                        }
                    }

                    JSONObject getExamsOfClass = response.getJSONObject("Exams");
                    if (validateResponse(getExamsOfClass)) {
                        JSONArray getExamsOfClassArray = getExamsOfClass.getJSONArray("data");
                        if (getExamsOfClassArray != null && getExamsOfClassArray.length() > 0) {
                            teacherData.saveExamsOfClass(getExamsOfClassArray);
                        }
                    }

                    JSONObject getAcademicExamDetails = response.getJSONObject("examDetails");
                    if (validateResponse(getAcademicExamDetails)) {
                        JSONArray getAcademicExamDetailsArray = getAcademicExamDetails.getJSONArray("data");
                        if (getAcademicExamDetailsArray != null && getAcademicExamDetailsArray.length() > 0) {
                            teacherData.saveExamsDetails(getAcademicExamDetailsArray);
                        }
                    }

                    JSONObject getHomeWorkClassTest = response.getJSONObject("homeWork");
                    if (validateResponse(getHomeWorkClassTest)) {
                        JSONArray getHomeWorkClassTestArray = getHomeWorkClassTest.getJSONArray("data");
                        if (getHomeWorkClassTestArray != null && getHomeWorkClassTestArray.length() > 0) {
                            teacherData.saveHomeWorkClassTest(getHomeWorkClassTestArray);
                        }
                    }

//                    JSONObject getAcademicMonth = response.getJSONObject("academic_month");
//                    if (validateResponse(getAcademicMonth)) {
                    JSONArray getAcademicMonthArray = response.getJSONArray("academic_month");
                    if (getAcademicMonthArray != null && getAcademicMonthArray.length() > 0) {
                        teacherData.saveAcademicMonth(getAcademicMonthArray);
                    }
//                    }

                } else if (userType == 3) {

                    saveUserData(userData);
                    saveStudentParentDetails(response);

                    JSONArray getData = response.getJSONArray("studentProfile");
                    studentData.saveStudentProfile(getData);

                } else {

                    saveUserData(userData);
                    saveStudentParentDetails(response);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (PreferenceStorage.getForgotPasswordStatus(getApplicationContext()).contentEquals("1")) {
                String userTypeString = PreferenceStorage.getUserType(getApplicationContext());
                int userType = Integer.parseInt(userTypeString);
                if (userType == 1) {
                    Intent intent = new Intent(this, AdminDashBoardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else if (userType == 2) {
                    Intent intent = new Intent(this, TeacherDashBoardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else if (userType == 3) {
                    Intent intent = new Intent(this, StudentInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(this, StudentInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

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

    private void saveStudentParentDetails(JSONObject response) {
        try {

            JSONObject getParentData = response.getJSONObject("parentProfile");
            parentData.saveParentProfile(getParentData);

            JSONArray getStudentData = response.getJSONArray("registeredDetails");
            studentData.saveStudentRegisteredData(getStudentData);

        } catch (Exception ex) {
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
            ex.printStackTrace();
        }
    }

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(this, error);
    }
}