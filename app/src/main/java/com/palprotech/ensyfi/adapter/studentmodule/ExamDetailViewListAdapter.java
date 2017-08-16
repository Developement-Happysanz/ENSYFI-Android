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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.app.AppController;
import com.palprotech.ensyfi.bean.student.viewlist.DayView;
import com.palprotech.ensyfi.bean.student.viewlist.ExamDetailsView;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Admin on 18-05-2017.
 */

public class ExamDetailViewListAdapter extends BaseAdapter {

    private final Transformation transformation;
    private Context context;
    private ArrayList<ExamDetailsView> examDetailsViews;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    Comparator<ExamDetailsView> myComparator = new Comparator<ExamDetailsView>() {
        public int compare(ExamDetailsView obj1, ExamDetailsView obj2) {
            return obj1.getExamDate().compareTo(obj2.getExamDate());
        }
    };

    public ExamDetailViewListAdapter(Context context, ArrayList<ExamDetailsView> examDetailsViews) {
        this.context = context;
        this.examDetailsViews = examDetailsViews;
        Collections.sort(examDetailsViews, myComparator);
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
            return examDetailsViews.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return examDetailsViews.get(mValidSearchIndices.get(position));
        } else {
            return examDetailsViews.get(position);
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
            convertView = inflater.inflate(R.layout.exam_detail_view_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtExamDate = (TextView) convertView.findViewById(R.id.txtSubDate);
            holder.txtSub = (TextView) convertView.findViewById(R.id.txtSubName);
            holder.txtTime = (TextView) convertView.findViewById(R.id.txtSubTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        holder.txtSub.setText(examDetailsViews.get(position).getSubjectName());
        holder.txtExamDate.setText(examDetailsViews.get(position).getExamDate());
        holder.txtTime.setText(examDetailsViews.get(position).getTimes());


        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < examDetailsViews.size(); i++) {
            String homeWorkTitle = examDetailsViews.get(i).getSubjectName();
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
        public TextView txtExamName, txtExamDate, txtSub, txtTime;
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
