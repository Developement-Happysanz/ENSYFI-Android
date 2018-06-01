package com.palprotech.ensyfi.activity.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.adminmodule.GroupMemberListAdapter;
import com.palprotech.ensyfi.adapter.adminmodule.GroupsListAdapter;
import com.palprotech.ensyfi.bean.admin.support.StoreClassSectionId;
import com.palprotech.ensyfi.bean.admin.support.StoreRoleId;
import com.palprotech.ensyfi.bean.admin.viewlist.GroupStaffMembers;
import com.palprotech.ensyfi.bean.admin.viewlist.GroupStaffMembersList;
import com.palprotech.ensyfi.bean.admin.viewlist.GroupStudentMembersList;
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
import java.util.List;

public class GroupNotificationAddMemberActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = GroupNotificationAddMemberActivity.class.getName();
    private Groups groups;
    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    ListView loadMoreListView;
    GroupMemberListAdapter groupMemberListAdapter;
    ArrayList<GroupStaffMembers> groupStaffMembersArrayList;
    ArrayList<Integer> selectedMembers;
    ArrayList<Integer> removeMembers;
    Handler mHandler = new Handler();
    protected boolean isLoadingForFirstTime = true;
    int pageNumber = 0, totalCount = 0;
    TextView create, groupTitleDisp, groupLeadDisp;
    Spinner spnMemberType, spnStudentClass;
    String res = "", roleId, roleName, classSecName, classSectionId;
    boolean selval = false;
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
        loadMoreListView = (ListView) findViewById(R.id.listView_members);
        loadMoreListView.setOnItemClickListener(this);
        groupStaffMembersArrayList = new ArrayList<>();
        spnMemberType = findViewById(R.id.group_member_type_spinner);
        spnMemberType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMembers.clear();
                roleName = parent.getSelectedItem().toString();
                StoreRoleId teacherName = (StoreRoleId) parent.getSelectedItem();
                roleId = teacherName.getRoleId();
                int sRole = Integer.parseInt(roleId);
                switch (sRole) {
                    case 2:
                        spnStudentClass.setVisibility(View.GONE);
                        getTeacherList();
                        break;
                    case 3:
                        GetClassSectionData();
                        spnStudentClass.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        GetClassSectionData();
                        spnStudentClass.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        spnStudentClass.setVisibility(View.GONE);
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
                getStudentList();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        selectedMembers = new ArrayList<>();
        removeMembers = new ArrayList<>();

        create = (TextView) findViewById(R.id.add_members);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGroupMenbers();
            }
        });
    }

    private void sendGroupMenbers() {
        selectedMembers.removeAll(removeMembers);
        ArrayList<String> rollRdList = new ArrayList();
        for (int i = 0; i < selectedMembers.size(); i++) {
            if (!(rollRdList.contains(selectedMembers.get(i)))) {
                rollRdList.add(groupStaffMembersArrayList.get(selectedMembers.get(i)).getId());
            }
        }
        if (CommonUtils.isNetworkAvailable(this)) {
            res = "memberList";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_USER_ID, PreferenceStorage.getUserId(this));
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_ID, groups.getId());
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_MEMBER_ID, rollRdList);
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_GROUP_USER_TYPE_ID, roleId);
                jsonObject.put(EnsyfiConstants.PARAMS_GROUP_NOTIFICATIONS_CREATION_STATUS, groups.getStatus());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.SEND_GROUP_MEMBERS;
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

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_GROUP_MEMBER_STAFF;
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_GROUP_MEMBER_STUDENTS;
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

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_GROUP_MEMBER_ROLES;
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

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_GROUP_CLASS_SECTION;
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
                selectedMembers.add(pos);
                groupStaffMembersArrayList.get(pos).setStatus("1");
