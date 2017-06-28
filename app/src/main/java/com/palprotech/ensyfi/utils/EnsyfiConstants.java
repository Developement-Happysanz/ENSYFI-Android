package com.palprotech.ensyfi.utils;

/**
 * Created by Admin on 27-06-2017.
 */

public class EnsyfiConstants {

    // URLS
    // BASE URL
    public static final String BASE_URL = "http://happysanz.net/";

    // ADMIN URL
    public static final String ADMIN_BASE_URL = BASE_URL + "admin/admin_api/";
    public static final String ADMIN_BASE_API = "api.php";
    public static final String INSTITUTE_LOGIN_API = ADMIN_BASE_URL + ADMIN_BASE_API;
    public static final String GET_SCHOOL_LOGO = BASE_URL + "institute_logo/";

    // USERS URL
    public static final String USER_LOGIN_API = "/apimain/login/";
    public static final String USER_IMAGE_API = "/assets/parents/profile/";

    //FORGOT PASSWORD URL
    public static final String FORGOT_PASSWORD = "/api/forgot_password/";
    public static final String RESET_PASSWORD = "/api/reset_Password/";
    public static final String CHANGE_PASSWORD = "/api/change_Password/";

    // STUDENT ATTENDANCE URL

    public static final String GET_STUDENT_ATTENDANCD_API = "/api/disp_Attendence/";

    // STUDENT TIMETABLE URL
    public static final String GET_STUDENT_TIME_TABLE_API = "/api/stud_timetable/";

    // CLASS TEST & HOMEWORK URL
    public static final String GET_STUDENT_CLASSTEST_AND_HOMEWORK_API = "/api/disp_Homework/";
    public static final String GET_STUDENT_CLASSTEST_MARK_API = "/api/disp_Ctestmarks/";

    // EXAM & RESULT URL
    public static final String GET_EXAM_API = "/api/disp_Exams/";
    public static final String GET_EXAM_DETAIL_API = "/api/disp_Examdetails/";
    public static final String GET_EXAM_MARK_API = "/api/disp_Exammarks/";

    // EVENTS URL
    public static final String GET_EVENTS_API = "/api/disp_Events/";

    //EVENT ORGANISER URL
    public static final String GET_EVENT_ORGANISER_API = "/api/disp_subEvents/";

    // COMMUNICATION URL
    public static final String GET_COMMUNICATION_API = "/api/disp_ParentCommunication/";

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
    public static final String PARENT_ID = "parent_id";
    public static final String FATHER_NAME = "father_name";
    public static final String MOTHER_NAME = "mother_name";
    public static final String GUARDN_NAME = "guardn_name";
    public static final String OCCUPATION = "occupation";
    public static final String ADDRESS = "address";
    public static final String EMAIL = "email";
    public static final String HOME_PHONE = "home_phone";
    public static final String OFFICE_PHONE = "office_phone";
    public static final String MOBILE_ONE = "mobile";
    public static final String MOBILE_TWO = "mobile1";
    public static final String FATHER_IMAGE = "father_pic";
    public static final String MOTHER_IMAGE = "mother_pic";
    public static final String GUARDN_IMAGE = "guardn_pic";

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
