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
import com.palprotech.ensyfi.bean.admin.viewlist.Fees;
import com.palprotech.ensyfi.bean.student.viewlist.DayView;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Admin on 19-07-2017.
 */

public class FeesListAdapter extends BaseAdapter {

    private final Transformation transformation;
    private Context context;
    private ArrayList<Fees> fees;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    Comparator<Fees> myComparator = new Comparator<Fees>() {
        public int compare(Fees obj1, Fees obj2) {
            return obj1.getFeesId().compareTo(obj2.getFeesId());
        }
    };

    public FeesListAdapter(Context context, ArrayList<Fees> fees) {
        this.context = context;
        this.fees = fees;
        Collections.sort(fees, myComparator);
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
            return fees.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return fees.get(mValidSearchIndices.get(position));
        } else {
            return fees.get(position);
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
            convertView = inflater.inflate(R.layout.fees_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtTermName = (TextView) convertView.findViewById(R.id.txtTermName);
            holder.txtDueDateFrom = (TextView) convertView.findViewById(R.id.txtDueDateFrom);
            holder.txtDueDateTo = (TextView) convertView.findViewById(R.id.txtDueDateTo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }
        holder.txtTermName.setText(fees.get(position).getTermName());
        holder.txtDueDateFrom.setText(fees.get(position).getDueDateFrom());
        holder.txtDueDateTo.setText(fees.get(position).getDueDateTo());

        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < fees.size(); i++) {
            String classStudent = fees.get(i).getTermName();
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
        public TextView txtTermName, txtDueDateFrom, txtDueDateTo;
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
