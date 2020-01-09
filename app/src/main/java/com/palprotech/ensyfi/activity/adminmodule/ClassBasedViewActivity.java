package com.palprotech.ensyfi.activity.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.adminmodule.ClassStudentListAdapter;
import com.palprotech.ensyfi.adapter.adminmodule.TeacherViewListAdapter;
import com.palprotech.ensyfi.bean.admin.support.StoreClass;
import com.palprotech.ensyfi.bean.admin.support.StoreSection;
import com.palprotech.ensyfi.bean.admin.viewlist.ClassStudent;
import com.palprotech.ensyfi.bean.admin.viewlist.ClassStudentList;
import com.palprotech.ensyfi.bean.admin.viewlist.TeacherView;
import com.palprotech.ensyfi.bean.admin.viewlist.TeacherViewList;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 18-07-2017.
 */

public class ClassBasedViewActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = StudentsViewActivity.class.getName();
    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    private Spinner spnClassList, spnSectionList, spnClassStudentTeacher;
    private String checkSpinner = "", storeClassId, storeSectionId;
    ListView loadMoreListView;
    ClassStudentListAdapter classStudentListAdapter;
    ArrayList<ClassStudent> classStudentArrayList;
    int pageNumber = 0, totalCount = 0;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();
    //    private RadioGroup radioStudentsTeachersView;
    TeacherViewListAdapter teacherViewListAdapter;
    ArrayList<TeacherView> teacherViewArrayList;
    private RelativeLayout TeacherList, StudentList;
    private String isType = "STUD";
    private List<String> mClassStudentTeacherList = new ArrayList<String>();
    String classStudentTeacher = "Student", checkSpin = "no";
    ArrayAdapter<String> dataAdapter3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_based_view);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        spnClassList = (Spinner) findViewById(R.id.class_list_spinner);
        spnSectionList = (Spinner) findViewById(R.id.section_list_spinner);
        spnClassStudentTeacher = (Spinner) findViewById(R.id.class_student_teacher_spinner);
