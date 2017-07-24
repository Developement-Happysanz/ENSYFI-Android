package com.palprotech.ensyfi.bean.teacher.viewlist;

/**
 * Created by Admin on 13-07-2017.
 */

public class ClassTestHomeWork {

    public int id = -1;
    public String title;
    public String subject_name;
    public String hw_type;
    public String test_date;
    public String due_date;

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        id = Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String Title) {
        title = Title;
    }

    public String getSubjectName() {
        return subject_name;
    }

    public void setSubjectName(String SubjectName) {
        subject_name = SubjectName;
    }

    public String getHomeWorkType() {
        return hw_type;
    }

    public void setHomeWorkType(String HWType) {
        hw_type = HWType;
    }

    public String getTestDate() {
        return test_date;
    }

    public void setTestDate(String TestDate) {
        test_date = TestDate;
    }

    public String getDueDate() {
        return due_date;
    }

    public void setDueDate(String DueDate) {
        due_date = DueDate;
    }
}
