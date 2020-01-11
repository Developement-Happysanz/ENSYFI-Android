package com.palprotech.ensyfi.utils;

/**
 * Created by Admin on 27-06-2017.
 */

public class EnsyfiConstants {

    // URLS
    // BASE URL
    public static final String BASE_URL = "https://demo.ensyfi.com/";
//    public static final String BASE_URL = "https://happysanz.net/";


    // ADMIN URL
    private static final String ADMIN_BASE_URL = BASE_URL + "admin/admin_api/";
//    private static final String ADMIN_BASE_URL = BASE_URL + "inst_code_validation/";
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

    //HOLIDAY LIST URL
    public static final String GET_ALL_HOLIDAY_API = "/apimain/disp_Leaves/";
    public static final String GET_UPCOMING_HOLIDAY_API = "/apimain/disp_upcomingLeaves/";

    //EVENT ORGANISER URL
    public static final String GET_EVENT_ORGANISER_API = "/apimain/disp_subEvents/";

    // COMMUNICATION CIRCULAR URL
    public static final String GET_COMMUNICATION_CIRCULAR_API = "/apimain/disp_Circular/";

    public static final String GET_COMMUNICATION_CIRCULAR_ADMIN_API = "/apiadmin/get_all_circular_view/";

    // UPLOAD USER PROFILE
    public static final String UPLOAD_PROFILE_IMAGE = "/apimain/user_profilepic_upload/";

    // FEES STATUS
    public static final String GET_FEES_STATUS = "/apistudent/disp_Fees/";

    // ON DUTY URL
    public static final String GET_ON_DUTY_REQUEST = "/apimain/add_Onduty/";
    public static final String GET_ON_DUTY_VIEW = "/apimain/disp_Onduty/";

    // GROUP NOTIFICATION URL
    public static final String GET_GROUP_LIST = "/apimain/disp_Grouplist/";
    public static final String SEND_GROUP_MESSAGE = "/apiadmin/group_msg_send/";
    public static final String GET_GROUP_MESSAGE_VIEW = "/apimain/disp_Groupmessage/";

    // GROUP NOTIFICATION CREATION AND UPDATION ADMIN URL
    public static final String GET_ADMIN_GROUP_LIST = "/apiadmin/list_groupmaster/";
    public static final String CREATE_GROUP = "/apiadmin/add_groupmaster/";
    public static final String GET_ADMIN_GROUP_VIEW = "/apiadmin/view_groupmaster/";
    public static final String GET_ADMIN_GROUP_LEAD_TEACHER_VIEW = "/apiadmin/get_allteachersuserid/";
    public static final String UPDATE_GROUP= "/apiadmin/update_groupmaster/";
    public static final String GET_GROUP_MEMBER_ROLES = "/apiadmin/list_roles/";
    public static final String GET_GROUP_CLASS_SECTION = "/apiadmin/list_class_section/";
    public static final String GET_GROUP_MEMBER_STAFF = "/apiadmin/gn_stafflist/";
    public static final String GET_GROUP_MEMBER_STUDENTS = "/apiadmin/gn_studentlist/";
    public static final String SEND_GROUP_MEMBERS = "/apiadmin/add_gn_members/";
    public static final String VIEW_GROUP_MEMBERS = "/apiadmin/list_gn_members/";
    public static final String VIEW_GROUP_MESSAGE_HISTORY = "/apimain/grp_messsage_history/";

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
    public static final String GET_TIME_TABLE_DAYS_API = "/apimain/disp_timetabledays/";
    public static final String GET_TIME_TABLE_API = "/apimain/disp_timetable/";

    // STUDENT ATTENDANCE URL
    public static final String GET_STUDENT_ATTENDANCE_API = "/apiteacher/disp_Attendence/";

