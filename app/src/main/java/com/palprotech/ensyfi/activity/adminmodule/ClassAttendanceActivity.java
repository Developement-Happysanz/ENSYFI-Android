package com.palprotech.ensyfi.activity.adminmodule;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.bean.admin.support.StoreClassSectionId;
import com.palprotech.ensyfi.bean.admin.support.StoreTeacherId;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ClassAttendanceActivity extends AppCompatActivity implements DialogClickListener, IServiceListener, View.OnClickListener {

    private static final String TAG = ClassAttendanceActivity.class.getName();

    protected ProgressDialogHelper progressDialogHelper;
    private ServiceHelper serviceHelper;
    String Res = "";
    ArrayList<StoreClassSectionId> classesList = new ArrayList<>();
    LinearLayout classSpnList, classs, dateLayout;
    ScrollView sc;
    TextView date;
    RelativeLayout classListSpinner;
    Button gogo;
    private DatePickerDialog fromDatePickerDialog;
    final Calendar myCalendar = Calendar.getInstance();
    ArrayList<String> selectedclassesList = new ArrayList<>();
    String sendDate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_attendance_classwise);
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        classListSpinner = findViewById(R.id.classListSpinner);
        classSpnList = findViewById(R.id.class_sec_list);
        sc = findViewById(R.id.sc);
        date = findViewById(R.id.txtDateTime);
        dateLayout = findViewById(R.id.txtDateTime_layout);
        dateLayout.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, R.style.datePickerTheme ,new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
                String sendFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdfSend = new SimpleDateFormat(sendFormat, Locale.US);
                sendDate = sdfSend.format(myCalendar.getTime());
                date.setText(sdf.format(myCalendar.getTime()));
                GetClassData();
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        classs = findViewById(R.id.class_list_spinner);
        classs.setOnClickListener(this);
        initializeEventHelpers();

    }


    protected void initializeEventHelpers() {
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
    }


    private void GetClassData() {
        Res = "classdata";
        if (CommonUtils.isNetworkAvailable(this)) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.KEY_USER_ID, PreferenceStorage.getUserId(this));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_CLASS_SECTION;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);


        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
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
    public void onResponse(JSONObject response) {
        progressDialogHelper.hideProgressDialog();
        if (validateSignInResponse(response)) {
            try {
                if (Res.equalsIgnoreCase("classdata")) {
                    classesList.clear();
                    JSONArray getData = response.getJSONArray("classList");
                    int getLength = getData.length();
                    Log.d(TAG, "userData dictionary" + getData.toString());

                    String classId = "";
                    String className = "";

                    for (int i = 0; i < getLength; i++) {

                        classId = getData.getJSONObject(i).getString("class_sec_id");
                        className = getData.getJSONObject(i).getString("class_name");

                        classesList.add(new StoreClassSectionId(classId, className));
                    }

                    loadMembersList(getData.length());


                } else if (Res.equalsIgnoreCase("sendClass")) {
                    JSONArray getData = response.getJSONArray("attendence_list");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadMembersList(int memberCount) {

        try {
            selectedclassesList.clear();
            for (int c1 = 0; c1 < memberCount; c1++) {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100, 1.0f);
                params.setMargins(0, 1, 0, 1);

                LinearLayout cell = new LinearLayout(this);
                cell.setLayoutParams(params);
                cell.setPadding(0, 2, 0, 2);
                cell.setGravity(Gravity.CENTER_VERTICAL);
                cell.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                cell.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout cell1 = new LinearLayout(this);
                cell1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100, 1.0f));
                cell1.setPadding(0, 2, 0, 2);
                cell1.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                cell1.setOrientation(LinearLayout.HORIZONTAL);

//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100);
//                params.setMargins(01, 01, 0, 01);
//
//                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(250, 100);
//                params1.setMargins(01, 01, 01, 01);
//
//                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(140, 100);
//                params2.setMargins(01, 01, 0, 01);





                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(250, 100);
                params1.setMargins(2, 2, 2, 2);


                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100);
                params2.setMargins(0, 2, 40, 2);
                params2.gravity = Gravity.RIGHT;

//                TextView title = new TextView(this);
//                title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
//                title.setTextColor(Color.BLACK);
//                title.setText("Attendee Details " + c1);
//                title.setLayoutParams(params2);

                final TextView line1 = new TextView(this);
                line1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

                line1.setText(classesList.get(c1).getClassSectionName());
//                line1.setText("TESTTTTT");


                line1.setId(R.id.class_section_name);
                line1.requestFocusFromTouch();
                line1.setTextSize(18.0f);
                line1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                line1.setSingleLine(true);
                line1.setTextColor(Color.parseColor("#000000"));
                line1.setGravity(Gravity.CENTER);
                line1.setPadding(15, 0, 15, 0);
//                line1.setLayoutParams(params);

//                TextView line2 = new TextView(this);
//                line2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT, 2.0f));
//
//                line2.setText(classesList.get(c1).getId());
//
//                line2.setId(R.id.member_id_txt);
//                line2.setHint("Member Id");
//                line2.requestFocusFromTouch();
//                line2.setTextSize(18.0f);
//                line2.setAllCaps(true);
//                line2.setGravity(Gravity.CENTER);
//                line2.setSingleLine(true);
//                line2.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                line2.setTextColor(Color.parseColor("#000000"));
//                line2.setPadding(15, 0, 15, 0);
//                line2.setLayoutParams(params1);

                final ImageView line3 = new ImageView(this);
//                line3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));


                line3.setId(R.id.class_section_status);
                line3.setBackgroundColor(Color.parseColor("#FFFFFF"));

                line3.requestFocusFromTouch();
                line3.setPressed(true);
                line3.setImageResource(R.drawable.ic_unselect);
                line3.setPadding(0, 30, 50, 30);
                line3.setLayoutParams(params2);

                final int finalC = c1;
                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = classesList.get(finalC).getClassSectionId();
                        if (selectedclassesList.contains(id)) {
                            line3.setImageResource(R.drawable.ic_unselect);
                            selectedclassesList.remove(id);
                        } else {
                            line3.setImageResource(R.drawable.ic_select);
                            selectedclassesList.add(id);
                        }
                    }
                });

