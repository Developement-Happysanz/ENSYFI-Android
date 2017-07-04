package com.palprotech.ensyfi.bean.student.support;

import android.content.Context;

import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Admin on 04-07-2017.
 */

public class SaveStudentData {

    private Context context;
    SQLiteHelper database;

    public SaveStudentData(Context context) {
        this.context = context;
    }

    public void saveStudentRegisteredData(JSONArray studentRegistered) {
        database = new SQLiteHelper(context);
        try {
            database.deleteLocal();

            for (int i = 0; i < studentRegistered.length(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject jsonobj = studentRegistered.getJSONObject(i);


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

    public void saveStudentProfile(JSONArray studentProfile) {

        try {
            JSONObject getStudentProfile = studentProfile.getJSONObject(0);

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
            String StudentNationality = "";
            String StudentReligion = "";
            String StudentCaste = "";
            String StudentCommunity = "";
            String StudentParentOrGuardian = "";
            String StudentParentOrGuardianId = "";
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

            StudentAdmissionId = getStudentProfile.getString("admission_id");
            StudentAdmissionYear = getStudentProfile.getString("admisn_year");
            StudentAdmissionNumber = getStudentProfile.getString("admisn_no");
            StudentEmsiNumber = getStudentProfile.getString("emsi_num");
            StudentAdmissionDate = getStudentProfile.getString("admisn_date");
            StudentName = getStudentProfile.getString("name");
            StudentGender = getStudentProfile.getString("sex");
            StudentDateOfBirth = getStudentProfile.getString("dob");
            StudentAge = getStudentProfile.getString("age");
            StudentNationality = getStudentProfile.getString("nationality");
            StudentReligion = getStudentProfile.getString("religion");
            StudentCaste = getStudentProfile.getString("community_class");
            StudentCommunity = getStudentProfile.getString("community");
            StudentParentOrGuardian = getStudentProfile.getString("parnt_guardn");
            StudentParentOrGuardianId = getStudentProfile.getString("parnt_guardn_id");
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


            // Parents Preference - Student Admission Id
            if ((StudentAdmissionId != null) && !(StudentAdmissionId.isEmpty()) && !StudentAdmissionId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentAdmissionID(context, StudentAdmissionId);
            }

            // Parents Preference - Student Admission Year
            if ((StudentAdmissionYear != null) && !(StudentAdmissionYear.isEmpty()) && !StudentAdmissionYear.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentAdmissionYear(context, StudentAdmissionYear);
            }

            // Parents Preference - Student Admission Number
            if ((StudentAdmissionNumber != null) && !(StudentAdmissionNumber.isEmpty()) && !StudentAdmissionNumber.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentAdmissionNumber(context, StudentAdmissionNumber);
            }

            // Parents Preference - Student Emsi Number
            if ((StudentEmsiNumber != null) && !(StudentEmsiNumber.isEmpty()) && !StudentEmsiNumber.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentEmsiNumber(context, StudentEmsiNumber);
            }

            // Parents Preference - Student Admission Date
            if ((StudentAdmissionDate != null) && !(StudentAdmissionDate.isEmpty()) && !StudentAdmissionDate.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentAdmissionDate(context, StudentAdmissionDate);
            }

            // Parents Preference - Student Name
            if ((StudentName != null) && !(StudentName.isEmpty()) && !StudentName.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentName(context, StudentName);
            }

            // Parents Preference - Student Gender
            if ((StudentGender != null) && !(StudentGender.isEmpty()) && !StudentGender.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentGender(context, StudentGender);
            }

            // Parents Preference - Student Date Of Birth
            if ((StudentDateOfBirth != null) && !(StudentDateOfBirth.isEmpty()) && !StudentDateOfBirth.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentDateOfBirth(context, StudentDateOfBirth);
            }

            // Parents Preference - Student Age
            if ((StudentAge != null) && !(StudentAge.isEmpty()) && !StudentAge.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentAge(context, StudentAge);
            }

            // Parents Preference - Student Nationality
            if ((StudentNationality != null) && !(StudentNationality.isEmpty()) && !StudentNationality.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentNationality(context, StudentNationality);
            }

            // Parents Preference - Student Religion
            if ((StudentReligion != null) && !(StudentReligion.isEmpty()) && !StudentReligion.equalsIgnoreCase("null")) {
                PreferenceStorage.saveFatherImg(context, StudentReligion);
            }

            // Parents Preference - Student Caste
            if ((StudentCaste != null) && !(StudentCaste.isEmpty()) && !StudentCaste.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentCaste(context, StudentCaste);
            }

            // Parents Preference - Student Community
            if ((StudentCommunity != null) && !(StudentCommunity.isEmpty()) && !StudentCommunity.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentCommunity(context, StudentCommunity);
            }

            // Parents Preference - Student Parent Or Guardian
            if ((StudentParentOrGuardian != null) && !(StudentParentOrGuardian.isEmpty()) && !StudentParentOrGuardian.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentParentOrGuardian(context, StudentParentOrGuardian);
            }

            // Parents Preference - Student Parent Or Guardian Id
            if ((StudentParentOrGuardianId != null) && !(StudentParentOrGuardianId.isEmpty()) && !StudentParentOrGuardianId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentParentOrGuardianID(context, StudentParentOrGuardianId);
            }

            // Parents Preference - Student Mother Tongue
            if ((StudentMotherTongue != null) && !(StudentMotherTongue.isEmpty()) && !StudentMotherTongue.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentMotherTongue(context, StudentMotherTongue);
            }

            // Parents Preference - Student Language
            if ((StudentLanguage != null) && !(StudentLanguage.isEmpty()) && !StudentLanguage.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentLanguage(context, StudentLanguage);
            }

            // Parents Preference - Student Mobile
            if ((StudentMobile != null) && !(StudentMobile.isEmpty()) && !StudentMobile.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentMobile(context, StudentMobile);
            }

            // Parents Preference - Student Secondary Mobile
            if ((StudentSecondaryMobile != null) && !(StudentSecondaryMobile.isEmpty()) && !StudentSecondaryMobile.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentSecondaryMobile(context, StudentSecondaryMobile);
            }

            // Parents Preference - Student Mail
            if ((StudentMail != null) && !(StudentMail.isEmpty()) && !StudentMail.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentMail(context, StudentMail);
            }

            // Parents Preference - Student Secondary Mail
            if ((StudentSecondaryMail != null) && !(StudentSecondaryMail.isEmpty()) && !StudentSecondaryMail.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentSecondaryMail(context, StudentSecondaryMail);
            }

            // Parents Preference - Student Pic
            if ((StudentPic != null) && !(StudentPic.isEmpty()) && !StudentPic.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentImg(context, StudentPic);
            }

            // Parents Preference - Student Previous School
            if ((StudentPreviousSchool != null) && !(StudentPreviousSchool.isEmpty()) && !StudentPreviousSchool.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentPreviousSchool(context, StudentPreviousSchool);
            }

            // Parents Preference - Student Previous Class
            if ((StudentPreviousClass != null) && !(StudentPreviousClass.isEmpty()) && !StudentPreviousClass.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentPreviousClass(context, StudentPreviousClass);
            }

            // Parents Preference - Student Promotion Status
            if ((StudentPromotionStatus != null) && !(StudentPromotionStatus.isEmpty()) && !StudentPromotionStatus.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentPromotionStatus(context, StudentPromotionStatus);
            }

            // Parents Preference - Student Transfer Certificate
            if ((StudentTransferCertificate != null) && !(StudentTransferCertificate.isEmpty()) && !StudentTransferCertificate.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentTransferCertificate(context, StudentTransferCertificate);
            }

            // Parents Preference - Student Record Sheet
            if ((StudentRecordSheet != null) && !(StudentRecordSheet.isEmpty()) && !StudentRecordSheet.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentRecordSheet(context, StudentRecordSheet);
            }

            // Parents Preference - Student Status
            if ((StudentStatus != null) && !(StudentStatus.isEmpty()) && !StudentStatus.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentStatus(context, StudentStatus);
            }

            // Parents Preference - Student Parent Status
            if ((StudentParentStatus != null) && !(StudentParentStatus.isEmpty()) && !StudentParentStatus.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentParentStatus(context, StudentParentStatus);
            }

            // Parents Preference - Student Registered
            if ((StudentRegistered != null) && !(StudentRegistered.isEmpty()) && !StudentRegistered.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentRegistered(context, StudentRegistered);
            }


        } catch (Exception ex) {
        }

    }
}
