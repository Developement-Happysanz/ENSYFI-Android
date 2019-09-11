package com.palprotech.ensyfi.activity.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.general.LeaveStatusActivity;
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

public class OnDutyDetailActivity extends AppCompatActivity implements IServiceListener, DialogClickListener {

    private OnDuty onDuty;
    private TextView txtOnDutyReason, txtOnDutyStartDate, txtOnDutyEndDate, txtOnDutyNotes;
    private Button btnSend;
    private RadioGroup approve;
    protected ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    String onDutyApprovalStatus = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_duty_details);
        onDuty = (OnDuty) getIntent().getSerializableExtra("onDutyObj");
        initializeEventHelpers();
        initializeViews();
        populateData();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);

        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), OnDutyViewActivity.class);
                startActivity(intent);}
        });
    }

    protected void initializeEventHelpers() {
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
    }

    private void initializeViews() {
        txtOnDutyReason = (TextView) findViewById(R.id.txtOdFor);
        txtOnDutyNotes = (TextView) findViewById(R.id.txtOdNotes);
        txtOnDutyStartDate = (TextView) findViewById(R.id.txtFromDate);
        txtOnDutyEndDate = (TextView) findViewById(R.id.txtToDate);
        approve = findViewById(R.id.status_layout);
        approve.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId) {
                    case R.id.txtStatusDecline:
                        onDutyApprovalStatus = "Rejected";
                        break;

                    case R.id.txtStatusApprove:
                        onDutyApprovalStatus = "Approved";
                        break;
                }
            }
        });
        btnSend = (Button) findViewById(R.id.send_status);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOdStatus();
                finish();
                Intent intent = new Intent(getApplicationContext(), OnDutyViewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    private void populateData() {
        txtOnDutyReason.setText(onDuty.getOdFor());
        txtOnDutyNotes.setText(onDuty.getNotes());
        txtOnDutyStartDate.setText(onDuty.getFromDate());
        txtOnDutyEndDate.setText(onDuty.getToDate());
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    private void sendOdStatus() {
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_OD_APPROVAL_STATUS, onDutyApprovalStatus);
                jsonObject.put(EnsyfiConstants.PARAMS_OD_ID, onDuty.getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(this) + EnsyfiConstants.APPROVE_OD_API;
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
