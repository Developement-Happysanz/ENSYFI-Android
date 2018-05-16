package com.palprotech.ensyfi.bean.admin.support;

public class StoreRoleId {

    private String roleId;
    private String roleName;
    private String staffStatus;
    private String status;

    public StoreRoleId(String roleId, String roleName, String staffStatus, String status) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.staffStatus = staffStatus;
        this.status = status;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String teacherId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String teacherName) {
        this.roleName = roleName;
    }

    public String getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(String staffStatus) {
        this.staffStatus = staffStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    //to display object as a string in spinner
    @Override
    public String toString() {
        return roleName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StoreRoleId){
            StoreRoleId c = (StoreRoleId )obj;
            if(c.getRoleName().equals(roleName) && c.getRoleId()==roleId ) return true;
        }

        return false;
    }
}
