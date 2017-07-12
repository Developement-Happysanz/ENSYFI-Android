package com.palprotech.ensyfi.bean.student.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 12-07-2017.
 */

public class DayViewList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("attendenceDetails")
    @Expose
    private ArrayList<DayView> attendenceDetails = new ArrayList<DayView>();

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
     *     The dayView
     */
    public ArrayList<DayView> getDayView() {
        return attendenceDetails;
    }

    /**
     *
     * @param attendenceDetails
     *     The dayView
     */
    public void setDayView(ArrayList<DayView> attendenceDetails) {
        this.attendenceDetails = attendenceDetails;
    }
}
