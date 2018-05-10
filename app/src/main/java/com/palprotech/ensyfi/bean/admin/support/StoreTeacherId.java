package com.palprotech.ensyfi.bean.admin.support;

public class StoreTeacherId {

    private String teacherId;
    private String teacherName;

    public StoreTeacherId(String teacherId, String teacherName) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }


    //to display object as a string in spinner
    @Override
    public String toString() {
        return teacherName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StoreTeacherId){
            StoreTeacherId c = (StoreTeacherId )obj;
            if(c.getTeacherName().equals(teacherName) && c.getTeacherId()==teacherId ) return true;
        }

        return false;
    }
}
