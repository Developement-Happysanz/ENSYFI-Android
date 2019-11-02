package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SpecialClassList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("special_details")
    @Expose
    private ArrayList<SpecialClass> ctestmarkDetails = new ArrayList<SpecialClass>();

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
    public ArrayList<SpecialClass> getClassTestMark() {
        return ctestmarkDetails;
    }

    /**
     *
     * @param ctestmarkDetails
     *     The ClassTestMark
     */
    public void setClassTestMark(ArrayList<SpecialClass> ctestmarkDetails) {
        this.ctestmarkDetails = ctestmarkDetails;
    }

}