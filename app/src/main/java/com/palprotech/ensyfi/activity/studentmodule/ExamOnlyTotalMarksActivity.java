package com.palprotech.ensyfi.activity.studentmodule;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.studentmodule.ExamOnlyTotalMarkViewListAdapter;
import com.palprotech.ensyfi.bean.student.viewlist.ExamMark;
import com.palprotech.ensyfi.bean.student.viewlist.ExamMarkList;
import com.palprotech.ensyfi.bean.student.viewlist.Exams;
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

/**
 * Created by Admin on 25-05-2017.
 */

public class ExamOnlyTotalMarksActivity extends AppCompatActivity implements IServiceListener, AdapterView.OnItemClickListener, DialogClickListener {

    private static final String TAG = "ExamMarksActivity";
    ListView loadMoreListView;
    ExamOnlyTotalMarkViewListAdapter examOnlyTotalMarkViewListAdapter;
    ServiceHelper serviceHelper;
    ArrayList<ExamMark> examMarkArrayList;
    int totalCount = 0;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();
    private Exams exams;
    String ExamId, IsInternalExternal;
    TextView txtTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_only_total_marks);
        loadMoreListView = (ListView) findViewById(R.id.listView_events);
        loadMoreListView.setOnItemClickListener(this);
        examMarkArrayList = new ArrayList<>();
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        exams = (Exams) getIntent().getSerializableExtra("eventObj");

        callGetExamDetailViewService();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callGetExamDetailViewService() {

        ExamId = exams.getExamId();
        IsInternalExternal = exams.getIsInternalExternal();

        if (examMarkArrayList != null)
            examMarkArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            new HttpAsyncTask().execute("");
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
        if (validateSignInResponse(response)) {
            Log.d("ajazFilterresponse : ", response.toString());
            try {
                String totalMark = response.getString("total_points");
                txtTotal.setText(totalMark);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialogHelper.hideProgressDialog();

                    Gson gson = new Gson();
                    ExamMarkList examMarkList = gson.fromJson(response.toString(), ExamMarkList.class);
                    if (examMarkList.getExamMarkView() != null && examMarkList.getExamMarkView().size() > 0) {
                        totalCount = examMarkList.getCount();
                        isLoadingForFirstTime = false;
                        updateListAdapter(examMarkList.getExamMarkView());
                    }
                }
            });
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    protected void updateListAdapter(ArrayList<ExamMark> examDetailsViewArrayList) {
        this.examMarkArrayList.addAll(examDetailsViewArrayList);
        if (examOnlyTotalMarkViewListAdapter == null) {
            examOnlyTotalMarkViewListAdapter = new ExamOnlyTotalMarkViewListAdapter(this, this.examMarkArrayList);
            loadMoreListView.setAdapter(examOnlyTotalMarkViewListAdapter);
        } else {
            examOnlyTotalMarkViewListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
                AlertDialogHelper.showSimpleAlertDialog(ExamOnlyTotalMarksActivity.this, error);
            }
        });
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAM_EXAM_ID, ExamId);
                jsonObject.put(EnsyfiConstants.PARAM_STUDENT_ID, PreferenceStorage.getStudentRegisteredIdPreference(getApplicationContext()));
                jsonObject.put(EnsyfiConstants.PARAM_IS_INTERNAL_EXTERNAL, IsInternalExternal);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_EXAM_MARK_API;
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
