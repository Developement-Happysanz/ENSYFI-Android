package com.palprotech.ensyfi.adapter.adminmodule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.studentmodule.ClassTestListAdapter;
import com.palprotech.ensyfi.app.AppController;
import com.palprotech.ensyfi.bean.admin.viewlist.ClassStudent;
import com.palprotech.ensyfi.bean.student.viewlist.ClassTest;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by Admin on 17-07-2017.
 */

public class ClassStudentListAdapter extends BaseAdapter {

    private static final String TAG = ClassStudentListAdapter.class.getName();
    private final Transformation transformation;
    private Context context;
    private ArrayList<ClassStudent> classStudents;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();
    private ImageLoader imageLoader = AppController.getInstance().getUniversalImageLoader();

    public ClassStudentListAdapter(Context context, ArrayList<ClassStudent> classStudents) {
        this.context = context;
        this.classStudents = classStudents;

        transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(0)
                .oval(false)
                .build();
        mSearching = false;
    }

    @Override
    public int getCount() {
        if (mSearching) {
            // Log.d("Event List Adapter","Search count"+mValidSearchIndices.size());
            if (!mAnimateSearch) {
                mAnimateSearch = true;
            }
            return mValidSearchIndices.size();

        } else {
            // Log.d(TAG,"Normal count size");
            return classStudents.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return classStudents.get(mValidSearchIndices.get(position));
        } else {
            return classStudents.get(position);
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
            convertView = inflater.inflate(R.layout.class_student_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtClassStudentRegId = (TextView) convertView.findViewById(R.id.txtClassStudentRegId);
            holder.txtClassStudentName = (TextView) convertView.findViewById(R.id.txtClassStudentName);
            holder.txtClassStudentAdmnNo = (TextView) convertView.findViewById(R.id.txtClassStudentAdmnNo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            // Log.d("Event List Adapter","actual position"+ position);
            position = mValidSearchIndices.get(position);
            //Log.d("Event List Adapter", "position is"+ position);

        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        ClassStudent classStudent = classStudents.get(position);

        holder.txtClassStudentRegId.setText(classStudents.get(position).getEnrollId());
        holder.txtClassStudentName.setText(classStudents.get(position).getName());
        holder.txtClassStudentAdmnNo.setText(classStudents.get(position).getAdmisnNo());

        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < classStudents.size(); i++) {
            String classStudent = classStudents.get(i).getName();
            if ((classStudent != null) && !(classStudent.isEmpty())) {
                if (classStudent.toLowerCase().contains(eventName.toLowerCase())) {
                    mValidSearchIndices.add(i);
                }

            }

        }
        Log.d("Event List Adapter", "notify" + mValidSearchIndices.size());
        //notifyDataSetChanged();
    }

    public void exitSearch() {
        mSearching = false;
        mValidSearchIndices.clear();
        mAnimateSearch = false;
        // notifyDataSetChanged();
    }

    public void clearSearchFlag() {
        mSearching = false;
    }

    public class ViewHolder {
        public TextView txtClassStudentRegId, txtClassStudentName, txtClassStudentAdmnNo;
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
