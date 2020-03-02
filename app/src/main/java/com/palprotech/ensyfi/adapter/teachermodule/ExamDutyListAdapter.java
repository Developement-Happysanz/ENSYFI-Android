package com.palprotech.ensyfi.adapter.teachermodule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.teacher.viewlist.ExamDuty;

import java.util.ArrayList;

public class ExamDutyListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ExamDuty> examDuties;
    private boolean mSearching = false;
    private boolean mAnimationSearch = false;
    private ArrayList<Integer> mValidateSearchIndices = new ArrayList<Integer>();

    public ExamDutyListAdapter(Context context, ArrayList<ExamDuty> examDuties) {
        this.context = context;
        this.examDuties = examDuties;

        mSearching = false;
    }

    @Override
    public int getCount() {
        if (mSearching) {
            if (!mAnimationSearch) {
                mAnimationSearch = true;
            }
            return mValidateSearchIndices.size();
        } else {
            return examDuties.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return examDuties.get(mValidateSearchIndices.get(position));
        } else {
            return examDuties.get(position);
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
            convertView = inflater.inflate(R.layout.exam_duty_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtExamName = (TextView) convertView.findViewById(R.id.txtExamName);
            holder.txtExamDateTime = (TextView) convertView.findViewById(R.id.txtExamDateTime);
            holder.txtClassSection = (TextView) convertView.findViewById(R.id.txtDutyClass);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidateSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        holder.txtExamName.setText(examDuties.get(position).getExam_name());
        holder.txtExamDateTime.setText(examDuties.get(position).getExam_datetime());
        holder.txtClassSection.setText(examDuties.get(position).getClass_datetime());

        return convertView;
    }

    public class ViewHolder {
        public TextView txtExamName, txtSubjectName, txtExamDateTime, txtClassSection;
    }

    public void startSearch(String examDuty) {
        mSearching = true;
        mAnimationSearch = false;
        Log.d("ExamDutiesListAdapter", "search for exam duty" + examDuty);
        mValidateSearchIndices.clear();
        for (int i = 0; i < examDuties.size(); i++) {
            String examDutyTitle = examDuties.get(i).getExam_name();
            if ((examDutyTitle != null) && !(examDutyTitle.isEmpty())) {
                if (examDutyTitle.toLowerCase().contains(examDuty.toLowerCase())) {
                    mValidateSearchIndices.add(i);
                }
            }
        }
        Log.d("Exam duty list adapter", "notify" + mValidateSearchIndices.size());
    }

    public void exitSearch() {
        mSearching = false;
        mValidateSearchIndices.clear();
        mAnimationSearch = false;
    }

    public void clearSearchFlag(){
        mSearching = false;
    }

    public boolean ismSearching(){
        return mSearching;
    }

    public int getActualExamPos(int selaectSearchPos){
        if(selaectSearchPos < mValidateSearchIndices.size()){
            return mValidateSearchIndices.get(selaectSearchPos);
        } else {
            return 0;
        }
    }
}
