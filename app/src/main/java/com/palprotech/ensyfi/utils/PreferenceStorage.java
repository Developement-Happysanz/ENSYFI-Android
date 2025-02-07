package com.palprotech.ensyfi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Admin on 03-04-2017.
 */

public class PreferenceStorage {

    // School Id Login Preferences
    // InstituteId
    public static void saveInstituteId(Context context, String instituteId) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_INSTITUTE_ID, instituteId);
        editor.apply();
    }

    public static String getInstituteId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String instituteId;
        instituteId = sharedPreferences.getString(EnsyfiConstants.KEY_INSTITUTE_ID, "");
        return instituteId;
    }

    // InstituteName
    public static void saveInstituteName(Context context, String instituteName) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_INSTITUTE_NAME, instituteName);
        editor.apply();
    }

    public static String getInstituteName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String instituteName;
        instituteName = sharedPreferences.getString(EnsyfiConstants.KEY_INSTITUTE_NAME, "");
        return instituteName;
    }

    // InstituteCode

    public static void saveEnsyfiInstituteCode(Context context, String instituteCode) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_ENSYFI_INSTITUTE_CODE, instituteCode);
        editor.apply();
    }

    public static String getEnsyfiInstituteCode(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String instituteCode;
        instituteCode = sharedPreferences.getString(EnsyfiConstants.KEY_ENSYFI_INSTITUTE_CODE, "");
        return instituteCode;
    }

    public static void saveInstituteCode(Context context, String instituteCode) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_INSTITUTE_CODE, instituteCode);
        editor.apply();
    }

    public static String getInstituteCode(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String instituteCode;
        instituteCode = sharedPreferences.getString(EnsyfiConstants.KEY_INSTITUTE_CODE, "");
        return instituteCode;
    }

    // InstituteLogoPic
    public static void saveInstituteLogoPic(Context context, String url) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_INSTITUTE_LOGO, url);
        editor.apply();

    }

    public static String getInstituteLogoPicUrl(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String url;
        url = sharedPreferences.getString(EnsyfiConstants.KEY_INSTITUTE_LOGO, "");
        return url;

    }

    // User Login Preferences
    // User Dynamic API

    public static void saveUserDynamicDB(Context context, String userDynamicAPI) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_USER_DYNAMIC_DB, userDynamicAPI);
        editor.apply();
    }

    public static String getUserDynamicDB(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String userDynamicAPI;
        userDynamicAPI = sharedPreferences.getString(EnsyfiConstants.KEY_USER_DYNAMIC_DB, "");
        return userDynamicAPI;
    }

    public static void saveUserDynamicAPI(Context context, String userDynamicAPI) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_USER_DYNAMIC_API, userDynamicAPI);
        editor.apply();
    }

    public static String getUserDynamicAPI(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String userDynamicAPI;
        userDynamicAPI = sharedPreferences.getString(EnsyfiConstants.KEY_USER_DYNAMIC_API, "");
        return userDynamicAPI;
    }

    // UserId
    public static void saveUserId(Context context, String userId) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_USER_ID, userId);
        editor.apply();
    }

    public static String getUserId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String  userId;
        userId = sharedPreferences.getString(EnsyfiConstants.KEY_USER_ID, "");
        return userId;
    }

    // Name
    public static void saveName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_NAME, name);
        editor.apply();
    }

    public static String getName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String name;
        name = sharedPreferences.getString(EnsyfiConstants.KEY_NAME, "");
        return name;
    }

    // User Name
    public static void saveUserName(Context context, String userName) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_USER_NAME, userName);
        editor.apply();
    }

    public static String getUserName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String userName;
        userName = sharedPreferences.getString(EnsyfiConstants.KEY_USER_NAME, "");
        return userName;
    }

    // User Image
    public static void saveUserPicture(Context context, String userPicture) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_USER_IMAGE, userPicture);
        editor.apply();
    }

    public static String getUserPicture(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String userPicture;
        userPicture = sharedPreferences.getString(EnsyfiConstants.KEY_USER_IMAGE, "");
        return userPicture;
    }

    // User Type
    public static void saveUserType(Context context, String userType) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_USER_TYPE, userType);
        editor.apply();
    }

    public static String getUserType(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String userType;
        userType = sharedPreferences.getString(EnsyfiConstants.KEY_USER_TYPE, "");
        return userType;
    }

    // User Type Name
    public static void saveUserTypeName(Context context, String userTypeName) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_USER_TYPE_NAME, userTypeName);
        editor.apply();
    }

    public static String getUserTypeName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String userTypeName;
        userTypeName = sharedPreferences.getString(EnsyfiConstants.KEY_USER_TYPE_NAME, "");
        return userTypeName;
    }

    // Academic Year
    public static void saveAcademicYearId(Context context, String academicYearId) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_ACADEMIC_YEAR_ID, academicYearId);
        editor.apply();
    }

    public static String getAcademicYearId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String academicYearId;
        academicYearId = sharedPreferences.getString(EnsyfiConstants.KEY_ACADEMIC_YEAR_ID, "");
        return academicYearId;
    }

    // Student Preference Data
    // Get Student Enroll Id
    public static void saveStudentRegisteredIdPreference(Context context, String studentPrefEnrollID) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_STUDENT_ENROLL_ID_PREFERENCES, studentPrefEnrollID);
        editor.apply();
    }

    public static String getStudentRegisteredIdPreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPrefEnrollID;
        studentPrefEnrollID = sharedPreferences.getString(EnsyfiConstants.KEY_STUDENT_ENROLL_ID_PREFERENCES, "");
        return studentPrefEnrollID;
    }

    // Get Student Admission Id
    public static void saveStudentAdmissionIdPreference(Context context, String studentPrefAdmissionID) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_STUDENT_ADMISSION_ID_PREFERENCES, studentPrefAdmissionID);
        editor.apply();
    }

    public static String getStudentAdmissionIdPreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPrefAdmissionID;
        studentPrefAdmissionID = sharedPreferences.getString(EnsyfiConstants.KEY_STUDENT_ADMISSION_ID_PREFERENCES, "");
        return studentPrefAdmissionID;
    }

    // Get Student Admission No
    public static void saveStudentAdmissionNoPreference(Context context, String studentPrefAdmissionNo) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_STUDENT_ADMISSION_NO_PREFERENCES, studentPrefAdmissionNo);
        editor.apply();
    }

    public static String getStudentAdmissionNoPreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPrefAdmissionNo;
        studentPrefAdmissionNo = sharedPreferences.getString(EnsyfiConstants.KEY_STUDENT_ADMISSION_NO_PREFERENCES, "");
        return studentPrefAdmissionNo;
    }

    // Get Student Class Id
    public static void saveStudentClassIdPreference(Context context, String studentPrefclassId) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_STUDENT_CLASS_ID_PREFERENCES, studentPrefclassId);
        editor.apply();
    }

    public static String getStudentClassIdPreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPrefclassId;
        studentPrefclassId = sharedPreferences.getString(EnsyfiConstants.KEY_STUDENT_CLASS_ID_PREFERENCES, "");
        return studentPrefclassId;
    }

    // Get Student Name
    public static void saveStudentNamePreference(Context context, String studentPrefName) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_STUDENT_NAME_PREFERENCES, studentPrefName);
        editor.apply();
    }

    public static String getStudentNamePreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPrefName;
        studentPrefName = sharedPreferences.getString(EnsyfiConstants.KEY_STUDENT_NAME_PREFERENCES, "");
        return studentPrefName;
    }

    // Get Student Class Name
    public static void saveStudentClassNamePreference(Context context, String studentPrefClassName) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_STUDENT_CLASS_NAME_PREFERENCES, studentPrefClassName);
        editor.apply();
    }

    public static String getStudentClassNamePreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPrefClassName;
        studentPrefClassName = sharedPreferences.getString(EnsyfiConstants.KEY_STUDENT_CLASS_NAME_PREFERENCES, "");
        return studentPrefClassName;
    }

    // Get Student Section Name
    public static void saveStudentSectionNamePreference(Context context, String studentPrefSectionName) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_STUDENT_SECTION_NAME_PREFERENCES, studentPrefSectionName);
        editor.apply();
    }

    public static String getStudentSectionNamePreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPrefSectionName;
        studentPrefSectionName = sharedPreferences.getString(EnsyfiConstants.KEY_STUDENT_SECTION_NAME_PREFERENCES, "");
        return studentPrefSectionName;
    }

    // Forgot Password
    // Forgot Password Status Check
    public static String getForgotPasswordStatus(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String url;
        url = sharedPreferences.getString(EnsyfiConstants.KEY_FORGOT_PASSWORD_STATUS, "");
        return url;
    }

    public static void saveForgotPasswordStatus(Context context, String url) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_FORGOT_PASSWORD_STATUS, url);
        editor.apply();
    }

    // Forgot Password Enabled Check
    public static String getForgotPasswordStatusEnable(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String url;
        url = sharedPreferences.getString(EnsyfiConstants.KEY_FORGOT_PASSWORD_STATUS, "");
        return url;
    }

    public static void saveForgotPasswordStatusEnable(Context context, String url) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_FORGOT_PASSWORD_STATUS, url);
        editor.apply();
    }


    ////////        Father Details      ///////////

    public static void saveFatherID(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_ID, name);
        editor.apply();
    }

    public static String getFatherID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherID;
        fatherID = sharedPreferences.getString(EnsyfiConstants.FATHER_ID, "");
        return fatherID;
    }

    public static void saveFatherName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_NAME, name);
        editor.apply();
    }

    public static String getFatherName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherName;
        fatherName = sharedPreferences.getString(EnsyfiConstants.FATHER_NAME, "");
        return fatherName;
    }

    public static void saveFatherOccupation(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_OCCUPATION, name);
        editor.apply();
    }

    public static String getFatherOccupation(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherOccupation;
        fatherOccupation = sharedPreferences.getString(EnsyfiConstants.FATHER_OCCUPATION, "");
        return fatherOccupation;
    }

    public static void saveFatherIncome(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_INCOME, name);
        editor.apply();
    }

    public static String getFatherIncome(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherIncome;
        fatherIncome = sharedPreferences.getString(EnsyfiConstants.FATHER_INCOME, "");
        return fatherIncome;
    }

    public static void saveFatherAddress(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_ADDRESS, name);
        editor.apply();
    }

    public static String getFatherAddress(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherAddress;
        fatherAddress = sharedPreferences.getString(EnsyfiConstants.FATHER_ADDRESS, "");
        return fatherAddress;
    }

    public static void saveFatherEmail(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_EMAIL, name);
        editor.apply();
    }

    public static String getFatherEmail(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherEmail;
        fatherEmail = sharedPreferences.getString(EnsyfiConstants.FATHER_EMAIL, "");
        return fatherEmail;
    }

    public static void saveFatherHomePhone(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_HOME_PHONE, name);
        editor.apply();
    }

    public static String getFatherHomePhone(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherHomePhone;
        fatherHomePhone = sharedPreferences.getString(EnsyfiConstants.FATHER_HOME_PHONE, "");
        return fatherHomePhone;
    }

    public static void saveFatherOfficePhone(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_OFFICE_PHONE, name);
        editor.apply();
    }

    public static String getFatherOfficePhone(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherOfficePhone;
        fatherOfficePhone = sharedPreferences.getString(EnsyfiConstants.FATHER_OFFICE_PHONE, "");
        return fatherOfficePhone;
    }

    public static void saveFatherMobile(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_MOBILE, name);
        editor.apply();
    }

    public static String getFatherMobile(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherMobile;
        fatherMobile = sharedPreferences.getString(EnsyfiConstants.FATHER_MOBILE, "");
        return fatherMobile;
    }

    public static void saveFatherRelationship(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_RELATIONSHIP, name);
        editor.apply();
    }

    public static String getFatherRelationship(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherRelationship;
        fatherRelationship = sharedPreferences.getString(EnsyfiConstants.FATHER_RELATIONSHIP, "");
        return fatherRelationship;
    }

    public static void saveFatherImg(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_IMAGE, name);
        editor.apply();
    }

    public static String getFatherImg(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherImg;
        fatherImg = sharedPreferences.getString(EnsyfiConstants.FATHER_IMAGE, "");
        return fatherImg;
    }



    ////////        Mother Details      ///////////



    public static void saveMotherID(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_ID, name);
        editor.apply();
    }

    public static String getMotherID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherID;
        motherID = sharedPreferences.getString(EnsyfiConstants.MOTHER_ID, "");
        return motherID;
    }

    public static void saveMotherName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_NAME, name);
        editor.apply();
    }

    public static String getMotherName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherName;
        motherName = sharedPreferences.getString(EnsyfiConstants.MOTHER_NAME, "");
        return motherName;
    }

    public static void saveMotherOccupation(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_OCCUPATION, name);
        editor.apply();
    }

    public static String getMotherOccupation(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherOccupation;
        motherOccupation = sharedPreferences.getString(EnsyfiConstants.MOTHER_OCCUPATION, "");
        return motherOccupation;
    }

    public static void saveMotherIncome(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_INCOME, name);
        editor.apply();
    }

    public static String getMotherIncome(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherIncome;
        motherIncome = sharedPreferences.getString(EnsyfiConstants.MOTHER_INCOME, "");
        return motherIncome;
    }

    public static void saveMotherAddress(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_ADDRESS, name);
        editor.apply();
    }

    public static String getMotherAddress(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherAddress;
        motherAddress = sharedPreferences.getString(EnsyfiConstants.MOTHER_ADDRESS, "");
        return motherAddress;
    }

    public static void saveMotherEmail(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_EMAIL, name);
        editor.apply();
    }

    public static String getMotherEmail(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherEmail;
        motherEmail = sharedPreferences.getString(EnsyfiConstants.MOTHER_EMAIL, "");
        return motherEmail;
    }

    public static void saveMotherHomePhone(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_HOME_PHONE, name);
        editor.apply();
    }

    public static String getMotherHomePhone(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherHomePhone;
        motherHomePhone = sharedPreferences.getString(EnsyfiConstants.MOTHER_HOME_PHONE, "");
        return motherHomePhone;
    }

    public static void saveMotherOfficePhone(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_OFFICE_PHONE, name);
        editor.apply();
    }

    public static String getMotherOfficePhone(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherOfficePhone;
        motherOfficePhone = sharedPreferences.getString(EnsyfiConstants.MOTHER_OFFICE_PHONE, "");
        return motherOfficePhone;
    }

    public static void saveMotherMobile(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_MOBILE, name);
        editor.apply();
    }

    public static String getMotherMobile(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherMobile;
        motherMobile = sharedPreferences.getString(EnsyfiConstants.MOTHER_MOBILE, "");
        return motherMobile;
    }

    public static void saveMotherRelationship(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_RELATIONSHIP, name);
        editor.apply();
    }

    public static String getMotherRelationship(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherRelationship;
        motherRelationship = sharedPreferences.getString(EnsyfiConstants.MOTHER_RELATIONSHIP, "");
        return motherRelationship;
    }

    public static void saveMotherImg(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_IMAGE, name);
        editor.apply();
    }

    public static String getMotherImg(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherImg;
        motherImg = sharedPreferences.getString(EnsyfiConstants.MOTHER_IMAGE, "");
        return motherImg;
    }



    ////////        Guardian Details      ///////////



    public static void saveGuardianID(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_ID, name);
        editor.apply();
    }

    public static String getGuardianID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianID;
        guardianID = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_ID, "");
        return guardianID;
    }

    public static void saveGuardianName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_NAME, name);
        editor.apply();
    }

    public static String getGuardianName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianName;
        guardianName = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_NAME, "");
        return guardianName;
    }

    public static void saveGuardianOccupation(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_OCCUPATION, name);
        editor.apply();
    }

    public static String getGuardianOccupation(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianOccupation;
        guardianOccupation = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_OCCUPATION, "");
        return guardianOccupation;
    }

    public static void saveGuardianIncome(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_INCOME, name);
        editor.apply();
    }

    public static String getGuardianIncome(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianIncome;
        guardianIncome = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_INCOME, "");
        return guardianIncome;
    }

    public static void saveGuardianAddress(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_ADDRESS, name);
        editor.apply();
    }

    public static String getGuardianAddress(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianAddress;
        guardianAddress = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_ADDRESS, "");
        return guardianAddress;
    }

    public static void saveGuardianEmail(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_EMAIL, name);
        editor.apply();
    }

    public static String getGuardianEmail(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianEmail;
        guardianEmail = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_EMAIL, "");
        return guardianEmail;
    }

    public static void saveGuardianHomePhone(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_HOME_PHONE, name);
        editor.apply();
    }

    public static String getGuardianHomePhone(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianHomePhone;
        guardianHomePhone = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_HOME_PHONE, "");
        return guardianHomePhone;
    }

    public static void saveGuardianOfficePhone(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_OFFICE_PHONE, name);
        editor.apply();
    }

    public static String getGuardianOfficePhone(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianOfficePhone;
        guardianOfficePhone = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_OFFICE_PHONE, "");
        return guardianOfficePhone;
    }

    public static void saveGuardianMobile(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_MOBILE, name);
        editor.apply();
    }

    public static String getGuardianMobile(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianMobile;
        guardianMobile = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_MOBILE, "");
        return guardianMobile;
    }

    public static void saveGuardianRelationship(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_RELATIONSHIP, name);
        editor.apply();
    }

    public static String getGuardianRelationship(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianRelationship;
        guardianRelationship = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_RELATIONSHIP, "");
        return guardianRelationship;
    }

    public static void saveGuardianImg(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_IMAGE, name);
        editor.apply();
    }

    public static String getGuardianImg(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianImg;
        guardianImg = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_IMAGE, "");
        return guardianImg;
    }

    /////////       STUDENT DETAILS         /////////

    public static void saveStudentAdmissionID(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_ADMISSION_ID, name);
        editor.apply();
    }

    public static String getStudentAdmissionID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentAdmissionID;
        studentAdmissionID = sharedPreferences.getString(EnsyfiConstants.STUDENT_ADMISSION_ID, "");
        return studentAdmissionID;
    }

    public static void saveStudentAdmissionYear(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_ADMISSION_YEAR, name);
        editor.apply();
    }

    public static String getStudentAdmissionYear(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentAdmissionYear;
        studentAdmissionYear = sharedPreferences.getString(EnsyfiConstants.STUDENT_ADMISSION_YEAR, "");
        return studentAdmissionYear;
    }

    public static void saveStudentAdmissionNumber(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_ADMISSION_NUMBER, name);
        editor.apply();
    }

    public static String getStudentAdmissionNumber(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentAdmissionNumber;
        studentAdmissionNumber = sharedPreferences.getString(EnsyfiConstants.STUDENT_ADMISSION_NUMBER, "");
        return studentAdmissionNumber;
    }

    public static void saveStudentEmsiNumber(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_EMSI_NUMBER, name);
        editor.apply();
    }

    public static String getStudentEmsiNumber(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentEmsiNumber;
        studentEmsiNumber = sharedPreferences.getString(EnsyfiConstants.STUDENT_EMSI_NUMBER, "");
        return studentEmsiNumber;
    }

    public static void saveStudentAdmissionDate(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_ADMISSION_DATE, name);
        editor.apply();
    }

    public static String getStudentAdmissionDate(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentAdmissionDate;
        studentAdmissionDate = sharedPreferences.getString(EnsyfiConstants.STUDENT_ADMISSION_DATE, "");
        return studentAdmissionDate;
    }

    public static void saveStudentName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_NAME, name);
        editor.apply();
    }

    public static String getStudentName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentName;
        studentName = sharedPreferences.getString(EnsyfiConstants.STUDENT_NAME, "");
        return studentName;
    }

    public static void saveStudentGender(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_GENDER, name);
        editor.apply();
    }

    public static String getStudentGender(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentGender;
        studentGender = sharedPreferences.getString(EnsyfiConstants.STUDENT_GENDER, "");
        return studentGender;
    }

    public static void saveStudentDateOfBirth(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_DATE_OF_BIRTH, name);
        editor.apply();
    }

    public static String getStudentDateOfBirth(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentDateOfBirth;
        studentDateOfBirth = sharedPreferences.getString(EnsyfiConstants.STUDENT_DATE_OF_BIRTH, "");
        return studentDateOfBirth;
    }

    public static void saveStudentAge(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_AGE, name);
        editor.apply();
    }

    public static String getStudentAge(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentAge;
        studentAge = sharedPreferences.getString(EnsyfiConstants.STUDENT_AGE, "");
        return studentAge;
    }

    public static void saveStudentNationality(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_NATIONALITY, name);
        editor.apply();
    }

    public static String getStudentNationality(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentNationality;
        studentNationality = sharedPreferences.getString(EnsyfiConstants.STUDENT_NATIONALITY, "");
        return studentNationality;
    }

    public static void saveStudentReligion(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_RELIGION, name);
        editor.apply();
    }

    public static String getStudentReligion(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentReligion;
        studentReligion = sharedPreferences.getString(EnsyfiConstants.STUDENT_RELIGION, "");
        return studentReligion;
    }
    public static void saveStudentCaste(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_CASTE, name);
        editor.apply();
    }

    public static String getStudentCaste(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentCaste;
        studentCaste = sharedPreferences.getString(EnsyfiConstants.STUDENT_CASTE, "");
        return studentCaste;
    }

    public static void saveStudentCommunity(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_COMMUNITY, name);
        editor.apply();
    }

    public static String getStudentCommunity(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentCommunity;
        studentCommunity = sharedPreferences.getString(EnsyfiConstants.STUDENT_COMMUNITY, "");
        return studentCommunity;
    }

    public static void saveStudentParentOrGuardian(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_PARENT_OR_GUARDIAN, name);
        editor.apply();
    }

    public static String getStudentParentOrGuardian(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentParentOrGuardian;
        studentParentOrGuardian = sharedPreferences.getString(EnsyfiConstants.STUDENT_PARENT_OR_GUARDIAN, "");
        return studentParentOrGuardian;
    }

    public static void saveStudentParentOrGuardianID(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_PARENT_OR_GUARDIAN_ID, name);
        editor.apply();
    }

    public static String getStudentParentOrGuardianID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentParentOrGuardianID;
        studentParentOrGuardianID = sharedPreferences.getString(EnsyfiConstants.STUDENT_PARENT_OR_GUARDIAN_ID, "");
        return studentParentOrGuardianID;
    }

    public static void saveStudentMotherTongue(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_MOTHER_TONGUE, name);
        editor.apply();
    }

    public static String getStudentMotherTongue(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentMotherTongue;
        studentMotherTongue = sharedPreferences.getString(EnsyfiConstants.STUDENT_MOTHER_TONGUE, "");
        return studentMotherTongue;
    }

    public static void saveStudentLanguage(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_LANGUAGE, name);
        editor.apply();
    }

    public static String getStudentLanguage(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentLanguage;
        studentLanguage = sharedPreferences.getString(EnsyfiConstants.STUDENT_LANGUAGE, "");
        return studentLanguage;
    }

    public static void saveStudentMobile(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_MOBILE, name);
        editor.apply();
    }

    public static String getStudentMobile(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentMobile;
        studentMobile = sharedPreferences.getString(EnsyfiConstants.STUDENT_MOBILE, "");
        return studentMobile;
    }

    public static void saveStudentSecondaryMobile(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_SECONDARY_MOBILE, name);
        editor.apply();
    }

    public static String getStudentSecondaryMobile(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentSecondaryMobile;
        studentSecondaryMobile = sharedPreferences.getString(EnsyfiConstants.STUDENT_SECONDARY_MOBILE, "");
        return studentSecondaryMobile;
    }

    public static void saveStudentMail(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_MAIL, name);
        editor.apply();
    }

    public static String getStudentMail(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentMail;
        studentMail = sharedPreferences.getString(EnsyfiConstants.STUDENT_MAIL, "");
        return studentMail;
    }

    public static void saveStudentSecondaryMail(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_SECONDAR_MAIL, name);
        editor.apply();
    }

    public static String getStudentSecondaryMail(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentSecondaryMail;
        studentSecondaryMail = sharedPreferences.getString(EnsyfiConstants.STUDENT_SECONDAR_MAIL, "");
        return studentSecondaryMail;
    }

    public static void saveStudentImg(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_IMAGE, name);
        editor.apply();
    }

    public static String getStudentImg(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentImg;
        studentImg = sharedPreferences.getString(EnsyfiConstants.STUDENT_IMAGE, "");
        return studentImg;
    }

    public static void saveStudentPreviousSchool(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_PREVIOUS_SCHOOL, name);
        editor.apply();
    }

    public static String getStudentPreviousSchool(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPreviousSchool;
        studentPreviousSchool = sharedPreferences.getString(EnsyfiConstants.STUDENT_PREVIOUS_SCHOOL, "");
        return studentPreviousSchool;
    }

    public static void saveStudentPreviousClass(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_PREVIOUS_CLASS, name);
        editor.apply();
    }

    public static String getStudentPreviousClass(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPreviousClass;
        studentPreviousClass = sharedPreferences.getString(EnsyfiConstants.STUDENT_PREVIOUS_CLASS, "");
        return studentPreviousClass;
    }

    public static void saveStudentPromotionStatus(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_PROMOTION_STATUS, name);
        editor.apply();
    }

    public static String getStudentPromotionStatus(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPromotionStatus;
        studentPromotionStatus = sharedPreferences.getString(EnsyfiConstants.STUDENT_PROMOTION_STATUS, "");
        return studentPromotionStatus;
    }

    public static void saveStudentTransferCertificate(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_TRANSFER_CERTIFICATE, name);
        editor.apply();
    }

    public static String getStudentTransferCertificate(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentTransferCertificate;
        studentTransferCertificate = sharedPreferences.getString(EnsyfiConstants.STUDENT_TRANSFER_CERTIFICATE, "");
        return studentTransferCertificate;
    }

    public static void saveStudentRecordSheet(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_RECORD_SHEET, name);
        editor.apply();
    }

    public static String getStudentRecordSheet(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentRecordSheet;
        studentRecordSheet = sharedPreferences.getString(EnsyfiConstants.STUDENT_RECORD_SHEET, "");
        return studentRecordSheet;
    }

    public static void saveStudentStatus(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_STATUS, name);
        editor.apply();
    }

    public static String getStudentStatus(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentStatus;
        studentStatus = sharedPreferences.getString(EnsyfiConstants.STUDENT_STATUS, "");
        return studentStatus;
    }

    public static void saveStudentParentStatus(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_PARENT_STATUS, name);
        editor.apply();
    }

    public static String getStudentParentStatus(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentParentStatus;
        studentParentStatus = sharedPreferences.getString(EnsyfiConstants.STUDENT_PARENT_STATUS, "");
        return studentParentStatus;
    }

    public static void saveStudentRegistered(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_REGISTERED, name);
        editor.apply();
    }

    public static String getStudentRegistered(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentRegistered;
        studentRegistered = sharedPreferences.getString(EnsyfiConstants.STUDENT_REGISTERED, "");
        return studentRegistered;
    }


    /////////       TEACHER DETAILS         /////////

    public static void saveTeacherId(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_ID, name);
        editor.apply();
    }

    public static String getTeacherId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherId;
        teacherId = sharedPreferences.getString(EnsyfiConstants.TEACHER_ID, "");
        return teacherId;
    }

    public static void saveTeacherUserId(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_USER_ID, name);
        editor.apply();
    }

    public static String getTeacherUserId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherId;
        teacherId = sharedPreferences.getString(EnsyfiConstants.KEY_USER_ID, "");
        return teacherId;
    }

    public static void saveTeacherName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_NAME, name);
        editor.apply();
    }

    public static String getTeacherName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherName;
        teacherName = sharedPreferences.getString(EnsyfiConstants.TEACHER_NAME, "");
        return teacherName;
    }

    public static void saveTeacherGender(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_GENDER, name);
        editor.apply();
    }

    public static String getTeacherGender(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherGender ;
        teacherGender = sharedPreferences.getString(EnsyfiConstants.TEACHER_GENDER, "");
        return teacherGender ;
    }

    public static void saveTeacherAge(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_AGE, name);
        editor.apply();
    }

    public static String getTeacherAge(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherAge;
        teacherAge = sharedPreferences.getString(EnsyfiConstants.TEACHER_AGE, "");
        return teacherAge;
    }

    public static void saveTeacherNationality(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_NATIONALITY, name);
        editor.apply();
    }

    public static String getTeacherNationality(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherNationality;
        teacherNationality = sharedPreferences.getString(EnsyfiConstants.TEACHER_NATIONALITY, "");
        return teacherNationality;
    }

    public static void saveTeacherReligion(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_RELIGION, name);
        editor.apply();
    }

    public static String getTeacherReligion(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherReligion;
        teacherReligion = sharedPreferences.getString(EnsyfiConstants.TEACHER_RELIGION, "");
        return teacherReligion;
    }

    public static void saveTeacherCaste(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_CASTE, name);
        editor.apply();
    }

    public static String getTeacherCaste(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherCaste;
        teacherCaste = sharedPreferences.getString(EnsyfiConstants.TEACHER_CASTE, "");
        return teacherCaste;
    }

    public static void saveTeacherCommunity(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_COMMUNITY, name);
        editor.apply();
    }

    public static String getTeacherCommunity(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherCommunity;
        teacherCommunity = sharedPreferences.getString(EnsyfiConstants.TEACHER_COMMUNITY, "");
        return teacherCommunity;
    }

    public static void saveTeacherAddress(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_ADDRESS, name);
        editor.apply();
    }

    public static String getTeacherAddress(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherAddress;
        teacherAddress = sharedPreferences.getString(EnsyfiConstants.TEACHER_ADDRESS, "");
        return teacherAddress;
    }

    public static void saveTeacherMobile(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_MOBILE, name);
        editor.apply();
    }

    public static String getTeacherMobile(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherMobile;
        teacherMobile = sharedPreferences.getString(EnsyfiConstants.TEACHER_MOBILE, "");
        return teacherMobile;
    }

    public static void saveTeacherMail(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_MAIL, name);
        editor.apply();
    }

    public static String getTeacherMail(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherMail;
        teacherMail = sharedPreferences.getString(EnsyfiConstants.TEACHER_MAIL, "");
        return teacherMail;
    }
    public static void saveTeacherSecondaryMobile(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_SEC_MOBILE, name);
        editor.apply();
    }

    public static String getTeacherSecondaryMobile(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherSecondaryMobile;
        teacherSecondaryMobile = sharedPreferences.getString(EnsyfiConstants.TEACHER_SEC_MOBILE, "");
        return teacherSecondaryMobile;
    }

    public static void saveTeacherSecondaryMail(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_SEC_MAIL, name);
        editor.apply();
    }

    public static String getTeacherSecondaryMail(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherSecondaryMail;
        teacherSecondaryMail = sharedPreferences.getString(EnsyfiConstants.TEACHER_SEC_MAIL, "");
        return teacherSecondaryMail;
    }

    public static void saveTeacherPic(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_IMAGE, name);
        editor.apply();
    }

    public static String getTeacherPic(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherPic;
        teacherPic = sharedPreferences.getString(EnsyfiConstants.TEACHER_IMAGE, "");
        return teacherPic;
    }

    public static void saveTeacherSubject(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_SUBJECT, name);
        editor.apply();
    }

    public static String getTeacherSubject(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherSubject ;
        teacherSubject = sharedPreferences.getString(EnsyfiConstants.TEACHER_SUBJECT, "");
        return teacherSubject ;
    }

    public static void saveTeacherClassTaken(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_CLASS_TAKEN, name);
        editor.apply();
    }

    public static String getTeacherClassTaken(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherClassTaken ;
        teacherClassTaken = sharedPreferences.getString(EnsyfiConstants.TEACHER_CLASS_TAKEN, "");
        return teacherClassTaken ;
    }

    public static void saveTeacherSkillSet(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_SKILL_SET, name);
        editor.apply();
    }

    public static String getTeacherSkillSet(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherSkillSet;
        teacherSkillSet = sharedPreferences.getString(EnsyfiConstants.TEACHER_SKILL_SET, "");
        return teacherSkillSet ;
    }

    public static void saveTeacherPreviousInstitute(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_PREVIOUS_INSTITUTE, name);
        editor.apply();
    }

    public static String getTeacherPreviousInstitute(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherSkillSet;
        teacherSkillSet = sharedPreferences.getString(EnsyfiConstants.TEACHER_PREVIOUS_INSTITUTE, "");
        return teacherSkillSet ;
    }

    public static void saveTeacherTotalExperience(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_TOTAL_EXP, name);
        editor.apply();
    }

    public static String getTeacherTotalExperience(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherSkillSet;
        teacherSkillSet = sharedPreferences.getString(EnsyfiConstants.TEACHER_TOTAL_EXP, "");
        return teacherSkillSet ;
    }

    public static void saveTeacherSubjectName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_SUBJECT_NAME, name);
        editor.apply();
    }

    public static String getTeacherSubjectName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherSubject ;
        teacherSubject = sharedPreferences.getString(EnsyfiConstants.TEACHER_SUBJECT_NAME, "");
        return teacherSubject ;
    }

    public static void saveTeacherSectionName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_SECTION, name);
        editor.apply();
    }

    public static String getTeacherSectionName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherSectionName ;
        teacherSectionName = sharedPreferences.getString(EnsyfiConstants.TEACHER_SECTION, "");
        return teacherSectionName ;
    }

    public static void saveTeacherClassName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_CLASS_NAME, name);
        editor.apply();
    }

    public static String getTeacherClassName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherClassName;
        teacherClassName = sharedPreferences.getString(EnsyfiConstants.TEACHER_CLASS_NAME, "");
        return teacherClassName;
    }

    public static void saveClassTeacher(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_CLASS_TEACHER, name);
        editor.apply();
    }

    public static String getClassTeacher(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String classTeacher;
        classTeacher = sharedPreferences.getString(EnsyfiConstants.TEACHER_CLASS_TEACHER, "");
        return classTeacher;
    }

    public static void saveGCM(Context context, String gcmId) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_GCM_ID, gcmId);
        editor.commit();
    }

    public static String getGCM(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String userGCMId = sharedPreferences.getString(EnsyfiConstants.KEY_GCM_ID, "");
        return userGCMId;
    }

    public static void saveIMEI(Context context, String imei) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_IMEI, imei);
        editor.commit();
    }

    public static String getIMEI(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String userGCMId = sharedPreferences.getString(EnsyfiConstants.KEY_IMEI, "");
        return userGCMId;
    }
}
