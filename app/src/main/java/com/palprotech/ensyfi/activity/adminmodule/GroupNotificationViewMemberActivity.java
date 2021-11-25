package com.palprotech.ensyfi.activity.adminmodule;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.adminmodule.GroupMemberListViewAdapter;
import com.palprotech.ensyfi.bean.admin.support.StoreTeacherId;
import com.palprotech.ensyfi.bean.admin.viewlist.GroupMembersView;
import com.palprotech.ensyfi.bean.admin.viewlist.GroupMembersViewList;
import com.palprotech.ensyfi.bean.admin.viewlist.Groups;
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

public class GroupNotificationViewMemberActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, View.OnClickListener {

    private static final String TAG = GroupNotificationUpdateActivity.class.getName();
    private Groups groups;
    protected ProgressDialogHelper progressDialogHelper;
    private ProgressDialog mProgressDialog = null;
    private ServiceHelper serviceHelper;
    GroupMemberListViewAdapter groupMemberListViewAdapter;
    ListView loadMoreListView;
    ArrayList<GroupMembersView> groupMembersArrayList;
    String groupStatus = "";
    String groupRes = "";
    String groupLead = "";
    String storeGroupId;
    TextView mamberName, membertype;
    Handler mHandler = new Handler();
    protected boolean isLoadingForFirstTime = true;
    Boolean update = false;
    int totalCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_notification_view_members);
        groups = (Groups) getIntent().getSerializableExtra("groupsObj");
        initializeEventHelpers();
        GetGroupData();
        initializeViews();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);

        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void initializeEventHelpers() {
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
    }

    private void initializeViews() {
        loadMoreListView = (ListView) findViewById(R.id.listView_members);
        groupMembersArrayList = new ArrayList<>();
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    private void GetGroupData() {

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_ID, groups.getId());
                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.VIEW_GROUP_MEMBERS;
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
    public void onResponse(final JSONObject response) {

        progressDialogHelper.hideProgressDialog();

        if (validateSignInResponse(response)) {

            try {
                JSONArray getData = response.getJSONArray("memberList");
                if (getData != null && getData.length() > 0) {

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialogHelper.hideProgressDialog();
                            Gson gson = new Gson();
                            GroupMembersViewList groupMembersViewList = gson.fromJson(response.toString(), GroupMembersViewList.class);
                            if (groupMembersViewList.getGroups() != null && groupMembersViewList.getGroups().size() > 0) {
                                totalCount = groupMembersViewList.getCount();
                                isLoadingForFirstTime = false;
                                updateListAdapter(groupMembersViewList.getGroups());
                            }
                        }
                    });
                } else {
                    if (groupMembersArrayList != null) {
                        groupMembersArrayList.clear();
                        groupMemberListViewAdapter = new GroupMemberListViewAdapter(this, this.groupMembersArrayList);
                        loadMoreListView.setAdapter(groupMemberListViewAdapter);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "Error while sign In");
        }

    }

    protected void updateListAdapter(ArrayList<GroupMembersView> groupMembersArrayList) {
        this.groupMembersArrayList.addAll(groupMembersArrayList);
        if (groupMemberListViewAdapter == null) {
            groupMemberListViewAdapter = new GroupMemberListViewAdapter(this, this.groupMembersArrayList);
            loadMoreListView.setAdapter(groupMemberListViewAdapter);
        } else {
            groupMemberListViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onClick(View v) {

    }
}