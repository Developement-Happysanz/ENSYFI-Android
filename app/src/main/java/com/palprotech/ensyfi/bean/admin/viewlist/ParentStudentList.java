package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 18-07-2017.
 */

public class ParentStudentList {
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("data")
    @Expose
    private ArrayList<ParentStudent> data = new ArrayList<ParentStudent>();

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
     *     The ParentStudent
     */
    public ArrayList<ParentStudent> getParentStudent() {
        return data;
    }

    /**
     *
     * @param data
     *     The ParentStudent
     */
    public void setParentStudent(ArrayList<ParentStudent> data) {
        this.data = data;
    }
}
