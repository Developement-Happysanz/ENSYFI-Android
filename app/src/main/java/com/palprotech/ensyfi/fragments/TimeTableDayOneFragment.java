package com.palprotech.ensyfi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.palprotech.ensyfi.R;

public class TimeTableDayOneFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;

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

        sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        TextView textView = (TextView) rootView.findViewById(R.id.txtTabItemNumber);
        textView.setText("" + sectionNumber);
        return rootView;
    }
}