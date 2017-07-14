package com.palprotech.ensyfi.bean.teacher.viewlist;

/**
 * Created by Admin on 15-07-2017.
 */

public class AcademicExamDetails {

    public int id = -1;
    public String subject_name;
    public String exam_date;
    public String times;


    public int getId() {
        return id;
    }

    public void setId(int Id) {
        id = Id;
    }

    public String getSubjectName() {
        return subject_name;
    }

    public void setSubjectName(String SubjectName) {
        subject_name = SubjectName;
    }

    public String getExamDate() {
        return exam_date;
    }

    public void setExamDate(String ExamDate) {
        exam_date = ExamDate;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String Times) {
        times = Times;
    }
}
