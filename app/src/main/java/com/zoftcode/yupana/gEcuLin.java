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

public class gEcuLin extends AppCompatActivity {

    private ListView Opciones;
    private String[] temas = {"Estructuras Algebraicas" , "Sistemas Lineales", "Matrices"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_ecu_lin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Opciones = (ListView)findViewById(R.id.gEcuLin);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,temas);
        Opciones.setAdapter(adaptador);

        Opciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0 :
                        Intent estAlg = new Intent(gEcuLin.this,g_21EstAlg.class);
                        startActivity(estAlg);
                        break;

                    case 1 :
                        Intent sisLin = new Intent(gEcuLin.this,g_22SisLin.class);
                        startActivity(sisLin);
                        break;

                    case 2 :
                        Intent mat = new Intent(gEcuLin.this,g_23Mat.class);
                        startActivity(mat);
                        break;

                }
            }
        });



    }

}
