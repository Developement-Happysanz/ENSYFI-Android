package com.palprotech.ensyfi.activity.general;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.database.SQLiteHelper;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Created by Narendar on 14/07/17.
 */

public class LeaveApplyActivity extends AppCompatActivity implements View.OnClickListener, IServiceListener, DialogClickListener {

    private Spinner spnLeaveType;
    private TextView dateFrom, dateTo, dateFromTime, timeTo;
    private EditText edtOnDutyRequestDetails;
    private Button btnRequest;
    protected ProgressDialogHelper progressDialogHelper;
    ServiceHelper serviceHelper;
    private static final String TAG = "LeaveApplyActivity";
    Handler mHandler = new Handler();
    SQLiteHelper database;
    List<String> lsLeaveList = new ArrayList<String>();
    Vector<String> vecLeaveList;
    String storeLeaveTypeId;
    RelativeLayout relativedate, relativetime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_application_request);

        spnLeaveType = (Spinner) findViewById(R.id.class_list_spinner);
        dateFrom = (TextView) findViewById(R.id.dateFrom);
        dateFrom.setOnClickListener(this);

        dateTo = (TextView) findViewById(R.id.dateTo);
        dateTo.setOnClickListener(this);

        dateFromTime = (TextView) findViewById(R.id.dateFromTime);
        dateFromTime.setOnClickListener(this);

        timeTo = (TextView) findViewById(R.id.timeTo);
        timeTo.setOnClickListener(this);

        edtOnDutyRequestDetails = (EditText) findViewById(R.id.edtOnDutyRequestDetails);
        btnRequest = (Button) findViewById(R.id.btnRequest);
        btnRequest.setOnClickListener(this);

        progressDialogHelper = new ProgressDialogHelper(this);
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        database = new SQLiteHelper(getApplicationContext());
        vecLeaveList = new Vector<String>();
        relativedate = (RelativeLayout) findViewById(R.id.relativedate);
        relativetime = (RelativeLayout) findViewById(R.id.relativetime);

        loadLeaveType();
        getLeaveList();

        spnLeaveType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String leaveType = parent.getItemAtPosition(position).toString();

                getLeaveTypeId(leaveType);

                int leaveTypeId = Integer.parseInt(storeLeaveTypeId);

                if (leaveTypeId == 0) {
                    relativetime.setVisibility(View.VISIBLE);
                    relativedate.setVisibility(View.VISIBLE);
                } else {
                    relativetime.setVisibility(View.GONE);
                    relativedate.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getLeaveTypeId(String leaveTypeName) {

        try {
            Cursor c = database.getLeaveTypeId(leaveTypeName);
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        storeLeaveTypeId = c.getString(0);
                    } while (c.moveToNext());
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void getLeaveList() {

        try {
            Cursor c = database.getLeaveTypeList();
            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        vecLeaveList.add(c.getString(1));
                    } while (c.moveToNext());
                }
            }
            for (int i = 0; i < vecLeaveList.size(); i++) {
                lsLeaveList.add(vecLeaveList.get(i));
            }
            HashSet hs = new HashSet();
            TreeSet ts = new TreeSet(hs);
            ts.addAll(lsLeaveList);
            lsLeaveList.clear();
            lsLeaveList.addAll(ts);
            database.close();
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item_ns, lsLeaveList);

            spnLeaveType.setAdapter(dataAdapter3);
            spnLeaveType.setWillNotDraw(false);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error getting class list lookup", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void loadLeaveType() {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(EnsyfiConstants.PARAMS_FP_USER_ID, PreferenceStorage.getUserId(getApplicationContext()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
        String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_USER_LEAVES_TYPE_API;
        serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

    }

    @Override
    public void onClick(View v) {

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
            Log.d("ajazFilterresponse : ", response.toString());
            try {
                JSONArray getLeaveTypes = response.getJSONArray("leaveDetails");
                SaveLeaveTypes(getLeaveTypes);
            } catch (Exception ex) {
            }
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
//                loadMoreListView.onLoadMoreComplete();
                AlertDialogHelper.showSimpleAlertDialog(LeaveApplyActivity.this, error);
            }
        });
    }

    private void SaveLeaveTypes(JSONArray getLeaveTypes) {
        try {
            database.deleteLeaveTypes();

            for (int i = 0; i < getLeaveTypes.length(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject jsonobj = getLeaveTypes.getJSONObject(i);

                String id = "";
                String leave_title = "";
                String leave_type = "";

                id = jsonobj.getString("id");
                leave_title = jsonobj.getString("leave_title");
                leave_type = jsonobj.getString("leave_type");

                System.out.println("id : " + i + " = " + id);
                System.out.println("leave_title : " + i + " = " + leave_title);
                System.out.println("leave_type : " + i + " = " + leave_type);


                String v1 = id,
                        v2 = leave_title,
                        v3 = leave_type;

                long l = database.leave_type_insert(v1, v2, v3);

                System.out.println("" + l);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
