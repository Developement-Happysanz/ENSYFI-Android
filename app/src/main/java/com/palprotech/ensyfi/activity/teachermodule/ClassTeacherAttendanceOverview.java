package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherAttendance;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.AppValidator;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClassTeacherAttendanceOverview extends AppCompatActivity implements IServiceListener,
        DialogClickListener, View.OnClickListener {

    private static final String TAG = ClassTeacherAttendanceOverview.class.getName();
    private ClassTeacherAttendance classTeacherAttendance;
    private TextView txtAttendanceDate, txtClassStrength, txtNoPresent, txtNoAbsent, txtName;
    private LinearLayout sendReport, viewAttendance;
    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    Handler mHandler = new Handler();
    private RelativeLayout reportPopup;
    private ImageView close;
    private String checkSpinner = "";
    TextView sms, mail, notification;
    Boolean smsSelect = false, mailSelect = false, notificationSelect = false;
    Button sendNotification;
    EditText notes;
    private String message = "";
    ArrayList<String> notificationTypes = new ArrayList<>();
    private String message_type_sms = "SMS", message_type_mail = "Mail", message_type_notification = "Notification";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_teacher_attendance_overview);
        classTeacherAttendance = (ClassTeacherAttendance) getIntent().getSerializableExtra("attendanceObj");
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        initializeViews();
        populateData();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);

        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializeViews() {
        txtClassStrength = (TextView) findViewById(R.id.txt_class_strength);
        txtAttendanceDate = (TextView) findViewById(R.id.txt_attendance_date);
        txtNoPresent = (TextView) findViewById(R.id.txt_no_present);
        txtNoAbsent = (TextView) findViewById(R.id.txt_no_absent);
        txtName = (TextView) findViewById(R.id.taken_by_name);
        sendReport = (LinearLayout) findViewById(R.id.send_attendance);
        sendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportPopup.setVisibility(View.VISIBLE);
            }
        });
        if (classTeacherAttendance.getSent_status().equalsIgnoreCase("1")) {
            sendReport.setVisibility(View.GONE);
        } else {
            sendReport.setVisibility(View.VISIBLE);
        }
        viewAttendance = (LinearLayout) findViewById(R.id.view_attendance);
        viewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClassTeacherAttendanceDetailView.class);
                intent.putExtra("attendanceObj", classTeacherAttendance);
                startActivity(intent);
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

        sms = (TextView) findViewById(R.id.sms);
        sms.setOnClickListener(this);
        mail = (TextView) findViewById(R.id.mail);
        mail.setOnClickListener(this);
        notification = (TextView) findViewById(R.id.notification);
        notification.setOnClickListener(this);

        sendNotification = (Button) findViewById(R.id.send_message);
        sendNotification.setOnClickListener(this);
    }

    private void populateData() {
        txtClassStrength.setText(": " + classTeacherAttendance.getClass_total());

        String start = classTeacherAttendance.getCreated_at();
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = formatter.parse(start);
            SimpleDateFormat sent_date = new SimpleDateFormat("dd-MM-yyyy");
            String sent_date_name = sent_date.format(date.getTime());
            if (start != null) {
                txtAttendanceDate.setText(": " + sent_date_name);
            } else {
                txtAttendanceDate.setText(": N/A");
            }
        } catch (final ParseException e) {
            e.printStackTrace();
        }

        txtNoPresent.setText(": " + classTeacherAttendance.getNoOfPresent());
        txtNoAbsent.setText(": " + classTeacherAttendance.getNoOfAbsent());
        txtName.setText(": " + classTeacherAttendance.getName());
    }

    @Override
    public void onClick(View v) {
        if (v == sms) {
            if (smsSelect) {
                sms.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_unchecked, 0, 0, 0);
                smsSelect = false;
                notificationTypes.remove(message_type_sms);
            } else {
                sms.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_checked, 0, 0, 0);
                smsSelect = true;
                notificationTypes.add(message_type_sms);

            }
        }
        if (v == mail) {
            if (mailSelect) {
                mail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_unchecked, 0, 0, 0);
                mailSelect = false;
                notificationTypes.remove(message_type_mail);
            } else {
                mail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_checked, 0, 0, 0);
                mailSelect = true;
                notificationTypes.add(message_type_mail);
            }
        }
        if (v == notification) {
            if (notificationSelect) {
                notification.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_unchecked, 0, 0, 0);
                notificationSelect = false;
                notificationTypes.remove(message_type_notification);
            } else {
                notification.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_checked, 0, 0, 0);
                notificationSelect = true;
                notificationTypes.add(message_type_notification);
            }
        }
        if (v == sendNotification) {
            checkSpinner = "send";
            if (validateFields()) {
                sendReportService();

            }
        }
    }

    private boolean validateFields() {
        if (!(smsSelect || mailSelect || notificationSelect)) {
            AlertDialogHelper.showSimpleAlertDialog(this, "Select at least one mode");
            return false;
        } else {
            return true;
        }
    }

    private void sendReportService() {
        checkSpinner = "send";
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_ID, classTeacherAttendance.getAtId());
                jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_MESSAGE_TYPE,
                        notificationTypes.toString().replace("[", "").replace("]", ""));


            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) +
                    EnsyfiConstants.SEND_ATTENDANCE_VIEW;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);


        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
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
                Intent intent = new Intent(getApplicationContext(), ClassTeacherAttendanceView.class);
                startActivity(intent);
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
                AlertDialogHelper.showSimpleAlertDialog(ClassTeacherAttendanceOverview.this, error);
            }
        });
    }
}