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
import com.palprotech.ensyfi.bean.admin.viewlist.GroupMembersView;

import java.util.ArrayList;

public class GroupMemberListViewAdapter  extends BaseAdapter {

    //    private final Transformation transformation;
    private Context context;
    ArrayList<GroupMembersView> groupMembers;

    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    public GroupMemberListViewAdapter(Context context, ArrayList<GroupMembersView> groupMembers) {
        this.context = context;
        this.groupMembers = groupMembers;
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
            return groupMembers.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return groupMembers.get(mValidSearchIndices.get(position));
        } else {
            return groupMembers.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GroupMemberListViewAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.group_notification_members_view_item, parent, false);
            holder = new GroupMemberListViewAdapter.ViewHolder();
            holder.txtMemberName = convertView.findViewById(R.id.member_name);
            holder.txtMemberTypeName = convertView.findViewById(R.id.member_type);

            convertView.setTag(holder);
        } else {
            holder = (GroupMemberListViewAdapter.ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }
        holder.txtMemberName.setText(groupMembers.get(position).getName());
        holder.txtMemberTypeName.setText(groupMembers.get(position).getUserTypeName());

        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < groupMembers.size(); i++) {
            String group = groupMembers.get(i).getName();
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
        public TextView txtMemberName, txtMemberTypeName;
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