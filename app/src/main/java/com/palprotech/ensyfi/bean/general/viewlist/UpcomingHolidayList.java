package com.palprotech.ensyfi.bean.general.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UpcomingHolidayList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("upcomingleavesDetails")
    @Expose
    private ArrayList<UpcomingHoliday> upcomingHolidays = new ArrayList<UpcomingHoliday>();

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
     * @return The UpcomingHoliday
     */
    public ArrayList<UpcomingHoliday> getUpcomingHolidays() {
        return upcomingHolidays;
    }

    /**
     * @param upcomingHolidays The UpcomingHoliday
     */
    public void setUpcomingHolidays(ArrayList<UpcomingHoliday> upcomingHolidays) {
        this.upcomingHolidays = upcomingHolidays;
    }
}
