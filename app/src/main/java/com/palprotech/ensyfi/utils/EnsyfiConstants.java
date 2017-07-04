package com.palprotech.ensyfi.utils;

/**
 * Created by Admin on 27-06-2017.
 */

public class EnsyfiConstants {

    // URLS
    // BASE URL
    public static final String BASE_URL = "http://happysanz.net/";

    // ADMIN URL
    private static final String ADMIN_BASE_URL = BASE_URL + "admin/admin_api/";
    private static final String ADMIN_BASE_API = "api.php";
    public static final String INSTITUTE_LOGIN_API = ADMIN_BASE_URL + ADMIN_BASE_API;
    public static final String GET_SCHOOL_LOGO = BASE_URL + "institute_logo/";

    // GENERAL URL
    // USERS URL
    public static final String USER_LOGIN_API = "/apimain/login/";
    public static final String USER_IMAGE_API_PARENTS = "/assets/parents/profile/";
    public static final String USER_IMAGE_API_STUDENTS = "/assets/students/profile/";
    public static final String USER_IMAGE_API_TEACHERS = "/assets/teachers/profile/";
    public static final String USER_IMAGE_API_ADMIN = "/assets/admin/profile/";

    //FORGOT PASSWORD URL
    public static final String FORGOT_PASSWORD = "/apimain/forgot_Password/";
    public static final String RESET_PASSWORD = "/apimain/reset_Password/";
    public static final String CHANGE_PASSWORD = "/apimain/change_Password/";

    // EVENTS URL
    public static final String GET_EVENTS_API = "/apimain/disp_Events/";

    //EVENT ORGANISER URL
    public static final String GET_EVENT_ORGANISER_API = "/apimain/disp_subEvents/";

    // COMMUNICATION URL
    public static final String GET_COMMUNICATION_API = "/apistudent/disp_Communication/";

    // STUDENT & PARENTS URL
    // STUDENT ATTENDANCE URL
    public static final String GET_STUDENT_ATTENDANCD_API = "/apistudent/disp_Attendence/";

    // CLASS TEST & HOMEWORK URL
    public static final String GET_STUDENT_CLASSTEST_AND_HOMEWORK_API = "/apistudent/disp_Homework/";
    public static final String GET_STUDENT_CLASSTEST_MARK_API = "/apistudent/disp_Ctestmarks/";

    // EXAM & RESULT URL
    public static final String GET_EXAM_API = "/apistudent/disp_Exams/";
    public static final String GET_EXAM_DETAIL_API = "/apistudent/disp_Examdetails/";
    public static final String GET_EXAM_MARK_API = "/apistudent/disp_Exammarks/";

    // STUDENT TIMETABLE URL
    public static final String GET_STUDENT_TIME_TABLE_API = "/apistudent/disp_Timetable/";

    //TEACHER'S URL



    // PARAMETERS
    //Service Params
    public static String PARAM_MESSAGE = "msg";

    // Admin login params
    public static final String PARAMS_FUNC_NAME = "func_name";
    public static final String SIGN_IN_PARAMS_FUNC_NAME = "chkInstid";
    public static final String PARAMS_INSTITUTE_ID = "InstituteID";

    // User login params
    public static final String PARAMS_USER_NAME = "username";
    public static final String PARAMS_PASSWORD = "password";

    // Forgot Password
    public static final String PARAMS_FP_USER_NAME = "user_name";
    public static final String PARAMS_FP_USER_ID = "user_id";

    // Change Password
    public static final String PARAMS_CP_CURRENT_PASSWORD = "old_password";

    // Alert Dialog Constants
    public static String ALERT_DIALOG_TITLE = "alertDialogTitle";
    public static String ALERT_DIALOG_MESSAGE = "alertDialogMessage";
    public static String ALERT_DIALOG_TAG = "alertDialogTag";
    public static String ALERT_DIALOG_INPUT_HINT = "alert_dialog_input_hint";
    public static String ALERT_DIALOG_POS_BUTTON = "alert_dialog_pos_button";
    public static String ALERT_DIALOG_NEG_BUTTON = "alert_dialog_neg_button";

    // Preferences
    // Institute Login Preferences
    public static final String KEY_INSTITUTE_ID = "institute_id";
    public static final String KEY_INSTITUTE_NAME = "institute_name";
    public static final String KEY_INSTITUTE_CODE = "institute_code";
    public static final String KEY_INSTITUTE_LOGO = "institute_logo";

    // User Login Preferences
    public static final String KEY_USER_DYNAMIC_API = "user_dynamic_api";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_USER_IMAGE = "user_pic";
    public static final String KEY_USER_TYPE = "user_type";
    public static final String KEY_USER_TYPE_NAME = "user_type_name";
    public static final String KEY_FORGOT_PASSWORD_STATUS = "forgot_password_status";

    // Student Preferences Data
    public static final String KEY_STUDENT_ENROLL_ID_PREFERENCES = "student_enroll_id";
    public static final String KEY_STUDENT_ADMISSION_ID_PREFERENCES = "student_admission_id";
    public static final String KEY_STUDENT_ADMISSION_NO_PREFERENCES = "student_admission_no";
    public static final String KEY_STUDENT_CLASS_ID_PREFERENCES = "student_class_id";
    public static final String KEY_STUDENT_NAME_PREFERENCES = "student_name";
    public static final String KEY_STUDENT_CLASS_NAME_PREFERENCES = "student_class_name";
    public static final String KEY_STUDENT_SECTION_NAME_PREFERENCES = "student_section_name";

