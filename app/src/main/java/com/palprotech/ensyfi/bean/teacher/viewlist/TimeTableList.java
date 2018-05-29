package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TimeTableList {

    @SerializedName("count")
    @Expose
    private int count;
    private ArrayList<TimeTable> ttDetails = new ArrayList<TimeTable>();

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
     *     The TimeTable
     */
    public ArrayList<TimeTable> getExamResult() {
        return ttDetails;
    }

    /**
     *
     * @param ttDetails
     *     The TimeTable
     */
    public void setExamResult(ArrayList<TimeTable> ttDetails) {
        this.ttDetails = ttDetails;
    }
}
