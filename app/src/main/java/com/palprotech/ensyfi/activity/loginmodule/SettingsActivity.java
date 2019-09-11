package com.palprotech.ensyfi.activity.loginmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.adminmodule.ClassAttendanceActivity;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Admin on 25-05-2017.
 */

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, IServiceListener, DialogClickListener {

    private LinearLayout profileLayout, changePassLayout, notificationLayout, subNotification, aboutLayout;
    private Switch push, sms, mail;
    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    String res = "";
    boolean vis = false;
    String tt = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        profileLayout = findViewById(R.id.profile_layout);
        changePassLayout = findViewById(R.id.password_layout);
        notificationLayout = findViewById(R.id.notification_layout);
        subNotification = findViewById(R.id.sub_notification_layout);
        aboutLayout = findViewById(R.id.about_layout);
        push = findViewById(R.id.push_noti_switch);
        sms = findViewById(R.id.sms_noti_switch);
        mail = findViewById(R.id.mail_noti_switch);
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        profileLayout.setOnClickListener(this);
        changePassLayout.setOnClickListener(this);
        if (PreferenceStorage.getUserType(this).equalsIgnoreCase("1")) {
            notificationLayout.setVisibility(View.GONE);
        } else {
            notificationLayout.setOnClickListener(this);
        }
        subNotification.setOnClickListener(this);
        aboutLayout.setOnClickListener(this);
        push.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                sendNotif("push");
            }
        });
        sms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                sendNotif("sms");
            }
        });
        mail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                sendNotif("mail");
            }
        });
        checkNotif();

    }

    private void sendNotif(String type) {
        res = "update";
        String notifytype = "";
        boolean stat = false;

        JSONObject jsonObject = new JSONObject();
        switch (type) {
            case "push" :
                notifytype = "PUSH";
                stat = push.isChecked();
                break;
            case "sms" :
                notifytype = "SMS";
                stat = sms.isChecked();
                break;
            case "mail" :
                notifytype = "MAIL";
                stat = mail.isChecked();
                break;
        }
        String stt = "";
        if (stat) {
            stt = "Y";
        } else {
            stt = "N";
        }
        tt = notifytype;
        try {

            jsonObject.put(EnsyfiConstants.KEY_USER_ID, PreferenceStorage.getUserId(this));
            jsonObject.put(EnsyfiConstants.NOTIFICATION_TYPE, notifytype);
            jsonObject.put(EnsyfiConstants.PARAMS_OD_STATUS, stt);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
        String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(this) + EnsyfiConstants.USER_NOTIFICATION;
        serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
    }

    private void checkNotif() {
        res = "status";
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(EnsyfiConstants.KEY_USER_ID, PreferenceStorage.getUserId(this));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
        String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(this) + EnsyfiConstants.USER_NOTIFICATION_STATUS;
        serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
    }

    @Override
    public void onClick(View view) {
        if (view == profileLayout) {
            Intent navigationIntent = new Intent(this, ProfileActivityNew.class);
            navigationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(navigationIntent);
        } else if (view == changePassLayout) {
            Intent navigationIntent = new Intent(this, ChangePasswordActivity.class);
            navigationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(navigationIntent);
        } else if (view == notificationLayout) {
            if (vis) {
                subNotification.setVisibility(View.GONE);
                vis = false;
            } else {
                subNotification.setVisibility(View.VISIBLE);
                vis = true;
            }
        } else if (view == aboutLayout) {
            Intent navigationIntent = new Intent(this, ChangePasswordActivity.class);
            navigationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(navigationIntent);
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        try {
            String status = response.getString("status");
            if (status.equalsIgnoreCase("success")) {
                if (res.equalsIgnoreCase("status")) {
                    String statusPush = response.getJSONArray("notificationStatus").getJSONObject(0).getString("mail_prefs");
                    String statusSMS = response.getJSONArray("notificationStatus").getJSONObject(0).getString("sms_prefs");
                    String statusMail = response.getJSONArray("notificationStatus").getJSONObject(0).getString("push_prefs");
                    if (statusPush.equalsIgnoreCase("Y")) {
                        push.setChecked(true);
                    } else {
                        push.setChecked(false);
                    }
                    if (statusSMS.equalsIgnoreCase("Y")) {
                        sms.setChecked(true);
                    }else {
                        sms.setChecked(false);
                    }
                    if (statusMail.equalsIgnoreCase("Y")) {
                        mail.setChecked(true);
                    }else {
                        mail.setChecked(false);
                    }
                } else if (res.equalsIgnoreCase("update")){
                    String msg = response.getString("msg");

                    Toast.makeText(this, tt+" "+msg, Toast.LENGTH_SHORT).show();
                }
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String error) {

    }
}
