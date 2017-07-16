package com.palprotech.ensyfi.activity.studentmodule;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        GetTimeTableData();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetTimeTableData() {
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID, PreferenceStorage.getStudentClassIdPreference(getApplicationContext()));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_STUDENT_TIME_TABLE_API;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);


        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
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
                JSONArray getData = response.getJSONArray("timeTable");
                JSONObject userData = getData.getJSONObject(0);
                int getLength = getData.length();
                String subjectName = null;
                Log.d(TAG, "userData dictionary" + userData.toString());
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
                int r = 1;
                int col = 1;
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

                        if (((r == 1) && (col == 1)) || ((r == 1) && (col == 2)) || ((r == 1) && (col == 3)) || ((r == 1) && (col == 4))
                                || ((r == 1) && (col == 5)) || ((r == 1) && (col == 6)) || ((r == 1) && (col == 7)) || ((r == 1) && (col == 8))
                                || ((r == 1) && (col == 9)) || ((r == 2) && (col == 10)) || ((r == 3) && (col == 19)) || ((r == 4) && (col == 28))
                                || ((r == 5) && (col == 37)) || ((r == 6) && (col == 46)) || ((r == 7) && (col == 55))) {
                            b.setBackgroundColor(Color.parseColor("#708090"));
                            if ((r == 1) && (col == 1)) {
                                b.setTextColor(Color.parseColor("#FFFFFF"));
                                name = "Period\n&\nDay";
                            }
                            if ((r == 1) && (col == 2)) {
                                name = "" + 1;
                            }
                            if ((r == 1) && (col == 3)) {
                                name = "" + 2;
                            }
                            if ((r == 1) && (col == 4)) {
                                name = "" + 3;
                            }
                            if ((r == 1) && (col == 5)) {
                                name = "" + 4;
                            }
                            if ((r == 1) && (col == 6)) {
                                name = "" + 5;
                            }
                            if ((r == 1) && (col == 7)) {
                                name = "" + 6;
                            }
                            if ((r == 1) && (col == 8)) {
                                name = "" + 7;
                            }
                            if ((r == 1) && (col == 9)) {
                                name = "" + 8;
                            }
                            if ((r == 2) && (col == 10)) {
                                name = "Monday";
                            }
                            if ((r == 3) && (col == 19)) {
                                name = "Tuesday";
                            }
                            if ((r == 4) && (col == 28)) {
                                name = "Wednesday";
                            }
                            if ((r == 5) && (col == 37)) {
                                name = "Thursday";
                            }
                            if ((r == 6) && (col == 46)) {
                                name = "Friday";
                            }
                            if ((r == 7) && (col == 55)) {
                                name = "Saturday";
                            }
//                        b.setTextColor(Color.parseColor("#ffff00"));
                        } else {

                            name = getData.getJSONObject(i).getString("subject_name") + "";
                        }

                        cell.setBackgroundColor(Color.WHITE);//argb(255,104,53,142)

                        b.setText(name);
                        b.setTextSize(13.0f);
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

                        b.setHeight(160);
                        b.setWidth(160);
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

//                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    @Override
    public void onError(String error) {

    }
}
