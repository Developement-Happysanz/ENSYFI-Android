package com.palprotech.ensyfi.activity.teachermodule;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.studentmodule.DayViewListAdapter;
import com.palprotech.ensyfi.adapter.studentmodule.MonthViewListAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.student.viewlist.DayView;
import com.palprotech.ensyfi.bean.student.viewlist.DayViewList;
import com.palprotech.ensyfi.bean.student.viewlist.MonthView;
import com.palprotech.ensyfi.bean.student.viewlist.MonthViewList;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Created by Admin on 11-07-2017.
 */

public class AttendanceStatusActivity extends AppCompatActivity implements DialogClickListener, AdapterView.OnItemClickListener, IServiceListener, View.OnClickListener {

    private static final String TAG = AttendanceStatusActivity.class.getName();
    private ImageView btnBack, btnAddAttendnace, imgCalendar;
    ListView loadMoreListView;
    View view;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();
    private Spinner spnClassList;
    Vector<String> vecClassSectionList, vecAcademicMonths;
    List<String> lsClassList = new ArrayList<String>();
    List<String> lsAcademicMonthsList = new ArrayList<String>();
    ListView lvStudent;
    private String storeClassId;
    SQLiteHelper db;
    Calendar c = Calendar.getInstance();
    private RadioGroup radioDayMonthView;
    private String checkDayMonthType = "day";
    private Button selectDateMonth, selectMonth;
    private String mFromDateVal = null;
    String singleDate = "";
    DatePickerDialog mFromDatePickerDialog = null;
    AlertDialog dialog = null;
    private boolean isDoneClick = false;
    ServiceHelper serviceHelper;
    String getMonthName;
    Spinner spinMonthView;
    DayViewListAdapter dayViewListAdapter;
    ArrayList<DayView> dayViewArrayList;
    MonthViewListAdapter monthViewListAdapter;
    ArrayList<MonthView> monthViewArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_status);
        setView();
    }

    @Override
    public void onClick(View v) {
        if (v == btnBack) {
            finish();
        }
        if (v == btnAddAttendnace) {
            Intent intent = new Intent(getApplicationContext(), TeacherAttendanceInsertActivity.class);
            startActivity(intent);
        }
        if (v == selectDateMonth) {

            final DatePickerDialog.OnDateSetListener fromdate = new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int month, int day) {
                    Log.d(TAG, "From selected");
                    if (isDoneClick) {
                        ((Button) findViewById(R.id.btnDateMonth)).setText(formatDate(year, month, day));
                        mFromDateVal = formatDateServer(year, month, day);
                    } else {
                        Log.e("Clear", "Clear");
                        ((Button) findViewById(R.id.btnDateMonth)).setText("Select Date");
                        mFromDateVal = "";
                    }
                }
            };

            final Calendar c = Calendar.getInstance();
            final int currentYear = c.get(Calendar.YEAR);
            final int currentMonth = c.get(Calendar.MONTH);
            final int currentDay = c.get(Calendar.DAY_OF_MONTH);

            singleDate = "";

            mFromDatePickerDialog = new DatePickerDialog(AttendanceStatusActivity.this, R.style.datePickerTheme, fromdate, currentYear,
                    currentMonth, currentDay);

            mFromDatePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isDoneClick = true;
                    Log.d(TAG, "Done clicked");
                    DatePicker datePicker = mFromDatePickerDialog.getDatePicker();
                    fromdate.onDateSet(datePicker, datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                    callOnDayAttendanceViewService();
                    mFromDatePickerDialog.dismiss();
                }
            });

            mFromDatePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Clear", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isDoneClick = false;
                    ((Button) findViewById(R.id.btnDateMonth)).setText("Select Date");
                    mFromDatePickerDialog.dismiss();
                }
            });
            mFromDatePickerDialog.show();
        }
    }

    private void callOnDayAttendanceViewService() {

        if (dayViewArrayList != null)
            dayViewArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAM_CLASS_ID, storeClassId);
                jsonObject.put(EnsyfiConstants.PARAMS_DISPLAY_TYPE, checkDayMonthType);
                jsonObject.put(EnsyfiConstants.PARAMS_DISPLAY_DATE, mFromDateVal);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_STUDENT_ATTENDANCE_API;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private void callOnMonthAttendanceViewService() {

        if (monthViewArrayList != null)
            monthViewArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAM_CLASS_ID, storeClassId);
                jsonObject.put(EnsyfiConstants.PARAMS_DISPLAY_TYPE, checkDayMonthType);
                jsonObject.put(EnsyfiConstants.PARAMS_DISPLAY_MONTH_YEAR, getMonthName);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_STUDENT_ATTENDANCE_API;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    @Override
    public void onResponse(final JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        try {
            if (validateSignInResponse(response)) {
                Log.d("ajazFilterresponse : ", response.toString());
                if (checkDayMonthType.equalsIgnoreCase("day")) {
                    LinearLayout lisLay = findViewById(R.id.lis_layout);
                    lisLay.setBackground(ContextCompat.getDrawable(this, R.drawable.shadow_round));
                    lisLay.setElevation(6.0f);
                    JSONArray getData = response.getJSONArray("attendenceDetails");
                    if (getData != null && getData.length() > 0) {

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressDialogHelper.hideProgressDialog();

                                Gson gson = new Gson();
                                DayViewList dayViewList = gson.fromJson(response.toString(), DayViewList.class);
                                if (dayViewList.getDayView() != null && dayViewList.getDayView().size() > 0) {
                                    updateDayViewListAdapter(dayViewList.getDayView());
                                }
                            }
                        });

                    } else {
                        if (dayViewArrayList != null) {
                            dayViewArrayList.clear();
                            dayViewListAdapter = new DayViewListAdapter(this, this.dayViewArrayList);
                            loadMoreListView.setAdapter(dayViewListAdapter);
                        }
                    }
                } else {
                    LinearLayout lisLay = findViewById(R.id.lis_layout);
                    lisLay.setBackground(ContextCompat.getDrawable(this, R.drawable.shadow_round));
                    lisLay.setElevation(6.0f);
                    JSONArray getData = response.getJSONArray("attendenceDetails");
                    if (getData != null && getData.length() > 0) {

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressDialogHelper.hideProgressDialog();

                                Gson gson = new Gson();
                                MonthViewList monthViewList = gson.fromJson(response.toString(), MonthViewList.class);
                                if (monthViewList.getMonthView() != null && monthViewList.getMonthView().size() > 0) {
                                    updateMonthViewListAdapter(monthViewList.getMonthView());
                                }
                            }
                        });
                    } else {
                        if (monthViewArrayList != null) {
                            monthViewArrayList.clear();
                            monthViewListAdapter = new MonthViewListAdapter(this, this.monthViewArrayList);
                            loadMoreListView.setAdapter(monthViewListAdapter);
                        }
                    }
                }
            } else {
                Log.d(TAG, "Error while sign In");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
                AlertDialogHelper.showSimpleAlertDialog(AttendanceStatusActivity.this, error);
            }
        });
    }

    protected void updateDayViewListAdapter(ArrayList<DayView> dayViewArrayList) {
        this.dayViewArrayList.addAll(dayViewArrayList);
//        if (dayViewListAdapter == null) {
        dayViewListAdapter = new DayViewListAdapter(AttendanceStatusActivity.this, this.dayViewArrayList);
        loadMoreListView.setAdapter(dayViewListAdapter);
//        } else {
        dayViewListAdapter.notifyDataSetChanged();
//        }
    }

    protected void updateMonthViewListAdapter(ArrayList<MonthView> monthViewArrayList) {
        this.monthViewArrayList.addAll(monthViewArrayList);
//        if (monthViewListAdapter == null) {
        monthViewListAdapter = new MonthViewListAdapter(AttendanceStatusActivity.this, this.monthViewArrayList);
        loadMoreListView.setAdapter(monthViewListAdapter);
//        } else {
        monthViewListAdapter.notifyDataSetChanged();
//        }
    }

    private void setView() {
        btnBack = (ImageView) findViewById(R.id.back_res);
        btnBack.setOnClickListener(this);
        imgCalendar = (ImageView) findViewById(R.id.cal);
        btnAddAttendnace = (ImageView) findViewById(R.id.addAttendance);
        btnAddAttendnace.setOnClickListener(this);
        db = new SQLiteHelper(getApplicationContext());
        vecClassSectionList = new Vector<String>();
        vecAcademicMonths = new Vector<String>();
        spnClassList = (Spinner) findViewById(R.id.class_list_spinner);
        lvStudent = (ListView) findViewById(R.id.listView_events);
        loadMoreListView = (ListView) findViewById(R.id.listView_events);
        loadMoreListView.setOnItemClickListener(this);
        dayViewArrayList = new ArrayList<>();
        monthViewArrayList = new ArrayList<>();
        radioDayMonthView = (RadioGroup) findViewById(R.id.radioDayMonthView);
        selectDateMonth = (Button) findViewById(R.id.btnDateMonth);
        selectDateMonth.setOnClickListener(this);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        spinMonthView = (Spinner) findViewById(R.id.btnMonth);
        progressDialogHelper = new ProgressDialogHelper(this);
        getClassList();

        spnClassList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String className = parent.getItemAtPosition(position).toString();
                GetClassId(className);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        radioDayMonthView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radioDayView:

                        if (monthViewArrayList != null)
                            monthViewArrayList.clear();
                        if (dayViewArrayList != null)
                            dayViewArrayList.clear();

                        checkDayMonthType = "day";
                        selectDateMonth.setVisibility(View.VISIBLE);
                        imgCalendar.setVisibility(View.VISIBLE);
                        spinMonthView.setVisibility(View.GONE);
                        break;

                    case R.id.radioMonthView:

                        if (dayViewArrayList != null)
                            dayViewArrayList.clear();
                        if (monthViewArrayList != null)
                            monthViewArrayList.clear();

                        checkDayMonthType = "month";
                        selectDateMonth.setVisibility(View.GONE);
                        imgCalendar.setVisibility(View.GONE);
                        spinMonthView.setVisibility(View.VISIBLE);
                        getAcademicMonthsList();
                        break;
                }
            }
        });

        spinMonthView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (!item.equalsIgnoreCase("Select Month")) {
                    getMonthName = item;
                    callOnMonthAttendanceViewService();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onEvent list item click" + position);
        if (checkDayMonthType.equalsIgnoreCase("month")) {
            MonthView monthView = null;

            if ((monthViewListAdapter != null) && (monthViewListAdapter.ismSearching())) {
                Log.d(TAG, "while searching");
                int actualindex = monthViewListAdapter.getActualEventPos(position);
                Log.d(TAG, "actual index" + actualindex);
                monthView = monthViewArrayList.get(actualindex);
            } else {
                monthView = monthViewArrayList.get(position);
            }
            String ab_status = monthView.getAStatus();
            if (ab_status.equalsIgnoreCase("P")) {
                AlertDialogHelper.showSimpleAlertDialog(this, "No Leaves");
            }
            else {
                Intent intent = new Intent(this, AttendanceMonthViewActivity.class);
                intent.putExtra("eventObj", monthView);
                intent.putExtra("monthYear", getMonthName);
                startActivity(intent);
            }
        }
    }

    private void getAcademicMonthsList() {
        try {
            Cursor c = db.getAcademicMonths();
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        vecAcademicMonths.add(c.getString(0));
                    } while (c.moveToNext());
                }
            }
            lsAcademicMonthsList.add("Select Month");
            for (int i = 0; i < vecAcademicMonths.size(); i++) {
                lsAcademicMonthsList.add(vecAcademicMonths.get(i));
            }

            db.close();
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item_ns, lsAcademicMonthsList);
            spinMonthView.setAdapter(dataAdapter3);
            spinMonthView.setWillNotDraw(false);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error academic months lookup", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void GetClassId(String classID) {

        try {
            Cursor c = db.getClassId(classID);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        storeClassId = c.getString(0);
                    } while (c.moveToNext());
                }
            }
            db.close();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG)
                    .show();
            e.printStackTrace();
        }
    }

    private void getClassList() {
        Vector<String> vecClassList = new Vector<String>();
        List<String> lsClassList = new ArrayList<String>();
        try {
            Cursor c = db.getTeachersClass();
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        vecClassList.add(c.getString(1));
                    } while (c.moveToNext());
                }
            }
            for (int i = 0; i < vecClassList.size(); i++) {
                lsClassList.add(vecClassList.get(i));
            }
            HashSet hs = new HashSet();
            TreeSet ts = new TreeSet(hs);
            ts.addAll(lsClassList);
            lsClassList.clear();
            lsClassList.addAll(ts);
            db.close();
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item_ns, lsClassList);

            spnClassList.setAdapter(dataAdapter3);
            spnClassList.setWillNotDraw(false);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error getting class list lookup", Toast.LENGTH_LONG).show();
            e.printStackTrace();
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

                        if (checkDayMonthType.equalsIgnoreCase("day")) {
                            if (dayViewArrayList != null) {
                                dayViewArrayList.clear();
                                dayViewListAdapter = new DayViewListAdapter(this, this.dayViewArrayList);
                                loadMoreListView.setAdapter(dayViewListAdapter);
                            }
                        } else {
                            if (monthViewArrayList != null) {
                                monthViewArrayList.clear();
                                monthViewListAdapter = new MonthViewListAdapter(this, this.monthViewArrayList);
                                loadMoreListView.setAdapter(monthViewListAdapter);
                            }
                        }

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

}
