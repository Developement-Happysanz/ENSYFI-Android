package com.palprotech.ensyfi.adapter.teachermodule;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.teacher.viewlist.StudentsClassTestMarks;

/**
 * Created by Admin on 07-08-2017.
 */

public class StudentClassTestMarkAddBaseAdapter extends BaseAdapter {

    Context context;
    String studentId[];
    String studentName[];
    LayoutInflater inflater;

    // Created String Array Here
    private final String[] valueList;

    public StudentClassTestMarkAddBaseAdapter(Context context, String studentId[], String studentName[]) {
        super();
        this.context = context;
        this.studentId = studentId;
        this.studentName = studentName;

        //initialization of array
        valueList = new String[studentId.length];
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return studentName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.students_class_test_mark_list_item, null);
            holder = new ViewHolder();
            holder.tvstudentid = (TextView) convertView.findViewById(R.id.txt_studentId);
            holder.tvstudentname = (TextView) convertView.findViewById(R.id.txt_studentName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        holder.student_mark = (EditText) convertView.findViewById(R.id.class_test_marks);

        holder.student_mark.setText("" + holder.defaultNumber);

        holder.tvstudentid.setText(studentId[position]);
        holder.tvstudentname.setText(studentName[position]);

        return null;
    }

    public class ViewHolder {
        TextView tvstudentid;
        TextView tvstudentname;
        EditText student_mark;
        int defaultNumber = 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 500;
    }

    // created method here
    public String[] getValueList() {
        return valueList;
    }
}
