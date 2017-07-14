package com.palprotech.ensyfi.adapter.teachermodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.teacher.viewlist.AcademicExamDetails;

import java.util.ArrayList;

/**
 * Created by Admin on 15-07-2017.
 */

public class AcademicExamDetailsListBaseAdapter extends BaseAdapter {

    ArrayList<AcademicExamDetails> myList = new ArrayList<AcademicExamDetails>();
    LayoutInflater inflater;
    Context context;
    int[] result;

    public AcademicExamDetailsListBaseAdapter(Context context, ArrayList<AcademicExamDetails> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public AcademicExamDetails getItem(int position) {
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
            convertView = inflater.inflate(R.layout.academic_exam_detail_vies_list_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        AcademicExamDetails currentListData = getItem(position);

        mViewHolder.txtSubDate.setText(currentListData.getExamDate());
        mViewHolder.txtSubTime.setText(currentListData.getTimes());
        mViewHolder.txtSubName.setText(currentListData.getSubjectName());

        return convertView;
    }

    private class MyViewHolder {

        TextView txtSubDate;
        TextView txtSubTime;
        TextView txtSubName;

        public MyViewHolder(View item) {
            txtSubDate = (TextView) item.findViewById(R.id.txtSubDate);
            txtSubTime = (TextView) item.findViewById(R.id.txtSubTime);
            txtSubName = (TextView) item.findViewById(R.id.txtSubName);
        }
    }
}
