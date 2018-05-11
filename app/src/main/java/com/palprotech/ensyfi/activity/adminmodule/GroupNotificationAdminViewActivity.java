package com.palprotech.ensyfi.activity.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.adminmodule.ClassStudentListAdapter;
import com.palprotech.ensyfi.adapter.adminmodule.GroupsListAdapter;
import com.palprotech.ensyfi.bean.admin.viewlist.ClassStudent;
import com.palprotech.ensyfi.bean.admin.viewlist.Groups;
import com.palprotech.ensyfi.bean.admin.viewlist.GroupsList;
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

public class GroupNotificationAdminViewActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = StudentsViewActivity.class.getName();
    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    ListView loadMoreListView;
    GroupsListAdapter groupsListAdapter;
    ArrayList<Groups> groupsArrayList;
    Handler mHandler = new Handler();
    int pageNumber = 0, totalCount = 0;
    ImageView create;
    protected boolean isLoadingForFirstTime = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_notification_admin_view);
        create = findViewById(R.id.createNewGroup);
        create.setOnClickListener(this);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        loadMoreListView = (ListView) findViewById(R.id.listView_groups);
        loadMoreListView.setOnItemClickListener(this);
        groupsArrayList = new ArrayList<>();

        GetGroupsData();

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void GetGroupsData() {

        if (groupsArrayList != null)
            groupsArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_USER_ID, PreferenceStorage.getUserId(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_ADMIN_GROUP_LIST;
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
        Log.d(TAG, "onOD list item clicked" + position);
        Groups groups = null;
        if ((groupsListAdapter != null) && (groupsListAdapter.ismSearching())) {
            Log.d(TAG, "while searching");
            int actualindex = groupsListAdapter.getActualEventPos(position);
            Log.d(TAG, "actual index" + actualindex);
            groups = groupsArrayList.get(actualindex);
        } else {
            groups = groupsArrayList.get(position);
        }
        Intent intent = new Intent(this, GroupNotificationUpdateActivity.class);
        intent.putExtra("groupsObj", groups);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
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
                JSONArray getData = response.getJSONArray("groupList");
                if (getData != null && getData.length() > 0) {

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialogHelper.hideProgressDialog();
                            Gson gson = new Gson();
                            GroupsList groupstList = gson.fromJson(response.toString(), GroupsList.class);
                            if (groupstList.getGroups() != null && groupstList.getGroups().size() > 0) {
                                totalCount = groupstList.getCount();
                                isLoadingForFirstTime = false;
                                updateListAdapter(groupstList.getGroups());
                            }
                        }
                    });
                } else {
                    if (groupsArrayList != null) {
                        groupsArrayList.clear();
                        groupsListAdapter = new GroupsListAdapter(this, this.groupsArrayList);
                        loadMoreListView.setAdapter(groupsListAdapter);
                    }
                }

        } catch(JSONException e){
            e.printStackTrace();
        }
    } else

    {
        Log.d(TAG, "Error while sign In");
    }
}

    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
                AlertDialogHelper.showSimpleAlertDialog(GroupNotificationAdminViewActivity.this, error);
            }
        });
    }

    protected void updateListAdapter(ArrayList<Groups> groupsArrayList) {
        this.groupsArrayList.addAll(groupsArrayList);
        if (groupsListAdapter == null) {
            groupsListAdapter = new GroupsListAdapter(this, this.groupsArrayList);
            loadMoreListView.setAdapter(groupsListAdapter);
        } else {
            groupsListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == create) {
            finish();
            Intent intent = new Intent(getApplicationContext(), GroupNotificationCreationActivity.class);
            startActivity(intent);
        }
    }
}
