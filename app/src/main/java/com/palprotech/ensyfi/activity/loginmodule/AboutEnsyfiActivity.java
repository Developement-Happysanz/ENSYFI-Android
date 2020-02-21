package com.palprotech.ensyfi.activity.loginmodule;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.palprotech.ensyfi.R;

public class AboutEnsyfiActivity extends AppCompatActivity {

    private static final String TAG = AboutEnsyfiActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_ensyfi);
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        WebView wb = (WebView) findViewById(R.id.webView1);
//
//
//            wb.loadUrl("https://www.skilex.in");
//            wb.setWebViewClient(new WebViewClient(){
//
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url){
//                    view.loadUrl(url);
//                    return true;
//                }
//            });
    }

}