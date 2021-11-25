package com.palprotech.ensyfi.activity.teachermodule;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.ClassTeacherCtHwOverallListAdapter;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherCtHwOverall;
import com.palprotech.ensyfi.bean.teacher.viewlist.ClassTeacherCtHwOverallList;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassTeacherCtHwOverallView extends AppCompatActivity implements IServiceListener, DialogClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "ClassTeacherView";

    ServiceHelper serviceHelper;
    ProgressDialogHelper progressDialogHelper;
    ListView attendaceList;
    ClassTeacherCtHwOverallListAdapter classTeacherCtHwOverallListAdapter;
    ArrayList<ClassTeacherCtHwOverall> ctHwArrayList;
    protected boolean isLoadingForFirstTime = true;
    int totalCount = 0;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_teacher_ct_hw_overall_view);
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);

        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SetUI();
    }
    private void SetUI() {

        attendaceList = (ListView) findViewById(R.id.listView_overall_work);
        attendaceList.setOnItemClickListener(this);
        ctHwArrayList = new ArrayList<>();

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);

        progressDialogHelper = new ProgressDialogHelper(this);
        callGetEventService();
    }

    public void callGetEventService() {

        if (ctHwArrayList != null)
            ctHwArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            new HttpAsyncTask().execute("");
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onEvent list item clicked" + position);
        ClassTeacherCtHwOverall classTeacherCtHwOverall = null;
        if ((classTeacherCtHwOverallListAdapter != null) && (classTeacherCtHwOverallListAdapter.ismSearching())) {
            Log.d(TAG, "while searching");
            int actualindex = classTeacherCtHwOverallListAdapter.getActualEventPos(position);
            Log.d(TAG, "actual index" + actualindex);
            classTeacherCtHwOverall = ctHwArrayList.get(actualindex);
        } else {
            classTeacherCtHwOverall = ctHwArrayList.get(position);
        }
        Intent intent = new Intent(this, ClassTeacherCtHwDaywiseView.class);
        intent.putExtra("attendanceObj", classTeacherCtHwOverall);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAM_CLASS_ID, PreferenceStorage.getClassTeacher(getApplicationContext()));
                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(getApplicationContext()));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.GET_CLASS_TEACHER_CT_HW_OVERVIEW;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void result) {
            progressDialogHelper.cancelProgressDialog();
        }
    }

    @Override
    public void onResponse(final JSONObject response) {
        if (validateSignInResponse(response)) {
            Log.d("ajazFilterresponse : ", response.toString());

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialogHelper.hideProgressDialog();

                    Gson gson = new Gson();
                    ClassTeacherCtHwOverallList classTeacherCtHwOverallList = gson.fromJson(response.toString(), ClassTeacherCtHwOverallList.class);
                    if (classTeacherCtHwOverallList.getClassTeacherCtHwOverallDetails() != null && classTeacherCtHwOverallList.getClassTeacherCtHwOverallDetails().size() > 0) {
                        totalCount = classTeacherCtHwOverallList.getCount();
                        isLoadingForFirstTime = false;
                        updateListAdapter(classTeacherCtHwOverallList.getClassTeacherCtHwOverallDetails());
                    }
                }
            });
        } else {
            Log.d(TAG, "Error while sign In");
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

    protected void updateListAdapter(ArrayList<ClassTeacherCtHwOverall> ctHwArrayList) {
        this.ctHwArrayList.addAll(ctHwArrayList);
        if (classTeacherCtHwOverallListAdapter == null) {
            classTeacherCtHwOverallListAdapter = new ClassTeacherCtHwOverallListAdapter(this, this.ctHwArrayList);
            attendaceList.setAdapter(classTeacherCtHwOverallListAdapter);
        } else {
            classTeacherCtHwOverallListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

}
