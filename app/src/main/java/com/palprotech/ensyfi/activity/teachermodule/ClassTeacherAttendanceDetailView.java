package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.ClassTeacherAttendanceListAdapter;
import com.palprotech.ensyfi.adapter.teachermodule.ClassTeacherAttendeeListAdapter;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherAttendance;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherAttendanceList;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherAttendee;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherAttendeeList;
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

import java.util.ArrayList;

public class ClassTeacherAttendanceDetailView extends AppCompatActivity implements IServiceListener, DialogClickListener {

    private static final String TAG = "ClassTeacherView";

    private ClassTeacherAttendance classTeacherAttendance;
    ServiceHelper serviceHelper;
    ProgressDialogHelper progressDialogHelper;
    ListView attendeeList;
    ClassTeacherAttendeeListAdapter classTeacherAttendeeListAdapter;
    ArrayList<ClassTeacherAttendee> attendeeArrayList;
    protected boolean isLoadingForFirstTime = true;
    int totalCount = 0;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_teacher_attendance_detail_view);

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SetUI();
    }
    private void SetUI() {
        attendeeList = (ListView) findViewById(R.id.listView_students);
        attendeeArrayList = new ArrayList<>();
        classTeacherAttendance = (ClassTeacherAttendance) getIntent().getSerializableExtra("attendanceObj");
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);

        progressDialogHelper = new ProgressDialogHelper(this);
        callGetEventService();
    }

    public void callGetEventService() {

        if (attendeeArrayList != null)
            attendeeArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            new HttpAsyncTask().execute("");
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAM_CLASS_ID, PreferenceStorage.getClassTeacher(getApplicationContext()));
                jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_ID, classTeacherAttendance.getAtId());
                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(getApplicationContext()));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.GET_CLASS_TEACHER_ATTENEE_VIEW;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void result) {
            progressDialogHelper.cancelProgressDialog();
        }
    }

    @Override
    public void onResponse(final JSONObject response) {
        if (validateSignInResponse(response)) {
            Log.d("ajazFilterresponse : ", response.toString());

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialogHelper.hideProgressDialog();

                    Gson gson = new Gson();
                    ClassTeacherAttendeeList classTeacherAttendeeList = gson.fromJson(response.toString(), ClassTeacherAttendeeList.class);
                    if (classTeacherAttendeeList.getClassTeacherAttendeeDetails() != null && classTeacherAttendeeList.getClassTeacherAttendeeDetails().size() > 0) {
                        totalCount = classTeacherAttendeeList.getCount();
                        isLoadingForFirstTime = false;
                        updateListAdapter(classTeacherAttendeeList.getClassTeacherAttendeeDetails());
                    }
                }
            });
        } else {
            Log.d(TAG, "Error while sign In");
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

    protected void updateListAdapter(ArrayList<ClassTeacherAttendee> attendeeArrayList) {
        this.attendeeArrayList.addAll(attendeeArrayList);
        if (classTeacherAttendeeListAdapter == null) {
            classTeacherAttendeeListAdapter = new ClassTeacherAttendeeListAdapter(this, this.attendeeArrayList);
            attendeeList.setAdapter(classTeacherAttendeeListAdapter);
        } else {
            classTeacherAttendeeListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }


}