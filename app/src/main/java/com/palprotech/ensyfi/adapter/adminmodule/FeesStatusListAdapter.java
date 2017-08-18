package com.palprotech.ensyfi.adapter.adminmodule;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.admin.viewlist.FeesStatus;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Admin on 19-07-2017.
 */

public class FeesStatusListAdapter extends BaseAdapter {

    private final Transformation transformation;
    private Context context;
    private ArrayList<FeesStatus> feesStatuses;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    Comparator<FeesStatus> myComparator = new Comparator<FeesStatus>() {
        public int compare(FeesStatus obj1, FeesStatus obj2) {
            return obj1.getId().compareTo(obj2.getId());
        }
    };

    public FeesStatusListAdapter(Context context, ArrayList<FeesStatus> feesStatuses) {
        this.context = context;
        this.feesStatuses = feesStatuses;
        Collections.sort(feesStatuses, myComparator);
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
            return feesStatuses.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return feesStatuses.get(mValidSearchIndices.get(position));
        } else {
            return feesStatuses.get(position);
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
            convertView = inflater.inflate(R.layout.fees_status_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtStudentName = (TextView) convertView.findViewById(R.id.txtStudentName);
            holder.txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);
            holder.txtDueDateFrom = (TextView) convertView.findViewById(R.id.txtDueDateFrom);
            holder.txtQuotaName = (TextView) convertView.findViewById(R.id.txtQuotaName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        FeesStatus feesStatus = feesStatuses.get(position);

        holder.txtStudentName.setText(feesStatuses.get(position).getName());
        holder.txtStatus.setText(feesStatuses.get(position).getStatus());
        holder.txtDueDateFrom.setText("Paid At : " + feesStatuses.get(position).getUpdatedAt());
        holder.txtQuotaName.setText("Quota : " + feesStatuses.get(position).getQuotaName());

        if ((feesStatuses.get(position).getStatus()).contentEquals("Paid")) {
            holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.approve));
        } else {
            holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.reject));
        }

        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < feesStatuses.size(); i++) {
            String classStudent = feesStatuses.get(i).getName();
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
        public TextView txtStudentName, txtStatus, txtDueDateFrom, txtQuotaName;
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
