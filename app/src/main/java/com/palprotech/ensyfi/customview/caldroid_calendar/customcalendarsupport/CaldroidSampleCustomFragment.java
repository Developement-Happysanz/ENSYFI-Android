package com.palprotech.ensyfi.customview.caldroid_calendar.customcalendarsupport;


import com.palprotech.ensyfi.customview.caldroid_calendar.customcalendar.CaldroidFragment;
import com.palprotech.ensyfi.customview.caldroid_calendar.customcalendar.CaldroidGridAdapter;

public class CaldroidSampleCustomFragment extends CaldroidFragment {

	@Override
	public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
		// TODO Auto-generated method stub
		return new CaldroidSampleCustomAdapter(getActivity(), month, year,
				getCaldroidData(), extraData);
	}

}
