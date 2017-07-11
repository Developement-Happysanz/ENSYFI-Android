package com.palprotech.ensyfi.activity.general;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Admin on 10-07-2017.
 */

public class OnDutyRequestActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, View.OnClickListener {

    private static final String TAG = OnDutyRequestActivity.class.getName();
    private EditText edtOnDutyRequestFor, edtOnDutyRequestDetails;
    private TextView dateFrom, dateTo;
    private Button btnRequest;
    Context context;
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

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);

    }

    private static String formatDate(int year, int month, int day) {

            /*Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day);
            Date date = cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mmm-yyyy");
            return sdf.format(date);*/
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

            /*Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day);
            Date date = cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mmm-yyyy");
            return sdf.format(date);*/
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
            dateFrom.setCompoundDrawablesWithIntrinsicBounds( R.drawable.od_from_date_selected, 0, 0, 0);
            dateFrom.setTextColor((Color.parseColor("#663366")));
            final DatePickerDialog.OnDateSetListener fromdate = new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int month, int day) {
                    Log.d(TAG, "From selected");
                    // isdoneclick = true;
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
        }

        if (v == dateTo) {

            tobackground.setBackgroundColor(Color.parseColor("#663366"));
            dateTo.setCompoundDrawablesWithIntrinsicBounds( R.drawable.od_from_date_selected, 0, 0, 0);
            dateTo.setTextColor((Color.parseColor("#663366")));
            final DatePickerDialog.OnDateSetListener todate = new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int month, int day) {
                    // isdoneclick = true;

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
        }

        if (v == btnRequest) {
            SimpleDateFormat serverDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formattedServerDate = serverDF.format(c.getTime());
            callGetStudentInfoService();
        }
    }

    private void callGetStudentInfoService() {
        String reasonFor, odDetails;
        reasonFor = edtOnDutyRequestFor.getText().toString();
        odDetails = edtOnDutyRequestDetails.getText().toString();


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

                    }
                }
//                studentData.saveStudentProfile(getData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(String error) {

    }
}
