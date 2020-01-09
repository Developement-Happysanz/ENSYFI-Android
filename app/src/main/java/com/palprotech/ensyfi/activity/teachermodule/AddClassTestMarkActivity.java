package com.palprotech.ensyfi.activity.teachermodule;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTestMark;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTestMarkList;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.AppValidator;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Admin on 08-08-2017.
 */

public class AddClassTestMarkActivity extends AppCompatActivity implements View.OnClickListener, DialogClickListener, IServiceListener {

    private static final String TAG = AddClassTestMarkActivity.class.getName();
    long hwId;
    TextView txtTitle, txtTestDate;
    SQLiteHelper db;
    String serverHomeWorkId, yearId, classId, teacherId, homeWorkType, subjectId, subjectName, title, pageEdit,
            testDate, dueDate, homeWorkDetails, status, markStatus, createdBy, createdAt, updatedBy, updatedAt, syncStatus;
    ImageView btnSave;
    Calendar c = Calendar.getInstance();
    String homeWorkId, formattedServerDate;
    LinearLayout layout_all;
    ArrayList<ClassTestMark> classTestMarkArrayList = new ArrayList<>();

    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;

    private String resString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class_test_mark);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        hwId = getIntent().getExtras().getLong("hw_id");
        db = new SQLiteHelper(getApplicationContext());
        layout_all = (LinearLayout) findViewById(R.id.layout_student_list);
        homeWorkId = String.valueOf(hwId);
        pageEdit = getIntent().getStringExtra("mark_array");
        if (pageEdit != null && pageEdit.equalsIgnoreCase("edit")) {
            GetClassTestMarkData();
        } else {
            GetHomeWorkClassTestDetails(homeWorkId);

            GetStudentsList(classId);
        }

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTestDate = (TextView) findViewById(R.id.txtTestDate);

        btnSave = (ImageView) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);


        SimpleDateFormat serverDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedServerDate = serverDF.format(c.getTime());

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetClassTestMarkData() {

        resString = "markData";

        if (classTestMarkArrayList != null)
            classTestMarkArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAM_HOMEWORK_ID, homeWorkId);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_CLASS_TEST_MARK;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);


        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }


    private void GetStudentsList(String classSectionId) {

        try {

            TableLayout layout = new TableLayout(this);
            layout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            layout_all.setScrollbarFadingEnabled(false);
            layout.setPadding(0, 50, 0, 50);

            TableRow.LayoutParams cellLp = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            cellLp.setMargins(2, 2, 2, 2);

            int i = 1;
            Cursor c = db.getStudentsOfClassBasedOnClassId(classSectionId);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        for (int c1 = 0; c1 <= 0; c1++) {
                            LinearLayout cell = new LinearLayout(this);
                            cell.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                            cell.setOrientation(LinearLayout.HORIZONTAL);
                            cell.setPadding(20, 5, 20, 5);
                            cell.setBackgroundColor(Color.parseColor("#FFFFFF"));

                            TextView t1 = new TextView(this);
                            t1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.10f));
                            t1.setGravity(Gravity.CENTER);

                            TextView t3 = new TextView(this);
                            t3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.10f));
                            t3.setGravity(Gravity.CENTER);

                            TextView t2 = new TextView(this);
                            t2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.50f));

                            EditText b = new EditText(this);
                            b.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT, 0.30f));
                            b.setGravity(Gravity.CENTER);

                            String name = "";
                            if (!classTestMarkArrayList.isEmpty() && classTestMarkArrayList!=null){
                                name = classTestMarkArrayList.get(c1).getMarks();
                            }
                            b.setText(name);
                            b.setId(R.id.my_edit_text_1);
                            b.requestFocusFromTouch();
                            b.setTextSize(13.0f);
                            b.setTypeface(null, Typeface.BOLD);
                            b.setKeyListener(DigitsKeyListener.getInstance("0123456789AB"));
                            b.setInputType(InputType.TYPE_CLASS_TEXT);
                            b.setAllCaps(true);
                            b.setSingleLine(true);
                            b.setTextColor(ContextCompat.getColor(this, R.color.new_gray));
                            b.setPressed(true);
                            b.setHeight(120);
                            b.setWidth(50);
                            b.setPadding(1, 0, 2, 0);

                            t1.setText(c.getString(1));
                            t1.setVisibility(View.GONE);
