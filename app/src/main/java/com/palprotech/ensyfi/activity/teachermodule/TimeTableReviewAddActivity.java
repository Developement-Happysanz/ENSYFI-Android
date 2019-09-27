package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Admin on 19-07-2017.
 */

public class TimeTableReviewAddActivity extends AppCompatActivity implements IServiceListener, DialogClickListener {

    private static final String TAG = TimeTableReviewAddActivity.class.getName();
    String getTimeTableValue;
    TextView txtClassName, txtSubjectName, txtPeriodId;
    EditText edtTimetableReviewDetails;
    Button btnSubmit;
    String sClassName, sClassId, sSubjectName, sSubjectId, sPeriodId;
    final Calendar c = Calendar.getInstance();
    protected ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_review_add);
        Intent intent = getIntent();
        getTimeTableValue = intent.getStringExtra("timeTableValue");
        txtClassName = (TextView) findViewById(R.id.txtClassName);
        txtSubjectName = (TextView) findViewById(R.id.txtSubjectName);
        txtPeriodId = (TextView) findViewById(R.id.txtPeriodId);
        edtTimetableReviewDetails = (EditText) findViewById(R.id.edtTimetableReviewDetails);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        progressDialogHelper = new ProgressDialogHelper(this);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);

        String[] animalsArray = getTimeTableValue.split(",");
        sClassName = animalsArray[0];
        txtClassName.setText(sClassName);
        sClassId = animalsArray[1];
        sSubjectName = animalsArray[2];
        txtSubjectName.setText(sSubjectName);
        sSubjectId = animalsArray[3];
        sPeriodId = animalsArray[4];
        txtPeriodId.setText(sPeriodId);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReview();
            }
        });

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void saveReview() {
        SimpleDateFormat serverDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedServerDate = serverDF.format(c.getTime());

        if (validateFields()) {
            if (CommonUtils.isNetworkAvailable(this)) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(EnsyfiConstants.PARAMS_REVIEW_TIME_DATE, formattedServerDate);
                    jsonObject.put(EnsyfiConstants.PARAMS_REVIEW_CLASS_ID, sClassId);
                    jsonObject.put(EnsyfiConstants.PARAMS_REVIEW_SUBJECT_ID, sSubjectId);
                    jsonObject.put(EnsyfiConstants.PARAMS_REVIEW_PERIOD_ID, sPeriodId);
                    jsonObject.put(EnsyfiConstants.PARAMS_REVIEW_USER_TYPE, PreferenceStorage.getUserType(this));
                    jsonObject.put(EnsyfiConstants.PARAMS_REVIEW_USER_ID, PreferenceStorage.getUserId(this));
                    jsonObject.put(EnsyfiConstants.PARAMS_REVIEW_COMMENTS, edtTimetableReviewDetails.getText().toString());
                    jsonObject.put(EnsyfiConstants.PARAMS_REVIEW_CREATED_AT, formattedServerDate);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
                String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(this) + EnsyfiConstants.GET_ON_TIME_TABLE_REVIEW_ADD;
                serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
            } else {

                AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
            }
        }
    }

    private boolean validateFields() {

        if (!AppValidator.checkNullString(this.edtTimetableReviewDetails.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid reason");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    private boolean validateSignInResponse(JSONObject response) {
        boolean signInSuccess = false;
        if ((response != null)) {
            try {
                String status = response.getString("status");
                String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                Log.d(TAG, "status val" + status + "msg" + msg);

                if ((status != null)) {
                    if (((status.equalsIgnoreCase("activationError")) || (status.equalsIgnoreCase("alreadyRegistered")) ||
                            (status.equalsIgnoreCase("notRegistered")) || (status.equalsIgnoreCase("error")))) {
                        signInSuccess = false;
                        Log.d(TAG, "Show error dialog");
                        AlertDialogHelper.showSimpleAlertDialog(this, msg);

                    } else {
                        signInSuccess = true;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return signInSuccess;
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {
            try {
                String status = response.getString("status");
                String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                Log.d(TAG, "status val" + status + "msg" + msg);
                if ((status != null)) {
                    if (((status.equalsIgnoreCase("success")))) {

                        Log.d(TAG, "Show error dialog");
                        AlertDialogHelper.showSimpleAlertDialog(this, msg);
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(this, error);
    }
}
