package com.palprotech.ensyfi.bean.general.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AllHoliday implements Serializable {

    @SerializedName("START")
    @Expose
    private String START;

    @SerializedName("day")
    @Expose
    private String day;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;


    @SerializedName("status")
    @Expose
    private String status;

    /**
     * @return The START
     */
    public String getSTART() {
        return START;
    }

    /**
     * @param START The START
     */
    public void setSTART(String START) {
        this.START = START;
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
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
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

}
