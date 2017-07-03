package com.palprotech.ensyfi.activity.loginmodule;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.parentsmodule.ParentDashBoardActivity;
import com.palprotech.ensyfi.utils.AppValidator;
import com.palprotech.ensyfi.utils.PreferenceStorage;


/**
 * Created by Admin on 22-03-2017.
 */

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3900;
    private static final String TAG = SplashScreenActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        WebView wView = (WebView) findViewById(R.id.web);
        wView.loadUrl("file:///android_asset/ensyfi_logo.gif");
        // disable scroll on touch
        wView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if (PreferenceStorage.getInstituteName(getApplicationContext()) != null && AppValidator.checkNullString(PreferenceStorage.getInstituteName(getApplicationContext()))) {
                    String userName = PreferenceStorage.getUserName(getApplicationContext());
                    String isResetOver = PreferenceStorage.getForgotPasswordStatusEnable(getApplicationContext());
                    String instituteName = PreferenceStorage.getInstituteName(getApplicationContext());
                    String studentName = PreferenceStorage.getStudentNamePreference(getApplicationContext());

                    if (isResetOver.equalsIgnoreCase("no")) {
                        Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (AppValidator.checkNullString(userName) && AppValidator.checkNullString(instituteName)) {
                        String userTypeString = PreferenceStorage.getUserType(getApplicationContext());
                        int userType = Integer.parseInt(userTypeString);
                        if (userType == 1) {

                        } else if (userType == 2) {

                        } else if (userType == 3) {

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
}
