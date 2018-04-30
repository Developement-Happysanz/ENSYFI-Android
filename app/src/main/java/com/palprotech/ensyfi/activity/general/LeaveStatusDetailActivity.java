package com.palprotech.ensyfi.activity.general;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    private TextView txtLeaveReason, txtLeaveStartDate, txtLeaveEndDate, txtLeaveStartTime, txtLeaveEndTime;
    private Button btnApprove, getBtnDecline;
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
        txtLeaveStartDate = (TextView) findViewById(R.id.txtFromDate);
        txtLeaveEndDate = (TextView) findViewById(R.id.txtToDate);
        txtLeaveStartTime = (TextView) findViewById(R.id.txtFromTime);
        txtLeaveEndTime = (TextView) findViewById(R.id.txtToTime);
        getBtnDecline = (Button) findViewById(R.id.txtStatusDecline);
        btnApprove = (Button) findViewById(R.id.txtStatusApprove);

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveApprovalStatus = "Approved";
                sendLeaveStatus();
                finish();
            }
        });

        getBtnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveApprovalStatus = "Rejected";
                sendLeaveStatus();
                finish();
            }
        });
    }

    private void populateData() {
        txtLeaveReason.setText(leaveStatus.getLeaveTitle());
        txtLeaveStartDate.setText(leaveStatus.getFromLeaveDate());
        txtLeaveEndDate.setText(leaveStatus.getToLeaveDate());
        txtLeaveEndDate.setText(leaveStatus.getFromTime());
        txtLeaveEndDate.setText(leaveStatus.getToTime());
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
                jsonObject.put(EnsyfiConstants.PARAMS_LEAVE_ID, leaveStatus.getLeave_id());
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