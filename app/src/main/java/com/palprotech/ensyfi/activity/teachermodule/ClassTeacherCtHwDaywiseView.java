package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.palprotech.ensyfi.adapter.teachermodule.ClassTeacherCtHwDaywiseListAdapter;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherCtHwDaywise;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherCtHwDaywiseList;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherCtHwOverall;
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

public class ClassTeacherCtHwDaywiseView extends AppCompatActivity implements IServiceListener, DialogClickListener,
        AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = "ClassTeacherView";

    ServiceHelper serviceHelper;
    ProgressDialogHelper progressDialogHelper;
    ListView attendaceList;
    ClassTeacherCtHwOverall classTeacherCtHwOverall;
    ClassTeacherCtHwDaywiseListAdapter classTeacherCtHwDaywiseListAdapter;
    ArrayList<ClassTeacherCtHwDaywise> ctHwDaywiseArrayList;
    protected boolean isLoadingForFirstTime = true;
    int totalCount = 0;
    Handler mHandler = new Handler();

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
        setContentView(R.layout.activity_class_teacher_ct_hw_daywise_view);
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

        attendaceList = (ListView) findViewById(R.id.listView_daywise_work);
        attendaceList.setOnItemClickListener(this);
        ctHwDaywiseArrayList = new ArrayList<>();
        classTeacherCtHwOverall = (ClassTeacherCtHwOverall) getIntent().getSerializableExtra("attendanceObj");


        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);

        progressDialogHelper = new ProgressDialogHelper(this);
        callGetEventService();
        openPopup = (ImageView) findViewById(R.id.send);
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

    public void callGetEventService() {

        if (ctHwDaywiseArrayList != null)
            ctHwDaywiseArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            new HttpAsyncTask().execute("");
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onEvent list item clicked" + position);
        ClassTeacherCtHwDaywise classTeacherCtHwDaywise = null;
        if ((classTeacherCtHwDaywiseListAdapter != null) && (classTeacherCtHwDaywiseListAdapter.ismSearching())) {
            Log.d(TAG, "while searching");
            int actualindex = classTeacherCtHwDaywiseListAdapter.getActualEventPos(position);
            Log.d(TAG, "actual index" + actualindex);
            classTeacherCtHwDaywise = ctHwDaywiseArrayList.get(actualindex);
        } else {
            classTeacherCtHwDaywise = ctHwDaywiseArrayList.get(position);
        }
        Intent intent = new Intent(this, ClassTeacherCtHwDetailView.class);
        intent.putExtra("attendanceObj", classTeacherCtHwDaywise);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.CT_HW_CLASS_ID, PreferenceStorage.getClassTeacher(getApplicationContext()));
                jsonObject.put(EnsyfiConstants.CT_HW_HOMEWORK_DATE, classTeacherCtHwOverall.getHw_date());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext())
                    + EnsyfiConstants.GET_CLASS_TEACHER_CT_HW_DAYWISE;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void result) {
            progressDialogHelper.cancelProgressDialog();
        }
    }

    @Override
    public void onResponse(final JSONObject response) {
        if (validateSignInResponse(response)) {
            if (checkSpinner.equalsIgnoreCase("send")) {
                try {
                    progressDialogHelper.hideProgressDialog();
                    String status = response.getString("status");
                    String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                    Toast.makeText(this, "Notification sent!!", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.d("ajazFilterresponse : ", response.toString());

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialogHelper.hideProgressDialog();

                    Gson gson = new Gson();
                    ClassTeacherCtHwDaywiseList classTeacherCtHwDaywiseList = gson.fromJson(response.toString(), ClassTeacherCtHwDaywiseList.class);
                    if (classTeacherCtHwDaywiseList.getClassTeacherCtHwDaywise() != null && classTeacherCtHwDaywiseList.getClassTeacherCtHwDaywise().size() > 0) {
                        totalCount = classTeacherCtHwDaywiseList.getCount();
                        isLoadingForFirstTime = false;
                        updateListAdapter(classTeacherCtHwDaywiseList.getClassTeacherCtHwDaywise());
                    }
                }
            });
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    private boolean validateSignInResponse(JSONObject response) {
        boolean signInsuccess = false;
        if ((response != null)) {
            try {
                String status = response.getString("status");
                String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                Log.d(TAG, "status val" + status + "msg" + msg);

                if ((status != null)) {
                    if (((status.equalsIgnoreCase("activationError")) || (status.equalsIgnoreCase("alreadyRegistered")) ||
                            (status.equalsIgnoreCase("notRegistered")) || (status.equalsIgnoreCase("error")))) {
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

    protected void updateListAdapter(ArrayList<ClassTeacherCtHwDaywise> ctHwDaywiseArrayList) {
        this.ctHwDaywiseArrayList.addAll(ctHwDaywiseArrayList);
        if (classTeacherCtHwDaywiseListAdapter == null) {
            classTeacherCtHwDaywiseListAdapter = new ClassTeacherCtHwDaywiseListAdapter(this, this.ctHwDaywiseArrayList);
            attendaceList.setAdapter(classTeacherCtHwDaywiseListAdapter);
        } else {
            classTeacherCtHwDaywiseListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String error) {

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

    private void sendReportService() {
        checkSpinner = "send";
        getNoti();
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.KEY_USER_ID, PreferenceStorage.getUserId(this));
                jsonObject.put(EnsyfiConstants.CT_HW_HOMEWORK_CREATED_DATE, classTeacherCtHwOverall.getHw_date());
                jsonObject.put(EnsyfiConstants.CT_HW_CLASS_ID, PreferenceStorage.getClassTeacher(getApplicationContext()));
                jsonObject.put(EnsyfiConstants.CT_HW_HOMEWORK_SEND_TYPE,
                        notificationTypes.toString().replace("[", "").replace("]", ""));


            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) +
                    EnsyfiConstants.GET_CLASS_TEACHER_CT_HW_SEND_ALL;
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

}