//                            t1.setText(""+i);
                            t1.setTextColor(ContextCompat.getColor(this, R.color.new_gray));
                            t1.setHeight(120);
                            t1.setWidth(80);
                            t1.setPadding(1, 0, 2, 0);
                            t1.setId(R.id.my_text_1);

//                            t3.setText(c.getString(1));
                            t3.setText("" + i);
                            t3.setTextColor(ContextCompat.getColor(this, R.color.new_gray));
                            t3.setHeight(120);
                            t3.setWidth(80);
                            t3.setPadding(1, 0, 2, 0);
//                            t1.setId(R.id.my_text_1);

                            t2.setText(c.getString(4));
                            t2.setTextColor(ContextCompat.getColor(this, R.color.new_gray));
                            t2.setHeight(120);
                            t2.setWidth(120);
                            t2.setPadding(1, 0, 2, 0);
                            t2.setId(R.id.my_text_2);


                            cell.addView(t1);
                            cell.addView(t3);
                            cell.addView(t2);
                            cell.addView(b);

                            layout_all.addView(cell);
                        }
                        i++;
                    } while (c.moveToNext());

                }
            }


            db.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void GetStudentsEditList(ArrayList<ClassTestMark> classSectionId) {

        try {

            TableLayout layout = new TableLayout(this);
            layout.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            layout_all.setScrollbarFadingEnabled(false);
            layout.setPadding(0, 50, 0, 50);

            TableRow.LayoutParams cellLp = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            cellLp.setMargins(2, 2, 2, 2);

            int i = 1;
            for (int c1 = 0; c1 < classSectionId.size(); c1++) {
                LinearLayout cell = new LinearLayout(this);
                cell.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                cell.setOrientation(LinearLayout.HORIZONTAL);
                cell.setPadding(20, 5, 20, 5);
                cell.setBackgroundColor(Color.parseColor("#FFFFFF"));

                TextView t1 = new TextView(this);
                t1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 0.10f));
                t1.setGravity(Gravity.CENTER);

                TextView t3 = new TextView(this);
                t3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 0.10f));
                t3.setGravity(Gravity.CENTER);

                TextView t2 = new TextView(this);
                t2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 0.50f));

                EditText b = new EditText(this);
                b.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 0.30f));
                b.setGravity(Gravity.CENTER);

                String mark = "";
                if (!classSectionId.isEmpty() && classSectionId!=null){
                    mark = classSectionId.get(c1).getMarks();
                }
                b.setText(mark);
                b.setId(R.id.my_edit_text_1);
                b.requestFocusFromTouch();
                b.setTextSize(13.0f);
                b.setTypeface(null, Typeface.BOLD);
                b.setKeyListener(DigitsKeyListener.getInstance("0123456789AB"));
                b.setInputType(InputType.TYPE_CLASS_TEXT);
                b.setAllCaps(true);
                b.setSingleLine(true);
                b.setTextColor(ContextCompat.getColor(this, R.color.new_gray));
                b.setPressed(true);
                b.setHeight(120);
                b.setWidth(50);
                b.setPadding(1, 0, 2, 0);


                t1.setText(classSectionId.get(c1).getEnroll_id());
                t1.setVisibility(View.GONE);
                t1.setTextColor(ContextCompat.getColor(this, R.color.new_gray));
                t1.setHeight(120);
                t1.setWidth(80);
                t1.setPadding(1, 0, 2, 0);
                t1.setId(R.id.my_text_1);

//                            t3.setText(c.getString(1));
                t3.setText("" + i);
                t3.setTextColor(ContextCompat.getColor(this, R.color.new_gray));
                t3.setHeight(120);
                t3.setWidth(80);
                t3.setPadding(1, 0, 2, 0);
