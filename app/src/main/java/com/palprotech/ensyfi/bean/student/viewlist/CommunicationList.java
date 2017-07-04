package com.palprotech.ensyfi.bean.student.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 18-05-2017.
 */

public class CommunicationList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("communicationDetails")
    @Expose
    private ArrayList<Communication> communicationDetails = new ArrayList<Communication>();

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
     * @return The Communication
     */
    public ArrayList<Communication> getCommunication() {
        return communicationDetails;
    }

    /**
     * @param communicationDetails The Communication
     */
    public void setClassTest(ArrayList<Communication> communicationDetails) {
        this.communicationDetails = communicationDetails;
    }
}
