package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GroupsList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("groupList")
    @Expose
    private ArrayList<Groups> groupList = new ArrayList<Groups>();

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
    public ArrayList<Groups> getGroups() {
        return groupList;
    }

    /**
     * @param groupList The Groups
     */
    public void setGroups(ArrayList<Groups> groupList) {
        this.groupList = groupList;
    }
}
