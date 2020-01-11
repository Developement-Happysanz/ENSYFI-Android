package com.palprotech.ensyfi.activity.studentmodule;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.studentmodule.StudentTimeTableAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.viewlist.TTDays;
import com.palprotech.ensyfi.bean.teacher.viewlist.TTDaysList;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.util.Log.d;

public class StudentTimeTableAcitivityNewnew extends AppCompatActivity implements IServiceListener, DialogClickListener, AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = StudentTimeTableAcitivityNewnew.class.getName();
    private ServiceHelper serviceHelper;
    private ProgressDialogHelper progressDialogHelper;
    TabLayout tab;
    ViewPager viewPager;
    List<String> list = new ArrayList<String>();
    List<String> list1 = new ArrayList<String>();
    int dayCount = 0;
    ArrayList<TTDays> dayArrayList = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_table_newnew);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        findViewById(R.id.back_res).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tab = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        loadCat();
    }

    private void loadCat() {
        JSONObject jsonObject = new JSONObject();
        String id = "";
        id = PreferenceStorage.getStudentClassIdPreference(this);
        try {
            jsonObject.put(EnsyfiConstants.CT_HW_CLASS_ID, id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
        String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_TIME_TABLE_DAYS_API;
        serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    private boolean validateResponse(JSONObject response) {
        boolean signInSuccess = false;
        if ((response != null)) {
            try {
                String status = response.getString("status");
                String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                d(TAG, "status val" + status + "msg" + msg);

                if ((status != null)) {
                    if (((status.equalsIgnoreCase("activationError")) || (status.equalsIgnoreCase("alreadyRegistered")) ||
                            (status.equalsIgnoreCase("notRegistered")) || (status.equalsIgnoreCase("error")))) {
                        signInSuccess = false;
                        d(TAG, "Show error dialog");

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
        if (validateResponse(response)) {
            try {
                JSONArray getData = response.getJSONArray("timetableDays");
                Gson gson = new Gson();
                TTDaysList subCategoryList = gson.fromJson(response.toString(), TTDaysList.class);
                if (subCategoryList.getTTDays() != null && subCategoryList.getTTDays().size() > 0) {
                    this.dayArrayList.addAll(subCategoryList.getTTDays());
//                    updateListAdapter(subCategoryList.getCategoryArrayList());
                } else {
                    if (dayArrayList != null) {
                        dayArrayList.clear();
//                        updateListAdapter(subCategoryList.getCategoryArrayList());
                    }
                }
                initialiseTabs(getData);
            } catch (JSONException e) {
            e.printStackTrace();
        }
        }
    }

    @Override
    public void onError(String error) {

    }

    private void initialiseTabs(JSONArray subCategory) {
//        startTimer();
        if (PreferenceStorage.getUserType(this).equalsIgnoreCase("2") ||
                PreferenceStorage.getUserType(this).equalsIgnoreCase("1")) {
            for (int i = 0; i < dayCount; i++) {
                tab.addTab(tab.newTab().setText(list1.get(i)));
            }
        } else {
            for (int k = 0; k < subCategory.length(); k++) {
                try {
                    tab.addTab(tab.newTab().setText(String.valueOf(subCategory.getJSONObject(k).get("list_day"))));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        StudentTimeTableAdapter adapter = new StudentTimeTableAdapter
                (getSupportFragmentManager(), tab.getTabCount(), dayArrayList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                viewPager.performClick();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
//        tab.removeOnTabSelectedListener(TabLayout.OnTabSelectedListener);
//Bonus Code : If your tab layout has more than 2 tabs then tab will scroll other wise they will take whole width of the screen
        if (tab.getTabCount() <= 2) {
            tab.setTabMode(TabLayout.
                    MODE_FIXED);
        } else {
            tab.setTabMode(TabLayout.
                    MODE_SCROLLABLE);
        }
    }
}
