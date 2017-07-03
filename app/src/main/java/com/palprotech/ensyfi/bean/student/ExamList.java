package com.palprotech.ensyfi.bean.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 17-05-2017.
 */

public class ExamList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("Exams")
    @Expose
    private ArrayList<Exams> exams = new ArrayList<Exams>();

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
     *     The exams
     */
    public ArrayList<Exams> getExams() {
        return exams;
    }

    /**
     *
     * @param exams
     *     The exams
     */
    public void setExams(ArrayList<Exams> exams) {
        this.exams = exams;
    }
}

