package com.palprotech.ensyfi.adapter.general;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.general.viewlist.Circular;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by Admin on 08-07-2017.
 */

public class CircularListAdapter extends BaseAdapter {

    private final Transformation transformation;
    private Context context;
    private ArrayList<Circular> circulars;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    public CircularListAdapter(Context context, ArrayList<Circular> circulars) {
        this.context = context;
        this.circulars = circulars;

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
            return circulars.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return circulars.get(mValidSearchIndices.get(position));
        } else {
            return circulars.get(position);
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
            convertView = inflater.inflate(R.layout.circular_list_item, parent, false);

            holder = new CircularListAdapter.ViewHolder();
            holder.txtCircularType = (TextView) convertView.findViewById(R.id.txtCircularType);
            holder.txtCircularTitle = (TextView) convertView.findViewById(R.id.txtCircularTitle);
            holder.txtCircularDescription = (TextView) convertView.findViewById(R.id.txtCircularDescription);
            holder.txtCircularDate = (TextView) convertView.findViewById(R.id.txtCircularDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mSearching) {
            position = mValidSearchIndices.get(position);

        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }
        holder.txtCircularType.setText(circulars.get(position).getCircularType());
        holder.txtCircularTitle.setText(circulars.get(position).getCircularTitle());
        holder.txtCircularDescription.setText(circulars.get(position).getCircularDescription());
        holder.txtCircularDate.setText(circulars.get(position).getCircularDate());
        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < circulars.size(); i++) {
            String circularTitle = circulars.get(i).getCircularTitle();
            if ((circularTitle != null) && !(circularTitle.isEmpty())) {
                if (circularTitle.toLowerCase().contains(eventName.toLowerCase())) {
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
        public TextView txtCircularType, txtCircularTitle, txtCircularDescription, txtCircularDate;
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
