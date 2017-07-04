package com.palprotech.ensyfi.activity.studentmodule;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.student.viewlist.Attendance;
import com.palprotech.ensyfi.customview.caldroid_calendar.customcalendar.CaldroidFragment;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Narendar on 05/04/17.
 */

public class AttendanceActivity extends AppCompatActivity implements IServiceListener, DialogClickListener {

    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    private static final String TAG = "ClassTestHomework";

    View view;

    ServiceHelper serviceHelper;
    ArrayList<Attendance> attendanceArrayList;
    int pageNumber = 0, totalCount = 0;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();
    private SearchView mSearchView = null;
    ArrayList<String> dateStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        caldroidFragment = new CaldroidFragment();
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        dateStrings = new ArrayList<String>();  // ArrayList of strings

        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        } else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            caldroidFragment.setArguments(args);
        }

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        callGetAttendanceService();

//        leaveDates();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void callGetAttendanceService() {
        /*if(eventsListAdapter != null){
            eventsListAdapter.clearSearchFlag();
        }*/
        if (attendanceArrayList != null)
            attendanceArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            //    eventServiceHelper.makeRawRequest(FindAFunConstants.GET_ADVANCE_SINGLE_SEARCH);
            new HttpAsyncTask().execute("");
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }

    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAM_CLASS_ID, PreferenceStorage.getStudentClassIdPreference(getApplicationContext()));
                jsonObject.put(EnsyfiConstants.PARAM_STUDENT_ID, PreferenceStorage.getStudentRegisteredPreference(getApplicationContext()));


            } catch (JSONException e) {
                e.printStackTrace();
            }


            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_STUDENT_ATTENDANCD_API;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void result) {
            progressDialogHelper.cancelProgressDialog();
        }
    }

    private void leaveDates() {


//        ArrayList<Attendance> dateStrings1 = new ArrayList<Attendance>();

//        dateStrings.add("2017-05-14");
//
//        dateStrings.add("2017-05-16");
//
//        dateStrings.add("2017-05-18");
//
//        dateStrings.add("2017-05-20");
//
//        dateStrings.add("2017-05-25");

        ArrayList<Date> dates = new ArrayList<>(dateStrings.size()); // ArrayList of dates

        for (String s : dateStrings) {

            try {
                Date dateObj = new SimpleDateFormat("yyyy-MM-dd").parse(s);

                dates.add(dateObj);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        for (Date d : dates) {
            String str = new SimpleDateFormat("yyyy-MM-dd").format(d);

            System.out.println(str);
        }

        // Customize

        caldroidFragment.setDisableDates(dates);
        caldroidFragment.refreshView();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
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
//                        AlertDialogHelper.showSimpleAlertDialog(this, msg);

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
    public void onResponse(final JSONObject response) {

        if (validateSignInResponse(response)) {

            Log.d("ajazFilterresponse : ", response.toString());

            String repo = response.toString();
            try {
                JSONArray getData = response.getJSONArray("attendenceDetails");

                for (int l = 0; l < getData.length(); l++) {
                    dateStrings.add(getData.getJSONObject(l).getString("abs_date"));
                }

                ArrayList<Date> dates = new ArrayList<>(dateStrings.size()); // ArrayList of dates

                for (String s : dateStrings) {

                    try {
                        Date dateObj = new SimpleDateFormat("yyyy-MM-dd").parse(s);

                        dates.add(dateObj);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                for (Date d : dates) {
                    String str = new SimpleDateFormat("yyyy-MM-dd").format(d);

                    System.out.println(str);
                }

                // Customize

                caldroidFragment.setDisableDates(dates);
                caldroidFragment.refreshView();

//            JSONObject userData = getData.getJSONObject(0);
            } catch (Exception ex) {
            }

//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                progressDialogHelper.hideProgressDialog();
////                loadMoreListView.onLoadMoreComplete();
//
//                Gson gson = new Gson();
//                AttendanceList attendanceList = gson.fromJson(response.toString(), AttendanceList.class);
//                if (attendanceList.getAttendanceList() != null && attendanceList.getAttendanceList().size() > 0) {
//                    totalCount = attendanceList.getCount();
//                    isLoadingForFirstTime = false;
//                    updateListAdapter(attendanceList.getAttendanceList());
//                }
//            }
//        });
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    protected void updateListAdapter(ArrayList<Attendance> classTestArrayList) {
        this.attendanceArrayList.addAll(classTestArrayList);
//        if (classTestListAdapter == null) {
//            classTestListAdapter = new ClassTestListAdapter(this, this.classTestArrayList);
//            loadMoreListView.setAdapter(classTestListAdapter);
//        } else {
//            classTestListAdapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void onError(final String error) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
//                loadMoreListView.onLoadMoreComplete();
                AlertDialogHelper.showSimpleAlertDialog(AttendanceActivity.this, error);
            }
        });
    }
}
