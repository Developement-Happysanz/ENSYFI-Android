package com.palprotech.ensyfi.adapter.teachermodule;

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
import com.palprotech.ensyfi.adapter.adminmodule.ClassStudentListAdapter;
import com.palprotech.ensyfi.app.AppController;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTestMark;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by Admin on 19-07-2017.
 */

public class ClassTestMarkListAdapter extends BaseAdapter{

    private static final String TAG = ClassStudentListAdapter.class.getName();
    private final Transformation transformation;
    private Context context;
    private ArrayList<ClassTestMark> classTestMarks;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();
    private ImageLoader imageLoader = AppController.getInstance().getUniversalImageLoader();

    public ClassTestMarkListAdapter(Context context, ArrayList<ClassTestMark> classTestMarks) {
        this.context = context;
        this.classTestMarks = classTestMarks;

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
            return classTestMarks.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return classTestMarks.get(mValidSearchIndices.get(position));
        } else {
            return classTestMarks.get(position);
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
            convertView = inflater.inflate(R.layout.class_test_mark_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtStudentName = (TextView) convertView.findViewById(R.id.txtStudentName);
            holder.txtStudentMark = (TextView) convertView.findViewById(R.id.txtStudentMark);

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

        ClassTestMark classTestMark = classTestMarks.get(position);

        holder.txtStudentName.setText(classTestMarks.get(position).getName());
        holder.txtStudentMark.setText(classTestMarks.get(position).getMarks());

        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < classTestMarks.size(); i++) {
            String classStudent = classTestMarks.get(i).getName();
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
        public TextView txtStudentName, txtStudentMark;
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
