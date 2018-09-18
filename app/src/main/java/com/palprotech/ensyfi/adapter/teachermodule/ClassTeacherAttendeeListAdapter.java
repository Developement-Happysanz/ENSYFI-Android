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
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherAttendee;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClassTeacherAttendeeListAdapter extends BaseAdapter {

    //    private final Transformation transformation;
    private Context context;
    private ArrayList<ClassTeacherAttendee> classTeacherAttendee;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    public ClassTeacherAttendeeListAdapter(Context context, ArrayList<ClassTeacherAttendee> classTeacherAttendee) {
        this.context = context;
        this.classTeacherAttendee = classTeacherAttendee;

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
            return classTeacherAttendee.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return classTeacherAttendee.get(mValidSearchIndices.get(position));
        } else {
            return classTeacherAttendee.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClassTeacherAttendeeListAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.class_teacher_attendee_list_item, parent, false);

            holder = new ClassTeacherAttendeeListAdapter.ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.student_name);
            holder.txtStatus = (TextView) convertView.findViewById(R.id.student_status);
            convertView.setTag(holder);
        } else {
            holder = (ClassTeacherAttendeeListAdapter.ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        holder.txtName.setText(classTeacherAttendee.get(position).getName());
        holder.txtStatus.setText(classTeacherAttendee.get(position).getA_status());


        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < classTeacherAttendee.size(); i++) {
            String eventTitle = classTeacherAttendee.get(i).getName();
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
        public TextView txtName, txtStatus;
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