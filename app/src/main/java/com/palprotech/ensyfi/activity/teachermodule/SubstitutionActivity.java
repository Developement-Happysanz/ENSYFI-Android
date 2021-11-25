package com.palprotech.ensyfi.activity.teachermodule;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.teachermodule.SpecialClassListAdapter;
import com.palprotech.ensyfi.adapter.teachermodule.SubstitutionClassListAdapter;
import com.palprotech.ensyfi.bean.teacher.viewlist.SpecialClass;
import com.palprotech.ensyfi.bean.teacher.viewlist.SpecialClassList;
import com.palprotech.ensyfi.bean.teacher.viewlist.SubstitutionClass;
import com.palprotech.ensyfi.bean.teacher.viewlist.SubstitutionClassList;
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

public class SubstitutionActivity extends AppCompatActivity implements IServiceListener, AdapterView.OnItemClickListener, DialogClickListener {

    private static final String TAG = "SubstitutionActivity";
    ListView loadMoreListView;
    View view;
    SubstitutionClassListAdapter circularListAdapter;
    ServiceHelper serviceHelper;
    ArrayList<SubstitutionClass> circularArrayList;
    int totalCount = 0;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substitution);
        loadMoreListView = (ListView) findViewById(R.id.list_classes);
        loadMoreListView.setOnItemClickListener(this);
        circularArrayList = new ArrayList<>();
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);

        callGetCircularService();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void callGetCircularService() {

        if (circularArrayList != null) {
            circularArrayList.clear();
        }

        if (CommonUtils.isNetworkAvailable(this)) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.KEY_USER_ID, PreferenceStorage.getUserId(getApplicationContext()));
                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = "";
            url = EnsyfiConstants.BASE_URL + EnsyfiConstants.GET_SUBSTITUTION_CLASS;
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
            Log.d("ajazFilterresponse : ", response.toString());
            Gson gson = new Gson();
            SubstitutionClassList circularList = gson.fromJson(response.toString(), SubstitutionClassList.class);
            if (circularList.getClassTestMark() != null && circularList.getClassTestMark().size() > 0) {
                totalCount = circularList.getCount();
                isLoadingForFirstTime = false;
                updateListAdapter(circularList.getClassTestMark());
            }
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    protected void updateListAdapter(ArrayList<SubstitutionClass> circularArrayList) {
        this.circularArrayList.addAll(circularArrayList);
        if (circularListAdapter == null) {
            circularListAdapter = new SubstitutionClassListAdapter(this, this.circularArrayList);
            loadMoreListView.setAdapter(circularListAdapter);
        } else {
            circularListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(final String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(SubstitutionActivity.this, error);
    }
}