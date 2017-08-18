package com.palprotech.ensyfi.adapter.teachermodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.teacher.viewlist.Students;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Admin on 05-07-2017.
 */

public class StudentListBaseAdapter extends BaseAdapter {

    ArrayList<Students> myList = new ArrayList<Students>();
    LayoutInflater inflater;
    Context context;
    int[] result;

    Comparator<Students> myComparator = new Comparator<Students>() {
        public int compare(Students obj1, Students obj2) {
            return obj1.getEnrollId().compareTo(obj2.getEnrollId());
        }
    };

    public StudentListBaseAdapter(Context context, ArrayList<Students> myList) {
        this.myList = myList;
        this.context = context;
        Collections.sort(myList, myComparator);
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Students getItem(int position) {
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
            convertView = inflater.inflate(R.layout.students_list_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        Students currentListData = getItem(position);

        mViewHolder.txtStudentId.setText(currentListData.getEnrollId());
        mViewHolder.txtStudentName.setText(currentListData.getStudentName());

        return convertView;
    }

    private class MyViewHolder {

        TextView txtStudentId;
        TextView txtStudentName;
        TextView txtTablePrimaryKey;


        public MyViewHolder(View item) {
            txtStudentId = (TextView) item.findViewById(R.id.txt_studentId);
            txtStudentName = (TextView) item.findViewById(R.id.txt_studentName);
        }
    }
}
