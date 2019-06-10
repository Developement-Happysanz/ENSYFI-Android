package com.palprotech.ensyfi.bean.student.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StudentTimeTable implements Serializable {

    @SerializedName("class_id")
    @Expose
    private String class_id;

    @SerializedName("subject_id")
    @Expose
    private String subject_id;

    @SerializedName("subject_name")
    @Expose
    private String subject_name;

    @SerializedName("teacher_id")
    @Expose
    private String teacher_id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("day")
    @Expose
    private String day;

    @SerializedName("period")
    @Expose
    private String period;

    @SerializedName("sec_name")
    @Expose
    private String sec_name;

    @SerializedName("class_name")
    @Expose
    private String class_name;

    @SerializedName("from_time")
    @Expose
    private String from_time;

    @SerializedName("to_time")
    @Expose
    private String to_time;

    @SerializedName("is_break")
    @Expose
    private String is_break;

    @SerializedName("break_name")
    @Expose
    private String break_name;

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
     * @return The subject_id
     */
    public String getSubjectId() {
        return subject_id;
    }

    /**
     * @param subject_id The subject_id
     */
    public void setSubjectId(String subject_id) {
        this.subject_id = subject_id;
    }

    /**
     * @return The teacher_id
     */
    public String getTeacherId() {
        return teacher_id;
    }

    /**
     * @param teacher_id The teacher_id
     */
    public void setTeacherId(String teacher_id) {
        this.teacher_id = teacher_id;
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
     * @return The period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * @param period The period
     */
    public void setPeriod(String period) {
        this.period = period;
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
     * @return The from_time
     */
    public String getFromTime() {
        return from_time;
    }

    /**
     * @param from_time The from_time
     */
    public void setFromTime(String from_time) {
        this.from_time = from_time;
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
     * @return The is_break
     */
    public String getIsBreak() {
        return is_break;
    }

    /**
     * @param is_break The is_break
     */
    public void setIsBreak(String is_break) {
        this.is_break = is_break;
    }
    /**
     * @return The break_name
     */
    public String getBreakName() {
        return break_name;
    }

    /**
     * @param break_name The break_name
     */
    public void setBreakName(String break_name) {
        this.break_name = break_name;
    }
}
