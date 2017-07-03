package com.palprotech.ensyfi.bean.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 18-05-2017.
 */

public class ExamDetailsViewList {
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("examDetails")
    @Expose
    private ArrayList<ExamDetailsView> examDetails = new ArrayList<ExamDetailsView>();

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
     * @return The examDetails
     */
    public ArrayList<ExamDetailsView> getExamDetailView() {
        return examDetails;
    }

    /**
     * @param examDetails The examDetails
     */
    public void setEvents(ArrayList<ExamDetailsView> examDetails) {
        this.examDetails = examDetails;
    }
}
