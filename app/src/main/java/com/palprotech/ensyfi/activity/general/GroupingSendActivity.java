package com.palprotech.ensyfi.activity.general;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
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
    int pageNumber = 0, totalCount = 0;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();
    private SearchView mSearchView = null;
    TextView sms, mail, notification;
    Boolean smsSelect = false, mailSelect = false, notificationSelect = false;
    Button sendNotification;
    EditText notes;
    private String message = "", message_type_sms = "0", message_type_mail = "0", message_type_notification = "0";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_notification_send);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        spnGroupList = (Spinner) findViewById(R.id.group_select_list_spinner);

        sms = (TextView) findViewById(R.id.sms);
        sms.setOnClickListener(this);
        mail = (TextView) findViewById(R.id.mail);
        mail.setOnClickListener(this);
        notification = (TextView) findViewById(R.id.notification);
        notification.setOnClickListener(this);

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
//                Toast.makeText(getApplicationContext(), "Class ID: " + classList.getClassId() + ",  Class Name : " + classList.getClassName(), Toast.LENGTH_SHORT).show();
                storeGroupId = groupList.getGroupId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        GetStudentData();
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
        progressDialogHelper.hideProgressDialog();

        if (validateSignInResponse(response)) {

            if (checkSpinner.equalsIgnoreCase("group")) {

                try {

                    JSONArray getData = response.getJSONArray("groupDetails");
                    JSONObject userData = getData.getJSONObject(0);
                    int getLength = getData.length();
                    String subjectName = null;
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
//                spnClassList.setSelection(adapter.getPosition());//Optional to set the selected item.


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (checkSpinner.equalsIgnoreCase("send")) {
                try {
                    String status = response.getString("status");
                    String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                    Log.d(TAG, "status val" + status + "msg" + msg);
                    if ((status != null)) {
                        if (((status.equalsIgnoreCase("success")))) {

                            Log.d(TAG, "Show error dialog");
                            AlertDialogHelper.showSimpleAlertDialog(this, msg);

                            Intent intent = new Intent(this, GroupingActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                    }
//                studentData.saveStudentProfile(getData);
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
//                loadMoreListView.onLoadMoreComplete();
                AlertDialogHelper.showSimpleAlertDialog(GroupingSendActivity.this, error);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == sms) {
            if (smsSelect) {
                sms.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_unchecked, 0, 0, 0);
                smsSelect = false;
                message_type_sms = "0";
            } else {
                sms.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_checked, 0, 0, 0);
                smsSelect = true;
                message_type_sms = "1";
            }

        }
        if (v == mail) {
            if (mailSelect) {
                mail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_unchecked, 0, 0, 0);
                mailSelect = false;
                message_type_mail = "0";
            } else {
                mail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_checked, 0, 0, 0);
                mailSelect = true;
                message_type_mail = "1";
            }

        }
        if (v == notification) {
            if (notificationSelect) {
                notification.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_unchecked, 0, 0, 0);
                notificationSelect = false;
                message_type_notification = "0";
            } else {
                notification.setCompoundDrawablesWithIntrinsicBounds(R.drawable.grouping_checked, 0, 0, 0);
                notificationSelect = true;
                message_type_notification = "1";
            }

        }

        if (v == sendNotification) {
            checkSpinner = "send";
            callGetStudentInfoService();

        }

    }

    private void callGetStudentInfoService() {
        try {

            message = notes.getText().toString();

            if (validateFields()) {
                if (CommonUtils.isNetworkAvailable(this)) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_TITLE_ID, storeGroupId);
                        jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_MESSAGE_TYPE_SMS, message_type_sms);
                        jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_MESSAGE_TYPE_MAIL, message_type_mail);
                        jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_MESSAGE_TYPE_NOTIFICATION, message_type_notification);
                        jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_MESSAGE_DETAILS, message);
                        jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATED_BY, PreferenceStorage.getUserId(this));

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
        } else if (!(smsSelect || mailSelect || notificationSelect)) {
            AlertDialogHelper.showSimpleAlertDialog(this, "Select at least one mode");
            return false;
        } else {
            return true;
        }
    }
}
