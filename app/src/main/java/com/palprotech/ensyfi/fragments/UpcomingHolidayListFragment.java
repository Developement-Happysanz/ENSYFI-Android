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
import com.palprotech.ensyfi.activity.general.LeaveCalendarActivity;
import com.palprotech.ensyfi.adapter.general.UpcomingHolidayListAdapter;
import com.palprotech.ensyfi.bean.general.viewlist.UpcomingHoliday;
import com.palprotech.ensyfi.bean.general.viewlist.UpcomingHolidayList;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UpcomingHolidayListFragment extends Fragment implements AdapterView.OnItemClickListener, IServiceListener {

    private static final String TAG = UpcomingHolidayListFragment.class.getName();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    private View rootView;
    protected ListView loadMoreListView;
    protected UpcomingHolidayListAdapter upcomingHolidayListAdapter;
    protected ArrayList<UpcomingHoliday> upcomingHolidayArrayList;
    protected ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    protected boolean isLoadingForFirstTime = true;
    int pageNumber = 0, totalCount = 0;
    String classId = "", sectionId = "", classSectionId = "", userType = "";

    public UpcomingHolidayListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_upcoming_holiday, container, false);

        ((LeaveCalendarActivity) getActivity()).setFragmentRefreshListener(new LeaveCalendarActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager().beginTransaction().detach(getTargetFragment()).attach(getTargetFragment()).commit();
                userType = PreferenceStorage.getUserType(getActivity());
                Bundle bundle = getArguments();
                if (userType.equals("1") || userType.equals("2")) {
                    if (bundle != null) {
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
            }
        });

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
        upcomingHolidayArrayList = new ArrayList<>();
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
        UpcomingHolidayList upcomingHolidayList = gson.fromJson(response.toString(), UpcomingHolidayList.class);
        if (upcomingHolidayList != null) {
            Log.d(TAG, "fetched all event list count" + upcomingHolidayList.getCount());
        }
//        updateListAdapter(eventsList.getEvents());
        int totalNearbyCount = 0;
        if (upcomingHolidayList.getUpcomingHolidays() != null && upcomingHolidayList.getUpcomingHolidays().size() > 0) {
            totalCount = upcomingHolidayList.getCount();
            updateListAdapter(upcomingHolidayList.getUpcomingHolidays());
        }
    }

    protected void updateListAdapter(ArrayList<UpcomingHoliday> upcomingHolidayArrayList) {
        this.upcomingHolidayArrayList.addAll(upcomingHolidayArrayList);
       /* if (mNoEventsFound != null)
            mNoEventsFound.setVisibility(View.GONE);*/

        if (upcomingHolidayListAdapter == null) {
            upcomingHolidayListAdapter = new UpcomingHolidayListAdapter(getActivity(), this.upcomingHolidayArrayList);
            loadMoreListView.setAdapter(upcomingHolidayListAdapter);
        } else {
            upcomingHolidayListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(getActivity(), error);

    }
}