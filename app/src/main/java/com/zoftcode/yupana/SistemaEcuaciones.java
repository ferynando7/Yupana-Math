package com.zoftcode.yupana;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.algebra.Matrix;

import static com.zoftcode.yupana.EncontrarRaices.fromHtml;
import static com.zoftcode.yupana.EncontrarRaices.pxFromDp;

public class SistemaEcuaciones extends AppCompatActivity {

    boolean isEverythingOk = false; //sirve para el alertDialog
    int contFila=0, contCol=0, contVInd = 1000;
    double[][] matriz;
    double[] vector;
    EditText nEq;
    Button OK, resolver, reset;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sistema_ecuaciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        nEq = (EditText) findViewById(R.id.etNEq);
        OK = (Button) findViewById(R.id.btnOK);
        resolver = (Button) findViewById(R.id.btnResolver2);
        reset = (Button) findViewById(R.id.btnReset2);

        result = (TextView) findViewById(R.id.tvResultado);


        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(isValidOK()){
                quitarTerminos();
                OK.setVisibility(View.GONE);
                int nEquations = Integer.parseInt(nEq.getText().toString());

                for (int i = 0; i < nEquations; i++){
                    crearFila(nEquations);
                }
            }
            }
        });


        resolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(isSystemCreated()) {
                final int nEquations = Integer.parseInt(nEq.getText().toString());

                matriz = getMatrix(nEquations,false);
                vector = getVector(nEquations, false);


                if(matriz.length == 1 || vector.length==1){
                    final AlertDialog alertDialog = new AlertDialog.Builder(SistemaEcuaciones.this).create(); //Read Update
                    alertDialog.setTitle("¡Aviso!");
                    alertDialog.setMessage("Existen campos vacíos, ¿desea rellenarlos con CEROS y continuar?");

                    alertDialog.setButton(Dialog.BUTTON_POSITIVE,"Sí", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            matriz = getMatrix(nEquations,true);
                            vector = getVector(nEquations, true);
                            wrapperSolveSystem(nEquations);
                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.setButton(Dialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                        }
                    });

                    alertDialog.show();  //<-- See This!
                }else{
                    wrapperSolveSystem(nEquations);
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

    }


    public void wrapperSolveSystem(int nEquations){
        Matrix A = new Matrix(matriz);
        Matrix b = new Matrix(vector);

        int tieneSol = A.tieneSol(b);

        if(tieneSol == 0){

            Toast toast =
                    Toast.makeText(getApplicationContext(),
                            "El sistema no tiene solución", Toast.LENGTH_SHORT);

            toast.show();

        }else if(tieneSol == 10){
            Toast toast =
                    Toast.makeText(getApplicationContext(),
                            "El sistema tiene infinitas soluciones", Toast.LENGTH_SHORT);

            toast.show();

        }else {

            Matrix X = solveSystem(A, b);

            for (int i = 0; i < nEquations; i++) {
                X.setPos(i, 0, redondearDecimales(X.getPos(i, 0), 2));
            }

            String finalRes = imprimirVector(X);

            result.setText(fromHtml(finalRes));
        }
    }

    public String imprimirVector(Matrix vector){
        int n = vector.dim()[0];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<n; i++){
            sb.append("x<sub><small>"+String.valueOf(i+1)+"</small></sub> = "
                    + String.valueOf(vector.getPos(i,0))+"<br/>");
        }

        return sb.toString();
    }



    public void quitarTerminos(){
        LinearLayout system = (LinearLayout) findViewById(R.id.sistema);

        if(system.getChildCount() > 0)
            system.removeAllViews();

        contFila=0;
        contCol=0;
        contVInd = 1000;
    }


    public void crearFila (int nVar){

        contCol = 100*contFila+1;
        LinearLayout sistema = (LinearLayout) findViewById(R.id.sistema);
        LinearLayout fila = new LinearLayout(SistemaEcuaciones.this);
        fila.setOrientation(LinearLayout.HORIZONTAL);
        fila.setId(contFila++);

        for (int i = 0; i<nVar;i++)
            crearTerminos(contCol++, fila);

        EditText vInd = new EditText(SistemaEcuaciones.this);
        vInd.setId(contVInd++);
        vInd.setSingleLine();
        vInd.setInputType(InputType.TYPE_CLASS_NUMBER);
        vInd.setKeyListener(DigitsKeyListener.getInstance(true,true)); // decimals and positive/negative numbers.

        vInd.setGravity(Gravity.CENTER);
        vInd.setBackgroundColor(Color.parseColor("#FFFFFF"));
        vInd.setImeOptions(EditorInfo.IME_ACTION_DONE);

        float width = pxFromDp(this, 30);

        vInd.setMinWidth(Math.round(width));
        LinearLayout.LayoutParams paramsVInd= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsVInd.setMargins(10,10,10,10);
        vInd.setLayoutParams(paramsVInd);

        fila.addView(vInd);

        sistema.addView(fila);
    }




    public void crearTerminos(int indice, LinearLayout fila){

            /* Create an EditText to be the row-content. */
        EditText coeficiente = new EditText(SistemaEcuaciones.this);
        coeficiente.setId(indice);
        coeficiente.setSingleLine();
        coeficiente.setInputType(InputType.TYPE_CLASS_NUMBER);
        coeficiente.setKeyListener(DigitsKeyListener.getInstance(true,true)); // decimals and positive/negative numbers.


        coeficiente.setGravity(Gravity.CENTER);
        coeficiente.setBackgroundColor(Color.parseColor("#FFFFFF"));

        if (indice==0){
            coeficiente.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }else{
            coeficiente.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        }

        float width = pxFromDp(this, 30);

        coeficiente.setMinWidth(Math.round(width));
        LinearLayout.LayoutParams paramsCoef= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsCoef.setMargins(10,10,10,10);
        coeficiente.setLayoutParams(paramsCoef);


            /* Create an TextView to be the row-content. */
        TextView variable = new TextView(SistemaEcuaciones.this);

        int nVari = Integer.parseInt(nEq.getText().toString());
        //if (indice==100*contFila+1+nVari){
        Log.d("Indice", String.valueOf(indice));
        Log.d("Valor", String.valueOf(contFila* 100 + nVari-1));


        if (indice==(contFila-1)* 100 + nVari){
            variable.setText(Html.fromHtml("x<sub><small>"+String.valueOf(indice%100)+"</small></sub> = ")); //using method fromHtml to avoid deprecation

        }else{
            variable.setText(fromHtml("x<sub><small>"+String.valueOf(indice%100)+"</small></sub> + ")); //using method fromHtml to avoid deprecation

        }
        variable.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        variable.setGravity(Gravity.CENTER);
        variable.setTextColor(Color.parseColor("#ffffff"));
        LinearLayout.LayoutParams paramsVar= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        variable.setLayoutParams(paramsVar);
        paramsVar.gravity= Gravity.CENTER;

        fila.addView(coeficiente);
        fila.addView(variable);

    }


    public void reiniciar(){
        LinearLayout sistema = (LinearLayout) findViewById(R.id.sistema);
        OK.setVisibility(View.VISIBLE);
        if(sistema.getChildCount() > 0)
            sistema.removeAllViews();

        EditText numEq = (EditText) findViewById(R.id.etNEq);
        numEq.setText("");


        TextView resultado = (TextView) findViewById(R.id.tvResultado);
        resultado.setText("");

        contFila=0;
        contCol=0;
        contVInd = 1000;

    }




    double[][] getMatrix( int nEq, boolean fillZeros){
        double[][] matriz = new double[nEq][nEq];

        for (int i=0;i<contFila;i++){
            EditText value;
            int col =0;
            for(int j = i*100+1; j< i*100+nEq+1;j++){

                value = (EditText) findViewById(j);
                String datoValido = value.getText().toString();
                if(datoValido.equals("") && !fillZeros){
                    matriz = new double[][] {{0}};
                    return matriz;
                }else if(datoValido.equals("") && fillZeros){
                    matriz[i][col] = 0;
                    col++;
                }else {

                    Log.d("i", String.valueOf(i));
                    Log.d("col", String.valueOf(col));

                    matriz[i][col] = Double.parseDouble(datoValido);
                    col++;
                }
            }
        }

        return matriz;
    }

    double[] getVector( int nEq, boolean fillZeros){
        double[] vector = new double[nEq];

            Log.d("sIZEVECTOR", String.valueOf(nEq));

        for (int i=1000;i<1000+contFila;i++) {
            EditText value;
            value = (EditText) findViewById(i+0);
            String datoValido = value.getText().toString();

            if(datoValido.equals("") && !fillZeros){
                vector = new double[]{0};
                return vector;
            }else if(datoValido.equals("") && fillZeros){
                vector[i-1000] = 0;
            }else{
                vector[i-1000] = Double.parseDouble(datoValido);
            }
        }
        return vector;
    }



    public Matrix solveSystem (Matrix A, Matrix b){

        Matrix[] QR = A.houseQR();

        Matrix Q = QR[0];
        Matrix R = QR[1];

        Matrix C = Q.trasp().prod(b);

        Matrix X = R.regresiva(C);

        return X;
    }




    public boolean isValidOK(){
        boolean flag = true;

        EditText numEq;
        numEq = (EditText) findViewById(R.id.etNEq);

        String cadenaEq = numEq.getText().toString();
        int intEq = Integer.parseInt(cadenaEq);
        if(cadenaEq.equals("")){
            flag = false;
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Ingrese el número de ecuaciones", Toast.LENGTH_SHORT);
            toast1.show();

        }else if(intEq < 2){
            flag = false;
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Mínimo 2 ecuaciones", Toast.LENGTH_SHORT);
            toast1.show();

        }else if(intEq > 15){
            flag = false;
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Máximo 15 ecuaciones", Toast.LENGTH_SHORT);
            toast1.show();
        }

        return flag;
    }

    public boolean isSystemCreated() {
        boolean flag = true;

        LinearLayout sistema = (LinearLayout) findViewById(R.id.sistema);

        //Analizamos que exista el sistema
        if (sistema.getChildCount() == 0) {
            flag = false;
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Cree primero el sistema de ecuaciones", Toast.LENGTH_SHORT);
            toast1.show();

            /*
        }else{
            //Analizamos que la matriz esté llena
            EditText nVar = (EditText) findViewById(R.id.etNVar);
            int numVar = Integer.parseInt(nVar.getText().toString());
            for (int i = 0; i < contFila; i++) {
                EditText value;
                int col = 0;
                for (int j = i * 100 + 1; j < i * 100 + numVar + 1; j++) {
                    value = (EditText) findViewById(j);
                    if (value.getText().toString().equals("")) {
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "Existen campos vacíos", Toast.LENGTH_SHORT);
                        toast1.show();
                        flag = false;
                        return flag;
                    }
                    col++;
                }
            }

            //Analizamos si el vector está completo
            for (int j = 1000; j < 1000 + contFila; j++) {
                EditText valueV;
                valueV = (EditText) findViewById(j + 0);

                if (valueV.getText().toString().equals("")) {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Existen campos vacíos", Toast.LENGTH_SHORT);
                    toast1.show();
                    flag = false;
                    return flag;
                }
            }
            */


        }
        return flag;
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





