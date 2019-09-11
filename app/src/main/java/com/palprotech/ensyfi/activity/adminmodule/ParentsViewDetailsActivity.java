package com.palprotech.ensyfi.activity.adminmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.admin.viewlist.ParentStudent;
import com.palprotech.ensyfi.bean.parents.Support.SaveParentData;
import com.palprotech.ensyfi.bean.student.support.SaveStudentData;
import com.palprotech.ensyfi.helper.AlertDialogHelper;
import com.palprotech.ensyfi.helper.ProgressDialogHelper;
import com.palprotech.ensyfi.interfaces.DialogClickListener;
import com.palprotech.ensyfi.servicehelpers.ServiceHelper;
import com.palprotech.ensyfi.serviceinterfaces.IServiceListener;
import com.palprotech.ensyfi.utils.CommonUtils;
import com.palprotech.ensyfi.utils.EnsyfiConstants;
import com.palprotech.ensyfi.utils.PreferenceStorage;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 18-07-2017.
 */

public class ParentsViewDetailsActivity extends AppCompatActivity implements IServiceListener, DialogClickListener, View.OnClickListener {

    private static final String TAG = ParentsViewDetailsActivity.class.getName();
    private ParentStudent parentStudent;
    private TextView Name, Address, Mail, Occupation, Income, Mobile, OfficePhone, HomePhone;
    private Button btnShowStudentInfo;
    private ServiceHelper serviceHelper;
    private ProgressDialogHelper progressDialogHelper;
    private ImageView guardianImg, fatherInfo, motherInfo, btnBack;
    private SaveStudentData studentData;
    private SaveParentData parentData;
    private RelativeLayout info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents_view_details);

        parentStudent = (ParentStudent) getIntent().getSerializableExtra("eventObj");

        btnShowStudentInfo = (Button) findViewById(R.id.btnShowStudentInfo);
        btnShowStudentInfo.setOnClickListener(this);

        findViewById();

        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        studentData = new SaveStudentData(this);
        parentData = new SaveParentData(this);

        progressDialogHelper = new ProgressDialogHelper(this);

        callEmptyData();
        populateData();
        callFatherInfoPreferences();
        info.setVisibility(View.VISIBLE);
    }

    private void populateData() {

        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAMS_PARENT_ID_SHOW_NEW, parentStudent.getAdmnNo());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_PARENT_INFO;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnShowStudentInfo) {
            Intent intent = new Intent(this, StudentsInfoActivity.class);
            intent.putExtra("eventObj", parentStudent);
            startActivity(intent);
        }

        if (v == btnBack) {
            finish();
        }

        if (v == fatherInfo) {
            info.setVisibility(View.VISIBLE);
            callFatherInfoPreferences();
        }

        if (v == motherInfo) {
            info.setVisibility(View.VISIBLE);
            callMotherInfoPreferences();
        }

        if (v == guardianImg) {
            info.setVisibility(View.VISIBLE);
            callGuardianInfoPreferences();
        }
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {
            try {
                JSONObject getParentData = response.getJSONObject("parentProfile");
                saveStudentParentDetails(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    @Override
    public void onError(String error) {
        progressDialogHelper.hideProgressDialog();
        AlertDialogHelper.showSimpleAlertDialog(this, error);
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

    private void callFatherInfoPreferences() {
        Name.setText("");
        Address.setText("");
        Mail.setText("");
        Occupation.setText("");
        Income.setText("");
        Mobile.setText("");
        OfficePhone.setText("");
        HomePhone.setText("");

        Name.setText(PreferenceStorage.getFatherName(getApplicationContext()));
        Address.setText(PreferenceStorage.getFatherAddress(getApplicationContext()));
        Mail.setText(PreferenceStorage.getFatherEmail(getApplicationContext()));
        Occupation.setText(PreferenceStorage.getFatherOccupation(getApplicationContext()));
        Income.setText(PreferenceStorage.getFatherIncome(getApplicationContext()));
        Mobile.setText(PreferenceStorage.getFatherMobile(getApplicationContext()));
        OfficePhone.setText(PreferenceStorage.getFatherOfficePhone(getApplicationContext()));
        HomePhone.setText(PreferenceStorage.getFatherHomePhone(getApplicationContext()));
        String url = PreferenceStorage.getFatherImg(this);

        if (((url != null) && !(url.isEmpty()))) {
            Picasso.get().load(url).placeholder(R.drawable.ic_father).error(R.drawable.ic_father).into(fatherInfo);
        }
        if (((url != null) && !(url.isEmpty()))) {
            Picasso.get().load(url).placeholder(R.drawable.ic_mother_deselect).error(R.drawable.ic_mother_deselect).into(motherInfo);
        }
        if (((url != null) && !(url.isEmpty()))) {
            Picasso.get().load(url).placeholder(R.drawable.ic_guardian_deselect).error(R.drawable.ic_guardian_deselect).into(guardianImg);
        }
    }

    private void callMotherInfoPreferences() {
        Name.setText("");
        Address.setText("");
        Mail.setText("");
        Occupation.setText("");
        Income.setText("");
        Mobile.setText("");
        OfficePhone.setText("");
        HomePhone.setText("");

        Name.setText(PreferenceStorage.getMotherName(getApplicationContext()));
        Address.setText(PreferenceStorage.getMotherAddress(getApplicationContext()));
        Mail.setText(PreferenceStorage.getMotherEmail(getApplicationContext()));
        Occupation.setText(PreferenceStorage.getMotherOccupation(getApplicationContext()));
        Income.setText(PreferenceStorage.getMotherIncome(getApplicationContext()));
        Mobile.setText(PreferenceStorage.getMotherMobile(getApplicationContext()));
        OfficePhone.setText(PreferenceStorage.getMotherOfficePhone(getApplicationContext()));
        HomePhone.setText(PreferenceStorage.getMotherHomePhone(getApplicationContext()));
        String url = PreferenceStorage.getMotherImg(this);

        if (((url != null) && !(url.isEmpty()))) {
            Picasso.get().load(url).placeholder(R.drawable.ic_father_deselect).error(R.drawable.ic_father_deselect).into(fatherInfo);
        }
        if (((url != null) && !(url.isEmpty()))) {
            Picasso.get().load(url).placeholder(R.drawable.ic_mother).error(R.drawable.ic_mother).into(motherInfo);
        }
        if (((url != null) && !(url.isEmpty()))) {
            Picasso.get().load(url).placeholder(R.drawable.ic_guardian_deselect).error(R.drawable.ic_guardian_deselect).into(guardianImg);
        }
    }

    private void callGuardianInfoPreferences() {
        Name.setText("");
        Address.setText("");
        Mail.setText("");
        Occupation.setText("");
        Income.setText("");
        Mobile.setText("");
        OfficePhone.setText("");
        HomePhone.setText("");

        Name.setText(PreferenceStorage.getGuardianName(getApplicationContext()));
        Address.setText(PreferenceStorage.getGuardianAddress(getApplicationContext()));
        Mail.setText(PreferenceStorage.getGuardianEmail(getApplicationContext()));
        Occupation.setText(PreferenceStorage.getGuardianOccupation(getApplicationContext()));
        Income.setText(PreferenceStorage.getGuardianIncome(getApplicationContext()));
        Mobile.setText(PreferenceStorage.getGuardianMobile(getApplicationContext()));
        OfficePhone.setText(PreferenceStorage.getGuardianOfficePhone(getApplicationContext()));
        HomePhone.setText(PreferenceStorage.getGuardianHomePhone(getApplicationContext()));
        String url = PreferenceStorage.getGuardianImg(this);

        if (((url != null) && !(url.isEmpty()))) {
            Picasso.get().load(url).placeholder(R.drawable.ic_father_deselect).error(R.drawable.ic_father_deselect).into(fatherInfo);
        }
        if (((url != null) && !(url.isEmpty()))) {
            Picasso.get().load(url).placeholder(R.drawable.ic_mother_deselect).error(R.drawable.ic_mother_deselect).into(motherInfo);
        }
        if (((url != null) && !(url.isEmpty()))) {
            Picasso.get().load(url).placeholder(R.drawable.ic_guardian_1).error(R.drawable.ic_guardian_1).into(guardianImg);
        }
    }

    private void findViewById() {
        btnBack = (ImageView) findViewById(R.id.back_res);
        btnBack.setOnClickListener(this);

        Name = (TextView) findViewById(R.id.txtfathername);
        Address = (TextView) findViewById(R.id.txtfatheraddress);
        Mail = (TextView) findViewById(R.id.txtfathermail);
        Occupation = (TextView) findViewById(R.id.txtfatheroccupation);
        Income = (TextView) findViewById(R.id.txtincome);
        Mobile = (TextView) findViewById(R.id.txtfathermobile);
        OfficePhone = (TextView) findViewById(R.id.txtfatherofficephone);
        HomePhone = (TextView) findViewById(R.id.txtfatherhomephone);
        guardianImg = (ImageView) findViewById(R.id.img_guardian_profile);
        guardianImg.setOnClickListener(this);
        motherInfo = (ImageView) findViewById(R.id.img_mother_profile);
        motherInfo.setOnClickListener(this);
        fatherInfo = (ImageView) findViewById(R.id.img_father_profile);
        fatherInfo.setOnClickListener(this);

        info = (RelativeLayout) findViewById(R.id.iid);

    }

    private void saveStudentParentDetails(JSONObject response) {
        try {

            JSONObject getParentData = response.getJSONObject("parentProfile");
            parentData.saveParentProfile(getParentData);

            JSONArray getStudentData = response.getJSONArray("registeredDetails");
            studentData.saveStudentRegisteredData(getStudentData);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void callEmptyData() {

        PreferenceStorage.saveFatherID(getApplicationContext(), "");
        PreferenceStorage.saveFatherName(getApplicationContext(), "");
        PreferenceStorage.saveFatherEmail(getApplicationContext(), "");
        PreferenceStorage.saveFatherAddress(getApplicationContext(), "");
        PreferenceStorage.saveFatherOccupation(getApplicationContext(), "");
        PreferenceStorage.saveFatherIncome(getApplicationContext(), "");
        PreferenceStorage.saveFatherHomePhone(getApplicationContext(), "");
        PreferenceStorage.saveFatherMobile(getApplicationContext(), "");
        PreferenceStorage.saveFatherOfficePhone(getApplicationContext(), "");
        PreferenceStorage.saveFatherRelationship(getApplicationContext(), "");
        PreferenceStorage.saveFatherImg(getApplicationContext(), "");

        PreferenceStorage.saveMotherID(getApplicationContext(), "");
        PreferenceStorage.saveMotherName(getApplicationContext(), "");
        PreferenceStorage.saveMotherHomePhone(getApplicationContext(), "");
        PreferenceStorage.saveMotherEmail(getApplicationContext(), "");
        PreferenceStorage.saveMotherAddress(getApplicationContext(), "");
        PreferenceStorage.saveMotherOccupation(getApplicationContext(), "");
        PreferenceStorage.saveMotherIncome(getApplicationContext(), "");
        PreferenceStorage.saveMotherHomePhone(getApplicationContext(), "");
        PreferenceStorage.saveMotherMobile(getApplicationContext(), "");
        PreferenceStorage.saveMotherOfficePhone(getApplicationContext(), "");
        PreferenceStorage.saveMotherRelationship(getApplicationContext(), "");
        PreferenceStorage.saveMotherImg(getApplicationContext(), "");

        PreferenceStorage.saveGuardianID(getApplicationContext(), "");
        PreferenceStorage.saveGuardianName(getApplicationContext(), "");
        PreferenceStorage.saveGuardianHomePhone(getApplicationContext(), "");
        PreferenceStorage.saveGuardianEmail(getApplicationContext(), "");
        PreferenceStorage.saveGuardianAddress(getApplicationContext(), "");
        PreferenceStorage.saveGuardianOccupation(getApplicationContext(), "");
        PreferenceStorage.saveGuardianIncome(getApplicationContext(), "");
        PreferenceStorage.saveGuardianHomePhone(getApplicationContext(), "");
        PreferenceStorage.saveGuardianMobile(getApplicationContext(), "");
        PreferenceStorage.saveGuardianOfficePhone(getApplicationContext(), "");
        PreferenceStorage.saveGuardianRelationship(getApplicationContext(), "");
        PreferenceStorage.saveGuardianImg(getApplicationContext(), "");
    }
}
