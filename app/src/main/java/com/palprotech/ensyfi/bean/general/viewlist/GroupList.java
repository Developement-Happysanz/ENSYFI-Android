package com.palprotech.ensyfi.bean.general.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by Admin on 15-05-2017.
 */

public class GroupList implements Serializable{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("group_title_id")
    @Expose
    private String group_title_id;

    @SerializedName("group_title")
    @Expose
    private String group_title;

    @SerializedName("notes")
    @Expose
    private String notes;

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
     * @return The group_title_id
     */
    public String getGroup_title_id() {
        return group_title_id;
    }

    /**
     * @param group_title_id The group_title_id
     */
    public void setGroup_title_id(String group_title_id) {
        this.group_title_id = group_title_id;
    }

    /**
     * @return The group_title_id
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

}
