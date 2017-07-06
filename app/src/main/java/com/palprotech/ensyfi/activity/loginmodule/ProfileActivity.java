package com.palprotech.ensyfi.activity.loginmodule;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.utils.PreferenceStorage;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Narendar on 05/04/17.
 */

public class ProfileActivity extends AppCompatActivity implements DialogClickListener {
    private static final String TAG = ProfileActivity.class.getName();
    private ImageView mProfileImage = null;
    private TextView txtUsrName, txtUserType,txtPassword;
    private EditText txtUsrID, txtMail, numPhone, txtAddress;
    private ImageView ParentProfile, GuardianProfile, StudentProfile, motherInfo, fatherInfo;
    private Uri outputFileUri;
    static final int REQUEST_IMAGE_GET = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //new LoadProfile().execute();
        SetUI();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void SetUI() {

        mProfileImage = (ImageView) findViewById(R.id.image_profile_pic);
        txtUsrID = (EditText) findViewById(R.id.userid);
        txtUsrID.setEnabled(false);
        txtPassword = (TextView) findViewById(R.id.password);
        txtUsrName = (TextView) findViewById(R.id.name);
        txtUserType = (TextView) findViewById(R.id.typename);
        txtUsrID.setText(PreferenceStorage.getUserName(getApplicationContext()));
//        txtMail.setText(PreferenceStorage.getEmail(getApplicationContext()));
//        txtPassword.setText(PreferenceStorage.get(getApplicationContext()));
//        txtAddress.setText(PreferenceStorage.getAddress(getApplicationContext()));
//        numPhone.setText(PreferenceStorage.getHomePhone(getApplicationContext()));
        txtUsrName.setText(PreferenceStorage.getName(getApplicationContext()));
        txtUserType.setText(PreferenceStorage.getUserTypeName(getApplicationContext()));
        fatherInfo = (ImageView) findViewById(R.id.img_father_profile);
        motherInfo = (ImageView) findViewById(R.id.img_mother_profile);
        ParentProfile = (ImageView) findViewById(R.id.ic_parentprofile);
        GuardianProfile = (ImageView) findViewById(R.id.ic_guardianprofile);
        StudentProfile = (ImageView) findViewById(R.id.ic_studentprofile);

        final RelativeLayout parentinfo = (RelativeLayout) findViewById(R.id.popup_parent);
        final RelativeLayout guardianinfo = (RelativeLayout) findViewById(R.id.popup_guardian);
        final RelativeLayout studentinfo = (RelativeLayout) findViewById(R.id.popup_student);

        txtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogHelper.showCompoundAlertDialog(ProfileActivity.this, "Change Password", "Password will be Reset. Do you still wish to continue...", "OK", "CANCEL", 1);

            }
        });
        String url = PreferenceStorage.getUserPicture(this);
        if ((url == null) || (url.isEmpty())) {
           /* if ((loginMode == 1) || (loginMode == 3)) {
                url = PreferenceStorage.getSocialNetworkProfileUrl(this);
            } */
        }
        if (((url != null) && !(url.isEmpty()))) {
            Picasso.with(this).load(url).placeholder(R.drawable.profile_pic).error(R.drawable.profile_pic).into(mProfileImage);
        }

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageIntent();

            }
        });

        //////For Parent and Guardian///////
        final TextView Name = (TextView) findViewById(R.id.txtfathername);
        final TextView Address = (TextView) findViewById(R.id.txtfatheraddress);
        final TextView Mail = (TextView) findViewById(R.id.txtfathermail);
        final TextView Occupation = (TextView) findViewById(R.id.txtfatheroccupation);
        final TextView Income = (TextView) findViewById(R.id.txtincome);
        final TextView Mobile = (TextView) findViewById(R.id.txtfathermobile);
        final TextView OfficePhone = (TextView) findViewById(R.id.txtfatherofficephone);
        final TextView HomePhone = (TextView) findViewById(R.id.txtfatherhomephone);
        final Button btnCancel = (Button) findViewById(R.id.cancel);

        //////For Parent and Guardian///////
        final TextView GName = (TextView) findViewById(R.id.txtmothername);
        final TextView GAddress = (TextView) findViewById(R.id.txtmotheraddress);
        final TextView GMail = (TextView) findViewById(R.id.txtmothermail);
        final TextView GOccupation = (TextView) findViewById(R.id.txtmotheroccupation);
        final TextView GIncome = (TextView) findViewById(R.id.txtmotherincome);
        final TextView GMobile = (TextView) findViewById(R.id.txtmothermobile);
        final TextView GOfficePhone = (TextView) findViewById(R.id.txtmotherofficephone);
        final TextView GHomePhone = (TextView) findViewById(R.id.txtmotherhomephone);
        final Button GbtnCancel = (Button) findViewById(R.id.cancel1);

        ////// For Student ///////
        final TextView studentAdmissionId = (TextView) findViewById(R.id.txtstudentadminid);
        final TextView studentAdmissionYear = (TextView) findViewById(R.id.txtstudentadminyear);
        final TextView studentAdmissionNumber = (TextView) findViewById(R.id.txtstudentadminnum);
        final TextView studentEmsiNumber = (TextView) findViewById(R.id.txtstudentemsinum);
        final TextView studentAdmissionDate = (TextView) findViewById(R.id.txtStudentAdmissionDate);
        final TextView studentName = (TextView) findViewById(R.id.txtStudentName);
        final TextView studentGender = (TextView) findViewById(R.id.txtStudentGender);
        final TextView studentDateOfBirth = (TextView) findViewById(R.id.txtStudentDateOfBirth);
        final TextView studentAge = (TextView) findViewById(R.id.txtStudentAge);
        final TextView studentNationality = (TextView) findViewById(R.id.txtStudentNationality);
        final TextView studentReligion = (TextView) findViewById(R.id.txtStudentReligion);
        final TextView studentCaste = (TextView) findViewById(R.id.txtStudentCaste);
        final TextView studentCommunity = (TextView) findViewById(R.id.txtStudentCommunity);
        final TextView studentParentOrGuardian = (TextView) findViewById(R.id.txtStudentParentOrGuardian);
        final TextView studentParentOrGuardianId = (TextView) findViewById(R.id.txtStudentParentOrGuardianId);
        final TextView studentMotherTongue = (TextView) findViewById(R.id.txtStudentMotherTongue);
        final TextView studentLanguage = (TextView) findViewById(R.id.txtStudentLanguage);
        final TextView studentMobile = (TextView) findViewById(R.id.txtStudentMobile);
        final TextView studentSecondaryMobile = (TextView) findViewById(R.id.txtStudentSecondaryMobile);
        final TextView studentMail = (TextView) findViewById(R.id.txtStudentMail);
        final TextView studentSecondaryMail = (TextView) findViewById(R.id.txtStudentSecondaryMail);
        final TextView studentPreviousSchool = (TextView) findViewById(R.id.txtStudentPreviousSchool);
        final TextView studentPreviousClass = (TextView) findViewById(R.id.txtStudentPreviousClass);
        final TextView studentPromotionStatus = (TextView) findViewById(R.id.txtStudentPromotionStatus);
        final TextView studentTransferCertificate = (TextView) findViewById(R.id.txtStudentTransferCertificate);
        final TextView studentRecordSheet = (TextView) findViewById(R.id.txtStudentRecordSheet);
        final TextView studentStatus = (TextView) findViewById(R.id.txtStudentStatus);
        final TextView studentParentStatus = (TextView) findViewById(R.id.txtStudentParentStatus);
        final TextView studentRegistered = (TextView) findViewById(R.id.txtStudentRegistered);
        final Button SbtnCancel = (Button) findViewById(R.id.cancel2);


        Name.setText(PreferenceStorage.getFatherName(getApplicationContext()));
        Address.setText(PreferenceStorage.getFatherAddress(getApplicationContext()));
        Mail.setText(PreferenceStorage.getFatherEmail(getApplicationContext()));
        Occupation.setText(PreferenceStorage.getFatherOccupation(getApplicationContext()));
        Income.setText(PreferenceStorage.getFatherID(getApplicationContext()));
        Mobile.setText(PreferenceStorage.getFatherMobile(getApplicationContext()));
        OfficePhone.setText(PreferenceStorage.getFatherOfficePhone(getApplicationContext()));
        HomePhone.setText(PreferenceStorage.getFatherHomePhone(getApplicationContext()));
        ParentProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentinfo.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
            }
        });

        GuardianProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardianinfo.setVisibility(View.VISIBLE);
                GbtnCancel.setVisibility(View.VISIBLE);
                Name.setText(PreferenceStorage.getGuardianName(getApplicationContext()));
                Address.setText(PreferenceStorage.getGuardianAddress(getApplicationContext()));
                Mail.setText(PreferenceStorage.getGuardianEmail(getApplicationContext()));
                Occupation.setText(PreferenceStorage.getGuardianOccupation(getApplicationContext()));
                Income.setText(PreferenceStorage.getGuardianID(getApplicationContext()));
                Mobile.setText(PreferenceStorage.getGuardianMobile(getApplicationContext()));
                OfficePhone.setText(PreferenceStorage.getGuardianOfficePhone(getApplicationContext()));
                HomePhone.setText(PreferenceStorage.getGuardianHomePhone(getApplicationContext()));
            }
        });

        StudentProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentinfo.setVisibility(View.VISIBLE);
                SbtnCancel.setVisibility(View.VISIBLE);
                studentAdmissionId.setText(PreferenceStorage.getStudentAdmissionID(getApplicationContext()));
                studentAdmissionYear.setText(PreferenceStorage.getStudentAdmissionYear(getApplicationContext()));
                studentAdmissionNumber.setText(PreferenceStorage.getStudentAdmissionNumber(getApplicationContext()));
                studentEmsiNumber.setText(PreferenceStorage.getStudentEmsiNumber(getApplicationContext()));
                studentAdmissionDate.setText(PreferenceStorage.getStudentAdmissionDate(getApplicationContext()));
                studentName.setText(PreferenceStorage.getStudentName(getApplicationContext()));
                studentGender.setText(PreferenceStorage.getStudentGender(getApplicationContext()));
                studentDateOfBirth.setText(PreferenceStorage.getStudentDateOfBirth(getApplicationContext()));
                studentAge.setText(PreferenceStorage.getStudentAge(getApplicationContext()));
                studentNationality.setText(PreferenceStorage.getStudentNationality(getApplicationContext()));
                studentReligion.setText(PreferenceStorage.getStudentReligion(getApplicationContext()));
                studentCaste.setText(PreferenceStorage.getStudentCaste(getApplicationContext()));
                studentCommunity.setText(PreferenceStorage.getStudentCommunity(getApplicationContext()));
                studentParentOrGuardian.setText(PreferenceStorage.getStudentParentOrGuardian(getApplicationContext()));
                studentParentOrGuardianId.setText(PreferenceStorage.getStudentParentOrGuardianID(getApplicationContext()));
                studentMotherTongue.setText(PreferenceStorage.getStudentMotherTongue(getApplicationContext()));
                studentLanguage.setText(PreferenceStorage.getStudentLanguage(getApplicationContext()));
                studentMobile.setText(PreferenceStorage.getStudentMobile(getApplicationContext()));
                studentSecondaryMobile.setText(PreferenceStorage.getStudentSecondaryMobile(getApplicationContext()));
                studentMail.setText(PreferenceStorage.getStudentMail(getApplicationContext()));
                studentSecondaryMail.setText(PreferenceStorage.getStudentSecondaryMail(getApplicationContext()));
                studentPreviousSchool.setText(PreferenceStorage.getStudentPreviousSchool(getApplicationContext()));
                studentPreviousClass.setText(PreferenceStorage.getStudentPreviousClass(getApplicationContext()));
                studentPromotionStatus.setText(PreferenceStorage.getStudentPromotionStatus(getApplicationContext()));
                studentTransferCertificate.setText(PreferenceStorage.getStudentTransferCertificate(getApplicationContext()));
                studentRecordSheet.setText(PreferenceStorage.getStudentRecordSheet(getApplicationContext()));
                studentStatus.setText(PreferenceStorage.getStudentStatus(getApplicationContext()));
                studentParentStatus.setText(PreferenceStorage.getStudentParentStatus(getApplicationContext()));
                studentRegistered.setText(PreferenceStorage.getStudentRegistered(getApplicationContext()));
            }
        });

        fatherInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name.setText(PreferenceStorage.getFatherName(getApplicationContext()));
                Address.setText(PreferenceStorage.getFatherAddress(getApplicationContext()));
                Mail.setText(PreferenceStorage.getFatherEmail(getApplicationContext()));
                Occupation.setText(PreferenceStorage.getFatherOccupation(getApplicationContext()));
                Income.setText(PreferenceStorage.getFatherID(getApplicationContext()));
                Mobile.setText(PreferenceStorage.getFatherMobile(getApplicationContext()));
                OfficePhone.setText(PreferenceStorage.getFatherOfficePhone(getApplicationContext()));
                HomePhone.setText(PreferenceStorage.getFatherHomePhone(getApplicationContext()));
            }
        });

        motherInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name.setText(PreferenceStorage.getMotherName(getApplicationContext()));
                Address.setText(PreferenceStorage.getMotherAddress(getApplicationContext()));
                Mail.setText(PreferenceStorage.getMotherEmail(getApplicationContext()));
                Occupation.setText(PreferenceStorage.getMotherOccupation(getApplicationContext()));
                Income.setText(PreferenceStorage.getMotherID(getApplicationContext()));
                Mobile.setText(PreferenceStorage.getMotherMobile(getApplicationContext()));
                OfficePhone.setText(PreferenceStorage.getMotherOfficePhone(getApplicationContext()));
                HomePhone.setText(PreferenceStorage.getMotherHomePhone(getApplicationContext()));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentinfo.setVisibility(View.INVISIBLE);
                btnCancel.setVisibility(View.INVISIBLE);
            }
        });

        GbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardianinfo.setVisibility(View.INVISIBLE);
                GbtnCancel.setVisibility(View.INVISIBLE);
            }
        });

        SbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentinfo.setVisibility(View.INVISIBLE);
                SbtnCancel.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void openImageIntent() {

// Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyDir");

        if (!root.exists()) {
            if (!root.mkdirs()) {
                Log.d(TAG, "Failed to create directory for storing images");
                return;
            }
        }

        final String fname = PreferenceStorage.getUserId(this) + ".png";
        final File sdImageMainDirectory = new File(root.getPath() + File.separator + fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);
        Log.d(TAG, "camera output Uri" + outputFileUri);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Profile Photo");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, REQUEST_IMAGE_GET);
    }


    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}

