package com.palprotech.ensyfi.bean.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 22-05-2017.
 */

public class EventOrganiser {

    @SerializedName("sub_event_name")
    @Expose
    private String sub_event_name;

    @SerializedName("name")
    @Expose
    private String name;

    /**
     * @return The sub_event_name
     */
    public String getSubEventName() {
        return sub_event_name;
    }

    /**
     * @param sub_event_name The sub_event_name
     */
    public void setSubEventName(String sub_event_name) {
        this.sub_event_name = sub_event_name;
    }

    /**
     * @return The name
     */
    public String getEventOrganiserName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setEventOrganiserName(String name) {
        this.name = name;
    }
}
