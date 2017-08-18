package com.palprotech.ensyfi.activity.studentmodule;

import android.content.Intent;
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
import com.palprotech.ensyfi.activity.parentsmodule.ParentDashBoardActivity;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Created by Admin on 27-05-2017.
 */

public class StudentInfoActivity extends AppCompatActivity {

    private Spinner spnStudentInfo;
    SQLiteHelper db;
    Vector<String> vecStudent;
    List<String> lsStudent = new ArrayList<String>();
    String StudentName, StudentId, AdmissionId, Class, Section, AdmissionNo, ClassId;
    TextView txtstudname, txtstudid, txtadmissid, txtclass, txtsec;
    Button btnContinue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_info_activity);
        db = new SQLiteHelper(getApplicationContext());
        vecStudent = new Vector<String>();
        spnStudentInfo = (Spinner) findViewById(R.id.eventtypespinner);
        txtstudname = (TextView) findViewById(R.id.txtstudname);
        txtstudid = (TextView) findViewById(R.id.txtstudid);
        txtadmissid = (TextView) findViewById(R.id.txtadmissid);
        txtclass = (TextView) findViewById(R.id.txtclass);
        txtsec = (TextView) findViewById(R.id.txtsec);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        getStudentList();

        spnStudentInfo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String studentName = parent.getItemAtPosition(position).toString();
                StudentName = studentName;
                GetStudentDetails(studentName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // Student Preference - EnrollId
                if ((StudentId != null) && !(StudentId.isEmpty()) && !StudentId.equalsIgnoreCase("null")) {
                    PreferenceStorage.saveStudentRegisteredIdPreference(getApplicationContext(), StudentId);
                }
                // Student Preference - AdmissionId
                if ((AdmissionId != null) && !(AdmissionId.isEmpty()) && !AdmissionId.equalsIgnoreCase("null")) {
                    PreferenceStorage.saveStudentAdmissionIdPreference(getApplicationContext(), AdmissionId);
                }

                // Student Preference - AdmissionNo
                if ((AdmissionNo != null) && !(AdmissionNo.isEmpty()) && !AdmissionNo.equalsIgnoreCase("null")) {
                    PreferenceStorage.saveStudentAdmissionNoPreference(getApplicationContext(), AdmissionNo);
                }

                // Student Preference - ClassId
                if ((ClassId != null) && !(ClassId.isEmpty()) && !ClassId.equalsIgnoreCase("null")) {
                    PreferenceStorage.saveStudentClassIdPreference(getApplicationContext(), ClassId);
                }

                // Student Preference - Name
                if ((StudentName != null) && !(StudentName.isEmpty()) && !StudentName.equalsIgnoreCase("null")) {
                    PreferenceStorage.saveStudentNamePreference(getApplicationContext(), StudentName);
                }

                // Student Preference - ClassName
                if ((Class != null) && !(Class.isEmpty()) && !Class.equalsIgnoreCase("null")) {
                    PreferenceStorage.saveStudentClassNamePreference(getApplicationContext(), Class);
                }

                // Student Preference - SectionName
                if ((Section != null) && !(Section.isEmpty()) && !Section.equalsIgnoreCase("null")) {
                    PreferenceStorage.saveStudentSectionNamePreference(getApplicationContext(), Section);
                }

                Intent intent = new Intent(getApplicationContext(), ParentDashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void GetStudentDetails(String studentname) {
        try {
            Cursor c = db.selectStudentDtls(studentname);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        StudentId = c.getString(0);
                        AdmissionId = c.getString(1);
                        AdmissionNo = c.getString(2);
                        ClassId = c.getString(3);
                        StudentName = c.getString(4);
                        Class = c.getString(5);
                        Section = c.getString(6);
                    } while (c.moveToNext());
                }
            }
            db.close();
            txtstudname.setText(StudentName);
            txtstudid.setText(StudentId);
            txtadmissid.setText(AdmissionNo);
            txtclass.setText(Class);
            txtsec.setText("-" + Section);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Get Student Details", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void getStudentList() {
        try {
            Cursor c = db.selectStudent();
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        vecStudent.add(c.getString(4));
                    } while (c.moveToNext());
                }
            }
            for (int i = 0; i < vecStudent.size(); i++) {
                lsStudent.add(vecStudent.get(i));
            }
            HashSet hs = new HashSet();
            TreeSet ts = new TreeSet(hs);
            ts.addAll(lsStudent);
            lsStudent.clear();
//            lsStudent.add("Select");
            lsStudent.addAll(ts);
            db.close();
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item_ns, lsStudent);
//            adptStudent = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, lsStudent);
//            adptStudent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnStudentInfo.setAdapter(dataAdapter3);
            spnStudentInfo.setWillNotDraw(false);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
