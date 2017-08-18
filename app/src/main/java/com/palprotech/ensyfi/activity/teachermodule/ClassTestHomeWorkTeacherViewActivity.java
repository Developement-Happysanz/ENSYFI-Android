package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.ClassTestHomeWorkListBaseAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTestHomeWork;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Created by Admin on 13-07-2017.
 */

public class ClassTestHomeWorkTeacherViewActivity extends AppCompatActivity implements DialogClickListener {

    private Spinner spnClassList;
    ListView loadMoreListView;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;
    List<String> lsClassList = new ArrayList<String>();
    SQLiteHelper db;
    Vector<String> vecClassList;
    String getClassSectionId;
    String classSection;
    private RadioGroup radioClassTestHomeWork;
    ArrayList<ClassTestHomeWork> myList = new ArrayList<ClassTestHomeWork>();
    ClassTestHomeWorkListBaseAdapter cadapter;
    ImageView createClassTest;
    String ClassTestOrHomeWork = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_test_homework_teacher_view);

        db = new SQLiteHelper(getApplicationContext());
        vecClassList = new Vector<String>();

        progressDialogHelper = new ProgressDialogHelper(this);

        loadMoreListView = (ListView) findViewById(R.id.listView_events);

        spnClassList = (Spinner) findViewById(R.id.class_list_spinner);

        radioClassTestHomeWork = (RadioGroup) findViewById(R.id.radioClassTestHomeWorkView);

        createClassTest = (ImageView) findViewById(R.id.createClassTest);

        getClassList();

        spnClassList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classSection = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        callSpinner();

        radioClassTestHomeWork.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radioClassTest:
                        ClassTestOrHomeWork = "HT";
                        getClassId(classSection);
                        GetClassTestList(getClassSectionId, ClassTestOrHomeWork);
                        cadapter = new ClassTestHomeWorkListBaseAdapter(ClassTestHomeWorkTeacherViewActivity.this, myList);
                        loadMoreListView.setAdapter(cadapter);

                        break;

                    case R.id.radioHomeWork:
                        ClassTestOrHomeWork = "HW";
                        getClassId(classSection);
                        GetClassTestList(getClassSectionId, ClassTestOrHomeWork);
                        cadapter = new ClassTestHomeWorkListBaseAdapter(ClassTestHomeWorkTeacherViewActivity.this, myList);
                        loadMoreListView.setAdapter(cadapter);

                        break;
                }
            }
        });

        createClassTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent navigationIntent = new Intent(getApplicationContext(), ClassTestHomeWorkAddActivity.class);
                navigationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(navigationIntent);
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

    private void callSpinner() {
        ClassTestOrHomeWork = "HT";
        String getClassId = spnClassList.getSelectedItem().toString();
        getClassId(getClassId);
        GetClassTestList(getClassSectionId, ClassTestOrHomeWork);
        cadapter = new ClassTestHomeWorkListBaseAdapter(ClassTestHomeWorkTeacherViewActivity.this, myList);
        loadMoreListView.setAdapter(cadapter);
    }

    private void GetClassTestList(String classSectionId, String ClassTestOrHomeWork) {

        myList.clear();
        try {
            Cursor c = db.getClassTestHomeWork(classSectionId, ClassTestOrHomeWork);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        ClassTestHomeWork lde = new ClassTestHomeWork();
                        lde.setId(Integer.parseInt(c.getString(0)));
                        lde.setTitle(c.getString(1));
                        lde.setSubjectName(c.getString(2));
                        lde.setHomeWorkType(c.getString(3));
                        lde.setTestDate(c.getString(4));
                        lde.setDueDate(c.getString(5));

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

    public void viewClassTestHomeWorkDetailPage(long id) {
        Intent intent = new Intent(this, ClassTestHomeWorkDetailPageActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 0);
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

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}