    // CLASS TEACHER ATTENDANCE URL
    public static final String GET_CLASS_TEACHER_ATTENDANCE_VIEW = "/apiteacher/disp_Attendence_classteacher/";
    public static final String GET_CLASS_TEACHER_ATTENEE_VIEW = "/apiteacher/list_Studentattend_classteacher/";
    public static final String SEND_ATTENDANCE_VIEW = "/apiteacher/send_attendance_parents/";


    // CLASS TEACHER CLASSTEXT & HOMEWORK URL
    public static final String GET_CLASS_TEACHER_CT_HW_OVERVIEW = "/apiteacher/daywisect_homework/";
    public static final String GET_CLASS_TEACHER_CT_HW_DAYWISE = "/apiteacher/daywisect_allhomework/";
    public static final String GET_CLASS_TEACHER_CT_HW_SEND_ALL = "/apiteacher/send_allhw_parents/";
    public static final String GET_CLASS_TEACHER_CT_HW_SEND_SINGLE = "/apiteacher/send_singlehw_parents/";


    //TEACHER'S URL
    public static final String GET_TEACHERS_CLASS_ATTENDANCE_API = "/apiteacher/sync_Attendance/";
    public static final String GET_TEACHERS_CLASS_ATTENDANCE_HISTORY_API = "/apiteacher/sync_Attendancehistory/";

    // CLASS TEST & HOMEWORK
    public static final String GET_CLASS_TEST_HOMEWORK_API = "/apiteacher/add_Homework/";
    public static final String GET_CLASS_TEST_MARK_API = "/apiteacher/add_HWmarks/";
    public static final String LOAD_STUDENT_CLASSTEST_AND_HOMEWORK = "/apiteacher/reloadHomework/";
    public static final String LOAD_STUDENT_EXAM_AND_EXAM_DETAILS = "/apiteacher/reloadExam/";

    // EXAM & RESULT
    public static final String GET_ACADEMIC_EXAM_MARK_API = "/apiteacher/add_Exammarks/";

    // USER LEAVES
    public static final String GET_USER_LEAVES_API = "/apiteacher/disp_Userleaves/";
    public static final String GET_USER_LEAVES_TYPE_API = "/apiteacher/disp_Leavetype/";
    public static final String GET_USER_LEAVES_APPLY_API = "/apiteacher/add_Userleaves/";

    public static final String GET_USER_LEAVES_STATUS_ADMIN_API = "/apiadmin/get_teachers_leaves/";
    public static final String APPROVE_LEAVES_API = "/apiadmin/update_teachers_leaves/";

    //OD Admin urls
    public static final String GET_OD_STUDENT_API = "/apiadmin/get_students_od_view/";
    public static final String GET_OD_TEACHER_API = "/apiadmin/get_teachers_od_view/";
    public static final String APPROVE_OD_API = "/apiadmin/update_teachers_od/";

    // ADMIN URLS
    // CLASS LIST URL
    public static final String GET_CLASS_LISTS = "/apiadmin/get_all_classes/";
    public static final String GET_SECTION_LISTS = "/apiadmin/get_all_sections/";
    public static final String GET_STUDENT_LISTS = "/apiadmin/get_all_students_in_classes/";

    public static final String GET_BOARD_MEMBER_LIST = "/apiadmin/get_all_board_members/";

    public static final String GET_STUDENT_INFO = "/apiadmin/get_student_details/";

    public static final String GET_TEACHERS_LIST = "/apiadmin/get_all_teachers/";

    public static final String GET_TEACHERS_INFO = "/apiadmin/get_teacher_class_details/";

    public static final String GET_PARENT_LIST = "/apiadmin/get_list_of_parents/";

    public static final String GET_CLASS_TEST_MARK = "/apiteacher/disp_Ctestmarks/";

    public static final String GET_ACADEMIC_EXAM_MARK = "/apiteacher/disp_Exammarks/";

    public static final String GET_PARENT_INFO = "/apiadmin/get_parent_details/";

    public static final String GET_VIEW_STUDENT_INFO = "/apiadmin/get_parent_student_list/";

