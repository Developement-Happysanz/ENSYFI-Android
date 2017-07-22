package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 17-07-2017.
 */

public class ClassStudentList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("data")
    @Expose
    private ArrayList<ClassStudent> data = new ArrayList<ClassStudent>();

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
     *     The ClassStudent
     */
    public ArrayList<ClassStudent> getClassStudent() {
        return data;
    }

    /**
     *
     * @param data
     *     The ClassStudent
     */
    public void setClassStudent(ArrayList<ClassStudent> data) {
        this.data = data;
    }
}
