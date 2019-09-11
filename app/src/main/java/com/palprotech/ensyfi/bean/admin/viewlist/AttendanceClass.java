package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AttendanceClass implements Serializable {

    @SerializedName("class_id")
    @Expose
    private String class_id;

    @SerializedName("class_name")
    @Expose
    private String class_name;

    @SerializedName("class_total")
    @Expose
    private String class_total;

    @SerializedName("no_of_present")
    @Expose
    private String no_of_present;

    @SerializedName("no_of_absent")
    @Expose
    private String no_of_absent;

    @SerializedName("status")
    @Expose
    private String status;

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
     * @return The class_total
     */
    public String getClass_total() {
        return class_total;
    }

    /**
     * @param class_total The class_total
     */
    public void setClass_total(String class_total) {
        this.class_total = class_total;
    }

    /**
     * @return The no_of_present
     */
    public String getNoOfPresent() {
        return no_of_present;
    }

    /**
     * @param no_of_present The no_of_present
     */
    public void setNoOfPresent(String no_of_present) {
        this.no_of_present = no_of_present;
    }

    /**
     * @return The no_of_absent
     */
    public String getNoOfAbsent() {
        return no_of_absent;
    }

    /**
     * @param no_of_absent The no_of_absent
     */
    public void setNoOfAbsent(String no_of_absent) {
        this.no_of_absent = no_of_absent;
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
     * @return The class_name
     */
    public String getClass_name() {
        return class_name;
    }

    /**
     * @param class_name The class_name
     */
    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

}