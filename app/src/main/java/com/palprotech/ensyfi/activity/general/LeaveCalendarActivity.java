package com.palprotech.ensyfi.activity.general;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.adminmodule.ClassStudentListAdapter;
import com.palprotech.ensyfi.adapter.general.HolidayFragmentAdapter;
import com.palprotech.ensyfi.bean.admin.support.StoreClass;
import com.palprotech.ensyfi.bean.admin.support.StoreSection;
import com.palprotech.ensyfi.bean.admin.viewlist.ClassStudentList;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.viewlist.Students;
import com.palprotech.ensyfi.fragments.AllHolidayListFragment;
import com.palprotech.ensyfi.fragments.UpcomingHolidayListFragment;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

public class LeaveCalendarActivity extends AppCompatActivity implements IServiceListener, DialogClickListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ServiceHelper serviceHelper;
    private Spinner spnClassList, spnSectionList, spnClassSecList;
    private String checkSpinner = "", storeClassId, storeSectionId;
    RelativeLayout adminView, teacherView;
    ViewGroup container;
    LayoutInflater inflater;
    private View rootView;
    SQLiteHelper db;
    Vector<String> vecClassList, vecClassSectionList;
    List<String> lsClassList = new ArrayList<String>();
    ArrayList<Students> myList = new ArrayList<Students>();
    String set3, AM_PM = "0";
    Boolean pS = false;

    Handler mHandler = new Handler();

    private static final String TAG = LeaveCalendarActivity.class.getName();
    private ProgressDialogHelper progressDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday_calendar_tabs);

        adminView = findViewById(R.id.admin_view_holiday);
        teacherView = findViewById(R.id.teacher_view_holiday);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        String userId = PreferenceStorage.getUserType(getApplicationContext());
        if (userId.equals("1")) {
            adminView.setVisibility(View.VISIBLE);
            GetClassData();
            spnClassList = findViewById(R.id.class_list_spinner);
            spnSectionList = findViewById(R.id.section_list_spinner);
            spnClassList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    StoreClass classList = (StoreClass) parent.getSelectedItem();
                    storeClassId = classList.getClassId();
                    GetSectionData();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            spnSectionList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    StoreSection sectionList = (StoreSection) parent.getSelectedItem();
                    storeSectionId = sectionList.getSectionId();
                    Bundle bundle = new Bundle();
                    bundle.putString("class_id", storeClassId);
                    bundle.putString("section_id", storeSectionId);
                    bundle.putString("class_sec_id", "");
                    UpcomingHolidayListFragment fragobj = new UpcomingHolidayListFragment();
                    AllHolidayListFragment fragobj1 = new AllHolidayListFragment();
                    fragobj.setArguments(bundle);
                    fragobj1.setArguments(bundle);
                    pS = true;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    pS = false;
                }
            });
        } else if (userId.equals("2")) {
            db = new SQLiteHelper(getApplicationContext());
            vecClassList = new Vector<String>();
            getClassSectionList();
            teacherView.setVisibility(View.VISIBLE);
            spnClassSecList = (Spinner) findViewById(R.id.class_section_list_spinner);
            spnClassSecList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String classSeactionName = parent.getItemAtPosition(position).toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("class_id", "");
                    bundle.putString("section_id", "");
                    bundle.putString("class_sec_id", classSeactionName);
                    UpcomingHolidayListFragment fragobj = new UpcomingHolidayListFragment();
                    AllHolidayListFragment fragobj1 = new AllHolidayListFragment();
                    fragobj.setArguments(bundle);
                    fragobj1.setArguments(bundle);
                    pS = true;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    pS = false;
                }
            });
        } else {
            teacherView.setVisibility(View.GONE);
            adminView.setVisibility(View.GONE);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText("Upcoming Leaves"));
        tabLayout.addTab(tabLayout.newTab().setText("All Leaves"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        final HolidayFragmentAdapter adapter = new HolidayFragmentAdapter
                (getSupportFragmentManager());

        viewPager.setAdapter(adapter);
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
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    private void GetClassData() {
        checkSpinner = "class";
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID, "1");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_CLASS_LISTS;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);


        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private void GetSectionData() {
        checkSpinner = "section";
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID_LIST, storeClassId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_SECTION_LISTS;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private void getClassSectionList() {

        try {
            Cursor c = db.getTeachersClass();
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        vecClassList.add(c.getString(1));
                        set3 = c.getString(0);
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
//            lsStudent.add("Select");
            lsClassList.addAll(ts);
            db.close();
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item_ns, lsClassList);
//            adptStudent = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, lsStudent);
//            adptStudent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnClassList.setAdapter(dataAdapter3);
            spnClassList.setWillNotDraw(false);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
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
    public void onResponse(final JSONObject response) {

        progressDialogHelper.hideProgressDialog();

        if (validateSignInResponse(response)) {

            try {
                if (checkSpinner.equalsIgnoreCase("class")) {

                    JSONArray getData = response.getJSONArray("data");
                    JSONObject userData = getData.getJSONObject(0);
                    int getLength = getData.length();
                    Log.d(TAG, "userData dictionary" + userData.toString());

                    String classId = "";
                    String className = "";
                    ArrayList<StoreClass> classesList = new ArrayList<>();

                    for (int i = 0; i < getLength; i++) {

                        classId = getData.getJSONObject(i).getString("class_id");
                        className = getData.getJSONObject(i).getString("class_name");

                        classesList.add(new StoreClass(classId, className));
                    }

                    //fill data in spinner
                    ArrayAdapter<StoreClass> adapter = new ArrayAdapter<StoreClass>(getApplicationContext(), R.layout.spinner_item_ns, classesList);
                    spnClassList.setAdapter(adapter);
                } else if (checkSpinner.equalsIgnoreCase("section")) {
                    JSONArray getData = response.getJSONArray("data");
                    JSONObject userData = getData.getJSONObject(0);
                    int getLength = getData.length();
                    Log.d(TAG, "userData dictionary" + userData.toString());

                    String sectionId = "";
                    String sectionclass = "";
                    ArrayList<StoreSection> sectionList = new ArrayList<>();

                    for (int i = 0; i < getLength; i++) {

                        sectionId = getData.getJSONObject(i).getString("sec_id");
                        sectionclass = getData.getJSONObject(i).getString("sec_name");

                        sectionList.add(new StoreSection(sectionId, sectionclass));
                    }

                    //fill data in spinner
                    ArrayAdapter<StoreSection> adapter = new ArrayAdapter<StoreSection>(getApplicationContext(), R.layout.spinner_item_ns, sectionList);
                    spnSectionList.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
    }
}
