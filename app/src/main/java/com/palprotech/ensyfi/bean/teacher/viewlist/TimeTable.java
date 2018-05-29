package com.palprotech.ensyfi.bean.teacher.viewlist;

public class TimeTable {

    private String class_id;
    private String subject_id;
    private String subject_name;
    private String teacher_id;
    private String name;
    private String day;
    private String period;
    private String sec_name;
    private String class_name;
    private String from_time;
    private String to_time;
    private String is_break;

    public String getClassId() {
        return class_id;
    }

    public void setClassId(String class_id) {
        this.class_id = class_id;
    }

    public String getSubjectName() {
        return subject_name;
    }

    public void setSubjectName(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectId() {
        return subject_id;
    }

    public void setSubjectId(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getTeacherId() {
        return teacher_id;
    }

    public void setTeacherId(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSecName() {
        return sec_name;
    }

    public void setSecName(String sec_name) {
        this.sec_name = sec_name;
    }

    public String getClassName() {
        return class_name;
    }

    public void setClassName(String class_name) {
        this.class_name = class_name;
    }

    public String getFromTime() {
        return from_time;
    }

    public void setFromTime(String from_time) {
        this.from_time = from_time;
    }

    public String getToTime() {
        return to_time;
    }

    public void setToTime(String to_time) {
        this.to_time = to_time;
    }

    public String getIsBreak() {
        return is_break;
    }

    public void setIsBreak(String is_break) {
        this.is_break = is_break;
    }
}
