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
    private static final int DATABASE_VERSION = 30;

    private static final String table_create_student = "Create table IF NOT EXISTS studentInfo(_id integer primary key autoincrement,"
            + "registered_id text,"
            + "admission_id text,"
            + "admission_no text,"
            + "class_id text,"
            + "name text,"
            + "class_name text,"
            + "sec_name text);";

    private static final String table_create_teacher_timetable = "Create table IF NOT EXISTS teacherTimeTable(_id integer primary key autoincrement,"
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

    private static final String table_create_teacher_student_details = "Create table IF NOT EXISTS teachersStudentDetails(_id integer primary key autoincrement,"
            + "enroll_id text,"
            + "admission_id text,"
            + "class_id text,"
            + "name text,"
            + "class_section text);";

    private static final String table_create_attendance = "Create table IF NOT EXISTS attendance(_id integer primary key autoincrement,"
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

    private static final String table_create_attendance_history = "Create table IF NOT EXISTS attendanceHistory(_id integer primary key autoincrement,"
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

    private static final String table_create_attendance_flag = "Create table IF NOT EXISTS attendanceFlag(_id integer primary key autoincrement,"
            + "class_id text,"
            + "attendance_date text,"
            + "attend_period text,"
            + "status text);";

    private static final String table_create_academic_months = "Create table IF NOT EXISTS academicMonths(_id integer primary key autoincrement,"
            + "academic_months text);";

    private static final String table_create_homework_class_test = "Create table IF NOT EXISTS homeWorkClassTest(_id integer primary key autoincrement,"
            + "server_hw_id text," //1
            + "year_id text," //2
            + "class_id text," //3
            + "teacher_id text," //4
            + "hw_type text,"//5
            + "subject_id text,"//6
            + "subject_name text,"//7
            + "title text,"//8
            + "test_date text,"//9
            + "due_date text,"//10
            + "hw_details text,"//11
            + "status text,"//12
            + "mark_status text,"//13
            + "created_by text,"//14
            + "created_at text,"//15
            + "updated_by text,"//16
            + "updated_at text,"//17
            + "sync_status text);";//18

    private static final String table_create_class_test_mark = "Create table IF NOT EXISTS classTestMark(_id integer primary key autoincrement,"
            + "student_id text," //1
            + "local_hw_id text," //2
            + "server_hw_id text," //3
            + "marks text," //4
            + "remarks text,"//5
            + "status text,"//6
            + "created_by text,"//7
            + "created_at text,"//8
            + "updated_by text,"//9
            + "updated_at text,"//10
            + "sync_status text);";//11

    private static final String table_create_exams_of_the_class = "Create table IF NOT EXISTS academicExams(_id integer primary key autoincrement,"
            + "exam_id text," //1
            + "exam_name text," //2
            + "is_internal_external text," //3
            + "classmaster_id text," //4
            + "sec_name text," //5
            + "class_name text,"//6
            + "Fromdate text,"//7
            + "Todate text,"//8
            + "MarkStatus text);";//9

    private static final String table_create_exams_details = "Create table IF NOT EXISTS academicExamsDetails(_id integer primary key autoincrement,"
            + "exam_id text," //1
            + "exam_name text," //2
            + "subject_name text," //3
            + "exam_date text," //4
            + "times text,"//5
            + "classmaster_id text,"//6
            + "class_name text,"//7
            + "sec_name text);";//8

    private static final String table_create_academic_exam_marks = "Create table IF NOT EXISTS academicExamMarks(_id integer primary key autoincrement,"
            + "exam_id text," //1
            + "teacher_id text," //2
            + "subject_id text," //3
            + "stu_id text," //4
            + "classmaster_id text,"//5
            + "internal_mark text,"//6
            + "internal_grade text,"//7
            + "external_mark text,"//8
            + "external_grade text,"//9
            + "total_marks text,"//10
            + "total_grade text,"//11
            + "created_by text,"//12
            + "created_at text,"//13
            + "updated_by text,"//14
            + "updated_at text,"//15
            + "sync_status text);";//16

    private static final String table_create_leave_type = "Create table IF NOT EXISTS leaveType(_id integer primary key autoincrement,"
            + "id text," //1
            + "leave_title text," //2
            + "leave_type text);";//3

    private static final String table_create_teacher_handling_subjects = "Create table IF NOT EXISTS teacherHandlingSubject(_id integer primary key autoincrement,"
            + "class_master_id text," //1
            + "teacher_id text," //2
            + "class_name text," //3
            + "sec_name text," //4
            + "subject_name text," //5
            + "subject_id text);";//6

    private static final String table_create_academic_exam_subject_marks_status = "Create table IF NOT EXISTS academicExamSubjectMarksStatus(_id integer primary key autoincrement,"
            + "exam_id text," //1
            + "teacher_id text," //2
            + "subject_id text);";//3


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

        db.execSQL(table_create_attendance_flag);

        db.execSQL(table_create_academic_months);

        db.execSQL(table_create_homework_class_test);

        db.execSQL(table_create_class_test_mark);

        db.execSQL(table_create_exams_of_the_class);

        db.execSQL(table_create_exams_details);

        db.execSQL(table_create_academic_exam_marks);

        db.execSQL(table_create_leave_type);

        db.execSQL(table_create_teacher_handling_subjects);

        db.execSQL(table_create_academic_exam_subject_marks_status);
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

        db.execSQL("DROP TABLE IF EXISTS attendanceFlag");

        db.execSQL("DROP TABLE IF EXISTS academicMonths");

        db.execSQL("DROP TABLE IF EXISTS homeWorkClassTest");

        db.execSQL("DROP TABLE IF EXISTS classTestMark");

        db.execSQL("DROP TABLE IF EXISTS academicExams");

        db.execSQL("DROP TABLE IF EXISTS academicExamsDetails");

        db.execSQL("DROP TABLE IF EXISTS academicExamMarks");

        db.execSQL("DROP TABLE IF EXISTS leaveType");

        db.execSQL("DROP TABLE IF EXISTS teacherHandlingSubject");

        db.execSQL("DROP TABLE IF EXISTS academicExamSubjectMarksStatus");

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
        String fetch = "Select class_name,sec_name,subject_name,class_id,subject_id,period,subject_name from teacherTimeTable where day = '" + day + "' and period = '" + period + "';";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public void deleteTeacherTimeTable() {
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

    public Cursor getClassId(String classSection) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select distinct class_id from teachersStudentDetails where class_section='" + classSection + "';";
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

    public Cursor getStudentsOfClassBasedOnClassId(String classSectionId) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select * from teachersStudentDetails where class_id = '" + classSectionId + "' order by enroll_id;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public void deleteTeachersClassStudentDetails() {
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
        String fetch = "Select _id,ac_year,class_id,class_total,no_of_present,no_of_absent,attendance_period,created_by,created_at,status from attendance where sync_status = 'NS' order by _id;";
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

    public String isAttendanceSynced() {
        String checkFlag = "0";
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "Select count(*) from attendance where sync_status = 'NS'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                checkFlag = cursor.getString(0);
            } while (cursor.moveToNext());
        }
//        if(cursor != null)
        cursor.close();
        return checkFlag;
    }

    public void deleteStudentAttendance() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("attendance", null, null);
    }
    /*
    *   End
    */

    /*
    *   Attendance History Store & Retrieve Functionality
    */
    public long student_attendance_history_insert(String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8, String val9, String val10, String val11, String val12, String val13) {
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
        initialValues.put("updated_at", val11);
        initialValues.put("status", val12);
        initialValues.put("sync_status", val13);
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
        String fetch = "Select _id, " +
                "attend_id," +
                "server_attend_id, " +
                "class_id, " +
                "student_id, " +
                "abs_date, " +
                "a_status, " +
                "attend_period, " +
                "a_val, " +
                "a_taken_by, " +
                "created_at, " +
                "status from attendanceHistory where sync_status = 'NS' and server_attend_id = " + val1 + " order by _id;";
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
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("attendanceHistory", null, null);
    }
    /*
    *   End
    */

    /*
    *   Attendance History Store & Retrieve Functionality
    */
    public long student_attendance_flag_insert(String val1, String val2, String val3, String val4) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("class_id", val1);
        initialValues.put("attendance_date", val2);
        initialValues.put("attend_period", val3);
        initialValues.put("status", val4);
        long l = db.insert("attendanceFlag", "_id", initialValues);
        db.close();
        return l;
    }

 /*   public Cursor isAttendanceFlag(String val1, String val2, String val3) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select count(*) from attendanceFlag where class_id = '" + val1 + "' and attendance_date = '" + val2 + "' and attend_period = '" + val3 + "';";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }*/

    public String isAttendanceFlag(String val1, String val2, String val3) {
        String checkFlag = "0";
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "Select count(*) from attendanceFlag where class_id = '" + val1 + "' and attendance_date = '" + val2 + "' and attend_period = '" + val3 + "';";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                checkFlag = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return checkFlag;
    }

    public void deleteStudentAttendanceFlag() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("attendanceFlag", null, null);
    }
    /*
    *   End
    */

    /*
     *   Academic Store & Retrieve Functionality
     */
    public long academic_months_insert(String val1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("academic_months", val1);
        long l = db.insert("academicMonths", "_id", initialValues);
        db.close();
        return l;
    }

    public Cursor getAcademicMonths() throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select academic_months from academicMonths order by _id asc;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public void deleteAcademicMonths() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("academicMonths", null, null);
    }
    /*
    *   End
    */

    /*
  *   Homework Class Test Store & Retrieve Functionality
  */
    public long homework_class_test_insert(String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8, String val9, String val10, String val11, String val12, String val13, String val14, String val15, String val16, String val17, String val18) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("server_hw_id", val1);
        initialValues.put("year_id", val2);
        initialValues.put("class_id", val3);
        initialValues.put("teacher_id", val4);
        initialValues.put("hw_type", val5);
        initialValues.put("subject_id", val6);
        initialValues.put("subject_name", val7);
        initialValues.put("title", val8);
        initialValues.put("test_date", val9);
        initialValues.put("due_date", val10);
        initialValues.put("hw_details", val11);
        initialValues.put("status", val12);
        initialValues.put("mark_status", val13);
        initialValues.put("created_by", val14);
        initialValues.put("created_at", val15);
        initialValues.put("updated_by", val16);
        initialValues.put("updated_at", val17);
        initialValues.put("sync_status", val18);
        long l = db.insert("homeWorkClassTest", "_id", initialValues);
        db.close();
        return l;
    }

    public Cursor getClassTestHomeWork(String classId, String homeWorkType) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        //_id,title,subject_name,hw_type,test_date
        String fetch = "Select _id,title,subject_name,hw_type,test_date,due_date from homeWorkClassTest where class_id=" + classId + " and hw_type = '" + homeWorkType + "' order by _id asc;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public String isClassTestHomeWorkServer(String val1) {
        String classTestHomeWorkStatusFlag = "0";
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "Select count(*) from homeWorkClassTest where server_hw_id = '" + val1 + "';";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                classTestHomeWorkStatusFlag = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return classTestHomeWorkStatusFlag;
    }

    public Cursor getClassTestHomeWorkDetails(String homeworkId) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select * from homeWorkClassTest where _id=" + homeworkId + ";";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public void updateClassTestHomeWorkMarkStatus(String val1) {
        SQLiteDatabase sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mark_status", "1");
        System.out.print(val1);
        sqdb.update("homeWorkClassTest", values, "_id=" + val1, null);
    }

    public Cursor getClassTestHomeWorkList() throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select * from homeWorkClassTest where sync_status = 'NS' order by _id;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public String isClassTestHomeWorkStatusFlag() {
        String classTestHomeWorkStatusFlag = "0";
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "Select count(*) from homeWorkClassTest where sync_status = 'NS' order by _id;";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                classTestHomeWorkStatusFlag = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return classTestHomeWorkStatusFlag;
    }

    public void updateClassTestHomeWorkServerId(String val1, String val2) {
        SQLiteDatabase sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("server_hw_id", val1);
        System.out.print(val1 + "--" + val2);
        sqdb.update("homeWorkClassTest", values, "_id=" + val2, null);
    }

    public void updateClassTestHomeWorkSyncStatus(String val1) {
        SQLiteDatabase sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sync_status", "S");
        System.out.print(val1);
        sqdb.update("homeWorkClassTest", values, "_id=" + val1, null);
    }

    public void deleteHomeWorkClassTest() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("homeWorkClassTest", null, null);
    }
    /*
    *   End
    */

    /*
 *   Class Test Marks Store & Retrieve Functionality
 */
    public long class_test_mark_insert(String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8, String val9, String val10, String val11) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("student_id", val1);
        initialValues.put("local_hw_id", val2);
        initialValues.put("server_hw_id", val3);
        initialValues.put("marks", val4);
        initialValues.put("remarks", val5);
        initialValues.put("status", val6);
        initialValues.put("created_by", val7);
        initialValues.put("created_at", val8);
        initialValues.put("updated_by", val9);
        initialValues.put("updated_at", val10);
        initialValues.put("sync_status", val11);
        long l = db.insert("classTestMark", "_id", initialValues);
        db.close();
        return l;
    }

    public Cursor getClassTestMarkList(String val1) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select * from classTestMark where sync_status = 'NS' and server_hw_id = " + val1 + " order by _id;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getClassTestMarkListView() throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select * from classTestMark where sync_status = 'NS' order by _id;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public String isClassTestMarkStatusFlag() {
        String classTestMarkStatusFlag = "0";
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "Select count(*) from classTestMark where sync_status = 'NS' order by _id;";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                classTestMarkStatusFlag = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return classTestMarkStatusFlag;
    }

    public void updateClassTestMarkServerId(String val1, String val2) {
        SQLiteDatabase sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("server_hw_id", val1);
        System.out.print(val1 + "--" + val2);
        sqdb.update("classTestMark", values, "local_hw_id=" + val2, null);
    }

    public void updateClassTestSyncStatus(String val1) {
        SQLiteDatabase sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sync_status", "S");
        System.out.print(val1);
        sqdb.update("classTestMark", values, "server_hw_id=" + val1, null);
    }

    public void deleteClassTestMark() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("homeWorkClassTest", null, null);
    }
    /*
    *   End
    */

    /*
    *   Academic Exams Store & Retrieve Functionality
    */
    public long exam_of_classes_insert(String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8, String val9) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("exam_id", val1);
        initialValues.put("exam_name", val2);
        initialValues.put("is_internal_external", val3);
        initialValues.put("classmaster_id", val4);
        initialValues.put("sec_name", val5);
        initialValues.put("class_name", val6);
        initialValues.put("Fromdate", val7);
        initialValues.put("Todate", val8);
        initialValues.put("MarkStatus", val9);
        long l = db.insert("academicExams", "_id", initialValues);
        db.close();
        return l;
    }

    public Cursor getAcademicExamList(String val1) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select * from academicExams where classmaster_id = " + val1 + " order by _id;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getAcademicExamInfo(String val1) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select * from academicExams where _id = " + val1 + " order by _id;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public String isAcademicExam(String val1, String val2) {
        String classTestHomeWorkStatusFlag = "0";
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "Select count(*) from academicExams where exam_id = '" + val1 + "' and classmaster_id = '" + val2 + "';";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                classTestHomeWorkStatusFlag = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return classTestHomeWorkStatusFlag;
    }

    /*public void updateAcademicExamMarksStatus(String val1, String val2) {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Update academicExams SET MarkStatus = '1' where exam_id = '" + val1 + "' and classmaster_id = '" + val2 + "';";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
    }*/

    public void deleteExamOfClasses() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("academicExams", null, null);
    }
    /*
    *   End
    */

    /*
    *   Academic Exams Details Store & Retrieve Functionality
    */
    public long exam_details_insert(String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("exam_id", val1);
        initialValues.put("exam_name", val2);
        initialValues.put("subject_name", val3);
        initialValues.put("exam_date", val4);
        initialValues.put("times", val5);
        initialValues.put("classmaster_id", val6);
        initialValues.put("class_name", val7);
        initialValues.put("sec_name", val8);
        long l = db.insert("academicExamsDetails", "_id", initialValues);
        db.close();
        return l;
    }

    public Cursor getAcademicExamDetailsList(String val1, String val2) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select * from academicExamsDetails where classmaster_id = '" + val1 + "' and exam_id = '" + val2 + "' order by _id;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public String isAcademicExamDetails(String val1, String val2, String val3) {
        String classTestHomeWorkStatusFlag = "0";
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "Select count(*) from academicExamsDetails where exam_id = '" + val1 + "' and subject_name = '" + val2 + "' and classmaster_id = '" + val3 + "';";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                classTestHomeWorkStatusFlag = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return classTestHomeWorkStatusFlag;
    }

    public void deleteExamDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("academicExamsDetails", null, null);
    }
    /*
    *   End
    */

    /*
  *   Homework Class Test Store & Retrieve Functionality
  */
    public long academic_exam_marks_insert(String val1, String val2, String val3, String val4, String val5, String val6, String val7, String val8, String val9, String val10, String val11, String val12, String val13, String val14, String val15, String val16) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("exam_id", val1);
        initialValues.put("teacher_id", val2);
        initialValues.put("subject_id", val3);
        initialValues.put("stu_id", val4);
        initialValues.put("classmaster_id", val5);
        initialValues.put("internal_mark", val6);
        initialValues.put("internal_grade", val7);
        initialValues.put("external_mark", val8);
        initialValues.put("external_grade", val9);
        initialValues.put("total_marks", val10);
        initialValues.put("total_grade", val11);
        initialValues.put("created_by", val12);
        initialValues.put("created_at", val13);
        initialValues.put("updated_by", val14);
        initialValues.put("updated_at", val15);
        initialValues.put("sync_status", val16);
        long l = db.insert("academicExamMarks", "_id", initialValues);
        db.close();
        return l;
    }

    public Cursor getAcademicExamMarksList() throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select * from academicExamMarks where sync_status = 'NS' order by _id;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public String isInternalExternalFlag(String val1) {
        String internalExternalFlag = "0";
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "Select is_internal_external from academicExams where exam_id = '" + val1 + "';";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                internalExternalFlag = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return internalExternalFlag;
    }

    public void updateAcademicExamMarksSyncStatus(String val1) {
        SQLiteDatabase sqdb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sync_status", "S");
        System.out.print(val1);
        sqdb.update("academicExamMarks", values, "_id=" + val1, null);
    }

    public void deleteAcademicExamMarks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("academicExamMarks", null, null);
    }
    /*
    *   End
    */

    /*
  *   Homework Class Test Store & Retrieve Functionality
  */
    public long leave_type_insert(String val1, String val2, String val3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("id", val1);
        initialValues.put("leave_title", val2);
        initialValues.put("leave_type", val3);
        long l = db.insert("leaveType", "_id", initialValues);
        db.close();
        return l;
    }

    public Cursor getLeaveTypeList() throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select distinct id,leave_title,leave_type from leaveType order by _id;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getLeaveTypeId(String val1) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select distinct leave_type,id from leaveType where leave_title= '" + val1 + "' order by _id;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public void deleteLeaveTypes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("leaveType", null, null);
    }
    /*
    *   End
    */

    /*
  *   Homework Class Test Store & Retrieve Functionality
  */
    public long teacher_handling_subject_insert(String val1, String val2, String val3, String val4, String val5, String val6) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("class_master_id", val1);
        initialValues.put("teacher_id", val2);
        initialValues.put("class_name", val3);
        initialValues.put("sec_name", val4);
        initialValues.put("subject_name", val5);
        initialValues.put("subject_id", val6);
        long l = db.insert("teacherHandlingSubject", "_id", initialValues);
        db.close();
        return l;
    }

    public Cursor getHandlingSubjectList(String val1) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select * from teacherHandlingSubject where class_master_id='" + val1 + "' order by _id;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getAllHandlingSubjectList() throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select distinct * from teacherHandlingSubject order by _id;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getSubjectId(String val1, String val2) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select * from teacherHandlingSubject where subject_name='" + val1 + "' and class_master_id='" + val2 + "' order by _id;";
        Cursor c = db.rawQuery(fetch, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public void deleteTeacherHandlingSubjects() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("teacherHandlingSubject", null, null);
    }
    /*
    *   End
    */

    /*
  *   Academic Exam Subject Marks Status Functionality
  */
    public long academic_exam_subject_marks_status_insert(String val1, String val2, String val3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put("exam_id", val1);
        initialValues.put("teacher_id", val2);
        initialValues.put("subject_id", val3);
        long l = db.insert("academicExamSubjectMarksStatus", "_id", initialValues);
        db.close();
        return l;
    }

    public String isAcademicExamSubjectMarksStatusFlag(String val1, String val2, String val3) {
        String academicExamSubjectMarksStatusFlag = "0";
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "Select count(*) from academicExamSubjectMarksStatus where exam_id = '" + val1 + "' and teacher_id = '" + val2 + "' and subject_id = '" + val3 + "';";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                academicExamSubjectMarksStatusFlag = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return academicExamSubjectMarksStatusFlag;
    }


    public void deleteAcademicExamSubjectMarksStatus() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("academicExamSubjectMarksStatus", null, null);
    }
    /*
    *   End
    */

}
