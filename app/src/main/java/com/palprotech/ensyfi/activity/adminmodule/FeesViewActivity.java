package com.palprotech.ensyfi.activity.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.adminmodule.FeesListAdapter;
import com.palprotech.ensyfi.bean.admin.support.StoreClass;
import com.palprotech.ensyfi.bean.admin.support.StoreSection;
import com.palprotech.ensyfi.bean.admin.viewlist.Fees;
import com.palprotech.ensyfi.bean.admin.viewlist.FeesList;
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

/**
 * Created by Admin on 18-07-2017.
 */

public class FeesViewActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = FeesViewActivity.class.getName();
    private ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    private Spinner spnClassList, spnSectionList;
    private String checkSpinner = "", storeClassId, storeSectionId;
    ListView loadMoreListView;
    FeesListAdapter feesListAdapter;
    ArrayList<Fees> feesArrayList;
    int pageNumber = 0, totalCount = 0;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_view);

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        spnClassList = (Spinner) findViewById(R.id.class_list_spinner);
        spnSectionList = (Spinner) findViewById(R.id.section_list_spinner);
        loadMoreListView = (ListView) findViewById(R.id.listView_events);
        loadMoreListView.setOnItemClickListener(this);
        feesArrayList = new ArrayList<>();

        GetClassData();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spnClassList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {

                    StoreClass classList = (StoreClass) parent.getSelectedItem();
                    storeClassId = classList.getClassId();
                    GetSectionData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnSectionList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {

                    StoreSection sectionList = (StoreSection) parent.getSelectedItem();
                    storeSectionId = sectionList.getSectionId();
                    GetFeesData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void GetSectionData() {
        checkSpinner = "section";
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID_LIST, storeClassId);
                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.GET_SECTION_LISTS;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private void GetClassData() {
        checkSpinner = "class";
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID, PreferenceStorage.getStudentClassIdPreference(getApplicationContext()));
                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.GET_CLASS_LISTS;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);


        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    private void GetFeesData() {

        checkSpinner = "fees";
        if (feesArrayList != null)
            feesArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_CLASS_ID_NEW, storeClassId);
                jsonObject.put(EnsyfiConstants.PARAMS_SECTION_ID, storeSectionId);
                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.GET_VIEW_FEES;
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

                        if (feesArrayList != null) {
                            feesArrayList.clear();
                            feesListAdapter = new FeesListAdapter(this, this.feesArrayList);
                            loadMoreListView.setAdapter(feesListAdapter);
                        }

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
        Fees fees = null;
        if ((feesListAdapter != null) && (feesListAdapter.ismSearching())) {
            Log.d(TAG, "while searching");
            int actualindex = feesListAdapter.getActualEventPos(position);
            Log.d(TAG, "actual index" + actualindex);
            fees = feesArrayList.get(actualindex);
        } else {
            fees = feesArrayList.get(position);
        }
        Intent intent = new Intent(this, FeesStatusView.class);
        intent.putExtra("eventObj", fees);
        intent.putExtra("storeClassId", storeClassId);
        intent.putExtra("storeSectionId", storeSectionId);
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
                if (checkSpinner.equalsIgnoreCase("class")) {

                    JSONArray getData = response.getJSONArray("data");
                    JSONObject userData = getData.getJSONObject(0);
                    int getLength = getData.length();
                    Log.d(TAG, "userData dictionary" + userData.toString());

                    String classId = "Default id";
                    String className = "Select Class";
                    ArrayList<StoreClass> classesList = new ArrayList<>();
                    classesList.add(new StoreClass(classId, className));

                    for (int i = 0; i < getLength; i++) {

                        classId = getData.getJSONObject(i).getString("class_id");
                        className = getData.getJSONObject(i).getString("class_name");

                        classesList.add(new StoreClass(classId, className));
                    }

                    //fill data in spinner
                    ArrayAdapter<StoreClass> adapter = new ArrayAdapter<StoreClass>(getApplicationContext(), R.layout.spinner_item_ns, classesList);
                    spnClassList.setAdapter(adapter);
                } else if (checkSpinner.equalsIgnoreCase("section")) {
                    JSONArray getData = response.getJSONArray("data");
                    JSONObject userData = getData.getJSONObject(0);
                    int getLength = getData.length();
                    Log.d(TAG, "userData dictionary" + userData.toString());

                    String sectionId = "Default id";
                    String sectionclass = "Select section";
                    ArrayList<StoreSection> sectionList = new ArrayList<>();
                    sectionList.add(new StoreSection(sectionId, sectionclass));

                    for (int i = 0; i < getLength; i++) {

                        sectionId = getData.getJSONObject(i).getString("sec_id");
                        sectionclass = getData.getJSONObject(i).getString("sec_name");

                        sectionList.add(new StoreSection(sectionId, sectionclass));
                    }

                    //fill data in spinner
                    ArrayAdapter<StoreSection> adapter = new ArrayAdapter<StoreSection>(getApplicationContext(), R.layout.spinner_item_ns, sectionList);
                    spnSectionList.setAdapter(adapter);
                } else {

                    JSONArray getData = response.getJSONArray("data");
                    if (getData != null && getData.length() > 0) {

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressDialogHelper.hideProgressDialog();
                                Gson gson = new Gson();
                                FeesList feesList = gson.fromJson(response.toString(), FeesList.class);
                                if (feesList.getFees() != null && feesList.getFees().size() > 0) {
                                    totalCount = feesList.getCount();
                                    isLoadingForFirstTime = false;
                                    updateListAdapter(feesList.getFees());
                                }
                            }
                        });
                    } else {
                        if (feesArrayList != null) {
                            feesArrayList.clear();
                            feesListAdapter = new FeesListAdapter(this, this.feesArrayList);
                            loadMoreListView.setAdapter(feesListAdapter);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
                AlertDialogHelper.showSimpleAlertDialog(FeesViewActivity.this, error);
            }
        });
    }

    protected void updateListAdapter(ArrayList<Fees> feesArrayList) {
        this.feesArrayList.addAll(feesArrayList);
        if (feesListAdapter == null) {
            feesListAdapter = new FeesListAdapter(this, this.feesArrayList);
            loadMoreListView.setAdapter(feesListAdapter);
        } else {
            feesListAdapter.notifyDataSetChanged();
        }
    }
}
