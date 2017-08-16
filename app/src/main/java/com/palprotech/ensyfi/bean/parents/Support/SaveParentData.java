package com.palprotech.ensyfi.bean.parents.Support;

import android.content.Context;

import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONObject;

/**
 * Created by Admin on 04-07-2017.
 */

public class SaveParentData {

    private Context context;

    public SaveParentData(Context context) {
        this.context = context;
    }

    public void saveParentProfile(JSONObject parentProfile) {

        try {
            JSONObject fatherData = parentProfile.getJSONObject("fatherProfile");
            JSONObject motherData = parentProfile.getJSONObject("motherProfile");
            JSONObject guardianData = parentProfile.getJSONObject("guardianProfile");

            //Father Details
            String FatherId = "";
            String FatherName = "";
            String FatherAddress = "";
            String FatherMail = "";
            String FatherOccupation = "";
            String FatherIncome = "";
            String FatherMobile = "";
            String FatherHomePhone = "";
            String FatherOfficePhone = "";
            String FatherRelationship = "";
            String FatherPic = "";
            String ParentimgUrl = EnsyfiConstants.USER_IMAGE_API_PARENTS;

            //Mother Details
            String MotherId = "";
            String MotherName = "";
            String MotherAddress = "";
            String MotherMail = "";
            String MotherOccupation = "";
            String MotherIncome = "";
            String MotherMobile = "";
            String MotherHomePhone = "";
            String MotherOfficePhone = "";
            String MotherRelationship = "";
            String MotherPic = "";
            String MotherPicUrl = "";

            //Guardian Details
            String GuardianId = "";
            String GuardianName = "";
            String GuardianAddress = "";
            String GuardianMail = "";
            String GuardianOccupation = "";
            String GuardianIncome = "";
            String GuardianMobile = "";
            String GuardianHomePhone = "";
            String GuardianOfficePhone = "";
            String GuardianRelationship = "";
            String GuardianPic = "";
            String GuardianPicUrl = "";

            FatherId = fatherData.getString("id");
            FatherName = fatherData.getString("name");
            FatherHomePhone = fatherData.getString("home_phone");
            FatherMail = fatherData.getString("email");
            FatherAddress = fatherData.getString("home_address");
            FatherOccupation = fatherData.getString("occupation");
            FatherIncome = fatherData.getString("income");
            FatherMobile = fatherData.getString("mobile");
            FatherOfficePhone = fatherData.getString("office_phone");
            FatherRelationship = fatherData.getString("relationship");
            FatherPic = fatherData.getString("user_pic");

            ///////////     FATHER      //////////

            // Parents Preference - Father's Id
            if ((FatherId != null) && !(FatherId.isEmpty()) && !FatherId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveFatherID(context, FatherId);
            }

            // Parents Preference - Father's Name
            if ((FatherName != null) && !(FatherName.isEmpty()) && !FatherName.equalsIgnoreCase("null")) {
                PreferenceStorage.saveFatherName(context, FatherName);
            }

            // Parents Preference - Father's Mail
            if ((FatherMail != null) && !(FatherMail.isEmpty()) && !FatherMail.equalsIgnoreCase("null")) {
                PreferenceStorage.saveFatherEmail(context, FatherMail);
            }

            // Parents Preference - Address
            if ((FatherAddress != null) && !(FatherAddress.isEmpty()) && !FatherAddress.equalsIgnoreCase("null")) {
                PreferenceStorage.saveFatherAddress(context, FatherAddress);
            }

            // Parents Preference - Father's Occupation
            if ((FatherOccupation != null) && !(FatherOccupation.isEmpty()) && !FatherOccupation.equalsIgnoreCase("null")) {
                PreferenceStorage.saveFatherOccupation(context, FatherOccupation);
            }

            // Parents Preference - Father's Income
            if ((FatherIncome != null) && !(FatherIncome.isEmpty()) && !FatherIncome.equalsIgnoreCase("null")) {
                PreferenceStorage.saveFatherIncome(context, FatherIncome);
            }

            // Parents Preference - Father's Home Phone
            if ((FatherHomePhone != null) && !(FatherHomePhone.isEmpty()) && !FatherHomePhone.equalsIgnoreCase("null")) {
                PreferenceStorage.saveFatherHomePhone(context, FatherHomePhone);
            }

            // Parents Preference - Father's Mobile
            if ((FatherMobile != null) && !(FatherMobile.isEmpty()) && !FatherMobile.equalsIgnoreCase("null")) {
                PreferenceStorage.saveFatherMobile(context, FatherMobile);
            }

            // Parents Preference - Father's Office Phone
            if ((FatherOfficePhone != null) && !(FatherOfficePhone.isEmpty()) && !FatherOfficePhone.equalsIgnoreCase("null")) {
                PreferenceStorage.saveFatherOfficePhone(context, FatherOfficePhone);
            }

            // Parents Preference - Father's Relationship
            if ((FatherRelationship != null) && !(FatherRelationship.isEmpty()) && !FatherRelationship.equalsIgnoreCase("null")) {
                PreferenceStorage.saveFatherRelationship(context, FatherRelationship);
            }

            // Parents Preference - Father's Pic
            FatherPic = PreferenceStorage.getUserDynamicAPI(context) + ParentimgUrl + FatherPic;
            if ((FatherPic != null) && !(FatherPic.isEmpty()) && !FatherPic.equalsIgnoreCase("null")) {
                PreferenceStorage.saveFatherImg(context, FatherPic);
            }

            ///////////     MOTHER      //////////

            MotherId = motherData.getString("id");
            MotherName = motherData.getString("name");
            MotherHomePhone = motherData.getString("home_phone");
            MotherMail = motherData.getString("email");
            MotherAddress = motherData.getString("home_address");
            MotherOccupation = motherData.getString("occupation");
            MotherIncome = motherData.getString("income");
            MotherMobile = motherData.getString("mobile");
            MotherOfficePhone = motherData.getString("office_phone");
            MotherRelationship = motherData.getString("relationship");
            MotherPic = motherData.getString("user_pic");


            // Parents Preference - Mother's Id
            if ((MotherId != null) && !(MotherId.isEmpty()) && !MotherId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveMotherID(context, MotherId);
            }

            // Parents Preference - Mother's Name
            if ((MotherName != null) && !(MotherName.isEmpty()) && !MotherName.equalsIgnoreCase("null")) {
                PreferenceStorage.saveMotherName(context, MotherName);
            }

            // Parents Preference - Mother's Phone
            if ((MotherHomePhone != null) && !(MotherHomePhone.isEmpty()) && !MotherHomePhone.equalsIgnoreCase("null")) {
                PreferenceStorage.saveMotherHomePhone(context, MotherHomePhone);
            }

            // Parents Preference - Mother's Mail
            if ((MotherMail != null) && !(MotherMail.isEmpty()) && !MotherMail.equalsIgnoreCase("null")) {
                PreferenceStorage.saveMotherEmail(context, MotherMail);
            }

            // Parents Preference - Address
            if ((MotherAddress != null) && !(MotherAddress.isEmpty()) && !MotherAddress.equalsIgnoreCase("null")) {
                PreferenceStorage.saveMotherAddress(context, MotherAddress);
            }

            // Parents Preference - Mother's Occupation
            if ((MotherOccupation != null) && !(MotherOccupation.isEmpty()) && !MotherOccupation.equalsIgnoreCase("null")) {
                PreferenceStorage.saveMotherOccupation(context, MotherOccupation);
            }

            // Parents Preference - Mother's Income
            if ((MotherIncome != null) && !(MotherIncome.isEmpty()) && !MotherIncome.equalsIgnoreCase("null")) {
                PreferenceStorage.saveMotherIncome(context, MotherIncome);
            }

            // Parents Preference - Mother's Home Phone
            if ((MotherHomePhone != null) && !(MotherHomePhone.isEmpty()) && !MotherHomePhone.equalsIgnoreCase("null")) {
                PreferenceStorage.saveMotherHomePhone(context, MotherHomePhone);
            }

            // Parents Preference - Mother's Mobile
            if ((MotherMobile != null) && !(MotherMobile.isEmpty()) && !MotherMobile.equalsIgnoreCase("null")) {
                PreferenceStorage.saveMotherMobile(context, MotherMobile);
            }

            // Parents Preference - Mother's Office Phone
            if ((MotherOfficePhone != null) && !(MotherOfficePhone.isEmpty()) && !MotherOfficePhone.equalsIgnoreCase("null")) {
                PreferenceStorage.saveMotherOfficePhone(context, MotherOfficePhone);
            }

            // Parents Preference - Mother's Relationship
            if ((MotherRelationship != null) && !(MotherRelationship.isEmpty()) && !MotherRelationship.equalsIgnoreCase("null")) {
                PreferenceStorage.saveMotherRelationship(context, MotherRelationship);
            }

            // Parents Preference - Mother's Pic
            MotherPic = PreferenceStorage.getUserDynamicAPI(context) + ParentimgUrl + MotherPic;
            if ((MotherPic != null) && !(MotherPic.isEmpty()) && !MotherPic.equalsIgnoreCase("null")) {
                PreferenceStorage.saveMotherImg(context, MotherPic);
            }

            ///////////     GUARDIAN      //////////

            GuardianId = guardianData.getString("id");
            GuardianName = guardianData.getString("name");
            GuardianHomePhone = guardianData.getString("home_phone");
            GuardianMail = guardianData.getString("email");
            GuardianAddress = guardianData.getString("home_address");
            GuardianOccupation = guardianData.getString("occupation");
            GuardianIncome = guardianData.getString("income");
            GuardianMobile = guardianData.getString("mobile");
            GuardianOfficePhone = guardianData.getString("office_phone");
            GuardianRelationship = guardianData.getString("relationship");
            GuardianPic = guardianData.getString("user_pic");


            // Parents Preference - Guardian's Id
            if ((GuardianId != null) && !(GuardianId.isEmpty()) && !GuardianId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveGuardianID(context, GuardianId);
            }

            // Parents Preference - Guardian's Name
            if ((GuardianName != null) && !(GuardianName.isEmpty()) && !GuardianName.equalsIgnoreCase("null")) {
                PreferenceStorage.saveGuardianName(context, GuardianName);
            }

            // Parents Preference - Guardian's Phone
            if ((GuardianHomePhone != null) && !(GuardianHomePhone.isEmpty()) && !GuardianHomePhone.equalsIgnoreCase("null")) {
                PreferenceStorage.saveGuardianHomePhone(context, GuardianHomePhone);
            }

            // Parents Preference - Guardian's Mail
            if ((GuardianMail != null) && !(GuardianMail.isEmpty()) && !GuardianMail.equalsIgnoreCase("null")) {
                PreferenceStorage.saveGuardianEmail(context, GuardianMail);
            }

            // Parents Preference - Address
            if ((GuardianAddress != null) && !(GuardianAddress.isEmpty()) && !GuardianAddress.equalsIgnoreCase("null")) {
                PreferenceStorage.saveGuardianAddress(context, GuardianAddress);
            }

            // Parents Preference - Guardian's Occupation
            if ((GuardianOccupation != null) && !(GuardianOccupation.isEmpty()) && !GuardianOccupation.equalsIgnoreCase("null")) {
                PreferenceStorage.saveGuardianOccupation(context, GuardianOccupation);
            }

            // Parents Preference - Guardian's Income
            if ((GuardianIncome != null) && !(GuardianIncome.isEmpty()) && !GuardianIncome.equalsIgnoreCase("null")) {
                PreferenceStorage.saveGuardianIncome(context, GuardianIncome);
            }

            // Parents Preference - Guardian's Home Phone
            if ((GuardianHomePhone != null) && !(GuardianHomePhone.isEmpty()) && !GuardianHomePhone.equalsIgnoreCase("null")) {
                PreferenceStorage.saveGuardianHomePhone(context, GuardianHomePhone);
            }

            // Parents Preference - Guardian's Mobile
            if ((GuardianMobile != null) && !(GuardianMobile.isEmpty()) && !GuardianMobile.equalsIgnoreCase("null")) {
                PreferenceStorage.saveGuardianMobile(context, GuardianMobile);
            }

            // Parents Preference - Guardian's Office Phone
            if ((GuardianOfficePhone != null) && !(GuardianOfficePhone.isEmpty()) && !GuardianOfficePhone.equalsIgnoreCase("null")) {
                PreferenceStorage.saveGuardianOfficePhone(context, GuardianOfficePhone);
            }

            // Parents Preference - Guardian's Relationship
            if ((GuardianRelationship != null) && !(GuardianRelationship.isEmpty()) && !GuardianRelationship.equalsIgnoreCase("null")) {
                PreferenceStorage.saveGuardianRelationship(context, GuardianRelationship);
            }

            // Parents Preference - Guardian's Pic
            GuardianPic = PreferenceStorage.getUserDynamicAPI(context) + ParentimgUrl + GuardianPic;
            if ((GuardianPic != null) && !(GuardianPic.isEmpty()) && !GuardianPic.equalsIgnoreCase("null")) {
                PreferenceStorage.saveGuardianImg(context, GuardianPic);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
