package com.palprotech.ensyfi.activity.general;

import android.content.Intent;
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

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.general.OnDutyListAdapter;
import com.palprotech.ensyfi.bean.general.viewlist.OnDuty;
import com.palprotech.ensyfi.bean.general.viewlist.OnDutyList;
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
 * Created by Admin on 10-07-2017.
 */

public class OnDutyActivity extends AppCompatActivity implements View.OnClickListener, IServiceListener, AdapterView.OnItemClickListener, DialogClickListener {

    private ImageView btnBack, btnReqOnDuty;
    private static final String TAG = "OnDutyActivity";
    ListView loadMoreListView;
    View view;
    OnDutyListAdapter onDutyListAdapter;
    ServiceHelper serviceHelper;
    ArrayList<OnDuty> onDutyArrayList;
    int totalCount = 0;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_duty);

        btnBack = (ImageView) findViewById(R.id.back_res);
        btnBack.setOnClickListener(this);

        btnReqOnDuty = (ImageView) findViewById(R.id.reqOD);
        btnReqOnDuty.setOnClickListener(this);

        loadMoreListView = (ListView) findViewById(R.id.listView_events);

        loadMoreListView.setOnItemClickListener(this);
        onDutyArrayList = new ArrayList<>();

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);

        progressDialogHelper = new ProgressDialogHelper(this);

        callOnDutyViewService();

        String userTypeString = PreferenceStorage.getUserType(getApplicationContext());
        int userType = Integer.parseInt(userTypeString);
        if (userType == 1) {
            btnReqOnDuty.setVisibility(View.GONE);
        } else if (userType == 2) {
            btnReqOnDuty.setVisibility(View.VISIBLE);
        } else if (userType == 3) {
            btnReqOnDuty.setVisibility(View.GONE);
        } else {
            btnReqOnDuty.setVisibility(View.GONE);
        }
    }

    private void callOnDutyViewService() {

        if (onDutyArrayList != null)
            onDutyArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            new HttpAsyncTask().execute("");
        } else {
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
                String userTypeString = PreferenceStorage.getUserType(getApplicationContext());
                int userType = Integer.parseInt(userTypeString);
                if (userType == 1) {
                    String okNew = "";
                } else if (userType == 2) {
                    jsonObject.put(EnsyfiConstants.PARAMS_FP_USER_ID, PreferenceStorage.getUserId(getApplicationContext()));
                    jsonObject.put(EnsyfiConstants.KEY_USER_TYPE, PreferenceStorage.getUserType(getApplicationContext()));
                } else if (userType == 3) {
                    jsonObject.put(EnsyfiConstants.PARAMS_FP_USER_ID, PreferenceStorage.getUserId(getApplicationContext()));
                    jsonObject.put(EnsyfiConstants.KEY_USER_TYPE, PreferenceStorage.getUserType(getApplicationContext()));
                } else {
                    jsonObject.put(EnsyfiConstants.PARAMS_FP_USER_ID, PreferenceStorage.getStudentAdmissionIdPreference(getApplicationContext()));
                    jsonObject.put(EnsyfiConstants.KEY_USER_TYPE, PreferenceStorage.getUserType(getApplicationContext()));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_ON_DUTY_VIEW;
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
        if (v == btnReqOnDuty) {
            Intent intent = new Intent(getApplicationContext(), OnDutyRequestActivity.class);
            startActivity(intent);
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

                    Gson gson = new Gson();
                    OnDutyList onDutyList = gson.fromJson(response.toString(), OnDutyList.class);
                    if (onDutyList.getOnDuty() != null && onDutyList.getOnDuty().size() > 0) {
                        totalCount = onDutyList.getCount();
                        isLoadingForFirstTime = false;
                        updateListAdapter(onDutyList.getOnDuty());
                    }
                }
            });
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    protected void updateListAdapter(ArrayList<OnDuty> onDutyArrayList) {
        this.onDutyArrayList.addAll(onDutyArrayList);
        if (onDutyListAdapter == null) {
            onDutyListAdapter = new OnDutyListAdapter(this, this.onDutyArrayList);
            loadMoreListView.setAdapter(onDutyListAdapter);
        } else {
            onDutyListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
                AlertDialogHelper.showSimpleAlertDialog(OnDutyActivity.this, error);
            }
        });
    }

}
