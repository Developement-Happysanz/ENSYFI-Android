package com.palprotech.ensyfi.adapter.teachermodule;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.palprotech.ensyfi.fragments.TimeTableFragment;

public class TeacherTimeTableAdapter  extends FragmentStatePagerAdapter {

    public TeacherTimeTableAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TimeTableFragment.newInstance(position + 1);
    }


    @Override
    public int getCount() {
        return 7;
    }
}
