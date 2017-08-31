package com.palprotech.ensyfi.adapter.adminmodule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.admin.viewlist.ClassStudent;

import java.util.ArrayList;

//import com.makeramen.roundedimageview.RoundedTransformationBuilder;

/**
 * Created by Admin on 17-07-2017.
 */

public class ClassStudentListAdapter extends BaseAdapter {

//    private final Transformation transformation;
    private Context context;
    private ArrayList<ClassStudent> classStudents;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

//    Comparator<ClassStudent> myComparator = new Comparator<ClassStudent>() {
//        public int compare(ClassStudent obj1, ClassStudent obj2) {
//            return obj1.getEnrollId().compareTo(obj2.getEnrollId());
//        }
//    };

    public ClassStudentListAdapter(Context context, ArrayList<ClassStudent> classStudents) {
        this.context = context;
        this.classStudents = classStudents;
//        Collections.sort(classStudents, myComparator);
//        transformation = new RoundedTransformationBuilder().cornerRadiusDp(0).oval(false).build();
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
        int i = 1;

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
            position = mValidSearchIndices.get(position);

        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        holder.txtClassStudentRegId.setText(""+(position+1));
        holder.txtClassStudentName.setText(classStudents.get(position).getName());
        holder.txtClassStudentAdmnNo.setText(classStudents.get(position).getAdmisnNo());

        i++;
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
