package com.palprotech.ensyfi.adapter.teachermodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.teachermodule.AcademicExamViewActivity;
import com.palprotech.ensyfi.bean.teacher.viewlist.AcademicExams;

import java.util.ArrayList;

/**
 * Created by Admin on 15-07-2017.
 */

public class AcademicExamsListBaseAdapter extends BaseAdapter {

    ArrayList<AcademicExams> myList = new ArrayList<AcademicExams>();
    LayoutInflater inflater;
    Context context;
    int[] result;

    public AcademicExamsListBaseAdapter(Context context, ArrayList<AcademicExams> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public AcademicExams getItem(int position) {
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
            convertView = inflater.inflate(R.layout.academic_exams_list_view, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        AcademicExams currentListData = getItem(position);

        mViewHolder.txtExamName.setText(currentListData.getExamName());
        mViewHolder.txtFromDate.setText(currentListData.getFromDate());
        mViewHolder.txtToDate.setText(currentListData.getToDate());
        mViewHolder.txtExamIdLocal.setText("" + currentListData.getId());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text = (TextView) view.findViewById(R.id.txtExamIdLocal);
                String tEXT = text.getText().toString();
                ((AcademicExamViewActivity) context).viewAcademicExamsDetailPage(Long.valueOf(tEXT).longValue());
            }
        });

        return convertView;
    }

    private class MyViewHolder {

        TextView txtExamName;
        TextView txtFromDate;
        TextView txtToDate;
        TextView txtExamIdLocal;


        public MyViewHolder(View item) {
            txtExamName = (TextView) item.findViewById(R.id.txtExamName);
            txtFromDate = (TextView) item.findViewById(R.id.txtFromDate);
            txtToDate = (TextView) item.findViewById(R.id.txtToDate);
            txtExamIdLocal = (TextView) item.findViewById(R.id.txtExamIdLocal);
        }
    }
}
