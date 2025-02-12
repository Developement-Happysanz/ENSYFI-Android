package com.palprotech.ensyfi.customview.caldroid_calendar.customcalendarsupport;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.customview.caldroid_calendar.customcalendar.CaldroidFragment;
import com.palprotech.ensyfi.customview.caldroid_calendar.customcalendar.CaldroidGridAdapter;

import java.util.Map;

import hirondelle.date4j.DateTime;

public class CaldroidSampleCustomAdapter extends CaldroidGridAdapter {

  public CaldroidSampleCustomAdapter(Context context, int month, int year,
                                     Map<String, Object> caldroidData,
                                     Map<String, Object> extraData) {
    super(context, month, year, caldroidData, extraData);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View cellView = convertView;

    // For reuse
    if (convertView == null) {
      cellView = inflater.inflate(R.layout.custom_cell, null);
    }

    int topPadding = cellView.getPaddingTop();
    int leftPadding = cellView.getPaddingLeft();
    int bottomPadding = cellView.getPaddingBottom();
    int rightPadding = cellView.getPaddingRight();

    TextView tv1 = (TextView) cellView.findViewById(R.id.tv1);
    TextView tv2 = (TextView) cellView.findViewById(R.id.tv2);

    tv1.setTextColor(Color.BLACK);

    // Get dateTime of this cell
    DateTime dateTime = this.datetimeList.get(position);
    Resources resources = context.getResources();

    // Set color of the dates in previous / next month
    if (dateTime.getMonth() != month) {
      tv1.setTextColor(resources
              .getColor(R.color.caldroid_darker_gray));
    }

    boolean shouldResetDiabledView = false;
    boolean shouldResetSelectedView = false;
    boolean shouldResetLeaveView = false;

    // Customize for disabled dates and date outside min/max dates
    if ((minDateTime != null && dateTime.lt(minDateTime))
            || (maxDateTime != null && dateTime.gt(maxDateTime))
            || (disableDates != null && disableDates.indexOf(dateTime) != -1)) {

      tv1.setTextColor(CaldroidFragment.disabledTextColor);
//			if (CaldroidFragment.disabledBackgroundDrawable == -1) {
      cellView.setBackgroundResource(R.drawable.disable_cell);
//			} else {
//				cellView.setBackgroundResource(CaldroidFragment.disabledBackgroundDrawable);
//			}

      if (dateTime.equals(getToday())) {
        cellView.setBackgroundResource(R.drawable.red_border_gray_bg);
      }

    } else {
      shouldResetDiabledView = true;
    }

    // Customize for selected dates
    if (selectedDates != null && selectedDates.indexOf(dateTime) != -1) {
      cellView.setBackgroundColor(resources
              .getColor(R.color.caldroid_sky_blue));

      tv1.setTextColor(Color.BLACK);

    } else {
      shouldResetSelectedView = true;
    }

    // Customize for abs dates
    if (disableDates != null && disableDates.indexOf(dateTime) != -1) {
      cellView.setBackgroundColor(resources
              .getColor(R.color.green));

      tv1.setTextColor(Color.BLACK);

    } else {
      shouldResetSelectedView = true;
    }

    // Customize for od dates
    if (odDates != null && odDates.indexOf(dateTime) != -1) {
      cellView.setBackgroundColor(resources
              .getColor(R.color.pending));

      tv1.setTextColor(Color.BLACK);

    } else {
      shouldResetSelectedView = true;
    }

    // Customize for leave dates
    if (leaveDates != null && leaveDates.indexOf(dateTime) != -1) {
      cellView.setBackgroundColor(resources
              .getColor(R.color.approve));

      tv1.setTextColor(Color.BLACK);

    } else {
      shouldResetLeaveView = true;
    }

    if (shouldResetDiabledView && shouldResetSelectedView && shouldResetLeaveView) {
      // Customize for today
      if (dateTime.equals(getToday())) {
        cellView.setBackgroundResource(R.drawable.red_border);
      } else {
        cellView.setBackgroundResource(R.drawable.cell_bg);
      }
    }

    tv1.setText("" + dateTime.getDay());
    tv2.setText("Hi");

    // Somehow after setBackgroundResource, the padding collapse.
    // This is to recover the padding
    cellView.setPadding(leftPadding, topPadding, rightPadding,
            bottomPadding);

    // Set custom color if required
    setCustomResources(dateTime, cellView, tv1);

    return cellView;
  }

}
