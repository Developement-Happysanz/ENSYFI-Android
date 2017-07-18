package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 19-07-2017.
 */

public class FeesList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("data")
    @Expose
    private ArrayList<Fees> data = new ArrayList<Fees>();

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
     * @return The Fees
     */
    public ArrayList<Fees> getFees() {
        return data;
    }

    /**
     * @param data The Fees
     */
    public void setFees(ArrayList<Fees> data) {
        this.data = data;
    }

}
