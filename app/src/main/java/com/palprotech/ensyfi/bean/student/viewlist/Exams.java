package com.palprotech.ensyfi.bean.student.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 17-05-2017.
 */

public class Exams implements Serializable {

    @SerializedName("exam_id")
    @Expose
    private String exam_id;

    @SerializedName("exam_name")
    @Expose
    private String exam_name;

    @SerializedName("Fromdate")
    @Expose
    private String from_date;

    @SerializedName("Todate")
    @Expose
    private String to_date;

    @SerializedName("MarkStatus")
    @Expose
    private String MarkStatus;

    @SerializedName("classmaster_id")
    @Expose
    private String classmaster_id;


    /**
     * @return The hw_id
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
        this.exam_name = exam_name;
    }

    /**
     * @return The from_date
     */
    public String getFromDate() {
        return from_date;
    }

    /**
     * @param from_date The from_date
     */
    public void setFromDate(String from_date) {
        this.from_date = from_date;
    }

    /**
     * @return The to_date
     */
    public String getToDate() {
        return to_date;
    }

    /**
     * @param to_date The to_date
     */
    public void setToDate(String to_date) {
        this.to_date = to_date;
    }

    /**
     * @return The MarkStatus
     */
    public String getMarkStatus() {
        return MarkStatus;
    }

    /**
     * @param MarkStatus The MarkStatus
     */
    public void setMarkStatus(String MarkStatus) {
        this.MarkStatus = MarkStatus;
    }

    /**
     * @return The classmaster_id
     */
    public String getCassMasterId() {
        return classmaster_id;
    }

    /**
     * @param classmaster_id The classmaster_id
     */
    public void setCassMasterId(String classmaster_id) {
        this.classmaster_id = classmaster_id;
    }

}
