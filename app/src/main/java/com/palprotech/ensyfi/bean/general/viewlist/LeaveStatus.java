package com.palprotech.ensyfi.bean.general.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 15-07-2017.
 */

public class LeaveStatus implements Serializable {

    @SerializedName("leave_title")
    @Expose
    private String leave_title;

    @SerializedName("from_leave_date")
    @Expose
    private String from_leave_date;

    @SerializedName("to_leave_date")
    @Expose
    private String to_leave_date;

    @SerializedName("frm_time")
    @Expose
    private String frm_time;

    @SerializedName("to_time")
    @Expose
    private String to_time;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("leave_type")
    @Expose
    private String leave_type;

    /**
     * @return The leave_title
     */
    public String getLeaveTitle() {
        return leave_title;
    }

    /**
     * @param leave_title The leave_title
     */
    public void setLeaveTitle(String leave_title) {
        this.leave_title = leave_title;
    }

    /**
     * @return The from_leave_date
     */
    public String getFromLeaveDate() {
        return from_leave_date;
    }

    /**
     * @param from_leave_date The from_leave_date
     */
    public void setFromLeaveDate(String from_leave_date) {
        this.from_leave_date = from_leave_date;
    }

    /**
     * @return The to_leave_date
     */
    public String getToLeaveDate() {
        return to_leave_date;
    }

    /**
     * @param to_leave_date The to_leave_date
     */
    public void setToLeaveDate(String to_leave_date) {
        this.to_leave_date = to_leave_date;
    }

    /**
     * @return The frm_time
     */
    public String getFromTime() {
        return frm_time;
    }

    /**
     * @param frm_time The frm_time
     */
    public void setFromTime(String frm_time) {
        this.frm_time = frm_time;
    }

    /**
     * @return The to_time
     */
    public String getToTime() {
        return to_time;
    }

    /**
     * @param to_time The to_time
     */
    public void setToTime(String to_time) {
        this.to_time = to_time;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The leave_type
     */
    public String getLeaveType() {
        return leave_type;
    }

    /**
     * @param leave_type The leave_type
     */
    public void setLeaveType(String leave_type) {
        this.leave_type = leave_type;
    }
}
