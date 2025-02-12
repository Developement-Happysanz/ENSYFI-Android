package com.palprotech.ensyfi.activity.studentmodule;

import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.adapter.studentmodule.ClassTestListAdapter;
import com.palprotech.ensyfi.bean.student.viewlist.ClassTest;
import com.palprotech.ensyfi.bean.student.viewlist.ClassTestList;
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
import java.util.List;

/**
 * Created by Narendar on 07/04/17.
 */

public class ClassTestHomeworkActivity extends AppCompatActivity implements IServiceListener, AdapterView.OnItemClickListener, DialogClickListener {

    private static final String TAG = "ClassTestHomework";
    ListView loadMoreListView;
    private Spinner spnClassList, spnClassTestHomeWork;
    ClassTestListAdapter classTestListAdapter;
    private ServiceHelper serviceHelper;
    ArrayList<ClassTest> classTestArrayList;
    int totalCount = 0, checkrun = 0;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();
    private RadioGroup radioClassHome;
    String ClassTestOrHomeWork = "";
    String classSection;
    private String isHomeWorkType = "HT";
    private List<String> mClassTestHomeWorkList = new ArrayList<String>();
    String classTestHomeWork = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classtest_homework);
        loadMoreListView = (ListView) findViewById(R.id.listView_events);
        loadMoreListView.setOnItemClickListener(this);
        classTestArrayList = new ArrayList<>();
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);

        spnClassTestHomeWork = (Spinner) findViewById(R.id.class_test_homework_spinner);

        radioClassHome = (RadioGroup) findViewById(R.id.radioClassHome);

        callGetClassTestService();

        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mClassTestHomeWorkList.add("Class Test");
        mClassTestHomeWorkList.add("Home Work");

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item_ns, mClassTestHomeWorkList);

        spnClassTestHomeWork.setAdapter(dataAdapter3);
        spnClassTestHomeWork.setWillNotDraw(false);

        spnClassTestHomeWork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (++checkrun > 1) {
                    classTestHomeWork = parent.getItemAtPosition(position).toString();
                    if (classTestHomeWork.equalsIgnoreCase("Class Test")) {
                        isHomeWorkType = "HT";
                        callGetClassTestService();
                    } else {
                        isHomeWorkType = "HW";
                        callGetClassTestService();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        radioClassHome.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                switch (checkedId) {
//                    case R.id.radioClassTest:
//                        isHomeWorkType = "HT";
//                        callGetClassTestService();
//                        break;
//                    case R.id.radioHomeWork:
//                        isHomeWorkType = "HW";
//                        callGetClassTestService();
//                        break;
//                }
//            }
//        });
    }

    public void callGetClassTestService() {
        if (classTestArrayList != null)
            classTestArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            new HttpAsyncTask().execute("");
        } else {
            AlertDialogHelper.showSimpleAlertDialog(this, "No Network connection");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onEvent list item click" + position);
        ClassTest classTest = null;
        if ((classTestListAdapter != null) && (classTestListAdapter.ismSearching())) {
            Log.d(TAG, "while searching");
            int actualindex = classTestListAdapter.getActualEventPos(position);
            Log.d(TAG, "actual index" + actualindex);
            classTest = classTestArrayList.get(actualindex);
        } else {
            classTest = classTestArrayList.get(position);
        }
        Intent intent = new Intent(this, ClassTestDetailActivity.class);
        intent.putExtra("eventObj", classTest);
        startActivity(intent);
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
            Log.d("ajazFilterresponse : ", response.toString());

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    progressDialogHelper.hideProgressDialog();

                    Gson gson = new Gson();
                    ClassTestList classTestsList = gson.fromJson(response.toString(), ClassTestList.class);
                    if (classTestsList.getClassTest() != null && classTestsList.getClassTest().size() > 0) {
                        totalCount = classTestsList.getCount();
                        isLoadingForFirstTime = false;
                        updateListAdapter(classTestsList.getClassTest());
                    }
                }
            });
        } else {
            Log.d(TAG, "Error while sign In");
        }
    }

    protected void updateListAdapter(ArrayList<ClassTest> classTestArrayList) {
        this.classTestArrayList.addAll(classTestArrayList);
        if (classTestListAdapter == null) {
            classTestListAdapter = new ClassTestListAdapter(this, this.classTestArrayList);
            loadMoreListView.setAdapter(classTestListAdapter);
        } else {
            classTestListAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onError(final String error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progressDialogHelper.hideProgressDialog();
                AlertDialogHelper.showSimpleAlertDialog(ClassTestHomeworkActivity.this, error);
            }
        });
    }

    @Override
    public void onAlertPositiveClicked(int tag) {

    }

    @Override
    public void onAlertNegativeClicked(int tag) {

    }


    private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EnsyfiConstants.PARAM_CLASS_ID, PreferenceStorage.getStudentClassIdPreference(getApplicationContext()));
                jsonObject.put(EnsyfiConstants.PARM_HOME_WORK_TYPE, isHomeWorkType);
                jsonObject.put(EnsyfiConstants.KEY_USER_DYNAMIC_DB, PreferenceStorage.getUserDynamicDB(getApplicationContext()));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + EnsyfiConstants.GET_STUDENT_CLASSTEST_AND_HOMEWORK_API;
            serviceHelper.makeGetServiceCall(jsonObject.toString(), url);

            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void result) {
            progressDialogHelper.cancelProgressDialog();
        }
    }

}
