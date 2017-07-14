package com.palprotech.ensyfi.bean.teacher.viewlist;

/**
 * Created by Admin on 14-07-2017.
 */

public class AcademicExams {

    public int id = -1;
    public String exam_name;
    public String Fromdate;
    public String Todate;

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        id = Id;
    }

    public String getExamName() {
        return exam_name;
    }

    public void setExamName(String ExamName) {
        exam_name = ExamName;
    }

    public String getFromDate() {
        return Fromdate;
    }

    public void setFromDate(String FromDate) {
        Fromdate = FromDate;
    }

    public String getToDate() {
        return Todate;
    }

    public void setToDate(String ToDate) {
        Todate = ToDate;
    }

}
