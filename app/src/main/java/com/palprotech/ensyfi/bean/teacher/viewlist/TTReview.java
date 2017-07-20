package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 20-07-2017.
 */

public class TTReview implements Serializable {

    @SerializedName("time_date")
    @Expose
    private String time_date;

    @SerializedName("day")
    @Expose
    private String day;

    @SerializedName("class_id")
    @Expose
    private String class_id;

    @SerializedName("class_name")
    @Expose
    private String class_name;

    @SerializedName("sec_name")
    @Expose
    private String sec_name;

    @SerializedName("subject_name")
    @Expose
    private String subject_name;

    @SerializedName("comments")
    @Expose
    private String comments;

    @SerializedName("remarks")
    @Expose
    private String remarks;

    @SerializedName("status")
    @Expose
    private String status;


    /**
     * @return The time_date
     */
    public String getTimeDate() {
        return time_date;
    }

    /**
     * @param time_date The time_date
     */
    public void setTimeDate(String time_date) {
        this.time_date = time_date;
    }

    /**
     * @return The day
     */
    public String getDay() {
        return day;
    }

    /**
     * @param day The day
     */
    public void setDay(String day) {
        this.day = day;
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
    public String getSectionName() {
        return sec_name;
    }

    /**
     * @param sec_name The sec_name
     */
    public void setSectionName(String sec_name) {
        this.sec_name = sec_name;
    }

    /**
     * @return The subject_name
     */
    public String getSubjectName() {
        return subject_name;
    }

    /**
     * @param subject_name The subject_name
     */
    public void setSubjectName(String subject_name) {
        this.subject_name = subject_name;
    }

    /**
     * @return The comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments The comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return The remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks The remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
}
