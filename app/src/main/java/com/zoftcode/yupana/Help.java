package com.zoftcode.yupana;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import java.util.Locale;

public class Help extends AppCompatActivity {

    private WebView helpContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        helpContent= (WebView) findViewById(R.id.help_page);
        helpContent.getSettings().setJavaScriptEnabled(true);
        helpContent.getSettings().setBuiltInZoomControls(true);

        try{
            if(Locale.getDefault().getLanguage().equals("es")){
                helpContent.loadUrl("file:///android_asset/mathscribe/Ayuda.html");
            }else if(Locale.getDefault().getLanguage().equals("en")){
                helpContent.loadUrl("file:///android_asset/mathscribe/Help.html");
            }

        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }
}