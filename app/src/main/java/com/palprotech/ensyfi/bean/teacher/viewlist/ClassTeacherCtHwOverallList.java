package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClassTeacherCtHwOverallList {
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("hwDates")
    @Expose
    private ArrayList<ClassTeacherCtHwOverall> classTeacherCtHwOverallDetails = new ArrayList<ClassTeacherCtHwOverall>();

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
     *     The classTeacherCtHwOverallDetails
     */
    public ArrayList<ClassTeacherCtHwOverall> getClassTeacherCtHwOverallDetails() {
        return classTeacherCtHwOverallDetails;
    }

    /**
     *
     * @param classTeacherCtHwOverallDetails
     *     The classTeacherCtHwOverallDetails
     */
    public void setClassTeacherCtHwOverallDetails(ArrayList<ClassTeacherCtHwOverall> classTeacherCtHwOverallDetails) {
        this.classTeacherCtHwOverallDetails = classTeacherCtHwOverallDetails;
    }
}
