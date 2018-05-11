package com.palprotech.ensyfi.activity.teachermodule;

import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.TeacherTimeTableAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.student.viewlist.FeeStatusList;
import com.palprotech.ensyfi.bean.teacher.viewlist.TTDays;
import com.palprotech.ensyfi.bean.teacher.viewlist.TTDaysList;
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
    SQLiteHelper db;
    String [] DayName;
    String [] DayId;
    List<String> list = new ArrayList<String>();
    List<String> list1 = new ArrayList<String>();
    int dayCount = 0;

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
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
                    list.add("" + c.getString(0)) ;
                    list1.add("" + c.getString(1)) ;
                } while (c.moveToNext());
            }
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
