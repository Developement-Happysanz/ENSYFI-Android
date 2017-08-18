package com.palprotech.ensyfi.activity.general;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Admin on 10-07-2017.
 */

public class OnDutyRequestActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, View.OnClickListener {

    private static final String TAG = OnDutyRequestActivity.class.getName();
    private EditText edtOnDutyRequestFor, edtOnDutyRequestDetails;
    private TextView dateFrom, dateTo;
    private Button btnRequest;
    private boolean isDoneClick = false;
    String singleDate = "";
    DatePickerDialog mFromDatePickerDialog = null;
    private String mFromDateVal = null;
    private String mToDateVal = null;
    protected ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    final Calendar c = Calendar.getInstance();
    LinearLayout frombackground, tobackground;
    String formattedServerDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_duty_request);
        edtOnDutyRequestFor = (EditText) findViewById(R.id.edtOnDutyRequestFor);
        edtOnDutyRequestDetails = (EditText) findViewById(R.id.edtOnDutyRequestDetails);

        dateFrom = (TextView) findViewById(R.id.dateFrom);
        dateFrom.setOnClickListener(this);

        dateTo = (TextView) findViewById(R.id.dateTo);
        dateTo.setOnClickListener(this);

        btnRequest = (Button) findViewById(R.id.btnRequest);
        btnRequest.setOnClickListener(this);

        frombackground = (LinearLayout) findViewById(R.id.fromDatee);
        tobackground = (LinearLayout) findViewById(R.id.toDatee);

        progressDialogHelper = new ProgressDialogHelper(this);

        loadFromDate();
        loadToDate();

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
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

        ((TextView) findViewById(R.id.dateFrom)).setText(formattedDate);

        mFromDateVal = formattedServerDate;
    }

    private void loadToDate() {
        SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = DF.format(c.getTime());
        SimpleDateFormat serverDF = new SimpleDateFormat("yyyy-MM-dd");
        String formattedServerDate = serverDF.format(c.getTime());

        ((TextView) findViewById(R.id.dateTo)).setText(formattedDate);

        mToDateVal = formattedServerDate;
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

            mFromDatePickerDialog = new DatePickerDialog(OnDutyRequestActivity.this, R.style.datePickerTheme, fromdate, currentYear,
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

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                    INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

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

            final DatePickerDialog dpd = new DatePickerDialog(OnDutyRequestActivity.this, R.style.datePickerTheme, todate, currentYear,
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

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                    INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        if (v == btnRequest) {
            SimpleDateFormat serverDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formattedServerDate = serverDF.format(c.getTime());
            callGetStudentInfoService();
        }
    }

    private boolean validateFields() {
        int getDate = 0, getDate1 = 0;
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

        if (!AppValidator.checkNullString(this.edtOnDutyRequestFor.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid reason");
            return false;
        } else if (!AppValidator.checkNullString(this.edtOnDutyRequestDetails.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid reason details");
            return false;
        } else if (getDate1 > 0) {
            AlertDialogHelper.showSimpleAlertDialog(this, "ToDate should not lesser than FromDate");
            return false;
        } else {
            return true;
        }
    }

    private void callGetStudentInfoService() {
        try {

            String reasonFor = "", odDetails = "";

            reasonFor = edtOnDutyRequestFor.getText().toString();
            odDetails = edtOnDutyRequestDetails.getText().toString();

            if (validateFields()) {
                if (CommonUtils.isNetworkAvailable(this)) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(EnsyfiConstants.PARAMS_OD_UESR_TYPE, PreferenceStorage.getUserType(this));
                        jsonObject.put(EnsyfiConstants.PARAMS_OD_UESR_ID, PreferenceStorage.getUserId(this));
                        jsonObject.put(EnsyfiConstants.PARAMS_OD_FOR, reasonFor);
                        jsonObject.put(EnsyfiConstants.PARAMS_OD_FROM_DATE, mFromDateVal);
                        jsonObject.put(EnsyfiConstants.PARAMS_OD_TO_DATE, mToDateVal);
                        jsonObject.put(EnsyfiConstants.PARAMS_OD_NOTES, odDetails);
                        jsonObject.put(EnsyfiConstants.PARAMS_OD_STATUS, "Pending");
                        jsonObject.put(EnsyfiConstants.PARAMS_OD_CREATED_BY, PreferenceStorage.getUserType(this));
                        jsonObject.put(EnsyfiConstants.PARAMS_OD_CREATED_AT, formattedServerDate);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
                    String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(this) + EnsyfiConstants.GET_ON_DUTY_REQUEST;
                    serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
                } else {

                    AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    private boolean validateSignInResponse(JSONObject response) {
        boolean signInSuccess = false;
        if ((response != null)) {
            try {
                String status = response.getString("status");
                String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                Log.d(TAG, "status val" + status + "msg" + msg);

                if ((status != null)) {
                    if (((status.equalsIgnoreCase("activationError")) || (status.equalsIgnoreCase("alreadyRegistered")) ||
                            (status.equalsIgnoreCase("notRegistered")) || (status.equalsIgnoreCase("error")))) {
                        signInSuccess = false;
                        Log.d(TAG, "Show error dialog");
                        AlertDialogHelper.showSimpleAlertDialog(this, msg);

                    } else {
                        signInSuccess = true;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return signInSuccess;
    }

    @Override
    public void onResponse(final JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {
            try {
                String status = response.getString("status");
                String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                Log.d(TAG, "status val" + status + "msg" + msg);
                if ((status != null)) {
                    if (((status.equalsIgnoreCase("success")))) {

                        Log.d(TAG, "Show error dialog");
                        AlertDialogHelper.showSimpleAlertDialog(this, msg);

                        Intent intent = new Intent(this, OnDutyActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(this, error);
    }
}
