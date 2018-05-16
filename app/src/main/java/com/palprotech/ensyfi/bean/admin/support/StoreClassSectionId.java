package com.palprotech.ensyfi.bean.admin.support;

public class StoreClassSectionId {

    private String classSectionId;
    private String classSectionName;

    public StoreClassSectionId(String classSectionId, String classSectionName) {
        this.classSectionId = classSectionId;
        this.classSectionName = classSectionName;
    }

    public String getClassSectionId() {
        return classSectionId;
    }

    public void setClassSectionId(String classSectionId) {
        this.classSectionId = classSectionId;
    }

    public String getClassSectionName() {
        return classSectionName;
    }

    public void setClassSectionName(String classSectionName) {
        this.classSectionName = classSectionName;
    }


    //to display object as a string in spinner
    @Override
    public String toString() {
        return classSectionName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StoreClassSectionId) {
            StoreClassSectionId c = (StoreClassSectionId) obj;
            if (c.getClassSectionName().equals(classSectionName) && c.getClassSectionId() == classSectionId)
                return true;
        }

        return false;
    }
}
