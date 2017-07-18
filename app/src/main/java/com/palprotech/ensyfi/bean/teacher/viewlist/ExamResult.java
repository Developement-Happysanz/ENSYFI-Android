package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 19-07-2017.
 */

public class ExamResult implements Serializable {

    @SerializedName("exam_name")
    @Expose
    private String exam_name;

    @SerializedName("subject_name")
    @Expose
    private String subject_name;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("internal_mark")
    @Expose
    private String internal_mark;

    @SerializedName("internal_grade")
    @Expose
    private String internal_grade;

    @SerializedName("external_mark")
    @Expose
    private String external_mark;

    @SerializedName("external_grade")
    @Expose
    private String external_grade;

    @SerializedName("total_marks")
    @Expose
    private String total_marks;

    @SerializedName("total_grade")
    @Expose
    private String total_grade;

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
        this.exam_name = exam_name;
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
     * @return The internal_mark
     */
    public String getInternalMark() {
        return internal_mark;
    }

    /**
     * @param internal_mark The internal_mark
     */
    public void setInternalMark(String internal_mark) {
        this.internal_mark = internal_mark;
    }

    /**
     * @return The internal_grade
     */
    public String getInternalGrade() {
        return internal_grade;
    }

    /**
     * @param internal_grade The internal_grade
     */
    public void setInternalGrade(String internal_grade) {
        this.internal_grade = internal_grade;
    }

    /**
     * @return The external_mark
     */
    public String getExternalMark() {
        return external_mark;
    }

    /**
     * @param external_mark The external_mark
     */
    public void setExternalMark(String external_mark) {
        this.external_mark = external_mark;
    }

    /**
     * @return The external_grade
     */
    public String getExternalGrade() {
        return external_grade;
    }

    /**
     * @param external_grade The external_grade
     */
    public void setExternalGrade(String external_grade) {
        this.external_grade = external_grade;
    }

    /**
     * @return The total_marks
     */
    public String getTotalMarks() {
        return total_marks;
    }

    /**
     * @param total_marks The total_marks
     */
    public void setTotalMarks(String total_marks) {
        this.total_marks = total_marks;
    }

    /**
     * @return The total_grade
     */
    public String getTotalGrade() {
        return total_grade;
    }

    /**
     * @param total_grade The total_grade
     */
    public void setTotalGrade(String total_grade) {
        this.total_grade = total_grade;
    }
}
