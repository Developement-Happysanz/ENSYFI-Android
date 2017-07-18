package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 18-07-2017.
 */

public class Fees implements Serializable {

    @SerializedName("fees_id")
    @Expose
    private String fees_id;

    @SerializedName("due_date_from")
    @Expose
    private String due_date_from;

    @SerializedName("term_name")
    @Expose
    private String term_name;

    @SerializedName("due_date_to")
    @Expose
    private String due_date_to;

    @SerializedName("from_year")
    @Expose
    private String from_year;

    @SerializedName("to_year")
    @Expose
    private String to_year;

    /**
     * @return The fees_id
     */
    public String getFeesId() {
        return fees_id;
    }

    /**
     * @param fees_id The fees_id
     */
    public void setFeesId(String fees_id) {
        this.fees_id = fees_id;
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

    /**
     * @return The from_year
     */
    public String getFromYear() {
        return from_year;
    }

    /**
     * @param from_year The from_year
     */
    public void setFromYear(String from_year) {
        this.from_year = from_year;
    }

    /**
     * @return The to_year
     */
    public String getToYear() {
        return to_year;
    }

    /**
     * @param to_year The to_year
     */
    public void setToYear(String to_year) {
        this.to_year = to_year;
    }
}
