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
import com.palprotech.ensyfi.bean.admin.viewlist.BoardMember;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BoardMemberListAdapter extends BaseAdapter {

    //    private final Transformation transformation;
    private Context context;
    private ArrayList<BoardMember> BoardMembers;
    private boolean mSearching = false;
    private boolean mAnimateSearch = false;
    private ArrayList<Integer> mValidSearchIndices = new ArrayList<Integer>();

    Comparator<BoardMember> myComparator = new Comparator<BoardMember>() {
        public int compare(BoardMember obj1, BoardMember obj2) {
            return obj1.getId().compareTo(obj2.getId());
        }
    };

    public BoardMemberListAdapter(Context context, ArrayList<BoardMember> BoardMembers) {
        this.context = context;
        this.BoardMembers = BoardMembers;
        Collections.sort(BoardMembers, myComparator);
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
            return BoardMembers.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mSearching) {
            return BoardMembers.get(mValidSearchIndices.get(position));
        } else {
            return BoardMembers.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BoardMemberListAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.board_member_list_item, parent, false);

            holder = new BoardMemberListAdapter.ViewHolder();
            holder.txtTeacherId = (TextView) convertView.findViewById(R.id.txtTeacherId);
            holder.txtTeacherName = (TextView) convertView.findViewById(R.id.txtTeacherName);
            convertView.setTag(holder);
        } else {
            holder = (BoardMemberListAdapter.ViewHolder) convertView.getTag();
        }

        if (mSearching) {
            position = mValidSearchIndices.get(position);
        } else {
            Log.d("Event List Adapter", "getview pos called" + position);
        }
        holder.txtTeacherId.setText(""+(position+1));
        holder.txtTeacherName.setText(BoardMembers.get(position).getName());

        return convertView;
    }

    public void startSearch(String eventName) {
        mSearching = true;
        mAnimateSearch = false;
        Log.d("EventListAdapter", "serach for event" + eventName);
        mValidSearchIndices.clear();
        for (int i = 0; i < BoardMembers.size(); i++) {
            String classStudent = BoardMembers.get(i).getName();
            if ((classStudent != null) && !(classStudent.isEmpty())) {
                if (classStudent.toLowerCase().contains(eventName.toLowerCase())) {
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
        public TextView txtTeacherId, txtTeacherName, txtTeacherMainSubject;
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
