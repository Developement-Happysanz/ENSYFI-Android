package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExamDuty implements Serializable{

    @SerializedName("exam_name")
    @Expose
    private String exam_name;

    @SerializedName("subject_name")
    @Expose
    private String subject_name;

    @SerializedName("exam_datetime")
    @Expose
    private String exam_datetime;

    @SerializedName("class_section")
    @Expose
    private String class_section;

    /**
    * @return The exam_name
    */
    public String getExam_name() {
        return exam_name;
    }

    /**
    * @param exam_name The exam_name
    */
    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
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
     * @return The exam_datetime
     */
    public String getExam_datetime() {
        return exam_datetime;
    }

    /**
     * @param exam_datetime The exam_datetime
     */
    public void setExam_datetime(String exam_datetime) {
        this.exam_datetime = exam_datetime;
    }

    /**
     * @return The class_section
     */
    public String getClass_datetime() {
        return class_section;
    }

    /**
     * @param class_section The class_section
     */
    public void setClass_datetime(String class_section) {
        this.class_section = class_section;
    }
}
