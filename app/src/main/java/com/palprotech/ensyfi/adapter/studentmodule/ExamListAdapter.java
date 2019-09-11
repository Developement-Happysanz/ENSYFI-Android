package com.palprotech.ensyfi.adapter.studentmodule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.student.viewlist.Exams;
import com.palprotech.ensyfi.bean.teacher.viewlist.AcademicExams;
import com.squareup.picasso.Transformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Admin on 17-05-2017.
 */

public class ExamListAdapter extends BaseAdapter {

    //    private final Transformation transformation;
    private Context context;
    private ArrayList<Exams> exams;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();
    ViewHolder holder;

    public ExamListAdapter(Context context, ArrayList<Exams> exams) {
        this.context = context;
        this.exams = exams;

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
            return exams.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return exams.get(mValidSearchIndices.get(position));
        } else {
            return exams.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.exam_list_item, parent, false);

            holder = new ViewHolder();

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        holder.txtExamName = (TextView) convertView.findViewById(R.id.txtExamName);
        holder.txtStartDate = (TextView) convertView.findViewById(R.id.txtFromDate);
        holder.txtEndDate = (TextView) convertView.findViewById(R.id.txtToDate);
        holder.tstStatus = (TextView) convertView.findViewById(R.id.status);
        holder.status = (ImageView) convertView.findViewById(R.id.imgStatus);

        holder.txtExamName.setText(exams.get(position).getExamName());
        holder.txtStartDate.setText(exams.get(position).getFromDate());
        holder.txtEndDate.setText(exams.get(position).getToDate());

        viewAcademicExamsDetailPage(exams.get(position));
        return convertView;
    }
    public void viewAcademicExamsDetailPage(Exams id) {
//        Date Date2 = new Date();
        Date Date2  = null;
        String toDate = id.getToDate();
//        Date date1 = new Date();
        Date date12 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");
        if (!toDate.isEmpty()) {
            try {
                Date2 = sdf.parse(toDate);
                date12 = sdf.parse(sdf.format(new Date() ));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if ((Date2.compareTo(date12) > 0) || (Date2.compareTo(date12) == 0)) {
                holder.tstStatus.setVisibility(View.GONE);
                holder.status.setVisibility(View.GONE);
            }
            else {
                holder.tstStatus.setVisibility(View.VISIBLE);
                holder.status.setVisibility(View.VISIBLE);
            }
        } else {

        }

    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < exams.size(); i++) {
            String homeWorkTitle = exams.get(i).getExamName();
            if ((homeWorkTitle != null) && !(homeWorkTitle.isEmpty())) {
                if (homeWorkTitle.toLowerCase().contains(eventName.toLowerCase())) {
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
        public TextView txtExamName, txtEndDate, txtStartDate;
        TextView tstStatus;
        ImageView status;
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
