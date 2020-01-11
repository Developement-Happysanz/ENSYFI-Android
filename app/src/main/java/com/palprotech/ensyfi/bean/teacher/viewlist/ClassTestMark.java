package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 19-07-2017.
 */

public class ClassTestMark implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("marks")
    @Expose
    private String marks;

    @SerializedName("enroll_id")
    @Expose
    private String enroll_id;

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The marks
     */
    public String getMarks() {
        return marks;
    }

    /**
     * @param marks The marks
     */
    public void setMarks(String marks) {
        this.marks = marks;
    }

    /**
     * @return The enroll_id
     */
    public String getEnroll_id() {
        return enroll_id;
    }

    /**
     * @param enroll_id The enroll_id
     */
    public void setEnroll_id(String enroll_id) {
        this.enroll_id = enroll_id;
    }
}
