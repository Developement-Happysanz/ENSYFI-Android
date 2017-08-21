package com.palprotech.ensyfi.adapter.studentmodule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.general.viewlist.Communication;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by Admin on 18-05-2017.
 */

public class CommunicationListAdapter extends BaseAdapter {

//    private final Transformation transformation;
    private Context context;
    private ArrayList<Communication> communications;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    public CommunicationListAdapter(Context context, ArrayList<Communication> communications) {
        this.context = context;
        this.communications = communications;

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
                mAnimateSearch = true;
            }
            return mValidSearchIndices.size();
        } else {
            return communications.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return communications.get(mValidSearchIndices.get(position));
        } else {
            return communications.get(position);
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
            convertView = inflater.inflate(R.layout.communication_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtCommunicationTitle = (TextView) convertView.findViewById(R.id.txtCommunicationTitle);
            holder.txtCommunicationDate = (TextView) convertView.findViewById(R.id.txtCommunicationDate);
            holder.txtCommunicationDetail = (TextView) convertView.findViewById(R.id.txtCommunicationDetail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        holder.txtCommunicationTitle.setText(communications.get(position).getCommunicationTitle());
        holder.txtCommunicationDate.setText(communications.get(position).getCommunicationDate());
        holder.txtCommunicationDetail.setText(communications.get(position).getCommunicationDetails());
        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < communications.size(); i++) {
            String communicationTitle = communications.get(i).getCommunicationTitle();
            if ((communicationTitle != null) && !(communicationTitle.isEmpty())) {
                if (communicationTitle.toLowerCase().contains(eventName.toLowerCase())) {
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
        public TextView txtCommunicationTitle, txtCommunicationDate, txtCommunicationDetail;
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
