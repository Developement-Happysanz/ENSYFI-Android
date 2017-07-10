package com.palprotech.ensyfi.adapter.general;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.palprotech.ensyfi.R;
import com.squareup.picasso.Transformation;

import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.palprotech.ensyfi.app.AppController;
import com.palprotech.ensyfi.bean.general.viewlist.Circular;
import com.palprotech.ensyfi.bean.general.viewlist.OnDuty;

import java.util.ArrayList;

/**
 * Created by Admin on 10-07-2017.
 */

public class OnDutyListAdapter extends BaseAdapter {

    private static final String TAG = CircularListAdapter.class.getName();
    private final Transformation transformation;
    private Context context;
    private ArrayList<OnDuty> onDuty;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();
    private ImageLoader imageLoader = AppController.getInstance().getUniversalImageLoader();

    public OnDutyListAdapter(Context context, ArrayList<OnDuty> onDuty) {
        this.context = context;
        this.onDuty = onDuty;

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
            return onDuty.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return onDuty.get(mValidSearchIndices.get(position));
        } else {
            return onDuty.get(position);
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
            convertView = inflater.inflate(R.layout.on_duty_list_item, parent, false);

            holder = new OnDutyListAdapter.ViewHolder();
            holder.txtOdFor = (TextView) convertView.findViewById(R.id.txtOdFor);
            holder.txtFromDate = (TextView) convertView.findViewById(R.id.txtFromDate);
            holder.txtToDate = (TextView) convertView.findViewById(R.id.txtToDate);
            holder.txtNotes = (TextView) convertView.findViewById(R.id.txtNotes);
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

        OnDuty onDutys = onDuty.get(position);

        holder.txtOdFor.setText(onDuty.get(position).getOdFor());
        holder.txtFromDate.setText(onDuty.get(position).getFromDate());
        holder.txtToDate.setText(onDuty.get(position).getToDate());
        holder.txtNotes.setText(onDuty.get(position).getNotes());
        holder.txtStatus.setText(onDuty.get(position).getStatus());
        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < onDuty.size(); i++) {
            String onDutyTitle = onDuty.get(i).getOdFor();
            if ((onDutyTitle != null) && !(onDutyTitle.isEmpty())) {
                if (onDutyTitle.toLowerCase().contains(eventName.toLowerCase())) {
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
        public TextView txtOdFor, txtFromDate, txtToDate, txtNotes, txtStatus;
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
