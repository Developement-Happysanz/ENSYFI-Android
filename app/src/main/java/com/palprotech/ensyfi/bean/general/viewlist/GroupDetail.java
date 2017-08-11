package com.palprotech.ensyfi.bean.general.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Narendar on 18/05/17.
 */

public class GroupDetail implements Serializable {

    @SerializedName("group_title")
    @Expose
    private String group_title;

    @SerializedName("notes")
    @Expose
    private String notes;

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
