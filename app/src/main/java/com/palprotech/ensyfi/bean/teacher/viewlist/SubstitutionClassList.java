package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubstitutionClassList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("substitution_details")
    @Expose
    private ArrayList<SubstitutionClass> ctestmarkDetails = new ArrayList<SubstitutionClass>();

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
     *     The ClassTestMark
     */
    public ArrayList<SubstitutionClass> getClassTestMark() {
        return ctestmarkDetails;
    }

    /**
     *
     * @param ctestmarkDetails
     *     The ClassTestMark
     */
    public void setClassTestMark(ArrayList<SubstitutionClass> ctestmarkDetails) {
        this.ctestmarkDetails = ctestmarkDetails;
    }

}