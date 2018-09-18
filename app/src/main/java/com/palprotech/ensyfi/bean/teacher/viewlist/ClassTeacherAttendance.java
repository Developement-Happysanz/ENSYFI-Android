package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClassTeacherAttendance implements Serializable {

    @SerializedName("at_id")
    @Expose
    private String at_id;

    @SerializedName("ac_year")
    @Expose
    private String ac_year;

    @SerializedName("class_id")
    @Expose
    private String class_id;

    @SerializedName("class_total")
    @Expose
    private String class_total;

    @SerializedName("no_of_present")
    @Expose
    private String no_of_present;

    @SerializedName("no_of_absent")
    @Expose
    private String no_of_absent;

    @SerializedName("attendence_period")
    @Expose
    private String attendence_period;

    @SerializedName("created_by")
    @Expose
    private String created_by;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_by")
    @Expose
    private String updated_by;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("sent_status")
    @Expose
    private String sent_status;

    @SerializedName("name")
    @Expose
    private String name;


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
     * @return The ac_year
     */
    public String getAcYear() {
        return ac_year;
    }

    /**
     * @param ac_year The ac_year
     */
    public void setAcYear(String ac_year) {
        this.ac_year = ac_year;
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
     * @return The attendence_period
     */
    public String getAttendencePeriod() {
        return attendence_period;
    }

    /**
     * @param attendence_period The attendence_period
     */
    public void setAttendencePeriod(String attendence_period) {
        this.attendence_period = attendence_period;
    }

    /**
     * @return The created_by
     */
    public String getCreatedBy() {
        return created_by;
    }

    /**
     * @param created_by The created_by
     */
    public void setdCreatedBy(String created_by) {
        this.created_by = created_by;
    }

    /**
     * @return The created_at
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * @return The updated_by
     */
    public String getUpdated_by() {
        return updated_by;
    }

    /**
     * @param updated_by The updated_by
     */
    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    /**
     * @return The updated_at
     */
    public String getUpdated_at() {
        return updated_at;
    }

    /**
     * @param updated_at The updated_at
     */
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * @return The sent_status
     */
    public String getSent_status() {
        return sent_status;
    }

    /**
     * @param sent_status The sent_status
     */
    public void setSent_status(String sent_status) {
        this.sent_status = sent_status;
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

}
