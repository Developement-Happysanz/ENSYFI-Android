package com.palprotech.ensyfi.bean.student.support;

import android.content.Context;

import com.palprotech.ensyfi.bean.database.SQLiteHelper;

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
            String StudentNationaity = "";
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
            StudentNationaity = getStudentProfile.getString("nationality");
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

        } catch (Exception ex) {
        }

    }
}
