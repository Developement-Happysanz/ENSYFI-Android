package com.palprotech.ensyfi.bean.general.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 10-07-2017.
 */

public class OnDutyList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("ondutyDetails")
    @Expose
    private ArrayList<OnDuty> ondutyDetails = new ArrayList<OnDuty>();

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
     * @return The OnDuty
     */
    public ArrayList<OnDuty> getOnDuty() {
        return ondutyDetails;
    }

    /**
     * @param ondutyDetails The OnDuty
     */
    public void setOnDuty(ArrayList<OnDuty> ondutyDetails) {
        this.ondutyDetails = ondutyDetails;
    }
}
