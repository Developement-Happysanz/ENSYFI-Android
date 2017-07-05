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
    private TextView txtUsrName, txtUserType;
    private EditText txtUsrID, txtMail, txtPassword, numPhone, txtAddress;
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
        txtPassword = (EditText) findViewById(R.id.txtpassword);
        txtPassword.setEnabled(false);
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


        final TextView fatherName = (TextView) findViewById(R.id.txtfathername);
        final TextView fatherAddress = (TextView) findViewById(R.id.txtfatheraddress);
        final TextView fatherMail = (TextView) findViewById(R.id.txtfathermail);
        final TextView fatherOccupation = (TextView) findViewById(R.id.txtfatheroccupation);
        final TextView fatherIncome = (TextView) findViewById(R.id.txtincome);
        final TextView fatherMobile = (TextView) findViewById(R.id.txtfathermobile);
        final TextView fatherOfficePhone = (TextView) findViewById(R.id.txtfatherofficephone);
        final TextView fatherHomePhone = (TextView) findViewById(R.id.txtfatherhomephone);
        final Button btnCancel = (Button) findViewById(R.id.cancel);

        fatherName.setText(PreferenceStorage.getFatherName(getApplicationContext()));
        fatherAddress.setText(PreferenceStorage.getFatherAddress(getApplicationContext()));
        fatherMail.setText(PreferenceStorage.getFatherEmail(getApplicationContext()));
        fatherOccupation.setText(PreferenceStorage.getFatherOccupation(getApplicationContext()));
        fatherIncome.setText(PreferenceStorage.getFatherID(getApplicationContext()));
        fatherMobile.setText(PreferenceStorage.getFatherMobile(getApplicationContext()));
        fatherOfficePhone.setText(PreferenceStorage.getFatherOfficePhone(getApplicationContext()));
        fatherHomePhone.setText(PreferenceStorage.getFatherHomePhone(getApplicationContext()));
        ParentProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentinfo.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentinfo.setVisibility(View.INVISIBLE);
                btnCancel.setVisibility(View.INVISIBLE);
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

