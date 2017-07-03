package com.palprotech.ensyfi.bean.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Narendar on 17/05/17.
 */

public class EventList {
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("eventDetails")
    @Expose
    private ArrayList<Event> eventDetails = new ArrayList<Event>();

    /**
     *
     * @return
     *     The count
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @param count
     *     The count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     *
     * @return
     *     The events
     */
    public ArrayList<Event> getEvents() {
        return eventDetails;
    }

    /**
     *
     * @param eventDetails
     *     The events
     */
    public void setEvents(ArrayList<Event> eventDetails) {
        this.eventDetails = eventDetails;
    }
}

