package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClassTeacherAttendee  implements Serializable {



    @SerializedName("enroll_id")
    @Expose
    private String enroll_id;

    @SerializedName("admission_id")
    @Expose
    private String admission_id;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("a_status")
    @Expose
    private String a_status;

    @SerializedName("name")
    @Expose
    private String name;


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

    /**
     * @return The admission_id
     */
    public String getAdmission_id() {
        return admission_id;
    }

    /**
     * @param admission_id The admission_id
     */
    public void setAdmission_id(String admission_id) {
        this.admission_id = admission_id;
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
     * @return The a_status
     */
    public String getA_status() {
        return a_status;
    }

    /**
     * @param a_status The a_status
     */
    public void setA_status(String a_status) {
        this.a_status = a_status;
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

}
