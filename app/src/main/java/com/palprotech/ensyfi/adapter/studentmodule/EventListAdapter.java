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
import com.palprotech.ensyfi.bean.student.Event;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by Narendar on 17/05/17.
 */

public class EventListAdapter extends BaseAdapter {

    private static final String TAG = EventListAdapter.class.getName();
    private final Transformation transformation;
    private Context context;
    private ArrayList<Event> events;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices =new ArrayList<Integer>();
    private ImageLoader imageLoader = AppController.getInstance().getUniversalImageLoader();

    public EventListAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;

        transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(0)
                .oval(false)
                .build();
        mSearching = false;
    }

    @Override
    public int getCount() {
        if(mSearching){
            // Log.d("Event List Adapter","Search count"+mValidSearchIndices.size());
            if(!mAnimateSearch){
                mAnimateSearch = true;
            }
            return mValidSearchIndices.size();

        }else{
            // Log.d(TAG,"Normal count size");
            return events.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if(mSearching){
            return events.get(mValidSearchIndices.get(position));
        }else {
            return events.get(position);
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
            convertView = inflater.inflate(R.layout.events_list_item, parent, false);

            holder = new EventListAdapter.ViewHolder();
//            holder.txtClassTestTitle = (TextView) convertView.findViewById(R.id.txtClassTestTitle);
            holder.txtEventName = (TextView) convertView.findViewById(R.id.txtEventName);
            holder.txtEventDate = (TextView) convertView.findViewById(R.id.txtEventDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(mSearching){
            // Log.d("Event List Adapter","actual position"+ position);
            position = mValidSearchIndices.get(position);
            //Log.d("Event List Adapter", "position is"+ position);

        }else{
            Log.d("Event List Adapter","getview pos called"+ position);
        }

        Event event = events.get(position);

//        holder.txtClassTestTitle.setText(classTests.get(position).getHwTitle());
        holder.txtEventName.setText(events.get(position).getEvent_name());
        holder.txtEventDate.setText(events.get(position).getEvent_date());

        return convertView;
    }

    public void startSearch(String eventName){
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter","serach for event"+eventName);
        mValidSearchIndices.clear();
        for(int i =0; i< events.size(); i++){
            String eventTitle = events.get(i).getEvent_name();
            if((eventTitle != null) && !(eventTitle.isEmpty())){
                if( eventTitle.toLowerCase().contains(eventName.toLowerCase())){
                    mValidSearchIndices.add(i);
                }

            }

        }
        Log.d("Event List Adapter","notify"+ mValidSearchIndices.size());
        //notifyDataSetChanged();
    }

    public void exitSearch(){
        mSearching = false;
        mValidSearchIndices.clear();
        mAnimateSearch = false;
        // notifyDataSetChanged();
    }

    public void clearSearchFlag(){
        mSearching = false;
    }

    public class ViewHolder {
        public TextView txtClassTestTitle, txtEventName, txtEventDate;
    }

    public boolean ismSearching() {
        return mSearching;
    }

    public int getActualEventPos(int selectedSearchpos){
        if(selectedSearchpos < mValidSearchIndices.size()) {
            return mValidSearchIndices.get(selectedSearchpos);
        }else{
            return 0;
        }
    }
}

