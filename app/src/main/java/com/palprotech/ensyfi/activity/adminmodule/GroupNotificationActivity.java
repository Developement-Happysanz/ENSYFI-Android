package com.palprotech.ensyfi.activity.adminmodule;

import android.app.ProgressDialog;
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
import com.palprotech.ensyfi.activity.general.GroupingActivity;
import com.palprotech.ensyfi.activity.general.GroupingSendActivity;
import com.palprotech.ensyfi.adapter.adminmodule.GroupHistoryAdapter;
import com.palprotech.ensyfi.adapter.general.GroupListAdapter;
import com.palprotech.ensyfi.bean.admin.viewlist.GroupHistory;
import com.palprotech.ensyfi.bean.admin.viewlist.GroupHistoryList;
import com.palprotech.ensyfi.bean.admin.viewlist.Groups;
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

public class GroupNotificationActivity extends AppCompatActivity implements IServiceListener, AdapterView.OnItemClickListener, DialogClickListener, View.OnClickListener {

    private static final String TAG = "GroupNotification";
    ListView loadMoreListView;
    View view;
    GroupHistoryAdapter groupListAdapter;
    private ServiceHelper serviceHelper;
    ArrayList<GroupHistory> groupListArrayList;
    int totalCount = 0;
    private Groups groups;
    ImageView CreateNotification;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_notifications_sent_view);
        groups = (Groups) getIntent().getSerializableExtra("groupsObj");

        loadMoreListView = (ListView) findViewById(R.id.listView_groups);
        loadMoreListView.setOnItemClickListener(this);
        groupListArrayList = new ArrayList<>();
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);

        CreateNotification = (ImageView) findViewById(R.id.sendNewNotification);
        CreateNotification.setOnClickListener(this);
        CreateNotification.setVisibility(View.INVISIBLE);

        String userTypeString = PreferenceStorage.getUserType(getApplicationContext());
        int userType = Integer.parseInt(userTypeString);
        if (userType == 1 || userType == 2) {
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
                    GroupHistoryList group = gson.fromJson(response.toString(), GroupHistoryList.class);

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

    protected void updateListAdapter(ArrayList<GroupHistory> groupListArrayList) {
        this.groupListArrayList.addAll(groupListArrayList);
//        if (groupListAdapter == null) {
        groupListAdapter = new GroupHistoryAdapter(this, this.groupListArrayList);
        loadMoreListView.setAdapter(groupListAdapter);
//        } else {
//            groupListAdapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
                AlertDialogHelper.showSimpleAlertDialog(GroupNotificationActivity.this, error);
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

            startGroupingSendActivity(0);

//            Intent intent = new Intent(getApplicationContext(), GroupingSendActivity.class);
//            startActivity(intent);
        }
    }

    public void startGroupingSendActivity(long id) {
        Intent intent = new Intent(getApplicationContext(), GroupingSendActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (groupListArrayList != null) {
                groupListArrayList.clear();
                groupListAdapter = new GroupHistoryAdapter(getApplicationContext(), this.groupListArrayList);
                loadMoreListView.setAdapter(groupListAdapter);
                isLoadingForFirstTime = true;
                callGetGroupService();
            }
        }

    }


    private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_ID, groups.getId());
                } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.VIEW_GROUP_MESSAGE_HISTORY;
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