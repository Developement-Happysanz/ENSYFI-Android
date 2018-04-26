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
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    private View rootView;
    protected ListView loadMoreListView;
    protected AllHolidayListAdapter allHolidayListAdapter;
    protected ArrayList<AllHoliday> allHolidayArrayList;
    protected ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    protected boolean isLoadingForFirstTime = true;
    int pageNumber = 0, totalCount = 0;

    public AllHolidayListFragment() {
    }

    public static AllHolidayListFragment newInstance(int sectionNumber) {
        AllHolidayListFragment fragment = new AllHolidayListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_all_holiday, container, false);

        sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        TextView textView = (TextView) rootView.findViewById(R.id.txtTabItemNumber);
        textView.setText("TAB " + sectionNumber);
        initializeViews();
        initializeEventHelpers();
        return rootView;
    }

    protected void initializeViews() {
        Log.d(TAG, "initialize pull to refresh view");
        loadMoreListView = (ListView) rootView.findViewById(R.id.listView_holidays);
       /* mNoEventsFound = (TextView) view.findViewById(R.id.no_home_events);
        if (mNoEventsFound != null)
            mNoEventsFound.setVisibility(View.GONE);
        loadMoreListView.setOnLoadMoreListener(this); */
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
//            jsonObject.put(EnsyfiConstants.PARAMS_HDAY_CLASS_ID, PreferenceStorage.getStudentClassIdPreference(getApplicationContext()));
            jsonObject.put(EnsyfiConstants.PARAMS_HDAY_CLASS_ID, "1");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
        String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getActivity()) + EnsyfiConstants.GET_UPCOMING_HOLIDAY_API;
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