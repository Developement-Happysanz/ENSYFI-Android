package com.palprotech.ensyfi.adapter.studentmodule;

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
import com.palprotech.ensyfi.bean.student.Communication;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by Admin on 18-05-2017.
 */

public class CommunicationListAdapter extends BaseAdapter {

    private static final String TAG = ClassTestListAdapter.class.getName();
    private final Transformation transformation;
    private Context context;
    private ArrayList<Communication> communications;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();
    private ImageLoader imageLoader = AppController.getInstance().getUniversalImageLoader();

    public CommunicationListAdapter(Context context, ArrayList<Communication> communications) {
        this.context = context;
        this.communications = communications;

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
//            holder.txtClassTestTitle = (TextView) convertView.findViewById(R.id.txtClassTestTitle);
            holder.txtCommunicationTitle = (TextView) convertView.findViewById(R.id.txtCommunicationTitle);
            holder.txtCommunicationDate = (TextView) convertView.findViewById(R.id.txtCommunicationDate);
            holder.txtCommunicationDetail = (TextView) convertView.findViewById(R.id.txtCommunicationDetail);
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

        Communication communication = communications.get(position);

//        holder.txtClassTestTitle.setText(classTests.get(position).getHwTitle());
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
        public TextView txtCommunicationTitle, txtCommunicationDate,txtCommunicationDetail;
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
