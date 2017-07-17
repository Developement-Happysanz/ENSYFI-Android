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

    // COMMUNICATION CIRCULAR URL
    public static final String GET_COMMUNICATION_CIRCULAR_API = "/apimain/disp_Circular/";

    // UPLOAD USER PROFILE
    public static final String UPLOAD_PROFILE_IMAGE = "/apimain/update_Profilepic/";

    // FEES STATUS
    public static final String GET_FEES_STATUS = "/apistudent/disp_Fees/";

    // ON DUTY URL
    public static final String GET_ON_DUTY_REQUEST = "/apimain/add_Onduty/";
    public static final String GET_ON_DUTY_VIEW = "/apimain/disp_Onduty/";

    // STUDENT & PARENTS URL
    // STUDENT ATTENDANCE URL
    public static final String GET_STUDENT_ATTENDANCD_API = "/apistudent/disp_Attendence/";
    public static final String GET_STUDENT_ATTENDANCD_MONTH_VIEW_API = "/apiteacher/disp_Monthview/";

    //STUDENT PROFILE URL

    public static final String GET_STUDENT_INFO_DETAILS_API = "/apistudent/showStudentProfile/";

    // CLASS TEST & HOMEWORK URL
    public static final String GET_STUDENT_CLASSTEST_AND_HOMEWORK_API = "/apistudent/disp_Homework/";
    public static final String GET_STUDENT_CLASSTEST_MARK_API = "/apistudent/disp_Ctestmarks/";

    // EXAM & RESULT URL
    public static final String GET_EXAM_API = "/apistudent/disp_Exams/";
    public static final String GET_EXAM_DETAIL_API = "/apistudent/disp_Examdetails/";
    public static final String GET_EXAM_MARK_API = "/apistudent/disp_Exammarks/";

    // STUDENT TIMETABLE URL
    public static final String GET_STUDENT_TIME_TABLE_API = "/apistudent/disp_Timetable/";

    // STUDENT ATTENDANCE URL
    public static final String GET_STUDENT_ATTENDANCE_API = "/apiteacher/disp_Attendence/";

    //TEACHER'S URL
    public static final String GET_TEACHERS_CLASS_ATTENDANCE_API = "/apiteacher/sync_Attendance/";
    public static final String GET_TEACHERS_CLASS_ATTENDANCE_HISTORY_API = "/apiteacher/sync_Attendancehistory/";

    // CLASS TEST & HOMEWORK
    public static final String GET_CLASS_TEST_HOMEWORK_API = "/apiteacher/add_Homework/";
    public static final String GET_CLASS_TEST_MARK_API = "/apiteacher/add_HWmarks/";

    // EXAM & RESULT
    public static final String GET_ACADEMIC_EXAM_MARK_API = "/apiteacher/add_Exammarks/";

    // USER LEAVES
    public static final String GET_USER_LEAVES_API = "/apiteacher/disp_Userleaves/";
    public static final String GET_USER_LEAVES_TYPE_API = "/apiteacher/disp_Leavetype/";
    public static final String GET_USER_LEAVES_APPLY_API = "/apiteacher/add_Userleaves/";

    // ADMIN URLS
    // CLASS LIST URL
    public static final String GET_CLASS_LISTS = "/apiadmin/get_all_classes/";
    public static final String GET_SECTION_LISTS = "/apiadmin/get_all_sections/";
    public static final String GET_STUDENT_LISTS = "/apiadmin/get_all_students_in_classes/";


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

    // Academic Year Id
    public static final String KEY_ACADEMIC_YEAR_ID = "academic_year_id";

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
    public static final String STUDENT_PARENT_OR_GUARDIAN = "stud_parnt_guardn";
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

    //Student Details
    public static final String TEACHER_ID = "teacher_id";
    public static final String TEACHER_NAME = "teacher_name";
    public static final String TEACHER_GENDER = "teacher_sex";
    public static final String TEACHER_AGE = "teacher_age";
    public static final String TEACHER_NATIONALITY = "teacher_nationality";
    public static final String TEACHER_RELIGION = "teacher_religion";
    public static final String TEACHER_CASTE = "teacher_community_class";
    public static final String TEACHER_COMMUNITY = "teacher_community";
    public static final String TEACHER_ADDRESS = "teacher_home_address";
    public static final String TEACHER_MOBILE = "teacher_mobile";
    public static final String TEACHER_MAIL = "teacher_email";
    public static final String TEACHER_SEC_MOBILE = "teacher_sec_mobile";
    public static final String TEACHER_SEC_MAIL = "teacher_sec_email";
    public static final String TEACHER_IMAGE = "teacher_pic";
    public static final String TEACHER_SUBJECT = "teacher_subject";
    public static final String TEACHER_CLASS_TAKEN = "teacher_class_taken";
    public static final String TEACHER_SUBJECT_NAME = "teacher_subject_name";
    public static final String TEACHER_SECTION = "teacher_sec_name";
    public static final String TEACHER_CLASS_NAME = "teacher_class_name";
    public static final String TEACHER_CLASS_TEACHER = "class_teacher";

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

    //Teacher's Class Students Attendance
    public static final String KEY_ATTENDANCE_AC_YEAR = "ac_year";
    public static final String KEY_ATTENDANCE_CLASS_ID = "class_id";
    public static final String KEY_ATTENDANCE_CLASS_TOTAL = "class_total";
    public static final String KEY_ATTENDANCE_NO_OF_PRESSENT = "no_of_present";
    public static final String KEY_ATTENDANCE_NO_OF_ABSENT = "no_of_absent";
    public static final String KEY_ATTENDANCE_PERIOD = "attendence_period";
    public static final String KEY_ATTENDANCE_CREATED_BY = "created_by";
    public static final String KEY_ATTENDANCE_CREATED_AT = "created_at";
    public static final String KEY_ATTENDANCE_STATUS = "status";

    //Teacher's Class Students Attendance History
    public static final String KEY_ATTENDANCE_HISTORY_ATTEND_ID = "attend_id";
    public static final String KEY_ATTENDANCE_HISTORY_CLASS_ID = "class_id";
    public static final String KEY_ATTENDANCE_HISTORY_STUDENT_ID = "student_id";
    public static final String KEY_ATTENDANCE_HISTORY_ABS_DATE = "abs_date";
    public static final String KEY_ATTENDANCE_HISTORY_A_STATUS = "a_status";
    public static final String KEY_ATTENDANCE_HISTORY_ATTEND_PERIOD = "attend_period";
    public static final String KEY_ATTENDANCE_HISTORY_A_VAL = "a_val";
    public static final String KEY_ATTENDANCE_HISTORY_A_TAKEN_BY = "a_taken_by";
    public static final String KEY_ATTENDANCE_HISTORY_CREATED_AT = "created_at";
    public static final String KEY_ATTENDANCE_HISTORY_STATUS = "status";

    //OnDuty Params
    public static final String PARAMS_OD_UESR_TYPE = "user_type";
    public static final String PARAMS_OD_UESR_ID = "user_id";
    public static final String PARAMS_OD_FOR = "od_for";
    public static final String PARAMS_OD_FROM_DATE = "from_date";
    public static final String PARAMS_OD_TO_DATE = "to_date";
    public static final String PARAMS_OD_NOTES = "notes";
    public static final String PARAMS_OD_STATUS = "status";
    public static final String PARAMS_OD_CREATED_BY = "created_by";
    public static final String PARAMS_OD_CREATED_AT = "created_at";

    // Attendance Params
    public static final String PARAMS_DISPLAY_TYPE = "disp_type";
    public static final String PARAMS_DISPLAY_DATE = "disp_date";
    public static final String PARAMS_DISPLAY_MONTH_YEAR = "month_year";

    // Class Test & Homework Params
    public static final String PARAMS_CTHW_CLASS_ID = "class_id";
    public static final String PARAMS_CTHW_TEACHER_ID = "teacher_id";
    public static final String PARAMS_CTHW_HOMEWORK_TYPE = "homeWork_type";
    public static final String PARAMS_CTHW_SUBJECT_ID = "subject_id";
    public static final String PARAMS_CTHW_TITLE = "title";
    public static final String PARAMS_CTHW_TEST_DATE = "test_date";
    public static final String PARAMS_CTHW_DUE_DATE = "due_date";
    public static final String PARAMS_CTHW_HOMEWORK_DETAILS = "homework_details";
    public static final String PARAMS_CTHW_CREATED_BY = "created_by";
    public static final String PARAMS_CTHW_CREATED_AT = "created_at";

    // Class Test Marks Params
    public static final String PARAMS_CTMARKS_HW_SERVER_MASTER_ID = "hw_masterid";
    public static final String PARAMS_CTMARKS_STUDENT_ID = "student_id";
    public static final String PARAMS_CTMARKS_MARKS = "marks";
    public static final String PARAMS_CTMARKS_REMARKS = "remarks";
    public static final String PARAMS_CTMARKS_CREATED_BY = "created_by";
    public static final String PARAMS_CTMARKS_CREATED_AT = "created_at";

    // Academic Exam Marks Params
    public static final String PARAMS_ACADEMIC_EXAM_MARKS_EXAM_ID = "exam_id";
    public static final String PARAMS_ACADEMIC_EXAM_MARKS_TEACHER_ID = "teacher_id";
    public static final String PARAMS_ACADEMIC_EXAM_MARKS_SUBJECT_ID = "subject_id";
    public static final String PARAMS_ACADEMIC_EXAM_MARKS_STUDENT_ID = "stu_id";
    public static final String PARAMS_ACADEMIC_EXAM_MARKS_CLASS_MASTER_ID = "classmaster_id";
    public static final String PARAMS_ACADEMIC_EXAM_MARKS_INTERNAL_MARK = "internal_mark";
    public static final String PARAMS_ACADEMIC_EXAM_MARKS_EXTERNAL_MARK = "external_mark";
    public static final String PARAMS_ACADEMIC_EXAM_MARKS_CREATED_BY = "created_by";

    //Leave Params
    public static final String PARAMS_LEAVE_USER_TYPE = "user_type";
    public static final String PARAMS_LEAVE_USER_ID = "user_id";
    public static final String PARAMS_LEAVE_LEAVE_MASTER_ID = "leave_master_id";
    public static final String PARAMS_LEAVE_LEAVE_TYPE = "leave_type";
    public static final String PARAMS_LEAVE_DATE_FROM = "date_from";
    public static final String PARAMS_LEAVE_DATE_TO = "date_to";
    public static final String PARAMS_LEAVE_FROM_TIME = "fromTime";
    public static final String PARAMS_LEAVE_TO_TIME = "toTime";
    public static final String PARAMS_LEAVE_DESCRIPTION = "description";

    //Class and Section Params
    public static final String PARAMS_CLASS_ID_LIST = "ClassId";
    public static final String PARAMS_SECTION_ID_LIST = "SectionId";
}
