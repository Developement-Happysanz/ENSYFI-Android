package com.palprotech.ensyfi.adapter.teachermodule;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;


import com.palprotech.ensyfi.fragments.TimeTableDayOneFragment;

import java.util.List;

public class TeacherTimeTableAdapter  extends FragmentStatePagerAdapter {
    SQLiteHelper db;
    private Context context;
    private TabLayout data;
    private Fragment[] fragments;

    public TeacherTimeTableAdapter(Context context, FragmentManager fm, TabLayout data) {
        super(fm);
        this.context = context;
        this.data = data;
        fragments = new Fragment[data.getTabCount()];
    }

    @Override
    public Fragment getItem(int position) {
        return TimeTableDayOneFragment.newInstance(position + 1);
    }


    @Override
    public int getCount() {
        if (data != null) {
            return data.getTabCount();
        } else {
            return 0;
        }
    }
}
