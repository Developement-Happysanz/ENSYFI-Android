package com.palprotech.ensyfi.adapter.teachermodule;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
//        for (int i = 0; i < 20; i++) {
//            ListItem listItem = new ListItem();
//            listItem.caption = "Caption" + i;
//            this.myList.add(listItem);
//        }
//        notifyDataSetChanged();
    }

//    public MyAdapter() {
//        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        for (int i = 0; i < 20; i++) {
//            ListItem listItem = new ListItem();
//            listItem.caption = "Caption" + i;
//            myItems.add(listItem);
//        }
//        notifyDataSetChanged();
//    }


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

        mViewHolder.edtStudentMarks.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                String text = editable.toString();
//                ARR[holder.getPosition()] = text;
//                Log.e("Watcher > ", holder.getPosition()+"> "+ ARR[holder.getPosition()] );
            }
        });

        StudentsClassTestMarks currentListData = getItem(position);

        mViewHolder.txtStudentId.setText(currentListData.getEnrollId());
        mViewHolder.txtStudentName.setText(currentListData.getStudentName());


        return convertView;
    }

    class ListItem {
        String caption;
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
