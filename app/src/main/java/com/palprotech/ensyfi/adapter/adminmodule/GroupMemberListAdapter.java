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
import com.palprotech.ensyfi.bean.admin.viewlist.GroupStaffMembers;
import com.palprotech.ensyfi.bean.admin.viewlist.Groups;

import java.util.ArrayList;

public class GroupMemberListAdapter extends BaseAdapter {

    //    private final Transformation transformation;
    private Context context;
    ArrayList<GroupStaffMembers> groupStaffMembers;

    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    public GroupMemberListAdapter(Context context, ArrayList<GroupStaffMembers> groupStaffMembers) {
        this.context = context;
        this.groupStaffMembers = groupStaffMembers;
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
            return groupStaffMembers.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return groupStaffMembers.get(mValidSearchIndices.get(position));
        } else {
            return groupStaffMembers.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GroupMemberListAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.group_notification_add_member_list_item, parent, false);
            holder = new GroupMemberListAdapter.ViewHolder();
            holder.txtTermName = convertView.findViewById(R.id.member_name);
            holder.txtDueDateFrom = convertView.findViewById(R.id.group_lead);
            holder.active = convertView.findViewById(R.id.status_selected);
            holder.deactive = convertView.findViewById(R.id.status_deselected);
            if (groupStaffMembers.get(position).getStatus().equalsIgnoreCase("1")) {
                holder.active.setVisibility(View.VISIBLE);
                holder.deactive.setVisibility(View.INVISIBLE);
            } else if (groupStaffMembers.get(position).getStatus().equalsIgnoreCase("0")) {
                holder.deactive.setVisibility(View.VISIBLE);
                holder.active.setVisibility(View.INVISIBLE);
            }
            convertView.setTag(holder);
        } else {
            holder = (GroupMemberListAdapter.ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }
        holder.txtTermName.setText(groupStaffMembers.get(position).getName());
        holder.txtDueDateFrom.setText(groupStaffMembers.get(position).getId());

        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < groupStaffMembers.size(); i++) {
            String group = groupStaffMembers.get(i).getName();
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
