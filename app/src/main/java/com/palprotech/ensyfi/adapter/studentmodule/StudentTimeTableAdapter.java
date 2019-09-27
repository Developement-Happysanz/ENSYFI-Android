package com.palprotech.ensyfi.adapter.studentmodule;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.palprotech.ensyfi.bean.teacher.viewlist.TTDays;
import com.palprotech.ensyfi.fragments.DynamicTTFragment;

import java.util.ArrayList;

public class StudentTimeTableAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    ArrayList<TTDays> ttDays;
    public StudentTimeTableAdapter(FragmentManager fm, int NumOfTabs, ArrayList<TTDays> ttDaysArrayList) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.ttDays = ttDaysArrayList;
    }
    @Override
    public Fragment getItem(int position) {
        return DynamicTTFragment.newInstance(position, ttDays);
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}