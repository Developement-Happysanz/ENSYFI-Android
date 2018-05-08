package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TTDays implements Serializable {

    @SerializedName("day_id")
    @Expose
    private String day_id;

    @SerializedName("list_day")
    @Expose
    private String list_day;

    /**
     * @return The day_id
     */
    public String getDayId() {
        return day_id;
    }

    /**
     * @param day_id The day_id
     */
    public void setDayId(String day_id) {
        this.day_id = day_id;
    }

    /**
     * @return The list_day
     */
    public String getDay() {
        return list_day;
    }

    /**
     * @param list_day The list_day
     */
    public void setDay(String list_day) {
        this.list_day = list_day;
    }

}
