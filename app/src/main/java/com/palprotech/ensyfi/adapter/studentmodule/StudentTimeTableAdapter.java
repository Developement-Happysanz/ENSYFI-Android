package com.palprotech.ensyfi.adapter.studentmodule;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.fragments.TimeTableDayFiveFragment;
import com.palprotech.ensyfi.fragments.TimeTableDayFourFragment;
import com.palprotech.ensyfi.fragments.TimeTableDayOneFragment;
import com.palprotech.ensyfi.fragments.TimeTableDaySevenFragment;
import com.palprotech.ensyfi.fragments.TimeTableDaySixFragment;
import com.palprotech.ensyfi.fragments.TimeTableDayThreeFragment;
import com.palprotech.ensyfi.fragments.TimeTableDayTwoFragment;

public class StudentTimeTableAdapter extends FragmentStatePagerAdapter {

    public StudentTimeTableAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TimeTableDayOneFragment();
            case 1:
                return new TimeTableDayTwoFragment();
            case 2:
                return new TimeTableDayThreeFragment();
            case 3:
                return new TimeTableDayFourFragment();
            case 4:
                return new TimeTableDayFiveFragment();
//            case 5:
//                return new TimeTableDaySixFragment();
//            case 6:
//                return new TimeTableDaySevenFragment();
        }
        return null;
    }


    @Override
    public int getCount() {
        return 5;
//        return 7;
    }
}