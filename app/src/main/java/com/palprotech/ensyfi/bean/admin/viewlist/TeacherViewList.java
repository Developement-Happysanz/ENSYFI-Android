package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 18-07-2017.
 */

public class TeacherViewList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("data")
    @Expose
    private ArrayList<TeacherView> data = new ArrayList<TeacherView>();

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
     *     The TeacherView
     */
    public ArrayList<TeacherView> getTeacherView() {
        return data;
    }

    /**
     *
     * @param data
     *     The TeacherView
     */
    public void setTeacherView(ArrayList<TeacherView> data) {
        this.data = data;
    }
}
