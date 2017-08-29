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
import com.palprotech.ensyfi.bean.admin.viewlist.ParentStudent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//import com.makeramen.roundedimageview.RoundedTransformationBuilder;

/**
 * Created by Admin on 18-07-2017.
 */

public class ParentStudentListAdapter extends BaseAdapter {

    //    private final Transformation transformation;
    private Context context;
    private ArrayList<ParentStudent> parentStudents;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();
//    private ImageLoader imageLoader = AppController.getInstance().getUniversalImageLoader();

    Comparator<ParentStudent> myComparator = new Comparator<ParentStudent>() {
        public int compare(ParentStudent obj1, ParentStudent obj2) {
            return obj1.getStudentId().compareTo(obj2.getStudentId());
        }
    };

    public ParentStudentListAdapter(Context context, ArrayList<ParentStudent> parentStudents) {
        this.context = context;
        this.parentStudents = parentStudents;
        Collections.sort(parentStudents, myComparator);
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
            return parentStudents.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return parentStudents.get(mValidSearchIndices.get(position));
        } else {
            return parentStudents.get(position);
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
            convertView = inflater.inflate(R.layout.parent_student_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtClassStudentRegId = (TextView) convertView.findViewById(R.id.txtClassStudentRegId);
            holder.txtClassStudentName = (TextView) convertView.findViewById(R.id.txtClassStudentName);
            holder.txtParentName = (TextView) convertView.findViewById(R.id.txtParentName);
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
        holder.txtClassStudentName.setText(parentStudents.get(position).getName());
        holder.txtParentName.setText(parentStudents.get(position).getFatherName());

        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < parentStudents.size(); i++) {
            String classStudent = parentStudents.get(i).getName();
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
        public TextView txtClassStudentRegId, txtClassStudentName, txtParentName;
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
