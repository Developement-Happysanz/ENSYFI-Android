package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.StudentClassTestMarkAddBaseAdapter;
import com.palprotech.ensyfi.adapter.teachermodule.StudentListClassTestMarkBaseAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.viewlist.StudentsClassTestMarks;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.utils.AppValidator;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Admin on 14-07-2017.
 */

public class AddClassTestMarkActivity extends AppCompatActivity implements View.OnClickListener, DialogClickListener {

    long hwId;
    TextView txtTitle, txtTestDate;
    SQLiteHelper db;
    String serverHomeWorkId, yearId, classId, teacherId, homeWorkType, subjectId, subjectName, title,
            testDate, dueDate, homeWorkDetails, status, markStatus, createdBy, createdAt, updatedBy, updatedAt, syncStatus;
    ListView lvStudent;
    ArrayList<StudentsClassTestMarks> myList = new ArrayList<StudentsClassTestMarks>();
    ImageView btnSave;
    private String storeClassId;
    Calendar c = Calendar.getInstance();
    String homeWorkId, formattedServerDate;
    StudentListClassTestMarkBaseAdapter cadapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class_test_mark);
        Intent intent = getIntent();
        hwId = getIntent().getExtras().getLong("hw_id");
        db = new SQLiteHelper(getApplicationContext());
        homeWorkId = String.valueOf(hwId);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTestDate = (TextView) findViewById(R.id.txtTestDate);

        lvStudent = (ListView) findViewById(R.id.listView_students);

        btnSave = (ImageView) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        SimpleDateFormat serverDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedServerDate = serverDF.format(c.getTime());

        GetHomeWorkClassTestDetails(homeWorkId);

        GetStudentsList(classId);
        lvStudent.setItemsCanFocus(true);
        cadapter = new StudentListClassTestMarkBaseAdapter(AddClassTestMarkActivity.this, myList);
        lvStudent.setAdapter(cadapter);
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    private void GetStudentsList(String classSectionId) {

        myList.clear();

        try {
            Cursor c = db.getStudentsOfClassBasedOnClassId(classSectionId);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    int i = 0;
                    do {
                        StudentsClassTestMarks lde = new StudentsClassTestMarks();
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

    private void GetHomeWorkClassTestDetails(String homeWorkId) {

        try {
            Cursor c = db.getClassTestHomeWorkDetails(homeWorkId);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        String homeWorkIdLocal = c.getString(0);
                        serverHomeWorkId = c.getString(1);
                        yearId = c.getString(2);
                        classId = c.getString(3);
                        teacherId = c.getString(4);
                        homeWorkType = c.getString(5);
                        subjectId = c.getString(6);
                        subjectName = c.getString(7);
                        title = c.getString(8);
                        testDate = c.getString(9);
                        dueDate = c.getString(10);
                        homeWorkDetails = c.getString(11);
                        status = c.getString(12);
                        markStatus = c.getString(13);
                        createdBy = c.getString(14);
                        createdAt = c.getString(15);
                        updatedBy = c.getString(16);
                        updatedAt = c.getString(17);
                        syncStatus = c.getString(18);


                    } while (c.moveToNext());
                }
            } else {
                Toast.makeText(getApplicationContext(), "No records", Toast.LENGTH_LONG).show();
            }

            db.close();

            txtTitle.setText(title);
            txtTestDate.setText(testDate);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        SaveStudentsClasstestMarks();
    }

    private boolean validateFields() {
        int getCount = 0;
        getCount = lvStudent.getCount();

        int count = 0;
        int validMark = 100;

   /*     int wantedPosition = lvStudent.getAdapter().getCount();
        int firstPosition = lvStudent.getFirstVisiblePosition() - lvStudent.getHeaderViewsCount();
        int wantedChild = wantedPosition - firstPosition;
        if (wantedChild < 0 || wantedChild >= lvStudent.getAdapter().getCount()) {
//            Log.w(TAG, "Unable to get view for desired position, because it's not being displayed on screen.");
//            return;
        }

//        View wantedView = listView.getChildAt(wantedChild);*/


        int position = 0;
        lvStudent.setItemChecked(position, true);
//        View wantedView = adapter.getView(position, null, listview);


        for (int i = 0; i < lvStudent.getChildCount(); i++) {


//            View viewTelefone = lvStudent.getChildAt(i);
//            View viewTelefone = lvStudent.getChildAt(wantedChild);
            View viewTelefone = cadapter.getView(position, null, lvStudent);

            TextView et1 = (TextView) viewTelefone.findViewById(R.id.txt_studentName);
            EditText edtMarks = (EditText) viewTelefone.findViewById(R.id.class_test_marks);

            int Mark = Integer.parseInt(edtMarks.getText().toString());

            if (!AppValidator.checkNullString(edtMarks.getText().toString().trim())) {
                AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid marks for student - " + String.valueOf(et1.getText()));
            }
//            if (Mark <= 0 || Mark >= validMark + 1) {
//                AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid marks for student - " + String.valueOf(et1.getText()) + " between 0 to " + validMark);
//            }
            else {
                count++;
            }
//            }
            position++;
        }

        if (getCount == count) {
            return true;
        } else {
            return false;
        }
    }

    public View getViewByPosition(int position, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition) {
            return listView.getAdapter().getView(position, null, listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    private void SaveStudentsClasstestMarks() {

        try {

            ArrayList<String> mannschaftsnamen = new ArrayList<String>();

            if (validateFields()) {
                for (int i = 0; i < lvStudent.getAdapter().getCount(); i++) {
                    View viewTelefone = lvStudent.getChildAt(i);
                    TextView et = (TextView) viewTelefone.findViewById(R.id.txt_studentId);
                    TextView et1 = (TextView) viewTelefone.findViewById(R.id.txt_studentName);
                    EditText edtMarks = (EditText) viewTelefone.findViewById(R.id.class_test_marks);

                    if (et != null) {
                        mannschaftsnamen.add(String.valueOf(et.getText().toString()));
                        String enrollId = String.valueOf(et.getText().toString());
                        String studentName = String.valueOf(et1.getText().toString());
                        String marks = edtMarks.getText().toString();
                        String remarks = "";
                        if (marks.isEmpty()) {
                            marks = "0";
                        }

                        long c = db.class_test_mark_insert(enrollId, homeWorkId, serverHomeWorkId, marks, remarks, "Active", PreferenceStorage.getUserId(getApplicationContext()), formattedServerDate, PreferenceStorage.getUserId(getApplicationContext()), formattedServerDate, "NS");
                        if (c == -1) {
                            Toast.makeText(getApplicationContext(), "Error while marks add...",
                                    Toast.LENGTH_LONG).show();
                        }
                        Log.v("ypgs", String.valueOf(et.getText()));
                    }
                }

                db.updateClassTestHomeWorkMarkStatus(homeWorkId);
                Toast.makeText(getApplicationContext(), "Class Test - " + title + ".\n Marks Updated Successfully...",
                        Toast.LENGTH_LONG).show();

                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Marks not updated...", Toast.LENGTH_LONG).show();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "Try again...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}
