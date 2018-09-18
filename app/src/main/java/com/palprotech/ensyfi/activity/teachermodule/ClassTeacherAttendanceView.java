package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.ClassTeacherAttendanceListAdapter;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherAttendance;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherAttendanceList;
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

public class ClassTeacherAttendanceView extends AppCompatActivity implements IServiceListener, DialogClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "ClassTeacherView";

    ServiceHelper serviceHelper;
    ProgressDialogHelper progressDialogHelper;
    ListView attendaceList;
    ClassTeacherAttendanceListAdapter classTeacherAttendanceListAdapter;
    ArrayList<ClassTeacherAttendance> attendanceArrayList;
    protected boolean isLoadingForFirstTime = true;
    int totalCount = 0;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_teacher_attendance);
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

        attendaceList = (ListView) findViewById(R.id.listView_daywise_attendance);
        attendaceList.setOnItemClickListener(this);
        attendanceArrayList = new ArrayList<>();

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);

        progressDialogHelper = new ProgressDialogHelper(this);
        callGetEventService();
    }

    public void callGetEventService() {

        if (attendanceArrayList != null)
            attendanceArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            new HttpAsyncTask().execute("");
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onEvent list item clicked" + position);
        ClassTeacherAttendance classTeacherAttendance = null;
        if ((classTeacherAttendanceListAdapter != null) && (classTeacherAttendanceListAdapter.ismSearching())) {
            Log.d(TAG, "while searching");
            int actualindex = classTeacherAttendanceListAdapter.getActualEventPos(position);
            Log.d(TAG, "actual index" + actualindex);
            classTeacherAttendance = attendanceArrayList.get(actualindex);
        } else {
            classTeacherAttendance = attendanceArrayList.get(position);
        }
        Intent intent = new Intent(this, ClassTeacherAttendanceOverview.class);
        intent.putExtra("attendanceObj", classTeacherAttendance);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAM_CLASS_ID, PreferenceStorage.getClassTeacher(getApplicationContext()));


            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_CLASS_TEACHER_ATTENDANCE_VIEW;
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
                    ClassTeacherAttendanceList classTeacherAttendanceList = gson.fromJson(response.toString(), ClassTeacherAttendanceList.class);
                    if (classTeacherAttendanceList.getClassTeacherDetails() != null && classTeacherAttendanceList.getClassTeacherDetails().size() > 0) {
                        totalCount = classTeacherAttendanceList.getCount();
                        isLoadingForFirstTime = false;
                        updateListAdapter(classTeacherAttendanceList.getClassTeacherDetails());
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

    protected void updateListAdapter(ArrayList<ClassTeacherAttendance> attendanceArrayList) {
        this.attendanceArrayList.addAll(attendanceArrayList);
        if (classTeacherAttendanceListAdapter == null) {
            classTeacherAttendanceListAdapter = new ClassTeacherAttendanceListAdapter(this, this.attendanceArrayList);
            attendaceList.setAdapter(classTeacherAttendanceListAdapter);
        } else {
            classTeacherAttendanceListAdapter.notifyDataSetChanged();
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
