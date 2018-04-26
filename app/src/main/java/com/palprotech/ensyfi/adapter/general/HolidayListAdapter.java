package com.palprotech.ensyfi.adapter.general;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.palprotech.ensyfi.fragments.OneFragment;

public class HolidayListAdapter   extends FragmentStatePagerAdapter {

    public HolidayListAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return OneFragment.newInstance(position + 1);
    }


    @Override
    public int getCount() {
        return 2;
    }
}
