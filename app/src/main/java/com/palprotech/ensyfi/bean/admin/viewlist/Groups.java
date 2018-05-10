package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Groups implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("group_title")
    @Expose
    private String group_title;

    @SerializedName("group_lead_id")
    @Expose
    private String group_lead_id;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("created_by")
    @Expose
    private String created_by;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_by")
    @Expose
    private String updated_by;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("lead_name")
    @Expose
    private String lead_name;

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
     * @return The group_title
     */
    public String getGroup_title() {
        return group_title;
    }

    /**
     * @param group_title The group_title
     */
    public void setGroup_title(String group_title) {
        this.group_title = group_title;
    }

    /**
     * @return The group_lead_id
     */
    public String getGroup_lead_id() {
        return group_lead_id;
    }

    /**
     * @param group_lead_id The group_lead_id
     */
    public void setGroup_lead_id(String group_lead_id) {
        this.group_lead_id = group_lead_id;
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
     * @return The created_by
     */
    public String getCreated_by() {
        return created_by;
    }

    /**
     * @param created_by The created_by
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
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

    /**
     * @return The updated_by
     */
    public String getUpdated_by() {
        return updated_by;
    }

    /**
     * @param updated_by The updated_by
     */
    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    /**
     * @return The updated_at
     */
    public String getUpdated_at() {
        return updated_at;
    }

    /**
     * @param updated_at The updated_at
     */
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * @return The lead_name
     */
    public String getLead_name() {
        return lead_name;
    }

    /**
     * @param lead_name The lead_name
     */
    public void setLead_name(String lead_name) {
        this.lead_name = lead_name;
    }


}