//                TextView border = new TextView(this);
//                border.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
//                border.setHeight(1);
//                border.setBackgroundColor(Color.BLACK);
                cell1.addView(line3);
                cell.addView(line1);
                cell.addView(cell1);
//                cell.addView(border);

                classSpnList.addView(cell);
                classSpnList.setElevation(12.0f);
                classSpnList.setBackgroundColor(ContextCompat.getColor(this, R.color.background_grey));
            }
            gogo = new Button(this);
            gogo.setId(R.id.class_section_go);
            gogo.setBackground(ContextCompat.getDrawable(this, R.drawable.round_btn_color));
            gogo.setText("Go");

            LinearLayout.LayoutParams paramsgogo = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100);
            paramsgogo.setMargins(0, 20, 0, 20);

            LinearLayout.LayoutParams paramsgogolay = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsgogolay.setMargins(0, 0, 0, 0);
            gogo.setLayoutParams(paramsgogo);

            gogo.setTextColor(ContextCompat.getColor(this, R.color.white));
            gogo.setGravity(Gravity.CENTER);
            gogo.setPadding(80, 0, 80, 0);
            gogo.setOnClickListener(this);

            LinearLayout cell = new LinearLayout(this);
            cell.setLayoutParams(paramsgogolay);
            cell.setPadding(0, 2, 0, 2);
            cell.setGravity(Gravity.CENTER);
            cell.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            cell.setOrientation(LinearLayout.HORIZONTAL);

            cell.addView(gogo);
            classSpnList.addView(cell);
            classSpnList.setGravity(Gravity.CENTER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onClick(View view) {
        if (view == classs) {
            if (!Res.isEmpty() || !Res.equalsIgnoreCase("")) {
                sc.setVisibility(View.VISIBLE);
                sc.setElevation(12.0f);
                classListSpinner.setVisibility(View.GONE);
            } else {
                AlertDialogHelper.showSimpleAlertDialog(this, "Please select the date first");
            }
        }
        if (view == gogo) {
            if (!selectedclassesList.isEmpty()) {
                Intent i = new Intent(this, ClassAttendanceListActivity.class);
                i.putExtra("date", sendDate);
                i.putExtra("class", selectedclassesList);
                startActivity(i);
                finish();
            } else {
                AlertDialogHelper.showSimpleAlertDialog(this, "Please select the class");
            }
        }
        if (view == dateLayout) {
            fromDatePickerDialog.show();
        }
    }
}
