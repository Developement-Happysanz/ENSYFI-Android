package com.palprotech.ensyfi.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.teachermodule.TimeTableReviewAddActivity;
import com.palprotech.ensyfi.adapter.studentmodule.StudentTimeTableListAdapter;
import com.palprotech.ensyfi.adapter.teachermodule.TeacherTimetableListAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.student.viewlist.StudentTimeTable;
import com.palprotech.ensyfi.bean.student.viewlist.StudentTimeTableList;
import com.palprotech.ensyfi.bean.teacher.viewlist.TTDays;
import com.palprotech.ensyfi.bean.teacher.viewlist.TimeTable;
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

import static android.util.Log.d;

public class DynamicTTTFragment extends Fragment implements AdapterView.OnItemClickListener, DialogClickListener {
    Context context;
    private View view;
    private static List<String> ttDays;
    ArrayList<TimeTable> ttArrayList = new ArrayList<>();
    ArrayList<StudentTimeTable> studentTTArrayList = new ArrayList<>();
    private int val;
    private StudentTimeTableListAdapter studentTimeTableListAdapter;
    private TeacherTimetableListAdapter teacherTimetableListAdapter;
    private static final String TAG = DynamicTTTFragment.class.getName();
    private String subCatId = "";
    private ServiceHelper serviceHelper;
    private int totalCount = 0, checkrun = 0;
    private boolean isLoadingForFirstTime = true;
    private ProgressDialogHelper progressDialogHelper;
    private ListView loadMoreListView;
    private Boolean msgErr = false;
    private Boolean noService = false;
    private String res = "";
    private String id = "";
    SQLiteHelper db;
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> list1 = new ArrayList<String>();
    int dayCount = 0;

    private static boolean _hasLoadedOnce = false; // your boolean field

    public static DynamicTTTFragment newInstance(int val, List<String> list1) {
        DynamicTTTFragment fragment = new DynamicTTTFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", val);
        fragment.setArguments(args);
        ttDays = list1;
        if (String.valueOf(val).equalsIgnoreCase("1")) {
            _hasLoadedOnce = true;
        } else {
            _hasLoadedOnce = false;
        }
        return fragment;
    }

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timetable, container, false);
        db = new SQLiteHelper(view.getContext());
//        serviceHelper = new ServiceHelper(view.getContext());
//        serviceHelper.setServiceListener(this);
//        progressDialogHelper = new ProgressDialogHelper(view.getContext());
        val = getArguments().getInt("someInt", 0);
//        categories = subCategoryList.getCategoryArrayList();
//        categories = subCategoryList.getCategoryArrayList();
        getDaysfromDB();
        subCatId = list.get(val);
//        PreferenceStorage.saveSubCatClick(view.getContext(), subCatId);
//        rateCount = (TextView) view.findViewById(R.id.service_count);
//        summary = (TextView) view.findViewById(R.id.view_summary);
//        summary.setOnClickListener(this);
//        c = view.findViewById(R.id.c);
//        c.setText("" + subCatId);

        loadTimeTable();

        loadMoreListView = view.findViewById(R.id.time_table_list);
        loadMoreListView.setOnItemClickListener(this);


        if (teacherTimetableListAdapter == null) {
            teacherTimetableListAdapter = new TeacherTimetableListAdapter(getActivity(), this.ttArrayList);
            loadMoreListView.setAdapter(teacherTimetableListAdapter);
        } else {
            teacherTimetableListAdapter.notifyDataSetChanged();
        }

        return view;
    }

    private void loadTimeTable() {
        ttArrayList.clear();
        try {
            Cursor c = db.getTeacherTimeTableValueNew(subCatId);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onEvent list item click" + position);

        if ((ttArrayList != null) && (!ttArrayList.isEmpty())) {

            String name1 = ttArrayList.get(position).getClassName() + "-" + ttArrayList.get(position).getSecName() +
                    "," + ttArrayList.get(position).getClassId() + "," + ttArrayList.get(position).getSubjectName() +
                    "," + ttArrayList.get(position).getSubjectId() + "," + ttArrayList.get(position).getPeriod();

            Intent navigationIntent = new Intent(getActivity(), TimeTableReviewAddActivity.class);
            navigationIntent.putExtra("timeTableValue", name1);
            startActivity(navigationIntent);
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}