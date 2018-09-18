package com.palprotech.ensyfi.adapter.teachermodule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherCtHwOverall;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClassTeacherCtHwOverallListAdapter extends BaseAdapter {

    //    private final Transformation transformation;
    private Context context;
    private ArrayList<ClassTeacherCtHwOverall> classTeacherCtHwOverall;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    public ClassTeacherCtHwOverallListAdapter(Context context, ArrayList<ClassTeacherCtHwOverall> classTeacherCtHwOverall) {
        this.context = context;
        this.classTeacherCtHwOverall = classTeacherCtHwOverall;

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
            return classTeacherCtHwOverall.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return classTeacherCtHwOverall.get(mValidSearchIndices.get(position));
        } else {
            return classTeacherCtHwOverall.get(position);
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
            convertView = inflater.inflate(R.layout.class_teacher_ct_hw_overall_list_item, parent, false);

            holder = new ClassTeacherCtHwOverallListAdapter.ViewHolder();
            holder.classTestDate = (TextView) convertView.findViewById(R.id.classtest_date);
            holder.txtClassTest = (TextView) convertView.findViewById(R.id.txt_classtest);
            holder.txtHomework = (TextView) convertView.findViewById(R.id.txt_homework);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        holder.txtClassTest.setText(classTeacherCtHwOverall.get(position).getHt_count());
        holder.txtHomework.setText(classTeacherCtHwOverall.get(position).getHw_count());
        String start = classTeacherCtHwOverall.get(position).getHw_date();
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(start);
            SimpleDateFormat sent_date = new SimpleDateFormat("dd-MMM-yyyy");
            String sent_date_name = sent_date.format(date.getTime());
            if (start != null) {
                holder.classTestDate.setText(sent_date_name);
            } else {
                holder.classTestDate.setText("N/A");
            }
        } catch (final ParseException e) {
            e.printStackTrace();
        }

//        if (classTeacherCtHwOverall.get(position).getSent_status().equalsIgnoreCase("1")) {
//            holder.sentLayout.setVisibility(View.VISIBLE);
//        } else {
//            holder.sentLayout.setVisibility(View.GONE);
//        }

        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < classTeacherCtHwOverall.size(); i++) {
            String eventTitle = classTeacherCtHwOverall.get(i).getHw_date();
            if ((eventTitle != null) && !(eventTitle.isEmpty())) {
                if (eventTitle.toLowerCase().contains(eventName.toLowerCase())) {
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
        public TextView classTestDate, txtClassTest, txtHomework;
        public RelativeLayout sentLayout;
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