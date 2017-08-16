package com.palprotech.ensyfi.bean.admin.support;

/**
 * Created by Admin on 17-07-2017.
 */

public class StoreSection {
    private String sectionId;
    private String sectionName;

    public StoreSection(String sectionId, String sectionName) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }


    //to display object as a string in spinner
    @Override
    public String toString() {
        return sectionName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StoreClass){
            StoreSection c = (StoreSection )obj;
            if(c.getSectionName().equals(sectionName) && c.getSectionId()==sectionId ) return true;
        }

        return false;
    }
}
