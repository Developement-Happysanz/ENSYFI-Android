package com.palprotech.ensyfi.activity.loginmodule;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.TeacherHandlingSubjectSpinnerAdapter;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.utils.PreferenceStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Created by Narendar on 19/07/17.
 */

public class TeacherInfoActivity extends AppCompatActivity implements DialogClickListener, View.OnClickListener {

    private static final String TAG = TeacherInfoActivity.class.getName();
    private ImageView teacherImg, btnBack;

    private TextView teacherId, teacherName, teacherGender, teacherAge, teacherNationality, teacherReligion, teacherCaste,
            teacherCommunity, teacherAddress, teacherSubject, classTeacher, teacherMobile, teacherSecondaryMobile, teacherMail,
            teacherSecondaryMail, teacherSectionName, teacherClassName, teacherClassTaken, teacherSkillSet, teacherPreviousExp,
            teacherTotalExp;
    ArrayList<String> lsSubjectList;
    SQLiteHelper db;
    private TeacherHandlingSubjectSpinnerAdapter teacherHandlingSubjectSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile_info);
        SetUI();

        teacherHandlingSubjectSpinnerAdapter = new TeacherHandlingSubjectSpinnerAdapter(this, R.layout.handling_subject_dropdown_item, lsSubjectList);
        callTeacherInfoPreferences();
    }

    private void SetUI() {

        btnBack = (ImageView) findViewById(R.id.back_res);
        btnBack.setOnClickListener(this);

        teacherImg = (ImageView) findViewById(R.id.img_teacher_profile);
        db = new SQLiteHelper(getApplicationContext());
        lsSubjectList = new ArrayList<String>();

        // Teacher's Info view
        teacherId = (TextView) findViewById(R.id.txtTeacherid);
        teacherName = (TextView) findViewById(R.id.txtTeacherName);
        teacherGender = (TextView) findViewById(R.id.txtTeacherGender);
        teacherAge = (TextView) findViewById(R.id.txtTeacherAge);
        teacherNationality = (TextView) findViewById(R.id.txtTeacherNationality);
        teacherReligion = (TextView) findViewById(R.id.txtTeacherReligion);
        teacherCaste = (TextView) findViewById(R.id.txtTeacherCaste);
        teacherCommunity = (TextView) findViewById(R.id.txtTeacherCommunity);
        teacherAddress = (TextView) findViewById(R.id.txtTeacherAddress);
        teacherSubject = (TextView) findViewById(R.id.txtTeacherSubject);
        teacherSubject.setOnClickListener(this);
        classTeacher = (TextView) findViewById(R.id.txtClassTeacher);
        teacherMobile = (TextView) findViewById(R.id.txtTeacherMobile);
        teacherSecondaryMobile = (TextView) findViewById(R.id.txtTeacherSecondaryMobile);
        teacherMail = (TextView) findViewById(R.id.txtTeacherMail);
        teacherSecondaryMail = (TextView) findViewById(R.id.txtTeacherSecondaryMail);
        teacherSectionName = (TextView) findViewById(R.id.txtTeacherSectionName);
        teacherClassName = (TextView) findViewById(R.id.txtTeacherClassName);
        teacherClassTaken = (TextView) findViewById(R.id.txtTeacherClassTaken);
        teacherSkillSet = (TextView) findViewById(R.id.txtTeacherSkillSet);
        teacherPreviousExp = (TextView) findViewById(R.id.txtTeacherPreviousExp);
        teacherTotalExp = (TextView) findViewById(R.id.txtTeacherTotalExp);


        getSubjectList();
    }

    private void getSubjectList() {
        Vector<String> vecSubjectList;
        vecSubjectList = new Vector<String>();
//        List<String> lsSubjectList = new ArrayList<String>();
        try {
            Cursor c = db.getAllHandlingSubjectList();
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
//            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item_ns, lsSubjectList);

//            spnSubjectList.setAdapter(dataAdapter3);
//            spnSubjectList.setWillNotDraw(false);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error getting class list lookup", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void callTeacherInfoPreferences() {

        teacherId.setText(PreferenceStorage.getTeacherId(getApplicationContext()));
        teacherName.setText(PreferenceStorage.getTeacherName(getApplicationContext()));
        teacherGender.setText(PreferenceStorage.getTeacherGender(getApplicationContext()));
        teacherAge.setText(PreferenceStorage.getTeacherAge(getApplicationContext()));
        teacherNationality.setText(PreferenceStorage.getTeacherNationality(getApplicationContext()));
        teacherReligion.setText(PreferenceStorage.getTeacherReligion(getApplicationContext()));
        teacherCaste.setText(PreferenceStorage.getTeacherCaste(getApplicationContext()));
        teacherCommunity.setText(PreferenceStorage.getTeacherCommunity(getApplicationContext()));
        teacherAddress.setText(PreferenceStorage.getTeacherAddress(getApplicationContext()));
//        teacherSubject.setText(PreferenceStorage.getTeacherSubjectName(getApplicationContext()));
        classTeacher.setText(PreferenceStorage.getClassTeacher(getApplicationContext()));
        teacherMobile.setText(PreferenceStorage.getTeacherMobile(getApplicationContext()));
        teacherSecondaryMobile.setText(PreferenceStorage.getTeacherSecondaryMobile(getApplicationContext()));
        teacherMail.setText(PreferenceStorage.getTeacherMail(getApplicationContext()));
        teacherSecondaryMail.setText(PreferenceStorage.getTeacherSecondaryMail(getApplicationContext()));
        teacherSectionName.setText(PreferenceStorage.getTeacherSectionName(getApplicationContext()));
        teacherClassName.setText(PreferenceStorage.getTeacherClassName(getApplicationContext()));
        teacherClassTaken.setText(PreferenceStorage.getTeacherClassTaken(getApplicationContext()));
        teacherSkillSet.setText(PreferenceStorage.getTeacherSkillSet(getApplicationContext()));
        teacherPreviousExp.setText(PreferenceStorage.getTeacherPreviousInstitute(getApplicationContext()));
        teacherTotalExp.setText(PreferenceStorage.getTeacherTotalExperience(getApplicationContext()));
        String url = PreferenceStorage.getTeacherPic(this);


        if (((url != null) && !(url.isEmpty()))) {
            Picasso.with(this).load(url).placeholder(R.drawable.profile_pic).error(R.drawable.profile_pic).into(teacherImg);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_res:
                finish();
                break;
            case R.id.txtTeacherSubject:
                Log.d(TAG, "Available cities count" + teacherHandlingSubjectSpinnerAdapter.getCount());
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
                View view = getLayoutInflater().inflate(R.layout.gender_header_layout, null);
                TextView header = (TextView) view.findViewById(R.id.gender_header);
                header.setText("Handling Subjects");
                builderSingle.setCustomTitle(view);

                builderSingle.setAdapter(teacherHandlingSubjectSpinnerAdapter, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        teacherSubject.setText(teacherHandlingSubjectSpinnerAdapter.getItem(which).toString());
//                        teacherSubject.clearComposingText();
                        dialog.dismiss();
                    }
                }).create().show();
                break;
        }

    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}
