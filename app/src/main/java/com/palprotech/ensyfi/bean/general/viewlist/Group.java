package com.palprotech.ensyfi.bean.general.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Narendar on 17/05/17.
 */

public class Group {
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("groupmsgDetails")
    @Expose
    private ArrayList<GroupList> groupmsgDetails = new ArrayList<GroupList>();

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
     * @return The groups
     */
    public ArrayList<GroupList> getGroups() {
        return groupmsgDetails;
    }
}

