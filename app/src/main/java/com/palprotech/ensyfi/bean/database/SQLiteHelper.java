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
    private static final int DATABASE_VERSION = 5;

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

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table_create_student);
        db.execSQL(table_create_teacher_timetable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS studentInfo");
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    /*
    // Student Info Data Store and Retrieve Functionality
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
    // End
    */

    /*
    Teacher's TimeTable Store & Retrieve Functionality
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

    public Cursor getTeacherTimeTable() throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        String fetch = "Select table_id,class_id,subject_id,subject_name,teacher_id,name,day,period,sec_name,class_name from teacherTimeTable order by table_id;";
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
}
