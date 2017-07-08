package com.palprotech.ensyfi.bean.general.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 08-07-2017.
 */

public class CircularList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("circularDetails")
    @Expose
    private ArrayList<Circular> circularDetails = new ArrayList<Circular>();

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
     * @return The Circular
     */
    public ArrayList<Circular> getCircular() {
        return circularDetails;
    }

    /**
     * @param circularDetails The Circular
     */
    public void setCircular(ArrayList<Circular> circularDetails) {
        this.circularDetails = circularDetails;
    }
}
