package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GroupStaffMembersList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("gnMemberlist")
    @Expose
    private ArrayList<GroupStaffMembers> groupList = new ArrayList<GroupStaffMembers>();

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
    public ArrayList<GroupStaffMembers> getGroups() {
        return groupList;
    }

    /**
     * @param groupList The Groups
     */
    public void setGroups(ArrayList<GroupStaffMembers> groupList) {
        this.groupList = groupList;
    }
}
