package com.palprotech.ensyfi.adapter.teachermodule;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;


import com.palprotech.ensyfi.fragments.TimeTableDayFiveFragment;
import com.palprotech.ensyfi.fragments.TimeTableDayFourFragment;
import com.palprotech.ensyfi.fragments.TimeTableDayOneFragment;
import com.palprotech.ensyfi.fragments.TimeTableDaySevenFragment;
import com.palprotech.ensyfi.fragments.TimeTableDaySixFragment;
import com.palprotech.ensyfi.fragments.TimeTableDayThreeFragment;
import com.palprotech.ensyfi.fragments.TimeTableDayTwoFragment;

import java.util.List;

public class TeacherTimeTableAdapter  extends FragmentStatePagerAdapter {
    SQLiteHelper db;
    private Context context;
    private TabLayout data;
    private Fragment[] fragments;

    public TeacherTimeTableAdapter(FragmentManager fm) {
        super(fm);
//        this.context = context;
//        this.data = data;
//        fragments = new Fragment[data.getTabCount()];
    }

    @Override
    public Fragment getItem(int position) {
//        return TimeTableDayOneFragment.newInstance(position + 1);
        switch (position) {
//            case 0:
//                return new TimeTableDayOneFragment();
//            case 1:
//                return new TimeTableDayTwoFragment();
//            case 2:
//                return new TimeTableDayThreeFragment();
//            case 3:
//                return new TimeTableDayFourFragment();
//            case 4:
//                return new TimeTableDayFiveFragment();
//            case 5:
//                return new TimeTableDaySixFragment();
//            case 6:
//                return new TimeTableDaySevenFragment();
        }
        return null;
    }


    @Override
    public int getCount() {
        return 7;

//        if (data != null) {
//            return data.getTabCount();
//        } else {
//            return 0;
//        }
    }
}
