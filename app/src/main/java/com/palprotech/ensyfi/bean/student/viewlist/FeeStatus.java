package com.palprotech.ensyfi.bean.student.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 08-07-2017.
 */

public class FeeStatus implements Serializable {

    @SerializedName("term_name")
    @Expose
    private String term_name;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("due_date_from")
    @Expose
    private String due_date_from;

    @SerializedName("due_date_to")
    @Expose
    private String due_date_to;

    /**
     * @return The term_name
     */
    public String getTermName() {
        return term_name;
    }

    /**
     * @param term_name The term_name
     */
    public void setTermName(String term_name) {
        this.term_name = term_name;
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

    /**
     * @return The due_date_from
     */
    public String getDueDateFrom() {

        return due_date_from;
    }

    /**
     * @param due_date_from The due_date_from
     */
    public void setDueDateFrom(String due_date_from) {
        this.due_date_from = due_date_from;
    }

    /**
     * @return The due_date_to
     */
    public String getDueDateTo() {

        return due_date_to;
    }

    /**
     * @param due_date_to The due_date_to
     */
    public void setDueDateTo(String due_date_to) {
        this.due_date_to = due_date_to;
    }
}
