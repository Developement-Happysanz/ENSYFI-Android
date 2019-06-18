package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherCtHwDaywise;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassTeacherCtHwDetailView extends AppCompatActivity implements IServiceListener, DialogClickListener, View.OnClickListener{

    private static final String TAG = "ClassTeacherView";

    private ClassTeacherCtHwDaywise classTeacherCtHwDaywise;
    protected boolean isLoadingForFirstTime = true;
    TextView txtTitle, txtSubject, txtType, txtName, txtDescription;
    int totalCount = 0;
    Handler mHandler = new Handler();
    ServiceHelper serviceHelper;
    ProgressDialogHelper progressDialogHelper;

    private RelativeLayout reportPopup;
    private ImageView close, openPopup;
    private String checkSpinner = "";
    CheckBox sms, mail, notification;
    Boolean smsSelect = false, mailSelect = false, notificationSelect = false;
    Button sendNotification;
    EditText notes;
    private String message = "";
    ArrayList<String> notificationTypes = new ArrayList<>();
    private String message_type_sms = "SMS", message_type_mail = "Mail", message_type_notification = "Notification";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_teacher_ct_hw_detail);
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SetUI();
    }
    private void SetUI() {

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);

        progressDialogHelper = new ProgressDialogHelper(this);

        classTeacherCtHwDaywise = (ClassTeacherCtHwDaywise) getIntent().getSerializableExtra("attendanceObj");

        txtTitle = (TextView) findViewById(R.id.txt_ct_hw_title);
        txtTitle.setText(": " + classTeacherCtHwDaywise.getTitle());

        txtSubject = (TextView) findViewById(R.id.txt_ct_hw_subject);
        txtSubject.setText(": " + classTeacherCtHwDaywise.getSubject_name());

        txtType = (TextView) findViewById(R.id.txt_ct_hw_type);
        if (classTeacherCtHwDaywise.getHw_type().equalsIgnoreCase("HT")) {
            txtType.setText(": Classtest");
        } else {
            txtType.setText(": Homework");
        }

        txtName = (TextView) findViewById(R.id.txt_ct_hw_teacher_name);
        txtName.setText(": " + classTeacherCtHwDaywise.getName());

        txtDescription = (TextView) findViewById(R.id.txt_ct_hw_description);
        txtDescription.setText(": " + classTeacherCtHwDaywise.getHw_details());

        openPopup = (ImageView) findViewById(R.id.send);
        if (classTeacherCtHwDaywise.getSend_option_status().equalsIgnoreCase("1")) {
            openPopup.setVisibility(View.GONE);
        } else {
            openPopup.setVisibility(View.VISIBLE);
        }
        openPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportPopup.setVisibility(View.VISIBLE);
            }
        });
        reportPopup = (RelativeLayout) findViewById(R.id.report_popup);
        close = (ImageView) findViewById(R.id.close_popup);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportPopup.setVisibility(View.GONE);
            }
        });

        sms = (CheckBox) findViewById(R.id.sms_check);
//        sms.setOnClickListener(this);
        mail = (CheckBox) findViewById(R.id.mail_check);
//        mail.setOnClickListener(this);
        notification = (CheckBox) findViewById(R.id.notification_check);
//        notification.setOnClickListener(this);

        sendNotification = (Button) findViewById(R.id.send_message);
        sendNotification.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
//        if (v == sms) {
//            if (smsSelect) {
//                sms.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_unchecked, 0, 0, 0);
//                smsSelect = false;
//                notificationTypes.remove(message_type_sms);
//            } else {
//                sms.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_checked, 0, 0, 0);
//                smsSelect = true;
//                notificationTypes.add(message_type_sms);
//
//            }
//        }
//        if (v == mail) {
//            if (mailSelect) {
//                mail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_unchecked, 0, 0, 0);
//                mailSelect = false;
//                notificationTypes.remove(message_type_mail);
//            } else {
//                mail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_checked, 0, 0, 0);
//                mailSelect = true;
//                notificationTypes.add(message_type_mail);
//            }
//        }
//        if (v == notification) {
//            if (notificationSelect) {
//                notification.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_unchecked, 0, 0, 0);
//                notificationSelect = false;
//                notificationTypes.remove(message_type_notification);
//            } else {
//                notification.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_checked, 0, 0, 0);
//                notificationSelect = true;
//                notificationTypes.add(message_type_notification);
//            }
//        }
        if (v == sendNotification) {
            checkSpinner = "send";
            if (validateFields()) {
                sendReportService();

            }
        }
    }

    private boolean validateFields() {
        if (!(sms.isChecked() || mail.isChecked() || notification.isChecked())) {
            AlertDialogHelper.showSimpleAlertDialog(this, "Select at least one mode");
            return false;
        } else {
            return true;
        }
    }

    private void getNoti() {
        if (sms.isChecked()) {
            notificationTypes.add(message_type_sms);
        }
        if (mail.isChecked()) {
            notificationTypes.add(message_type_mail);
        }
        if (notification.isChecked()) {
            notificationTypes.add(message_type_notification);
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    private boolean validateSignInResponse(JSONObject response) {
        boolean signInsuccess = false;
        if ((response != null)) {
            try {
                String status = response.getString("status");
                String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                Log.d(TAG, "status val" + status + "msg" + msg);

                if ((status != null)) {
                    if (((status.equalsIgnoreCase("activationError")) ||
                            (status.equalsIgnoreCase("alreadyRegistered")) ||
                            (status.equalsIgnoreCase("notRegistered")) ||
                            (status.equalsIgnoreCase("error")))) {
                        signInsuccess = false;
                        Log.d(TAG, "Show error dialog");
                        AlertDialogHelper.showSimpleAlertDialog(this, msg);

                    } else {
                        signInsuccess = true;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return signInsuccess;
    }

    @Override
    public void onResponse(final JSONObject response) {
        if (validateSignInResponse(response)) {

            try {
                progressDialogHelper.hideProgressDialog();
                String status = response.getString("status");
                String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                Toast.makeText(this, "Notification sent!!", Toast.LENGTH_SHORT).show();
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
                AlertDialogHelper.showSimpleAlertDialog(ClassTeacherCtHwDetailView.this, error);
            }
        });
    }

    private void sendReportService() {
        checkSpinner = "send";
        getNoti();
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.KEY_USER_ID, PreferenceStorage.getUserId(this));
                jsonObject.put(EnsyfiConstants.CT_HW_HOMEWORK_ID, classTeacherCtHwDaywise.getHw_id());
                jsonObject.put(EnsyfiConstants.CT_HW_CLASS_ID, PreferenceStorage.getClassTeacher(getApplicationContext()));
                jsonObject.put(EnsyfiConstants.CT_HW_HOMEWORK_SEND_TYPE,
                        notificationTypes.toString().replace("[", "").replace("]", ""));


            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) +
                    EnsyfiConstants.GET_CLASS_TEACHER_CT_HW_SEND_SINGLE;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);


        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

}