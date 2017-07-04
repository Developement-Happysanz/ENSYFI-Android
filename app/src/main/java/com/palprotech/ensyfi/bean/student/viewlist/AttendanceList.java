package com.palprotech.ensyfi.bean.student.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 21-05-2017.
 */

public class AttendanceList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("attendenceDetails")
    @Expose
    private ArrayList<Attendance> attendenceDetails = new ArrayList<Attendance>();

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
     *     The attendance
     */
    public ArrayList<Attendance> getAttendanceList() {
        return attendenceDetails;
    }

    /**
     *
     * @param attendenceDetails
     *     The attendenceDetails
     */
    public void setAttendanceList(ArrayList<Attendance> attendenceDetails) {
        this.attendenceDetails = attendenceDetails;
    }
}
