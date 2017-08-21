package com.palprotech.ensyfi.adapter.studentmodule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.student.viewlist.ClassTest;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by Admin on 15-05-2017.
 */

public class ClassTestListAdapter extends BaseAdapter {

//    private final Transformation transformation;
    private Context context;
    private ArrayList<ClassTest> classTests;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    public ClassTestListAdapter(Context context, ArrayList<ClassTest> classTests) {
        this.context = context;
        this.classTests = classTests;

//        transformation = new RoundedTransformationBuilder()
//                .cornerRadiusDp(0)
//                .oval(false)
//                .build();
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
            return classTests.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return classTests.get(mValidSearchIndices.get(position));
        } else {
            return classTests.get(position);
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
            convertView = inflater.inflate(R.layout.class_test_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtClassTestTitle = (TextView) convertView.findViewById(R.id.txtClassTestTitle);
            holder.txtClassTestSubject = (TextView) convertView.findViewById(R.id.txtClassTestSubject);
            holder.txtClassTestDate = (TextView) convertView.findViewById(R.id.txtClassTestDate);
            holder.txtClassTestType = (TextView) convertView.findViewById(R.id.txtClassTestType);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);

        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        String checkClassTestType = classTests.get(position).getHwType();
        String isTypeChecked = "";

        if (checkClassTestType.equalsIgnoreCase("HT")) {
            isTypeChecked = "Class Test";
            holder.txtClassTestDate.setText("Test Date : " + classTests.get(position).getHwTestDate());
        } else {
            isTypeChecked = "Home Work";
            holder.txtClassTestDate.setText("Due Date : " + classTests.get(position).getHwDueDate());
        }


        holder.txtClassTestTitle.setText(classTests.get(position).getHwTitle());
        holder.txtClassTestSubject.setText(classTests.get(position).getHwSubjectName());

        holder.txtClassTestType.setText("-  " + isTypeChecked);

        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < classTests.size(); i++) {
            String homeWorkTitle = classTests.get(i).getHwTitle();
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
        public TextView txtClassTestTitle, txtClassTestSubject, txtClassTestDate, txtClassTestType;
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
