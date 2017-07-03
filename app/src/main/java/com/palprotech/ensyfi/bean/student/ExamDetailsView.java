package com.palprotech.ensyfi.bean.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 18-05-2017.
 */

public class ExamDetailsView implements Serializable {

    @SerializedName("exam_id")
    @Expose
    private String exam_id;

    @SerializedName("exam_name")
    @Expose
    private String exam_name;

    @SerializedName("subject_name")
    @Expose
    private String subject_name;

    @SerializedName("exam_date")
    @Expose
    private String exam_date;

    @SerializedName("times")
    @Expose
    private String times;

    /**
     * @return The exam_id
     */
    public String getExamId() {
        return exam_id;
    }

    /**
     * @param exam_id The exam_id
     */
    public void setExamId(String exam_id) {
        this.exam_id = exam_id;
    }

    /**
     * @return The exam_name
     */
    public String getExamName() {
        return exam_name;
    }

    /**
     * @param exam_name The exam_name
     */
    public void setExamName(String exam_name) {
        this.exam_id = exam_name;
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
        this.exam_id = subject_name;
    }

    /**
     * @return The exam_date
     */
    public String getExamDate() {
        return exam_date;
    }

    /**
     * @param exam_date The exam_date
     */
    public void setExamDate(String exam_date) {
        this.exam_id = exam_date;
    }

    /**
     * @return The times
     */
    public String getTimes() {
        return times;
    }

    /**
     * @param times The times
     */
    public void setTimes(String times) {
        this.times = times;
    }
}
