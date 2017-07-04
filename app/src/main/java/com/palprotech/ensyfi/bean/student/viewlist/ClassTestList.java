package com.palprotech.ensyfi.bean.student.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 15-05-2017.
 */

public class ClassTestList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("homeworkDetails")
    @Expose
    private ArrayList<ClassTest> homeworkDetails = new ArrayList<ClassTest>();

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
     *     The classTest
     */
    public ArrayList<ClassTest> getClassTest() {
        return homeworkDetails;
    }

    /**
     *
     * @param homeworkDetails
     *     The classTest
     */
    public void setClassTest(ArrayList<ClassTest> homeworkDetails) {
        this.homeworkDetails = homeworkDetails;
    }
}
