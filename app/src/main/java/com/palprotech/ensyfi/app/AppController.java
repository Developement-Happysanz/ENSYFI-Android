package com.palprotech.ensyfi.app;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
//import com.facebook.FacebookSdk;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.palprotech.ensyfi.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class AppController extends android.support.multidex.MultiDexApplication  {

    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;
    com.nostra13.universalimageloader.core.ImageLoader universalImageLoader;
    private DisplayImageOptions logoDisplayOptions;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        FacebookSdk.sdkInitialize(getApplicationContext());
        getHashKey();
    }

    public com.nostra13.universalimageloader.core.ImageLoader getUniversalImageLoader() {
        if (universalImageLoader == null) {
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisk(true).resetViewBeforeLoading(true).showImageOnLoading(R.drawable.ab_logo)
                    .showImageForEmptyUri(R.drawable.ab_logo)
                    .build();
            // Initialize ImageLoader with configuration.
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                    .defaultDisplayImageOptions(options)
                    .threadPoolSize(3)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .memoryCacheSize(1500000) // 1.5 Mb
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .build();
            universalImageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
            universalImageLoader.init(config);
        }
        return universalImageLoader;
    }
    public DisplayImageOptions getLogoDisplayOptions() {
        if (logoDisplayOptions == null) {
            logoDisplayOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisk(true).resetViewBeforeLoading(true).showImageOnLoading(R.drawable.ab_logo)
                    .showImageForEmptyUri(R.drawable.ab_logo)
                    .showImageOnFail(R.drawable.ab_logo)
                    .build();
        }
        return logoDisplayOptions;
    }


    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    private void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY_KEY_HASH:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } }


}
