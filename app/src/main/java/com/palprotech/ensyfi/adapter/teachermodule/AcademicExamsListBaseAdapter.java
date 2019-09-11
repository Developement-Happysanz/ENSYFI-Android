package com.palprotech.ensyfi.adapter.teachermodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.teachermodule.AcademicExamViewActivity;
import com.palprotech.ensyfi.bean.teacher.viewlist.AcademicExams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Admin on 15-07-2017.
 */

public class AcademicExamsListBaseAdapter extends BaseAdapter {

    ArrayList<AcademicExams> myList = new ArrayList<AcademicExams>();
    LayoutInflater inflater;
    Context context;
    int[] result;
    MyViewHolder mViewHolder;

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

        viewAcademicExamsDetailPage(currentListData);

        return convertView;
    }

    public void viewAcademicExamsDetailPage(AcademicExams id) {
//        Date Date2 = new Date();
        Date Date2  = null;
        String toDate = id.getToDate();
//        Date date1 = new Date();
        Date date12 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");
        if (!toDate.isEmpty()) {
            try {
                Date2 = sdf.parse(toDate);
                date12 = sdf.parse(sdf.format(new Date() ));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if ((Date2.compareTo(date12) > 0) || (Date2.compareTo(date12) == 0)) {
            mViewHolder.tstStatus.setVisibility(View.GONE);
            mViewHolder.status.setVisibility(View.GONE);
        }
        else {
            mViewHolder.tstStatus.setVisibility(View.VISIBLE);
            mViewHolder.status.setVisibility(View.VISIBLE);
        }
    }

    private class MyViewHolder {

        TextView txtExamName;
        TextView txtFromDate;
        TextView txtToDate;
        TextView txtExamIdLocal;
        TextView tstStatus;
        ImageView status;


        public MyViewHolder(View item) {
            txtExamName = (TextView) item.findViewById(R.id.txtExamName);
            txtFromDate = (TextView) item.findViewById(R.id.txtFromDate);
            txtToDate = (TextView) item.findViewById(R.id.txtToDate);
            txtExamIdLocal = (TextView) item.findViewById(R.id.txtExamIdLocal);
            tstStatus = (TextView) item.findViewById(R.id.status);
            status = (ImageView) item.findViewById(R.id.imgStatus);
        }
    }
}
