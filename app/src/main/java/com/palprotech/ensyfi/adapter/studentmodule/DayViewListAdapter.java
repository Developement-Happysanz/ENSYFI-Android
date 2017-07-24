package com.palprotech.ensyfi.adapter.studentmodule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.app.AppController;
import com.palprotech.ensyfi.bean.student.viewlist.DayView;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Admin on 12-07-2017.
 */

public class DayViewListAdapter extends BaseAdapter {

    private static final String TAG = DayViewListAdapter.class.getName();
    private final Transformation transformation;
    private Context context;
    private ArrayList<DayView> dayViews;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();
    private ImageLoader imageLoader = AppController.getInstance().getUniversalImageLoader();

    Comparator<DayView> myComparator = new Comparator<DayView>() {
        public int compare(DayView obj1, DayView obj2) {
            return obj1.getEnrollId().compareTo(obj2.getEnrollId());
        }
    };

    public DayViewListAdapter(Context context, ArrayList<DayView> dayViews) {
        this.context = context;
        this.dayViews = dayViews;
        Collections.sort(dayViews, myComparator);

        transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(0)
                .oval(false)
                .build();
        mSearching = false;
    }

    @Override
    public int getCount() {
        if (mSearching) {
            // Log.d("Event List Adapter","Search count"+mValidSearchIndices.size());
            if (!mAnimateSearch) {
                mAnimateSearch = true;
            }
            return mValidSearchIndices.size();

        } else {
            // Log.d(TAG,"Normal count size");
            return dayViews.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return dayViews.get(mValidSearchIndices.get(position));
        } else {
            return dayViews.get(position);
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
            convertView = inflater.inflate(R.layout.day_view_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtEnrollId = (TextView) convertView.findViewById(R.id.txtEnrollId);
            holder.txtStudentName = (TextView) convertView.findViewById(R.id.txtStudentName);
            holder.txtAttendanceStatus = (TextView) convertView.findViewById(R.id.txtAttendanceStatus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            // Log.d("Event List Adapter","actual position"+ position);
            position = mValidSearchIndices.get(position);
            //Log.d("Event List Adapter", "position is"+ position);

        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        DayView dayView = dayViews.get(position);

        holder.txtEnrollId.setText(dayViews.get(position).getEnrollId());
        holder.txtStudentName.setText(dayViews.get(position).getName());
        if ((dayViews.get(position).getAStatus()).contentEquals("A")) {
            holder.txtAttendanceStatus.setBackgroundResource(R.drawable.btn_attendance_absent);
            holder.txtAttendanceStatus.setText("Absent");
        } else if ((dayViews.get(position).getAStatus()).contentEquals("P")) {
            holder.txtAttendanceStatus.setBackgroundResource(R.drawable.btn_attendance_present);
            holder.txtAttendanceStatus.setText("Present");
        } else if ((dayViews.get(position).getAStatus()).contentEquals("OD")) {
            holder.txtAttendanceStatus.setBackgroundResource(R.drawable.btn_attendance_od);
            holder.txtAttendanceStatus.setText("OD");
        } else {
            holder.txtAttendanceStatus.setBackgroundResource(R.drawable.btn_attendance_leave);
            holder.txtAttendanceStatus.setText("Leave");
        }
        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < dayViews.size(); i++) {
            String dayViewTitle = dayViews.get(i).getName();
            if ((dayViewTitle != null) && !(dayViewTitle.isEmpty())) {
                if (dayViewTitle.toLowerCase().contains(eventName.toLowerCase())) {
                    mValidSearchIndices.add(i);
                }

            }

        }
        Log.d("Event List Adapter", "notify" + mValidSearchIndices.size());
        //notifyDataSetChanged();
    }

    public void exitSearch() {
        mSearching = false;
        mValidSearchIndices.clear();
        mAnimateSearch = false;
        // notifyDataSetChanged();
    }

    public void clearSearchFlag() {
        mSearching = false;
    }

    public class ViewHolder {
        public TextView txtEnrollId, txtStudentName, txtAttendanceStatus;
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
