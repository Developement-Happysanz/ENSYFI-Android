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
import com.palprotech.ensyfi.activity.studentmodule.ExamsResultActivity;
import com.palprotech.ensyfi.activity.teachermodule.TeacherTimeTableActivity;
import com.palprotech.ensyfi.bean.admin.viewlist.ClassStudent;
import com.palprotech.ensyfi.bean.admin.viewlist.TeacherView;
import com.palprotech.ensyfi.bean.teacher.support.SaveTeacherData;
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

public class TeacherViewDetailsActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, View.OnClickListener {

    private static final String TAG = TeacherViewDetailsActivity.class.getName();
    private TeacherView teacherView;
    private TextView txtTeacherName, txtTeacherId;
    private Button btnTeacherTimeTable;
    private ServiceHelper serviceHelper;
    private ProgressDialogHelper progressDialogHelper;
    private SaveTeacherData teacherData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_details);

        teacherView = (TeacherView) getIntent().getSerializableExtra("eventObj");

        txtTeacherName = (TextView) findViewById(R.id.txtTeacherName);
        txtTeacherId = (TextView) findViewById(R.id.txtTeacherId);

        btnTeacherTimeTable = (Button) findViewById(R.id.btnTeacherTimeTable);
        btnTeacherTimeTable.setOnClickListener(this);

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);

        teacherData = new SaveTeacherData(this);

        populateData();
        String view = "";
    }

    private void populateData() {
        txtTeacherName.setText(teacherView.getName());
        txtTeacherId.setText(teacherView.getTeacherId());

        if (CommonUtils.isNetworkAvailable(this)) {


            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_TEACHER_ID_SHOW, teacherView.getTeacherId());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_TEACHERS_INFO;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);


        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnTeacherTimeTable) {

            Intent intent = new Intent(this, TeacherTimeTableActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {

            String repo = response.toString();
//            longInfo(repo);

            try {
                JSONArray getTimeTable = response.getJSONArray("timeTable");
                teacherData.saveTeacherTimeTable(getTimeTable);

                JSONArray getClassSubject = response.getJSONArray("class_name");
                teacherData.saveTeacherHandlingSubject(getClassSubject);

                JSONArray getTeacherProfile = response.getJSONArray("teacherProfile");
                teacherData.saveTeacherProfile(getTeacherProfile);

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
}
