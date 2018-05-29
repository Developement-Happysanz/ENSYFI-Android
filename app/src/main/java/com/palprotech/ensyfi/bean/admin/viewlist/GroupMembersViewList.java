package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GroupMembersViewList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("memberList")
    @Expose
    private ArrayList<GroupMembersView> groupList = new ArrayList<GroupMembersView>();

    /**
     * @return The count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return The Groups
     */
    public ArrayList<GroupMembersView> getGroups() {
        return groupList;
    }

    /**
     * @param groupList The Groups
     */
    public void setGroups(ArrayList<GroupMembersView> groupList) {
        this.groupList = groupList;
    }
}
