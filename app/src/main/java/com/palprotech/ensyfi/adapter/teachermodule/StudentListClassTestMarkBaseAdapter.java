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

/**
 * Created by Admin on 14-07-2017.
 */

public class StudentListClassTestMarkBaseAdapter extends BaseAdapter {

    ArrayList<StudentsClassTestMarks> myList = new ArrayList<StudentsClassTestMarks>();
    LayoutInflater inflater;
    Context context;
    int[] result;

    public StudentListClassTestMarkBaseAdapter(Context context, ArrayList<StudentsClassTestMarks> myList) {
        this.myList = myList;
        this.context = context;
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
            mViewHolder.edtStudentMarks = (EditText)convertView.findViewById(R.id.class_test_marks);
//            mViewHolder.edtStudentMarks.addTextChangedListener(new MeuTextWatcher(mViewHolder.edtStudentMarks));

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        StudentsClassTestMarks currentListData = getItem(position);

        mViewHolder.txtStudentId.setText(currentListData.getEnrollId());
        mViewHolder.txtStudentName.setText(currentListData.getStudentName());


        return convertView;
    }

    private class MyViewHolder {

        TextView txtStudentId;
        TextView txtStudentName;
        TextView txtTablePrimaryKey;
        EditText edtStudentMarks;


        public MyViewHolder(View item) {
            txtStudentId = (TextView) item.findViewById(R.id.txt_studentId);
            txtStudentName = (TextView) item.findViewById(R.id.txt_studentName);


        }
    }
}
