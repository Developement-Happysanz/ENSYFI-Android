package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 19-07-2017.
 */

public class ExamResultList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("marksDetails")
    @Expose
    private ArrayList<ExamResult> marksDetails = new ArrayList<ExamResult>();

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
     *     The ExamResult
     */
    public ArrayList<ExamResult> getExamResult() {
        return marksDetails;
    }

    /**
     *
     * @param marksDetails
     *     The ExamResult
     */
    public void setExamResult(ArrayList<ExamResult> marksDetails) {
        this.marksDetails = marksDetails;
    }
}
