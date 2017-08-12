package com.palprotech.ensyfi.activity.adminmodule;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.studentmodule.ExamDetailActivity;
import com.palprotech.ensyfi.activity.studentmodule.ExamMarksActivity;
import com.palprotech.ensyfi.adapter.studentmodule.ExamListAdapter;
import com.palprotech.ensyfi.bean.admin.support.StoreClass;
import com.palprotech.ensyfi.bean.admin.support.StoreSection;
import com.palprotech.ensyfi.bean.student.viewlist.ExamList;
import com.palprotech.ensyfi.bean.student.viewlist.Exams;
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

/**
 * Created by Admin on 18-07-2017.
 */

public class ExamViewActivity extends AppCompatActivity implements IServiceListener, AdapterView.OnItemClickListener, DialogClickListener {

    private static final String TAG = "ExamViewActivity";
    ListView loadMoreListView;
    View view;
    ExamListAdapter examListAdapter;
    ServiceHelper serviceHelper;
    ArrayList<Exams> examsArrayList;
    int pageNumber = 0, totalCount = 0;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();
    private SearchView mSearchView = null;
    private Spinner spnClassList, spnSectionList;
    private String checkSpinner = "", storeClassId, storeSectionId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_view);
        loadMoreListView = (ListView) findViewById(R.id.listView_events);
//        loadMoreListView.setOnLoadMoreListener(this);
        loadMoreListView.setOnItemClickListener(this);
        spnClassList = (Spinner) findViewById(R.id.class_list_spinner);
        spnSectionList = (Spinner) findViewById(R.id.section_list_spinner);
        examsArrayList = new ArrayList<>();
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);

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
//                Toast.makeText(getApplicationContext(), "Class ID: " + classList.getClassId() + ",  Class Name : " + classList.getClassName(), Toast.LENGTH_SHORT).show();
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
//                Toast.makeText(getApplicationContext(), "Section ID: " + sectionList.getSectionId() + ",  Section Name : " + sectionList.getSectionName(), Toast.LENGTH_SHORT).show();
                storeSectionId = sectionList.getSectionId();
                callGetExamResultService();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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

    public void callGetExamResultService() {
        /*if(eventsListAdapter != null){
            eventsListAdapter.clearSearchFlag();
        }*/
        checkSpinner = "exams";

        if (examsArrayList != null)
            examsArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            //    eventServiceHelper.makeRawRequest(FindAFunConstants.GET_ADVANCE_SINGLE_SEARCH);
            new HttpAsyncTask().execute("");
        } else {
//            AlertDialogHelper.showSimpleAlertDialog(this, getString(R.string.no_connectivity));
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
        Log.d(TAG, "onEvent list item click" + position);
        Exams exams = null;
        if ((examListAdapter != null) && (examListAdapter.ismSearching())) {
            Log.d(TAG, "while searching");
            int actualindex = examListAdapter.getActualEventPos(position);
            Log.d(TAG, "actual index" + actualindex);
            exams = examsArrayList.get(actualindex);
        } else {
            exams = examsArrayList.get(position);
        }

        String isMarkStatus = exams.getMarkStatus();

        if (isMarkStatus.equalsIgnoreCase("1")) {
            Intent intent = new Intent(this, ExamMarksActivity.class);
            intent.putExtra("eventObj", exams);
            // intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ExamDetailActivity.class);
            intent.putExtra("eventObj", exams);
            // intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    @Override
    public void onResponse(final JSONObject response) {
        progressDialogHelper.hideProgressDialog();

        if (validateSignInResponse(response)) {

            try {
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
//                spnClassList.setSelection(adapter.getPosition());//Optional to set the selected item.

                } else if (checkSpinner.equalsIgnoreCase("section")) {
                    JSONArray getData = response.getJSONArray("data");
                    JSONObject userData = getData.getJSONObject(0);
                    int getLength = getData.length();
                    String subjectName = null;
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
//                spnClassList.setSelection(adapter.getPosition());//Optional to set the selected item.
                } else {

                    JSONArray getData = response.getJSONArray("Exams");
                    if (getData != null && getData.length() > 0) {

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressDialogHelper.hideProgressDialog();
//                loadMoreListView.onLoadMoreComplete();

                                Gson gson = new Gson();
                                ExamList examList = gson.fromJson(response.toString(), ExamList.class);
                                if (examList.getExams() != null && examList.getExams().size() > 0) {
                                    totalCount = examList.getCount();
                                    isLoadingForFirstTime = false;
                                    updateListAdapter(examList.getExams());
                                }
                            }
                        });
                    } else {
                        if (examsArrayList != null) {
                            examsArrayList.clear();
                            examListAdapter = new ExamListAdapter(this, this.examsArrayList);
                            loadMoreListView.setAdapter(examListAdapter);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
//                loadMoreListView.onLoadMoreComplete();
                AlertDialogHelper.showSimpleAlertDialog(ExamViewActivity.this, error);
            }
        });
    }

    protected void updateListAdapter(ArrayList<Exams> examsArrayList) {
        this.examsArrayList.addAll(examsArrayList);
        if (examListAdapter == null) {
            examListAdapter = new ExamListAdapter(this, this.examsArrayList);
            loadMoreListView.setAdapter(examListAdapter);
        } else {
            examListAdapter.notifyDataSetChanged();
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID_NEW, storeClassId);
                jsonObject.put(EnsyfiConstants.PARAMS_SECTION_ID, storeSectionId);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_VIEW_EXAMS;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void result) {
            progressDialogHelper.cancelProgressDialog();
        }
    }
}
