package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubstitutionClass implements Serializable {

    @SerializedName("class_sec_name")
    @Expose
    private String class_sec_name;

    @SerializedName("class_sec_id")
    @Expose
    private String class_sec_id;

    @SerializedName("period")
    @Expose
    private String period;

    @SerializedName("sub_date")
    @Expose
    private String sub_date;

    /**
     * @return The class_sec_name
     */
    public String getclass_sec_name() {
        return class_sec_name;
    }

    /**
     * @param class_sec_name The class_sec_name
     */
    public void setclass_sec_name(String class_sec_name) {
        this.class_sec_name = class_sec_name;
    }

    /**
     * @return The class_sec_id
     */
    public String getclass_sec_id() {
        return class_sec_id;
    }

    /**
     * @param class_sec_id The class_sec_id
     */
    public void setclass_sec_id(String class_sec_id) {
        this.class_sec_id = class_sec_id;
    }

    /**
     * @return The period
     */
    public String getperiod() {
        return period;
    }

    /**
     * @param period The period
     */
    public void setperiod(String period) {
        this.period = period;
    }

    /**
     * @return The sub_date
     */
    public String getsub_date() {
        return sub_date;
    }

    /**
     * @param sub_date The sub_date
     */
    public void setsub_date(String sub_date) {
        this.sub_date = sub_date;
    }

}