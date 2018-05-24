package com.zoftcode.yupana;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

public class g_22SisLin extends AppCompatActivity {

    private WebView teoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_22_sis_lin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        teoria = (WebView) findViewById(R.id.wbSisLin);
        teoria.getSettings().setJavaScriptEnabled(true);
        teoria.getSettings().setBuiltInZoomControls(true);
        teoria.loadUrl("file:///android_asset/mathscribe/SL.html");

    }

}
