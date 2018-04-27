package com.palprotech.ensyfi.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.general.AllHolidayListAdapter;
import com.palprotech.ensyfi.bean.admin.support.StoreClass;
import com.palprotech.ensyfi.bean.admin.support.StoreSection;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.general.viewlist.AllHoliday;
import com.palprotech.ensyfi.bean.general.viewlist.AllHolidayList;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
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

public class AllHolidayListFragment extends Fragment implements AdapterView.OnItemClickListener, IServiceListener {

    private static final String TAG = AllHolidayListFragment.class.getName();
    private View rootView;
    protected ListView loadMoreListView;
    protected AllHolidayListAdapter allHolidayListAdapter;
    protected ArrayList<AllHoliday> allHolidayArrayList;
    protected ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    protected boolean isLoadingForFirstTime = true;
    String classId = "", sectionId = "", classSectionId = "", userType = "";
    int pageNumber = 0, totalCount = 0;

    private Spinner spnClassList, spnSectionList, spnClassSecList;
    RelativeLayout adminView, teacherView;
    private String checkSpinner = "", storeClassId, storeSectionId;
    SQLiteHelper db;
    Vector<String> vecClassList, vecClassSectionList;
    List<String> lsClassList = new ArrayList<String>();
    String set3, AM_PM = "0";

    public AllHolidayListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_all_holiday, container, false);

        userType = PreferenceStorage.getUserType(getActivity());
        if (userType.equals("1")||userType.equals("2")){
            {
                classId = "";
                sectionId = "";
                classSectionId = "";
            }
        } else {
            classId = "";
            sectionId = "";
            classSectionId = PreferenceStorage.getStudentClassIdPreference(getActivity());
        }
        initializeEventHelpers();
        initializeViews();
        return rootView;

    }

    protected void initializeViews() {
        Log.d(TAG, "initialize pull to refresh view");
        loadMoreListView = (ListView) rootView.findViewById(R.id.listView_holidays);
        loadMoreListView.setOnItemClickListener(this);
        allHolidayArrayList = new ArrayList<>();
        adminView = rootView.findViewById(R.id.admin_view_holiday);
        teacherView = rootView.findViewById(R.id.teacher_view_holiday);
        String userId = PreferenceStorage.getUserType(getActivity());
        if (userId.equals("1")) {
            adminView.setVisibility(View.VISIBLE);
            GetClassData();
            spnClassList = rootView.findViewById(R.id.class_list_spinner);
            spnSectionList = rootView.findViewById(R.id.section_list_spinner);
            spnClassList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    StoreClass classList = (StoreClass) parent.getSelectedItem();
                    storeClassId = classList.getClassId();
                    classId = storeClassId;
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
                    sectionId = storeSectionId;
                    getHolsList();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } else if (userId.equals("2")) {
            db = new SQLiteHelper(getActivity());
            vecClassList = new Vector<String>();
            getClassSectionList();
            teacherView.setVisibility(View.VISIBLE);
            spnClassSecList = (Spinner) rootView.findViewById(R.id.class_section_list_spinner);
            spnClassSecList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String classSeactionName = parent.getItemAtPosition(position).toString();
                    classSectionId = classSeactionName;
                    getHolsList();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } else {
            teacherView.setVisibility(View.GONE);
            adminView.setVisibility(View.GONE);
            getHolsList();
        }
    }

    protected void initializeEventHelpers() {
        serviceHelper = new ServiceHelper(getActivity());
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(getActivity());
    }

    public void getHolsList() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(EnsyfiConstants.PARAMS_HDAY_USER_TYPE, PreferenceStorage.getUserType(getActivity()));
            jsonObject.put(EnsyfiConstants.PARAMS_HDAY_CLASS_ID, classId);
            jsonObject.put(EnsyfiConstants.PARAMS_HDAY_SEC_ID, sectionId);
            jsonObject.put(EnsyfiConstants.PARAMS_HDAY_CLASS_SEC_ID, classSectionId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
        String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getActivity()) + EnsyfiConstants.GET_ALL_HOLIDAY_API;
        serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
    }

    private void GetClassData() {
        checkSpinner = "class";
        if (CommonUtils.isNetworkAvailable(getActivity())) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID, "1");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getActivity()) + EnsyfiConstants.GET_CLASS_LISTS;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);


        } else {
            AlertDialogHelper.showSimpleAlertDialog(getActivity(), "No Network connection");
        }
    }

    private void GetSectionData() {
        checkSpinner = "section";
        if (CommonUtils.isNetworkAvailable(getActivity())) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID_LIST, storeClassId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getActivity()) + EnsyfiConstants.GET_SECTION_LISTS;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

        } else {
            AlertDialogHelper.showSimpleAlertDialog(getActivity(), "No Network connection");
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
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_ns, lsClassList);
//            adptStudent = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, lsStudent);
//            adptStudent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnClassList.setAdapter(dataAdapter3);
            spnClassList.setWillNotDraw(false);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
                        AlertDialogHelper.showSimpleAlertDialog(getActivity(), msg);
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
    public void onResponse(JSONObject response) {
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
                    ArrayAdapter<StoreClass> adapter = new ArrayAdapter<StoreClass>(getActivity(), R.layout.spinner_item_ns, classesList);
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
                    ArrayAdapter<StoreSection> adapter = new ArrayAdapter<StoreSection>(getActivity(), R.layout.spinner_item_ns, sectionList);
                    spnSectionList.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "Error while sign In");
        }

        LoadListView(response);
    }

    private void LoadListView(JSONObject response) {
        progressDialogHelper.hideProgressDialog();
//        loadMoreListView.onLoadMoreComplete();
        Gson gson = new Gson();
        AllHolidayList allHolidayList = gson.fromJson(response.toString(), AllHolidayList.class);
        if (allHolidayList != null) {
            Log.d(TAG, "fetched all event list count" + allHolidayList.getCount());
        }
//        updateListAdapter(eventsList.getEvents());
        int totalNearbyCount = 0;
        if (allHolidayList.getAllHolidays() != null && allHolidayList.getAllHolidays().size() > 0) {


            isLoadingForFirstTime = false;
            totalCount = allHolidayList.getCount();
            updateListAdapter(allHolidayList.getAllHolidays());
        }
    }
    protected void updateListAdapter(ArrayList<AllHoliday> allHolidayArrayList) {
        this.allHolidayArrayList.addAll(allHolidayArrayList);
       /* if (mNoEventsFound != null)
            mNoEventsFound.setVisibility(View.GONE);*/

        if (allHolidayListAdapter == null) {
            allHolidayListAdapter = new AllHolidayListAdapter(getActivity(), this.allHolidayArrayList);
            loadMoreListView.setAdapter(allHolidayListAdapter);
        } else {
            allHolidayListAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(getActivity(), error);

    }
}