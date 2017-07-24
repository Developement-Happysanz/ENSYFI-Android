package com.palprotech.ensyfi.adapter.teachermodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.teachermodule.ClassTestHomeWorkTeacherViewActivity;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTestHomeWork;

import java.util.ArrayList;

/**
 * Created by Admin on 13-07-2017.
 */

public class ClassTestHomeWorkListBaseAdapter extends BaseAdapter {

    ArrayList<ClassTestHomeWork> myList = new ArrayList<ClassTestHomeWork>();
    LayoutInflater inflater;
    Context context;
    int[] result;

    public ClassTestHomeWorkListBaseAdapter(Context context, ArrayList<ClassTestHomeWork> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public ClassTestHomeWork getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (myList != null) {
            return myList.get(position).id;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.class_test_homework_list_view, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        ClassTestHomeWork currentListData = getItem(position);


        String checkClassTestType = currentListData.getHomeWorkType();
        String isTypeChecked = "";


        mViewHolder.txtClassTestTitle.setText(currentListData.getTitle());
        mViewHolder.txtClassTestSubject.setText(currentListData.getSubjectName());
        mViewHolder.txtClassTestType.setText("-  " + isTypeChecked);
        if (checkClassTestType.equalsIgnoreCase("HT")) {
            isTypeChecked = "Class Test";
            mViewHolder.txtClassTestDate.setText("Test Date : " + currentListData.getDueDate());
        } else {
            isTypeChecked = "Home Work";
            mViewHolder.txtClassTestDate.setText("Homework Submission Date : " + currentListData.getDueDate());
        }
        mViewHolder.txtClassTestHomeWorkId.setText("" + currentListData.getId());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text = (TextView) view.findViewById(R.id.txtClassTestHomeWorkId);
                String tEXT = text.getText().toString();
                ((ClassTestHomeWorkTeacherViewActivity) context).viewClassTestHomeWorkDetailPage(Long.valueOf(tEXT).longValue());
            }
        });

        return convertView;
    }

    private class MyViewHolder {

        TextView txtClassTestTitle;
        TextView txtClassTestSubject;
        TextView txtClassTestType;
        TextView txtClassTestDate;
        TextView txtClassTestHomeWorkId;

        public MyViewHolder(View item) {
            txtClassTestTitle = (TextView) item.findViewById(R.id.txtClassTestTitle);
            txtClassTestSubject = (TextView) item.findViewById(R.id.txtClassTestSubject);
            txtClassTestType = (TextView) item.findViewById(R.id.txtClassTestType);
            txtClassTestDate = (TextView) item.findViewById(R.id.txtClassTestDate);
            txtClassTestHomeWorkId = (TextView) item.findViewById(R.id.txtClassTestHomeWorkId);
        }
    }
}
