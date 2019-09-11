package com.palprotech.ensyfi.adapter.adminmodule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.admin.viewlist.AttendanceClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ClassAttendanceListAdapter extends BaseAdapter {

    //    private final Transformation transformation;
    private Context context;
    private ArrayList<AttendanceClass> attendanceClasses;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

//    Comparator<AttendanceClass> myComparator = new Comparator<Fees>() {
//        public int compare(Fees obj1, Fees obj2) {
//            return obj1.getFeesId().compareTo(obj2.getFeesId());
//        }
//    };

    public ClassAttendanceListAdapter(Context context, ArrayList<AttendanceClass> classes) {
        this.context = context;
        this.attendanceClasses = classes;
//        Collections.sort(fees, myComparator);
//        transformation = new RoundedTransformationBuilder().cornerRadiusDp(0).oval(false).build();
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
            return attendanceClasses.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return attendanceClasses.get(mValidSearchIndices.get(position));
        } else {
            return attendanceClasses.get(position);
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
            convertView = inflater.inflate(R.layout.attendance_class_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtClassName = (TextView) convertView.findViewById(R.id.class_name);
            holder.txtClassStrength = (TextView) convertView.findViewById(R.id.class_strength);
            holder.txtPresent = (TextView) convertView.findViewById(R.id.present);
            holder.txtAbsesnt = (TextView) convertView.findViewById(R.id.absent);
            holder.layPresent = (LinearLayout) convertView.findViewById(R.id.present_layout);
            holder.layAbsesnt = (LinearLayout) convertView.findViewById(R.id.absent_layout);
            holder.layNotTaken = (LinearLayout) convertView.findViewById(R.id.notTaken);

            holder.txtClassName.setText(attendanceClasses.get(position).getClass_name());
            holder.txtClassStrength.setText("Class Strength: " + attendanceClasses.get(position).getClass_total());
            holder.txtPresent.setText("NO. of Present: " + attendanceClasses.get(position).getNoOfPresent());
            holder.txtAbsesnt.setText("NO. of Absent: " + attendanceClasses.get(position).getNoOfAbsent());

            String check = "";
            check = attendanceClasses.get(position).getStatus();
            if (check.equalsIgnoreCase("No")) {
                holder.layPresent.setVisibility(View.GONE);
                holder.layAbsesnt.setVisibility(View.GONE);
                holder.layNotTaken.setVisibility(View.VISIBLE);
                holder.txtClassStrength.setVisibility(View.GONE);
            } else {
                holder.layPresent.setVisibility(View.VISIBLE);
                holder.layAbsesnt.setVisibility(View.VISIBLE);
                holder.layNotTaken.setVisibility(View.GONE);
                holder.txtClassStrength.setVisibility(View.VISIBLE);
            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.txtClassName.setText(attendanceClasses.get(position).getClass_name());
            holder.txtClassStrength.setText("Class Strength: " + attendanceClasses.get(position).getClass_total());
            holder.txtPresent.setText("NO. of Present: " + attendanceClasses.get(position).getNoOfPresent());
            holder.txtAbsesnt.setText("NO. of Absent: " + attendanceClasses.get(position).getNoOfAbsent());

            String check = "";
            check = attendanceClasses.get(position).getStatus();
            if (check.equalsIgnoreCase("No")) {
                holder.layPresent.setVisibility(View.GONE);
                holder.layAbsesnt.setVisibility(View.GONE);
                holder.layNotTaken.setVisibility(View.VISIBLE);
                holder.txtClassStrength.setVisibility(View.GONE);
            } else {
                holder.layPresent.setVisibility(View.VISIBLE);
                holder.layAbsesnt.setVisibility(View.VISIBLE);
                holder.layNotTaken.setVisibility(View.GONE);
                holder.txtClassStrength.setVisibility(View.VISIBLE);
            }
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }


        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < attendanceClasses.size(); i++) {
            String classStudent = attendanceClasses.get(i).getClass_name();
            if ((classStudent != null) && !(classStudent.isEmpty())) {
                if (classStudent.toLowerCase().contains(eventName.toLowerCase())) {
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
        public TextView txtClassName, txtClassStrength, txtPresent, txtAbsesnt;
        public LinearLayout layPresent, layAbsesnt, layNotTaken;
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
