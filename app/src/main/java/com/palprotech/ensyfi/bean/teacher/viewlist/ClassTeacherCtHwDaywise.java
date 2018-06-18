package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClassTeacherCtHwDaywise implements Serializable {
    
    @SerializedName("hw_id")
    @Expose
    private String hw_id;

    @SerializedName("hw_type")
    @Expose
    private String hw_type;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("test_date")
    @Expose
    private String test_date;

    @SerializedName("due_date")
    @Expose
    private String due_date;

    @SerializedName("hw_details")
    @Expose
    private String hw_details;

    @SerializedName("send_option_status")
    @Expose
    private String send_option_status;

    @SerializedName("subject_id")
    @Expose
    private String subject_id;

    @SerializedName("subject_name")
    @Expose
    private String subject_name;

    @SerializedName("name")
    @Expose
    private String name;


    /**
     * @return The hw_id
     */
    public String getHw_id() {
        return hw_id;
    }

    /**
     * @param hw_id The hw_id
     */
    public void setHw_id(String hw_id) {
        this.hw_id = hw_id;
    }

    /**
     * @return The hw_type
     */
    public String getHw_type() {
        return hw_type;
    }

    /**
     * @param hw_type The hw_type
     */
    public void setHw_type(String hw_type) {
        this.hw_type = hw_type;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The created_at
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * @param created_at The created_at
     */
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    /**
     * @return The test_date
     */
    public String getTest_date() {
        return test_date;
    }

    /**
     * @param test_date The test_date
     */
    public void setTest_date(String test_date) {
        this.test_date = test_date;
    }

    /**
     * @return The due_date
     */
    public String getDue_date() {
        return due_date;
    }

    /**
     * @param due_date The due_date
     */
    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    /**
     * @return The hw_details
     */
    public String getHw_details() {
        return hw_details;
    }

    /**
     * @param hw_details The hw_details
     */
    public void setHw_details(String hw_details) {
        this.hw_details = hw_details;
    }

    /**
     * @return The send_option_status
     */
    public String getSend_option_status() {
        return send_option_status;
    }

    /**
     * @param send_option_status The send_option_status
     */
    public void setSend_option_status(String send_option_status) {
        this.send_option_status = send_option_status;
    }

    /**
     * @return The subject_id
     */
    public String getSubject_id() {
        return subject_id;
    }

    /**
     * @param subject_id The subject_id
     */
    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    /**
     * @return The subject_name
     */
    public String getSubject_name() {
        return subject_name;
    }

    /**
     * @param subject_name The subject_name
     */
    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
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