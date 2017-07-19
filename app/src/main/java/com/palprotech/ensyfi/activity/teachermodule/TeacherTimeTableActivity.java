package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.studentmodule.StudentTimeTableActivity;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Admin on 04-07-2017.
 */

public class TeacherTimeTableActivity extends AppCompatActivity implements DialogClickListener {

    private static final String TAG = TeacherTimeTableActivity.class.getName();
    LinearLayout layout_all;
    private ProgressDialogHelper progressDialogHelper;
    SQLiteHelper db;
    Vector<String> vecTableId, vecClassName, vecSectionName, vecSubjectName, vecDay, vecPeriod;
    List<String> lsTeacherTimeTable = new ArrayList<String>();
    ArrayAdapter<String> adapterTeacherTimeTable;
    String ClassName, SectionName, SubjectName, TableId;
    String ClassId = "";
    String SubjectId = "";
    String PeriodId = "";
    String SubjectNameVal = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        progressDialogHelper = new ProgressDialogHelper(this);
        db = new SQLiteHelper(getApplicationContext());
        vecTableId = new Vector<String>();
        vecClassName = new Vector<String>();
        vecSectionName = new Vector<String>();
        vecSubjectName = new Vector<String>();
        vecDay = new Vector<String>();
        vecPeriod = new Vector<String>();
        GetTimeTableData();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView viewExamMark = (ImageView) findViewById(R.id.viewExamMarks);
        viewExamMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TimeTableReview.class);
//            intent.putExtra("id", id);
                startActivity(intent);

            }
        });
    }

    private void GetTimeTableData() {
        try {

//            JSONArray getData = response.getJSONArray("timeTable");
//            JSONObject userData = getData.getJSONObject(0);
            //


//            int getLength = getData.length();
//            String subjectName = null;
//            Log.d(TAG, "userData dictionary" + userData.toString());
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
            int i = 1;
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
                    final TextView set = new TextView(this);
                    String name = "";
                    String name1 = "";

                    if (((r == 1) && (col == 1)) || ((r == 1) && (col == 2)) || ((r == 1) && (col == 3)) || ((r == 1) && (col == 4))
                            || ((r == 1) && (col == 5)) || ((r == 1) && (col == 6)) || ((r == 1) && (col == 7))
                            || ((r == 1) && (col == 8))
                            || ((r == 1) && (col == 9)) || ((r == 2) && (col == 10)) || ((r == 3) && (col == 19))
                            || ((r == 4) && (col == 28))
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

                        String fValue = String.valueOf(f);
                        String c1Value = String.valueOf(c1);
                        Cursor c = db.getTeacherTimeTableValue(fValue, c1Value);
                        if (c.getCount() > 0) {
                            if (c.moveToFirst()) {
                                do {
                                    ClassName = c.getString(0);
                                    SectionName = c.getString(1);
                                    SubjectName = c.getString(2);
                                    ClassId = c.getString(3);
                                    SubjectId = c.getString(4);
                                    PeriodId = c.getString(5);
                                } while (c.moveToNext());
                            }
                            name = ClassName + "-" + SectionName + "\n" + SubjectName;
                            /*name1 = "ClassName:" + ClassName +
                                    "-" + SectionName + ",ClassId:" + ClassId + ",SubjectName:" + SubjectName +
                                    ",SubjectId:" + SubjectId + ",PeriodId:" + PeriodId;*/
                            name1 = ClassName + "-" + SectionName + "," + ClassId + "," + SubjectName + "," + SubjectId + "," + PeriodId;
                        } else {
                            name = "";
                            name1 = "";
                        }
                    }
                    db.close();

                    cell.setBackgroundColor(Color.WHITE);//argb(255,104,53,142)

                    set.setText(name1);
                    b.setText(name);
                    b.setTextSize(13.0f);
                    set.setTextSize(13.0f);
                    b.setTypeface(null, Typeface.BOLD);
                    set.setTypeface(null, Typeface.BOLD);
                    b.setAllCaps(true);
                    set.setAllCaps(true);
                    b.setTextColor(Color.parseColor("#FF68358E"));
                    b.setGravity(Gravity.CENTER);
                    set.setGravity(Gravity.CENTER);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
//                            Toast.makeText(getApplicationContext(), set.getText().toString(),
//                                    Toast.LENGTH_LONG).show();
                            Intent navigationIntent = new Intent(getApplicationContext(), TimeTableReviewAddActivity.class);
                            navigationIntent.putExtra("timeTableValue", set.getText().toString());
                            startActivity(navigationIntent);
                        }
                    });
                    b.setPressed(true);
                    b.setHeight(160);
                    set.setHeight(0);
                    b.setWidth(160);
                    set.setWidth(0);
                    b.setPadding(1, 0, 2, 0);
                    set.setPadding(0, 0, 0, 0);
                    set.setVisibility(View.INVISIBLE);
                    cell.addView(b);
                    cell.addView(set);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}
