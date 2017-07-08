package com.palprotech.ensyfi.bean.general.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 22-05-2017.
 */

public class EventOrganiserList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("subeventDetails")
    @Expose
    private ArrayList<EventOrganiser> subeventDetails = new ArrayList<EventOrganiser>();

    /**
     * @return The count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return The subeventDetails
     */
    public ArrayList<EventOrganiser> getEventOrganiserList() {
        return subeventDetails;
    }

    /**
     * @param subeventDetails The subeventDetails
     */
    public void setEventOrganiserList(ArrayList<EventOrganiser> subeventDetails) {
        this.subeventDetails = subeventDetails;
    }
}
