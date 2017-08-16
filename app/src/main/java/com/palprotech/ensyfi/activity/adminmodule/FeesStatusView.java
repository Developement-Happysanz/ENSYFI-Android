package com.palprotech.ensyfi.activity.adminmodule;

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
import com.palprotech.ensyfi.adapter.adminmodule.FeesStatusListAdapter;
import com.palprotech.ensyfi.bean.admin.viewlist.Fees;
import com.palprotech.ensyfi.bean.admin.viewlist.FeesStatus;
import com.palprotech.ensyfi.bean.admin.viewlist.FeesStatusList;
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
 * Created by Admin on 19-07-2017.
 */

public class FeesStatusView extends AppCompatActivity implements IServiceListener, DialogClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = FeesStatusView.class.getName();

    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    private String checkSpinner = "", storeClassId, storeSectionId;
    ListView loadMoreListView;
    FeesStatusListAdapter feesStatusListAdapter;
    ArrayList<FeesStatus> feesStatusArrayList;
    int pageNumber = 0, totalCount = 0;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();
    private Fees fees;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fees_status_view);

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        loadMoreListView = (ListView) findViewById(R.id.listView_events);
        loadMoreListView.setOnItemClickListener(this);
        feesStatusArrayList = new ArrayList<>();
        fees = (Fees) getIntent().getSerializableExtra("eventObj");
        Bundle extras = getIntent().getExtras();
        storeClassId = extras.getString("storeClassId");
        storeSectionId = extras.getString("storeSectionId");

        GetFeesStatusData();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetFeesStatusData() {

        checkSpinner = "feesStatus";
        if (feesStatusArrayList != null)
            feesStatusArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID_NEW, storeClassId);
                jsonObject.put(EnsyfiConstants.PARAMS_SECTION_ID, storeSectionId);
                jsonObject.put(EnsyfiConstants.PARAMS_FEES_ID_SHOW, fees.getFeesId());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_VIEW_FEES_STATUS;
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
                JSONArray getData = response.getJSONArray("data");
//                JSONObject userData = getData.getJSONObject(0);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressDialogHelper.hideProgressDialog();
                        Gson gson = new Gson();
                        FeesStatusList feesStatusList = gson.fromJson(response.toString(), FeesStatusList.class);
                        if (feesStatusList.getFeesStatus() != null && feesStatusList.getFeesStatus().size() > 0) {
                            totalCount = feesStatusList.getCount();
                            isLoadingForFirstTime = false;
                            updateListAdapter(feesStatusList.getFeesStatus());
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
                AlertDialogHelper.showSimpleAlertDialog(FeesStatusView.this, error);
            }
        });
    }

    protected void updateListAdapter(ArrayList<FeesStatus> feesStatusArrayList) {
        this.feesStatusArrayList.addAll(feesStatusArrayList);
        if (feesStatusListAdapter == null) {
            feesStatusListAdapter = new FeesStatusListAdapter(this, this.feesStatusArrayList);
            loadMoreListView.setAdapter(feesStatusListAdapter);
        } else {
            feesStatusListAdapter.notifyDataSetChanged();
        }
    }
}
