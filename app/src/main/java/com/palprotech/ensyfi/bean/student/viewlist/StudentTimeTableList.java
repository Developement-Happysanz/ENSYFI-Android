package com.palprotech.ensyfi.bean.student.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StudentTimeTableList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("timeTable")
    @Expose
    private ArrayList<StudentTimeTable> studentTT = new ArrayList<StudentTimeTable>();

    /**
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
     * @return The studentTT
     */
    public ArrayList<StudentTimeTable> getStudentTT() {
        return studentTT;
    }

    /**
     * @param studentTT The studentTT
     */
    public void setStudentTT(ArrayList<StudentTimeTable> studentTT) {
        this.studentTT = studentTT;
    }
}