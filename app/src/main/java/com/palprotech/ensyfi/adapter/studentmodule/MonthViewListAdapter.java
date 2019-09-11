package com.palprotech.ensyfi.adapter.studentmodule;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.student.viewlist.MonthView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//import com.makeramen.roundedimageview.RoundedTransformationBuilder;

/**
 * Created by Admin on 12-07-2017.
 */

public class MonthViewListAdapter extends BaseAdapter {

//    private final Transformation transformation;
    private Context context;
    private ArrayList<MonthView> monthViews;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    Comparator<MonthView> myComparator = new Comparator<MonthView>() {
        public int compare(MonthView obj1, MonthView obj2) {
            return obj1.getEnrollId().compareTo(obj2.getEnrollId());
        }
    };

    public MonthViewListAdapter(Context context, ArrayList<MonthView> monthViews) {
        this.context = context;
        this.monthViews = monthViews;
//        Collections.sort(monthViews, myComparator);

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
            return monthViews.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return monthViews.get(mValidSearchIndices.get(position));
        } else {
            return monthViews.get(position);
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
            convertView = inflater.inflate(R.layout.month_view_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtEnrollId = (TextView) convertView.findViewById(R.id.txtEnrollId);
            holder.txtStudentName = (TextView) convertView.findViewById(R.id.txtStudentName);
            holder.txtLeaves = (TextView) convertView.findViewById(R.id.txtLeaves);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        holder.txtEnrollId.setText((position+1)+".");
        holder.txtStudentName.setText(monthViews.get(position).getName());
        Double leave = Double.parseDouble(monthViews.get(position).getLeaves());
        double roundOff = (double) Math.round(leave * 100) / 100;
        String roundLeaves = String.valueOf(roundOff);
        if ((monthViews.get(position).getAStatus().equalsIgnoreCase("P"))) {
            holder.txtLeaves.setText("No Leave");
            holder.txtLeaves.setTextColor(ContextCompat.getColor(context, R.color.approve));
        }
        else {
            holder.txtLeaves.setText(roundLeaves + " Days Leave");
            holder.txtLeaves.setTextColor(ContextCompat.getColor(context, R.color.reject));
        }


        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < monthViews.size(); i++) {
            String monthViewTitle = monthViews.get(i).getName();
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
        public TextView txtEnrollId, txtStudentName, txtLeaves;
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
