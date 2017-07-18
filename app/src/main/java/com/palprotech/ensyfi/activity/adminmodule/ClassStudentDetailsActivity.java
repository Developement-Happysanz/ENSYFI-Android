package com.palprotech.ensyfi.activity.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.studentmodule.AttendanceActivity;
import com.palprotech.ensyfi.activity.studentmodule.ClassTestHomeworkActivity;
import com.palprotech.ensyfi.activity.studentmodule.ExamsResultActivity;
import com.palprotech.ensyfi.activity.studentmodule.FeeStatusActivity;
import com.palprotech.ensyfi.bean.admin.viewlist.ClassStudent;
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
 * Created by Admin on 18-07-2017.
 */

public class ClassStudentDetailsActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, View.OnClickListener {

    private static final String TAG = ClassStudentDetailsActivity.class.getName();
    private ClassStudent classStudent;
    private TextView txtStudentName, txtStudentRegId;
    private Button btnClassTestHomework, btnExams, btnFees,btnAttendance,btnOnDuty;
    private ServiceHelper serviceHelper;
    private ProgressDialogHelper progressDialogHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_student_details);
        classStudent = (ClassStudent) getIntent().getSerializableExtra("eventObj");
        txtStudentName = (TextView) findViewById(R.id.txtStudentName);
        txtStudentRegId = (TextView) findViewById(R.id.txtStudentRegId);

        btnClassTestHomework = (Button) findViewById(R.id.btnClassTestHomework);
        btnClassTestHomework.setOnClickListener(this);

        btnExams = (Button) findViewById(R.id.btnExams);
        btnExams.setOnClickListener(this);

        btnFees = (Button) findViewById(R.id.btnFees);
        btnFees.setOnClickListener(this);

        btnAttendance = (Button)findViewById(R.id.btnAttendance);
        btnAttendance.setOnClickListener(this);

        btnOnDuty=(Button)findViewById(R.id.btnOnDuty);
        btnOnDuty.setOnClickListener(this);

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);

        populateData();
        String view = "";
    }

    private void populateData() {
        txtStudentName.setText(classStudent.getName());
        txtStudentRegId.setText(classStudent.getEnrollId());

        if (CommonUtils.isNetworkAvailable(this)) {


            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_STUDENT_ID_SHOW, classStudent.getEnrollId());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_STUDENT_INFO;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);


        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {

            String repo = response.toString();

//            longInfo(repo);

            try {
                JSONArray getStudentData = response.getJSONArray("studentData");
                JSONObject studentData = getStudentData.getJSONObject(0);
                String studentMark = null, studentRemarks = null;

                JSONObject getParentData = response.getJSONObject("parents_details");

                JSONObject fatherData = getParentData.getJSONObject("fatherProfile");
                JSONObject motherData = getParentData.getJSONObject("motherProfile");
                JSONObject guardianData = getParentData.getJSONObject("guardianProfile");

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
    public void onClick(View v) {
        if (v == btnClassTestHomework) {

            String ClassId = classStudent.getClassId();
            // Student Preference - ClassId
            if ((ClassId != null) && !(ClassId.isEmpty()) && !ClassId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentClassIdPreference(getApplicationContext(), ClassId);
            }

            String StudentId = classStudent.getEnrollId();
            // Student Preference - EnrollId
            if ((StudentId != null) && !(StudentId.isEmpty()) && !StudentId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentRegisteredIdPreference(getApplicationContext(), StudentId);
            }
            Intent intent = new Intent(this, ClassTestHomeworkActivity.class);
            startActivity(intent);
        }
        if (v == btnExams) {
            String ClassId = classStudent.getClassId();
            // Student Preference - ClassId
            if ((ClassId != null) && !(ClassId.isEmpty()) && !ClassId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentClassIdPreference(getApplicationContext(), ClassId);
            }

            Intent intent = new Intent(this, ExamsResultActivity.class);
            startActivity(intent);
        }
        if (v == btnFees) {

            String StudentId = classStudent.getEnrollId();
            // Student Preference - EnrollId
            if ((StudentId != null) && !(StudentId.isEmpty()) && !StudentId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentRegisteredIdPreference(getApplicationContext(), StudentId);
            }

            Intent intent = new Intent(getApplicationContext(), FeeStatusActivity.class);
            startActivity(intent);
        }
        if(v==btnAttendance){
            String ClassId = classStudent.getClassId();
            // Student Preference - ClassId
            if ((ClassId != null) && !(ClassId.isEmpty()) && !ClassId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentClassIdPreference(getApplicationContext(), ClassId);
            }

            String StudentId = classStudent.getEnrollId();
            // Student Preference - EnrollId
            if ((StudentId != null) && !(StudentId.isEmpty()) && !StudentId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentRegisteredIdPreference(getApplicationContext(), StudentId);
            }
            Intent intent = new Intent(this, AttendanceActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}
