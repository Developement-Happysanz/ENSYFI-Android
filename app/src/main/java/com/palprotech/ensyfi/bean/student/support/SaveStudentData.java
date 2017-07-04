package com.palprotech.ensyfi.bean.student.support;

import android.content.Context;

import com.palprotech.ensyfi.bean.database.SQLiteHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Admin on 04-07-2017.
 */

public class SaveStudentData {

    private Context context;
    SQLiteHelper database;

    public SaveStudentData(Context context) {
        this.context = context;
    }

    public void saveStudentRegisteredData(JSONArray studentRegistered) {
        database = new SQLiteHelper(context);
        try {
            database.deleteLocal();

            for (int i = 0; i < studentRegistered.length(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject jsonobj = studentRegistered.getJSONObject(i);


                System.out.println("registered_id : " + i + " = " + jsonobj.getString("registered_id"));
                System.out.println("admission_id : " + i + " = " + jsonobj.getString("admission_id"));
                System.out.println("admission_no : " + i + " = " + jsonobj.getString("admission_no"));
                System.out.println("class_id : " + i + " = " + jsonobj.getString("class_id"));
                System.out.println("name : " + i + " = " + jsonobj.getString("name"));
                System.out.println("class_name : " + i + " = " + jsonobj.getString("class_name"));
                System.out.println("sec_name : " + i + " = " + jsonobj.getString("sec_name"));

                String v1 = jsonobj.getString("registered_id"),
                        v2 = jsonobj.getString("admission_id"),
                        v3 = jsonobj.getString("admission_no"),
                        v4 = jsonobj.getString("class_id"),
                        v5 = jsonobj.getString("name"),
                        v6 = jsonobj.getString("class_name"),
                        v7 = jsonobj.getString("sec_name");

                database.student_details_insert(v1, v2, v3, v4, v5, v6, v7);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
