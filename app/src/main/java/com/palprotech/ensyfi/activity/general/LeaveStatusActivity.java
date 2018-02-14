package com.palprotech.ensyfi.activity.general;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.general.LeaveStatusListAdapter;
import com.palprotech.ensyfi.bean.general.viewlist.LeaveStatus;
import com.palprotech.ensyfi.bean.general.viewlist.LeaveStatusList;
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
 * Created by Admin on 15-07-2017.
 */

public class LeaveStatusActivity extends AppCompatActivity implements View.OnClickListener, IServiceListener, DialogClickListener {

    private ImageView btnBack, btnReqLeave;
    private static final String TAG = "LeaveStatusActivity";
    ListView loadMoreListView;
    View view;

    LeaveStatusListAdapter leaveStatusListAdapter;
    ServiceHelper serviceHelper;
    ArrayList<LeaveStatus> leaveStatusArrayList;
    int totalCount = 0;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_application_status);

        btnBack = (ImageView) findViewById(R.id.back_res);
        btnBack.setOnClickListener(this);

        btnReqLeave = (ImageView) findViewById(R.id.reqLeave);
        btnReqLeave.setOnClickListener(this);

        loadMoreListView = (ListView) findViewById(R.id.listView_events);

        leaveStatusArrayList = new ArrayList<>();

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);

        progressDialogHelper = new ProgressDialogHelper(this);

        String userTypeString = PreferenceStorage.getUserType(getApplicationContext());
        int userType = Integer.parseInt(userTypeString);
        if (userType == 1) {
            btnReqLeave.setVisibility(View.GONE);
        } else {
            btnReqLeave.setVisibility(View.VISIBLE);
        }
        callLeaveStatusViewService();
    }

    private void callLeaveStatusViewService() {
        if (leaveStatusArrayList != null)
            leaveStatusArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            new HttpAsyncTask().execute("");
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_FP_USER_ID, PreferenceStorage.getUserId(getApplicationContext()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = "";
            String userTypeString = PreferenceStorage.getUserType(getApplicationContext());
            int userType = Integer.parseInt(userTypeString);
            if (userType == 1) {
                url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_USER_LEAVES_STATUS_ADMIN_API;
            } else {
                url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_USER_LEAVES_API;
            }

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
    public void onClick(View v) {
        if (v == btnBack) {
            finish();
        }
        if (v == btnReqLeave) {

            startLeaveApplyActivity(0);

            /*Intent intent = new Intent(getApplicationContext(), LeaveApplyActivity.class);
            startActivity(intent);*/
        }
    }

    public void startLeaveApplyActivity(long id) {
        Intent intent = new Intent(getApplicationContext(), LeaveApplyActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (leaveStatusArrayList != null) {
                leaveStatusArrayList.clear();
                leaveStatusListAdapter = new LeaveStatusListAdapter(getApplicationContext(), this.leaveStatusArrayList);
                loadMoreListView.setAdapter(leaveStatusListAdapter);
                isLoadingForFirstTime = true;
                callLeaveStatusViewService();
            }
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
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

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
                    LeaveStatusList leaveStatusList = gson.fromJson(response.toString(), LeaveStatusList.class);
                    if (leaveStatusList.getLeaveStatus() != null && leaveStatusList.getLeaveStatus().size() > 0) {
                        totalCount = leaveStatusList.getCount();
                        isLoadingForFirstTime = false;
                        updateListAdapter(leaveStatusList.getLeaveStatus());
                    }
                }
            });
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    protected void updateListAdapter(ArrayList<LeaveStatus> leaveStatusArrayList) {
        this.leaveStatusArrayList.addAll(leaveStatusArrayList);
//        if (leaveStatusListAdapter == null) {
            leaveStatusListAdapter = new LeaveStatusListAdapter(this, this.leaveStatusArrayList);
            loadMoreListView.setAdapter(leaveStatusListAdapter);
//        } else {
//            leaveStatusListAdapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
                AlertDialogHelper.showSimpleAlertDialog(LeaveStatusActivity.this, error);
            }
        });
    }
}
