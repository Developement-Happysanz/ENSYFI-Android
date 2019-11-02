package com.palprotech.ensyfi.adapter.teachermodule;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.palprotech.ensyfi.fragments.DynamicTTTFragment;

import java.util.ArrayList;

public class TeacherTimeTableAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    ArrayList<String> ttDays;
    public TeacherTimeTableAdapter(FragmentManager fm, int NumOfTabs, ArrayList<String> list1) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.ttDays = list1;
    }
    @Override
    public Fragment getItem(int position) {
        return DynamicTTTFragment.newInstance(position, ttDays);
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
