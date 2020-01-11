package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.parentsmodule.ParentDashBoardActivity;
import com.palprotech.ensyfi.adapter.teachermodule.AcademicExamsListBaseAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.support.SaveTeacherData;
import com.palprotech.ensyfi.bean.teacher.viewlist.AcademicExams;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Created by Admin on 14-07-2017.
 */

public class AcademicExamViewActivity extends AppCompatActivity implements DialogClickListener, IServiceListener {

    private static final String TAG = AcademicExamViewActivity.class.getName();
    private Spinner spnClassList, spnSubjectList;
    protected ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    private SaveTeacherData teacherData;
    List<String> lsClassList = new ArrayList<String>();
    SQLiteHelper db;
    Vector<String> vecClassList;
    private String classSection, getClassSectionId;
    ArrayList<AcademicExams> myList = new ArrayList<AcademicExams>();
    AcademicExamsListBaseAdapter cadapter;
    ListView loadMoreListView;
    String subjectName = "", getClassSubjectId;
    String examId, examName, isInternalExternal, classMasterId, sectionName, className, fromDate, toDate,
            markStatus, isInternalExternalSubject;
    int isInternalExternalForTheSubject;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_exam_view_activity);

        db = new SQLiteHelper(getApplicationContext());
        vecClassList = new Vector<String>();

        progressDialogHelper = new ProgressDialogHelper(this);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        teacherData = new SaveTeacherData(this);

        spnClassList = (Spinner) findViewById(R.id.class_list_spinner);
        spnSubjectList = (Spinner) findViewById(R.id.subject_list_spinner);

        loadMoreListView = (ListView) findViewById(R.id.listView_events);

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
                getSubjectId(subjectName, getClassSectionId);
                PreferenceStorage.saveTeacherSubject(getApplicationContext(), getClassSubjectId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getExamDetails();

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
                        lde.setFromDate(c.getString(7));
                        lde.setToDate(c.getString(8));

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
        GetAcademicExamInfo(String.valueOf(id));
//        Date Date2 = new Date();
        Date Date2  = null;
//        Date date1 = new Date();
        Date date12 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");
        if (!toDate.isEmpty()) {
            try {
                Date2 = sdf.parse(toDate);
                date12 = sdf.parse(sdf.format(new Date() ));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if ((Date2.compareTo(date12) > 0) || (Date2.compareTo(date12) == 0)) {
            Intent intent = new Intent(this, AcademicExamDetailPage.class);
            intent.putExtra("id", id);
            intent.putExtra("subject_id", getClassSubjectId);
            startActivityForResult(intent, 0);
        }
        else {
            isInternalExternalForTheSubject = Integer.parseInt(db.getAcademicExamsInternalExternalMarkStatus(classMasterId, examId, getClassSubjectId));
            String AcademicExamSubjectMarksStatus = db.isAcademicExamSubjectMarksStatusFlag(examId, PreferenceStorage.getTeacherId(getApplicationContext()), PreferenceStorage.getTeacherSubject(getApplicationContext()));
            int checkAcademicExamSubjectMarksStatus = Integer.parseInt(AcademicExamSubjectMarksStatus);
            int checkMarkStatus = Integer.parseInt(markStatus);
            if (checkMarkStatus == 0) {
                if (checkAcademicExamSubjectMarksStatus == 0) {
                    if (isInternalExternalForTheSubject == 1) {
                        Intent intent = new Intent(getApplicationContext(), AddAcademicExamMarksActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("subject_id", getClassSubjectId);
                        intent.putExtra("classMasterId", classMasterId);
                        intent.putExtra("examId", examId);
                        intent.putExtra("type", "add");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), AddAcademicExamMarksOnlyTotalActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("subject_id", getClassSubjectId);
                        intent.putExtra("classMasterId", classMasterId);
                        intent.putExtra("examId", examId);
                        intent.putExtra("type", "add");
                        startActivity(intent);
                    }
                } else {
                    if (isInternalExternalForTheSubject == 1) {
                        Intent intent = new Intent(getApplicationContext(), AcademicExamResultView.class);
                        intent.putExtra("id", id);
                        intent.putExtra("subject_id", getClassSubjectId);
                        intent.putExtra("classMasterId", classMasterId);
                        intent.putExtra("examId", examId);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), AcademicExamOnlyTotalResultView.class);
                        intent.putExtra("id", id);
                        intent.putExtra("subject_id", getClassSubjectId);
                        intent.putExtra("classMasterId", classMasterId);
                        intent.putExtra("examId", examId);
                        startActivity(intent);
                    }
                }
            } else {
                if (isInternalExternalForTheSubject == 1) {
                    Intent intent = new Intent(getApplicationContext(), AcademicExamResultView.class);
                    intent.putExtra("id", id);
                    intent.putExtra("subject_id", getClassSubjectId);
                    intent.putExtra("classMasterId", classMasterId);
                    intent.putExtra("examId", examId);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), AcademicExamOnlyTotalResultView.class);
                    intent.putExtra("id", id);
                    intent.putExtra("subject_id", getClassSubjectId);
                    intent.putExtra("classMasterId", classMasterId);
                    intent.putExtra("examId", examId);
                    startActivity(intent);
                }
            }
        }
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


    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    private void getExamDetails() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(EnsyfiConstants.TEACHER_ID, PreferenceStorage.getTeacherId(this));

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
        String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_EXAM_DETAIL_TEACHER_API;
        serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
    }

    private boolean validateSignInResponse(JSONObject response) {
        boolean signInsuccess = false;
        if ((response != null)) {
            try {
                String status = response.getString("status");
                String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                Log.d(TAG, "status val" + status + "msg" + msg);

                if ((status != null)) {
                    if (((status.equalsIgnoreCase("activationError")) || (status.equalsIgnoreCase("alreadyRegistered")) ||
                            (status.equalsIgnoreCase("notRegistered")) || (status.equalsIgnoreCase("error")))) {
                        signInsuccess = false;
                        Log.d(TAG, "Show error dialog");
                        AlertDialogHelper.showSimpleAlertDialog(this, msg);
                    } else {
                        signInsuccess = true;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return signInsuccess;
    }

    @Override
    public void onResponse(JSONObject response) {
//        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {
            JSONArray getExamsOfClassArray = null;
            try {
                getExamsOfClassArray = response.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (getExamsOfClassArray != null && getExamsOfClassArray.length() > 0) {
                teacherData.saveExamsDetails(getExamsOfClassArray);
            }
        }
    }

    @Override
    public void onError(String error) {

    }
}
