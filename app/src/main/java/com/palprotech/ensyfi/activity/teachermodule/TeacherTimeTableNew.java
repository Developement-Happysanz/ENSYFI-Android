package com.palprotech.ensyfi.activity.teachermodule;

import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.TeacherTimeTableAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.student.viewlist.FeeStatusList;
import com.palprotech.ensyfi.bean.teacher.viewlist.TTDays;
import com.palprotech.ensyfi.bean.teacher.viewlist.TTDaysList;
import com.palprotech.ensyfi.bean.teacher.viewlist.TimeTable;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TeacherTimeTableNew extends AppCompatActivity implements DialogClickListener, IServiceListener, View.OnClickListener {

    private static final String TAG = TeacherTimeTableNew.class.getName();
    ServiceHelper serviceHelper;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;
    ArrayList<TTDays> dayDetailsArrayList;
    ArrayList<TimeTable> ttArrayList = new ArrayList<>();
    SQLiteHelper db;
    String[] DayName;
    String[] DayId;
    List<String> list = new ArrayList<String>();
    List<String> list1 = new ArrayList<String>();
    int dayCount = 0, currentTab = 0;
    int periodsCount = 0;
    String currentday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_table_new);
        db = new SQLiteHelper(getApplicationContext());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        dayDetailsArrayList = new ArrayList<>();
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
//        getTimeTableDays();
        getDaysfromDB();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (int i = 0; i < dayCount; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(list1.get(i)));
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        final TeacherTimeTableAdapter adapter = new TeacherTimeTableAdapter
                (this, getSupportFragmentManager(), tabLayout);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        periodsCount = db.getProfilesCount(String.valueOf(1));
        loadTimeTable(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                currentTab = tab.getPosition();
                periodsCount = db.getProfilesCount(String.valueOf(currentTab + 1));
                currentday = tab.getText().toString();
                loadTimeTable(currentTab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                currentTab = tab.getPosition();
                periodsCount = db.getProfilesCount(String.valueOf(currentTab + 1));
                tab.getText();
                currentday = tab.getText().toString();
                loadTimeTable(currentTab);
            }
        });
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

//    private void getTimeTableDays() {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID, "1");
////            jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID, PreferenceStorage.getTeacherId(this));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
//        String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_TIME_TABLE_DAYS_API;
//        serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
//    }

    private void getDaysfromDB() {
        Cursor c = db.selectTimeTableDays();
        dayCount = c.getCount();
        if (c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    list.add("" + c.getString(0));
                    list1.add("" + c.getString(1));
                } while (c.moveToNext());
            }
        }
    }

    private void loadTimeTable(int i) {
        ttArrayList.clear();
        try {
            String f1Value = list1.get(i);
            String dayId = db.getTimeTableDayId(f1Value);
            for (int abc = 0; abc < periodsCount; abc++) {
                String c1Value = String.valueOf(abc);
                Cursor c = db.getTeacherTimeTableValueNew(dayId);
                dayCount = c.getCount();
                if (c.getCount() > 0) {
                    if (c.moveToFirst()) {
                        do {
                            TimeTable lde = new TimeTable();
                            lde.setClassName(c.getString(0));
                            lde.setSecName(c.getString(1));
                            lde.setSubjectName(c.getString(2));
                            lde.setClassId(c.getString(3));
                            lde.setSubjectId(c.getString(4));
                            lde.setName(c.getString(5));
                            lde.setSubjectName(c.getString(6));
                            lde.setFromTime(c.getString(7));
                            lde.setToTime(c.getString(8));
                            lde.setIsBreak(c.getString(9));

                            // Add this object into the ArrayList myList
                            ttArrayList.add(lde);
                        } while (c.moveToNext());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_LONG).show();
                }
                db.close();
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
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
    public void onResponse(JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {

        }
    }

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(this, error);
    }

    @Override
    public void onClick(View v) {

    }
}
