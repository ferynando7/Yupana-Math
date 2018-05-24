package com.zoftcode.yupana;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

public class ListaFunciones extends AppCompatActivity {

    ImageButton calcu, solPoli, evaPoli, sisEcu, estad, guiaT, ayuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_funciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calcu = (ImageButton) findViewById(R.id.ibCalcu);
        solPoli = (ImageButton) findViewById(R.id.ibSolPoli);
        evaPoli = (ImageButton) findViewById(R.id.ibEvaPoli);
        sisEcu = (ImageButton) findViewById(R.id.ibSisEcu);
        estad = (ImageButton) findViewById(R.id.ibEstad);
        guiaT = (ImageButton) findViewById(R.id.ibGuiaT);
        //ayuda = (ImageButton) findViewById(R.id.ibAyuda);

        calcu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListaFunciones.this, calcuScreen.class);
                startActivity(i);
            }
        });

        solPoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListaFunciones.this, EncontrarRaices.class);
                startActivity(i);
            }
        });

        evaPoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListaFunciones.this, EvaluarPolinomio.class);
                startActivity(i);
            }
        });

        estad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListaFunciones.this, Estadistica.class);
                startActivity(i);
            }
        });

        sisEcu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListaFunciones.this, SistemaEcuaciones.class);
                startActivity(i);
            }
        });

//        ayuda.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(ListaFunciones.this, Help.class);
//                startActivity(i);
//            }
//        });

        guiaT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListaFunciones.this, GuiaTeorica.class);
                startActivity(i);
            }
        });




    }

}
