package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClassTeacherAttendeeList {
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("ct_student_history")
    @Expose
    private ArrayList<ClassTeacherAttendee> classAttendeeDetails = new ArrayList<ClassTeacherAttendee>();

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
     *     The classAttendeeDetails
     */
    public ArrayList<ClassTeacherAttendee> getClassTeacherAttendeeDetails() {
        return classAttendeeDetails;
    }

    /**
     *
     * @param classAttendeeDetails
     *     The classAttendeeDetails
     */
    public void setClassTeacherDetails(ArrayList<ClassTeacherAttendee> classAttendeeDetails) {
        this.classAttendeeDetails = classAttendeeDetails;
    }
}
