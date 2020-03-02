package com.palprotech.ensyfi.syncadapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyAuthenticatorService extends Service {

    private MyAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        Log.d("MyAuthenticatorService", "onCreate");
        // Create a new authenticator object
        mAuthenticator = new MyAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MyAuthenticatorService", "onBind");
        return mAuthenticator.getIBinder();
    }
}
