package com.palprotech.ensyfi.bean.general.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 15-07-2017.
 */

public class LeaveStatusList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("leaveDetails")
    @Expose
    private ArrayList<LeaveStatus> leaveDetails = new ArrayList<LeaveStatus>();

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
     * @return The LeaveStatus
     */
    public ArrayList<LeaveStatus> getLeaveStatus() {
        return leaveDetails;
    }

    /**
     * @param leaveDetails The LeaveStatus
     */
    public void setOnDuty(ArrayList<LeaveStatus> leaveDetails) {
        this.leaveDetails = leaveDetails;
    }
}
