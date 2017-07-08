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
        editor.commit();
    }

    public static String getInstituteId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String instituteId = sharedPreferences.getString(EnsyfiConstants.KEY_INSTITUTE_ID, "");
        return instituteId;
    }

    // InstituteName
    public static void saveInstituteName(Context context, String instituteName) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_INSTITUTE_NAME, instituteName);
        editor.commit();
    }

    public static String getInstituteName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String instituteName = sharedPreferences.getString(EnsyfiConstants.KEY_INSTITUTE_NAME, "");
        return instituteName;
    }

    // InstituteCode
    public static void saveInstituteCode(Context context, String instituteCode) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_INSTITUTE_CODE, instituteCode);
        editor.commit();
    }

    public static String getInstituteCode(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String instituteCode = sharedPreferences.getString(EnsyfiConstants.KEY_INSTITUTE_CODE, "");
        return instituteCode;
    }

    // InstituteLogoPic
    public static void saveInstituteLogoPic(Context context, String url) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_INSTITUTE_LOGO, url);
        editor.commit();

    }

    public static String getInstituteLogoPicUrl(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String url = sharedPreferences.getString(EnsyfiConstants.KEY_INSTITUTE_LOGO, "");
        return url;

    }

    // User Login Preferences
    // User Dynamic API
    public static void saveUserDynamicAPI(Context context, String userDynamicAPI) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_USER_DYNAMIC_API, userDynamicAPI);
        editor.commit();
    }

    public static String getUserDynamicAPI(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String userDynamicAPI = sharedPreferences.getString(EnsyfiConstants.KEY_USER_DYNAMIC_API, "");
        return userDynamicAPI;
    }

    // UserId
    public static void saveUserId(Context context, String userId) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_USER_ID, userId);
        editor.commit();
    }

    public static String getUserId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String userId = sharedPreferences.getString(EnsyfiConstants.KEY_USER_ID, "");
        return userId;
    }

    // Name
    public static void saveName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_NAME, name);
        editor.commit();
    }

    public static String getName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString(EnsyfiConstants.KEY_NAME, "");
        return name;
    }

    // User Name
    public static void saveUserName(Context context, String userName) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String userName = sharedPreferences.getString(EnsyfiConstants.KEY_USER_NAME, "");
        return userName;
    }

    // User Image
    public static void saveUserPicture(Context context, String userPicture) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_USER_IMAGE, userPicture);
        editor.commit();
    }

    public static String getUserPicture(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String userPicture = sharedPreferences.getString(EnsyfiConstants.KEY_USER_IMAGE, "");
        return userPicture;
    }

    // User Type
    public static void saveUserType(Context context, String userType) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_USER_TYPE, userType);
        editor.commit();
    }

    public static String getUserType(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String userType = sharedPreferences.getString(EnsyfiConstants.KEY_USER_TYPE, "");
        return userType;
    }

    // User Type Name
    public static void saveUserTypeName(Context context, String userTypeName) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_USER_TYPE_NAME, userTypeName);
        editor.commit();
    }

    public static String getUserTypeName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String userTypeName = sharedPreferences.getString(EnsyfiConstants.KEY_USER_TYPE_NAME, "");
        return userTypeName;
    }

    // Academic Year
    public static void saveAcademicYearId(Context context, String academicYearId) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_ACADEMIC_YEAR_ID, academicYearId);
        editor.commit();
    }

    public static String getAcademicYearId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String academicYearId = sharedPreferences.getString(EnsyfiConstants.KEY_ACADEMIC_YEAR_ID, "");
        return academicYearId;
    }

    // Student Preference Data
    // Get Student Enroll Id
    public static void saveStudentRegisteredIdPreference(Context context, String studentPrefEnrollID) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_STUDENT_ENROLL_ID_PREFERENCES, studentPrefEnrollID);
        editor.commit();
    }

    public static String getStudentRegisteredIdPreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPrefEnrollID = sharedPreferences.getString(EnsyfiConstants.KEY_STUDENT_ENROLL_ID_PREFERENCES, "");
        return studentPrefEnrollID;
    }

    // Get Student Admission Id
    public static void saveStudentAdmissionIdPreference(Context context, String studentPrefAdmissionID) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_STUDENT_ADMISSION_ID_PREFERENCES, studentPrefAdmissionID);
        editor.commit();
    }

    public static String getStudentAdmissionIdPreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPrefAdmissionID = sharedPreferences.getString(EnsyfiConstants.KEY_STUDENT_ADMISSION_ID_PREFERENCES, "");
        return studentPrefAdmissionID;
    }

    // Get Student Admission No
    public static void saveStudentAdmissionNoPreference(Context context, String studentPrefAdmissionNo) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_STUDENT_ADMISSION_NO_PREFERENCES, studentPrefAdmissionNo);
        editor.commit();
    }

    public static String getStudentAdmissionNoPreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPrefAdmissionNo = sharedPreferences.getString(EnsyfiConstants.KEY_STUDENT_ADMISSION_NO_PREFERENCES, "");
        return studentPrefAdmissionNo;
    }

    // Get Student Class Id
    public static void saveStudentClassIdPreference(Context context, String studentPrefclassId) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_STUDENT_CLASS_ID_PREFERENCES, studentPrefclassId);
        editor.commit();
    }

    public static String getStudentClassIdPreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPrefclassId = sharedPreferences.getString(EnsyfiConstants.KEY_STUDENT_CLASS_ID_PREFERENCES, "");
        return studentPrefclassId;
    }

    // Get Student Name
    public static void saveStudentNamePreference(Context context, String studentPrefName) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_STUDENT_NAME_PREFERENCES, studentPrefName);
        editor.commit();
    }

    public static String getStudentNamePreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPrefName = sharedPreferences.getString(EnsyfiConstants.KEY_STUDENT_NAME_PREFERENCES, "");
        return studentPrefName;
    }

    // Get Student Class Name
    public static void saveStudentClassNamePreference(Context context, String studentPrefClassName) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_STUDENT_CLASS_NAME_PREFERENCES, studentPrefClassName);
        editor.commit();
    }

    public static String getStudentClassNamePreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPrefClassName = sharedPreferences.getString(EnsyfiConstants.KEY_STUDENT_CLASS_NAME_PREFERENCES, "");
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
        String studentPrefSectionName = sharedPreferences.getString(EnsyfiConstants.KEY_STUDENT_SECTION_NAME_PREFERENCES, "");
        return studentPrefSectionName;
    }

    // Forgot Password
    // Forgot Password Status Check
    public static String getForgotPasswordStatus(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String url = sharedPreferences.getString(EnsyfiConstants.KEY_FORGOT_PASSWORD_STATUS, "");
        return url;
    }

    public static void saveForgotPasswordStatus(Context context, String url) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_FORGOT_PASSWORD_STATUS, url);
        editor.commit();
    }

    // Forgot Password Enabled Check
    public static String getForgotPasswordStatusEnable(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String url = sharedPreferences.getString(EnsyfiConstants.KEY_FORGOT_PASSWORD_STATUS, "");
        return url;
    }

    public static void saveForgotPasswordStatusEnable(Context context, String url) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.KEY_FORGOT_PASSWORD_STATUS, url);
        editor.commit();
    }


    ////////        Father Details      ///////////

    public static void saveFatherID(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_ID, name);
        editor.commit();
    }

    public static String getFatherID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherID = sharedPreferences.getString(EnsyfiConstants.FATHER_ID, "");
        return fatherID;
    }

    public static void saveFatherName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_NAME, name);
        editor.commit();
    }

    public static String getFatherName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherName = sharedPreferences.getString(EnsyfiConstants.FATHER_NAME, "");
        return fatherName;
    }

    public static void saveFatherOccupation(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_OCCUPATION, name);
        editor.commit();
    }

    public static String getFatherOccupation(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherOccupation = sharedPreferences.getString(EnsyfiConstants.FATHER_OCCUPATION, "");
        return fatherOccupation;
    }

    public static void saveFatherIncome(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_INCOME, name);
        editor.commit();
    }

    public static String getFatherIncome(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherIncome= sharedPreferences.getString(EnsyfiConstants.FATHER_INCOME, "");
        return fatherIncome;
    }

    public static void saveFatherAddress(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_ADDRESS, name);
        editor.commit();
    }

    public static String getFatherAddress(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherAddress = sharedPreferences.getString(EnsyfiConstants.FATHER_ADDRESS, "");
        return fatherAddress;
    }

    public static void saveFatherEmail(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_EMAIL, name);
        editor.commit();
    }

    public static String getFatherEmail(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherEmail = sharedPreferences.getString(EnsyfiConstants.FATHER_EMAIL, "");
        return fatherEmail;
    }

    public static void saveFatherHomePhone(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_HOME_PHONE, name);
        editor.commit();
    }

    public static String getFatherHomePhone(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherHomePhone = sharedPreferences.getString(EnsyfiConstants.FATHER_HOME_PHONE, "");
        return fatherHomePhone;
    }

    public static void saveFatherOfficePhone(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_OFFICE_PHONE, name);
        editor.commit();
    }

    public static String getFatherOfficePhone(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherOfficePhone = sharedPreferences.getString(EnsyfiConstants.FATHER_OFFICE_PHONE, "");
        return fatherOfficePhone;
    }

    public static void saveFatherMobile(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_MOBILE, name);
        editor.commit();
    }

    public static String getFatherMobile(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherMobile= sharedPreferences.getString(EnsyfiConstants.FATHER_MOBILE, "");
        return fatherMobile;
    }

    public static void saveFatherRelationship(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_RELATIONSHIP, name);
        editor.commit();
    }

    public static String getFatherRelationship(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherRelationship = sharedPreferences.getString(EnsyfiConstants.FATHER_RELATIONSHIP, "");
        return fatherRelationship;
    }

    public static void saveFatherImg(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.FATHER_IMAGE, name);
        editor.commit();
    }

    public static String getFatherImg(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String fatherImg = sharedPreferences.getString(EnsyfiConstants.FATHER_IMAGE, "");
        return fatherImg;
    }



    ////////        Mother Details      ///////////



    public static void saveMotherID(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_ID, name);
        editor.commit();
    }

    public static String getMotherID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherID = sharedPreferences.getString(EnsyfiConstants.MOTHER_ID, "");
        return motherID;
    }

    public static void saveMotherName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_NAME, name);
        editor.commit();
    }

    public static String getMotherName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherName = sharedPreferences.getString(EnsyfiConstants.MOTHER_NAME, "");
        return motherName;
    }

    public static void saveMotherOccupation(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_OCCUPATION, name);
        editor.commit();
    }

    public static String getMotherOccupation(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherOccupation = sharedPreferences.getString(EnsyfiConstants.MOTHER_OCCUPATION, "");
        return motherOccupation;
    }

    public static void saveMotherIncome(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_INCOME, name);
        editor.commit();
    }

    public static String getMotherIncome(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherIncome = sharedPreferences.getString(EnsyfiConstants.MOTHER_INCOME, "");
        return motherIncome;
    }

    public static void saveMotherAddress(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_ADDRESS, name);
        editor.commit();
    }

    public static String getMotherAddress(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherAddress = sharedPreferences.getString(EnsyfiConstants.MOTHER_ADDRESS, "");
        return motherAddress;
    }

    public static void saveMotherEmail(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_EMAIL, name);
        editor.commit();
    }

    public static String getMotherEmail(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherEmail = sharedPreferences.getString(EnsyfiConstants.MOTHER_EMAIL, "");
        return motherEmail;
    }

    public static void saveMotherHomePhone(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_HOME_PHONE, name);
        editor.commit();
    }

    public static String getMotherHomePhone(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherHomePhone = sharedPreferences.getString(EnsyfiConstants.MOTHER_HOME_PHONE, "");
        return motherHomePhone;
    }

    public static void saveMotherOfficePhone(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_OFFICE_PHONE, name);
        editor.commit();
    }

    public static String getMotherOfficePhone(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherOfficePhone = sharedPreferences.getString(EnsyfiConstants.MOTHER_OFFICE_PHONE, "");
        return motherOfficePhone;
    }

    public static void saveMotherMobile(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_MOBILE, name);
        editor.commit();
    }

    public static String getMotherMobile(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherMobile= sharedPreferences.getString(EnsyfiConstants.MOTHER_MOBILE, "");
        return motherMobile;
    }

    public static void saveMotherRelationship(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_RELATIONSHIP, name);
        editor.commit();
    }

    public static String getMotherRelationship(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherRelationship = sharedPreferences.getString(EnsyfiConstants.MOTHER_RELATIONSHIP, "");
        return motherRelationship;
    }

    public static void saveMotherImg(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.MOTHER_IMAGE, name);
        editor.commit();
    }

    public static String getMotherImg(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String motherImg = sharedPreferences.getString(EnsyfiConstants.MOTHER_IMAGE, "");
        return motherImg;
    }



    ////////        Guardian Details      ///////////



    public static void saveGuardianID(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_ID, name);
        editor.commit();
    }

    public static String getGuardianID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianID = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_ID, "");
        return guardianID;
    }

    public static void saveGuardianName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_NAME, name);
        editor.commit();
    }

    public static String getGuardianName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianName = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_NAME, "");
        return guardianName;
    }

    public static void saveGuardianOccupation(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_OCCUPATION, name);
        editor.commit();
    }

    public static String getGuardianOccupation(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianOccupation = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_OCCUPATION, "");
        return guardianOccupation;
    }

    public static void saveGuardianIncome(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_INCOME, name);
        editor.commit();
    }

    public static String getGuardianIncome(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianIncome = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_INCOME, "");
        return guardianIncome;
    }

    public static void saveGuardianAddress(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_ADDRESS, name);
        editor.commit();
    }

    public static String getGuardianAddress(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianAddress = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_ADDRESS, "");
        return guardianAddress;
    }

    public static void saveGuardianEmail(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_EMAIL, name);
        editor.commit();
    }

    public static String getGuardianEmail(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianEmail = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_EMAIL, "");
        return guardianEmail;
    }

    public static void saveGuardianHomePhone(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_HOME_PHONE, name);
        editor.commit();
    }

    public static String getGuardianHomePhone(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianHomePhone = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_HOME_PHONE, "");
        return guardianHomePhone;
    }

    public static void saveGuardianOfficePhone(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_OFFICE_PHONE, name);
        editor.commit();
    }

    public static String getGuardianOfficePhone(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianOfficePhone = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_OFFICE_PHONE, "");
        return guardianOfficePhone;
    }

    public static void saveGuardianMobile(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_MOBILE, name);
        editor.commit();
    }

    public static String getGuardianMobile(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianMobile= sharedPreferences.getString(EnsyfiConstants.GUARDIAN_MOBILE, "");
        return guardianMobile;
    }

    public static void saveGuardianRelationship(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_RELATIONSHIP, name);
        editor.commit();
    }

    public static String getGuardianRelationship(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianRelationship = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_RELATIONSHIP, "");
        return guardianRelationship;
    }

    public static void saveGuardianImg(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.GUARDIAN_IMAGE, name);
        editor.commit();
    }

    public static String getGuardianImg(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String guardianImg = sharedPreferences.getString(EnsyfiConstants.GUARDIAN_IMAGE, "");
        return guardianImg;
    }

    /////////       STUDENT DETAILS         /////////

    public static void saveStudentAdmissionID(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_ADMISSION_ID, name);
        editor.commit();
    }

    public static String getStudentAdmissionID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentAdmissionID = sharedPreferences.getString(EnsyfiConstants.STUDENT_ADMISSION_ID, "");
        return studentAdmissionID;
    }

    public static void saveStudentAdmissionYear(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_ADMISSION_YEAR, name);
        editor.commit();
    }

    public static String getStudentAdmissionYear(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentAdmissionYear = sharedPreferences.getString(EnsyfiConstants.STUDENT_ADMISSION_YEAR, "");
        return studentAdmissionYear;
    }

    public static void saveStudentAdmissionNumber(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_ADMISSION_NUMBER, name);
        editor.commit();
    }

    public static String getStudentAdmissionNumber(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentAdmissionNumber = sharedPreferences.getString(EnsyfiConstants.STUDENT_ADMISSION_NUMBER, "");
        return studentAdmissionNumber;
    }

    public static void saveStudentEmsiNumber(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_EMSI_NUMBER, name);
        editor.commit();
    }

    public static String getStudentEmsiNumber(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentEmsiNumber= sharedPreferences.getString(EnsyfiConstants.STUDENT_EMSI_NUMBER, "");
        return studentEmsiNumber;
    }

    public static void saveStudentAdmissionDate(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_ADMISSION_DATE, name);
        editor.commit();
    }

    public static String getStudentAdmissionDate(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentAdmissionDate = sharedPreferences.getString(EnsyfiConstants.STUDENT_ADMISSION_DATE, "");
        return studentAdmissionDate;
    }

    public static void saveStudentName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_NAME, name);
        editor.commit();
    }

    public static String getStudentName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentName = sharedPreferences.getString(EnsyfiConstants.STUDENT_NAME, "");
        return studentName;
    }

    public static void saveStudentGender(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_GENDER, name);
        editor.commit();
    }

    public static String getStudentGender(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentGender = sharedPreferences.getString(EnsyfiConstants.STUDENT_GENDER, "");
        return studentGender;
    }

    public static void saveStudentDateOfBirth(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_DATE_OF_BIRTH, name);
        editor.commit();
    }

    public static String getStudentDateOfBirth(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentDateOfBirth = sharedPreferences.getString(EnsyfiConstants.STUDENT_DATE_OF_BIRTH, "");
        return studentDateOfBirth;
    }

    public static void saveStudentAge(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_AGE, name);
        editor.commit();
    }

    public static String getStudentAge(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentAge = sharedPreferences.getString(EnsyfiConstants.STUDENT_AGE, "");
        return studentAge;
    }

    public static void saveStudentNationality(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_NATIONALITY, name);
        editor.commit();
    }

    public static String getStudentNationality(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentNationality = sharedPreferences.getString(EnsyfiConstants.STUDENT_NATIONALITY, "");
        return studentNationality;
    }

    public static void saveStudentReligion(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_RELIGION, name);
        editor.commit();
    }

    public static String getStudentReligion(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentReligion = sharedPreferences.getString(EnsyfiConstants.STUDENT_RELIGION, "");
        return studentReligion;
    }
    public static void saveStudentCaste(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_CASTE, name);
        editor.commit();
    }

    public static String getStudentCaste(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentCaste = sharedPreferences.getString(EnsyfiConstants.STUDENT_CASTE, "");
        return studentCaste;
    }

    public static void saveStudentCommunity(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_COMMUNITY, name);
        editor.commit();
    }

    public static String getStudentCommunity(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentCommunity = sharedPreferences.getString(EnsyfiConstants.STUDENT_COMMUNITY, "");
        return studentCommunity;
    }

    public static void saveStudentParentOrGuardian(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_PARENT_OR_GUARDIAN, name);
        editor.commit();
    }

    public static String getStudentParentOrGuardian(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentParentOrGuardian = sharedPreferences.getString(EnsyfiConstants.STUDENT_PARENT_OR_GUARDIAN, "");
        return studentParentOrGuardian;
    }

    public static void saveStudentParentOrGuardianID(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_PARENT_OR_GUARDIAN_ID, name);
        editor.commit();
    }

    public static String getStudentParentOrGuardianID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentParentOrGuardianID = sharedPreferences.getString(EnsyfiConstants.STUDENT_PARENT_OR_GUARDIAN_ID, "");
        return studentParentOrGuardianID;
    }

    public static void saveStudentMotherTongue(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_MOTHER_TONGUE, name);
        editor.commit();
    }

    public static String getStudentMotherTongue(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentMotherTongue = sharedPreferences.getString(EnsyfiConstants.STUDENT_MOTHER_TONGUE, "");
        return studentMotherTongue;
    }

    public static void saveStudentLanguage(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_LANGUAGE, name);
        editor.commit();
    }

    public static String getStudentLanguage(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentLanguage = sharedPreferences.getString(EnsyfiConstants.STUDENT_LANGUAGE, "");
        return studentLanguage;
    }

    public static void saveStudentMobile(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_MOBILE, name);
        editor.commit();
    }

    public static String getStudentMobile(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentMobile = sharedPreferences.getString(EnsyfiConstants.STUDENT_MOBILE, "");
        return studentMobile;
    }

    public static void saveStudentSecondaryMobile(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_SECONDARY_MOBILE, name);
        editor.commit();
    }

    public static String getStudentSecondaryMobile(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentSecondaryMobile = sharedPreferences.getString(EnsyfiConstants.STUDENT_SECONDARY_MOBILE, "");
        return studentSecondaryMobile;
    }

    public static void saveStudentMail(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_MAIL, name);
        editor.commit();
    }

    public static String getStudentMail(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentMail= sharedPreferences.getString(EnsyfiConstants.STUDENT_MAIL, "");
        return studentMail;
    }

    public static void saveStudentSecondaryMail(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_SECONDAR_MAIL, name);
        editor.commit();
    }

    public static String getStudentSecondaryMail(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentSecondaryMail = sharedPreferences.getString(EnsyfiConstants.STUDENT_SECONDAR_MAIL, "");
        return studentSecondaryMail;
    }

    public static void saveStudentImg(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_IMAGE, name);
        editor.commit();
    }

    public static String getStudentImg(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentImg = sharedPreferences.getString(EnsyfiConstants.STUDENT_IMAGE, "");
        return studentImg;
    }

    public static void saveStudentPreviousSchool(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_PREVIOUS_SCHOOL, name);
        editor.commit();
    }

    public static String getStudentPreviousSchool(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPreviousSchool = sharedPreferences.getString(EnsyfiConstants.STUDENT_PREVIOUS_SCHOOL, "");
        return studentPreviousSchool;
    }

    public static void saveStudentPreviousClass(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_PREVIOUS_CLASS, name);
        editor.commit();
    }

    public static String getStudentPreviousClass(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPreviousClass = sharedPreferences.getString(EnsyfiConstants.STUDENT_PREVIOUS_CLASS, "");
        return studentPreviousClass;
    }

    public static void saveStudentPromotionStatus(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_PROMOTION_STATUS, name);
        editor.commit();
    }

    public static String getStudentPromotionStatus(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentPromotionStatus = sharedPreferences.getString(EnsyfiConstants.STUDENT_PROMOTION_STATUS, "");
        return studentPromotionStatus;
    }

    public static void saveStudentTransferCertificate(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_TRANSFER_CERTIFICATE, name);
        editor.commit();
    }

    public static String getStudentTransferCertificate(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentTransferCertificate = sharedPreferences.getString(EnsyfiConstants.STUDENT_TRANSFER_CERTIFICATE, "");
        return studentTransferCertificate;
    }

    public static void saveStudentRecordSheet(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_RECORD_SHEET, name);
        editor.commit();
    }

    public static String getStudentRecordSheet(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentRecordSheet = sharedPreferences.getString(EnsyfiConstants.STUDENT_RECORD_SHEET, "");
        return studentRecordSheet;
    }

    public static void saveStudentStatus(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_STATUS, name);
        editor.commit();
    }

    public static String getStudentStatus(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentStatus = sharedPreferences.getString(EnsyfiConstants.STUDENT_STATUS, "");
        return studentStatus;
    }

    public static void saveStudentParentStatus(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_PARENT_STATUS, name);
        editor.commit();
    }

    public static String getStudentParentStatus(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentParentStatus = sharedPreferences.getString(EnsyfiConstants.STUDENT_PARENT_STATUS, "");
        return studentParentStatus;
    }

    public static void saveStudentRegistered(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.STUDENT_REGISTERED, name);
        editor.commit();
    }

    public static String getStudentRegistered(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String studentRegistered = sharedPreferences.getString(EnsyfiConstants.STUDENT_REGISTERED, "");
        return studentRegistered;
    }


    /////////       TEACHER DETAILS         /////////

    public static void saveTeacherId(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_ID, name);
        editor.commit();
    }

    public static String getTeacherId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherId = sharedPreferences.getString(EnsyfiConstants.TEACHER_ID, "");
        return teacherId;
    }

    public static void saveTeacherName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_NAME, name);
        editor.commit();
    }

    public static String getTeacherName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherName = sharedPreferences.getString(EnsyfiConstants.TEACHER_NAME, "");
        return teacherName;
    }

    public static void saveTeacherGender(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_GENDER, name);
        editor.commit();
    }

    public static String getTeacherGender(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherGender = sharedPreferences.getString(EnsyfiConstants.TEACHER_GENDER, "");
        return teacherGender ;
    }

    public static void saveTeacherAge(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_AGE, name);
        editor.commit();
    }

    public static String getTeacherAge(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherAge= sharedPreferences.getString(EnsyfiConstants.TEACHER_AGE, "");
        return teacherAge;
    }

    public static void saveTeacherNationality(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_NATIONALITY, name);
        editor.commit();
    }

    public static String getTeacherNationality(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherNationality = sharedPreferences.getString(EnsyfiConstants.TEACHER_NATIONALITY, "");
        return teacherNationality;
    }

    public static void saveTeacherReligion(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_RELIGION, name);
        editor.commit();
    }

    public static String getTeacherReligion(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherReligion = sharedPreferences.getString(EnsyfiConstants.TEACHER_RELIGION, "");
        return teacherReligion;
    }

    public static void saveTeacherCaste(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_CASTE, name);
        editor.commit();
    }

    public static String getTeacherCaste(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherCaste = sharedPreferences.getString(EnsyfiConstants.TEACHER_CASTE, "");
        return teacherCaste;
    }

    public static void saveTeacherCommunity(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_COMMUNITY, name);
        editor.commit();
    }

    public static String getTeacherCommunity(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherCommunity = sharedPreferences.getString(EnsyfiConstants.TEACHER_COMMUNITY, "");
        return teacherCommunity;
    }

    public static void saveTeacherAddress(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_ADDRESS, name);
        editor.commit();
    }

    public static String getTeacherAddress(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherAddress = sharedPreferences.getString(EnsyfiConstants.TEACHER_ADDRESS, "");
        return teacherAddress;
    }

    public static void saveTeacherMobile(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_MOBILE, name);
        editor.commit();
    }

    public static String getTeacherMobile(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherMobile = sharedPreferences.getString(EnsyfiConstants.TEACHER_MOBILE, "");
        return teacherMobile;
    }

    public static void saveTeacherMail(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_MAIL, name);
        editor.commit();
    }

    public static String getTeacherMail(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherMail = sharedPreferences.getString(EnsyfiConstants.TEACHER_MAIL, "");
        return teacherMail;
    }
    public static void saveTeacherSecondaryMobile(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_SEC_MOBILE, name);
        editor.commit();
    }

    public static String getTeacherSecondaryMobile(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherSecondaryMobile = sharedPreferences.getString(EnsyfiConstants.TEACHER_SEC_MOBILE, "");
        return teacherSecondaryMobile;
    }

    public static void saveTeacherSecondaryMail(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_SEC_MAIL, name);
        editor.commit();
    }

    public static String getTeacherSecondaryMail(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherSecondaryMail = sharedPreferences.getString(EnsyfiConstants.TEACHER_SEC_MAIL, "");
        return teacherSecondaryMail;
    }

    public static void saveTeacherPic(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_IMAGE, name);
        editor.commit();
    }

    public static String getTeacherPic(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherPic = sharedPreferences.getString(EnsyfiConstants.TEACHER_IMAGE, "");
        return teacherPic;
    }

    public static void saveTeacherSubject(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_SUBJECT, name);
        editor.commit();
    }

    public static String getTeacherSubject(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherSubject = sharedPreferences.getString(EnsyfiConstants.TEACHER_SUBJECT, "");
        return teacherSubject ;
    }

    public static void saveTeacherClassTaken(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_CLASS_TAKEN, name);
        editor.commit();
    }

    public static String getTeacherClassTaken(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherClassTaken = sharedPreferences.getString(EnsyfiConstants.TEACHER_CLASS_TAKEN, "");
        return teacherClassTaken ;
    }

    public static void saveTeacherSectionName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_SECTION, name);
        editor.commit();
    }

    public static String getTeacherSectionName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherSectionName = sharedPreferences.getString(EnsyfiConstants.TEACHER_SECTION, "");
        return teacherSectionName ;
    }

    public static void saveTeacherClassName(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_CLASS_NAME, name);
        editor.commit();
    }

    public static String getTeacherClassName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String teacherClassName = sharedPreferences.getString(EnsyfiConstants.TEACHER_CLASS_NAME, "");
        return teacherClassName;
    }

    public static void saveClassTeacher(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EnsyfiConstants.TEACHER_CLASS_TEACHER, name);
        editor.commit();
    }

    public static String getClassTeacher(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String classTeacher = sharedPreferences.getString(EnsyfiConstants.TEACHER_CLASS_TEACHER, "");
        return classTeacher;
    }
}
