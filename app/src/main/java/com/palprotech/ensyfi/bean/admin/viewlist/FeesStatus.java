package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 19-07-2017.
 */

public class FeesStatus implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("student_id")
    @Expose
    private String student_id;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("paid_by")
    @Expose
    private String paid_by;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;


    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
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

    /**
     * @return The paid_by
     */
    public String getPaidBy() {
        return paid_by;
    }

    /**
     * @param paid_by The paid_by
     */
    public void setPaidBy(String paid_by) {
        this.paid_by = paid_by;
    }

    /**
     * @return The updated_at
     */
    public String getUpdatedAt() {
        return updated_at;
    }

    /**
     * @param updated_at The updated_at
     */
    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }
}
