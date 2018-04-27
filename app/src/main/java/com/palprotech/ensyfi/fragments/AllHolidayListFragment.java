package com.palprotech.ensyfi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.general.AllHolidayListAdapter;
import com.palprotech.ensyfi.bean.general.viewlist.AllHoliday;
import com.palprotech.ensyfi.bean.general.viewlist.AllHolidayList;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    public AllHolidayListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_all_holiday, container, false);
        userType = PreferenceStorage.getUserType(getActivity());
        Bundle bundle = getArguments();
        if (userType.equals("1")||userType.equals("2")){
            if(bundle != null) {
                classId = getArguments().getString("class_id");
                sectionId = getArguments().getString("section_id");
                classSectionId = getArguments().getString("class_sec_id");
            } else {
                classId = "";
                sectionId = "";
                classSectionId = "";
            }
        } else {
            classId = "";
            sectionId = "";
            classSectionId = PreferenceStorage.getStudentClassIdPreference(getActivity());
        }
        initializeViews();
        initializeEventHelpers();
        return rootView;

    }

    protected void initializeViews() {
        Log.d(TAG, "initialize pull to refresh view");
        loadMoreListView = (ListView) rootView.findViewById(R.id.listView_holidays);
        loadMoreListView.setOnItemClickListener(this);
        allHolidayArrayList = new ArrayList<>();
    }

    protected void initializeEventHelpers() {
        serviceHelper = new ServiceHelper(getActivity());
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(getActivity());
        getHolsList();
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onResponse(JSONObject response) {
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