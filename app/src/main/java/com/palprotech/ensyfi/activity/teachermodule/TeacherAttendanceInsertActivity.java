package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.StudentListBaseAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.viewlist.Students;
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
    ArrayList<Students> myList = new ArrayList<Students>();
    ArrayAdapter<String> adptClassList;
    String set1, set2, set3;
    ListView lvStudent;
    Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance_insert);
        db = new SQLiteHelper(getApplicationContext());
        vecClassList = new Vector<String>();
        vecClassSectionList = new Vector<String>();
        spnClassList = (Spinner) findViewById(R.id.class_list_spinner);
        lvStudent = (ListView) findViewById(R.id.listView_students);
        btnSave = (Button) findViewById(R.id.btnSave);
        getClassList();

        spnClassList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String className = parent.getItemAtPosition(position).toString();

                GetStudentsList(className);
//                lvStudent.setAdapter(new StudentListBaseAdapter(TeacherAttendanceInsertActivity.this, myList));
                StudentListBaseAdapter cadapter = new StudentListBaseAdapter(TeacherAttendanceInsertActivity.this, myList);
                lvStudent.setAdapter(cadapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                /** get all values of the EditText-Fields */
                View view;
                ArrayList<String> mannschaftsnamen = new ArrayList<String>();
                TextView et, et1;
                RadioGroup radioGroup;
                for (int i = 0; i < lvStudent.getCount(); i++) {
                    et = (TextView) lvStudent.getChildAt(i).findViewById(R.id.txt_studentId);
                    et1 = (TextView) lvStudent.getChildAt(i).findViewById(R.id.txt_studentName);
                    radioGroup = (RadioGroup) lvStudent.getChildAt(i).findViewById(R.id.group_me);
                    if (et != null) {
                        mannschaftsnamen.add(String.valueOf(et.getText()));
                        String viewPrint = String.valueOf(et.getText());
                        String viewPrint1 = String.valueOf(et1.getText());
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        // find the radiobutton by returned id
                        RadioButton radioButton = (RadioButton) findViewById(selectedId);
                        String viewPrint2 = String.valueOf(radioButton.getText());
                        /** you can try to log your values EditText */
                        Log.v("ypgs", String.valueOf(et.getText()));
                    }
                }
            }
        });
    }

    private void GetStudentsList(String className) {

        myList.clear();
        try {
            Cursor c = db.getStudentsOfClass(className);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        Students lde = new Students();
                        lde.setId(Integer.parseInt(c.getString(0)));
                        lde.setEnrollId(c.getString(1));
                        lde.setAdmissionId(c.getString(2));
                        lde.setClassId(c.getString(3));
                        lde.setStudentName(c.getString(4));
                        lde.setClassSection(c.getString(5));

                        // Add this object into the ArrayList myList
                        myList.add(lde);

                    } while (c.moveToNext());
                }
            }

            db.close();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG)
                    .show();
            e.printStackTrace();
        }
    }

    private void getClassList() {

        try {
            Cursor c = db.getTeachersClass();
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        vecClassList.add(c.getString(1));
                        set3 = c.getString(0);
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
