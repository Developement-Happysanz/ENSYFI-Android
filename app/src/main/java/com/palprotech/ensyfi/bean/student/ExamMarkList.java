package com.palprotech.ensyfi.bean.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 25-05-2017.
 */

public class ExamMarkList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("marksDetails")
    @Expose
    private ArrayList<ExamMark> marksDetails = new ArrayList<ExamMark>();

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
     * @return The marksDetails
     */
    public ArrayList<ExamMark> getExamMarkView() {
        return marksDetails;
    }

    /**
     * @param marksDetails The marksDetails
     */
    public void setEvents(ArrayList<ExamMark> marksDetails) {
        this.marksDetails = marksDetails;
    }
}