//        radioStudentsTeachersView = (RadioGroup) findViewById(R.id.radioStudentsTeachersView);
        loadMoreListView = (ListView) findViewById(R.id.listView_events);
        loadMoreListView.setOnItemClickListener(this);
        classStudentArrayList = new ArrayList<>();
        teacherViewArrayList = new ArrayList<>();
        TeacherList = (RelativeLayout) findViewById(R.id.layout_frame_teacher);
        StudentList = (RelativeLayout) findViewById(R.id.layout_frame_student);

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        GetClassData();

        spnClassList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                StoreClass classList = (StoreClass) parent.getSelectedItem();

                if (classStudentArrayList != null) {
                    classStudentArrayList.clear();
                    loadMoreListView.setAdapter(classStudentListAdapter);
                }
                if (teacherViewArrayList != null) {
                    teacherViewArrayList.clear();
                    loadMoreListView.setAdapter(teacherViewListAdapter);
                }
                storeClassId = classList.getClassId();
                GetSectionData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnSectionList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                StoreSection sectionList = (StoreSection) parent.getSelectedItem();
                storeSectionId = sectionList.getSectionId();

                if (checkSpin.equalsIgnoreCase("no")) {
                    mClassStudentTeacherList.add("Student");
                    mClassStudentTeacherList.add("Teacher");

                    dataAdapter3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_ns, mClassStudentTeacherList);

                    spnClassStudentTeacher.setAdapter(dataAdapter3);
                    spnClassStudentTeacher.setWillNotDraw(false);
                    checkSpin = "yes";
                }

                if (classStudentArrayList != null) {
                    classStudentArrayList.clear();
                    loadMoreListView.setAdapter(classStudentListAdapter);
                }
                if (teacherViewArrayList != null) {
                    teacherViewArrayList.clear();
                    loadMoreListView.setAdapter(teacherViewListAdapter);
                }

                if (classStudentTeacher.equalsIgnoreCase("Student")) {
                    findViewById(R.id.alert_student).setVisibility(View.VISIBLE);
                    findViewById(R.id.alert_teacher).setVisibility(View.GONE);
                    StudentList.setVisibility(View.VISIBLE);
                    TeacherList.setVisibility(View.GONE);
                    GetStudentData();
                } else {
                    findViewById(R.id.alert_student).setVisibility(View.GONE);
                    findViewById(R.id.alert_teacher).setVisibility(View.VISIBLE);
                    TeacherList.setVisibility(View.VISIBLE);
                    StudentList.setVisibility(View.GONE);
                    GetTeacherData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


//        mClassStudentTeacherList.add("Student or Teacher");


        spnClassStudentTeacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classStudentTeacher = parent.getItemAtPosition(position).toString();

                if (checkSpin.equalsIgnoreCase("yes")) {
                    if (classStudentTeacher.equalsIgnoreCase("Student")) {
                        findViewById(R.id.alert_student).setVisibility(View.VISIBLE);
                        findViewById(R.id.alert_teacher).setVisibility(View.GONE);
                        StudentList.setVisibility(View.VISIBLE);
                        TeacherList.setVisibility(View.GONE);
                        GetStudentData();
                    } else {
                        findViewById(R.id.alert_student).setVisibility(View.GONE);
                        findViewById(R.id.alert_teacher).setVisibility(View.VISIBLE);
                        TeacherList.setVisibility(View.VISIBLE);
                        StudentList.setVisibility(View.GONE);
                        GetTeacherData();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        radioStudentsTeachersView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//
//                switch (checkedId) {
//                    case R.id.radioStudent:
//                        StudentList.setVisibility(View.VISIBLE);
//                        TeacherList.setVisibility(View.GONE);
//                        GetStudentData();
//                        break;
//
//                    case R.id.radioTeachers:
//                        TeacherList.setVisibility(View.VISIBLE);
//                        StudentList.setVisibility(View.GONE);
//                        GetTeacherData();
//                        break;
//                }
//            }
//        });

    }

    private void GetClassData() {

        checkSpinner = "class";

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID, PreferenceStorage.getStudentClassIdPreference(getApplicationContext()));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_CLASS_LISTS;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);


        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private void GetSectionData() {

        checkSpinner = "section";

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID_LIST, storeClassId);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_SECTION_LISTS;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private void GetTeacherData() {

        checkSpinner = "teachers";

        if (teacherViewArrayList != null)
            teacherViewArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID_NEW, storeClassId);
                jsonObject.put(EnsyfiConstants.PARAMS_SECTION_ID, storeSectionId);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_VIEW_TEACHERS_INFO_LIST;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private void GetStudentData() {

        checkSpinner = "students";

        if (classStudentArrayList != null)
            classStudentArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID_LIST, storeClassId);
                jsonObject.put(EnsyfiConstants.PARAMS_SECTION_ID_LIST, storeSectionId);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_STUDENT_LISTS;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (checkSpinner.equalsIgnoreCase("students")) {
            Log.d(TAG, "onEvent list item click" + position);
            ClassStudent classStudent = null;
            if ((classStudentListAdapter != null) && (classStudentListAdapter.ismSearching())) {
                Log.d(TAG, "while searching");
                int actualindex = classStudentListAdapter.getActualEventPos(position);
                Log.d(TAG, "actual index" + actualindex);
                classStudent = classStudentArrayList.get(actualindex);
            } else {
                classStudent = classStudentArrayList.get(position);
            }
            Intent intent = new Intent(this, ClassStudentDetailsActivity.class);
            intent.putExtra("eventObj", classStudent);
            startActivity(intent);
        }
        if (checkSpinner.equalsIgnoreCase("teachers")) {
            Log.d(TAG, "onEvent list item click" + position);
            TeacherView teacherView = null;
            if ((teacherViewListAdapter != null) && (teacherViewListAdapter.ismSearching())) {
                Log.d(TAG, "while searching");
                int actualindex = teacherViewListAdapter.getActualEventPos(position);
                Log.d(TAG, "actual index" + actualindex);
                teacherView = teacherViewArrayList.get(actualindex);
            } else {
                teacherView = teacherViewArrayList.get(position);
            }
            Intent intent = new Intent(this, TeacherViewDetailsActivity.class);
            intent.putExtra("eventObj", teacherView);
            startActivity(intent);
        }
    }

    @Override
    public void onResponse(final JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        try {
            if (validateSignInResponse(response)) {

                if (checkSpinner.equalsIgnoreCase("class")) {

                    JSONArray getData = response.getJSONArray("data");
                    JSONObject userData = getData.getJSONObject(0);
                    int getLength = getData.length();
                    String subjectName = null;
                    Log.d(TAG, "userData dictionary" + userData.toString());

                    String classId = "";
                    String className = "";
                    ArrayList<StoreClass> classesList = new ArrayList<>();

                    for (int i = 0; i < getLength; i++) {

                        classId = getData.getJSONObject(i).getString("class_id");
                        className = getData.getJSONObject(i).getString("class_name");

                        classesList.add(new StoreClass(classId, className));
                    }

                    //fill data in spinner
                    ArrayAdapter<StoreClass> adapter = new ArrayAdapter<StoreClass>(getApplicationContext(), R.layout.spinner_item_ns, classesList);
                    spnClassList.setAdapter(adapter);
                } else if (checkSpinner.equalsIgnoreCase("section")) {
                    JSONArray getData = response.getJSONArray("data");
                    JSONObject userData = getData.getJSONObject(0);
                    int getLength = getData.length();
                    Log.d(TAG, "userData dictionary" + userData.toString());

                    String sectionId = "";
                    String sectionclass = "";
                    ArrayList<StoreSection> sectionList = new ArrayList<>();

                    for (int i = 0; i < getLength; i++) {

                        sectionId = getData.getJSONObject(i).getString("sec_id");
                        sectionclass = getData.getJSONObject(i).getString("sec_name");
                        sectionList.add(new StoreSection(sectionId, sectionclass));
                    }

                    //fill data in spinner
                    ArrayAdapter<StoreSection> adapter = new ArrayAdapter<StoreSection>(getApplicationContext(), R.layout.spinner_item_ns, sectionList);
                    spnSectionList.setAdapter(adapter);
                } else if (checkSpinner.equalsIgnoreCase("students")) {

                    JSONArray getData = response.getJSONArray("data");
                    if (getData != null && getData.length() > 0) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressDialogHelper.hideProgressDialog();
                                Gson gson = new Gson();
                                ClassStudentList classStudentList = gson.fromJson(response.toString(), ClassStudentList.class);
                                if (classStudentList.getClassStudent() != null && classStudentList.getClassStudent().size() > 0) {
                                    totalCount = classStudentList.getCount();
                                    isLoadingForFirstTime = false;
                                    updateListStudentAdapter(classStudentList.getClassStudent());
                                }
                            }
                        });
                    } else {
                        if (classStudentArrayList != null) {
                            classStudentArrayList.clear();
                            classStudentListAdapter = new ClassStudentListAdapter(this, this.classStudentArrayList);
                            loadMoreListView.setAdapter(classStudentListAdapter);
                        }
                    }

                } else {
                    JSONArray getData = response.getJSONArray("data");
                    if (getData != null && getData.length() > 0) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressDialogHelper.hideProgressDialog();

                                Gson gson = new Gson();
                                TeacherViewList teacherViewList = gson.fromJson(response.toString(), TeacherViewList.class);
                                if (teacherViewList.getTeacherView() != null && teacherViewList.getTeacherView().size() > 0) {
                                    totalCount = teacherViewList.getCount();
                                    isLoadingForFirstTime = false;
                                    updateListTeacherAdapter(teacherViewList.getTeacherView());
                                }
                            }
                        });
                    } else {
                        if (teacherViewArrayList != null) {
                            teacherViewArrayList.clear();
                            teacherViewListAdapter = new TeacherViewListAdapter(this, this.teacherViewArrayList);
                            loadMoreListView.setAdapter(teacherViewListAdapter);
                        }
                    }
                }

            } else {
                Log.d(TAG, "Error while sign In");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
                AlertDialogHelper.showSimpleAlertDialog(ClassBasedViewActivity.this, error);
            }
        });
    }

    protected void updateListStudentAdapter(ArrayList<ClassStudent> classStudentArrayList) {
        this.classStudentArrayList.addAll(classStudentArrayList);
//        if (classStudentListAdapter == null) {
        classStudentListAdapter = new ClassStudentListAdapter(this, this.classStudentArrayList);
        loadMoreListView.setAdapter(classStudentListAdapter);
//        } else {
        classStudentListAdapter.notifyDataSetChanged();
//        }
    }

    protected void updateListTeacherAdapter(ArrayList<TeacherView> teacherViewArrayList) {
        this.teacherViewArrayList.addAll(teacherViewArrayList);
//        if (teacherViewListAdapter == null) {
        teacherViewListAdapter = new TeacherViewListAdapter(this, this.teacherViewArrayList);
        loadMoreListView.setAdapter(teacherViewListAdapter);
//        } else {
        teacherViewListAdapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }
}
