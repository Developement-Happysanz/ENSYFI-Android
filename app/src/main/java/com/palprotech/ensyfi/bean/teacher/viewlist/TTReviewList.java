package com.palprotech.ensyfi.bean.teacher.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 20-07-2017.
 */

public class TTReviewList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("reviewDetails")
    @Expose
    private ArrayList<TTReview> reviewDetails = new ArrayList<TTReview>();

    /**
     * @return The count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return The TTReview
     */
    public ArrayList<TTReview> getTTReview() {
        return reviewDetails;
    }

    /**
     * @param reviewDetails The TTReview
     */
    public void setTTReview(ArrayList<TTReview> reviewDetails) {
        this.reviewDetails = reviewDetails;
    }
}
