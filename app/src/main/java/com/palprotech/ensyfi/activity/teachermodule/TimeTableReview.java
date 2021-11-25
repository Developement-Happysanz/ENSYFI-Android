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
import com.palprotech.ensyfi.adapter.teachermodule.TTReviewListAdapter;
import com.palprotech.ensyfi.bean.teacher.viewlist.TTReview;
import com.palprotech.ensyfi.bean.teacher.viewlist.TTReviewList;
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
 * Created by Admin on 20-07-2017.
 */

public class TimeTableReview extends AppCompatActivity implements IServiceListener, DialogClickListener, AdapterView.OnItemClickListener {

    private ImageView btnBack, btnReqOnDuty;
    private static final String TAG = "OnDutyActivity";
    ListView loadMoreListView;
    View view;

    TTReviewListAdapter tTReviewListAdapter;
    ServiceHelper serviceHelper;
    ArrayList<TTReview> tTReviewArrayList;
    int totalCount = 0;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_table_review);

        loadMoreListView = (ListView) findViewById(R.id.listView_events);

        loadMoreListView.setOnItemClickListener(this);
        tTReviewArrayList = new ArrayList<>();

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);

        progressDialogHelper = new ProgressDialogHelper(this);

        callTTReviewService();

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void callTTReviewService() {

        if (tTReviewArrayList != null)
            tTReviewArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CTHW_TEACHER_ID, PreferenceStorage.getTeacherId(getApplicationContext()));
                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.GET_ON_TIME_TABLE_REVIEW_;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onEvent list item clicked" + position);
        TTReview ttReview = null;
        if ((tTReviewListAdapter != null) && (tTReviewListAdapter.ismSearching())) {
            Log.d(TAG, "while searching");
            int actualindex = tTReviewListAdapter.getActualEventPos(position);
            Log.d(TAG, "actual index" + actualindex);
            ttReview = tTReviewArrayList.get(actualindex);
        } else {
            ttReview = tTReviewArrayList.get(position);
        }
        Intent intent = new Intent(this, TimeTableReviewDetails.class);
        intent.putExtra("eventObj", ttReview);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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
        progressDialogHelper.cancelProgressDialog();
        if (validateSignInResponse(response)) {
            Log.d("ajazFilterresponse : ", response.toString());

            Gson gson = new Gson();
            TTReviewList tTReviewList = gson.fromJson(response.toString(), TTReviewList.class);
            if (tTReviewList.getTTReview() != null && tTReviewList.getTTReview().size() > 0) {
                totalCount = tTReviewList.getCount();
                isLoadingForFirstTime = false;
                updateListAdapter(tTReviewList.getTTReview());
            }
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    protected void updateListAdapter(ArrayList<TTReview> tTReviewArrayList) {
        this.tTReviewArrayList.addAll(tTReviewArrayList);
        if (tTReviewListAdapter == null) {
            tTReviewListAdapter = new TTReviewListAdapter(this, this.tTReviewArrayList);
            loadMoreListView.setAdapter(tTReviewListAdapter);
        } else {
            tTReviewListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(final String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(TimeTableReview.this, error);
    }
}
