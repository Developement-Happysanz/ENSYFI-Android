package com.palprotech.ensyfi.bean.student.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.palprotech.ensyfi.bean.general.viewlist.Circular;

import java.util.ArrayList;

/**
 * Created by Admin on 08-07-2017.
 */

public class FeeStatusList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("feesDetails")
    @Expose
    private ArrayList<FeeStatus> feesDetails = new ArrayList<FeeStatus>();

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
     * @return The FeeStatus
     */
    public ArrayList<FeeStatus> getFeeStatus() {
        return feesDetails;
    }

    /**
     * @param feesDetails The FeeStatus
     */
    public void setFeeStatus(ArrayList<FeeStatus> feesDetails) {
        this.feesDetails = feesDetails;
    }
}
