package com.palprotech.ensyfi.activity.teachermodule;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.StudentListBaseAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.viewlist.Students;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Created by Admin on 05-07-2017.
 */

public class TeacherAttendanceInsertActivity extends AppCompatActivity implements DialogClickListener {

    private static final String TAG = TeacherAttendanceInsertActivity.class.getName();
    private Spinner spnClassList;
    SQLiteHelper db;
    Vector<String> vecClassList, vecClassSectionList;
    List<String> lsClassList = new ArrayList<String>();
    ArrayList<Students> myList = new ArrayList<Students>();
    ArrayAdapter<String> adptClassList;
    String set1, set2, set3, AM_PM;
    ListView lvStudent;
    Button btnSave;
    TextView txtDateTime;
    private String storeClassId;
    String formattedServerDate;
    int valPresent = 0, valAbsent = 0, valLeave = 0, valOD = 0, setAM_PM;
    String lastInsertedId;
    Calendar c = Calendar.getInstance();

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
        txtDateTime = (TextView) findViewById(R.id.txtDateTime);
        btnSave.setVisibility(View.VISIBLE);
        getClassList();

        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat serverDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedServerDate = serverDF.format(c.getTime());
        SimpleDateFormat slocalDF = new SimpleDateFormat("dd-MM-yyyy");
        String formattedLocalDate = slocalDF.format(c.getTime());
        txtDateTime.setText(formattedLocalDate);

        Calendar now = Calendar.getInstance();
        int a = now.get(Calendar.AM_PM);
        if (a == Calendar.AM) {
            setAM_PM = a;
            AM_PM = String.valueOf(setAM_PM);
        } else {
            setAM_PM = a;
            AM_PM = String.valueOf(setAM_PM);
        }

        spnClassList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String className = parent.getItemAtPosition(position).toString();

                GetStudentsList(className);
                btnSave.setVisibility(View.VISIBLE);
//                lvStudent.setAdapter(new StudentListClassTestMarkBaseAdapter(TeacherAttendanceInsertActivity.this, myList));
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
                Spinner spinner;
                StoreStudentAttendance();
                for (int i = 0; i < lvStudent.getCount(); i++) {
                    et = (TextView) lvStudent.getChildAt(i).findViewById(R.id.txt_studentId);
                    et1 = (TextView) lvStudent.getChildAt(i).findViewById(R.id.txt_studentName);
                    spinner = (Spinner) lvStudent.getChildAt(i).findViewById(R.id.class_attendance_spinner);
                    if (et != null) {
                        mannschaftsnamen.add(String.valueOf(et.getText()));
                        String enrollId = String.valueOf(et.getText());
                        String studentName = String.valueOf(et1.getText());
                        String attendanceStatus = String.valueOf(spinner.getSelectedItem());
                        if (attendanceStatus.equalsIgnoreCase("Leave")) {
                            valLeave = valLeave + 1;
                            attendanceStatus = "L";
                        } else if (attendanceStatus.equalsIgnoreCase("Absent")) {
                            valAbsent = valAbsent + 1;
                            attendanceStatus = "A";
                        } else if (attendanceStatus.equalsIgnoreCase("OD")) {
                            valOD = valOD + 1;
                            attendanceStatus = "OD";
                        } else {
                            valPresent = valPresent + 1;
                            attendanceStatus = "P";
                        }
                        SimpleDateFormat slocalDF = new SimpleDateFormat("yyyy-MM-dd");
                        String formattedLocalInsertDate = slocalDF.format(c.getTime());

                        long c = db.student_attendance_history_insert(lastInsertedId, storeClassId, enrollId, formattedLocalInsertDate, attendanceStatus, AM_PM, "", PreferenceStorage.getTeacherId(getApplicationContext()), formattedServerDate, PreferenceStorage.getUserId(getApplicationContext()), formattedServerDate, "A", "NS");
                        if (c == -1) {
                            Toast.makeText(getApplicationContext(), "Error while attendance insert...",
                                    Toast.LENGTH_LONG).show();
                        }
                        /** you can try to log your values EditText */
                        Log.v("ypgs", String.valueOf(et.getText()));
                    }
                }
                UpdateLastInsertedStudentAttendance(valLeave, valAbsent, valOD, valPresent, lastInsertedId);
                btnSave.setVisibility(View.GONE);
            }
        });

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void UpdateLastInsertedStudentAttendance(int valLeave, int valAbsent, int valOD, int valPresent, String totalNoOfStudents) {
        try {
            int combinePOD = valOD + valPresent;
            int combineAL = valLeave + valAbsent;

            String noOfPresentOD = String.valueOf(combinePOD);
            String noOfLeaveAbsent = String.valueOf(combineAL);

            db.updateAttendance(noOfPresentOD, noOfLeaveAbsent, totalNoOfStudents);

        } catch (Exception ex) {
        }
    }

    private void StoreStudentAttendance() {
        try {
            int totalNoOfStudents = lvStudent.getCount();
            String convertTotalNoOfStudents = String.valueOf(totalNoOfStudents);
            String convertAM_PM = String.valueOf(setAM_PM);

            long c = db.student_attendance_insert(PreferenceStorage.getAcademicYearId(this), storeClassId, convertTotalNoOfStudents, "", "", convertAM_PM, PreferenceStorage.getUserId(this), formattedServerDate, PreferenceStorage.getUserId(this), formattedServerDate, "A", "NS");
            if (c == -1) {
                Toast.makeText(this, "Error while attendance insert...",
                        Toast.LENGTH_LONG).show();
            } else {
                lastInsertedId = String.valueOf(c);
            }
        } catch (Exception ex) {
            Log.println(Log.VERBOSE, TAG, ex.toString());
        }
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
                        storeClassId = c.getString(3);
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
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
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

