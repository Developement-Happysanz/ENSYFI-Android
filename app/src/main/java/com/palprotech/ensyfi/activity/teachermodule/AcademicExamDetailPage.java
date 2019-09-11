package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.AcademicExamDetailsListBaseAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.viewlist.AcademicExamDetails;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import java.util.ArrayList;

/**
 * Created by Admin on 15-07-2017.
 */

public class AcademicExamDetailPage extends AppCompatActivity implements DialogClickListener, View.OnClickListener {

    long id;
    String subjectId;
    SQLiteHelper db;
    protected ProgressDialogHelper progressDialogHelper;
    ArrayList<AcademicExamDetails> myList = new ArrayList<AcademicExamDetails>();
    AcademicExamDetailsListBaseAdapter cadapter;
    ListView loadMoreListView;
    String examId, examName, isInternalExternal, classMasterId, sectionName, className, fromDate, toDate,
            markStatus, isInternalExternalSubject;
    ImageView addExamMark, viewExamMark;
    int isInternalExternalForTheSubject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_exam_details);
        id = getIntent().getExtras().getLong("id");
        subjectId = getIntent().getExtras().getString("subject_id");
        db = new SQLiteHelper(getApplicationContext());
        String localExamId = String.valueOf(id);
        loadMoreListView = (ListView) findViewById(R.id.listView_events);
        addExamMark = (ImageView) findViewById(R.id.addExamMarks);
        addExamMark.setOnClickListener(this);
        viewExamMark = (ImageView) findViewById(R.id.viewExamMarks);
        viewExamMark.setOnClickListener(this);

        GetAcademicExamInfo(localExamId);
        loadAcademicExamDetails(classMasterId, examId);
        isInternalExternalForTheSubject = Integer.parseInt(db.getAcademicExamsInternalExternalMarkStatus(classMasterId, examId, subjectId));
        String AcademicExamSubjectMarksStatus = db.isAcademicExamSubjectMarksStatusFlag(examId, PreferenceStorage.getTeacherId(getApplicationContext()), PreferenceStorage.getTeacherSubject(getApplicationContext()));
        int checkAcademicExamSubjectMarksStatus = Integer.parseInt(AcademicExamSubjectMarksStatus);
        int checkMarkStatus = Integer.parseInt(markStatus);
        if (checkMarkStatus == 0) {
            if (checkAcademicExamSubjectMarksStatus == 0) {
                addExamMark.setVisibility(View.GONE);
                viewExamMark.setVisibility(View.GONE);
            } else {
                addExamMark.setVisibility(View.GONE);
                viewExamMark.setVisibility(View.GONE);
            }
        } else {
            addExamMark.setVisibility(View.GONE);
            viewExamMark.setVisibility(View.GONE);
        }

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);

        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetAcademicExamInfo(String examIdLocal) {
        try {
            Cursor c = db.getAcademicExamInfo(examIdLocal);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        examId = c.getString(1);
                        examName = c.getString(2);
                        isInternalExternal = c.getString(3);
                        classMasterId = c.getString(4);
                        sectionName = c.getString(5);
                        className = c.getString(6);
                        fromDate = c.getString(7);
                        toDate = c.getString(8);
                        markStatus = c.getString(9);
                    } while (c.moveToNext());
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void loadAcademicExamDetails(String classSectionId, String ExamId) {
        myList.clear();
        try {
            Cursor c = db.getAcademicExamDetailsList(classSectionId, ExamId);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        AcademicExamDetails lde = new AcademicExamDetails();
                        lde.setId(Integer.parseInt(c.getString(0)));
                        lde.setExamDate(c.getString(4));
                        lde.setSubjectName(c.getString(3));
                        lde.setTimes(c.getString(5));

                        // Add this object into the ArrayList myList
                        myList.add(lde);

                    } while (c.moveToNext());
                }
            } else {
                Toast.makeText(getApplicationContext(), "No records", Toast.LENGTH_LONG).show();
            }
            db.close();

            cadapter = new AcademicExamDetailsListBaseAdapter(AcademicExamDetailPage.this, myList);
            loadMoreListView.setAdapter(cadapter);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == addExamMark) {
            int checkInternalMarkStatus = Integer.parseInt(isInternalExternal);
            if (isInternalExternalForTheSubject == 1) {
                Intent intent = new Intent(getApplicationContext(), AddAcademicExamMarksActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("subject_id", subjectId);
                intent.putExtra("classMasterId", classMasterId);
                intent.putExtra("examId", examId);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), AddAcademicExamMarksOnlyTotalActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("subject_id", subjectId);
                intent.putExtra("classMasterId", classMasterId);
                intent.putExtra("examId", examId);
                startActivity(intent);
            }
        }
        if (v == viewExamMark) {
            int checkInternalMarkStatus = Integer.parseInt(isInternalExternal);
            if (isInternalExternalForTheSubject == 1) {
                Intent intent = new Intent(getApplicationContext(), AcademicExamResultView.class);
                intent.putExtra("id", id);
                intent.putExtra("subject_id", subjectId);
                intent.putExtra("classMasterId", classMasterId);
                intent.putExtra("examId", examId);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), AcademicExamOnlyTotalResultView.class);
                intent.putExtra("id", id);
                intent.putExtra("subject_id", subjectId);
                intent.putExtra("classMasterId", classMasterId);
                intent.putExtra("examId", examId);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}
