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
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherAttendance;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClassTeacherAttendanceListAdapter  extends BaseAdapter {

    //    private final Transformation transformation;
    private Context context;
    private ArrayList<ClassTeacherAttendance> classTeacherAttendance;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    public ClassTeacherAttendanceListAdapter(Context context, ArrayList<ClassTeacherAttendance> classTeacherAttendance) {
        this.context = context;
        this.classTeacherAttendance = classTeacherAttendance;

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
            return classTeacherAttendance.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return classTeacherAttendance.get(mValidSearchIndices.get(position));
        } else {
            return classTeacherAttendance.get(position);
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
            convertView = inflater.inflate(R.layout.class_teacher_attendance_list_item, parent, false);

            holder = new ClassTeacherAttendanceListAdapter.ViewHolder();
            holder.txtClassStrength = (TextView) convertView.findViewById(R.id.class_strength);
            holder.txtAttendanceDate = (TextView) convertView.findViewById(R.id.attendance_date);
            holder.txtNoPresent = (TextView) convertView.findViewById(R.id.txt_no_present);
            holder.txtNoAbsent = (TextView) convertView.findViewById(R.id.txt_no_absent);
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

        holder.txtClassStrength.setText(classTeacherAttendance.get(position).getClass_total());
        String start = classTeacherAttendance.get(position).getCreated_at();
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = formatter.parse(start);
            SimpleDateFormat sent_date = new SimpleDateFormat("dd-MMM-yyyy");
            String sent_date_name = sent_date.format(date.getTime());
            if (start != null) {
                holder.txtAttendanceDate.setText(sent_date_name);
            } else {
                holder.txtAttendanceDate.setText("N/A");
            }
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        holder.txtNoPresent.setText(classTeacherAttendance.get(position).getNoOfPresent());
        holder.txtNoAbsent.setText(classTeacherAttendance.get(position).getNoOfAbsent());

        if (classTeacherAttendance.get(position).getSent_status().equalsIgnoreCase("1")) {
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
        for (int i = 0; i < classTeacherAttendance.size(); i++) {
            String eventTitle = classTeacherAttendance.get(i).getName();
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
        public TextView txtAttendanceDate, txtClassStrength, txtNoPresent, txtNoAbsent;
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
