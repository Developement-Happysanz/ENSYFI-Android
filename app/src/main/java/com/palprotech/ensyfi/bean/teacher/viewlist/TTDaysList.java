package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TTDaysList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("reviewDetails")
    @Expose
    private ArrayList<TTDays> dayDetails = new ArrayList<TTDays>();

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
     * @return The TTDays
     */
    public ArrayList<TTDays> getTTDays() {
        return dayDetails;
    }

    /**
     * @param dayDetails The TTDays
     */
    public void setTTDays(ArrayList<TTDays> dayDetails) {
        this.dayDetails = dayDetails;
    }
}
