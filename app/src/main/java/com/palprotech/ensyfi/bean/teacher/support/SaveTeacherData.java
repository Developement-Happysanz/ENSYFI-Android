package com.palprotech.ensyfi.bean.teacher.support;

import android.content.Context;

import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Admin on 04-07-2017.
 */

public class SaveTeacherData {

    private Context context;
    SQLiteHelper database;

    public SaveTeacherData(Context context) {
        this.context = context;
    }

    public void saveTeacherProfile(JSONArray teacherProfile) {
        try {
            JSONObject getTeacherProfile = teacherProfile.getJSONObject(0);

            //Teacher Details
            String imageURL = "";
            imageURL = EnsyfiConstants.USER_IMAGE_API_TEACHERS; // Teacher user image url
            String TeacherId = "";
            String TeacherName = "";
            String TeacherGender = "";
            String TeacherAge = "";
            String TeacherNationality = "";
            String TeacherReligion = "";
            String TeacherCaste = "";
            String TeacherCommunity = "";
            String TeacherAddress = "";
            String TeacherSubject = "";
            String ClassTeacher = "";
            String TeacherMobile = "";
            String TeacherSecondaryMobile = "";
            String TeacherMail = "";
            String TeacherSecondaryMail = "";
            String TeacherPicUrl = "";
            String TeacherPic = "";
            String TeacherSectionName = "";
            String TeacherClassName = "";
            String TeacherClassTaken = "";
            String TeacherSubjectName = "";

            TeacherId = getTeacherProfile.getString("teacher_id");
            TeacherName = getTeacherProfile.getString("name");
            TeacherGender = getTeacherProfile.getString("sex");
            TeacherAge = getTeacherProfile.getString("age");
            TeacherNationality = getTeacherProfile.getString("nationality");
            TeacherReligion = getTeacherProfile.getString("religion");
            TeacherCaste = getTeacherProfile.getString("community_class");
            TeacherCommunity = getTeacherProfile.getString("community");
            TeacherAddress = getTeacherProfile.getString("address");
            TeacherMail = getTeacherProfile.getString("email");
            TeacherMobile = getTeacherProfile.getString("phone");
            TeacherSecondaryMail = getTeacherProfile.getString("sec_email");
            TeacherSecondaryMobile = getTeacherProfile.getString("sec_phone");
            TeacherPic = getTeacherProfile.getString("profile_pic");
            TeacherSubject = getTeacherProfile.getString("subject");
            TeacherClassTaken = getTeacherProfile.getString("class_taken");
            ClassTeacher = getTeacherProfile.getString("class_teacher");
            TeacherSubjectName = getTeacherProfile.getString("subject_name");
            TeacherSectionName = getTeacherProfile.getString("sec_name");
            TeacherClassName = getTeacherProfile.getString("class_name");

            TeacherPicUrl = PreferenceStorage.getUserDynamicAPI(context) + imageURL + TeacherPic; // Generate user image url

            // Parents Preference - Student Admission Id
            if ((TeacherId != null) && !(TeacherId.isEmpty()) && !TeacherId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherId(context, TeacherId);
            }

            // Parents Preference - Student Admission Year
            if ((TeacherName != null) && !(TeacherName.isEmpty()) && !TeacherName.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherName(context, TeacherName);
            }

            // Parents Preference - Student Admission Number
            if ((TeacherGender != null) && !(TeacherGender.isEmpty()) && !TeacherGender.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherGender(context, TeacherGender);
            }

            // Parents Preference - Student Emsi Number
            if ((TeacherAge != null) && !(TeacherAge.isEmpty()) && !TeacherAge.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherAge(context, TeacherAge);
            }

            // Parents Preference - Student Admission Date
            if ((TeacherNationality != null) && !(TeacherNationality.isEmpty()) && !TeacherNationality.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherNationality(context, TeacherNationality);
            }

            // Parents Preference - Student Name
            if ((TeacherReligion != null) && !(TeacherReligion.isEmpty()) && !TeacherReligion.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherReligion(context, TeacherReligion);
            }

            // Parents Preference - Student Gender
            if ((TeacherCaste != null) && !(TeacherCaste.isEmpty()) && !TeacherCaste.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherCaste(context, TeacherCaste);
            }

            // Parents Preference - Student Date Of Birth
            if ((TeacherCommunity != null) && !(TeacherCommunity.isEmpty()) && !TeacherCommunity.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherCommunity(context, TeacherCommunity);
            }

            // Parents Preference - Student Age
            if ((TeacherAddress != null) && !(TeacherAddress.isEmpty()) && !TeacherAddress.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherAddress(context, TeacherAddress);
            }

            // Parents Preference - Student Nationality
            if ((TeacherSubject != null) && !(TeacherSubject.isEmpty()) && !TeacherSubject.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherSubject(context, TeacherSubject);
            }

            // Parents Preference - Student Religion
            if ((ClassTeacher != null) && !(ClassTeacher.isEmpty()) && !ClassTeacher.equalsIgnoreCase("null")) {
                PreferenceStorage.saveClassTeacher(context, ClassTeacher);
            }

            // Parents Preference - Student Caste
            if ((TeacherMobile != null) && !(TeacherMobile.isEmpty()) && !TeacherMobile.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherMobile(context, TeacherMobile);
            }

            // Parents Preference - Student Community
            if ((TeacherSecondaryMobile != null) && !(TeacherSecondaryMobile.isEmpty()) && !TeacherSecondaryMobile.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherSecondaryMobile(context, TeacherSecondaryMobile);
            }

            // Parents Preference - Student Parent Or Guardian
            if ((TeacherMail != null) && !(TeacherMail.isEmpty()) && !TeacherMail.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherMail(context, TeacherMail);
            }

            // Parents Preference - Student Parent Or Guardian Id
            if ((TeacherSecondaryMail != null) && !(TeacherSecondaryMail.isEmpty()) && !TeacherSecondaryMail.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherSecondaryMail(context, TeacherSecondaryMail);
            }

            // Parents Preference - Student Mother Tongue
            if ((TeacherPicUrl != null) && !(TeacherPicUrl.isEmpty()) && !TeacherPicUrl.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherPic(context, TeacherPicUrl);
            }

            // Parents Preference - Student Language
            if ((TeacherSectionName != null) && !(TeacherSectionName.isEmpty()) && !TeacherSectionName.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherSectionName(context, TeacherSectionName);
            }

            // Parents Preference - Student Mobile
            if ((TeacherClassName != null) && !(TeacherClassName.isEmpty()) && !TeacherClassName.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherClassName(context, TeacherClassName);
            }

            // Parents Preference - Student Secondary Mobile
            if ((TeacherClassTaken != null) && !(TeacherClassTaken.isEmpty()) && !TeacherClassTaken.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherClassTaken(context, TeacherClassTaken);
            }

            // Parents Preference - Student Secondary Mobile
            if ((TeacherSubjectName != null) && !(TeacherSubjectName.isEmpty()) && !TeacherSubjectName.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherSubjectName(context, TeacherSubjectName);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveTeacherTimeTable(JSONArray timeTable) {
        database = new SQLiteHelper(context);
        try {
            database.deleteTeacherTimeTable();

            for (int i = 0; i < timeTable.length(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject jsonobj = timeTable.getJSONObject(i);

                String table_id = "";
                String class_id = "";
                String subject_id = "";
                String subject_name = "";
                String teacher_id = "";
                String name = "";
                String day = "";
                String period = "";
                String sec_name = "";
                String class_name = "";

                table_id = jsonobj.getString("table_id");
                class_id = jsonobj.getString("class_id");
                subject_id = jsonobj.getString("subject_id");
                subject_name = jsonobj.getString("subject_name");
                teacher_id = jsonobj.getString("teacher_id");
                name = jsonobj.getString("name");
                day = jsonobj.getString("day");
                period = jsonobj.getString("period");
                sec_name = jsonobj.getString("sec_name");
                class_name = jsonobj.getString("class_name");

                System.out.println("table_id : " + i + " = " + table_id);
                System.out.println("class_id : " + i + " = " + class_id);
                System.out.println("subject_id : " + i + " = " + subject_id);
                System.out.println("subject_name : " + i + " = " + subject_name);
                System.out.println("teacher_id : " + i + " = " + teacher_id);
                System.out.println("name : " + i + " = " + name);
                System.out.println("day : " + i + " = " + day);
                System.out.println("period : " + i + " = " + period);
                System.out.println("sec_name : " + i + " = " + sec_name);
                System.out.println("class_name : " + i + " = " + class_name);

                String v1 = table_id,
                        v2 = class_id,
                        v3 = subject_id,
                        v4 = subject_name,
                        v5 = teacher_id,
                        v6 = name,
                        v7 = day,
                        v8 = period,
                        v9 = sec_name,
                        v10 = class_name;

                database.teacher_timetable_insert(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveStudentDetails(JSONArray studentDetails) {

        database = new SQLiteHelper(context);
        try {
            database.deleteTeachersClassStudentDetails();

            for (int i = 0; i < studentDetails.length(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject jsonobj = studentDetails.getJSONObject(i);

                String enroll_id = "";
                String admission_id = "";
                String class_id = "";
                String name = "";
                String class_section = "";


                enroll_id = jsonobj.getString("enroll_id");
                admission_id = jsonobj.getString("admission_id");
                class_id = jsonobj.getString("class_id");
                name = jsonobj.getString("name");
                class_section = jsonobj.getString("class_section");

                System.out.println("enroll_id : " + i + " = " + enroll_id);
                System.out.println("admission_id : " + i + " = " + admission_id);
                System.out.println("class_id : " + i + " = " + class_id);
                System.out.println("name : " + i + " = " + name);
                System.out.println("class_section : " + i + " = " + class_section);


                String v1 = enroll_id,
                        v2 = admission_id,
                        v3 = class_id,
                        v4 = name,
                        v5 = class_section;

                database.teachers_class_students_details_insert(v1, v2, v3, v4, v5);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveAcademicMonth(JSONArray academicMonth) {
        database = new SQLiteHelper(context);
        try {
            database.deleteAcademicMonths();

            for (int i = 0; i < academicMonth.length(); i++) {

                String months = "";

                months = academicMonth.getString(i);

                System.out.println("table_id : " + i + " = " + months);

                long x = database.academic_months_insert(months);

                System.out.println("Stored Id : " + x);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveHomeWorkClassTest(JSONArray homeWorkClassTest) {
        database = new SQLiteHelper(context);
        try {
            String formattedServerDate = "";
            Calendar c = Calendar.getInstance();
            SimpleDateFormat serverDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formattedServerDate = serverDF.format(c.getTime());
            database.deleteHomeWorkClassTest();

            for (int i = 0; i < homeWorkClassTest.length(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject jsonobj = homeWorkClassTest.getJSONObject(i);

                String hw_id = "";
                String class_id = "";
                String homeWork_type = "";
                String subject_id = "";
                String subject_name = "";
                String title = "";
                String test_date = "";
                String due_date = "";
                String homework_details = "";
                String mark_status = "";


                hw_id = jsonobj.getString("hw_id");
                class_id = jsonobj.getString("class_id");
                homeWork_type = jsonobj.getString("hw_type");
                subject_id = jsonobj.getString("subject_id");
                subject_name = jsonobj.getString("subject_name");
                title = jsonobj.getString("title");
                test_date = jsonobj.getString("test_date");
                due_date = jsonobj.getString("due_date");
                homework_details = jsonobj.getString("hw_details");
                mark_status = jsonobj.getString("mark_status");

                System.out.println("hw_id : " + i + " = " + hw_id);
                System.out.println("class_id : " + i + " = " + class_id);
                System.out.println("homeWork_type : " + i + " = " + homeWork_type);
                System.out.println("subject_id : " + i + " = " + subject_id);
                System.out.println("subject_name : " + i + " = " + subject_name);
                System.out.println("title : " + i + " = " + title);
                System.out.println("test_date : " + i + " = " + test_date);
                System.out.println("due_date : " + i + " = " + due_date);
                System.out.println("homework_details : " + i + " = " + homework_details);
                System.out.println("mark_status : " + i + " = " + mark_status);


                long x = database.homework_class_test_insert(hw_id, PreferenceStorage.getAcademicYearId(context), class_id, PreferenceStorage.getUserId(context), homeWork_type, subject_id, subject_name, title, test_date, due_date, homework_details, "Active", mark_status, PreferenceStorage.getUserId(context), formattedServerDate, PreferenceStorage.getUserId(context), formattedServerDate, "S");

                System.out.println("Stored Id : " + x);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveExamsOfClass(JSONArray academicExams) {
        database = new SQLiteHelper(context);

        try {
            database.deleteExamOfClasses();
            for (int i = 0; i < academicExams.length(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject jsonobj = academicExams.getJSONObject(i);

                String exam_id = "";
                String exam_name = "";
                String classmaster_id = "";
                String sec_name = "";
                String class_name = "";
                String Fromdate = "";
                String Todate = "";
                String MarkStatus = "";

                exam_id = jsonobj.getString("exam_id");
                exam_name = jsonobj.getString("exam_name");
                classmaster_id = jsonobj.getString("classmaster_id");
                sec_name = jsonobj.getString("sec_name");
                class_name = jsonobj.getString("class_name");
                Fromdate = jsonobj.getString("Fromdate");
                Todate = jsonobj.getString("Todate");
                MarkStatus = jsonobj.getString("MarkStatus");

                System.out.println("exam_id : " + i + " = " + exam_id);
                System.out.println("exam_name : " + i + " = " + exam_name);
                System.out.println("classmaster_id : " + i + " = " + classmaster_id);
                System.out.println("sec_name : " + i + " = " + sec_name);
                System.out.println("class_name : " + i + " = " + class_name);
                System.out.println("Fromdate : " + i + " = " + Fromdate);
                System.out.println("Todate : " + i + " = " + Todate);
                System.out.println("MarkStatus : " + i + " = " + MarkStatus);

                String v1 = exam_id,
                        v2 = exam_name,
                        v3 = classmaster_id,
                        v4 = sec_name,
                        v5 = class_name,
                        v6 = Fromdate,
                        v7 = Todate,
                        v8 = MarkStatus;

                long l = database.exam_of_classes_insert(v1, v2, v3, v4, v5, v6, v7, v8);

                System.out.println("" + l);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveExamsDetails(JSONArray academicExamsDetails) {
        database = new SQLiteHelper(context);

        try {
            database.deleteExamDetails();
            for (int i = 0; i < academicExamsDetails.length(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject jsonobj = academicExamsDetails.getJSONObject(i);

                String exam_id = "";
                String exam_name = "";
                String subject_name = "";
                String exam_date = "";
                String times = "";
                String classmaster_id = "";
                String class_name = "";
                String sec_name = "";

                exam_id = jsonobj.getString("exam_id");
                exam_name = jsonobj.getString("exam_name");
                subject_name = jsonobj.getString("subject_name");
                exam_date = jsonobj.getString("exam_date");
                times = jsonobj.getString("times");
                classmaster_id = jsonobj.getString("classmaster_id");
                class_name = jsonobj.getString("class_name");
                sec_name = jsonobj.getString("sec_name");

                System.out.println("exam_id : " + i + " = " + exam_id);
                System.out.println("exam_name : " + i + " = " + exam_name);
                System.out.println("subject_name : " + i + " = " + subject_name);
                System.out.println("exam_date : " + i + " = " + exam_date);
                System.out.println("times : " + i + " = " + times);
                System.out.println("classmaster_id : " + i + " = " + classmaster_id);
                System.out.println("class_name : " + i + " = " + class_name);
                System.out.println("sec_name : " + i + " = " + sec_name);

                String v1 = exam_id,
                        v2 = exam_name,
                        v3 = subject_name,
                        v4 = exam_date,
                        v5 = times,
                        v6 = classmaster_id,
                        v7 = class_name,
                        v8 = sec_name;

                long l = database.exam_details_insert(v1, v2, v3, v4, v5, v6, v7, v8);

                System.out.println("" + l);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveTeacherHandlingSubject(JSONArray teacherHandlingSubject) {
        database = new SQLiteHelper(context);

        try {
            database.deleteTeacherHandlingSubjects();
            for (int i = 0; i < teacherHandlingSubject.length(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject jsonobj = teacherHandlingSubject.getJSONObject(i);

                String class_master_id = "";
                String teacher_id = "";
                String class_name = "";
                String sec_name = "";
                String subject_name = "";
                String subject_id = "";

                class_master_id = jsonobj.getString("class_master_id");
                teacher_id = jsonobj.getString("teacher_id");
                class_name = jsonobj.getString("class_name");
                sec_name = jsonobj.getString("sec_name");
                subject_name = jsonobj.getString("subject_name");
                subject_id = jsonobj.getString("subject_id");

                System.out.println("class_master_id : " + i + " = " + class_master_id);
                System.out.println("teacher_id : " + i + " = " + teacher_id);
                System.out.println("class_name : " + i + " = " + class_name);
                System.out.println("sec_name : " + i + " = " + sec_name);
                System.out.println("subject_name : " + i + " = " + subject_name);
                System.out.println("subject_id : " + i + " = " + subject_id);

                String v1 = class_master_id,
                        v2 = teacher_id,
                        v3 = class_name,
                        v4 = sec_name,
                        v5 = subject_name,
                        v6 = subject_id;

                long l = database.teacher_handling_subject_insert(v1, v2, v3, v4, v5, v6);

                System.out.println("" + l);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

