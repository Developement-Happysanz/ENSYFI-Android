package com.palprotech.ensyfi.bean.admin.viewlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Admin on 18-07-2017.
 */

public class TeacherView implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("age")
    @Expose
    private String age;

    @SerializedName("class_teacher")
    @Expose
    private String class_teacher;

    @SerializedName("class_name")
    @Expose
    private String class_name;

    @SerializedName("sec_name")
    @Expose
    private String sec_name;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("subject_name")
    @Expose
    private String subject_name;

    @SerializedName("teacher_id")
    @Expose
    private String teacher_id;

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
     * @return The age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age The age
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return The class_teacher
     */
    public String getClassTeacher() {
        return class_teacher;
    }

    /**
     * @param class_teacher The class_teacher
     */
    public void setClassTeacher(String class_teacher) {
        this.class_teacher = class_teacher;
    }

    /**
     * @return The class_name
     */
    public String getClassName() {
        return class_name;
    }

    /**
     * @param class_name The class_name
     */
    public void setClassName(String class_name) {
        this.class_name = class_name;
    }

    /**
     * @return The sec_name
     */
    public String getSectionName() {
        return sec_name;
    }

    /**
     * @param sec_name The sec_name
     */
    public void setSectionName(String sec_name) {
        this.sec_name = sec_name;
    }

    /**
     * @return The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return The subject_name
     */
    public String getSubjectName() {
        return subject_name;
    }

    /**
     * @param subject_name The subject_name
     */
    public void setSubjectName(String subject_name) {
        this.subject_name = subject_name;
    }

    /**
     * @return The teacher_id
     */
    public String getTeacherId() {
        return teacher_id;
    }

    /**
     * @param teacher_id The teacher_id
     */
    public void setTeacherId(String teacher_id) {
        this.teacher_id = teacher_id;
    }

}
