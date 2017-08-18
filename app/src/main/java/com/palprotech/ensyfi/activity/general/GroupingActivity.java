package com.palprotech.ensyfi.activity.general;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.general.GroupListAdapter;
import com.palprotech.ensyfi.bean.general.viewlist.Group;
import com.palprotech.ensyfi.bean.general.viewlist.GroupList;
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
 * Created by Narendar on 18/04/17.
 */

public class GroupingActivity extends AppCompatActivity implements IServiceListener, AdapterView.OnItemClickListener, DialogClickListener, View.OnClickListener {

    private static final String TAG = "GroupingActivity";
    ListView loadMoreListView;
    View view;
    GroupListAdapter groupListAdapter;
    private ServiceHelper serviceHelper;
    ArrayList<GroupList> groupListArrayList;
    int totalCount = 0;
    ImageView CreateNotification;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_notification);
        loadMoreListView = (ListView) findViewById(R.id.listView_groups);
        loadMoreListView.setOnItemClickListener(this);
        groupListArrayList = new ArrayList<>();
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);

        CreateNotification = (ImageView) findViewById(R.id.create_notification);
        CreateNotification.setOnClickListener(this);
        CreateNotification.setVisibility(View.INVISIBLE);

        String userTypeString = PreferenceStorage.getUserType(getApplicationContext());
        int userType = Integer.parseInt(userTypeString);
        if (userType == 2) {
            CreateNotification.setVisibility(View.VISIBLE);
        }

        callGetGroupService();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void callGetGroupService() {

        if (groupListArrayList != null)
            groupListArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            new HttpAsyncTask().execute("");
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onGroup list item clicked" + position);
        GroupList groupList = null;
        if ((groupListAdapter != null) && (groupListAdapter.ismSearching())) {
            Log.d(TAG, "while searching");
            int actualindex = groupListAdapter.getActualGroupPos(position);
            Log.d(TAG, "actual index" + actualindex);
//            groupList = groupListAdapter.get(actualindex);
        } else {
//            groupList = groupListAdapter.get(position);
        }
//        Intent intent = new Intent(this, GroupingDetailActivity.class);
//        intent.putExtra("eventObj", groupList);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        startActivity(intent);
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
                    Group group = gson.fromJson(response.toString(), Group.class);

                    if (group.getGroups() != null && group.getGroups().size() > 0) {
                        totalCount = group.getCount();
                        isLoadingForFirstTime = false;
                        updateListAdapter(group.getGroups());
                    }
                }
            });
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    protected void updateListAdapter(ArrayList<GroupList> groupListArrayList) {
        this.groupListArrayList.addAll(groupListArrayList);
        if (groupListAdapter == null) {
            groupListAdapter = new GroupListAdapter(this, this.groupListArrayList);
            loadMoreListView.setAdapter(groupListAdapter);
        } else {
            groupListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
                AlertDialogHelper.showSimpleAlertDialog(GroupingActivity.this, error);
            }
        });
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    @Override
    public void onClick(View v) {
        if (v == CreateNotification) {
            Intent intent = new Intent(getApplicationContext(), GroupingSendActivity.class);
            startActivity(intent);
        }
    }


    private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_USER_TYPE, PreferenceStorage.getUserType(getApplicationContext()));
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_USER_ID, PreferenceStorage.getUserId(getApplicationContext()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_GROUP_MESSAGE_VIEW;
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
