package com.palprotech.ensyfi.activity.studentmodule;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    ServiceHelper serviceHelper;
    ArrayList<Attendance> attendanceArrayList;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();
    ArrayList<String> dateStrings;
    ArrayList<String> leaveDateStrings = new ArrayList<String>();
    ArrayList<String> oDdateStrings = new ArrayList<String>();
    private TextView txtWorkingDays, txtPresentDays, txtODDays, txtLeaveDays, txtAbsentDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        txtWorkingDays = (TextView) findViewById(R.id.txtWorkingDays);
        txtPresentDays = (TextView) findViewById(R.id.txtPresentDays);
        txtODDays = (TextView) findViewById(R.id.txtODDays);
        txtLeaveDays = (TextView) findViewById(R.id.txtLeaveDays);
        txtAbsentDays = (TextView) findViewById(R.id.txtAbsentDays);
        caldroidFragment = new CaldroidFragment();
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        dateStrings = new ArrayList<String>();  // ArrayList of strings // ArrayList of strings

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

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void callGetAttendanceService() {
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
                jsonObject.put(EnsyfiConstants.PARAM_STUDENT_ID, PreferenceStorage.getStudentRegisteredIdPreference(getApplicationContext()));
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

            try {
                JSONArray getData = response.getJSONArray("attendenceDetails");
                JSONObject getAttendanceHistory = response.getJSONObject("attendenceHistory");

                String totalWorkingDays, presentDays, odDays, leaveDays, absentDays;
                totalWorkingDays = getAttendanceHistory.getString("total_working_days");
                presentDays = getAttendanceHistory.getString("present_days");
                odDays = getAttendanceHistory.getString("od_days");
                leaveDays = getAttendanceHistory.getString("leave_days");
                absentDays = getAttendanceHistory.getString("absent_days");
                Double no1 = Double.parseDouble(totalWorkingDays),
                        no2 = Double.parseDouble(presentDays),
                        no3 = Double.parseDouble(odDays),
                        no4 = Double.parseDouble(leaveDays),
                        no5 = Double.parseDouble(absentDays);
                String s1, s2, s3, s4, s5;
                if (no1 > 1) {
                    s1 = totalWorkingDays + "\tDays";
                    txtWorkingDays.setText(s1);
                } else {
                    s1 = totalWorkingDays + "\tDay";
                    txtWorkingDays.setText(s1);
                }
                if (no2 > 1) {
                    s2 = presentDays + "\tDays";
                    txtPresentDays.setText(s2);
                } else {
                    s2 = presentDays + "\tDay";
                    txtPresentDays.setText(s2);
                }
                if (no3 > 1) {
                    s3 = odDays + "\tDays";
                    txtODDays.setText(s3);
                } else {
                    s3 = odDays + "\tDay";
                    txtODDays.setText(s3);
                }
                if (no4 > 1) {
                    s4 = leaveDays + "\tDays";
                    txtLeaveDays.setText(s4);
                } else {
                    s4 = leaveDays + "\tDay";
                    txtLeaveDays.setText(s4);
                }
                if (no5 > 1) {
                    s5 = absentDays + "\tDays";
                    txtAbsentDays.setText(s5);
                } else {
                    s5 = absentDays + "\tDay";
                    txtAbsentDays.setText(s5);
                }

                for (int l = 0; l < getData.length(); l++) {
                    if (getData.getJSONObject(l).getString("a_status").equalsIgnoreCase("A")) {
                        dateStrings.add(getData.getJSONObject(l).getString("abs_date"));
                    }
                }
                for (int l = 0; l < getData.length(); l++) {
                    if (getData.getJSONObject(l).getString("a_status").equalsIgnoreCase("OD")) {
                        oDdateStrings.add(getData.getJSONObject(l).getString("abs_date"));

                    }
                }

                for (int l = 0; l < getData.length(); l++) {
                    if (getData.getJSONObject(l).getString("a_status").equalsIgnoreCase("L")) {
                        leaveDateStrings.add(getData.getJSONObject(l).getString("abs_date"));
                    }
                }

                ArrayList<Date> dates = new ArrayList<>(dateStrings.size()); // ArrayList of leave dates
                ArrayList<Date> dates1 = new ArrayList<>(oDdateStrings.size()); // ArrayList of dates
                ArrayList<Date> dates2 = new ArrayList<>(leaveDateStrings.size()); // ArrayList of dates

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

                for (String s : oDdateStrings) {

                    try {
                        Date dateObj = new SimpleDateFormat("yyyy-MM-dd").parse(s);

                        dates1.add(dateObj);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                for (Date d : dates1) {
                    String str = new SimpleDateFormat("yyyy-MM-dd").format(d);

                    System.out.println(str);
                }

                for (String s : leaveDateStrings) {

                    try {
                        Date dateObj = new SimpleDateFormat("yyyy-MM-dd").parse(s);

                        dates2.add(dateObj);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                for (Date d : dates2) {
                    String str = new SimpleDateFormat("yyyy-MM-dd").format(d);

                    System.out.println(str);
                }

                // Customize

                caldroidFragment.setDisableDates(dates);
                caldroidFragment.setOdDates(dates1);
                caldroidFragment.setLeaveDates(dates2);
                caldroidFragment.refreshView();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    protected void updateListAdapter(ArrayList<Attendance> classTestArrayList) {
        this.attendanceArrayList.addAll(classTestArrayList);
    }

    @Override
    public void onError(final String error) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
                AlertDialogHelper.showSimpleAlertDialog(AttendanceActivity.this, error);
            }
        });
    }
}
