package com.palprotech.ensyfi.activity.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.adminmodule.BoardMemberListAdapter;
import com.palprotech.ensyfi.bean.admin.viewlist.BoardMemberList;
import com.palprotech.ensyfi.bean.admin.viewlist.BoardMember;
import com.palprotech.ensyfi.bean.admin.viewlist.BoardMemberList;
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

public class BoardMembersListActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = BoardMembersListActivity.class.getName();
    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    ListView loadMoreListView;
    BoardMemberListAdapter BoardMemberListAdapter;
    ArrayList<BoardMember> BoardMemberArrayList;
    int pageNumber = 0, totalCount = 0;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_member_list);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        loadMoreListView = (ListView) findViewById(R.id.listView_events);
        loadMoreListView.setOnItemClickListener(this);
        BoardMemberArrayList = new ArrayList<>();
        GetMemberData();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void GetMemberData() {
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.KEY_USER_ID, "");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_BOARD_MEMBER_LIST;
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
        Log.d(TAG, "onEvent list item click" + position);
        BoardMember BoardMember = null;
        if ((BoardMemberListAdapter != null) && (BoardMemberListAdapter.ismSearching())) {
            Log.d(TAG, "while searching");
            int actualindex = BoardMemberListAdapter.getActualEventPos(position);
            Log.d(TAG, "actual index" + actualindex);
            BoardMember = BoardMemberArrayList.get(actualindex);
        } else {
            BoardMember = BoardMemberArrayList.get(position);
        }
//        Intent intent = new Intent(this, BoardMemberDetailsActivity.class);
//        intent.putExtra("eventObj", BoardMember);
//        startActivity(intent);
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

            Gson gson = new Gson();
            BoardMemberList boardMemberList = gson.fromJson(response.toString(), BoardMemberList.class);
            if (boardMemberList.getBoardMember() != null && boardMemberList.getBoardMember().size() > 0) {
                totalCount = boardMemberList.getCount();
                isLoadingForFirstTime = false;
                updateListAdapter(boardMemberList.getBoardMember());
            }

        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    @Override
    public void onError(final String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(BoardMembersListActivity.this, error);
    }

    protected void updateListAdapter(ArrayList<BoardMember> BoardMemberArrayList) {
        this.BoardMemberArrayList.addAll(BoardMemberArrayList);
        if (BoardMemberListAdapter == null) {
            BoardMemberListAdapter = new BoardMemberListAdapter(this, this.BoardMemberArrayList);
            loadMoreListView.setAdapter(BoardMemberListAdapter);
        } else {
            BoardMemberListAdapter.notifyDataSetChanged();
        }
    }
}
