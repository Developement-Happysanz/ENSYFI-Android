package com.palprotech.ensyfi.adapter.studentmodule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.student.viewlist.MonthViewStudentLeaveDays;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by Admin on 12-07-2017.
 */

public class MonthViewStudentLeaveDaysListAdapter extends BaseAdapter {

//    private final Transformation transformation;
    private Context context;
    private ArrayList<MonthViewStudentLeaveDays> monthViewStudentLeaveDays;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    public MonthViewStudentLeaveDaysListAdapter(Context context, ArrayList<MonthViewStudentLeaveDays> monthViewStudentLeaveDays) {
        this.context = context;
        this.monthViewStudentLeaveDays = monthViewStudentLeaveDays;

//        transformation = new RoundedTransformationBuilder()
//                .cornerRadiusDp(0)
//                .oval(false)
//                .build();
        mSearching = false;
    }

    @Override
    public int getCount() {
        if (mSearching) {
            if (!mAnimateSearch) {
                mAnimateSearch = true;
            }
            return mValidSearchIndices.size();
        } else {
            return monthViewStudentLeaveDays.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return monthViewStudentLeaveDays.get(mValidSearchIndices.get(position));
        } else {
            return monthViewStudentLeaveDays.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.month_view_student_leave_days_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtDuration = (TextView) convertView.findViewById(R.id.txtDuration);
            holder.txtLeaveDays = (TextView) convertView.findViewById(R.id.txtLeaveDays);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        String checkStatus = monthViewStudentLeaveDays.get(position).getLeaves();
        int status = Integer.parseInt(checkStatus);
        String leaveNos = "";
        if (status == 1) {
//            leaveNos = "Half day";
            leaveNos = "Full day";
        } else {
            leaveNos = "Full day";
        }
        String checkStatus1 = monthViewStudentLeaveDays.get(position).getAStatus();
        String leaveType = "";
        if (checkStatus1.contentEquals("L")) {
            leaveType = "leave";
        } else if (checkStatus1.contentEquals("A")) {
            leaveType = "absent";
        } else if (checkStatus1.contentEquals("OD")) {
            leaveType = "OD";
        } else {
            leaveType = "";
        }
        holder.txtDuration.setText(leaveNos + " " + leaveType);
        holder.txtLeaveDays.setText(monthViewStudentLeaveDays.get(position).getAbsDate());
        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < monthViewStudentLeaveDays.size(); i++) {
            String monthViewTitle = monthViewStudentLeaveDays.get(i).getName();
            if ((monthViewTitle != null) && !(monthViewTitle.isEmpty())) {
                if (monthViewTitle.toLowerCase().contains(eventName.toLowerCase())) {
                    mValidSearchIndices.add(i);
                }
            }
        }
        Log.d("Event List Adapter", "notify" + mValidSearchIndices.size());
    }

    public void exitSearch() {
        mSearching = false;
        mValidSearchIndices.clear();
        mAnimateSearch = false;
    }

    public void clearSearchFlag() {
        mSearching = false;
    }

    public class ViewHolder {
        public TextView txtStudentName, txtLeaveDays, txtDuration;
    }

    public boolean ismSearching() {
        return mSearching;
    }

    public int getActualEventPos(int selectedSearchpos) {
        if (selectedSearchpos < mValidSearchIndices.size()) {
            return mValidSearchIndices.get(selectedSearchpos);
        } else {
            return 0;
        }
    }
}
