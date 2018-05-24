package com.zoftcode.yupana;




import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.complejo.Complejo;

import static com.zoftcode.yupana.EncontrarRaices.fromHtml;
import static com.zoftcode.yupana.EncontrarRaices.pxFromDp;

public class EvaluarPolinomio extends AppCompatActivity {

    boolean isEverythingOk = true; //Sirve para el alertDialog
    String coeficientes;
    int contPares = 1000, contFila = 100;
    int gradoMax;
    Button OK, reset, evaluar, añadir, quitar;
    EditText grado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluar_polinomio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        OK = (Button) findViewById(R.id.btnOK);
        evaluar = (Button) findViewById(R.id.btnEvaluar);
        grado = (EditText) findViewById(R.id.etGrado);
        reset = (Button) findViewById(R.id.btnReset);
        añadir = (Button) findViewById(R.id.btnAnadir);
        quitar = (Button) findViewById(R.id.btnQuitar);

        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidOK()) {
                    quitarTerminos();
                    OK.setVisibility(View.GONE);
                    crearNodo();
                    int grade = Integer.parseInt(grado.getText().toString());
                    gradoMax = grade;
                    for (int i = grade; i >= 0; i--) {
                        crearTerminos(i);
                    }
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciar();
            }
        });


        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearNodo();
            }
        });

        quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quitarNodo();
            }

        });


        evaluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(isValidObjects()) {
                coeficientes = obtnrCoefs(gradoMax,false);
                if (coeficientes.equals("existenCeros")) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(EvaluarPolinomio.this).create(); //Read Update
                    alertDialog.setTitle("¡Aviso!");
                    alertDialog.setMessage("Existen campos vacíos en el POLINOMIO, ¿desea rellenarlos con CEROS y continuar?");

                    alertDialog.setButton(Dialog.BUTTON_POSITIVE,"Sí", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            coeficientes = obtnrCoefs(gradoMax, true);
                            wrapperEvalPol();
                        }
                    });

                    alertDialog.setButton(Dialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                        }
                    });

                    alertDialog.show();  //<-- See This!
                } else {
                    wrapperEvalPol();
                }
            }
        }});

    }


    //funcion wrapper de la evaluacion del polinomio
    public void wrapperEvalPol(){
        double valorX;
        double yValue;
        String contentX;
        EditText puntoX;
        TextView puntoY;

        for (int i = contPares; i >= 1001; i--) {

            puntoX = (EditText) findViewById(i);

            contentX = puntoX.getText().toString();

            if (contentX.equals("")) {
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Existen puntos sin rellenar", Toast.LENGTH_SHORT);
                toast1.show();
            } else {
                valorX = Double.parseDouble(contentX);


                yValue = evalPol(coeficientes, valorX);

                puntoY = (TextView) findViewById(i + 1000);
                puntoY.setText(String.valueOf(yValue));
            }
        }
    }




    ////////////////////////////////////////////////////
    //Funcion de reconocimeinto del polinomio

    public double evalPol(String coef, double punto) {

        String[] coefs = coef.split(",");
        double[] coefsnum = new double[coefs.length];

        for (int i = 0; i < coefs.length; i++) {
            coefsnum[i] = Double.parseDouble(coefs[i]);
        }


        int exponente = 0;
        double suma = 0;

        for (int contador = coefsnum.length-1; contador >= 0; contador--) {

            suma = suma + coefsnum[contador] * Math.pow(punto, exponente);
            exponente = exponente +1;
        }

        return suma;
    }





    ////////////////////////////////////////////////////

    /**Obtiene los coeficientes del polinomio*/
    public String obtnrCoefs(int grado, boolean fillZeros){

        boolean flag = true;
        EditText coeficiente;
        StringBuilder coefs=new StringBuilder();
        String datoValido, coefsStr;

        for(int i=grado;i>=0 && flag;i--){
            coeficiente = (EditText) findViewById(i);
            datoValido = coeficiente.getText().toString();

            if (datoValido.equals("") && !fillZeros) { //Si el dato no es valido y no se pide llenar con ceros
                flag = false;
            }else if (datoValido.equals("") && fillZeros){
                datoValido = "0";
                coefs.append(datoValido);
                coefs.append(",");
            }else{
                coefs.append(datoValido);
                coefs.append(",");
            }
        }
        if(flag) {
            if (coefs.length() > 0) {
                coefs.setLength(coefs.length() - 1);
            }
            coefsStr = coefs.toString();
        }else{
            coefsStr = "existenCeros";
        }

        return coefsStr;
    }


    public void crearTerminos(int exponente) {
        /* Find Linearlayout defined in main.xml */
        LinearLayout Polinomio = (LinearLayout) findViewById(R.id.poly);


        /* Create an EditText to be the row-content. */
        EditText coeficiente = new EditText(EvaluarPolinomio.this);
        coeficiente.setId(exponente);
        coeficiente.setSingleLine();
        coeficiente.setInputType(InputType.TYPE_CLASS_NUMBER);
        coeficiente.setKeyListener(DigitsKeyListener.getInstance(true, true)); // decimals and positive/negative numbers.

        //defining textSize
        //float sourceTextSize = coeficiente.getTextSize();
        //coeficiente.setTextSize(sourceTextSize/getResources().getDisplayMetrics().density);

        coeficiente.setGravity(Gravity.CENTER);
        coeficiente.setBackgroundColor(Color.parseColor("#FFFFFF"));
        float width = pxFromDp(this, 40);
        coeficiente.setMinWidth(Math.round(width));

        LinearLayout.LayoutParams paramsCoef = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsCoef.setMargins(10, 10, 10, 10);
        coeficiente.setLayoutParams(paramsCoef);


        /* Create an TextView to be the row-content. */
        TextView variable = new TextView(EvaluarPolinomio.this);

        if (exponente==0){
            variable.setText(fromHtml("x<sup><small>"+String.valueOf(exponente)+"</small></sup>")); //using method fromHtml to avoid deprecation

        }else{
            variable.setText(fromHtml("x<sup><small>"+String.valueOf(exponente)+"</small></sup> + ")); //using method fromHtml to avoid deprecation

        }

        variable.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        variable.setGravity(Gravity.CENTER);
        variable.setTextColor(Color.parseColor("#000000"));
        LinearLayout.LayoutParams paramsVar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        variable.setLayoutParams(paramsVar);
        paramsVar.gravity = Gravity.CENTER;

        Polinomio.addView(coeficiente);
        Polinomio.addView(variable);

    }

    public void quitarTerminos(){
        LinearLayout Polinomio = (LinearLayout) findViewById(R.id.poly);

        if(Polinomio.getChildCount() > 0)
            Polinomio.removeAllViews();
    }




    public void crearNodo(){

        /* Find TableLayout defined in .xml */
        TableLayout tablaPuntos = (TableLayout) findViewById(R.id.tablaPuntos);

        TableRow fila = new TableRow(EvaluarPolinomio.this);
        fila.setId(++contFila);


        /* Create an TextView to be the row-content. */
        TextView xLabel = new TextView(EvaluarPolinomio.this);
        xLabel.setText(fromHtml("x<sub><small>"+String.valueOf(++contPares-1000)+"</small></sub>=")); //using method fromHtml to avoid deprecation
        xLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        xLabel.setGravity(Gravity.CENTER);
        xLabel.setTextColor(Color.parseColor("#000000"));
        TableRow.LayoutParams paramsXLabel= new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.MATCH_PARENT);
        xLabel.setLayoutParams(paramsXLabel);
        paramsXLabel.setMargins(10,0,0,0);
        paramsXLabel.gravity= Gravity.CENTER;


        /* Create an EditText to be the row-content. */
        EditText xValue = new EditText(EvaluarPolinomio.this);
        xValue.setId(contPares+0);
        xValue.setSingleLine();
        xValue.setInputType(InputType.TYPE_CLASS_NUMBER);
        xValue.setKeyListener(DigitsKeyListener.getInstance(true,true)); // decimals and positive/negative numbers.
        xValue.setGravity(Gravity.CENTER);
        xValue.setBackgroundColor(Color.parseColor("#FFFFFF"));
        float width = pxFromDp(this, 60);
        TableRow.LayoutParams paramsVNodo= new TableRow.LayoutParams(Math.round(width), TableRow.LayoutParams.MATCH_PARENT);
        paramsVNodo.setMargins(10,10,10,10);
        xValue.setLayoutParams(paramsVNodo);



        /* Create an TextView to be the row-content. */
        TextView yLabel = new TextView(EvaluarPolinomio.this);
        yLabel.setText(fromHtml("P(x<sub><small>"+String.valueOf(contPares-1000)+"</small></sub>)=")); //using method fromHtml to avoid deprecation
        yLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        yLabel.setGravity(Gravity.CENTER);
        yLabel.setTextColor(Color.parseColor("#000000"));
        TableRow.LayoutParams paramsXPx= new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.MATCH_PARENT);
        paramsXPx.setMargins(30,0,0,0);
        yLabel.setLayoutParams(paramsXPx);
        paramsXPx.gravity= Gravity.CENTER;


                /* Create an TextView to be the row-content. */
        TextView yValue = new TextView(EvaluarPolinomio.this);
        yValue.setId(contPares+1000);
        yValue.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        yValue.setGravity(Gravity.CENTER);
        yValue.setTextColor(Color.parseColor("#000000"));
        yValue.setBackgroundColor(Color.parseColor("#ffffff"));
        yValue.setMinWidth(Math.round(width));
        TableRow.LayoutParams paramsVPx= new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.MATCH_PARENT);
        paramsVPx.setMargins(10,10,0,10);
        paramsVPx.gravity= Gravity.CENTER;
        yValue.setLayoutParams(paramsVPx);


        fila.addView(xLabel);
        fila.addView(xValue);
        fila.addView(yLabel);
        fila.addView(yValue);

        tablaPuntos.addView(fila,0);
    }


    public void quitarNodo(){
        TableLayout puntos = (TableLayout) findViewById(R.id.tablaPuntos);
        TableRow fila = new TableRow(EvaluarPolinomio.this);
        if(puntos.getChildCount() > 0)
            fila = (TableRow) findViewById(contFila--);
            contPares--;
            puntos.removeView(fila);
    }


    public void reiniciar(){
        LinearLayout Polinomio = (LinearLayout) findViewById(R.id.poly);
        OK.setVisibility(View.VISIBLE);
        if(Polinomio.getChildCount() > 0)
            Polinomio.removeAllViews();

        EditText grado = (EditText) findViewById(R.id.etGrado);
        grado.setText("");

        TableLayout tablaPuntos = (TableLayout) findViewById(R.id.tablaPuntos);
        if(tablaPuntos.getChildCount() > 0)
            tablaPuntos.removeAllViews();

        contPares = 1000;
        contFila = 100;


    }

    public boolean isValidOK(){
        boolean flag = true;

        EditText grado;
        grado = (EditText) findViewById(R.id.etGrado);
        int intEq = Integer.parseInt(grado.getText().toString());

        String cadena = grado.getText().toString();

        if(cadena.equals("")){

            flag = false;
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Ingrese el grado de P(x)", Toast.LENGTH_SHORT);

            toast1.show();

        }else if(intEq < 2){
            flag = false;
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Mínimo grado 2", Toast.LENGTH_SHORT);
            toast1.show();

        }else if(intEq > 15){
            flag = false;
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Máximo grado 15", Toast.LENGTH_SHORT);
            toast1.show();
        }

        return flag;


    }
    public boolean isValidObjects(){
        boolean flag = true;

        LinearLayout Polinomio = (LinearLayout) findViewById(R.id.poly);

        if(Polinomio.getChildCount()==0){
            flag=false;
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Cree primero el polinomio", Toast.LENGTH_SHORT);
            toast1.show();
        }
        return flag;
    }



}













