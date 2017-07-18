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
}
