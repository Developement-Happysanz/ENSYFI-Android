package com.palprotech.ensyfi.activity.loginmodule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.studentmodule.FeeStatusActivity;
import com.palprotech.ensyfi.bean.student.support.SaveStudentData;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.AndroidMultiPartEntity;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Narendar on 05/04/17.
 */

public class ProfileActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, View.OnClickListener {

    private static final String TAG = ProfileActivity.class.getName();
    private ImageView mProfileImage = null, btnBack;
    private TextView txtUsrName, txtUserType;
    private Button txtPassword;

    private TextView TeacherProfile;

    private ServiceHelper serviceHelper;
    private SaveStudentData studentData;
    private EditText txtUsrID;
    private ImageView fatherInfo, motherInfo, guardianImg, studentImg, teacherImg;
    private Uri outputFileUri;
    static final int REQUEST_IMAGE_GET = 1;
    static final int RESULT_CROP = 99;
    protected ProgressDialogHelper progressDialogHelper;
    RelativeLayout TeacherInfo, parentInfoPopup, guardianInfoPopup, studentInfoPopup, teacherInfoPopup;
    LinearLayout ParentInfo, ParentProfile, GuardianProfile, StudentProfile, FeeStatusView;
    Button btnCancel, GbtnCancel, tbtnCancel, SbtnCancel, btnSave;
    private String mActualFilePath = null;
    private Uri mSelectedImageUri = null;
    private Bitmap mCurrentUserImageBitmap = null;
    private ProgressDialog mProgressDialog = null;
    private String mUpdatedImageUrl = null;
    private UploadFileToServer mUploadTask = null;
    long totalSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SetUI();
    }

    private void SetUI() {

        findViewById();

        String url = PreferenceStorage.getUserPicture(this);

        if (((url != null) && !(url.isEmpty()))) {
            Picasso.get().load(url).placeholder(R.drawable.ic_profile).error(R.drawable.ic_profile).into(mProfileImage);
        }

        String userTypeString = PreferenceStorage.getUserType(getApplicationContext());
        int userType = Integer.parseInt(userTypeString);
        if (userType == 1) {
            TeacherInfo.setVisibility(View.GONE);
            ParentInfo.setVisibility(View.GONE);
            FeeStatusView.setVisibility(View.GONE);
        } else if (userType == 2) {
            TeacherInfo.setVisibility(View.VISIBLE);
            ParentInfo.setVisibility(View.GONE);
        } else if (userType == 3) {
            ParentInfo.setVisibility(View.VISIBLE);
        } else {
            ParentInfo.setVisibility(View.VISIBLE);
            FeeStatusView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        if (v == btnBack) {
            finish();
        }
        if (v == mProfileImage) {
            openImageIntent();
        }
        if (v == txtPassword) {
            AlertDialogHelper.showCompoundAlertDialog(this, "Change Password", "Password will be Reset. Do you still wish to continue...", "OK", "CANCEL", 1);
        }
        if (v == fatherInfo) {
//            callFatherInfoPreferences();
        }
        if (v == motherInfo) {
//            callMotherInfoPreferences();
        }
        if (v == FeeStatusView) {
            Intent intent = new Intent(getApplicationContext(), FeeStatusActivity.class);
            startActivity(intent);
        }
        if (v == ParentProfile) {
            Intent intent = new Intent(getApplicationContext(), ParentInfoActivity.class);
            startActivity(intent);
//            parentInfoPopup.setVisibility(View.VISIBLE);
//            btnCancel.setVisibility(View.VISIBLE);
        }
        if (v == btnCancel) {
//            parentInfoPopup.setVisibility(View.INVISIBLE);
//            btnCancel.setVisibility(View.INVISIBLE);
        }
        if (v == GuardianProfile) {
            Intent intent = new Intent(getApplicationContext(), GuardianInfoActivity.class);
            startActivity(intent);
//            callGuardianInfoPreferences();
        }
        if (v == GbtnCancel) {
//            guardianInfoPopup.setVisibility(View.INVISIBLE);
//            GbtnCancel.setVisibility(View.INVISIBLE);
        }
        if (v == StudentProfile) {
            Intent intent = new Intent(getApplicationContext(), StudentInfoActivity.class);
            startActivity(intent);
//            studentInfoPopup.setVisibility(View.VISIBLE);
//            SbtnCancel.setVisibility(View.VISIBLE);
//
            String userTypeString = PreferenceStorage.getUserType(getApplicationContext());
            int userType = Integer.parseInt(userTypeString);

            if (userType == 3) {
//                callStudentInfoPreferences();
            } else {
                callGetStudentInfoService();
//                callStudentInfoPreferences();
            }
        }
        if (v == SbtnCancel) {
            studentInfoPopup.setVisibility(View.INVISIBLE);
            SbtnCancel.setVisibility(View.INVISIBLE);
        }
        if (v == TeacherProfile) {
            Intent intent = new Intent(getApplicationContext(), TeacherInfoActivity.class);
            startActivity(intent);
//            callTeacherInfoPreferences();
        }
        if (v == tbtnCancel) {
            teacherInfoPopup.setVisibility(View.INVISIBLE);
            tbtnCancel.setVisibility(View.INVISIBLE);
        }
        if (v == btnSave) {
            saveUserProfile();
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialogHelper.hideProgressDialog();

        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }

        if (validateSignInResponse(response)) {
            try {

                JSONArray getData = response.getJSONArray("studentProfile");
                studentData.saveStudentProfile(getData);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(this, error);
    }

    private void saveUserProfile() {

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Updating Profile Picture...");
        mProgressDialog.show();
        if ((mActualFilePath != null)) {
            Log.d(TAG, "Update profile picture");
            saveUserImage();
        }
    }

    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        private static final String TAG = "UploadFileToServer";
        private HttpClient httpclient;
        HttpPost httppost;
        public boolean isTaskAborted = false;

        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

            // Making progress bar visible
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(String.format(EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.UPLOAD_PROFILE_IMAGE + Integer.parseInt(PreferenceStorage.getUserId(ProfileActivity.this)) + "/" + PreferenceStorage.getUserType(ProfileActivity.this)));

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {

                            }
                        });
                Log.d(TAG, "actual file path is" + mActualFilePath);
                if (mActualFilePath != null) {

                    File sourceFile = new File(mActualFilePath);

                    // Adding file data to http body
                    //fileToUpload
                    entity.addPart("user_pic", new FileBody(sourceFile));

                    // Extra parameters if you want to pass to server
                    entity.addPart("user_id", new StringBody(PreferenceStorage.getUserId(ProfileActivity.this)));
                    entity.addPart("user_type", new StringBody(PreferenceStorage.getUserType(ProfileActivity.this)));

                    totalSize = entity.getContentLength();
                    httppost.setEntity(entity);

                    // Making server call
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity r_entity = response.getEntity();

                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        // Server response
                        responseString = EntityUtils.toString(r_entity);
                        try {
                            JSONObject resp = new JSONObject(responseString);
                            String successVal = resp.getString("status");

                            mUpdatedImageUrl = resp.getString("user_picture");

                            Log.d(TAG, "updated image url is" + mUpdatedImageUrl);
                            if (successVal.equalsIgnoreCase("success")) {
                                Log.d(TAG, "Updated image succesfully");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        responseString = "Error occurred! Http Status Code: "
                                + statusCode;
                    }
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "Response from server: " + result);

            super.onPostExecute(result);
            if ((result == null) || (result.isEmpty()) || (result.contains("Error"))) {
                Toast.makeText(ProfileActivity.this, "Unable to save profile picture", Toast.LENGTH_SHORT).show();
            } else {
                if (mUpdatedImageUrl != null) {
                    String userTypeString = PreferenceStorage.getUserType(getApplicationContext()); //Get userType for generate user image url
                    String imageURL = "";
                    String UserPicUrl = "";
                    int userType = Integer.parseInt(userTypeString);
                    if (userType == 1) {
                        imageURL = EnsyfiConstants.USER_IMAGE_API_ADMIN; // Admin user image url
                    } else if (userType == 2) {
                        imageURL = EnsyfiConstants.USER_IMAGE_API_TEACHERS; // Teacher user image url
                    } else if (userType == 3) {
                        imageURL = EnsyfiConstants.USER_IMAGE_API_STUDENTS; // Student user image url
                    } else {
                        imageURL = EnsyfiConstants.USER_IMAGE_API_PARENTS; // Parents user image url
                    }
                    UserPicUrl = PreferenceStorage.getUserDynamicAPI(getApplicationContext()) + imageURL + mUpdatedImageUrl; // Generate user image url
                    PreferenceStorage.saveUserPicture(ProfileActivity.this, UserPicUrl);

                    if (mProgressDialog != null) {
                        mProgressDialog.cancel();
                    }

                    Toast.makeText(ProfileActivity.this, "Profile picture updated successfully", Toast.LENGTH_SHORT).show();
                    btnSave.setVisibility(View.GONE);
                }
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        public void taskCancel() {
            if (httppost != null) {
                isTaskAborted = true;
                httppost.abort();
                httppost = null;
            }
            if (httpclient != null) {
                isTaskAborted = true;
                httpclient.getConnectionManager().shutdown();
            }
            httpclient = null;
        }
    }

    private void saveUserImage() {
        mUpdatedImageUrl = null;

        new UploadFileToServer().execute();

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mActualFilePath = "file:" + image.getAbsolutePath();
        return image;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_IMAGE_GET) {
                Log.d(TAG, "ONActivity Result");
                final boolean isCamera;
                if (data == null) {
                    Log.d(TAG, "camera is true");
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    Log.d(TAG, "camera action is" + action);
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                Uri selectedImageUri;

                if (isCamera) {
                    Log.d(TAG, "Add to gallery");
                    selectedImageUri = outputFileUri;
                    mActualFilePath = outputFileUri.getPath();
                    galleryAddPic(selectedImageUri);
                } else {
                    selectedImageUri = data == null ? null : data.getData();
                    mActualFilePath = getRealPathFromURI(this, selectedImageUri);
                    Log.d(TAG, "path to image is" + mActualFilePath);

                }
                Log.d(TAG, "image Uri is" + selectedImageUri);
                if (selectedImageUri != null) {
                    Log.d(TAG, "image URI is" + selectedImageUri);
//                    setPic(selectedImageUri);
                    performCrop(selectedImageUri);
                }
            }
            if (requestCode == RESULT_CROP ) {
                if(resultCode == Activity.RESULT_OK){
                    Bundle extras = data.getExtras();
                    Bitmap selectedBitmap = extras.getParcelable("data");
                    // Set The Bitmap Data To ImageView
                    mProfileImage.setImageBitmap(selectedBitmap);
                    mProfileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    saveUserImage();
                }
            }
        }
    }

    private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties here
            cropIntent.putExtra("crop", true);
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void galleryAddPic(Uri urirequest) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(urirequest.getPath());
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        String result = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);


            Cursor cursor = loader.loadInBackground();
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                result = cursor.getString(column_index);
                cursor.close();
            } else {
                Log.d(TAG, "cursor is null");
            }
        } catch (Exception e) {
            result = null;
            Toast.makeText(this, "Was unable to save  image", Toast.LENGTH_SHORT).show();

        } finally {
            return result;
        }

    }

    private void setPic(Uri selectedImageUri) {
        // Get the dimensions of the View
        int targetW = mProfileImage.getWidth();
        int targetH = mProfileImage.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(this.getContentResolver().openInputStream(selectedImageUri), null, bmOptions);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        mSelectedImageUri = selectedImageUri;

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(selectedImageUri), null, bmOptions);
            mProfileImage.setImageBitmap(bitmap);
            mCurrentUserImageBitmap = bitmap;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        btnSave.setVisibility(View.VISIBLE);
    }

    private void callGetStudentInfoService() {

        if (CommonUtils.isNetworkAvailable(this)) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.STUDENT_ADMISSION_ID, PreferenceStorage.getStudentAdmissionIdPreference(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(this) + EnsyfiConstants.GET_STUDENT_INFO_DETAILS_API;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
        } else {

            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private boolean validateSignInResponse(JSONObject response) {
        boolean signInSuccess = false;
        if ((response != null)) {
            try {
                String status = response.getString("status");
                String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                Log.d(TAG, "status val" + status + "msg" + msg);

                if ((status != null)) {
                    if (((status.equalsIgnoreCase("activationError")) || (status.equalsIgnoreCase("alreadyRegistered")) ||
                            (status.equalsIgnoreCase("notRegistered")) || (status.equalsIgnoreCase("error")))) {
                        signInSuccess = false;
                        Log.d(TAG, "Show error dialog");
                        AlertDialogHelper.showSimpleAlertDialog(this, msg);

                    } else {
                        signInSuccess = true;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return signInSuccess;
    }

    private void findViewById() {

        mProfileImage = (ImageView) findViewById(R.id.image_profile_pic);
        mProfileImage.setOnClickListener(this);

        btnBack = (ImageView) findViewById(R.id.back_res);
        btnBack.setOnClickListener(this);

        txtUsrID = (EditText) findViewById(R.id.userid);
        txtUsrID.setEnabled(false);

        txtPassword = (Button) findViewById(R.id.password);
        txtPassword.setOnClickListener(this);

        txtUsrName = (TextView) findViewById(R.id.name);
        txtUserType = (TextView) findViewById(R.id.typename);
        progressDialogHelper = new ProgressDialogHelper(this);
        txtUsrID.setText(PreferenceStorage.getUserName(getApplicationContext()));
        txtUsrName.setText(PreferenceStorage.getName(getApplicationContext()));
        txtUserType.setText(PreferenceStorage.getUserTypeName(getApplicationContext()));

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        ParentProfile = (LinearLayout) findViewById(R.id.ic_parentprofile);
        ParentProfile.setOnClickListener(this);

        GuardianProfile = (LinearLayout) findViewById(R.id.ic_guardianprofile);
        GuardianProfile.setOnClickListener(this);

        TeacherProfile = (TextView) findViewById(R.id.ic_teacherprofile);
        TeacherProfile.setOnClickListener(this);

        StudentProfile = (LinearLayout) findViewById(R.id.ic_studentprofile);
        StudentProfile.setOnClickListener(this);

        FeeStatusView = (LinearLayout) findViewById(R.id.ic_feestatus);
        FeeStatusView.setOnClickListener(this);

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        studentData = new SaveStudentData(this);

        ParentInfo = (LinearLayout) findViewById(R.id.parentStudentView);
        TeacherInfo = (RelativeLayout) findViewById(R.id.teacherprofile);
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}

