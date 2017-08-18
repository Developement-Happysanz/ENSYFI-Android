package com.palprotech.ensyfi.activity.general;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.AppValidator;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

//import org.joda.time.DateTime;
//import org.joda.time.Days;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Created by Narendar on 14/07/17.
 */

public class LeaveApplyActivity extends AppCompatActivity implements View.OnClickListener, IServiceListener, DialogClickListener {

    private Spinner spnLeaveType;
    private TextView dateFrom, dateTo, dateFromTime, timeTo;
    private EditText edtOnDutyRequestDetails;
    private Button btnRequest;
    protected ProgressDialogHelper progressDialogHelper;
    ServiceHelper serviceHelper;
    private static final String TAG = "LeaveApplyActivity";
    Handler mHandler = new Handler();
    SQLiteHelper database;
    List<String> lsLeaveList = new ArrayList<String>();
    Vector<String> vecLeaveList;
    String storeLeaveTypeId, storeLeaveId;
    RelativeLayout relativedate, relativetime;
    final Calendar c = Calendar.getInstance();
    LinearLayout frombackground, tobackground, fromtimebackgroud, totimebackground;
    private boolean isDoneClick = false;
    private String mFromDateVal = null;
    private String mToDateVal = null;
    private String mFromTimeVal = null;
    private String mToTimeVal = null;
    String singleDate = "", checkVal = "N";
    String formattedServerDate;
    DatePickerDialog mFromDatePickerDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_application_request);

        spnLeaveType = (Spinner) findViewById(R.id.class_list_spinner);

        dateFrom = (TextView) findViewById(R.id.dateFrom);
        dateFrom.setOnClickListener(this);

        dateTo = (TextView) findViewById(R.id.dateTo);
        dateTo.setOnClickListener(this);

        dateFromTime = (TextView) findViewById(R.id.dateFromTime);
        dateFromTime.setOnClickListener(this);

        timeTo = (TextView) findViewById(R.id.timeTo);
        timeTo.setOnClickListener(this);

        edtOnDutyRequestDetails = (EditText) findViewById(R.id.edtOnDutyRequestDetails);

        btnRequest = (Button) findViewById(R.id.btnRequest);
        btnRequest.setOnClickListener(this);

        frombackground = (LinearLayout) findViewById(R.id.fromDatee);
        tobackground = (LinearLayout) findViewById(R.id.toDatee);

        fromtimebackgroud = (LinearLayout) findViewById(R.id.fromTimee);
        totimebackground = (LinearLayout) findViewById(R.id.toTimee);

        progressDialogHelper = new ProgressDialogHelper(this);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        database = new SQLiteHelper(getApplicationContext());
        vecLeaveList = new Vector<String>();
        relativedate = (RelativeLayout) findViewById(R.id.relativedate);
        relativetime = (RelativeLayout) findViewById(R.id.relativetime);

        loadLeaveType();

        loadFromDate();
        loadToDate();

        loadFromTime();
        loadToTime();

        spnLeaveType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String leaveType = parent.getItemAtPosition(position).toString();

                getLeaveTypeId(leaveType);

                int leaveTypeId = Integer.parseInt(storeLeaveTypeId);

                if (leaveTypeId == 0) {
                    relativetime.setVisibility(View.VISIBLE);
                    relativedate.setVisibility(View.VISIBLE);
                } else {
                    relativetime.setVisibility(View.GONE);
                    relativedate.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    private void loadFromDate() {
        SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = DF.format(c.getTime());
        SimpleDateFormat serverDF = new SimpleDateFormat("yyyy-MM-dd");
        String formattedServerDate = serverDF.format(c.getTime());

        frombackground.setBackgroundColor(Color.parseColor("#663366"));
        dateFrom.setCompoundDrawablesWithIntrinsicBounds(R.drawable.od_from_date_selected, 0, 0, 0);
        dateFrom.setTextColor((Color.parseColor("#663366")));

        ((TextView) findViewById(R.id.dateFrom)).setText(formattedDate);

        mFromDateVal = formattedServerDate;
    }

    private void loadToDate() {
        SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = DF.format(c.getTime());
        SimpleDateFormat serverDF = new SimpleDateFormat("yyyy-MM-dd");
        String formattedServerDate = serverDF.format(c.getTime());

        tobackground.setBackgroundColor(Color.parseColor("#663366"));
        dateTo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.od_from_date_selected, 0, 0, 0);
        dateTo.setTextColor((Color.parseColor("#663366")));

        ((TextView) findViewById(R.id.dateTo)).setText(formattedDate);

        mToDateVal = formattedServerDate;
    }

    private void loadFromTime() {
        SimpleDateFormat DF = new SimpleDateFormat("hh:mm a");
        String formattedTime = DF.format(c.getTime());

        dateFromTime.setText(formattedTime);

        mFromTimeVal = formattedTime;
    }

    private void loadToTime() {
        SimpleDateFormat DF = new SimpleDateFormat("hh:mm a");
        String formattedTime = DF.format(c.getTime());

        timeTo.setText(formattedTime);

        mToTimeVal = formattedTime;
    }

    private void getLeaveTypeId(String leaveTypeName) {

        try {
            Cursor c = database.getLeaveTypeId(leaveTypeName);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        storeLeaveTypeId = c.getString(0);
                        storeLeaveId = c.getString(1);
                    } while (c.moveToNext());
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void getLeaveList() {

        try {
            Cursor c = database.getLeaveTypeList();
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        vecLeaveList.add(c.getString(1));
                    } while (c.moveToNext());
                }
            }
            for (int i = 0; i < vecLeaveList.size(); i++) {
                lsLeaveList.add(vecLeaveList.get(i));
            }
            HashSet hs = new HashSet();
            TreeSet ts = new TreeSet(hs);
            ts.addAll(lsLeaveList);
            lsLeaveList.clear();
            lsLeaveList.addAll(ts);
            database.close();
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item_ns, lsLeaveList);

            spnLeaveType.setAdapter(dataAdapter3);
            spnLeaveType.setWillNotDraw(false);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void loadLeaveType() {
        checkVal = "Y";
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(EnsyfiConstants.PARAMS_FP_USER_ID, PreferenceStorage.getUserId(getApplicationContext()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
        String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_USER_LEAVES_TYPE_API;
        serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

    }

    @Override
    public void onClick(View v) {
        if (v == dateFrom) {
            frombackground.setBackgroundColor(Color.parseColor("#663366"));
            dateFrom.setCompoundDrawablesWithIntrinsicBounds(R.drawable.od_from_date_selected, 0, 0, 0);
            dateFrom.setTextColor((Color.parseColor("#663366")));

            final DatePickerDialog.OnDateSetListener fromdate = new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int month, int day) {
                    Log.d(TAG, "From selected");
                    if (isDoneClick) {
                        ((TextView) findViewById(R.id.dateFrom)).setText(formatDate(year, month, day));
                        mFromDateVal = formatDateServer(year, month, day);
                    } else {
                        Log.e("Clear", "Clear");
                        ((TextView) findViewById(R.id.dateFrom)).setText("");
                        mFromDateVal = "";
                    }
                }
            };

            final Calendar c = Calendar.getInstance();
            final int currentYear = c.get(Calendar.YEAR);
            final int currentMonth = c.get(Calendar.MONTH);
            final int currentDay = c.get(Calendar.DAY_OF_MONTH);

            singleDate = "";

            mFromDatePickerDialog = new DatePickerDialog(LeaveApplyActivity.this, R.style.datePickerTheme, fromdate, currentYear,
                    currentMonth, currentDay);

            mFromDatePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isDoneClick = true;
                    Log.d(TAG, "Done clicked");
                    DatePicker datePicker = mFromDatePickerDialog.getDatePicker();
                    fromdate.onDateSet(datePicker, datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                    mFromDatePickerDialog.dismiss();
                }
            });
            mFromDatePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Clear", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isDoneClick = false;
                    ((TextView) findViewById(R.id.dateFrom)).setText("");
                    mFromDatePickerDialog.dismiss();
                }
            });
            mFromDatePickerDialog.show();
        }

        if (v == dateTo) {

            tobackground.setBackgroundColor(Color.parseColor("#663366"));
            dateTo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.od_from_date_selected, 0, 0, 0);
            dateTo.setTextColor((Color.parseColor("#663366")));

            final DatePickerDialog.OnDateSetListener todate = new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int month, int day) {

                    if (isDoneClick) {
                        ((TextView) findViewById(R.id.dateTo)).setText(formatDate(year, month, day));
                        mToDateVal = formatDateServer(year, month, day);
                    } else {
                        ((TextView) findViewById(R.id.dateTo)).setText("Select Date");
                        mToDateVal = "";
                    }
                }
            };

            final int currentYear = c.get(Calendar.YEAR);
            final int currentMonth = c.get(Calendar.MONTH);
            final int currentDay = c.get(Calendar.DAY_OF_MONTH);

            singleDate = "";

            final DatePickerDialog dpd = new DatePickerDialog(LeaveApplyActivity.this, R.style.datePickerTheme, todate, currentYear,
                    currentMonth, currentDay);
            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isDoneClick = true;
                    DatePicker datePicker = dpd.getDatePicker();
                    todate.onDateSet(datePicker, datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                    dpd.dismiss();
                }
            });
            dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Clear", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isDoneClick = false;
                    ((TextView) findViewById(R.id.dateTo)).setText("Select Date");
                    dpd.dismiss();
                }
            });
            dpd.show();
        }
        if (v == dateFromTime) {

            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(LeaveApplyActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    try {
                        String s = selectedHour + ":" + selectedMinute + ":00";
                        DateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
                        Date d = f1.parse(s);
                        DateFormat f2 = new SimpleDateFormat("hh:mm a");
                        f2.format(d).toUpperCase();// "12:18am"
                        mFromTimeVal = f2.format(d).toUpperCase();
                        dateFromTime.setText(f2.format(d).toUpperCase());
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
            }, hour, minute, false);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
        if (v == timeTo) {

            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(LeaveApplyActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    try {
                        String s = selectedHour + ":" + selectedMinute + ":00";
                        DateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
                        Date d = f1.parse(s);
                        DateFormat f2 = new SimpleDateFormat("hh:mm a");
                        mToTimeVal = f2.format(d).toUpperCase();
                        timeTo.setText(f2.format(d).toUpperCase());
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
            }, hour, minute, false);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        }
        if (v == btnRequest) {
            checkVal = "YES";
            callLeaveRequest();
        }
    }

    private boolean validateFields() {
        int getDate = 0,getDate1 = 0;
        try {

            DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Date dateFrom = format.parse(this.dateFrom.getText().toString().trim());
            Date dateTo = format.parse(this.dateTo.getText().toString().trim());

//            DateTime dt1 = new DateTime(dateFrom);
//            DateTime dt2 = new DateTime(dateTo);

            getDate1 = dateFrom.compareTo(dateTo);

//            getDate = Days.daysBetween(dt1, dt2).getDays();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (!AppValidator.checkNullString(this.spnLeaveType.getSelectedItem().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, "Select valid leave type");
            return false;
        } else if (!AppValidator.checkNullString(this.edtOnDutyRequestDetails.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid leave details");
            return false;
        } else if (getDate1 > 0) {
            AlertDialogHelper.showSimpleAlertDialog(this, "ToDate should not lesser than FromDate");
            return false;
        } else {
            return true;
        }
    }

    private void callLeaveRequest() {
        try {

            String description = "";

            description = edtOnDutyRequestDetails.getText().toString();

            if (validateFields()) {
                if (CommonUtils.isNetworkAvailable(this)) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(EnsyfiConstants.PARAMS_LEAVE_USER_TYPE, PreferenceStorage.getUserType(this));
                        jsonObject.put(EnsyfiConstants.PARAMS_LEAVE_USER_ID, PreferenceStorage.getUserId(this));
                        jsonObject.put(EnsyfiConstants.PARAMS_LEAVE_LEAVE_MASTER_ID, storeLeaveId);
                        jsonObject.put(EnsyfiConstants.PARAMS_LEAVE_LEAVE_TYPE, storeLeaveTypeId);
                        jsonObject.put(EnsyfiConstants.PARAMS_LEAVE_DATE_FROM, mFromDateVal);
                        jsonObject.put(EnsyfiConstants.PARAMS_LEAVE_DATE_TO, mToDateVal);
                        jsonObject.put(EnsyfiConstants.PARAMS_LEAVE_FROM_TIME, mFromTimeVal);
                        jsonObject.put(EnsyfiConstants.PARAMS_LEAVE_TO_TIME, mToTimeVal);
                        jsonObject.put(EnsyfiConstants.PARAMS_LEAVE_DESCRIPTION, description);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
                    String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_USER_LEAVES_APPLY_API;
                    serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
                } else {

                    AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String formatDate(int year, int month, int day) {

        String formattedDay = "", formattedMonth = "";
        month = month + 1;
        if (day < 10) {
            formattedDay = "0" + day;
        } else {
            formattedDay = "" + day;
        }

        if (month < 10) {
            formattedMonth = "0" + month;
        } else {
            formattedMonth = "" + month;
        }

        return formattedDay + "-" + formattedMonth + "-" + year;
    }

    private static String formatDateServer(int year, int month, int day) {

        String formattedDay = "", formattedMonth = "";
        month = month + 1;
        if (day < 10) {
            formattedDay = "0" + day;
        } else {
            formattedDay = "" + day;
        }

        if (month < 10) {
            formattedMonth = "0" + month;
        } else {
            formattedMonth = "" + month;
        }

        return year + "-" + formattedMonth + "-" + formattedDay;
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

    @Override
    public void onResponse(final JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {
            Log.d("ajazFilterresponse : ", response.toString());
            try {
                if (checkVal.contentEquals("YES")) {
                    String status = response.getString("status");
                    String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                    if ((status != null)) {
                        if (((status.equalsIgnoreCase("success")))) {

                            Log.d(TAG, "Show error dialog");
                            AlertDialogHelper.showSimpleAlertDialog(this, msg);
                            finish();
                        }
                    }
                }
                if (checkVal.contentEquals("Y")) {
                    JSONArray loadLeaveTypes = response.getJSONArray("leaveDetails");
                    SaveLeaveTypes(loadLeaveTypes);
                    getLeaveList();
                }
            } catch (Exception ex) {
            }
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
                AlertDialogHelper.showSimpleAlertDialog(LeaveApplyActivity.this, error);
            }
        });
    }

    private void SaveLeaveTypes(JSONArray getLeaveTypes) {
        try {
            database.deleteLeaveTypes();

            for (int i = 0; i < getLeaveTypes.length(); i++) {

                JSONObject jsonobj = getLeaveTypes.getJSONObject(i);

                String id = "";
                String leave_title = "";
                String leave_type = "";

                id = jsonobj.getString("id");
                leave_title = jsonobj.getString("leave_title");
                leave_type = jsonobj.getString("leave_type");

                System.out.println("id : " + i + " = " + id);
                System.out.println("leave_title : " + i + " = " + leave_title);
                System.out.println("leave_type : " + i + " = " + leave_type);

                String v1 = id,
                        v2 = leave_title,
                        v3 = leave_type;

                long l = database.leave_type_insert(v1, v2, v3);

                System.out.println("" + l);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
