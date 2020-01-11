package com.palprotech.ensyfi.activity.adminmodule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
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

public class GroupNotificationCreationActivity extends AppCompatActivity implements IServiceListener, DialogClickListener {

    private static final String TAG = GroupNotificationCreationActivity.class.getName();
    private Groups groups;
    private EditText txtGroupTitle;
    private Spinner spnGroupLeadList;
    private ImageView createGroup;
    private Switch swStatus;
    protected ProgressDialogHelper progressDialogHelper;
    private ProgressDialog mProgressDialog = null;
    private ServiceHelper serviceHelper;
    String groupStatus = "";
    String groupRes = "";
    String groupLead = "";
    String storeGroupId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_notification_creation);
        groups = (Groups) getIntent().getSerializableExtra("groupsObj");
        initializeEventHelpers();
        GetGroupData();
        initializeViews();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);

        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void initializeEventHelpers() {
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
    }

    private void initializeViews() {
        txtGroupTitle = findViewById(R.id.group_title_txt);
        spnGroupLeadList = findViewById(R.id.group_lead_spinner);
        spnGroupLeadList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                groupLead = parent.getSelectedItem().toString();
                StoreTeacherId teacherName = (StoreTeacherId) parent.getSelectedItem();
                storeGroupId = teacherName.getTeacherId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        swStatus = findViewById(R.id.swStatus);
        swStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    swStatus.setText("Active");
                }
            }
        });
        createGroup = (ImageView) findViewById(R.id.createNewGroup);

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGroupStatus();
            }
        });
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

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_ADMIN_GROUP_LEAD_TEACHER_VIEW;
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
                String CircularStatus = "";
                if (Status) {
                    groupStatus = "Active";
                } else {
                    groupStatus = "Deactive";
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_USER_ID, PreferenceStorage.getUserId(this));
                    jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_TITLE, txtGroupTitle.getText().toString());
                    jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_LEAD_ID, storeGroupId);
                    jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_STATUS, groupStatus);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(this) + EnsyfiConstants.CREATE_GROUP;
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
        } else if (!AppValidator.checkNullString(storeGroupId.trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, this.getResources().getString(R.string.group_admin));
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
                    ArrayList<StoreTeacherId> classesList = new ArrayList<>();

                    for (int i = 0; i < getLength; i++) {

                        classId = getData.getJSONObject(i).getString("user_id");
                        className = getData.getJSONObject(i).getString("name");

                        classesList.add(new StoreTeacherId(classId, className));
                    }

                    //fill data in spinner
                    ArrayAdapter<StoreTeacherId> adapter = new ArrayAdapter<StoreTeacherId>(getApplicationContext(), R.layout.spinner_item_ns, classesList);
                    spnGroupLeadList.setAdapter(adapter);
                } else if (groupRes.equalsIgnoreCase("groupdata")) {
                    String aaa = txtGroupTitle.getText().toString();
                    String bbb = "New group named" + aaa + "created";
                    Toast.makeText(this, bbb , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), GroupNotificationAdminViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else

        {
            Log.d(TAG, "Error while sign In");
        }

    }

    @Override
    public void onError(String error) {

    }
}
