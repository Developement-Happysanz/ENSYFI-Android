package com.palprotech.ensyfi.bean.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 21-05-2017.
 */

public class Attendance {

    @SerializedName("at_id")
    @Expose
    private String at_id;

    @SerializedName("student_id")
    @Expose
    private String student_id;

    @SerializedName("abs_date")
    @Expose
    private String abs_date;

    @SerializedName("a_status")
    @Expose
    private String a_status;

    @SerializedName("attend_period")
    @Expose
    private String attend_period;

    /**
     * @return The at_id
     */
    public String getAtId() {
        return at_id;
    }

    /**
     * @param at_id The at_id
     */
    public void setAtId(String at_id) {
        this.at_id = at_id;
    }

    /**
     * @return The student_id
     */
    public String getStudentId() {
        return student_id;
    }

    /**
     * @param student_id The student_id
     */
    public void setStudentId(String student_id) {
        this.student_id = student_id;
    }

    /**
     * @return The abs_date
     */
    public String getAbsDate() {
        return abs_date;
    }

    /**
     * @param abs_date The abs_date
     */
    public void setAbsDate(String abs_date) {
        this.abs_date = abs_date;
    }

    /**
     * @return The a_status
     */
    public String getAStatus() {
        return a_status;
    }

    /**
     * @param a_status The a_status
     */
    public void setAStatus(String a_status) {
        this.a_status = a_status;
    }

    /**
     * @return The attend_period
     */
    public String getAPeriod() {
        return attend_period;
    }

    /**
     * @param attend_period The attend_period
     */
    public void setAPeriod(String attend_period) {
        this.attend_period = attend_period;
    }

}
