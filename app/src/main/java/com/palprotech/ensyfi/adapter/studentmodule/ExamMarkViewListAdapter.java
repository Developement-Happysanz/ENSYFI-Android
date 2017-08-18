package com.palprotech.ensyfi.adapter.studentmodule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.student.viewlist.ExamMark;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by Admin on 25-05-2017.
 */

public class ExamMarkViewListAdapter extends BaseAdapter {

    private final Transformation transformation;
    private Context context;
    private ArrayList<ExamMark> examMarks;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    public ExamMarkViewListAdapter(Context context, ArrayList<ExamMark> examMarks) {
        this.context = context;
        this.examMarks = examMarks;

        transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(0)
                .oval(false)
                .build();
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
            return examMarks.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return examMarks.get(mValidSearchIndices.get(position));
        } else {
            return examMarks.get(position);
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
            convertView = inflater.inflate(R.layout.exam_mark_view_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtExamSubject = (TextView) convertView.findViewById(R.id.txtExamSubject);
            holder.txtInternalMark = (TextView) convertView.findViewById(R.id.txtIntMark);
            holder.TxtInternalGrade = (TextView) convertView.findViewById(R.id.txtIntGrade);
            holder.txtExternalMark = (TextView) convertView.findViewById(R.id.txtExtMark);
            holder.TxtExternalGrade = (TextView) convertView.findViewById(R.id.txtExtGrade);
            holder.txtSubjectTotalMark = (TextView) convertView.findViewById(R.id.txtSubTotalMark);
            holder.txtSubjectTotalGrade = (TextView) convertView.findViewById(R.id.txtSubTotalGrade);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        holder.txtExamSubject.setText(examMarks.get(position).getSubjectName());
        holder.txtInternalMark.setText(examMarks.get(position).getInternalMark());
        holder.TxtInternalGrade.setText(examMarks.get(position).getInternalGrade());
        holder.txtExternalMark.setText(examMarks.get(position).getExternalMark());
        holder.TxtExternalGrade.setText(examMarks.get(position).getExternalGrade());
        holder.txtSubjectTotalMark.setText(examMarks.get(position).getTotalMarks());
        holder.txtSubjectTotalGrade.setText(examMarks.get(position).getTotalGrade());

        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < examMarks.size(); i++) {
            String homeWorkTitle = examMarks.get(i).getSubjectName();
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
        public TextView txtExamName, txtExamSubject, txtSubjectTotalGrade, txtSubjectTotalMark, txtInternalMark, TxtInternalGrade, txtExternalMark, TxtExternalGrade;
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
