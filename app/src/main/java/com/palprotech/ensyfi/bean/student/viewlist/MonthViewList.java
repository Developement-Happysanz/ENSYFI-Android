package com.palprotech.ensyfi.bean.student.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 12-07-2017.
 */

public class MonthViewList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("attendenceDetails")
    @Expose
    private ArrayList<MonthView> attendenceDetails = new ArrayList<MonthView>();

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
     *     The monthView
     */
    public ArrayList<MonthView> getMonthView() {
        return attendenceDetails;
    }

    /**
     *
     * @param attendenceDetails
     *     The monthView
     */
    public void setMonthView(ArrayList<MonthView> attendenceDetails) {
        this.attendenceDetails = attendenceDetails;
    }
}
