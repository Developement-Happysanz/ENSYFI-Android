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
import com.palprotech.ensyfi.app.AppController;
import com.palprotech.ensyfi.bean.admin.viewlist.ClassStudent;
import com.palprotech.ensyfi.bean.admin.viewlist.TeacherView;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Admin on 18-07-2017.
 */

public class TeacherViewListAdapter extends BaseAdapter {

    private final Transformation transformation;
    private Context context;
    private ArrayList<TeacherView> teacherViews;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    Comparator<TeacherView> myComparator = new Comparator<TeacherView>() {
        public int compare(TeacherView obj1, TeacherView obj2) {
            return obj1.getTeacherId().compareTo(obj2.getTeacherId());
        }
    };

    public TeacherViewListAdapter(Context context, ArrayList<TeacherView> teacherViews) {
        this.context = context;
        this.teacherViews = teacherViews;
        Collections.sort(teacherViews, myComparator);
        transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(0)
                .oval(false)
                .build();
        mSearching = false;
    }

    @Override
    public int getCount() {
        if (mSearching) {
            if (!mAnimateSearch) {
                mAnimateSearch = true;
            }
            return mValidSearchIndices.size();
        } else {
            return teacherViews.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return teacherViews.get(mValidSearchIndices.get(position));
        } else {
            return teacherViews.get(position);
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
            convertView = inflater.inflate(R.layout.teachers_view_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtTeacherId = (TextView) convertView.findViewById(R.id.txtTeacherId);
            holder.txtTeacherName = (TextView) convertView.findViewById(R.id.txtTeacherName);
            holder.txtTeacherMainSubject = (TextView) convertView.findViewById(R.id.txtTeacherMainSubject);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }
        holder.txtTeacherId.setText(teacherViews.get(position).getTeacherId());
        holder.txtTeacherName.setText(teacherViews.get(position).getName());
        holder.txtTeacherMainSubject.setText(teacherViews.get(position).getSubjectName());

        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < teacherViews.size(); i++) {
            String classStudent = teacherViews.get(i).getName();
            if ((classStudent != null) && !(classStudent.isEmpty())) {
                if (classStudent.toLowerCase().contains(eventName.toLowerCase())) {
                    mValidSearchIndices.add(i);
                }
            }
        }
        Log.d("Event List Adapter", "notify" + mValidSearchIndices.size());
    }

    public void exitSearch() {
        mSearching = false;
        mValidSearchIndices.clear();
        mAnimateSearch = false;
    }

    public void clearSearchFlag() {
        mSearching = false;
    }

    public class ViewHolder {
        public TextView txtTeacherId, txtTeacherName, txtTeacherMainSubject;
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
