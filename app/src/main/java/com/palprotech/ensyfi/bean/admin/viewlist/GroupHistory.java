package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GroupHistory implements Serializable {

    @SerializedName("group_title")
    @Expose
    private String group_title;

    @SerializedName("group_lead_id")
    @Expose
    private String group_lead_id;

    @SerializedName("notes")
    @Expose
    private String notes;

    @SerializedName("created_by")
    @Expose
    private String created_by;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("notification_type")
    @Expose
    private String notification_type;

    @SerializedName("name")
    @Expose
    private String name;


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
     * @return The notes
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
     * @return The notification_type
     */
    public String getNotificationType() {
        return notification_type;
    }

    /**
     * @param notification_type The notification_type
     */
    public void setNotificationType(String notification_type) {
        this.notification_type = notification_type;
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
