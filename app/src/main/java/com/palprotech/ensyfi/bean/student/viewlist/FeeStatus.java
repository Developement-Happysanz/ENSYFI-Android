package com.palprotech.ensyfi.bean.student.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 08-07-2017.
 */

public class FeeStatus implements Serializable {

    @SerializedName("term_name")
    @Expose
    private String term_name;

    @SerializedName("status")
    @Expose
    private String status;

    /**
     * @return The term_name
     */
    public String getTermName() {
        return term_name;
    }

    /**
     * @param term_name The term_name
     */
    public void setTermName(String term_name) {
        this.term_name = term_name;
    }

    /**
     * @return The status
     */
    public String getStatus() {

        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
