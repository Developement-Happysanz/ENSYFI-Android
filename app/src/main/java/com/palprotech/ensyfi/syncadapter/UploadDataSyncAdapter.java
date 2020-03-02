package com.palprotech.ensyfi.syncadapter;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.palprotech.ensyfi.R;
import com.palprotech.ensyfi.activity.general.SyncRecordsActivity;
import com.palprotech.ensyfi.activity.teachermodule.SyncAcademicExamMarks;
import com.palprotech.ensyfi.activity.teachermodule.SyncClassTestHomeWork;
import com.palprotech.ensyfi.activity.teachermodule.SyncClassTestMark;


public class UploadDataSyncAdapter extends AbstractThreadedSyncAdapter {

    //TODO change this constant SYNC_INTERVAL to change the sync frequency
    public static final int SYNC_INTERVAL = 60; //60 * 180;       // 60 seconds (1 minute) * 180 = 3 hours
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

    ContentResolver contentResolver;


    public UploadDataSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        contentResolver = context.getContentResolver();
    }

    public UploadDataSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        contentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.i("UploadDataSyncAdapter", "onPerformSync");
        SyncClassTestHomeWork syncClassTestHomeWork = new SyncClassTestHomeWork(getContext());
        SyncClassTestMark syncClassTestMark = new SyncClassTestMark(getContext());
        SyncAcademicExamMarks syncAcademicExamMarks = new SyncAcademicExamMarks(getContext());
        SyncRecordsActivity syncRecordsActivity = new SyncRecordsActivity();
        syncRecordsActivity.syncAttendancePreRecord();
        syncClassTestHomeWork.syncClassTestHomeWorkRecords();
        syncClassTestHomeWork.syncClassTestHomeWorkRecords();
        syncClassTestMark.syncClassTestMarkToServer();
        syncAcademicExamMarks.SyncAcademicMarks();
    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder()
                    .syncPeriodic(syncInterval, flexTime)
                    .setSyncAdapter(account, authority)
                    .setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account, authority, new Bundle(), syncInterval);
        }
    }

    public static void syncImmediately(Context context) {
        Log.i("MyServiceSyncAdapter", "syncImmediately");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.content_authority), bundle);
    }

    public static Account getSyncAccount(Context context) {
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE); // Get an instance of the Android account manager
        Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type)); // Create the account type and default account

        // If the password doesn't exist, the account doesn't exist
        if (accountManager.getPassword(newAccount) == null) {
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                Log.e("MyServiceSyncAdapter", "getSyncAccount Failed to create new account.");
                return null;
            }
            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        Log.i("MyServiceSyncAdapter", "onAccountCreated");
        UploadDataSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        Log.d("MyServiceSyncAdapter", "initializeSyncAdapter");
        getSyncAccount(context);
    }
}
