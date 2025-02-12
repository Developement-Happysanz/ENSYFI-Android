package com.palprotech.ensyfi.activity.studentmodule;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.student.viewlist.ClassTest;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 17-05-2017.
 */

public class ClassTestDetailActivity extends AppCompatActivity implements IServiceListener, DialogClickListener {
    private static final String TAG = ClassTestDetailActivity.class.getName();
    private ClassTest classTest;
    private TextView txtTitle, txtHomeWorkDate, txtHomeWorkDetails, txtHomeWorkSubject, tvTitleText, txtViewMarks, txtViewRemarks;
    private LinearLayout llMarkView, llMarkRemarks;
    private ServiceHelper serviceHelper;
    private ProgressDialogHelper progressDialogHelper;
    String txtHomeWorkType = "0";
    String mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classtest_details);
        classTest = (ClassTest) getIntent().getSerializableExtra("eventObj");
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
        txtTitle = (TextView) findViewById(R.id.txtClassTestTitle1);
        tvTitleText = (TextView) findViewById(R.id.tvtitletext);
        txtHomeWorkDetails = (TextView) findViewById(R.id.txthomeworkdetails);
        txtHomeWorkSubject = (TextView) findViewById(R.id.txthomeworksubject);
        txtViewMarks = (TextView) findViewById(R.id.viewmarks);
        txtViewRemarks = (TextView) findViewById(R.id.viewremarks);
        llMarkView = (LinearLayout) findViewById(R.id.llMarkView);
        llMarkRemarks = (LinearLayout) findViewById(R.id.llMarkRemarks);

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
    }

    private void populateData() {
        txtTitle.setText(classTest.getHwTitle());
        txtHomeWorkDetails.setText(classTest.getHwDatails());
        txtHomeWorkSubject.setText(classTest.getHwSubjectName());
        txtHomeWorkType = (classTest.getHwType());
        if (txtHomeWorkType.equalsIgnoreCase("HW")) {
            tvTitleText.setText("Homework");
        } else {
            tvTitleText.setText("Class Test");
        }

        mStatus = classTest.getHwMarkStatus();
        if (mStatus.equalsIgnoreCase("1")) {
            llMarkView.setVisibility(View.VISIBLE);
            llMarkRemarks.setVisibility(View.VISIBLE);

            if (CommonUtils.isNetworkAvailable(this)) {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(EnsyfiConstants.PARAM_HOMEWORK_ID, classTest.getHwId());
                    jsonObject.put(EnsyfiConstants.PARM_ENROLL_ID, PreferenceStorage.getStudentRegisteredIdPreference(getApplicationContext()));
                    jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
                String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.GET_STUDENT_CLASSTEST_MARK_API;
                serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
            } else {
                AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
            }

        } else {
            llMarkView.setVisibility(View.GONE);
            llMarkRemarks.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {
            try {
                JSONArray getData = response.getJSONArray("ctestmarkDetails");
                JSONObject marksData = getData.getJSONObject(0);
                String studentMark = null, studentRemarks = null;

                Log.d(TAG, "userData dictionary" + marksData.toString());
                if (marksData != null) {
                    studentMark = marksData.getString("marks") + "";
                    txtViewMarks.setText(studentMark);
                    studentRemarks = marksData.getString("remarks") + "";
                    txtViewRemarks.setText(studentRemarks);
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
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(this, error);
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
}
