package com.palprotech.ensyfi.adapter.general;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.general.viewlist.AllHoliday;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class AllHolidayListAdapter extends BaseAdapter {

    //    private final Transformation transformation;
    private Context context;
    private ArrayList<AllHoliday> allHoliday;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    public AllHolidayListAdapter(Context context, ArrayList<AllHoliday> allHoliday) {
        this.context = context;
        this.allHoliday = allHoliday;
        Collections.reverse(allHoliday);
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
            return allHoliday.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return allHoliday.get(mValidSearchIndices.get(position));
        } else {
            return allHoliday.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AllHolidayListAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.holiday_list_item, parent, false);

            holder = new AllHolidayListAdapter.ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.holiday_title);
            holder.txtDescription = (TextView) convertView.findViewById(R.id.holiday_reason);
            holder.txtDate = (TextView) convertView.findViewById(R.id.holiday_date);
            holder.txtDay= (TextView) convertView.findViewById(R.id.holiday_day);
            convertView.setTag(holder);
        } else {
            holder = (AllHolidayListAdapter.ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }

        holder.txtTitle.setText(allHoliday.get(position).getTitle());
        holder.txtDescription.setText(allHoliday.get(position).getDescription());
        String start = allHoliday.get(position).getSTART();
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(start);
            SimpleDateFormat sent_date = new SimpleDateFormat("dd-MM-yyyy");
            String sent_date_name = sent_date.format(date.getTime());
            if (start != null) {
                holder.txtDate.setText(sent_date_name);
            } else {
                holder.txtDate.setText("N/A");
            }
        } catch (final ParseException e) {
            e.printStackTrace();
        }

        holder.txtDay.setText("(" + allHoliday.get(position).getDay() + ")");
        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < allHoliday.size(); i++) {
            String holidayTitle = allHoliday.get(i).getTitle();
            if ((holidayTitle != null) && !(holidayTitle.isEmpty())) {
                if (holidayTitle.toLowerCase().contains(eventName.toLowerCase())) {
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
        public TextView txtTitle, txtDescription, txtDay, txtDate;
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