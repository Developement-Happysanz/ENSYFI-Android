package com.palprotech.ensyfi.activity.adminmodule;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    private static final String TAG = GroupNotificationAdminViewActivity.class.getName();
    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
//    ListView loadMoreListView;
    LinearLayout layout_all;
    GroupsListAdapter groupsListAdapter;
    ArrayList<Groups> groupsArrayList;
    Handler mHandler = new Handler();
    int pageNumber = 0, totalCount = 0;
    ImageView create;
    GroupsList groupstList;
    protected boolean isLoadingForFirstTime = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_notification_admin_view);
        create = findViewById(R.id.createNewGroup);
        if (PreferenceStorage.getUserType(this).equalsIgnoreCase("2")) {
            create.setVisibility(View.GONE);
        }
        create.setOnClickListener(this);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        layout_all = findViewById(R.id.layout_member_list);
        layout_all.removeAllViews();
//        loadMoreListView = (ListView) findViewById(R.id.listView_groups);
//        loadMoreListView.setOnItemClickListener(this);
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
        finish();
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
                            groupstList = gson.fromJson(response.toString(), GroupsList.class);
                            if (groupstList.getGroups() != null && groupstList.getGroups().size() > 0) {
                                totalCount = getData.length();
                                isLoadingForFirstTime = false;
//                                updateListAdapter(groupstList.getGroups());
                                loadMembersList(totalCount);
                            }
                        }
                    });
                } else {
                    if (groupsArrayList != null) {
                        groupsArrayList.clear();
                        groupsListAdapter = new GroupsListAdapter(this, this.groupsArrayList);
//                        loadMoreListView.setAdapter(groupsListAdapter);
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
//            loadMoreListView.setAdapter(groupsListAdapter);
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

    private void loadMembersList(int memberCount) {

        try {

            for (int c1 = 0; c1 < memberCount; c1++) {

                RelativeLayout cell = new RelativeLayout(this);
                cell.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                cell.setPadding(0, 0, 0, 0);
                cell.setBackgroundColor(Color.parseColor("#000000"));

//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100);
//                params.setMargins(01, 01, 0, 01);
//
//                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(250, 100);
//                params1.setMargins(01, 01, 01, 01);
//
//                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(140, 100);
//                params2.setMargins(01, 01, 0, 01);


                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
                params.setMargins(1,1,0,1);
                params.addRule(RelativeLayout.LEFT_OF, R.id.lead_name_s);


                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(250, 100);
                params1.setMargins(1, 1, 1, 1);
                params1.addRule(RelativeLayout.LEFT_OF, R.id.status_s);


                RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(140, 100);
                params2.setMargins(0, 1, 1, 1);
                params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

//                TextView title = new TextView(this);
//                title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
//                title.setTextColor(Color.BLACK);
//                title.setText("Attendee Details " + c1);
//                title.setLayoutParams(params2);

                TextView line1 = new TextView(this);
                line1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 2.0f));

                String aa = groupstList.getGroups().get(c1).getGroup_title();
                line1.setText(aa);


                line1.setId(R.id.group_name_s);
                line1.setHint("Member Name");
                line1.requestFocusFromTouch();
                line1.setTextSize(18.0f);
                line1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                line1.setSingleLine(true);
                line1.setTextColor(Color.parseColor("#000000"));
                line1.setGravity(Gravity.CENTER_VERTICAL);
                line1.setPadding(15, 0, 15, 0);
                line1.setLayoutParams(params);

                TextView line2 = new TextView(this);
                line2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 2.0f));

                String bb = groupstList.getGroups().get(c1).getLead_name();
                line2.setText(bb);

                line2.setId(R.id.lead_name_s);
                line2.setHint("Member Id");
                line2.requestFocusFromTouch();
                line2.setTextSize(18.0f);
                line2.setAllCaps(true);
                line2.setGravity(Gravity.CENTER);
                line2.setSingleLine(true);
                line2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                line2.setTextColor(Color.parseColor("#000000"));
                line2.setPadding(15, 0, 15, 0);
                line2.setLayoutParams(params1);

                final ImageView line3 = new ImageView(this);
                line3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 2.0f));


                line3.setId(R.id.status_s);
                line3.setBackgroundColor(Color.parseColor("#FFFFFF"));

                line3.requestFocusFromTouch();
                line3.setPressed(true);
                String cc = groupstList.getGroups().get(c1).getStatus();
                if (cc.equalsIgnoreCase("Active")) {
                    line3.setImageResource(R.drawable.ic_group_active);
                } else {
                    line3.setImageResource(R.drawable.ic_group_deactive);
                }
                line3.setPadding(50, 0, 50, 0);
                line3.setLayoutParams(params2);

//                TextView border = new TextView(this);
//                border.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
//                border.setHeight(1);
//                border.setBackgroundColor(Color.BLACK);

                cell.addView(line1);
                cell.addView(line2);
                cell.addView(line3);
                final int finalC = c1;
                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), GroupNotificationUpdateActivity.class);
                        intent.putExtra("groupsObj", groupstList.getGroups().get(finalC));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        finish();
                    }
                });

                layout_all.addView(cell);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
