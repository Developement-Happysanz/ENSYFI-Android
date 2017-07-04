package com.palprotech.ensyfi.activity.teachermodule;

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
    Vector<String> vecTableId, vecClassName, vecSectionName, vecSubjectName;
    List<String> lsTeacherTimeTable = new ArrayList<String>();
    ArrayAdapter<String> adapterTeacherTimeTable;
    String ClassName, SectionName, SubjectName, TableId;

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
        try {

            Cursor c = db.getTeacherTimeTable();
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        vecTableId.add(c.getString(0));
                        vecClassName.add(c.getString(9));
                        vecSectionName.add(c.getString(8));
                        vecSubjectName.add(c.getString(3));
                    } while (c.moveToNext());
                }
            }
            db.close();

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
            layout.setPadding(0, 80, 0, 80);

            TableLayout.LayoutParams rowLp = new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            TableRow.LayoutParams cellLp = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            cellLp.setMargins(2, 2, 2, 2);
            int i = 0;
            for (int f = 0; f <= 4; f++) {

                TableRow tr = new TableRow(this);

                tr.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                tr.setBackgroundColor(Color.BLACK);
                tr.setPadding(0, 0, 0, 1);

                TableRow.LayoutParams llp = new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                llp.setMargins(1, 1, 1, 1);//2px right-margin

                for (int c1 = 1; c1 <= 8; c1++) {

                    LinearLayout cell = new LinearLayout(this);
                    cell.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                    TextView b = new TextView(this);
                    final String name;
                    if(i>vecClassName.size()){
                        name = "ok";
                    } else {
                        name = vecClassName.get(i) + "/" + vecSectionName.get(i) ;
//                            + "/" + vecClassName.get(i) + "/" + vecSectionName.get(i) + "/" + vecSubjectName.get(i) + "";
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

                    b.setHeight(150);
                    b.setWidth(150);
                    b.setPadding(1, 0, 2, 0);
                    cell.addView(b);
                    cell.setLayoutParams(llp);//2px border on the right for the cell

                    tr.addView(cell, cellLp);
                    i++;
                } // for
                layout.addView(tr, rowLp);
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
