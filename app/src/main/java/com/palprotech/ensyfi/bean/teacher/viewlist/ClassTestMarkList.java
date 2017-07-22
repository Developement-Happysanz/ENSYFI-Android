package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 19-07-2017.
 */

public class ClassTestMarkList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("ctestmarkDetails")
    @Expose
    private ArrayList<ClassTestMark> ctestmarkDetails = new ArrayList<ClassTestMark>();

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
    public ArrayList<ClassTestMark> getClassTestMark() {
        return ctestmarkDetails;
    }

    /**
     *
     * @param ctestmarkDetails
     *     The ClassTestMark
     */
    public void setClassTestMark(ArrayList<ClassTestMark> ctestmarkDetails) {
        this.ctestmarkDetails = ctestmarkDetails;
    }

}
