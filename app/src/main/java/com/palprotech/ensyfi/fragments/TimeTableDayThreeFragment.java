package com.palprotech.ensyfi.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.studentmodule.StudentTimeTableListAdapter;
import com.palprotech.ensyfi.adapter.teachermodule.TeacherTimetableListAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.student.viewlist.StudentTimeTable;
import com.palprotech.ensyfi.bean.student.viewlist.StudentTimeTableList;
import com.palprotech.ensyfi.bean.teacher.viewlist.TimeTable;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class TimeTableDayThreeFragment extends Fragment implements AdapterView.OnItemClickListener, IServiceListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    String f1Value = "";
    ArrayList<TimeTable> ttArrayList = new ArrayList<>();
    ArrayList<StudentTimeTable> studentTTArrayList = new ArrayList<>();
    ListView loadMoreListView;
    SQLiteHelper db;
    String ClassName, SectionName, SubjectName;
    String ClassId = "";
    String SubjectId = "";
    String PeriodId = "";
    List<String> list = new ArrayList<String>();
    List<String> list1 = new ArrayList<String>();
    int dayCount = 0;
    int periodsCount = 0;
    //    Boolean setval = false;
    protected ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;

    TeacherTimetableListAdapter teacherTimetableListAdapter;
    StudentTimeTableListAdapter studentTimetableListAdapter;
    protected boolean isLoadingForFirstTime = true;
    int pageNumber = 0, totalCount = 0;

    public TimeTableDayThreeFragment() {
    }

    public static TimeTableDayThreeFragment newInstance(int sectionNumber) {
        TimeTableDayThreeFragment fragment = new TimeTableDayThreeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timetable, container, false);

//        ttArrayList = (ArrayList<TimeTable>) getActivity().getIntent().getSerializableExtra("leaveObj");

        loadMoreListView = (ListView) rootView.findViewById(R.id.time_table_list);
        db = new SQLiteHelper(getActivity());
//        getDaysfromDB();

//        periodsCount = db.getProfilesCount(String.valueOf(getArguments().get(ARG_SECTION_NUMBER)));
//        f1Value = list.get(((Integer) (getArguments().get(ARG_SECTION_NUMBER)) - 1));
//        if((!setval)&(f1Value.equalsIgnoreCase("1"))) {
//
//        } else {
//            f1Value = "";
//            setval = false;
//        }

        if (PreferenceStorage.getUserType(getActivity()).equalsIgnoreCase("2") ||
                PreferenceStorage.getUserType(getActivity()).equalsIgnoreCase("1")) {

            loadTimeTable();

            if (teacherTimetableListAdapter == null) {
                teacherTimetableListAdapter = new TeacherTimetableListAdapter(getActivity(), this.ttArrayList);
                loadMoreListView.setAdapter(teacherTimetableListAdapter);
            } else {
                teacherTimetableListAdapter.notifyDataSetChanged();
            }
        } else {
            initializeEventHelpers();
            getHolsList();
        }

        return rootView;
    }

    public void getHolsList() {
        JSONObject jsonObject = new JSONObject();
        String id = "";
        id = PreferenceStorage.getStudentClassIdPreference(getActivity());
        try {
            jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID, id);
            jsonObject.put(EnsyfiConstants.PARAMS_DAY_ID, "3");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
        String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getActivity()) + EnsyfiConstants.GET_TIME_TABLE_API;
        serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
    }

//    private void getDaysfromDB() {
//        Cursor c = db.selectTimeTableDays();
//        dayCount = c.getCount();
//        if (c.getCount() > 0) {
//            if (c.moveToFirst()) {
//                do {
//                    list.add("" + c.getString(0));
//                    list1.add("" + c.getString(1));
//                } while (c.moveToNext());
//            }
//        }
//    }

    protected void initializeEventHelpers() {
        serviceHelper = new ServiceHelper(getActivity());
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(getActivity());
    }

    private void loadTimeTable() {
        ttArrayList.clear();
        try {
            Cursor c = db.getTeacherTimeTableValueNew("1");
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
                        lde.setPeriod(c.getString(6));
                        lde.setFromTime(c.getString(7));
                        lde.setToTime(c.getString(8));
                        lde.setIsBreak(c.getString(9));
                        lde.setBreakName(c.getString(10));

                        // Add this object into the ArrayList myList
                        ttArrayList.add(lde);
                    } while (c.moveToNext());
                }
            } else {
                Toast.makeText(getActivity(), "No records found", Toast.LENGTH_LONG).show();
            }
            db.close();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
//        setval = true;
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
            LoadListView(response);
        }
    }

    private void LoadListView(JSONObject response) {
        progressDialogHelper.hideProgressDialog();
//        loadMoreListView.onLoadMoreComplete();
        Gson gson = new Gson();
        StudentTimeTableList studentTimeTableList = gson.fromJson(response.toString(), StudentTimeTableList.class);
        if (studentTimeTableList != null) {
            Log.d(TAG, "fetched all event list count" + studentTimeTableList.getCount());
        }
//        updateListAdapter(eventsList.getEvents());
        int totalNearbyCount = 0;
        if (studentTimeTableList.getStudentTT() != null && studentTimeTableList.getStudentTT().size() > 0) {


            isLoadingForFirstTime = false;
            totalCount = studentTimeTableList.getCount();
            updateListAdapter(studentTimeTableList.getStudentTT());
        }
    }

    protected void updateListAdapter(ArrayList<StudentTimeTable> studentTimeTableList) {
        this.studentTTArrayList.addAll(studentTimeTableList);
       /* if (mNoEventsFound != null)
            mNoEventsFound.setVisibility(View.GONE);*/

        if (studentTimetableListAdapter == null) {
            studentTimetableListAdapter = new StudentTimeTableListAdapter(getActivity(), this.studentTTArrayList);
            loadMoreListView.setAdapter(studentTimetableListAdapter);
        } else {
            studentTimetableListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String error) {

    }
}