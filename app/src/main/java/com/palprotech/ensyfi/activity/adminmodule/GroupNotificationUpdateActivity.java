package com.palprotech.ensyfi.activity.adminmodule;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.admin.support.StoreTeacherId;
import com.palprotech.ensyfi.bean.admin.viewlist.Groups;
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

public class GroupNotificationUpdateActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, View.OnClickListener {

    private static final String TAG = GroupNotificationUpdateActivity.class.getName();
    private Groups groups;
    private EditText txtGroupTitle;
    private EditText spnGroupLeadList;
    private Button btnSend, viewMembers;
    protected ProgressDialogHelper progressDialogHelper;
    private ProgressDialog mProgressDialog = null;
    private ServiceHelper serviceHelper;
    private Switch swStatus;
    String groupStatus = "";
    String groupRes = "";
    String groupLead = "";
    String storeGroupId;
    TextView groupTitleDisp, groupLeadDisp, groupStatusDisp, leadType, statusTxt;
    ImageView groupUpdate, groupMemberAdd, groupNotification;
    Boolean update = false;
    ArrayList<StoreTeacherId> classesList = new ArrayList<>();
    ArrayAdapter<StoreTeacherId> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_notification_update);
        groups = (Groups) getIntent().getSerializableExtra("groupsObj");
        initializeEventHelpers();
        GetGroupData();
        initializeViews();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);

        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GroupNotificationAdminViewActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void initializeEventHelpers() {
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
    }

    private void getVisibileViews() {
        if (!update) {
            txtGroupTitle.setVisibility(View.GONE);
            spnGroupLeadList.setVisibility(View.GONE);
            swStatus.setVisibility(View.GONE);
            btnSend.setVisibility(View.GONE);
            groupTitleDisp.setVisibility(View.VISIBLE);
            groupLeadDisp.setVisibility(View.VISIBLE);
            groupStatusDisp.setVisibility(View.VISIBLE);
            viewMembers.setVisibility(View.VISIBLE);
        } else {
            txtGroupTitle.setVisibility(View.VISIBLE);

            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params1.setMargins(40, 40, 0, 0);
            params1.addRule(RelativeLayout.BELOW, txtGroupTitle.getId());
            leadType.setLayoutParams(params1);
            leadType.setPadding(0, 20, 0, 20);

            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params2.setMargins(40, 40, 0, 0);
            params2.addRule(RelativeLayout.BELOW, spnGroupLeadList.getId());
            statusTxt.setLayoutParams(params2);

            spnGroupLeadList.setVisibility(View.VISIBLE);
            swStatus.setVisibility(View.VISIBLE);
            btnSend.setVisibility(View.VISIBLE);
            groupTitleDisp.setVisibility(View.GONE);
            groupLeadDisp.setVisibility(View.GONE);
            groupStatusDisp.setVisibility(View.GONE);
            viewMembers.setVisibility(View.GONE);
        }
    }

    private void initializeViews() {

        leadType = findViewById(R.id.lead_type);
        statusTxt = findViewById(R.id.group_status_txt);

        groupTitleDisp = findViewById(R.id.group_title_txt_disp);
        groupTitleDisp.setText(groups.getGroup_title());
        groupLeadDisp = findViewById(R.id.group_lead_spinner_txt);
        groupLeadDisp.setText(groups.getLead_name());
        groupStatusDisp = findViewById(R.id.radioStatusViewTxt);
        groupStatusDisp.setText(groups.getStatus());
        groupUpdate = findViewById(R.id.updateGroup);
        if (PreferenceStorage.getUserType(this).equalsIgnoreCase("2")) {
            groupUpdate.setVisibility(View.GONE);
        }
        groupUpdate.setOnClickListener(this);
        groupMemberAdd = findViewById(R.id.addGroupMembers);
        groupMemberAdd.setOnClickListener(this);
        groupNotification = findViewById(R.id.vierwGroupNotification);
        groupNotification.setOnClickListener(this);
        txtGroupTitle = findViewById(R.id.group_title_txt);
        txtGroupTitle.setText(groups.getGroup_title());
        spnGroupLeadList = findViewById(R.id.group_lead_spinner);
        spnGroupLeadList.setText(groups.getLead_name());
        storeGroupId = groups.getGroup_lead_id();

        spnGroupLeadList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBloodGroups();
            }
        });


        viewMembers = findViewById(R.id.view_group_members);
        viewMembers.setOnClickListener(this);

        swStatus = findViewById(R.id.swStatus);
        swStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    swStatus.setText("Active");
                }
            }
        });
        if (groups.getStatus().equalsIgnoreCase("Active")) {
            swStatus.setChecked(true);
        } else {
            swStatus.setChecked(false);
        }
        btnSend = (Button) findViewById(R.id.update_group);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGroupStatus();
                update = !update;
                getVisibileViews();
            }
        });
        getVisibileViews();
    }

    private void showBloodGroups() {
        Log.d(TAG, "Show blood group lists");
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.gender_header_layout, null);
        TextView header = (TextView) view.findViewById(R.id.gender_header);
        header.setText("Select Blood Group");
        builderSingle.setCustomTitle(view);

        builderSingle.setAdapter(adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        StoreBloodGroup bloodGroup = bloodGroupList.get(which);
//                        etCandidateBloodGroup.setText(bloodGroup.getBloodGroupName());
//                        bloodGroupId = bloodGroup.getBloodGroupId();
//                        groupLead = parent.getSelectedItem().toString();
                        StoreTeacherId teacherName = classesList.get(which);
                        storeGroupId = teacherName.getTeacherId();
                        groupLead = teacherName.getTeacherName();
                    }
                });
        builderSingle.show();
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    private void GetGroupData() {
        groupRes = "teacherdata";
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_USER_ID, PreferenceStorage.getUserId(this));
                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.GET_ADMIN_GROUP_LEAD_TEACHER_VIEW;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);


        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private void sendGroupStatus() {
        groupRes = "groupdata";
        if (CommonUtils.isNetworkAvailable(this)) {

            if (validateFields()) {
                boolean Status = false;
                Status = swStatus.isChecked();
                if (Status) {
                    groupStatus = "Active";
                } else {
                    groupStatus = "Deactive";
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_USER_ID, PreferenceStorage.getUserId(this));
                    jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_ID, groups.getId());
                    jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_TITLE, txtGroupTitle.getText().toString());
                    jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_LEAD_ID, storeGroupId);
                    jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_STATUS, groupStatus);
                    jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.UPDATE_GROUP;
                serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
            }

        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private boolean validateFields() {
        if (!AppValidator.checkNullString(this.txtGroupTitle.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, this.getResources().getString(R.string.group_title));
            return false;
        } else {
            return true;
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
    public void onResponse(JSONObject response) {


        if (validateSignInResponse(response)) {

            try {
                if (groupRes.equalsIgnoreCase("teacherdata")) {
                    progressDialogHelper.hideProgressDialog();
                    JSONArray getData = response.getJSONArray("teacherList");
                    JSONObject userData = getData.getJSONObject(0);
                    int getLength = getData.length();
                    Log.d(TAG, "userData dictionary" + userData.toString());

                    String classId = "";
                    String className = "";

                    for (int i = 0; i < getLength; i++) {

                        classId = getData.getJSONObject(i).getString("user_id");
                        className = getData.getJSONObject(i).getString("name");

                        classesList.add(new StoreTeacherId(classId, className));
                    }

                    //fill data in spinner
                    adapter = new ArrayAdapter<StoreTeacherId>(getApplicationContext(), R.layout.spinner_item_ns, classesList);
                } else if (groupRes.equalsIgnoreCase("groupdata")) {
                    Toast.makeText(this, "Changes made have been saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), GroupNotificationAdminViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "Error while sign In");
        }

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onClick(View v) {
        if (v == groupUpdate) {
            update = !update;
            getVisibileViews();
        } else if (v == groupMemberAdd) {
            Intent intent = new Intent(getApplicationContext(), GroupNotificationAddMemberActivity.class);
            intent.putExtra("groupsObj", groups);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        } else if (v == groupNotification) {
            Intent intent = new Intent(getApplicationContext(), GroupNotificationActivity.class);
            intent.putExtra("groupsObj", groups);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        } else if (v == viewMembers) {
            Intent intent = new Intent(getApplicationContext(), GroupNotificationViewMemberActivity.class);
            intent.putExtra("groupsObj", groups);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    }
}

