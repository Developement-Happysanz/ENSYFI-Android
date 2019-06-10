package com.palprotech.ensyfi.adapter.studentmodule;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.student.viewlist.StudentTimeTable;

import java.util.ArrayList;

public class StudentTimeTableListAdapter extends BaseAdapter {

    //    private final Transformation transformation;
    private Context context;
    LayoutInflater inflater;
    ArrayList<StudentTimeTable> teacherTimeTable = new ArrayList<StudentTimeTable>();;
    String isBreak = "";


    public StudentTimeTableListAdapter(Context context, ArrayList<StudentTimeTable> teacherTimeTable) {
        this.teacherTimeTable = teacherTimeTable;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return teacherTimeTable.size();

    }

    @Override
    public StudentTimeTable getItem(int position) {
        return teacherTimeTable.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StudentTimeTableListAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.time_table_list_item, parent, false);
            holder = new StudentTimeTableListAdapter.ViewHolder();
            holder.periodLayout = (RelativeLayout) convertView.findViewById(R.id.period_layout);
            holder.breakLayout = (RelativeLayout) convertView.findViewById(R.id.break_layout);
            holder.txtSubjectName = (TextView) convertView.findViewById(R.id.subject_name);
            holder.txtClassName = (TextView) convertView.findViewById(R.id.class_name);
            holder.txtSectionName = (TextView) convertView.findViewById(R.id.section_name);
            holder.txtTeacherName = (TextView) convertView.findViewById(R.id.teacher_name);
            holder.txtPeriodNumber = (TextView) convertView.findViewById(R.id.period_number);
            holder.txtPeriodStartTime = (TextView) convertView.findViewById(R.id.period_start_time);
            holder.txtPeriodEndTime = (TextView) convertView.findViewById(R.id.period_end_time);
            holder.txtBreakName = (TextView) convertView.findViewById(R.id.break_name);
            holder.txtBreakStartTime = (TextView) convertView.findViewById(R.id.break_start_time);
            holder.txtBreakEndTime = (TextView) convertView.findViewById(R.id.break_end_time);
            convertView.setTag(holder);
        } else {
            holder = (StudentTimeTableListAdapter.ViewHolder) convertView.getTag();
        }

        StudentTimeTable currentListData = getItem(position);

        isBreak = currentListData.getIsBreak();

        if (isBreak.equalsIgnoreCase("0")) {
            holder.periodLayout.setVisibility(View.VISIBLE);
            holder.breakLayout.setVisibility(View.GONE);
        } else if (isBreak.equalsIgnoreCase("1")) {
            holder.periodLayout.setVisibility(View.GONE);
            holder.breakLayout.setVisibility(View.VISIBLE);
        }

        holder.txtSubjectName.setText(currentListData.getSubjectName());
        holder.txtClassName.setText(currentListData.getClassName());
        holder.txtSectionName.setText(currentListData.getSecName());
        holder.txtTeacherName.setText(currentListData.getName());
        holder.txtPeriodNumber.setText(currentListData.getPeriod());
        holder.txtPeriodStartTime.setText(currentListData.getFromTime() + " - ");
        holder.txtPeriodEndTime.setText(currentListData.getToTime());
        holder.txtBreakName.setText(currentListData.getBreakName());
        holder.txtBreakStartTime.setText(currentListData.getFromTime() + " - ");
        holder.txtBreakEndTime.setText(currentListData.getToTime());

//        String endBre = currentListData.getFromTime();
//        try {
//            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            Date date = formatter.parse(endBre);
//            SimpleDateFormat sent_date = new SimpleDateFormat("dd-MM-yyyy");
//            String sent_date_name = sent_date.format(date.getTime());
//            if (endBre != null) {
//                holder.txtBreakEndTime.setText(sent_date_name);
//            } else {
//                holder.txtBreakEndTime.setText("N/A");
//            }
//        } catch (final ParseException e) {
//            e.printStackTrace();
//        }

//        holder.txtDay.setText("- " + allHoliday.get(position).getDay());
        return convertView;
    }

    public class ViewHolder {
        public RelativeLayout periodLayout, breakLayout;
        public TextView txtSubjectName, txtTeacherName, txtPeriodNumber, txtPeriodStartTime;
        public TextView txtPeriodEndTime, txtClassName, txtSectionName, txtBreakName, txtBreakStartTime, txtBreakEndTime;
    }
}
