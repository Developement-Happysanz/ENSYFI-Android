package com.palprotech.ensyfi.bean.general.support;

/**
 * Created by Admin on 17-07-2017.
 */

public class StoreGroup {

    private String groupId;
    private String groupTitle;

    public StoreGroup (String groupId, String groupTitle) {
        this.groupId = groupId;
        this.groupTitle = groupTitle;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }


    //to display object as a string in spinner
    @Override
    public String toString() {
        return groupTitle;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StoreGroup){
            StoreGroup g = (StoreGroup )obj;
            if(g.getGroupTitle().equals(groupTitle) && g.getGroupId()==groupId ) return true;
        }

        return false;
    }
}

