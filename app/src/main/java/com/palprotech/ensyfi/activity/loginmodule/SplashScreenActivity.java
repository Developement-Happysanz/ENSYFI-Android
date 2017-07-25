package com.palprotech.ensyfi.activity.loginmodule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.adminmodule.AdminDashBoardActivity;
import com.palprotech.ensyfi.activity.parentsmodule.ParentDashBoardActivity;
import com.palprotech.ensyfi.activity.teachermodule.TeacherDashBoardActivity;
import com.palprotech.ensyfi.servicehelpers.gcm.GCMRegistrationIntentService;
import com.palprotech.ensyfi.utils.AppValidator;
import com.palprotech.ensyfi.utils.PreferenceStorage;


/**
 * Created by Admin on 22-03-2017.
 */

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3900;
    private static final String TAG = SplashScreenActivity.class.getName();

    //Creating a broadcast receiver for gcm registration
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    String IMEINo;
    String regId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        WebView wView = (WebView) findViewById(R.id.web);
        wView.getSettings();
        wView.setBackgroundColor(Color.TRANSPARENT);
        wView.loadUrl("file:///android_asset/ensyfi.gif");

        // disable scroll on touch
        wView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        IMEINo = telephonyManager.getDeviceId();
//
//        PreferenceStorage.saveIMEI(getApplicationContext(), IMEINo);

        //Initializing our broadcast receiver
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {

            //When the broadcast received
            //We are sending the broadcast from GCMRegistrationIntentService

            @Override
            public void onReceive(Context context, Intent intent) {
                //If the broadcast has received with success
                //that means device is registered successfully
                if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_SUCCESS)){
                    //Getting the registration token from the intent
                    String token = intent.getStringExtra("token");

                    regId = intent.getStringExtra("token");

                    PreferenceStorage.saveGCM(getApplicationContext(), regId);
                    //Displaying the token as toast
//                    Toast.makeText(getApplicationContext(), "Registration token:" + token, Toast.LENGTH_LONG).show();

                    //if the intent is not with success then displaying error messages
                } else if(intent.getAction().equals(GCMRegistrationIntentService.REGISTRATION_ERROR)){
                    Toast.makeText(getApplicationContext(), "GCM registration error!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error occurred", Toast.LENGTH_LONG).show();
                }
            }
        };

        //Checking play service is available or not
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        //if play service is not available
        if(ConnectionResult.SUCCESS != resultCode) {
            //If play service is supported but not installed
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                //Displaying message that play service is not installed
                Toast.makeText(getApplicationContext(), "Google Play Service is not install/enabled in this device!", Toast.LENGTH_LONG).show();
                GooglePlayServicesUtil.showErrorNotification(resultCode, getApplicationContext());

                //If play service is not supported
                //Displaying an error message
            } else {
                Toast.makeText(getApplicationContext(), "This device does not support for Google Play Service!", Toast.LENGTH_LONG).show();
            }

            //If play service is available
        } else {
            //Starting intent to register device
            Intent itent = new Intent(this, GCMRegistrationIntentService.class);
            startService(itent);
        }



        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if (PreferenceStorage.getInstituteName(getApplicationContext()) != null && AppValidator.checkNullString(PreferenceStorage.getInstituteName(getApplicationContext()))) {
                    String userName = PreferenceStorage.getUserName(getApplicationContext());
                    String isResetOver = PreferenceStorage.getForgotPasswordStatusEnable(getApplicationContext());
                    String instituteName = PreferenceStorage.getInstituteName(getApplicationContext());

                    if (isResetOver.equalsIgnoreCase("no")) {
                        Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (AppValidator.checkNullString(userName) && AppValidator.checkNullString(instituteName)) {
                        String userTypeString = PreferenceStorage.getUserType(getApplicationContext());
                        int userType = Integer.parseInt(userTypeString);
                        if (userType == 1) {
                            Intent intent = new Intent(getApplicationContext(), AdminDashBoardActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (userType == 2) {
                            Intent intent = new Intent(getApplicationContext(), TeacherDashBoardActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (userType == 3) {
                            Intent intent = new Intent(getApplicationContext(), ParentDashBoardActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), ParentDashBoardActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else if (AppValidator.checkNullString(instituteName)) {
                        Log.d(TAG, "No institute name yet, show user activity activity");

                        Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    } else if (AppValidator.checkNullString(userName)) {
                        Log.d(TAG, "No preferences, so launch preferences activity");
                        Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                        //intent.putExtra("selectedCity", userName);
                        startActivity(intent);
                        //this.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                        finish();
                    }
//                    else if(AppValidator.checkNullString(studentName)){
//                        Log.d(TAG, "No preferences, so launch preferences activity");
//                        Intent intent = new Intent(getApplicationContext(), StudentInfoActivity.class);
//                        //intent.putExtra("selectedCity", userName);
//                        startActivity(intent);
//                        //this.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
//                        finish();
//                    }
                } else {
                    Intent i = new Intent(SplashScreenActivity.this, SchoolIdLoginActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        }, SPLASH_TIME_OUT);

    }

    //Registering receiver on activity resume
    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }


    //Unregistering receiver on activity paused
    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }
}
