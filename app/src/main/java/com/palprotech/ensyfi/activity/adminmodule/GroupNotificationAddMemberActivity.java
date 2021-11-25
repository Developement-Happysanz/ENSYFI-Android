package com.palprotech.ensyfi.activity.adminmodule;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.admin.support.StoreClassSectionId;
import com.palprotech.ensyfi.bean.admin.support.StoreRoleId;
import com.palprotech.ensyfi.bean.admin.viewlist.GroupStaffMembers;
import com.palprotech.ensyfi.bean.admin.viewlist.GroupStaffMembersList;
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

public class GroupNotificationAddMemberActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = GroupNotificationAddMemberActivity.class.getName();
    private Groups groups;
    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    ListView loadMoreListView;
    ArrayList<GroupStaffMembers> groupStaffMembersArrayList;

    GroupStaffMembersList gnStaffList = new GroupStaffMembersList();

    Handler mHandler = new Handler();
    protected boolean isLoadingForFirstTime = true;
    int pageNumber = 0, totalCount = 0;
    TextView create, groupTitleDisp, groupLeadDisp;
    Spinner spnMemberType, spnStudentClass;
    String res = "", roleId, roleName, classSecName, classSectionId;
    boolean selval = false;

    LinearLayout layout_all;

//    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_notification_add_members);

        /*toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
        setSupportActionBar(toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("ADD MEMBERS");*/

        groups = (Groups) getIntent().getSerializableExtra("groupsObj");
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        GetMemberRolesData();
        initializeViews();

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializeViews() {
        groupTitleDisp = findViewById(R.id.group_title_txt_disp);
        groupTitleDisp.setText(groups.getGroup_title());
        groupLeadDisp = findViewById(R.id.group_lead_spinner_txt);
        groupLeadDisp.setText(groups.getLead_name());
//        loadMoreListView = (ListView) findViewById(R.id.listView_members);
//        loadMoreListView.setOnItemClickListener(this);
        groupStaffMembersArrayList = new ArrayList<>();

        layout_all = findViewById(R.id.layout_member_list);

        spnMemberType = findViewById(R.id.group_member_type_spinner);
        spnMemberType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                roleName = parent.getSelectedItem().toString();
                StoreRoleId teacherName = (StoreRoleId) parent.getSelectedItem();
                roleId = teacherName.getRoleId();
                int sRole = Integer.parseInt(roleId);
                switch (sRole) {
                    case 2:
                        spnStudentClass.setVisibility(View.GONE);

                        layout_all.removeAllViews();
                        getTeacherList();
                        break;
                    case 3:

                        layout_all.removeAllViews();
                        GetClassSectionData();
                        spnStudentClass.setVisibility(View.VISIBLE);
                        break;
                    case 4:

                        layout_all.removeAllViews();
                        GetClassSectionData();
                        spnStudentClass.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        spnStudentClass.setVisibility(View.GONE);

                        layout_all.removeAllViews();
                        getTeacherList();
                        break;
                    default:
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnStudentClass = findViewById(R.id.group_student_class_spinner);
        spnStudentClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classSecName = parent.getSelectedItem().toString();
                StoreClassSectionId className = (StoreClassSectionId) parent.getSelectedItem();
                classSectionId = className.getClassSectionId();
                layout_all.removeAllViews();
                getStudentList();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        create = (TextView) findViewById(R.id.add_members);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGroupMenbers();
            }
        });
    }

    private void sendGroupMenbers() {

        ArrayList<String> rollRdList = new ArrayList();
        for (int i = 0; i < gnStaffList.getGroups().size(); i++) {
            if ((gnStaffList.getGroups().get(i).getStatus().equalsIgnoreCase("1"))) {
                rollRdList.add(gnStaffList.getGroups().get(i).getId());
            }
        }
        if (CommonUtils.isNetworkAvailable(this)) {
            res = "memberList";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_USER_ID, PreferenceStorage.getUserId(this));
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_ID, groups.getId());
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_MEMBER_ID, rollRdList.toString().replace("[", "").replace("]", ""));
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_USER_TYPE_ID, roleId);
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_CLASS_SEC_ID, classSectionId);
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_STATUS, groups.getStatus());
                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.SEND_GROUP_MEMBERS;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }


    }

    private void getTeacherList() {
        res = "teacher";
        if (groupStaffMembersArrayList != null)
            groupStaffMembersArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_ID, groups.getId());
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_USER_TYPE_ID, roleId);
                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.GET_GROUP_MEMBER_STAFF;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private void getStudentList() {
        res = "student";
        if (groupStaffMembersArrayList != null)
            groupStaffMembersArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_ID, groups.getId());
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_USER_TYPE_ID, roleId);
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_STUDENT_CLASS_ID, classSectionId);
                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.GET_GROUP_MEMBER_STUDENTS;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private void GetMemberRolesData() {
        res = "roles";

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_USER_ID, PreferenceStorage.getUserId(this));
                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.GET_GROUP_MEMBER_ROLES;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private void GetClassSectionData() {
        res = "classSection";

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_USER_ID, PreferenceStorage.getUserId(this));
                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.GET_GROUP_CLASS_SECTION;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.preference_select, menu);
        MenuItem menuSet = (MenuItem) menu.findItem(R.id.action_select);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        noinspection SimplifiableIfStatement
        if (id == R.id.action_select) {
            selval = true;
            for (int pos = 0; pos < totalCount; pos++) {

                groupStaffMembersArrayList.get(pos).setStatus("1");
//                loadMoreListView.getChildAt(pos).getRootView().findViewById(R.id.status_selected).setVisibility(View.VISIBLE);
//                loadMoreListView.getChildAt(pos).getRootView().findViewById(R.id.status_deselected).setVisibility(View.INVISIBLE);
//                groupMemberListAdapter.notifyDataSetChanged();
            }
//            loadMembersList(totalCount);
            return true;
        } else if (id == R.id.action_deselect) {
            selval = false;
            for (int pos = 0; pos < totalCount; pos++) {

                groupStaffMembersArrayList.get(pos).setStatus("0");
//                loadMoreListView.getChildAt(pos).getRootView().findViewById(R.id.status_deselected).setVisibility(View.VISIBLE);
//                loadMoreListView.getChildAt(pos).getRootView().findViewById(R.id.status_selected).setVisibility(View.INVISIBLE);
//                groupMemberListAdapter.notifyDataSetChanged();
            }
//            loadMembersList(totalCount);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onOD list item clicked" + position);

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
        progressDialogHelper.hideProgressDialog();


        if (validateSignInResponse(response)) {

            try {
                if (res.equalsIgnoreCase("roles")) {
                    JSONArray getData = response.getJSONArray("roleList");
                    JSONObject userData = getData.getJSONObject(0);
                    int getLength = getData.length();
                    Log.d(TAG, "userData dictionary" + userData.toString());
                    ArrayList<StoreRoleId> rolesList = new ArrayList<>();
                    String roleId = "";
                    String roleName = "";
                    String staffStatus = "";
                    String status = "";
                    for (int i = 0; i < getLength; i++) {

                        roleId = getData.getJSONObject(i).getString("role_id");
                        roleName = getData.getJSONObject(i).getString("user_type_name");
                        staffStatus = getData.getJSONObject(i).getString("staff_status");
                        status = getData.getJSONObject(i).getString("status");

                        rolesList.add(new StoreRoleId(roleId, roleName, staffStatus, status));
                    }
                    ArrayAdapter<StoreRoleId> adapter = new ArrayAdapter<StoreRoleId>(getApplicationContext(), R.layout.spinner_item_ns, rolesList);
                    spnMemberType.setAdapter(adapter);
                } else if (res.equalsIgnoreCase("teacher")) {
                    final JSONArray getData = response.getJSONArray("gnMemberlist");
                    if (getData != null && getData.length() > 0) {

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                gnStaffList = gson.fromJson(response.toString(), GroupStaffMembersList.class);
                                if (gnStaffList.getGroups() != null && gnStaffList.getGroups().size() > 0) {
                                    totalCount = getData.length();

                                    isLoadingForFirstTime = false;
//                                    updateListAdapter(groupStaffMembersList.getGroups());
                                    loadMembersList(totalCount);

                                }
                            }
                        });
                    } else {
                        if (groupStaffMembersArrayList != null) {
                            groupStaffMembersArrayList.clear();
//                            groupMemberListAdapter = new GroupMemberListAdapter(this, this.groupStaffMembersArrayList);
//                            loadMoreListView.setAdapter(groupMemberListAdapter);
                        }
                    }
                } else if (res.equalsIgnoreCase("classSection")) {
                    JSONArray getData = response.getJSONArray("listClasssection");
                    JSONObject userData = getData.getJSONObject(0);
                    int getLength = getData.length();
                    Log.d(TAG, "userData dictionary" + userData.toString());
                    ArrayList<StoreClassSectionId> classList = new ArrayList<>();
                    String classSecId = "";
                    String classSecName = "";
                    for (int i = 0; i < getLength; i++) {

                        classSecId = getData.getJSONObject(i).getString("class_sec_id");
                        classSecName = getData.getJSONObject(i).getString("class_section");

                        classList.add(new StoreClassSectionId(classSecId, classSecName));
                    }
                    ArrayAdapter<StoreClassSectionId> adapter = new ArrayAdapter<StoreClassSectionId>(getApplicationContext(), R.layout.spinner_item_ns, classList);
                    spnStudentClass.setAdapter(adapter);
                } else if (res.equalsIgnoreCase("student")) {
                    final JSONArray getData = response.getJSONArray("gnMemberlist");
                    if (getData != null && getData.length() > 0) {

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                gnStaffList = gson.fromJson(response.toString(), GroupStaffMembersList.class);
                                if (gnStaffList.getGroups() != null && gnStaffList.getGroups().size() > 0) {
                                    totalCount = getData.length();

                                    isLoadingForFirstTime = false;
//                                    updateListAdapter(groupStudentMembersList.getGroups());
                                    loadMembersList(totalCount);

                                }
                            }
                        });
                    } else {
                        if (groupStaffMembersArrayList != null) {
                            groupStaffMembersArrayList.clear();
                            totalCount = 0;
//                            groupMemberListAdapter = new GroupMemberListAdapter(this, this.groupStaffMembersArrayList);
//                            loadMoreListView.setAdapter(groupMemberListAdapter);
                        }
                    }
                } else if (res.equalsIgnoreCase("memberList")) {
                    String status = response.getString("status");
                    String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                    if (status.equalsIgnoreCase("success") && msg.equalsIgnoreCase("Group Members Added")) {
                        Toast.makeText(this, "New member(s) added to the group", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else

        {
            Log.d(TAG, "Error while sign In");
        }

    }

    @Override
    public void onError(String error) {

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
                params.addRule(RelativeLayout.LEFT_OF, R.id.member_id_txt);


                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(250, 100);
                params1.setMargins(1, 1, 1, 1);
                params1.addRule(RelativeLayout.LEFT_OF, R.id.member_status);


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

                line1.setText(gnStaffList.getGroups().get(c1).getName());


                line1.setId(R.id.member_name_txt);
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

                line2.setText(gnStaffList.getGroups().get(c1).getId());

                line2.setId(R.id.member_id_txt);
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


                line3.setId(R.id.member_status);
                line3.setBackgroundColor(Color.parseColor("#FFFFFF"));

                line3.requestFocusFromTouch();
                line3.setPressed(true);
                if (gnStaffList.getGroups().get(c1).getStatus().equalsIgnoreCase("1")) {
                    line3.setImageResource(R.drawable.ic_select);
                } else {
                    line3.setImageResource(R.drawable.ic_unselect);
                }
                line3.setPadding(50, 0, 50, 0);
                line3.setLayoutParams(params2);
                final int finalC = c1;
                line3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (gnStaffList.getGroups().get(finalC).getStatus().equalsIgnoreCase("1")) {
                            line3.setImageResource(R.drawable.ic_unselect);
                            gnStaffList.getGroups().get(finalC).setStatus("0");
                        } else {
                            line3.setImageResource(R.drawable.ic_select);
                            gnStaffList.getGroups().get(finalC).setStatus("1");
                        }
                    }
                });

//                TextView border = new TextView(this);
//                border.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
//                border.setHeight(1);
//                border.setBackgroundColor(Color.BLACK);

                cell.addView(line1);
                cell.addView(line2);
                cell.addView(line3);
//                cell.addView(border);

                layout_all.addView(cell);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
