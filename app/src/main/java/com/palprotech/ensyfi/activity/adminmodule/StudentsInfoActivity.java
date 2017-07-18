package com.palprotech.ensyfi.activity.adminmodule;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.adminmodule.ClassStudentListAdapter;
import com.palprotech.ensyfi.bean.admin.support.StoreClass;
import com.palprotech.ensyfi.bean.admin.support.StoreSection;
import com.palprotech.ensyfi.bean.admin.viewlist.ClassStudent;
import com.palprotech.ensyfi.bean.admin.viewlist.ClassStudentList;
import com.palprotech.ensyfi.bean.admin.viewlist.ParentStudent;
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

public class StudentsInfoActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = StudentsInfoActivity.class.getName();
    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    private ParentStudent parentStudent;
    ListView loadMoreListView;
    ClassStudentListAdapter classStudentListAdapter;
    ArrayList<ClassStudent> classStudentArrayList;
    int pageNumber = 0, totalCount = 0;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();
    private SearchView mSearchView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        parentStudent = (ParentStudent) getIntent().getSerializableExtra("eventObj");
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        ;
        loadMoreListView = (ListView) findViewById(R.id.listView_events);
        loadMoreListView.setOnItemClickListener(this);
        classStudentArrayList = new ArrayList<>();

        GetStudentData();

    }

    private void GetStudentData() {

        if (classStudentArrayList != null)
            classStudentArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_PARENT_ID_SHOW, parentStudent.getParentId());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_VIEW_STUDENT_INFO;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);


        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
        // intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
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
    public void onResponse(final JSONObject response) {
        progressDialogHelper.hideProgressDialog();

        if (validateSignInResponse(response)) {

            try {
                JSONArray getData = response.getJSONArray("data");
                JSONObject userData = getData.getJSONObject(0);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialogHelper.hideProgressDialog();
//                loadMoreListView.onLoadMoreComplete();

                        Gson gson = new Gson();
                        ClassStudentList classStudentList = gson.fromJson(response.toString(), ClassStudentList.class);
                        if (classStudentList.getClassStudent() != null && classStudentList.getClassStudent().size() > 0) {
                            totalCount = classStudentList.getCount();
                            isLoadingForFirstTime = false;
                            updateListAdapter(classStudentList.getClassStudent());
                        }
                    }
                });

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
                AlertDialogHelper.showSimpleAlertDialog(StudentsInfoActivity.this, error);
            }
        });
    }

    protected void updateListAdapter(ArrayList<ClassStudent> classStudentArrayList) {
        this.classStudentArrayList.addAll(classStudentArrayList);
        if (classStudentListAdapter == null) {
            classStudentListAdapter = new ClassStudentListAdapter(this, this.classStudentArrayList);
            loadMoreListView.setAdapter(classStudentListAdapter);
        } else {
            classStudentListAdapter.notifyDataSetChanged();
        }
    }
}
