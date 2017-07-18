package com.palprotech.ensyfi.activity.studentmodule;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.studentmodule.ExamDetailViewListAdapter;
import com.palprotech.ensyfi.bean.student.viewlist.ExamDetailsView;
import com.palprotech.ensyfi.bean.student.viewlist.ExamDetailsViewList;
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
 * Created by Admin on 18-05-2017.
 */

public class ExamDetailActivity extends AppCompatActivity implements IServiceListener, AdapterView.OnItemClickListener, DialogClickListener {

    private static final String TAG = "ExamDetailActivity";
    ListView loadMoreListView;
    View view;
    ExamDetailViewListAdapter examDetailViewListAdapter;
    ServiceHelper serviceHelper;
    ArrayList<ExamDetailsView> examDetailsViewArrayList;
    int pageNumber = 0, totalCount = 0;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();
    private SearchView mSearchView = null;
    private Exams exams;
    String ExamId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_detail);
        loadMoreListView = (ListView) findViewById(R.id.listView_events);
//        loadMoreListView.setOnLoadMoreListener(this);
        loadMoreListView.setOnItemClickListener(this);
        examDetailsViewArrayList = new ArrayList<>();
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
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

        /*if(eventsListAdapter != null){
            eventsListAdapter.clearSearchFlag();
        }*/
        if (examDetailsViewArrayList != null)
            examDetailsViewArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            //    eventServiceHelper.makeRawRequest(FindAFunConstants.GET_ADVANCE_SINGLE_SEARCH);
            new HttpAsyncTask().execute("");
        } else {
//            AlertDialogHelper.showSimpleAlertDialog(this, getString(R.string.no_connectivity));
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAM_CLASS_ID, exams.getCassMasterId());
                jsonObject.put(EnsyfiConstants.PARAM_EXAM_ID, ExamId);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_EXAM_DETAIL_API;
            serviceHelper.makeGetServiceCall(jsonObject.toString(),url);

            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void result) {
            progressDialogHelper.cancelProgressDialog();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
//                loadMoreListView.onLoadMoreComplete();

                Gson gson = new Gson();
                ExamDetailsViewList examDetailsViewList = gson.fromJson(response.toString(), ExamDetailsViewList.class);
                if (examDetailsViewList.getExamDetailView() != null && examDetailsViewList.getExamDetailView().size() > 0) {
                    totalCount = examDetailsViewList.getCount();
                    isLoadingForFirstTime = false;
                    updateListAdapter(examDetailsViewList.getExamDetailView());
                }
            }
        });
    }
        else {
        Log.d(TAG, "Error while sign In");
    }
    }

    protected void updateListAdapter(ArrayList<ExamDetailsView> examDetailsViewArrayList) {
        this.examDetailsViewArrayList.addAll(examDetailsViewArrayList);
        if (examDetailViewListAdapter == null) {
            examDetailViewListAdapter = new ExamDetailViewListAdapter(this, this.examDetailsViewArrayList);
            loadMoreListView.setAdapter(examDetailViewListAdapter);
        } else {
            examDetailViewListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
//                loadMoreListView.onLoadMoreComplete();
                AlertDialogHelper.showSimpleAlertDialog(ExamDetailActivity.this, error);
            }
        });
    }
}


