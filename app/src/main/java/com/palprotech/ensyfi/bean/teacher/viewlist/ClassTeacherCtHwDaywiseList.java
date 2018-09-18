package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClassTeacherCtHwDaywiseList {
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("hwdayDetails")
    @Expose
    private ArrayList<ClassTeacherCtHwDaywise> classTeacherCtHwDaywiseDetails = new ArrayList<ClassTeacherCtHwDaywise>();

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
     *     The classTeacherCtHwDaywiseDetails
     */
    public ArrayList<ClassTeacherCtHwDaywise> getClassTeacherCtHwDaywise() {
        return classTeacherCtHwDaywiseDetails;
    }

    /**
     *
     * @param classTeacherCtHwDaywiseDetails
     *     The classTeacherCtHwDaywiseDetails
     */
    public void setClassTeacherCtHwDaywise(ArrayList<ClassTeacherCtHwDaywise> classTeacherCtHwDaywiseDetails) {
        this.classTeacherCtHwDaywiseDetails = classTeacherCtHwDaywiseDetails;
    }
}
