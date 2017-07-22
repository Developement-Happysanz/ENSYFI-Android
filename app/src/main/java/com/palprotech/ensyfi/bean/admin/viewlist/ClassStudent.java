package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 17-07-2017.
 */

public class ClassStudent implements Serializable {

    @SerializedName("enroll_id")
    @Expose
    private String enroll_id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("admisn_no")
    @Expose
    private String admisn_no;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("admisn_year")
    @Expose
    private String admisn_year;

    @SerializedName("class_id")
    @Expose
    private String class_id;



    /**
     * @return The enroll_id
     */
    public String getEnrollId() {
        return enroll_id;
    }

    /**
     * @param enroll_id The enroll_id
     */
    public void setEnrollId(String enroll_id) {
        this.enroll_id = enroll_id;
    }

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
     * @return The admisn_no
     */
    public String getAdmisnNo() {
        return admisn_no;
    }

    /**
     * @param admisn_no The admisn_no
     */
    public void setAdmisnNo(String admisn_no) {
        this.admisn_no = admisn_no;
    }

    /**
     * @return The sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex The sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return The admisn_year
     */
    public String getAdmisnYear() {
        return admisn_year;
    }

    /**
     * @param admisn_year The admisn_year
     */
    public void setAdmisnYear(String admisn_year) {
        this.admisn_year = admisn_year;
    }

    /**
     * @return The class_id
     */
    public String getClassId() {
        return class_id;
    }

    /**
     * @param class_id The class_id
     */
    public void setClassId(String class_id) {
        this.class_id = class_id;
    }
}