    // Get Student Time table
    public static final String PARAMS_CLASS_ID = "class_id";


    //User Profile details

    //Student Details
    public static final String STUDENT_ADMISSION_ID = "stud_admission_id";
    public static final String STUDENT_ADMISSION_YEAR = "stud_admisn_year";
    public static final String STUDENT_ADMISSION_NUMBER = "stud_admisn_no";
    public static final String STUDENT_EMSI_NUMBER = "stud_emsi_num";
    public static final String STUDENT_ADMISSION_DATE = "stud_admisn_date";
    public static final String STUDENT_NAME = "stud_name";
    public static final String STUDENT_GENDER = "stud_sex";
    public static final String STUDENT_DATE_OF_BIRTH = "stud_dob";
    public static final String STUDENT_AGE = "stud_age";
    public static final String STUDENT_NATIONALITY = "stud_nationality";
    public static final String STUDENT_RELIGION = "stud_religion";
    public static final String STUDENT_CASTE = "stud_community_class";
    public static final String STUDENT_COMMUNITY = "stud_community";
    public static final String STUDENT_PARENT_OR_GUARDIAN= "stud_parnt_guardn";
    public static final String STUDENT_PARENT_OR_GUARDIAN_ID = "stud_parnt_guardn_id";
    public static final String STUDENT_MOTHER_TONGUE = "stud_mother_tongue";
    public static final String STUDENT_LANGUAGE = "stud_language";
    public static final String STUDENT_MOBILE = "stud_mobile";
    public static final String STUDENT_SECONDARY_MOBILE = "stud_sec_mobile";
    public static final String STUDENT_MAIL = "stud_email";
    public static final String STUDENT_SECONDAR_MAIL = "stud_sec_email";
    public static final String STUDENT_IMAGE = "stud_student_pic";
    public static final String STUDENT_PREVIOUS_SCHOOL = "stud_last_sch_name";
    public static final String STUDENT_PREVIOUS_CLASS = "stud_last_studied";
    public static final String STUDENT_PROMOTION_STATUS = "stud_qualified_promotion";
    public static final String STUDENT_TRANSFER_CERTIFICATE = "stud_transfer_certificate";
    public static final String STUDENT_RECORD_SHEET = "stud_record_sheet";
    public static final String STUDENT_STATUS = "stud_status";
    public static final String STUDENT_PARENT_STATUS = "stud_parents_status";
    public static final String STUDENT_REGISTERED = "stud_enrollment";

    //Father Details
    public static final String FATHER_ID = "father_id";
    public static final String FATHER_NAME = "father_name";
    public static final String FATHER_OCCUPATION = "father_occupation";
    public static final String FATHER_INCOME = "father_income";
    public static final String FATHER_ADDRESS = "father_home_address";
    public static final String FATHER_EMAIL = "father_email";
    public static final String FATHER_HOME_PHONE = "father_home_phone";
    public static final String FATHER_OFFICE_PHONE = "father_office_phone";
    public static final String FATHER_MOBILE = "father_mobile";
    public static final String FATHER_RELATIONSHIP = "father_relationship";
    public static final String FATHER_IMAGE = "father_user_pic";

    //Mother details
    public static final String MOTHER_ID = "mother_id";
    public static final String MOTHER_NAME = "mother_name";
    public static final String MOTHER_OCCUPATION = "mother_occupation";
    public static final String MOTHER_INCOME = "mother_income";
    public static final String MOTHER_ADDRESS = "mother_home_address";
    public static final String MOTHER_EMAIL = "mother_email";
    public static final String MOTHER_HOME_PHONE = "mother_home_phone";
    public static final String MOTHER_OFFICE_PHONE = "mother_office_phone";
    public static final String MOTHER_MOBILE = "mother_mobile";
    public static final String MOTHER_RELATIONSHIP = "mother_relationship";
    public static final String MOTHER_IMAGE = "mother_user_pic";


    //Guardian details
    public static final String GUARDIAN_ID = "guardian_id";
    public static final String GUARDIAN_NAME = "guardian_name";
    public static final String GUARDIAN_OCCUPATION = "guardian_occupation";
    public static final String GUARDIAN_INCOME = "guardian_income";
    public static final String GUARDIAN_ADDRESS = "guardian_home_address";
    public static final String GUARDIAN_EMAIL = "guardian_email";
    public static final String GUARDIAN_HOME_PHONE = "guardian_home_phone";
    public static final String GUARDIAN_OFFICE_PHONE = "guardian_office_phone";
    public static final String GUARDIAN_MOBILE = "guardian_mobile";
    public static final String GUARDIAN_RELATIONSHIP = "guardian_relationship";
    public static final String GUARDIAN_IMAGE = "guardian_user_pic";

    //Class Test & Homework
    public static final String PARAM_CLASS_ID = "class_id";
    public static final String PARAM_HOMEWORK_ID = "hw_id";
    public static final String PARM_ENROLL_ID = "entroll_id";
    public static final String PARM_HOME_WORK_TYPE = "hw_type";

    //Exam
    public static final String PARAM_EXAM_ID = "exam_id";
    public static final String PARAM_STUDENT_ID = "stud_id";

    //Event Organiser
    public static final String PARAM_EVENT_ID = "event_id";
}
