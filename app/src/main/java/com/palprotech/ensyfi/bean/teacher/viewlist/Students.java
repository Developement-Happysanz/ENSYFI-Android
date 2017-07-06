package com.palprotech.ensyfi.bean.teacher.viewlist;

/**
 * Created by Admin on 05-07-2017.
 */

public class Students {

    public int id = -1;
    public String enrollId;
    public String admissionId;
    public String classId;
    public String studentName;
    public String classSection;

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        id = Id;
    }

    public String getEnrollId() {
        return enrollId;
    }

    public void setEnrollId(String EnrollId) {
        enrollId = EnrollId;
    }

    public String getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(String AdmissionId) {
        admissionId = AdmissionId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String ClassId) {
        classId = ClassId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String StudentName) {
        studentName = StudentName;
    }

    public String getClassSection() {
        return classSection;
    }

    public void setClassSection(String ClassSection) {
        classSection = ClassSection;
    }
}
