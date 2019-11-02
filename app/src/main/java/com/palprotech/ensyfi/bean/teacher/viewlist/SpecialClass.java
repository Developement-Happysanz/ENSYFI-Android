package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SpecialClass implements Serializable {

    @SerializedName("class_sec_name")
    @Expose
    private String class_sec_name;

    @SerializedName("class_sec_id")
    @Expose
    private String class_sec_id;

    @SerializedName("subject_name")
    @Expose
    private String subject_name;

    @SerializedName("subject_topic")
    @Expose
    private String subject_topic;

    @SerializedName("special_class_date")
    @Expose
    private String special_class_date;

    @SerializedName("start_time")
    @Expose
    private String start_time;

    @SerializedName("end_time")
    @Expose
    private String end_time;

    /**
     * @return The class_sec_name
     */
    public String getclass_sec_name() {
        return class_sec_name;
    }

    /**
     * @param class_sec_name The class_sec_name
     */
    public void setclass_sec_name(String class_sec_name) {
        this.class_sec_name = class_sec_name;
    }

    /**
     * @return The class_sec_id
     */
    public String getclass_sec_id() {
        return class_sec_id;
    }

    /**
     * @param class_sec_id The class_sec_id
     */
    public void setclass_sec_id(String class_sec_id) {
        this.class_sec_id = class_sec_id;
    }

    /**
     * @return The subject_name
     */
    public String getsubject_name() {
        return subject_name;
    }

    /**
     * @param subject_name The subject_name
     */
    public void setsubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    /**
     * @return The subject_topic
     */
    public String getsubject_topic() {
        return subject_topic;
    }

    /**
     * @param subject_topic The subject_topic
     */
    public void setsubject_topic(String subject_topic) {
        this.subject_topic = subject_topic;
    }
    
    /**
     * @return The special_class_date
     */
    public String getspecial_class_date() {
        return special_class_date;
    }

    /**
     * @param special_class_date The special_class_date
     */
    public void setspecial_class_date(String special_class_date) {
        this.special_class_date = special_class_date;
    }
    
    /**
     * @return The start_time
     */
    public String getstart_time() {
        return start_time;
    }

    /**
     * @param start_time The start_time
     */
    public void setstart_time(String start_time) {
        this.start_time = start_time;
    }

    /**
     * @return The end_time
     */
    public String getend_time() {
        return end_time;
    }

    /**
     * @param end_time The end_time
     */
    public void setend_time(String end_time) {
        this.end_time = end_time;
    }


}