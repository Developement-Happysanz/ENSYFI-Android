package com.palprotech.ensyfi.activity.studentmodule;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;

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

/**
 * Created by Narendar on 07/04/17.
 */

public class ClassTestHomeworkActivity extends AppCompatActivity implements IServiceListener, AdapterView.OnItemClickListener, DialogClickListener {

    private static final String TAG = "ClassTestHomework";
    ListView loadMoreListView;
    View view;
    ClassTestListAdapter classTestListAdapter;
    private ServiceHelper serviceHelper;
    ArrayList<ClassTest> classTestArrayList;
    int pageNumber = 0, totalCount = 0;
    protected ProgressDialogHelper progressDialogHelper;
    protected boolean isLoadingForFirstTime = true;
    Handler mHandler = new Handler();
    private SearchView mSearchView = null;
    private RadioGroup radioClassHome;
    private String isHomeWorkType = "HT";
//    private SwitchCompat switcherClassTest, switcherHomeWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classtest_homework);
        loadMoreListView = (ListView) findViewById(R.id.listView_events);
//        loadMoreListView.setOnLoadMoreListener(this);
        loadMoreListView.setOnItemClickListener(this);
        classTestArrayList = new ArrayList<>();
        serviceHelper = new ServiceHelper(this);
        serviceHelper.setServiceListener(this);
        progressDialogHelper = new ProgressDialogHelper(this);
        radioClassHome = (RadioGroup) findViewById(R.id.radioClassHome);

        callGetClassTestService();
        ImageView bckbtn = (ImageView) findViewById(R.id.back_res);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        radioClassHome.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radioClassTest:
//                        RadioButton value = Integer.parseInt(((RadioButton) findViewById(R.id.radioClassTest).getText()) * 3);
//                        Toast.makeText(getApplicationContext(), "Class Test", Toast.LENGTH_SHORT).show();
                        isHomeWorkType = "HT";
                        callGetClassTestService();
                        break;
                    case R.id.radioHomeWork:
//                        RadioButton value1 = Integer.parseInt(((RadioButton) findViewById(R.id.radioClassHome).getText()) * 3);
//                        Toast.makeText(getApplicationContext(), "Home Work", Toast.LENGTH_SHORT).show();
                        isHomeWorkType = "HW";
                        callGetClassTestService();
                        break;

                }
            }
        });

//        switcherClassTest = (SwitchCompat) findViewById(R.id.switch_test);
//        switcherHomeWork = (SwitchCompat) findViewById(R.id.switch_homwork);
       /* switcherClassTest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);
                Toast.makeText(getApplicationContext(),"Switch State 1=" + isChecked,Toast.LENGTH_SHORT).show();
                //
//                switcherHomeWork.setChecked(true);
            }

        });


        switcherHomeWork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);
                Toast.makeText(getApplicationContext(),"Switch State 2=" + isChecked,Toast.LENGTH_SHORT).show();
                //
//                switcherClassTest.setChecked(true);
            }

        }); */


    }

    public void callGetClassTestService() {
        /*if(eventsListAdapter != null){
            eventsListAdapter.clearSearchFlag();
        }*/
        if (classTestArrayList != null)
            classTestArrayList.clear();

        if (CommonUtils.isNetworkAvailable(this)) {
            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            //    eventServiceHelper.makeRawRequest(FindAFunConstants.GET_ADVANCE_SINGLE_SEARCH);
            new HttpAsyncTask().execute("");
        } else {
//            AlertDialogHelper.showSimpleAlertDialog(this, getString(R.string.no_connectivity));
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
        // intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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
//                loadMoreListView.onLoadMoreComplete();

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
//                loadMoreListView.onLoadMoreComplete();
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


            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialogHelper.showProgressDialog(getString(R.string.progress_loading));
            String url = EnsyfiConstants.BASE_URL + PreferenceStorage.getInstituteCode(getApplicationContext()) + EnsyfiConstants.GET_STUDENT_CLASSTEST_AND_HOMEWORK_API;
            serviceHelper.makeGetServiceCall(jsonObject.toString(),url);

            return null;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Void result) {
            progressDialogHelper.cancelProgressDialog();
        }
    }

}
