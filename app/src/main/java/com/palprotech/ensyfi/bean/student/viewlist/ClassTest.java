package com.palprotech.ensyfi.bean.student.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by Admin on 15-05-2017.
 */

public class ClassTest implements Serializable{

    @SerializedName("hw_id")
    @Expose
    private String hw_id;

    @SerializedName("hw_type")
    @Expose
    private String hw_type;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("test_date")
    @Expose
    private String test_date;

    @SerializedName("due_date")
    @Expose
    private String due_date;

    @SerializedName("hw_details")
    @Expose
    private String hw_details;

    @SerializedName("mark_status")
    @Expose
    private String mark_status;

    @SerializedName("subject_name")
    @Expose
    private String subject_name;

    /**
     * @return The hw_id
     */
    public String getHwId() {
        return hw_id;
    }

    /**
     * @param hw_id The hw_id
     */
    public void setHwId(String hw_id) {
        this.hw_id = hw_id;
    }

    /**
     * @return The hw_type
     */
    public String getHwType() {
        return hw_type;
    }

    /**
     * @param hw_type The hw_type
     */
    public void setHwType(String hw_type) {
        this.hw_type = hw_type;
    }

    /**
     * @return The title
     */
    public String getHwTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setHwTitle(String title) {
        this.title = title;
    }

    /**
     * @return The test_date
     */
    public String getHwTestDate() {
        return test_date;
    }

    /**
     * @param test_date The test_date
     */
    public void setHwTestDate(String test_date) {
        this.test_date = test_date;
    }

    /**
     * @return The due_date
     */
    public String getHwDueDate() {
        return due_date;
    }

    /**
     * @param due_date The due_date
     */
    public void setHwDueDate(String due_date) {
        this.due_date = test_date;
    }

    /**
     * @return The hw_details
     */
    public String getHwDatails() {
        return hw_details;
    }

    /**
     * @param hw_details The hw_details
     */
    public void setHwDatails(String hw_details) {
        this.hw_details = hw_details;
    }

    /**
     * @return The mark_status
     */
    public String getHwMarkStatus() {
        return mark_status;
    }

    /**
     * @param mark_status The mark_status
     */
    public void setHwMarkStatus(String mark_status) {
        this.mark_status = mark_status;
    }

    /**
     * @return The subject_name
     */
    public String getHwSubjectName() {
        return subject_name;
    }

    /**
     * @param subject_name The subject_name
     */
    public void setHwSubjectName(String subject_name) {
        this.subject_name = subject_name;
    }

}
