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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.app.AppController;
import com.palprotech.ensyfi.bean.admin.viewlist.FeesStatus;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by Admin on 19-07-2017.
 */

public class FeesStatusListAdapter extends BaseAdapter {

    private static final String TAG = ClassStudentListAdapter.class.getName();
    private final Transformation transformation;
    private Context context;
    private ArrayList<FeesStatus> feesStatuses;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();
    private ImageLoader imageLoader = AppController.getInstance().getUniversalImageLoader();

    public FeesStatusListAdapter(Context context, ArrayList<FeesStatus> feesStatuses) {
        this.context = context;
        this.feesStatuses = feesStatuses;

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

        FeesStatus feesStatus = feesStatuses.get(position);

        holder.txtStudentName.setText(feesStatuses.get(position).getName());
        holder.txtStatus.setText(feesStatuses.get(position).getStatus());

        if((feesStatuses.get(position).getStatus()).contentEquals("Paid")){
            holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.approve));
        }
        else {
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
        public TextView txtStudentName, txtStatus;
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
