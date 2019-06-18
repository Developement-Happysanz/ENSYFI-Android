package com.palprotech.ensyfi.activity.loginmodule;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.BuildConfig;
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
import com.yalantis.ucrop.UCrop;

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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProfileActivityNew extends AppCompatActivity implements IServiceListener, DialogClickListener, View.OnClickListener {
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 101;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    private static final String TAG = ProfileActivityNew.class.getName();
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

    private File destFile;
    File image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SetUI();
    }

    public static void saveToPreferences(Context context, String key,
                                         Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(ProfileActivityNew.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);

                    }
                });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(ProfileActivityNew.this);

                    }
                });
        alertDialog.show();
    }


    @Override
    public void onRequestPermissionsResult
            (int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale
                                        (this, permission);
                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(ProfileActivityNew.this, ALLOW_KEY, true);
                        }
                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request

        }
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void SetUI() {

        findViewById();

        String url = PreferenceStorage.getUserPicture(this);

        if (((url != null) && !(url.isEmpty()))) {
            Picasso.with(this).load(url).placeholder(R.drawable.ic_profile).error(R.drawable.ic_profile).into(mProfileImage);
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

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                if (getFromPref(this, ALLOW_KEY)) {

                    showSettingsAlert();

                } else if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CAMERA)) {
                        showAlert();
                    } else {
                        // No explanation needed, we can request the permission.
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                }
            }
             else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (getFromPref(this, ALLOW_KEY)) {

                    showSettingsAlert();

                }
                else if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        showAlert();
                    } else {
                        // No explanation needed, we can request the permission.
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                }
            }
            else {
                selectImage();
            }
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
            httppost = new HttpPost(String.format(EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.UPLOAD_PROFILE_IMAGE + Integer.parseInt(PreferenceStorage.getUserId(ProfileActivityNew.this)) + "/" + PreferenceStorage.getUserType(ProfileActivityNew.this)));

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
                    entity.addPart("user_id", new StringBody(PreferenceStorage.getUserId(ProfileActivityNew.this)));
                    entity.addPart("user_type", new StringBody(PreferenceStorage.getUserType(ProfileActivityNew.this)));

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
                Toast.makeText(ProfileActivityNew.this, "Unable to save profile picture", Toast.LENGTH_SHORT).show();
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
                    PreferenceStorage.saveUserPicture(ProfileActivityNew.this, UserPicUrl);

                    if (mProgressDialog != null) {
                        mProgressDialog.cancel();
                    }

                    Toast.makeText(ProfileActivityNew.this, "Profile picture updated successfully", Toast.LENGTH_SHORT).show();
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

//    private void openCamera() {
//        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File file;
//        try {
//            file = getImageFile(); // 1
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Please take another image", Toast.LENGTH_SHORT);
//            return;
//        }
//        Uri uri;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) // 2
//            uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID.concat(".provider"), file);
//        else
//            uri = Uri.fromFile(file); // 3
//        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri); // 4
//        startActivityForResult(pictureIntent, 1);
//    }

    private void openCamera() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
//        if(pictureIntent.resolveActivity(getPackageManager()) != null){
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = getImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Please take another image", Toast.LENGTH_SHORT);
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,                                                                                                    "com.example.android.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(pictureIntent, 1);
            }
//        }
    }

    private void openImagesDocument() {
        Intent pictureIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pictureIntent.setType("image/*");
        pictureIntent.addCategory(Intent.CATEGORY_OPENABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String[] mimeTypes = new String[]{"image/jpeg", "image/png"};
            pictureIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        }
        startActivityForResult(Intent.createChooser(pictureIntent, "Select Picture"), 2);
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivityNew.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
//                    openCamera();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    Uri f = FileProvider.getUriForFile(ProfileActivityNew.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            createImageFile());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, f);
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    openImagesDocument();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
//                Uri uri = Uri.parse(mActualFilePath);
//                openCropActivity(uri, uri);
                final File file = new File(mActualFilePath);
                try {
                    InputStream ims = new FileInputStream(file);
                    mProfileImage.setImageBitmap(BitmapFactory.decodeStream(ims));
                } catch (FileNotFoundException e) {
                    return;
                }

                // ScanFile so it will be appeared on Gallery
                MediaScannerConnection.scanFile(ProfileActivityNew.this,
                        new String[]{mActualFilePath}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
//                                performCrop(uri);
                                Uri destinationUri = Uri.fromFile(file);  // 3
                                openCropActivity(uri, destinationUri);
                            }
                        });
            } else if (requestCode == 2) {
                Uri sourceUri = data.getData(); // 1
                File file = null; // 2
                try {
                    file = getImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri destinationUri = Uri.fromFile(file);  // 3
                openCropActivity(sourceUri, destinationUri);  // 4
            } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
                if (data != null) {
                    Uri uri = UCrop.getOutput(data);
                    mProfileImage.setImageURI(uri);
//                    mActualFilePath = uri.getPath();
                    saveUserImage();
                }
            }
        }
    }
//    private File getImageFile() throws IOException {
//        String imageFileName = "JPEG_" + System.currentTimeMillis() + "_";
//        File storageDir = new File(
//                Environment.getExternalStoragePublicDirectory(
//                        Environment.DIRECTORY_DCIM
//                ), "Camera"
//        );
//        System.out.println(storageDir.getAbsolutePath());
//        if (storageDir.exists())
//            System.out.println("File exists");
//        else
//            System.out.println("File not exists");
//        File file = File.createTempFile(
//                imageFileName, ".png", storageDir
//        );
//        mActualFilePath = "" + file.getAbsolutePath();
//        return file;
//    }

    private File getImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        mActualFilePath = image.getAbsolutePath();
        return image;
    }

    private File createImageFile() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PNG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".png",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        mActualFilePath = image.getAbsolutePath();
        return image;
    }


    private void openCropActivity(Uri sourceUri, Uri destinationUri) {
        UCrop.Options options = new UCrop.Options();
        options.setCircleDimmedLayer(true);
        options.setCropFrameColor(ContextCompat.getColor(this, R.color.appColorBase));
        UCrop.of(sourceUri, destinationUri)
                .withMaxResultSize(100, 100)
                .withAspectRatio(5f, 5f)
                .start(this);
    }

    private void performCrop(Uri picUri) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setType("image/*");
            List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
            int size = list.size();
            if (size == 0) {
                Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();

                return;
            } else {
                mSelectedImageUri = picUri;
                intent.setData(mSelectedImageUri);
                intent.putExtra("outputX", 300);
                intent.putExtra("outputY", 300);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, RESULT_CROP);
            }
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
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

