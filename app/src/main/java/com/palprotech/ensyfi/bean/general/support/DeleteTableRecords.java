package com.palprotech.ensyfi.bean.general.support;

import android.content.Context;

import com.palprotech.ensyfi.bean.database.SQLiteHelper;

import org.json.JSONArray;

/**
 * Created by Admin on 18-07-2017.
 */

public class DeleteTableRecords {

    private Context context;
    SQLiteHelper database;

    public DeleteTableRecords(Context context) {
        this.context = context;
    }

    public void deleteAllRecords() {
        database = new SQLiteHelper(context);
        try {
            database.deleteTeacherHandlingSubjects();
            database.deleteLeaveTypes();
            database.deleteAcademicExamMarks();
            database.deleteExamDetails();
            database.deleteExamOfClasses();
            database.deleteClassTestMark();
            database.deleteHomeWorkClassTest();
            database.deleteAcademicMonths();
            database.deleteStudentAttendanceHistory();
            database.deleteStudentAttendance();
            database.deleteTeachersClassStudentDetails();
            database.deleteTeacherTimeTable();
            database.deleteStudentInfo();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
