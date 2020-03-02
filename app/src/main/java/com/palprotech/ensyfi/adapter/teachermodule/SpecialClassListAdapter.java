package com.palprotech.ensyfi.adapter.teachermodule;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.teacher.viewlist.SpecialClass;
import com.palprotech.ensyfi.bean.teacher.viewlist.TimeTable;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SpecialClassListAdapter extends BaseAdapter {

    //    private final Transformation transformation;
    private Context context;
    LayoutInflater inflater;
    ArrayList<SpecialClass> teacherTimeTable = new ArrayList<SpecialClass>();;
    String isBreak = "";


    public SpecialClassListAdapter(Context context, ArrayList<SpecialClass> teacherTimeTable) {
        this.teacherTimeTable = teacherTimeTable;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return teacherTimeTable.size();

    }

    @Override
    public SpecialClass getItem(int position) {
        return teacherTimeTable.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpecialClassListAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.special_class_list_item, parent, false);
            holder = new SpecialClassListAdapter.ViewHolder();
            holder.txtSubjectName = (TextView) convertView.findViewById(R.id.subject_name);
            holder.txtClassName = (TextView) convertView.findViewById(R.id.class_name);
            holder.txtSectionName = (TextView) convertView.findViewById(R.id.section_name);
            holder.txtTeacherName = (TextView) convertView.findViewById(R.id.teacher_name);
            holder.txtPeriodNumber = (TextView) convertView.findViewById(R.id.period_number);
            holder.txtPeriodStartTime = (TextView) convertView.findViewById(R.id.period_start_time);
            holder.txtPeriodDate = (TextView) convertView.findViewById(R.id.date);
            holder.txtPeriodEndTime = (TextView) convertView.findViewById(R.id.period_end_time);
            holder.txtBreakName = (TextView) convertView.findViewById(R.id.break_name);
            holder.txtBreakStartTime = (TextView) convertView.findViewById(R.id.break_start_time);
            holder.txtBreakEndTime = (TextView) convertView.findViewById(R.id.break_end_time);
//            if (PreferenceStorage.getUserType(context).equalsIgnoreCase("2")) {
//                holder.txtClassName.setVisibility(View.VISIBLE);
//                holder.txtClassName.setVisibility(View.VISIBLE);
//            } else {
//                holder.txtClassName.setVisibility(View.GONE);
//                holder.txtClassName.setVisibility(View.GONE);
//            }
            convertView.setTag(holder);
        } else {
            holder = (SpecialClassListAdapter.ViewHolder) convertView.getTag();
        }

        SpecialClass currentListData = getItem(position);

        holder.txtSubjectName.setText(currentListData.getsubject_name());
        holder.txtClassName.setText(currentListData.getclass_sec_name());
        holder.txtTeacherName.setText(currentListData.getsubject_topic());
        holder.txtPeriodStartTime.setText(currentListData.getstart_time());
        holder.txtPeriodEndTime.setText(currentListData.getend_time());

        String endBre = currentListData.getspecial_class_date();
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = formatter.parse(endBre);
            SimpleDateFormat sent_date = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
            String sent_date_name = sent_date.format(date.getTime());
            if (endBre != null) {
                holder.txtPeriodDate.setText(sent_date_name);
            } else {
                holder.txtPeriodDate.setText("N/A");
            }
        } catch (final ParseException e) {
            e.printStackTrace();
        }

//        holder.txtDay.setText("- " + allHoliday.get(position).getDay());
        return convertView;
    }

    public class ViewHolder {
        public RelativeLayout periodLayout, breakLayout;
        public TextView txtSubjectName, txtTeacherName, txtPeriodNumber, txtPeriodStartTime, txtPeriodDate;
        public TextView txtPeriodEndTime, txtClassName, txtSectionName, txtBreakName, txtBreakStartTime, txtBreakEndTime;
    }
}