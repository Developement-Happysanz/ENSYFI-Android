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
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherCtHwDaywise;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClassTeacherCtHwDaywiseListAdapter extends BaseAdapter {

    //    private final Transformation transformation;
    private Context context;
    private ArrayList<ClassTeacherCtHwDaywise> classTeacherCtHwDaywise;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    public ClassTeacherCtHwDaywiseListAdapter(Context context, ArrayList<ClassTeacherCtHwDaywise> classTeacherCtHwDaywise) {
        this.context = context;
        this.classTeacherCtHwDaywise = classTeacherCtHwDaywise;

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
            return classTeacherCtHwDaywise.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return classTeacherCtHwDaywise.get(mValidSearchIndices.get(position));
        } else {
            return classTeacherCtHwDaywise.get(position);
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
            convertView = inflater.inflate(R.layout.class_teacher_ct_hw_daywise_list_item, parent, false);

            holder = new ClassTeacherCtHwDaywiseListAdapter.ViewHolder();
            holder.classTestTeacherName = (TextView) convertView.findViewById(R.id.txt_ct_hw_name);
            holder.txtclassTestSubject = (TextView) convertView.findViewById(R.id.txt_ct_hw_subject);
            holder.txtclassTestType = (TextView) convertView.findViewById(R.id.txt_ct_hw_type);
            holder.sentLayout = (RelativeLayout) convertView.findViewById(R.id.sent_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        holder.classTestTeacherName.setText(": " + classTeacherCtHwDaywise.get(position).getName());
        holder.txtclassTestSubject.setText(": " + classTeacherCtHwDaywise.get(position).getSubject_name());

        if (classTeacherCtHwDaywise.get(position).getHw_type().equalsIgnoreCase("HT")) {
            holder.txtclassTestType.setText(": Classtest");
        } else {
            holder.txtclassTestType.setText(": Homework");
        }

        if (classTeacherCtHwDaywise.get(position).getSend_option_status().equalsIgnoreCase("1")) {
            holder.sentLayout.setVisibility(View.VISIBLE);
        } else {
            holder.sentLayout.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < classTeacherCtHwDaywise.size(); i++) {
            String eventTitle = classTeacherCtHwDaywise.get(i).getHw_id();
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
        public TextView classTestTeacherName, txtclassTestSubject, txtclassTestType;
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