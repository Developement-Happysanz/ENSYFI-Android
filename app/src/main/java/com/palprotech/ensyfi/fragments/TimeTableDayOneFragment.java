package com.palprotech.ensyfi.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.TeacherTimetableListAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.viewlist.TimeTable;

import java.util.ArrayList;
import java.util.List;

public class TimeTableDayOneFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;

    ArrayList<TimeTable> ttArrayList = new ArrayList<>();
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

    TeacherTimetableListAdapter teacherTimetableListAdapter;

    public TimeTableDayOneFragment() {
    }

    public static TimeTableDayOneFragment newInstance(int sectionNumber) {
        TimeTableDayOneFragment fragment = new TimeTableDayOneFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timetable, container, false);

        db = new SQLiteHelper(getActivity());
        getDaysfromDB();

        periodsCount = db.getProfilesCount(String.valueOf(getArguments().get(ARG_SECTION_NUMBER)));
//        loadTimeTable();

        loadMoreListView = (ListView) rootView.findViewById(R.id.time_table_list);
        teacherTimetableListAdapter = new TeacherTimetableListAdapter(getActivity(), this.ttArrayList);
        loadMoreListView.setAdapter(teacherTimetableListAdapter);


        return rootView;
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

    private void loadTimeTable() {
        ttArrayList.clear();
        try {
            String f1Value = list1.get(((Integer) (getArguments().get(ARG_SECTION_NUMBER)) - 1));
            for (int abc = 0; abc < periodsCount; abc++) {
                String c1Value = String.valueOf(abc);
                Cursor c = db.getTeacherTimeTableValue(f1Value, c1Value);
                if (c.getCount() > 0) {
                    if (c.moveToFirst()) {
                        do {
                            TimeTable lde = new TimeTable();
                            lde.setClassName(c.getString(0));
                            lde.setSecName(c.getString(1));
                            lde.setSubjectName(c.getString(2));
                            lde.setClassId(c.getString(3));
                            lde.setSubjectId(c.getString(4));
                            lde.setPeriod(c.getString(5));
                            lde.setSubjectName(c.getString(6));
                            lde.setFromTime(c.getString(7));
                            lde.setToTime(c.getString(8));
                            lde.setIsBreak(c.getString(9));

                            // Add this object into the ArrayList myList
                            ttArrayList.add(lde);
                        } while (c.moveToNext());
                    }
                } else {
                    Toast.makeText(getActivity(), "No records found", Toast.LENGTH_LONG).show();
                }
                db.close();
            }

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}