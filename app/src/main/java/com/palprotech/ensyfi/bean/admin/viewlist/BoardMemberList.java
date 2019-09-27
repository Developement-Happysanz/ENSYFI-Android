package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BoardMemberList {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("data")
    @Expose
    private ArrayList<BoardMember> data = new ArrayList<BoardMember>();

    /**
     *
     * @return
     *     The count
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @param count
     *     The count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     *
     * @return
     *     The BoardMember
     */
    public ArrayList<BoardMember> getBoardMember() {
        return data;
    }

    /**
     *
     * @param data
     *     The BoardMember
     */
    public void setBoardMember(ArrayList<BoardMember> data) {
        this.data = data;
    }
}