    public static final String GET_VIEW_TEACHERS_INFO_LIST = "/apiadmin/list_of_teachers_for_class/";

    public static final String GET_VIEW_EXAMS = "/apiadmin/list_of_exams_class/";

    public static final String GET_VIEW_FEES = "/apiadmin/get_fees_master_class/";

    public static final String GET_VIEW_FEES_STATUS = "/apiadmin/get_fees_status/";

    //Exam duty
    public static final String GET_EXAM_DUTY = "/apiteacher/view_Examduty/";

    //Class and sec List
    public static final String GET_CLASS_SECTION = "/apimain/list_class_section/";

    //Class and sec List
    public static final String SEND_CLASS_SECTION = "/apimain/view_class_day_attendence/";

    //Class and sec List
    public static final String USER_NOTIFICATION = "/apimain/update_notification_status/";

    //Class and sec List
    public static final String USER_NOTIFICATION_STATUS = "/apimain/notification_status/";

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
    public static final String PARAMS_TEACHER_ID = "teacher_id";
    public static final String PARAMS_DAY_ID = "day_id";

    // Get AllHoliday list
    public static final String PARAMS_HDAY_USER_TYPE = "user_type";
    public static final String PARAMS_HDAY_CLASS_ID = "class_id";
    public static final String PARAMS_HDAY_SEC_ID = "sec_id";
    public static final String PARAMS_HDAY_CLASS_SEC_ID = "class_sec_id";

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
    public static final String TEACHER_SKILL_SET = "teacher_skill_set";
    public static final String TEACHER_PREVIOUS_INSTITUTE = "teacher_previous_institute";
    public static final String TEACHER_TOTAL_EXP = "teacher_total_exp";
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
    public static final String PARAM_IS_INTERNAL_EXTERNAL = "is_internal_external";

    //Event Organiser
    public static final String PARAM_EVENT_ID = "event_id";

    //Teacher's Class Teacher Attendance view
    public static final String KEY_ATTENDANCE_ID = "attend_id";
    public static final String KEY_ATTENDANCE_MESSAGE_TYPE = "msg_type";

    //Teacher's Class Teacher Attendance view
    public static final String CT_HW_CLASS_ID = "class_id";
    public static final String CT_HW_HOMEWORK_DATE = "hw_date";
    public static final String CT_HW_HOMEWORK_CREATED_DATE = "hw_created_date";
    public static final String CT_HW_HOMEWORK_SEND_TYPE = "msg_type";
    public static final String CT_HW_HOMEWORK_ID = "hw_id";

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
    public static final String PARAMS_DATE = "date";
    public static final String PARAMS_CLASS_IDS = "class_ids";
    public static final String PARAMS_OD_NOTES = "notes";
    public static final String PARAMS_OD_STATUS = "status";
    public static final String PARAMS_OD_CREATED_BY = "created_by";
    public static final String PARAMS_OD_CREATED_AT = "created_at";

    //Group notifications Params
    public static final String PARAMS_GROUP_NOTIFICATIONS_USER_TYPE = "user_type";
    public static final String PARAMS_GROUP_NOTIFICATIONS_USER_ID = "user_id";
    public static final String PARAMS_GROUP_NOTIFICATIONS_TITLE_ID = "group_title_id";
    public static final String PARAMS_GROUP_NOTIFICATIONS_MESSAGE_TYPE_SMS = "messagetype_sms";
    public static final String PARAMS_GROUP_NOTIFICATIONS_MESSAGE_TYPE_MAIL = "messagetype_mail";
    public static final String PARAMS_GROUP_NOTIFICATIONS_MESSAGE_TYPE_NOTIFICATION = "messagetype_notification";
    public static final String PARAMS_GROUP_NOTIFICATIONS_MESSAGE_DETAILS = "notes";
    public static final String PARAMS_GROUP_NOTIFICATIONS_CREATED_BY = "created_by";

