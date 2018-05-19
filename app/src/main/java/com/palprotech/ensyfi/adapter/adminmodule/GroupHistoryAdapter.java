package com.palprotech.ensyfi.adapter.adminmodule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.admin.viewlist.GroupHistory;
import com.palprotech.ensyfi.bean.admin.viewlist.GroupHistoryList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class GroupHistoryAdapter extends BaseAdapter {

    //    private final Transformation transformation;
    private Context context;
    private ArrayList<GroupHistory> groupListses;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    public GroupHistoryAdapter(Context context, ArrayList<GroupHistory> groupListses) {
        this.context = context;
        this.groupListses = groupListses;
//        Collections.sort(groupListses, myComparator);
//        transformation = new RoundedTransformationBuilder().cornerRadiusDp(0).oval(false).build();
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
            return groupListses.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return groupListses.get(mValidSearchIndices.get(position));
        } else {
            return groupListses.get(position);
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
            convertView = inflater.inflate(R.layout.group_notification_history_item, parent, false);

            holder = new GroupHistoryAdapter.ViewHolder();
            holder.txtNotificationTitle = (TextView) convertView.findViewById(R.id.txtNotificationTitle);
            holder.txtnotes = (TextView) convertView.findViewById(R.id.txtNotes);
            holder.txtDateTime = (TextView) convertView.findViewById(R.id.txtNotificationDate);
            holder.txtType = (TextView) convertView.findViewById(R.id.txtNotificationType);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("GroupList List Adapter", "getview pos called" + position);
        }
        String start = groupListses.get(position).getCreated_at();
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = formatter.parse(start);
            SimpleDateFormat sent_date = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            String sent_date_name = sent_date.format(date.getTime());
            if (start != null) {
                holder.txtDateTime.setText(sent_date_name);
            } else {
                holder.txtDateTime.setText("N/A");
            }
        } catch (final ParseException e) {
            e.printStackTrace();
        }

        holder.txtNotificationTitle.setText(groupListses.get(position).getGroup_title());
        holder.txtnotes.setText(groupListses.get(position).getNotes());
        holder.txtType.setText(groupListses.get(position).getNotificationType());

        return convertView;
    }

    public void startSearch(String groupName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("GroupListAdapter", "serach for group" + groupName);
        mValidSearchIndices.clear();
        for (int i = 0; i < groupListses.size(); i++) {
            String groupTitle = groupListses.get(i).getGroup_title();
            if ((groupTitle != null) && !(groupTitle.isEmpty())) {
                if (groupTitle.toLowerCase().contains(groupName.toLowerCase())) {
                    mValidSearchIndices.add(i);
                }
            }
        }
        Log.d("GroupList List Adapter", "notify" + mValidSearchIndices.size());
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
        public TextView txtNotificationTitle, txtnotes, txtDateTime, txtType;
    }

    public boolean ismSearching() {
        return mSearching;
    }

    public int getActualGroupPos(int selectedSearchpos) {
        if (selectedSearchpos < mValidSearchIndices.size()) {
            return mValidSearchIndices.get(selectedSearchpos);
        } else {
            return 0;
        }
    }
}
