package com.palprotech.ensyfi.activity.general;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.adminmodule.GroupNotificationActivity;
import com.palprotech.ensyfi.activity.adminmodule.GroupNotificationAdminViewActivity;
import com.palprotech.ensyfi.bean.general.support.StoreGroup;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.AppValidator;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Admin on 18-07-2017.
 */

public class GroupingSendActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, View.OnClickListener {

    private static final String TAG = GroupingSendActivity.class.getName();
    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    private Spinner spnGroupList;
    private String checkSpinner = "", storeGroupId;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();
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
        setContentView(R.layout.activity_group_notification_send);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        spnGroupList = (Spinner) findViewById(R.id.group_select_list_spinner);

        sms = (CheckBox) findViewById(R.id.sms_check);
//        sms.setOnClickListener(this);
        mail = (CheckBox) findViewById(R.id.mail_check);
//        mail.setOnClickListener(this);
        notification = (CheckBox) findViewById(R.id.notification_check);
//        notification.setOnClickListener(this);

        sendNotification = (Button) findViewById(R.id.send_message);
        sendNotification.setOnClickListener(this);

        notes = (EditText) findViewById(R.id.notes);

        GetGroupData();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spnGroupList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                StoreGroup groupList = (StoreGroup) parent.getSelectedItem();
                storeGroupId = groupList.getGroupId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    private void GetGroupData() {
        checkSpinner = "group";
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_USER_TYPE, PreferenceStorage.getUserType(this));
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_USER_ID, PreferenceStorage.getUserId(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_GROUP_LIST;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);


        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
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

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    @Override
    public void onResponse(final JSONObject response) {

        if (validateSignInResponse(response)) {

            if (checkSpinner.equalsIgnoreCase("group")) {

                try {
                    progressDialogHelper.hideProgressDialog();

                    JSONArray getData = response.getJSONArray("groupDetails");
                    JSONObject userData = getData.getJSONObject(0);
                    int getLength = getData.length();
                    Log.d(TAG, "userData dictionary" + userData.toString());

                    String groupId = "";
                    String groupName = "";
                    ArrayList<StoreGroup> groupList = new ArrayList<>();

                    for (int i = 0; i < getLength; i++) {

                        groupId = getData.getJSONObject(i).getString("id");
                        groupName = getData.getJSONObject(i).getString("group_title");

                        groupList.add(new StoreGroup(groupId, groupName));
                    }

                    //fill data in spinner
                    ArrayAdapter<StoreGroup> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item_ns, groupList);
                    spnGroupList.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (checkSpinner.equalsIgnoreCase("send")) {
                try {
                    progressDialogHelper.hideProgressDialog();
                    String status = response.getString("status");
                    String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                    if (status.equalsIgnoreCase("sucess") && msg.equalsIgnoreCase("Group Notification Send Sucessfully")) {
                        Toast.makeText(this, "Notification sent!!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                AlertDialogHelper.showSimpleAlertDialog(GroupingSendActivity.this, error);
            }
        });
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
            callGetStudentInfoService();
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

    private void callGetStudentInfoService() {
        try {

            getNoti();
            message = notes.getText().toString();

            if (validateFields()) {
                if (CommonUtils.isNetworkAvailable(this)) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_ID, storeGroupId);
                        jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_TYPE_NOTIFICATION, notificationTypes.toString().replace("[", "").replace("]", ""));
                        jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_MESSAGE_DETAILS, message);
                        jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_USER_ID, PreferenceStorage.getUserId(this));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
                    String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(this) + EnsyfiConstants.SEND_GROUP_MESSAGE;
                    serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
                } else {

                    AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean validateFields() {
        if (!AppValidator.checkNullString(this.notes.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid message");
            return false;
        } else if (!(sms.isChecked() || mail.isChecked() || notification.isChecked())) {
            AlertDialogHelper.showSimpleAlertDialog(this, "Select at least one mode");
            return false;
        } else {
            return true;
        }
    }
}