    //Group notifications creation Params
    public static final String PARAMS_GROUP_NOTIFICATIONS_CREATION_USER_ID = "user_id";
    public static final String PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_ID = "group_id";
    public static final String PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_TITLE = "group_title";
    public static final String PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_LEAD_ID = "group_lead_id";
    public static final String PARAMS_GROUP_NOTIFICATIONS_CREATION_STATUS = "status";
    public static final String PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_USER_TYPE_ID = "group_user_type";
    public static final String PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_CLASS_SEC_ID = "class_sec_id";
    public static final String PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_MEMBER_ID = "group_member_id";
    public static final String PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_STUDENT_CLASS_ID = "class_id";
    public static final String PARAMS_GROUP_NOTIFICATIONS_TYPE_NOTIFICATION = "notification_type";

    //On Duty Approval
    public static final String PARAMS_OD_APPROVAL_STATUS = "status";
    public static final String PARAMS_OD_ID = "od_id";

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
    public static final String PARAMS_ACADEMIC_EXAM_MARKS_TOTAL_MARK = "marks";
    public static final String PARAMS_ACADEMIC_INTERNAL_EXTERNAL_MARK_STATUS = "is_internal_external";
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

    //Leave Approval
    public static final String PARAMS_LEAVE_APPROVAL_STATUS = "status";
    public static final String PARAMS_LEAVE_ID = "leave_id";

    //Class and Section Params
    public static final String PARAMS_CLASS_ID_LIST = "class_id";
    public static final String PARAMS_SECTION_ID_LIST = "section_id";

    public static final String PARAMS_CLASS_ID_NEW = "class_id";
    public static final String PARAMS_SECTION_ID = "section_id";

    //Students
    public static final String PARAMS_STUDENT_ID_SHOW = "student_id";

    public static final String PARAMS_SUBJECT_ID_SHOW = "subject_id";

    //Teachers
    public static final String PARAMS_TEACHER_ID_SHOW = "teacher_id";

    public static final String PARAMS_PARENT_ID_SHOW = "parent_id";

    public static final String PARAMS_PARENT_ID_SHOW_NEW = "admission_id";

    public static final String PARAMS_FEES_ID_SHOW = "fees_id";

    // TimeTable Review Add Params
    public static final String PARAMS_REVIEW_TIME_DATE = "time_date";
    public static final String PARAMS_REVIEW_CLASS_ID = "class_id";
    public static final String PARAMS_REVIEW_SUBJECT_ID = "subject_id";
    public static final String PARAMS_REVIEW_PERIOD_ID = "period_id";
    public static final String PARAMS_REVIEW_USER_TYPE = "user_type";
    public static final String PARAMS_REVIEW_USER_ID = "user_id";
    public static final String PARAMS_REVIEW_COMMENTS = "comments";
    public static final String PARAMS_REVIEW_CREATED_AT = "created_at";

    // Timetable Review
    public static final String GET_ON_TIME_TABLE_REVIEW_ADD = "/apiteacher/add_Timetablereview/";
    public static final String GET_ON_TIME_TABLE_REVIEW_ = "/apiteacher/disp_Timetablereview/";

    // Circular Params
    public static final String PARAMS_CIRCULAR_TITLE = "circular_title";
    public static final String PARAMS_CIRCUALR_DETAILS = "circular_description";
    public static final String PARAMS_CIRCULAR_STATUS = "status";

    // Circular
    public static final String GET_CIRCULAR_MASTER_ADD = "/apiadmin/add_circular/";

    // Google Project Number
    public static final String GOOGLE_PROJECT_ID = "56118066242";
    public static final String MESSAGE_KEY = "message";

    public static final String KEY_GCM_ID = "gcm_id";
    public static final String KEY_IMEI = "imei_code";

    public static final String GCM_KEY = "gcm_key";
    public static final String MOBILE_TYPE = "mobile_type";
    public static final String NOTIFICATION_TYPE = "type";

    public static final long TWENTY4HOURS = 24 * 60 * 60 * 1000;//24 hours in milli seconds format

}
