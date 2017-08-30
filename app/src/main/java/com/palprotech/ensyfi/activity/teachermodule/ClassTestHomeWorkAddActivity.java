package com.palprotech.ensyfi.activity.teachermodule;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.IdRes;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.utils.AppValidator;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Created by Admin on 13-07-2017.
 */

public class ClassTestHomeWorkAddActivity extends AppCompatActivity implements DialogClickListener, View.OnClickListener {

    private Spinner spnClassList, spnSubjectList;
    private static final String TAG = "CTHWTeacherView";
    protected ProgressDialogHelper progressDialogHelper;
    List<String> lsClassList = new ArrayList<String>();
    SQLiteHelper db;
    Vector<String> vecClassList;
    EditText edtSetTitle, edtDescription;
    Button btnSubmit;
    private TextView dateFrom, dateTo, txthwctsubject;
    private String mFromDateVal = null;
    private String mToDateVal = null;
    final Calendar c = Calendar.getInstance();
    LinearLayout frombackground, tobackground;
    String formattedServerDate;
    private boolean isDoneClick = false;
    String singleDate = "", getClassSectionId, classSection, ClassTestOrHomeWork = "HT", subjectName = "", getClassSubjectId;
    DatePickerDialog mFromDatePickerDialog = null;
    private RadioGroup radioClassTestHomeWork;
    private LinearLayout checkTestTitle, checkDueTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_test_homework_add);

        db = new SQLiteHelper(getApplicationContext());
        vecClassList = new Vector<String>();

        progressDialogHelper = new ProgressDialogHelper(this);

        spnClassList = (Spinner) findViewById(R.id.class_list_spinner);
        spnSubjectList = (Spinner) findViewById(R.id.subject_list_spinner);

        edtSetTitle = (EditText) findViewById(R.id.edtSetTitle);
        edtDescription = (EditText) findViewById(R.id.edtDescription);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        dateFrom = (TextView) findViewById(R.id.dateFrom);
        dateFrom.setOnClickListener(this);

        dateTo = (TextView) findViewById(R.id.dateTo);
        dateTo.setOnClickListener(this);

        txthwctsubject = (TextView) findViewById(R.id.txthwctsubject);

        radioClassTestHomeWork = (RadioGroup) findViewById(R.id.radioClassTestHomeWorkView);

        frombackground = (LinearLayout) findViewById(R.id.fromDatee);
        tobackground = (LinearLayout) findViewById(R.id.toDatee);

        checkTestTitle = (LinearLayout) findViewById(R.id.checkTestTitle);

        checkDueTitle = (LinearLayout) findViewById(R.id.checkDueTitle);

        checkTestTitle.setVisibility(View.VISIBLE);
        checkDueTitle.setVisibility(View.GONE);

        getClassList();

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        radioClassTestHomeWork.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId) {
                    case R.id.radioClassTest:
                        ClassTestOrHomeWork = "HT";
                        checkTestTitle.setVisibility(View.VISIBLE);
                        checkDueTitle.setVisibility(View.GONE);
                        getClassId(classSection);
                        break;

                    case R.id.radioHomeWork:
                        ClassTestOrHomeWork = "HW";
                        checkTestTitle.setVisibility(View.GONE);
                        checkDueTitle.setVisibility(View.VISIBLE);
                        getClassId(classSection);
                        break;
                }
            }
        });

        spnClassList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classSection = parent.getItemAtPosition(position).toString();
                getClassId(classSection);
                getSubjectList(getClassSectionId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnSubjectList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subjectName = parent.getItemAtPosition(position).toString();
                txthwctsubject.setText(subjectName);
                getSubjectId(subjectName, getClassSectionId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadFromDate();
        loadToDate();
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

    private void saveClassTestHomeWork() {

        SimpleDateFormat serverDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedServerDate = serverDF.format(c.getTime());
        if (validateFields()) {
            String serverHomeWorkId = "";
            String yearId = PreferenceStorage.getAcademicYearId(this);
            String classId = getClassSectionId;
            String teacherId = PreferenceStorage.getTeacherId(this);
            String homeWorkType = ClassTestOrHomeWork;
            String subjectId = getClassSubjectId;
            String SubjectName = subjectName;
            String title = edtSetTitle.getText().toString();
            String testDate = mFromDateVal;
            String dueDate = mToDateVal;
            String homeWorkDetails = edtDescription.getText().toString();
            String status = "Active";
            String markStatus = "0";
            String createdBy = PreferenceStorage.getUserId(this);
            String createdAt = formattedServerDate;
            String updatedBy = PreferenceStorage.getUserId(this);
            String updatedAt = formattedServerDate;
            String syncStatus = "NS";

            long x = db.homework_class_test_insert(serverHomeWorkId, yearId, classId, teacherId, homeWorkType, subjectId,
                    SubjectName, title, testDate, dueDate, homeWorkDetails, status, markStatus,
                    createdBy, createdAt, updatedBy, updatedAt, syncStatus);

            System.out.println("Stored Id : " + x);

            Intent navigationIntent = new Intent(getApplicationContext(), ClassTestHomeWorkTeacherViewActivity.class);
            startActivity(navigationIntent);
            finish();
        }
    }

    private boolean validateFields() {

        if (!AppValidator.checkNullString(this.edtSetTitle.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid title");
            return false;
        } else if (!AppValidator.checkNullString(this.edtDescription.getText().toString().trim())) {
            AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid details");
            return false;
        } else {
            return true;
        }
    }

    private void getClassId(String classSectionName) {

        try {
            Cursor c = db.getClassId(classSectionName);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        getClassSectionId = c.getString(0);
                    } while (c.moveToNext());
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void getSubjectId(String subjectName, String classId) {

        try {
            Cursor c = db.getSubjectId(subjectName, classId);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        getClassSubjectId = c.getString(6);
                    } while (c.moveToNext());
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void getClassList() {

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

    private void getSubjectList(String classId) {
        Vector<String> vecSubjectList = new Vector<String>();
        List<String> lsSubjectList = new ArrayList<String>();
        try {
            Cursor c = db.getHandlingSubjectList(classId);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        vecSubjectList.add(c.getString(5));
                    } while (c.moveToNext());
                }
            }
            for (int i = 0; i < vecSubjectList.size(); i++) {
                lsSubjectList.add(vecSubjectList.get(i));
            }
            HashSet hs = new HashSet();
            TreeSet ts = new TreeSet(hs);
            ts.addAll(lsSubjectList);
            lsSubjectList.clear();
            lsSubjectList.addAll(ts);
            db.close();
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item_ns, lsSubjectList);

            spnSubjectList.setAdapter(dataAdapter3);
            spnSubjectList.setWillNotDraw(false);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error getting class list lookup", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    @Override
    public void onClick(View v) {
        if (v == dateFrom) {
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

            mFromDatePickerDialog = new DatePickerDialog(ClassTestHomeWorkAddActivity.this, R.style.datePickerTheme, fromdate, currentYear,
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

            final DatePickerDialog dpd = new DatePickerDialog(ClassTestHomeWorkAddActivity.this, R.style.datePickerTheme, todate, currentYear,
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
        if (v == btnSubmit) {
            saveClassTestHomeWork();
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

}
