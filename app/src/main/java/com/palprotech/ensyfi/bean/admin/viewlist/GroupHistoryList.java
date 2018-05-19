package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GroupHistoryList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("msg_history")
    @Expose
    private ArrayList<GroupHistory> groupHistoryList = new ArrayList<GroupHistory>();

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
    public ArrayList<GroupHistory> getGroups() {
        return groupHistoryList;
    }

    /**
     * @param groupHistoryList The Groups
     */
    public void setGroups(ArrayList<GroupHistory> groupHistoryList) {
        this.groupHistoryList = groupHistoryList;
    }
}