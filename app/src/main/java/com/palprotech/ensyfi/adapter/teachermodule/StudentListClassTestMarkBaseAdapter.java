package com.palprotech.ensyfi.adapter.teachermodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.teacher.viewlist.StudentsClassTestMarks;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Admin on 14-07-2017.
 */

public class StudentListClassTestMarkBaseAdapter extends BaseAdapter {

    ArrayList<StudentsClassTestMarks> myList = new ArrayList<StudentsClassTestMarks>();
    LayoutInflater inflater;
    Context context;
    private final String[] valueList;

    private HashMap<String, String> textValues = new HashMap<String, String>();

    public StudentListClassTestMarkBaseAdapter(Context context, ArrayList<StudentsClassTestMarks> myList) {
        this.myList = myList;
        this.context = context;
        valueList = new String[myList.size()];
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public StudentsClassTestMarks getItem(int position) {
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
            convertView = inflater.inflate(R.layout.students_class_test_mark_list_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        StudentsClassTestMarks currentListData = getItem(position);

        mViewHolder.txtStudentId.setText(currentListData.getEnrollId());
        mViewHolder.txtStudentName.setText(currentListData.getStudentName());
        mViewHolder.edtStudentMarks.setText("" + mViewHolder.defaultNumber);

        return convertView;
    }

    public String[] getValueList() {
        return valueList;
    }

    private class MyViewHolder {

        TextView txtStudentId;
        TextView txtStudentName;
        EditText edtStudentMarks;
        int defaultNumber = 0;

        public MyViewHolder(View item) {
            txtStudentId = (TextView) item.findViewById(R.id.txt_studentId);
            txtStudentName = (TextView) item.findViewById(R.id.txt_studentName);
            edtStudentMarks = (EditText) item.findViewById(R.id.class_test_marks);
        }
    }
}
