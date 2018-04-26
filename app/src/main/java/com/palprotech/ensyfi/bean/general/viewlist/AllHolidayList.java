package com.palprotech.ensyfi.bean.general.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllHolidayList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("leaveDetails")
    @Expose
    private ArrayList<AllHoliday> allHolidays = new ArrayList<AllHoliday>();

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
     * @return The AllHoliday
     */
    public ArrayList<AllHoliday> getAllHolidays() {
        return allHolidays;
    }

    /**
     * @param allHolidays The AllHoliday
     */
    public void setAllHolidays(ArrayList<AllHoliday> allHolidays) {
        this.allHolidays = allHolidays;
    }
}
