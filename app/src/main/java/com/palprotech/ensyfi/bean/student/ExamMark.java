package com.palprotech.ensyfi.bean.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 25-05-2017.
 */

public class ExamMark {

    @SerializedName("exam_name")
    @Expose
    private String exam_name;

    @SerializedName("subject_name")
    @Expose
    private String subject_name;

    @SerializedName("marks")
    @Expose
    private String marks;

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
     * @return The marks
     */
    public String getMarks() {
        return marks;
    }

    /**
     * @param marks The marks
     */
    public void setMarks(String marks) {
        this.marks = marks;
    }
}
