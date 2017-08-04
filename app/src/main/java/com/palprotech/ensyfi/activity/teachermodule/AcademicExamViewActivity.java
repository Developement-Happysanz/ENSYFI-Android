package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.AcademicExamsListBaseAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.viewlist.AcademicExams;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Created by Admin on 14-07-2017.
 */

public class AcademicExamViewActivity extends AppCompatActivity implements IServiceListener, AdapterView.OnItemClickListener, DialogClickListener, View.OnClickListener {

    private Spinner spnClassList, spnSubjectList;
    private static final String TAG = "AcademicExamView";
    protected ProgressDialogHelper progressDialogHelper;
    List<String> lsClassList = new ArrayList<String>();
    ServiceHelper serviceHelper;
    SQLiteHelper db;
    Vector<String> vecClassList;
    private String classSection, getClassSectionId;
    ArrayList<AcademicExams> myList = new ArrayList<AcademicExams>();
    AcademicExamsListBaseAdapter cadapter;
    ListView loadMoreListView;
    String subjectName = "", getClassSubjectId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_exam_view_activity);

        db = new SQLiteHelper(getApplicationContext());
        vecClassList = new Vector<String>();


        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);

        progressDialogHelper = new ProgressDialogHelper(this);

        spnClassList = (Spinner) findViewById(R.id.class_list_spinner);
        spnSubjectList = (Spinner) findViewById(R.id.subject_list_spinner);

        loadMoreListView = (ListView) findViewById(R.id.listView_events);

        loadMoreListView.setOnItemClickListener(this);

        getClassList();

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spnClassList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classSection = parent.getItemAtPosition(position).toString();
                getClassId(classSection);
                loadAcademicExams(getClassSectionId);
                getSubjectList(getClassSectionId);
                cadapter = new AcademicExamsListBaseAdapter(AcademicExamViewActivity.this, myList);
                loadMoreListView.setAdapter(cadapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnSubjectList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subjectName = parent.getItemAtPosition(position).toString();
//                txthwctsubject.setText(subjectName);
                getSubjectId(subjectName, getClassSectionId);
                PreferenceStorage.saveTeacherSubject(getApplicationContext(), getClassSubjectId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getSubjectId(String subjectName, String classId) {

        try {
            Cursor c = db.getSubjectId(subjectName, classId);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        getClassSubjectId = c.getString(6);
                    } while (c.moveToNext());
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void getSubjectList(String classId) {
        Vector<String> vecSubjectList;
        vecSubjectList = new Vector<String>();
        List<String> lsSubjectList = new ArrayList<String>();
        try {
            Cursor c = db.getHandlingSubjectList(classId);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        vecSubjectList.add(c.getString(5));
                    } while (c.moveToNext());
                }
            }
            for (int i = 0; i < vecSubjectList.size(); i++) {
                lsSubjectList.add(vecSubjectList.get(i));
            }
            HashSet hs = new HashSet();
            TreeSet ts = new TreeSet(hs);
            ts.addAll(lsSubjectList);
            lsSubjectList.clear();
            lsSubjectList.addAll(ts);
            db.close();
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item_ns, lsSubjectList);

            spnSubjectList.setAdapter(dataAdapter3);
            spnSubjectList.setWillNotDraw(false);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error getting class list lookup", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void loadAcademicExams(String classSectionId) {
        myList.clear();
        try {
            Cursor c = db.getAcademicExamList(classSectionId);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        AcademicExams lde = new AcademicExams();
                        lde.setId(Integer.parseInt(c.getString(0)));
                        lde.setExamName(c.getString(2));
                        lde.setFromDate(c.getString(6));
                        lde.setToDate(c.getString(7));

                        // Add this object into the ArrayList myList
                        myList.add(lde);

                    } while (c.moveToNext());
                }
            } else {
                Toast.makeText(getApplicationContext(), "No records", Toast.LENGTH_LONG).show();
            }

            db.close();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void getClassId(String classSectionName) {

        try {
            Cursor c = db.getClassId(classSectionName);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        getClassSectionId = c.getString(0);
                    } while (c.moveToNext());
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
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
            lsClassList.addAll(ts);
            db.close();
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item_ns, lsClassList);

            spnClassList.setAdapter(dataAdapter3);
            spnClassList.setWillNotDraw(false);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error getting class list lookup", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void viewAcademicExamsDetailPage(long id) {
        Intent intent = new Intent(this, AcademicExamDetailPage.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
