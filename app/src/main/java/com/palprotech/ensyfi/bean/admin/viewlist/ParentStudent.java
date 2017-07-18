package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 18-07-2017.
 */

public class ParentStudent implements Serializable {

    @SerializedName("student_id")
    @Expose
    private String student_id;

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

    @SerializedName("father_name")
    @Expose
    private String father_name;

    @SerializedName("mother_name")
    @Expose
    private String mother_name;

    @SerializedName("guardn_name")
    @Expose
    private String guardn_name;

    @SerializedName("parent_id")
    @Expose
    private String parent_id;

    /**
     * @return The student_id
     */
    public String getStudentId() {
        return student_id;
    }

    /**
     * @param student_id The student_id
     */
    public void setStudentId(String student_id) {
        this.student_id = student_id;
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
    public String getAdmnNo() {
        return admisn_no;
    }

    /**
     * @param admisn_no The admisn_no
     */
    public void setAdmnNo(String admisn_no) {
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
    public String getAdmnYear() {
        return admisn_year;
    }

    /**
     * @param admisn_year The admisn_year
     */
    public void setAdmnYear(String admisn_year) {
        this.admisn_year = admisn_year;
    }

    /**
     * @return The father_name
     */
    public String getFatherName() {
        return father_name;
    }

    /**
     * @param father_name The father_name
     */
    public void setFatherName(String father_name) {
        this.father_name = father_name;
    }

    /**
     * @return The mother_name
     */
    public String getMotherName() {
        return mother_name;
    }

    /**
     * @param mother_name The mother_name
     */
    public void setMotherName(String mother_name) {
        this.mother_name = mother_name;
    }

    /**
     * @return The guardn_name
     */
    public String getGuardnName() {
        return guardn_name;
    }

    /**
     * @param guardn_name The guardn_name
     */
    public void setGuardnName(String guardn_name) {
        this.guardn_name = guardn_name;
    }

    /**
     * @return The parent_id
     */
    public String getParentId() {
        return parent_id;
    }

    /**
     * @param parent_id The parent_id
     */
    public void setParentId(String parent_id) {
        this.parent_id = parent_id;
    }
}
