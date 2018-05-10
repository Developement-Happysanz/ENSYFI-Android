package com.palprotech.ensyfi.adapter.adminmodule;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.admin.viewlist.Fees;
import com.palprotech.ensyfi.bean.admin.viewlist.Groups;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GroupsListAdapter extends BaseAdapter {

    //    private final Transformation transformation;
    private Context context;
    ArrayList<Groups> groups;

    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    public GroupsListAdapter(Context context, ArrayList<Groups> groups) {
        this.context = context;
        this.groups = groups;
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
            return groups.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return groups.get(mValidSearchIndices.get(position));
        } else {
            return groups.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GroupsListAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.group_notification_admin_view_list_item, parent, false);

            holder = new GroupsListAdapter.ViewHolder();
            holder.txtTermName = convertView.findViewById(R.id.group_title_txt);
            holder.txtDueDateFrom = convertView.findViewById(R.id.group_lead);
            holder.active = convertView.findViewById(R.id.status_active);
            holder.deactive = convertView.findViewById(R.id.status_deactive);
            convertView.setTag(holder);
        } else {
            holder = (GroupsListAdapter.ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }
        holder.txtTermName.setText(groups.get(position).getGroup_title());
        holder.txtDueDateFrom.setText(groups.get(position).getLead_name());
        String status = groups.get(position).getStatus().toString();
        if (status.equals("Active")) {
            holder.active.setVisibility(View.VISIBLE);
        } else {
            holder.deactive.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < groups.size(); i++) {
            String group = groups.get(i).getGroup_title();
            if ((group != null) && !(group.isEmpty())) {
                if (group.toLowerCase().contains(eventName.toLowerCase())) {
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
        public TextView txtTermName, txtDueDateFrom;
        public ImageView active, deactive;
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
