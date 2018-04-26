package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ExamDutyList {

    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("examdutyDetails")
    @Expose
    private ArrayList<ExamDuty> examDuties = new ArrayList<ExamDuty>();

    /**
     *
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
     * @return The ExamDuty
     *
     */
    public ArrayList<ExamDuty> getExamDuties() {
        return examDuties;
    }

    /**
     * @param examDuties The ExamDuty
     */
    public void setExamDuties(ArrayList<ExamDuty> examDuties) {
        this.examDuties = examDuties;
    }
}
