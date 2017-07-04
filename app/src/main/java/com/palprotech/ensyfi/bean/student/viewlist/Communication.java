package com.palprotech.ensyfi.bean.student.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 18-05-2017.
 */

public class Communication implements Serializable {

    @SerializedName("commu_title")
    @Expose
    private String commu_title;

    @SerializedName("commu_details")
    @Expose
    private String commu_details;

    @SerializedName("commu_date")
    @Expose
    private String commu_date;

    /**
     * @return The commu_title
     */
    public String getCommunicationTitle() {
        return commu_title;
    }

    /**
     * @param commu_title The commu_title
     */
    public void setCommunicationTitle(String commu_title) {
        this.commu_title = commu_title;
    }

    /**
     * @return The commu_details
     */
    public String getCommunicationDetails() {
        return commu_details;
    }

    /**
     * @param commu_details The commu_details
     */
    public void setCommunicationDetails(String commu_details) {
        this.commu_details = commu_details;
    }

    /**
     * @return The commu_date
     */
    public String getCommunicationDate() {
        return commu_date;
    }

    /**
     * @param commu_date The commu_date
     */
    public void setCommunicationDate(String commu_date) {
        this.commu_date = commu_date;
    }
}
