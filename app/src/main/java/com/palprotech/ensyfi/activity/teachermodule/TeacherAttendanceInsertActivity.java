package com.palprotech.ensyfi.activity.teachermodule;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Created by Admin on 05-07-2017.
 */

public class TeacherAttendanceInsertActivity extends AppCompatActivity implements DialogClickListener {

    private Spinner spnClassList;
    SQLiteHelper db;
    Vector<String> vecClassList, vecClassSectionList;
    List<String> lsClassList = new ArrayList<String>();
    ArrayAdapter<String> adptClassList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance_insert);
        db = new SQLiteHelper(getApplicationContext());
        vecClassList = new Vector<String>();
        vecClassSectionList = new Vector<String>();
        spnClassList = (Spinner) findViewById(R.id.class_list_spinner);
        getClassList();

        spnClassList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String className = parent.getItemAtPosition(position).toString();

                GetStudentsList(className);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void GetStudentsList(String className) {

    }

    private void getClassList() {

        try {
            Cursor c = db.getTeachersClass();
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        vecClassList.add(c.getString(1));
//                        vecClassSectionList.add(c.getString(1));

                    } while (c.moveToNext());
                }
            }
            for (int i = 0; i < vecClassList.size(); i++) {
                lsClassList.add(vecClassList.get(i));
            }
            HashSet hs = new HashSet();
            TreeSet ts = new TreeSet(hs);
            ts.addAll(lsClassList);
            lsClassList.clear();
//            lsStudent.add("Select");
            lsClassList.addAll(ts);
            db.close();
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item_ns, lsClassList);
//            adptStudent = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, lsStudent);
//            adptStudent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnClassList.setAdapter(dataAdapter3);
            spnClassList.setWillNotDraw(false);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error company lookup", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}
