package com.palprotech.ensyfi.bean.student.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 12-07-2017.
 */

public class MonthViewStudentLeaveDays implements Serializable {

    @SerializedName("leaves")
    @Expose
    private String leaves;

    @SerializedName("enroll_id")
    @Expose
    private String enroll_id;

    @SerializedName("class_id")
    @Expose
    private String class_id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("class_name")
    @Expose
    private String class_name;

    @SerializedName("sec_name")
    @Expose
    private String sec_name;

    @SerializedName("abs_date")
    @Expose
    private String abs_date;

    @SerializedName("a_status")
    @Expose
    private String a_status;

    @SerializedName("attend_period")
    @Expose
    private String attend_period;

    @SerializedName("at_id")
    @Expose
    private String at_id;

    /**
     * @return The leaves
     */
    public String getLeaves() {
        return leaves;
    }

    /**
     * @param leaves The leaves
     */
    public void setLeaves(String leaves) {
        this.leaves = leaves;
    }

    /**
     * @return The enroll_id
     */
    public String getEnrollId() {
        return enroll_id;
    }

    /**
     * @param enroll_id The enroll_id
     */
    public void setEnrollId(String enroll_id) {
        this.enroll_id = enroll_id;
    }

    /**
     * @return The class_id
     */
    public String getClassId() {
        return class_id;
    }

    /**
     * @param class_id The class_id
     */
    public void setClassId(String class_id) {
        this.class_id = class_id;
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
     * @return The class_name
     */
    public String getClassName() {
        return class_name;
    }

    /**
     * @param class_name The class_name
     */
    public void setClassName(String class_name) {
        this.class_name = class_name;
    }

    /**
     * @return The sec_name
     */
    public String getSecName() {
        return sec_name;
    }

    /**
     * @param sec_name The sec_name
     */
    public void setSecName(String sec_name) {
        this.sec_name = sec_name;
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
    public String getAttendPeriod() {
        return attend_period;
    }

    /**
     * @param attend_period The attend_period
     */
    public void setAttendPeriod(String attend_period) {
        this.attend_period = attend_period;
    }

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
}
