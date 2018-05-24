package com.zoftcode.yupana;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

public class Splash extends Activity {

    //Duracion en milisegindos del splash
    private final int DuracionSplash = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                //Cuando pasen los 3 segundos pasamos a la actividad correspondiente
                Intent intent = new Intent(Splash.this,ListaFunciones.class);
                startActivity(intent);
                finish();
            };
        },DuracionSplash);

    }

}