//                loadMoreListView.getChildAt(pos).getRootView().findViewById(R.id.status_selected).setVisibility(View.VISIBLE);
//                loadMoreListView.getChildAt(pos).getRootView().findViewById(R.id.status_deselected).setVisibility(View.INVISIBLE);
                groupMemberListAdapter.notifyDataSetChanged();
            }
            return true;
        } else if (id == R.id.action_deselect) {
            selval = false;
            for (int pos = 0; pos < totalCount; pos++) {
                removeMembers.add(pos);
                groupStaffMembersArrayList.get(pos).setStatus("0");
//                loadMoreListView.getChildAt(pos).getRootView().findViewById(R.id.status_deselected).setVisibility(View.VISIBLE);
//                loadMoreListView.getChildAt(pos).getRootView().findViewById(R.id.status_selected).setVisibility(View.INVISIBLE);
                groupMemberListAdapter.notifyDataSetChanged();
            }
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
        setClickStatus(view, position);

    }

    private void setClickStatus(View view, int pos) {
        if (!(selectedMembers.isEmpty())) {
            if (selectedMembers.contains(pos)) {
                groupStaffMembersArrayList.get(pos).setStatus("0");
                view.findViewById(R.id.status_selected).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.status_deselected).setVisibility(View.VISIBLE);
                removeMembers.add(pos);
                groupMemberListAdapter.notifyDataSetChanged();
            } else {
                selectedMembers.add(pos);
                groupStaffMembersArrayList.get(pos).setStatus("1");
                view.findViewById(R.id.status_selected).setVisibility(View.VISIBLE);
                view.findViewById(R.id.status_deselected).setVisibility(View.INVISIBLE);
                groupMemberListAdapter.notifyDataSetChanged();
            }
        } else {
            selectedMembers.add(pos);
            groupStaffMembersArrayList.get(pos).setStatus("1");
            view.findViewById(R.id.status_selected).setVisibility(View.VISIBLE);
            view.findViewById(R.id.status_deselected).setVisibility(View.INVISIBLE);
            groupMemberListAdapter.notifyDataSetChanged();
        }
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


        if (validateSignInResponse(response)) {

            try {
                if (res.equalsIgnoreCase("roles")) {
                    progressDialogHelper.hideProgressDialog();
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
                    final JSONArray getData = response.getJSONArray("gnStafflist");
                    if (getData != null && getData.length() > 0) {

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressDialogHelper.hideProgressDialog();
                                Gson gson = new Gson();
                                GroupStaffMembersList groupStaffMembersList = gson.fromJson(response.toString(), GroupStaffMembersList.class);
                                if (groupStaffMembersList.getGroups() != null && groupStaffMembersList.getGroups().size() > 0) {
                                    totalCount = getData.length();
                                    for (int i = 0; i < totalCount; i++) {
                                        String status = groupStaffMembersList.getGroups().get(i).getStatus();
                                        if (status.equals("1")) {
                                            selectedMembers.add(i);
                                        }
                                    }
                                    isLoadingForFirstTime = false;
                                    updateListAdapter(groupStaffMembersList.getGroups());
                                }
                            }
                        });
                    } else {
                        if (groupStaffMembersArrayList != null) {
                            groupStaffMembersArrayList.clear();
                            groupMemberListAdapter = new GroupMemberListAdapter(this, this.groupStaffMembersArrayList);
                            loadMoreListView.setAdapter(groupMemberListAdapter);
                        }
                    }
                } else if (res.equalsIgnoreCase("classSection")) {
                    progressDialogHelper.hideProgressDialog();
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
                    final JSONArray getData = response.getJSONArray("gnStudentlist");
                    if (getData != null && getData.length() > 0) {

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressDialogHelper.hideProgressDialog();
                                Gson gson = new Gson();
                                GroupStaffMembersList groupStudentMembersList = gson.fromJson(response.toString(), GroupStaffMembersList.class);
                                if (groupStudentMembersList.getGroups() != null && groupStudentMembersList.getGroups().size() > 0) {
                                    totalCount = getData.length();
                                    for (int i = 0; i < totalCount; i++) {
                                        String status = groupStudentMembersList.getGroups().get(i).getStatus();
                                        if (status.equals("1")) {
                                            selectedMembers.add(i);
                                        }
                                    }
                                    isLoadingForFirstTime = false;
                                    updateListAdapter(groupStudentMembersList.getGroups());
                                }
                            }
                        });
                    } else {
                        if (groupStaffMembersArrayList != null) {
                            groupStaffMembersArrayList.clear();
                            groupMemberListAdapter = new GroupMemberListAdapter(this, this.groupStaffMembersArrayList);
                            loadMoreListView.setAdapter(groupMemberListAdapter);
                        }
                    }
                } else if (res.equalsIgnoreCase("memberList")) {
                    String status = response.getString("status");
                    String msg = response.getString(EnsyfiConstants.PARAM_MESSAGE);
                    if (status.equalsIgnoreCase("success") && msg.equalsIgnoreCase("Group Members Added")) {
                        Toast.makeText(this,"Group Members Added!!", Toast.LENGTH_SHORT).show();
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

    protected void updateListAdapter(ArrayList<GroupStaffMembers> groupStaffMembersArrayList) {
        this.groupStaffMembersArrayList.addAll(groupStaffMembersArrayList);
        if (groupMemberListAdapter == null) {
            groupMemberListAdapter = new GroupMemberListAdapter(this, this.groupStaffMembersArrayList);
            loadMoreListView.setAdapter(groupMemberListAdapter);
        } else {
            groupMemberListAdapter.notifyDataSetChanged();
        }
    }
}
