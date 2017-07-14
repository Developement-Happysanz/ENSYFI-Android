package com.palprotech.ensyfi.activity.studentmodule;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.studentmodule.MonthViewStudentLeaveDaysListAdapter;
import com.palprotech.ensyfi.bean.student.viewlist.MonthView;
import com.palprotech.ensyfi.bean.student.viewlist.MonthViewStudentLeaveDays;
import com.palprotech.ensyfi.bean.student.viewlist.MonthViewStudentLeaveDaysList;
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

/**
 * Created by Admin on 12-07-2017.
 */

public class AttendanceMonthViewActivity extends AppCompatActivity implements DialogClickListener, IServiceListener, View.OnClickListener {

    private static final String TAG = AttendanceMonthViewActivity.class.getName();
    private MonthView monthView;
    private String getMonthYear;
    ListView loadMoreListView;
    View view;

    MonthViewStudentLeaveDaysListAdapter monthViewStudentLeaveDaysListAdapter;
    ServiceHelper serviceHelper;
    ArrayList<MonthViewStudentLeaveDays> monthViewStudentLeaveDaysArrayList;
    int pageNumber = 0, totalCount = 0;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();
    private SearchView mSearchView = null;
    Button btnFullAttendance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_month_view);
        Intent intent = getIntent();

        monthView = (MonthView) getIntent().getSerializableExtra("eventObj");
        getMonthYear = intent.getStringExtra("monthYear");
        String okNew = "";

        loadMoreListView = (ListView) findViewById(R.id.listView_events);

//        loadMoreListView.setOnItemClickListener(this);
        monthViewStudentLeaveDaysArrayList = new ArrayList<>();

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);

        progressDialogHelper = new ProgressDialogHelper(this);
        btnFullAttendance = (Button) findViewById(R.id.btnFullAttendance);
        btnFullAttendance.setOnClickListener(this);

        callMonthViewStudentLeaveDaysViewService();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callMonthViewStudentLeaveDaysViewService() {

        if (monthViewStudentLeaveDaysArrayList != null)
            monthViewStudentLeaveDaysArrayList.clear();

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

                jsonObject.put(EnsyfiConstants.PARAM_CLASS_ID, monthView.getClassId());
                jsonObject.put(EnsyfiConstants.KEY_ATTENDANCE_HISTORY_STUDENT_ID, monthView.getEnrollId());
                jsonObject.put(EnsyfiConstants.PARAMS_DISPLAY_MONTH_YEAR, getMonthYear);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_STUDENT_ATTENDANCD_MONTH_VIEW_API;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void result) {
            progressDialogHelper.cancelProgressDialog();
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
    public void onClick(View v) {
        if (v == btnFullAttendance) {

            String StudentId = "", ClassId = "";
            StudentId = monthView.getEnrollId();
            ClassId = monthView.getClassId();

            // Student Preference - EnrollId
            if ((StudentId != null) && !(StudentId.isEmpty()) && !StudentId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentRegisteredIdPreference(getApplicationContext(), StudentId);
            }

            // Student Preference - ClassId
            if ((ClassId != null) && !(ClassId.isEmpty()) && !ClassId.equalsIgnoreCase("null")) {
                PreferenceStorage.saveStudentClassIdPreference(getApplicationContext(), ClassId);
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

    @Override
    public void onResponse(final JSONObject response) {
        if (validateSignInResponse(response)) {
            Log.d("ajazFilterresponse : ", response.toString());

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialogHelper.hideProgressDialog();

                    Gson gson = new Gson();
                    MonthViewStudentLeaveDaysList monthViewStudentLeaveDaysArrayList = gson.fromJson(response.toString(), MonthViewStudentLeaveDaysList.class);
                    if (monthViewStudentLeaveDaysArrayList.getMonthView() != null && monthViewStudentLeaveDaysArrayList.getMonthView().size() > 0) {
                        totalCount = monthViewStudentLeaveDaysArrayList.getCount();
                        isLoadingForFirstTime = false;
                        updateListAdapter(monthViewStudentLeaveDaysArrayList.getMonthView());
                    }
                }
            });
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    protected void updateListAdapter(ArrayList<MonthViewStudentLeaveDays> monthViewStudentLeaveDaysArrayList) {
        this.monthViewStudentLeaveDaysArrayList.addAll(monthViewStudentLeaveDaysArrayList);
        if (monthViewStudentLeaveDaysListAdapter == null) {
            monthViewStudentLeaveDaysListAdapter = new MonthViewStudentLeaveDaysListAdapter(this, this.monthViewStudentLeaveDaysArrayList);
            loadMoreListView.setAdapter(monthViewStudentLeaveDaysListAdapter);
        } else {
            monthViewStudentLeaveDaysListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
//                loadMoreListView.onLoadMoreComplete();
                AlertDialogHelper.showSimpleAlertDialog(AttendanceMonthViewActivity.this, error);
            }
        });
    }
}
