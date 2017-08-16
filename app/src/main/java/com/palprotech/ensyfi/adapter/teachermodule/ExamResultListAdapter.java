package com.palprotech.ensyfi.adapter.teachermodule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.adminmodule.ClassStudentListAdapter;
import com.palprotech.ensyfi.app.AppController;
import com.palprotech.ensyfi.bean.teacher.viewlist.ExamResult;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Admin on 19-07-2017.
 */

public class ExamResultListAdapter extends BaseAdapter {

    private final Transformation transformation;
    private Context context;
    private ArrayList<ExamResult> examResults;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    Comparator<ExamResult> myComparator = new Comparator<ExamResult>() {
        public int compare(ExamResult obj1, ExamResult obj2) {
            return obj1.getName().compareTo(obj2.getName());
        }
    };

    public ExamResultListAdapter(Context context, ArrayList<ExamResult> examResults) {
        this.context = context;
        this.examResults = examResults;
        Collections.sort(examResults, myComparator);

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
            return examResults.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return examResults.get(mValidSearchIndices.get(position));
        } else {
            return examResults.get(position);
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
            convertView = inflater.inflate(R.layout.academic_exam_result_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtStudentName = (TextView) convertView.findViewById(R.id.txtStudentName);
            holder.txtInternalMark = (TextView) convertView.findViewById(R.id.txtInternalMark);
            holder.txtExternalMark = (TextView) convertView.findViewById(R.id.txtExternalMark);
            holder.txtInternalGrade = (TextView) convertView.findViewById(R.id.txtIntGrade);
            holder.txtExternalGrade = (TextView) convertView.findViewById(R.id.txtExtGrade);
            holder.txtTotalMark = (TextView) convertView.findViewById(R.id.txtSubTotalMark);
            holder.txtTotalGrade = (TextView) convertView.findViewById(R.id.txtSubTotalGrade);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        holder.txtStudentName.setText(examResults.get(position).getName());
        holder.txtInternalMark.setText(examResults.get(position).getInternalMark());
        holder.txtInternalGrade.setText(examResults.get(position).getInternalGrade());
        holder.txtExternalMark.setText(examResults.get(position).getExternalMark());
        holder.txtExternalGrade.setText(examResults.get(position).getExternalGrade());
        holder.txtTotalMark.setText(examResults.get(position).getTotalMarks());
        holder.txtTotalGrade.setText(examResults.get(position).getTotalGrade());

        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < examResults.size(); i++) {
            String classStudent = examResults.get(i).getName();
            if ((classStudent != null) && !(classStudent.isEmpty())) {
                if (classStudent.toLowerCase().contains(eventName.toLowerCase())) {
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
        public TextView txtStudentName, txtInternalMark, txtExternalMark, txtInternalGrade,
                txtExternalGrade, txtTotalMark, txtTotalGrade;
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
