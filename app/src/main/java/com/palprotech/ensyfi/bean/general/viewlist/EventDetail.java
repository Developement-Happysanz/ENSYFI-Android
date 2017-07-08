package com.palprotech.ensyfi.bean.general.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Narendar on 18/05/17.
 */

public class EventDetail implements Serializable {

    @SerializedName("sub_event_name")
    @Expose
    private String sub_event_name;

    @SerializedName("name")
    @Expose
    private String name;

    /**
     * @return The sub_event_name
     */
    public String getSub_event_name() {
        return sub_event_name;
    }

    /**
     * @param sub_event_name The sub_event_name
     */
    public void setSub_event_name(String sub_event_name) {
        this.sub_event_name = sub_event_name;
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