//                            t1.setId(R.id.my_text_1);

                String name = "";
                if (!classSectionId.isEmpty() && classSectionId!=null){
                    name = classSectionId.get(c1).getName();
                }
                t2.setText(name);
                t2.setTextColor(ContextCompat.getColor(this, R.color.new_gray));
                t2.setHeight(120);
                t2.setWidth(120);
                t2.setPadding(1, 0, 2, 0);
                t2.setId(R.id.my_text_2);


                cell.addView(t1);
                cell.addView(t3);
                cell.addView(t2);
                cell.addView(b);

                layout_all.addView(cell);
                i++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean validateFields() {
        int getCount = 0;
        getCount = layout_all.getChildCount();

        int count = 0;
        int validMark = 100;

        int nViews = layout_all.getChildCount();

        for (int i = 0; i < nViews; i++) {

            View view = layout_all.getChildAt(i);

            EditText ed_marks = (EditText) view.findViewById(R.id.my_edit_text_1);
            TextView tv_studentName = (TextView) view.findViewById(R.id.my_text_2);

            String Marks = ed_marks.getText().toString().trim();

            if (!AppValidator.checkNullString(Marks)) {
                AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid marks for student - " + String.valueOf(tv_studentName.getText()));
            } else if ((AppValidator.checkEditTextValid100AndA(Marks,validMark)).equalsIgnoreCase("NotValidMark") || (AppValidator.checkEditTextValid100AndA(Marks,validMark)).equalsIgnoreCase("NotValidAbsent")) {
                if (((AppValidator.checkEditTextValid100AndA(Marks,validMark)).equalsIgnoreCase("NotValidMark"))) {
                    AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid marks for student - " + String.valueOf(tv_studentName.getText()) + " between 0 to " + validMark);
                }
                if (((AppValidator.checkEditTextValid100AndA(Marks,validMark)).equalsIgnoreCase("NotValidAbsent"))) {
                    AlertDialogHelper.showSimpleAlertDialog(this, "Enter valid leave character as 'AB' for student - " + String.valueOf(tv_studentName.getText()));
                }
            } else {
                count++;
            }
        }

        if (getCount == count) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            SaveStudentsClasstestMarks();
        }
    }

    private void SaveStudentsClasstestMarks() {

        try {
            if (validateFields()) {

                int nViews = layout_all.getChildCount();

                for (int i = 0; i < nViews; i++) {

                    View view = layout_all.getChildAt(i);

                    EditText ed_marks = (EditText) view.findViewById(R.id.my_edit_text_1);
//                    TextView tv_studentName = (TextView) view.findViewById(R.id.my_text_2);
                    TextView tv_studentId = (TextView) view.findViewById(R.id.my_text_1);

                    if (tv_studentId != null) {
                        String enrollId = String.valueOf(tv_studentId.getText().toString().trim());
//                        String studentName = String.valueOf(tv_studentName.getText().toString().trim());
                        String marks = ed_marks.getText().toString().trim();
                        String remarks = "";
                        if (marks.isEmpty()) {
                            marks = "0";
                        }

                        if (pageEdit != null && pageEdit.equalsIgnoreCase("edit")) {
                            resString = "markSend";
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put(EnsyfiConstants.PARAMS_CTMARKS_HW_SERVER_MASTER_ID, homeWorkId);
                                jsonObject.put(EnsyfiConstants.PARAMS_CTMARKS_STUDENT_ID, enrollId);
                                jsonObject.put(EnsyfiConstants.PARAMS_CTMARKS_MARKS, marks);
//                                jsonObject.put(EnsyfiConstants.PARAMS_CTMARKS_REMARKS, remarks);
                                jsonObject.put(EnsyfiConstants.KEY_USER_ID, PreferenceStorage.getUserId(this));
                                jsonObject.put(EnsyfiConstants.PARAMS_CTMARKS_CREATED_AT, formattedServerDate);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
                            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(this) + EnsyfiConstants.EDIT_CLASS_TEST_MARK_API;
                            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
                        }

                        long c = db.class_test_mark_insert(enrollId, homeWorkId, serverHomeWorkId, marks, remarks, "Active", PreferenceStorage.getUserId(getApplicationContext()), formattedServerDate, PreferenceStorage.getUserId(getApplicationContext()), formattedServerDate, "NS");
                        if (c == -1) {
                            Toast.makeText(getApplicationContext(), "Error while marks add...",
                                    Toast.LENGTH_LONG).show();
                        }
                        Log.v("ypgs", String.valueOf(tv_studentId.getText()));
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
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

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
        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {
            try {
                if (resString.equalsIgnoreCase("markSend")) {
                    String classTestMarksServerId = response.getString("last_id");
                    if (!classTestMarksServerId.isEmpty()) {
//                        db.updateClassTestSyncStatus(classTestMarkId);
                    }
                } else {
                    Gson gson = new Gson();
                    ClassTestMarkList classTestMarkList = gson.fromJson(response.toString(), ClassTestMarkList.class);
                    if (classTestMarkList.getClassTestMark() != null && classTestMarkList.getClassTestMark().size() > 0) {
                        classTestMarkArrayList = classTestMarkList.getClassTestMark();
                    }
                    GetStudentsEditList(classTestMarkArrayList);
                }

            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onError(String error) {

    }
}
