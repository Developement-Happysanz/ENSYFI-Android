package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClassTeacherCtHwOverall  implements Serializable {

    @SerializedName("hw_date")
    @Expose
    private String hw_date;

    @SerializedName("hw_count")
    @Expose
    private String hw_count;

    @SerializedName("ht_count")
    @Expose
    private String ht_count;


    /**
     * @return The hw_date
     */
    public String getHw_date() {
        return hw_date;
    }

    /**
     * @param hw_date The hw_date
     */
    public void setHw_date(String hw_date) {
        this.hw_date = hw_date;
    }

    /**
     * @return The hw_count
     */
    public String getHw_count() {
        return hw_count;
    }

    /**
     * @param hw_count The hw_count
     */
    public void setHw_count(String hw_count) {
        this.hw_count = hw_count;
    }

    /**
     * @return The ht_count
     */
    public String getHt_count() {
        return ht_count;
    }

    /**
     * @param ht_count The ht_count
     */
    public void setHt_count(String ht_count) {
        this.ht_count = ht_count;
    }
    
}
