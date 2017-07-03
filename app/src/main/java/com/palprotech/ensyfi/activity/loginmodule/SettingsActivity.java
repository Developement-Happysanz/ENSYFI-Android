package com.palprotech.ensyfi.activity.loginmodule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.palprotech.ensyfi.R;


/**
 * Created by Admin on 25-05-2017.
 */

public class SettingsActivity extends AppCompatActivity {

    TextView txtChangePassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}
