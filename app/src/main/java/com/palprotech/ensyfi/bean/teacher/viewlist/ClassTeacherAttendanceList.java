package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClassTeacherAttendanceList {
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("ct_attendance_history")
    @Expose
    private ArrayList<ClassTeacherAttendance> classTeacherDetails = new ArrayList<ClassTeacherAttendance>();

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
    public ArrayList<ClassTeacherAttendance> getClassTeacherDetails() {
        return classTeacherDetails;
    }

    /**
     * @param classTeacherDetails The classTeacherDetails
     */
    public void setClassTeacherDetails(ArrayList<ClassTeacherAttendance> classTeacherDetails) {
        this.classTeacherDetails = classTeacherDetails;
    }
}
