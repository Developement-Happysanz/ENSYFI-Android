package com.palprotech.ensyfi.bean.general.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by Admin on 15-05-2017.
 */

public class Event implements Serializable {

    @SerializedName("event_id")
    @Expose
    private String event_id;

    @SerializedName("year_id")
    @Expose
    private String year_id;

    @SerializedName("event_name")
    @Expose
    private String event_name;

    @SerializedName("event_date")
    @Expose
    private String event_date;

    @SerializedName("event_details")
    @Expose
    private String event_details;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("latitude")
    @Expose
    private String latitude = "0.0";

    @SerializedName("longitude")
    @Expose
    private String longitude = "0.0";

    @SerializedName("sub_event_status")
    @Expose
    private String sub_event_status;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    /**
     * @return The event_id
     */
    public String getEvent_id() {
        return event_id;
    }

    /**
     * @param event_id The event_id
     */
    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    /**
     * @return The year_id
     */
    public String getYear_id() {
        return year_id;
    }

    /**
     * @param year_id The year_id
     */
    public void setYear_id(String year_id) {
        this.year_id = year_id;
    }

    /**
     * @return The event_name
     */
    public String getEvent_name() {
        return event_name;
    }

    /**
     * @param event_name The event_name
     */
    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    /**
     * @return The event_date
     */
    public String getEvent_date() {
        return event_date;
    }

    /**
     * @param event_date The event_date
     */
    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    /**
     * @return The event_details
     */
    public String getEvent_details() {
        return event_details;
    }

    /**
     * @param event_details The event_details
     */
    public void setEvent_details(String event_details) {
        this.event_details = event_details;
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
     * @return The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return The sub_event_status
     */
    public String getSub_event_status() {
        return sub_event_status;
    }

    /**
     * @param sub_event_status The sub_event_status
     */
    public void setSub_event_status(String sub_event_status) {
        this.sub_event_status = sub_event_status;
    }

    /**
     * @return The created_at
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * @param created_at The created_at
     */
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

}
