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
import com.palprotech.ensyfi.bean.teacher.viewlist.SubstitutionClass;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SubstitutionClassListAdapter extends BaseAdapter {

    //    private final Transformation transformation;
    private Context context;
    LayoutInflater inflater;
    ArrayList<SubstitutionClass> teacherTimeTable = new ArrayList<SubstitutionClass>();;
    String isBreak = "";


    public SubstitutionClassListAdapter(Context context, ArrayList<SubstitutionClass> teacherTimeTable) {
        this.teacherTimeTable = teacherTimeTable;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return teacherTimeTable.size();

    }

    @Override
    public SubstitutionClass getItem(int position) {
        return teacherTimeTable.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SubstitutionClassListAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.substitution_list_item, parent, false);
            holder = new SubstitutionClassListAdapter.ViewHolder();
            holder.txtClassName = (TextView) convertView.findViewById(R.id.class_name);
            holder.txtPeriodNumber = (TextView) convertView.findViewById(R.id.period_number);
            holder.txtPeriodDate = (TextView) convertView.findViewById(R.id.period_start_time);

            convertView.setTag(holder);
        } else {
            holder = (SubstitutionClassListAdapter.ViewHolder) convertView.getTag();
        }

        SubstitutionClass currentListData = getItem(position);

        holder.txtPeriodNumber.setText(currentListData.getperiod());
        holder.txtClassName.setText(currentListData.getclass_sec_name());

        String endBre = currentListData.getsub_date();
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
        public TextView txtPeriodNumber, txtPeriodDate, txtClassName;
    }
}