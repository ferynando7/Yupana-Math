package com.zoftcode.yupana;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.DoubleBuffer;
import java.util.ArrayList;

import java.util.ArrayList;

public class Estadistica extends AppCompatActivity {

    private EditText data;
    private TextView media, mediana, moda, varianza;
    private Button hallar, reset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        data = (EditText) findViewById(R.id.etData);


        media = (TextView) findViewById(R.id.tvMedia);
        mediana = (TextView) findViewById(R.id.tvMediana);
        moda = (TextView) findViewById(R.id.tvModa);
        varianza = (TextView) findViewById(R.id.tvVarianza);


        hallar = (Button) findViewById(R.id.btnHallarV);
        reset = (Button) findViewById(R.id.btnReset);

        hallar.setOnClickListener(new View.OnClickListener() {
            String dataString = "";

            @Override
            public void onClick(View v) {

                dataString = data.getText().toString();

                try {
                    media.setText(String.valueOf(mean(dataString)));
                    mediana.setText(String.valueOf(median(dataString)));
                    moda.setText(String.valueOf(mode(dataString)));
                    varianza.setText(String.valueOf(variance(dataString)));
                }catch (Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Formato inválido...", Toast.LENGTH_SHORT);

                    toast.show();
                }

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciar();
            }
        });
    }


    public void reiniciar(){
        data.setText("");
        media.setText("");
        mediana.setText("");
        moda.setText("");
        varianza.setText("");
    }


    public double[] toDouble(String cadena){
        String[] datos = cadena.split(",");
        double[] dataDouble = new double[datos.length];
        for (int i=0; i<datos.length;i++){
            dataDouble[i] = (Double.parseDouble(datos[i]));
        }
        return dataDouble;
    }

    public float mean(String data) throws Exception{

            double result=0;
            float div;

            double[] dataDouble = toDouble(data);

            for(double item:dataDouble){
                result +=item;
            }
            div= (float)result/(float)dataDouble.length;


            return (float)redondearDecimales(div,2);
    }

    public double median(String data) throws Exception{
        double mediana;
        int longitud;

        double[] dataDouble = toDouble(data);
        QuickSort(dataDouble,0,dataDouble.length-1);

        longitud = dataDouble.length;

        if(dataDouble.length%2==0){
            mediana = ((dataDouble[longitud/2]) + ((dataDouble[(longitud/2)-1])))/2;
        }else{
            mediana = dataDouble[longitud/2];
        }

        return mediana;
    }

    public String mode(String data) throws Exception{
        StringBuilder modales = new StringBuilder();
        ArrayList<Double> modas = new ArrayList<Double>();
        int cont1,cont2;

        double[] dataDouble = toDouble(data);
        QuickSort(dataDouble,0,dataDouble.length-1);

        cont1 = 1;
        cont2 = 0;
        modas.add(dataDouble[0]); //añado el primer elemento a la lista de modas

        for(int i = 1; i<dataDouble.length;i++){

            if(dataDouble[i] == dataDouble[i-1]){ //si el dato actual es igual al dato anterior entonces:
                cont1++;
            }else{ //Si son diferentes entonces tenemos 3 opciones
                if (cont1==cont2){  //Las frecuencias de los datos anteriores son iguales
                    modas.add(dataDouble[i-1]);
                    cont2=cont1;
                    cont1=1;
                }else if(cont1>cont2){ //la frecuencia del dato actual es mayor que el anterior
                    modas.clear();
                    modas.add(dataDouble[i-1]);
                    cont2=cont1;
                    cont1=1;
                }else{ //la frecuencia del dato actual es menor que el del anterior
                    cont1=1;
                }
            }
        }

        /**Analisis de la ultima posicion del arreglo dataDouble*/
        if(cont1==cont2){
            modas.add(dataDouble[dataDouble.length-1]);
        }else if(cont1>cont2){
            modas.clear();
            modas.add(dataDouble[dataDouble.length-1]);
        }

        for(int i=0; i<modas.size(); i++){
            modales.append(modas.get(i));
            if(i!=modas.size()-1){
                modales.append(",");
            }
        }

        return modales.toString();
    }

    public String variance(String data) throws Exception{
        String var = "";

        double variance = 0;

        double[] dataDouble = toDouble(data);

        double mean = mean(data);

        for (int i = 0; i<dataDouble.length;i++){

            variance+= Math.pow(dataDouble[i]-mean,2);
        }



        variance = variance/dataDouble.length;
        variance = redondearDecimales(variance,2);


        return String.valueOf(variance);



    }

    /**QUICKSORT: algoritmo de ordenamiento mas efectivo existente*/
    public void QuickSort(double[] datos, int left, int right){
        int mitad;

        if(right>left){
            mitad = Partition(datos, left, right);
            QuickSort(datos, left, mitad-1);
            QuickSort(datos, mitad+1,right);
        }
    }

    public int Partition(double[] datos, int left, int right) {
        int p,s;
        double temp;
        p = left;

        while (left < right) {
            while (left <= right && datos[left] <= datos[p]) {
                left++;
            }
            while (left <= right && datos[right] > datos[p]) {
                right--;
            }

            if (left<right){
                temp = datos[left];
                datos[left] = datos[right];
                datos[right] = temp;
            }
        }
        temp = datos[p];
        datos[p] = datos[right];
        datos[right] = temp;

        s = right;
        return s;
    }
    /*************************************************************/

    public String Imprimir(double[] datos){

        StringBuilder res = new StringBuilder();

        for(double item:datos){
            res.append(String.valueOf(item));
            res.append(",");
        }

        return res.toString();

    }

    public double redondearDecimales(double valorInicial, int numeroDecimales) {

        double parteEntera, resultado;
        if(valorInicial<0){
            valorInicial=-valorInicial;

            resultado = valorInicial;
            parteEntera = Math.floor(resultado);
            resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
            resultado=Math.round(resultado);
            resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
            resultado=-resultado;

        }else {

            resultado = valorInicial;
            parteEntera = Math.floor(resultado);
            resultado = (resultado - parteEntera) * Math.pow(10, numeroDecimales);
            resultado = Math.round(resultado);
            resultado = (resultado / Math.pow(10, numeroDecimales)) + parteEntera;
        }
        return resultado;
    }

}
