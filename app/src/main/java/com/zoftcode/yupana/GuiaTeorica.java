package com.zoftcode.yupana;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GuiaTeorica extends AppCompatActivity {

    private ListView Opciones;
    private String[] temas = {"Ecuaciones Lineales"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guia_teorica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Opciones = (ListView)findViewById(R.id.Opciones);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,temas);
        Opciones.setAdapter(adaptador);

        Opciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0 :
                        Intent ecuLin = new Intent(GuiaTeorica.this,gEcuLin.class);
                        startActivity(ecuLin);
                        break;
                }
            }
        });



    }

}
