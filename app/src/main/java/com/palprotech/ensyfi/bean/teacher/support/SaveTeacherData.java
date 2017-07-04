package com.palprotech.ensyfi.bean.teacher.support;

import android.content.Context;

import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONObject;

/**
 * Created by Admin on 04-07-2017.
 */

public class SaveTeacherData {

    private Context context;

    public SaveTeacherData(Context context) {
        this.context = context;
    }

    public void saveTeacherProfile(JSONObject teacherProfile){

        try {
            JSONObject getTeacherProfile = teacherProfile.getJSONObject("0");

            //Student Details
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
            String TeacherPic = "";
            String TeacherSectionName = "";
            String TeacherClassName = "";
            String TeacherClassTaken = "";

            TeacherId = getTeacherProfile.getString("admission_id");
            TeacherName = getTeacherProfile.getString("admisn_year");
            TeacherGender = getTeacherProfile.getString("admisn_no");
            TeacherAge = getTeacherProfile.getString("emsi_num");
            TeacherNationality = getTeacherProfile.getString("admisn_date");
            TeacherReligion = getTeacherProfile.getString("name");
            TeacherCaste = getTeacherProfile.getString("sex");
            TeacherCommunity = getTeacherProfile.getString("dob");
            TeacherAddress = getTeacherProfile.getString("age");
            TeacherSubject = getTeacherProfile.getString("nationality");
            ClassTeacher = getTeacherProfile.getString("religion");
            TeacherMobile = getTeacherProfile.getString("community_class");
            TeacherSecondaryMobile = getTeacherProfile.getString("community");
            TeacherMail = getTeacherProfile.getString("parnt_guardn");
            TeacherSecondaryMail = getTeacherProfile.getString("parnt_guardn_id");
            TeacherPic = getTeacherProfile.getString("mother_tongue");
            TeacherSectionName = getTeacherProfile.getString("language");
            TeacherClassName = getTeacherProfile.getString("mobile");
            TeacherClassTaken = getTeacherProfile.getString("sec_mobile");


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
            if ((TeacherPic != null) && !(TeacherPic.isEmpty()) && !TeacherPic.equalsIgnoreCase("null")) {
                PreferenceStorage.saveTeacherPic(context, TeacherPic);
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


        } catch (Exception ex) {
        }

    }
}

