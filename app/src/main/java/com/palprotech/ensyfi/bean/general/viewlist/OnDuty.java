package com.palprotech.ensyfi.bean.general.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 10-07-2017.
 */

public class OnDuty implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("od_for")
    @Expose
    private String od_for;

    @SerializedName("from_date")
    @Expose
    private String from_date;

    @SerializedName("to_date")
    @Expose
    private String to_date;

    @SerializedName("notes")
    @Expose
    private String notes;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("name")
    @Expose
    private String name;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The od_for
     */
    public String getOdFor() {
        return od_for;
    }

    /**
     * @param od_for The od_for
     */
    public void setOdFor(String od_for) {
        this.od_for = od_for;
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
     * @return The to_date
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes The notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
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
