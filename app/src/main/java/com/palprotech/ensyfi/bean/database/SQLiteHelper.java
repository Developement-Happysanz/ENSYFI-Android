package com.palprotech.ensyfi.bean.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Admin on 27-05-2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public static final String TAG = "SQLiteHelper.java";

    private static final String DATABASE_NAME = "ENSYFI.db";
    private static final int DATABASE_VERSION = 11;

    private String table_create_student = "Create table studentInfo(_id integer primary key autoincrement,"
            + "registered_id text,"
            + "admission_id text,"
            + "admission_no text,"
            + "class_id text,"
            + "name text,"
            + "class_name text,"
            + "sec_name text);";

    private String table_create_teacher_timetable = "Create table teacherTimeTable(_id integer primary key autoincrement,"
            + "table_id text,"
            + "class_id text,"
            + "subject_id text,"
            + "subject_name text,"
            + "teacher_id text,"
            + "name text,"
            + "day text,"
            + "period text,"
            + "sec_name text,"
            + "class_name text);";

    private String table_create_teacher_student_details = "Create table teachersStudentDetails(_id integer primary key autoincrement,"
            + "enroll_id text,"
            + "admission_id text,"
            + "class_id text,"
            + "name text,"
            + "class_section text);";

    private String table_create_attendance = "Create table attendance(_id integer primary key autoincrement,"
            + "server_at_id text,"
            + "ac_year text,"
            + "class_id text,"
            + "class_total text,"
            + "no_of_present text,"
            + "no_of_absent text,"
            + "attendance_period text,"
            + "created_by text,"
            + "created_at text,"
            + "updated_by text,"
            + "updated_at text,"
            + "status text,"
            + "sync_status text);";

    private String table_create_attendance_history = "Create table attendanceHistory(_id integer primary key autoincrement,"
            + "attend_id text,"
            + "server_attend_id text,"
            + "class_id text,"
            + "student_id text,"
            + "abs_date text,"
            + "a_status text,"
            + "attend_period text,"
            + "a_val text,"
            + "a_taken_by text,"
            + "created_at text,"
            + "updated_by text,"
            + "updated_at text,"
            + "status text,"
            + "sync_status text);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Student Details
        db.execSQL(table_create_student);
        //Teacher Time Table
        db.execSQL(table_create_teacher_timetable);
        //Teacher's Class Student
        db.execSQL(table_create_teacher_student_details);
        //Attendance
        db.execSQL(table_create_attendance);
        db.execSQL(table_create_attendance_history);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        //Student Details
        db.execSQL("DROP TABLE IF EXISTS studentInfo");
        //Teacher's Time Table
        db.execSQL("DROP TABLE IF EXISTS teacherTimeTable");
        //Teacher's Class Student
        db.execSQL("DROP TABLE IF EXISTS teachersStudentDetails");
        //Attendance
        db.execSQL("DROP TABLE IF EXISTS attendance");
        db.execSQL("DROP TABLE IF EXISTS attendanceHistory");
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    /*
    *   Student Info Data Store and Retrieve Functionality
    */
    public long student_details_insert(String val1, String val2, String val3, String val4, String val5, String val6, String val7) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("registered_id", val1);
        initialValues.put("admission_id", val2);
        initialValues.put("admission_no", val3);
        initialValues.put("class_id", val4);
        initialValues.put("name", val5);
        initialValues.put("class_name", val6);
        initialValues.put("sec_name", val7);
        long l = db.insert("studentInfo", null, initialValues);
        db.close();
        return l;
    }

    public Cursor selectStudent() throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select registered_id,admission_id,admission_no,class_id,name,class_name,sec_name from studentInfo order by name;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor selectStudentDtls(String studentname) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select registered_id,admission_id,admission_no,class_id,name,class_name,sec_name from studentInfo where name ='"
                + studentname + "';";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public void deleteStudentInfo() {
        String ok;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("studentInfo", null, null);
    }
    /*
    *   End
    */

    /*
    *   Teacher's TimeTable Store & Retrieve Functionality
    */
    public long teacher_timetable_insert(String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8, String val9, String val10) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("table_id", val1);
        initialValues.put("class_id", val2);
        initialValues.put("subject_id", val3);
        initialValues.put("subject_name", val4);
        initialValues.put("teacher_id", val5);
        initialValues.put("name", val6);
        initialValues.put("day", val7);
        initialValues.put("period", val8);
        initialValues.put("sec_name", val9);
        initialValues.put("class_name", val10);
        long l = db.insert("teacherTimeTable", null, initialValues);
        db.close();
        return l;
    }

    public Cursor getTeacherTimeTableValue(String day, String period) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select class_name,sec_name,subject_name from teacherTimeTable where day = '" + day + "' and period = '" + period + "';";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public void deleteTeacherTimeTable() {
        String ok;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("teacherTimeTable", null, null);
    }
    /*
    *   End
    */

    /*
    *   Teacher's Class Students Details Store & Retrieve Functionality
    */
    public long teachers_class_students_details_insert(String val1, String val2, String val3, String val4, String val5) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("enroll_id", val1);
        initialValues.put("admission_id", val2);
        initialValues.put("class_id", val3);
        initialValues.put("name", val4);
        initialValues.put("class_section", val5);
        long l = db.insert("teachersStudentDetails", null, initialValues);
        db.close();
        return l;
    }

    public Cursor getTeachersClass() throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select distinct class_id,class_section from teachersStudentDetails;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getStudentsOfClass(String classSection) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select * from teachersStudentDetails where class_section = '" + classSection + "' order by enroll_id;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public void deleteTeachersClassStudentDetails() {
        String ok;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("teachersStudentDetails", null, null);

    }
    /*
    *   End
    */

    /*
    *   Attendance Store & Retrieve Functionality
    */
    public long student_attendance_insert(String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8, String val9, String val10, String val11, String val12) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("ac_year", val1);
        initialValues.put("class_id", val2);
        initialValues.put("class_total", val3);
        initialValues.put("no_of_present", val4);
        initialValues.put("no_of_absent", val5);
        initialValues.put("attendance_period", val6);
        initialValues.put("created_by", val7);
        initialValues.put("created_at", val8);
        initialValues.put("updated_by", val9);
        initialValues.put("updated_at", val10);
        initialValues.put("status", val11);
        initialValues.put("sync_status", val12);
        long l = db.insert("attendance", "_id", initialValues);
        db.close();
        return l;
    }

    public void updateAttendance(String val1, String val2, String val3) {
        SQLiteDatabase sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("no_of_present", val1);
        values.put("no_of_absent", val2);
        System.out.print(val1 + "--" + val2 + "--" + val3);
        sqdb.update("attendance", values, "_id=" + val3, null);
    }

    public Cursor getAttendanceList() throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select _id,ac_year, class_id, class_total, no_of_present, no_of_absent, attendance_period, created_by, created_at, status from attendance where sync_status = 'NS' order by _id;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public void updateAttendanceId(String val1, String val2) {
        SQLiteDatabase sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("server_at_id", val1);
        System.out.print(val1 + "--" + val2);
        sqdb.update("attendance", values, "_id=" + val2, null);
    }

    public void updateAttendanceSyncStatus(String val1) {
        SQLiteDatabase sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sync_status", "S");
        System.out.print(val1);
        sqdb.update("attendance", values, "_id=" + val1, null);
    }

    public void deleteStudentAttendance() {
        String ok;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("attendance", null, null);

    }
    /*
    *   End
    */

    /*
    *   Attendance History Store & Retrieve Functionality
    */
    public long student_attendance_history_insert(String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8, String val9, String val10, String val11, String val12) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("attend_id", val1);
        initialValues.put("class_id", val2);
        initialValues.put("student_id", val3);
        initialValues.put("abs_date", val4);
        initialValues.put("a_status", val5);
        initialValues.put("attend_period", val6);
        initialValues.put("a_val", val7);
        initialValues.put("a_taken_by", val8);
        initialValues.put("created_at", val9);
        initialValues.put("updated_by", val10);
        initialValues.put("updated_at", val10);
        initialValues.put("status", val11);
        initialValues.put("sync_status", val12);
        long l = db.insert("attendanceHistory", "_id", initialValues);
        db.close();
        return l;
    }

    public void updateAttendanceHistoryServerId(String val1, String val2) {
        SQLiteDatabase sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("server_attend_id", val1);
        System.out.print(val1 + "--" + val2);
        sqdb.update("attendanceHistory", values, "attend_id=" + val2, null);
    }

    public Cursor getAttendanceHistoryList(String val1) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select _id, attend_id,server_attend_id, class_id, student_id, abs_date, a_status, attend_period, a_val, a_taken_by, created_at, status from attendanceHistory where sync_status = 'NS' and server_attend_id = " + val1 + " order by _id;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public void updateAttendanceHistorySyncStatus(String val1) {
        SQLiteDatabase sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sync_status", "S");
        System.out.print(val1);
        sqdb.update("attendanceHistory", values, "_id=" + val1, null);
    }

    public void deleteStudentAttendanceHistory() {
        String ok;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("attendanceHistory", null, null);

    }
    /*
    *   End
    */

}
