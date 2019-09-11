package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherAttendance;

import java.io.Serializable;
import java.util.ArrayList;

public class AttendanceClassList {
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("attendence_list")
    @Expose
    private ArrayList<AttendanceClass> classTeacherDetails = new ArrayList<AttendanceClass>();

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
     * @return The classTeacherDetails
     */
    public ArrayList<AttendanceClass> getClassDetails() {
        return classTeacherDetails;
    }

    /**
     * @param classTeacherDetails The classTeacherDetails
     */
    public void setClassTeacherDetails(ArrayList<AttendanceClass> classTeacherDetails) {
        this.classTeacherDetails = classTeacherDetails;
    }
}