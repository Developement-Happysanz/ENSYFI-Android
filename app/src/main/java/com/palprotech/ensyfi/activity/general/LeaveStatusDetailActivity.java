package com.palprotech.ensyfi.activity.general;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.general.viewlist.LeaveStatus;
import com.palprotech.ensyfi.bean.general.viewlist.OnDuty;
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

public class LeaveStatusDetailActivity extends AppCompatActivity implements IServiceListener, DialogClickListener {

    private LeaveStatus leaveStatus;
    private TextView txtLeaveReason, txtLeaveStartDate, txtLeaveEndDate, txtLeaveStartTime, txtLeaveEndTime, txtLeaveType;
    private Button btnSend;
    private RadioGroup approve;
    protected ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    String leaveApprovalStatus = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_status_detail);
        leaveStatus = (LeaveStatus) getIntent().getSerializableExtra("leaveObj");
        initializeEventHelpers();
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

    protected void initializeEventHelpers() {
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
    }

    private void initializeViews() {
        txtLeaveReason = (TextView) findViewById(R.id.txtLeaveFor);
        txtLeaveType = (TextView) findViewById(R.id.txtLeaveType);
        txtLeaveStartDate = (TextView) findViewById(R.id.txtFromDate);
        txtLeaveEndDate = (TextView) findViewById(R.id.txtToDate);
        approve = findViewById(R.id.status_layout);
        approve.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId) {
                    case R.id.txtStatusDecline:
                        leaveApprovalStatus = "Rejected";
                        break;

                    case R.id.txtStatusApprove:
                        leaveApprovalStatus = "Approved";
                        break;
                }
            }
        });
        btnSend = (Button) findViewById(R.id.send_status);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLeaveStatus();
                finish();
                Intent intent = new Intent(getApplicationContext(), LeaveStatusActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    private void populateData() {
        txtLeaveType.setText(leaveStatus.getLeaveTitle());
        String leaveType = leaveStatus.getLeaveType();
        if (leaveType.equals("0")) {
            txtLeaveStartDate.setText("From : " + leaveStatus.getFromTime());
            txtLeaveEndDate.setText("To : " + leaveStatus.getToTime());
        }
        else {
            txtLeaveStartDate.setText("From : " + leaveStatus.getFromLeaveDate());
            txtLeaveEndDate.setText("To : " + leaveStatus.getToLeaveDate());
        }
        txtLeaveReason.setText(leaveStatus.getLeaveDescription());
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    private void sendLeaveStatus() {
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_LEAVE_APPROVAL_STATUS, leaveApprovalStatus);
                jsonObject.put(EnsyfiConstants.PARAMS_LEAVE_ID, leaveStatus.getLeaveId());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(this) + EnsyfiConstants.APPROVE_LEAVES_API;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onError(String error) {

    }
}