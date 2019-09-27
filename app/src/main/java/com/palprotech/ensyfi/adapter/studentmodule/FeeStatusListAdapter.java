package com.palprotech.ensyfi.adapter.studentmodule;

import android.app.Activity;
import android.content.Context;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.student.viewlist.FeeStatus;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by Admin on 08-07-2017.
 */

public class FeeStatusListAdapter extends BaseAdapter {

//    private final Transformation transformation;
    private Context context;
    private ArrayList<FeeStatus> feeStatuses;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    public FeeStatusListAdapter(Context context, ArrayList<FeeStatus> feeStatuses) {
        this.context = context;
        this.feeStatuses = feeStatuses;

//        transformation = new RoundedTransformationBuilder()
//                .cornerRadiusDp(0)
//                .oval(false)
//                .build();
        mSearching = false;
    }

    @Override
    public int getCount() {
        if (mSearching) {
            if (!mAnimateSearch) {
                mAnimateSearch = true;            }
            return mValidSearchIndices.size();

        } else {
            return feeStatuses.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return feeStatuses.get(mValidSearchIndices.get(position));
        } else {
            return feeStatuses.get(position);
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
            convertView = inflater.inflate(R.layout.fee_status_list_item, parent, false);

            holder = new FeeStatusListAdapter.ViewHolder();
            holder.txtTermName = (TextView) convertView.findViewById(R.id.txtTermName);
            holder.txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);
            holder.txtDueDateFrom = (TextView) convertView.findViewById(R.id.txtDueDateFrom);
            holder.txtDueDateTo = (TextView) convertView.findViewById(R.id.txtDueDateTo);
            holder.statusBg = (RelativeLayout) convertView.findViewById(R.id.status_color);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        holder.txtTermName.setText(feeStatuses.get(position).getTermName());
        holder.txtStatus.setText(feeStatuses.get(position).getStatus());
        holder.txtDueDateFrom.setText("Due From : " + feeStatuses.get(position).getDueDateFrom());
        holder.txtDueDateTo.setText("Due To : " + feeStatuses.get(position).getDueDateTo());

        if ((feeStatuses.get(position).getStatus()).contentEquals("Paid")) {
            holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.approve));
            holder.statusBg.setBackgroundColor(ContextCompat.getColor(context, R.color.approve));
        } else {
            holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.reject));
            holder.statusBg.setBackgroundColor(ContextCompat.getColor(context, R.color.reject));
        }
        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < feeStatuses.size(); i++) {
            String feeStatusTitle = feeStatuses.get(i).getTermName();
            if ((feeStatusTitle != null) && !(feeStatusTitle.isEmpty())) {
                if (feeStatusTitle.toLowerCase().contains(eventName.toLowerCase())) {
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
        public TextView txtTermName, txtStatus, txtDueDateFrom, txtDueDateTo;
        private RelativeLayout statusBg;
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
