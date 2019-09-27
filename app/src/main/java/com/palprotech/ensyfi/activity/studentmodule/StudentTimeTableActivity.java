package com.palprotech.ensyfi.activity.studentmodule;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.support.SaveTeacherData;
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

/**
 * Created by Narendar on 18/04/17.
 */

public class StudentTimeTableActivity extends AppCompatActivity implements IServiceListener, DialogClickListener {

    private static final String TAG = StudentTimeTableActivity.class.getName();
    LinearLayout layout_all;
    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    private SaveTeacherData teacherData;
    SQLiteHelper db;
    String ClassName, SectionName, SubjectName, fromTime, toTime;
    String ClassId = "";
    String SubjectId = "";
    String PeriodId = "";
    String isBreak = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        teacherData = new SaveTeacherData(this);
        db = new SQLiteHelper(getApplicationContext());
        progressDialogHelper = new ProgressDialogHelper(this);
        GetTimeTableDataFromServer();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView examMarks = (ImageView) findViewById(R.id.viewExamMarks);
        examMarks.setVisibility(View.GONE);
    }

    private void GetTimeTableDataFromServer() {
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID, PreferenceStorage.getStudentClassIdPreference(getApplicationContext()));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_TIME_TABLE_API;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private void GetTimeTableData() {

        try {
            layout_all = (LinearLayout) findViewById(R.id.layout_timetable);
            TableLayout layout = new TableLayout(this);
            layout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            layout_all.setScrollbarFadingEnabled(false);
            layout.setPadding(0, 50, 0, 50);

            TableLayout.LayoutParams rowLp = new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            TableRow.LayoutParams cellLp = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            cellLp.setMargins(2, 2, 2, 2);

            int i = 0;
            int r = 0;
            int col = 0;
            for (int f = 0; f <= 6; f++) {

                TableRow tr = new TableRow(this);

                tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                tr.setBackgroundColor(Color.BLACK);
                tr.setPadding(0, 0, 0, 1);

                TableRow.LayoutParams llp = new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                llp.setMargins(1, 1, 1, 1);//2px right-margin

                for (int c1 = 0; c1 <= 8; c1++) {

                    LinearLayout cell = new LinearLayout(this);
                    cell.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                    TextView b = new TextView(this);

                    String name = "";

                    if (((r == 0) && (col == 0)) || ((r == 0) && (col == 1)) || ((r == 0) && (col == 2))
                            || ((r == 0) && (col == 3)) || ((r == 0) && (col == 4)) || ((r == 0) && (col == 5))
                            || ((r == 0) && (col == 6)) || ((r == 0) && (col == 7)) || ((r == 0) && (col == 8))
                            || ((r == 1) && (col == 9)) || ((r == 2) && (col == 18)) || ((r == 3) && (col == 27))
                            || ((r == 4) && (col == 36)) || ((r == 5) && (col == 45)) || ((r == 6) && (col == 54))) {
                        b.setBackgroundColor(Color.parseColor("#708090"));
                        if ((r == 0) && (col == 0)) {
                            b.setTextColor(Color.parseColor("#FFFFFF"));
                            name = "Period\n&\nDay";
                        }
                        if ((r == 0) && (col == 1)) {
                            name = "" + 1;
                        }
                        if ((r == 0) && (col == 2)) {
                            name = "" + 2;
                        }
                        if ((r == 0) && (col == 3)) {
                            name = "" + 3;
                        }
                        if ((r == 0) && (col == 4)) {
                            name = "" + 4;
                        }
                        if ((r == 0) && (col == 5)) {
                            name = "" + 5;
                        }
                        if ((r == 0) && (col == 6)) {
                            name = "" + 6;
                        }
                        if ((r == 0) && (col == 7)) {
                            name = "" + 7;
                        }
                        if ((r == 0) && (col == 8)) {
                            name = "" + 8;
                        }
                        if ((r == 1) && (col == 9)) {
                            name = "Mon";
                        }
                        if ((r == 2) && (col == 18)) {
                            name = "Tues";
                        }
                        if ((r == 3) && (col == 27)) {
                            name = "Wed";
                        }
                        if ((r == 4) && (col == 36)) {
                            name = "Thurs";
                        }
                        if ((r == 5) && (col == 45)) {
                            name = "Fri";
                        }
                        if ((r == 6) && (col == 54)) {
                            name = "Sat";
                        }
                    } else {

                        String fValue = String.valueOf(f);
                        String c1Value = String.valueOf(c1);
                        Cursor c = db.getTeacherTimeTableValue(fValue, c1Value);
                        if (c.getCount() > 0) {
                            if (c.moveToFirst()) {
                                do {
                                    isBreak = c.getString(0);
                                    toTime = c.getString(1);
                                    fromTime = c.getString(4);
                                    ClassName = c.getString(5);
                                    SectionName = c.getString(3);
                                    SubjectName = c.getString(2);
                                    ClassId = c.getString(6);
                                    SubjectId = c.getString(7);
                                    PeriodId = c.getString(8);
                                } while (c.moveToNext());
                            }
                            name = SubjectName;
                            /*name1 = "ClassName:" + ClassName +
                                    "-" + SectionName + ",ClassId:" + ClassId + ",SubjectName:" + SubjectName +
                                    ",SubjectId:" + SubjectId + ",PeriodId:" + PeriodId;*/
//                            name1 = ClassName + "-" + SectionName + "," + ClassId + "," + SubjectName + "," + SubjectId + "," + PeriodId;
                        } else {
                            name = "";
//                            name1 = "";
                        }
                    }
                    db.close();

                    cell.setBackgroundColor(Color.WHITE);//argb(255,104,53,142)

                    b.setText(name);
                    b.setTextSize(8.0f);
                    b.setTypeface(null, Typeface.BOLD);
                    b.setAllCaps(true);
                    b.setTextColor(Color.parseColor("#FF68358E"));
                    b.setGravity(Gravity.CENTER);

                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub

                        }
                    });
                    b.setPressed(true);

                    b.setHeight(100);
                    b.setWidth(75);
                    b.setPadding(1, 0, 2, 0);
                    cell.addView(b);
                    cell.setLayoutParams(llp);//2px border on the right for the cell

                    tr.addView(cell, cellLp);
                    i++;
                    col++;
                } // for
                layout.addView(tr, rowLp);
                r++;
            }
            // for

            layout_all.addView(layout);

        } catch (Exception ex) {
            ex.printStackTrace();
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
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    @Override
    public void onResponse(JSONObject response) {

        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {

            try {
                JSONArray getTimeTable = response.getJSONArray("timeTable");
                teacherData.saveTeacherTimeTable(getTimeTable);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            GetTimeTableData();

        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(this, error);
    }
}
