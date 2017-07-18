package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 19-07-2017.
 */

public class FeesStatusList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("data")
    @Expose
    private ArrayList<FeesStatus> data = new ArrayList<FeesStatus>();

    /**
     *
     * @return
     *     The count
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @param count
     *     The count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     *
     * @return
     *     The FeesStatus
     */
    public ArrayList<FeesStatus> getFeesStatus() {
        return data;
    }

    /**
     *
     * @param data
     *     The FeesStatus
     */
    public void setFeesStatus(ArrayList<FeesStatus> data) {
        this.data = data;
    }
}
