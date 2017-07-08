package com.palprotech.ensyfi.bean.general.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 08-07-2017.
 */

public class Circular implements Serializable {

    @SerializedName("circular_type")
    @Expose
    private String circular_type;

    @SerializedName("circular_title")
    @Expose
    private String circular_title;

    @SerializedName("circular_description")
    @Expose
    private String circular_description;

    @SerializedName("circular_date")
    @Expose
    private String circular_date;

    /**
     * @return The circular_type
     */
    public String getCircularType() {
        return circular_type;
    }

    /**
     * @param circular_type The circular_type
     */
    public void setCircularType(String circular_type) {
        this.circular_type = circular_type;
    }

    /**
     * @return The circular_title
     */
    public String getCircularTitle() {
        return circular_title;
    }

    /**
     * @param circular_title The circular_title
     */
    public void setCircularTitle(String circular_title) {
        this.circular_title = circular_title;
    }

    /**
     * @return The circular_description
     */
    public String getCircularDescription() {
        return circular_description;
    }

    /**
     * @param circular_description The circular_description
     */
    public void setCircularDescription(String circular_description) {
        this.circular_description = circular_description;
    }

    /**
     * @return The circular_date
     */
    public String getCircularDate() {
        return circular_date;
    }

    /**
     * @param circular_date The circular_date
     */
    public void setCircularDate(String circular_date) {
        this.circular_date = circular_date;
    }
}
