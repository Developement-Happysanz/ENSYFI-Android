package com.palprotech.ensyfi.bean.admin.support;

/**
 * Created by Admin on 17-07-2017.
 */

public class StoreClass {

    private String classId;
    private String className;

    public StoreClass(String classId, String className) {
        this.classId = classId;
        this.className = className;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    //to display object as a string in spinner
    @Override
    public String toString() {
        return className;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StoreClass){
            StoreClass c = (StoreClass )obj;
            if(c.getClassName().equals(className) && c.getClassId()==classId ) return true;
        }

        return false;
    }
}
