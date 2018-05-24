package com.zoftcode.yupana;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.text.Spanned;
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
import com.example.complejo.Complejo;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.common.api.GoogleApiClient;

public class EncontrarRaices extends AppCompatActivity {

    AlertDialog alertDialog;
    String coeficientes;

    private Button OK, evaluar, reset;
    private EditText grado;
    private TextView result;
    private int gradoMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encontrar_raices);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alertDialog = new AlertDialog.Builder(EncontrarRaices.this).create(); //Read Update


        grado = (EditText) findViewById(R.id.etGrado);
        result = (TextView) findViewById(R.id.tvResultado);

        OK = (Button) findViewById(R.id.btnOK);
        OK.setVisibility(View.VISIBLE);
        evaluar = (Button) findViewById(R.id.btnResolver);
        reset = (Button) findViewById(R.id.btnReset);

        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidOK()){
                    quitarTerminos();
                    OK.setVisibility(View.GONE);
                    int grade = Integer.parseInt(grado.getText().toString());
                    gradoMax = grade;
                    for(int i=grade;i>=0;i--){
                        crearTerminos(i);
                    }
                }


            }
        });

        evaluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(isPolyCreated()) {
                    coeficientes = obtnrCoefs(gradoMax,false);

                    if (coeficientes.equals("existenCeros")) {

                        alertDialog.setTitle("¡Aviso!");
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage("Existen campos vacíos, ¿desea rellenarlos con CEROS y continuar?");

                        alertDialog.setButton(Dialog.BUTTON_POSITIVE,"Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                coeficientes = obtnrCoefs(gradoMax, true);
                                wrapperRaicesPolQR(alertDialog);
                            }
                        });

                        alertDialog.setButton(Dialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.cancel();
                            }
                        });

                        alertDialog.show();  //<-- See This!

                    } else {
                        wrapperRaicesPolQR(alertDialog);
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

    public void wrapperRaicesPolQR(AlertDialog alertDialog){

        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }
        Complejo[] raices = raicesPolinomicasQR(coeficientes);
        final String texto = imprimirCadenaCompleja(raices);
        result.setText(fromHtml(texto));
    }

    public String imprimirCadenaCompleja(Complejo[] cadena) {

        StringBuilder salida = new StringBuilder();

        for (int i = 0; i < cadena.length; i++) {

            salida.append("x<sub><small>"+String.valueOf(i+1)+"</small></sub> = ");

            /**Analizar si hay parte imaginaria o no*/
            if (cadena[i].imag()==0){
                salida.append(cadena[i].real()+" + 0.00000i");
                salida.append("<br/>");
            }else {
                String signo = "+";
                double parteImag = cadena[i].imag();
                if (parteImag < 0) {
                    parteImag = -parteImag;
                    signo = "-";
                }
                salida.append(cadena[i].real() + " " + signo + " " + parteImag + "i" + "<br/>");
            }
        }

        String out = salida.toString();
        return out;
    }



/*******************************************PARTE DE INTERFAZ*******************************************************/


    public void crearTerminos(int exponente){
            /* Find Linearlayout defined in main.xml */
        LinearLayout Polinomio = (LinearLayout) findViewById(R.id.poly);


            /* Create an EditText to be the row-content. */
        EditText coeficiente = new EditText(EncontrarRaices.this);
        coeficiente.setId(exponente);
        coeficiente.setSingleLine();
        coeficiente.setInputType(InputType.TYPE_CLASS_NUMBER);
        coeficiente.setKeyListener(DigitsKeyListener.getInstance(true,true)); // decimals and positive/negative numbers.


        coeficiente.setGravity(Gravity.CENTER);
        coeficiente.setBackgroundColor(Color.parseColor("#FFFFFF"));

        if (exponente==0){
            coeficiente.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }else{
            coeficiente.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        }

        float width = pxFromDp(this, 40);

        coeficiente.setMinWidth(Math.round(width));
        LinearLayout.LayoutParams paramsCoef= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsCoef.setMargins(10,10,10,10);
        coeficiente.setLayoutParams(paramsCoef);


            /* Create an TextView to be the row-content. */
        TextView variable = new TextView(EncontrarRaices.this);

        if (exponente==0){
            variable.setText(fromHtml("x<sup><small>"+String.valueOf(exponente)+"</small></sup>")); //using method fromHtml to avoid deprecation

        }else{
            variable.setText(fromHtml("x<sup><small>"+String.valueOf(exponente)+"</small></sup> + ")); //using method fromHtml to avoid deprecation

        }
        variable.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        variable.setGravity(Gravity.CENTER);
        variable.setTextColor(Color.parseColor("#000000"));
        LinearLayout.LayoutParams paramsVar= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        variable.setLayoutParams(paramsVar);
        paramsVar.gravity= Gravity.CENTER;

        Polinomio.addView(coeficiente);
        Polinomio.addView(variable);

    }

    public void quitarTerminos(){
        LinearLayout Polinomio = (LinearLayout) findViewById(R.id.poly);

        if(Polinomio.getChildCount() > 0)
            Polinomio.removeAllViews();
    }


    public void reiniciar(){
        LinearLayout Polinomio = (LinearLayout) findViewById(R.id.poly);
        OK.setVisibility(View.VISIBLE);
        if(Polinomio.getChildCount() > 0)
            Polinomio.removeAllViews();

        EditText grado = (EditText) findViewById(R.id.etGrado);
        grado.setText("");

        TextView resultado = (TextView) findViewById(R.id.tvResultado);
        resultado.setText("");


    }


    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }


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



    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }





/*******************************************PARTE MATEMÁTICA********************************************************/


    /*****************************************/
    /**Desarrollo de las funciones previas al algoritmo QR*/

    /**Funcion housecero:
     * crea ceros en un vector usando una matriz Householder.
     produce un vector u que define una matriz Householder H, y
     una constante sigma
     */
    public Matrix[] housecero(Matrix x) {
        int[] sizeX = x.dim();
        int m = sizeX[0];

        double max = x.absMatrix().max();
        x = x.prod(1 / max);
        double s = x.sign().getPos(0,0);
        if (s == 0) {
            s = 1;
        }
        double sigma = s * x.norma2V();
        Matrix u = x.suma(new Matrix("eye",m,1).prod(sigma));
        sigma = -max * sigma;

        Matrix Sigma = new Matrix(new double[][]{{sigma}});

        Matrix[] houseCero = {u,Sigma};

        return houseCero;
    }

    /**Funcion housemult;
     * postmultiplica una matriz por una matriz Householder H
     * Calcula AH, donde H es una matriz HOuseholder generada
     * por un vector u. La matriz resultante contiene el producto AH
     */
    public Matrix housemult(Matrix A, Matrix u) {

        int[] sizeA = A.dim();
        int m = sizeA[0];
        int n = sizeA[1];
        double s;

        double beta = 2 / (u.trasp().prod(u)).getPos(0,0);

        for (int i = 0; i < m; i++) {

            Matrix uSub = u.subMatrix(0,n-1,0,0);
            Matrix ASub = A.subMatrix(i,i,0,n-1);

            s = ASub.prod(uSub).getPos(0,0);
            s = s * beta;

            Matrix nFila = ASub.resta(uSub.prod(s).trasp());
            A = A.asignar(i,i, 0,n-1, nFila);

        }
        return A;
    }


    /**Funcion houseQR:
     *Factorizacion QR de una matriz A usando matrices Householder
     *houseQR produce an ortogonal matriz Q y una matriz triangular
     *superior R del mismo tamaño que A con ceros debajo de la diagonal
     * A tal que  A = QR.
     * Esta funcion llama a los metodos housecero y housemult
     */
    public Matrix[] houseQR(Matrix A) {

        int[] sizeA = A.dim();
        int m = sizeA[0];
        int n = sizeA[1];

        int S = Math.min(n, m-1);
        Matrix Q = new Matrix("eye", m, m);
        double s;

        for (int k = 0;k < S; k++) {


            Matrix[] xSigma = housecero(A.subMatrix(k,m-1,k,k));
            Matrix x = xSigma[0];
            double sigma = xSigma[1].getPos(0,0);



            Q = Q.asignar(0,m-1,k,m-1,housemult(Q.subMatrix(0,m-1,k,m-1),x));
            A.setPos(k,k,sigma);
            int s1 = x.dim()[0];


            A = A.asignar(k+1,m-1,k,k,x.subMatrix(1,s1-1,0,0));
            double beta = 2/(x.trasp().prod(x).getPos(0,0));

            for (int j = k + 1; j < n; j++) {

                Matrix xSub = (x.subMatrix(0,m-k-1,0,0));
                s = (((xSub).trasp()).prod(A.subMatrix(k,m-1,j,j)).getPos(0,0));
                s = beta * s;

                Matrix asignarA = (A.subMatrix(k,m-1,j,j)).resta((x.subMatrix(0,m-1-k,0,0)).prod(s));
                A = A.asignar(k,m-1,j,j,asignarA);
            }

        }
        Matrix R = A.triu();
        Matrix[] QR = {Q, R};
        return QR;
    }


    /**Esta funcion realiza el algoritmo QR usando el Shift de Wilkinson para acelerar
     * la convergencia y además obtener raíces con igual valor absoluto. Analiza la precision evaluando los autovalores
     * resultantes con el polinomio dado incialmente*/

    //	 static long startTime = System.currentTimeMillis();

    public Complejo[] iteracionQRShiftedWilkinson(Matrix A, String coefs) {

        Complejo[] autovalores;
        int maxiter = 1000;
        double tol = Math.pow(10, -10);

        int conv = 0;
        int iter = 0;

        int[] sizeA = A.dim();
        int n = sizeA[0];
        int m = sizeA[1];


        Matrix[] QR;
        Matrix Q = new Matrix(m, n);
        Matrix R = new Matrix(m, n);
        while (conv == 0 && iter < maxiter) {

            /**Elaboracion del Shift de Wilkinson*/
            double sigm = (A.getPos(n-1,n-2)-A.getPos(n-1,n-1))/2;
            double mu = A.getPos(n-1,n-1)-((sign(sigm)*(Math.pow(A.getPos(n-1,n-2), 2)))) / (Math.abs(sigm) + Math.sqrt(Math.pow(sigm, 2) + Math.pow(A.getPos(n-1,n-2), 2)));
            Matrix shift = new Matrix("eye",n,m).prod(mu);
            /***/

            QR = houseQR(A.resta(shift));
            Q = QR[0];
            R = QR[1];
            A = (R.prod(Q)).suma(shift);

            /**chequeo de la precision*/

//             double eval = evalPol(coefs, A.getPos(0,0));
//             if (Math.abs(eval) < tol) {
//                 conv = 1;
//             }
//             iter++;
//


            double belowLambda1 = redondearDecimales(A.getPos(1,0), 10);
            double rightLambda1 = redondearDecimales(A.getPos(0,1), 10);



            if(Math.abs(belowLambda1) < tol || Math.abs(rightLambda1) < tol){

                double eval = evalPol(coefs, A.getPos(0,0));

                if (Math.abs(eval) < tol) {
                    conv = 1;
                }
                iter++;
            }
            else{

                //Defino posiciones
                double a=A.getPos(0,0);
                double b=A.getPos(0,1);
                double c=A.getPos(1,0);
                double d=A.getPos(1,1);

                Complejo lambdaR = new Complejo((a+d)/2.0,0);
                Complejo lambdaI= Complejo.csqrt(((a+d)*(a+d)-4*(a*d-c*b))/4.0);

                Complejo lambda = Complejo.suma(lambdaR,lambdaI);
                lambda.redondearComplejo();

                double eval = evalPolComplejo(coefs, lambda);

                if (Math.abs(eval) < tol) {
                    conv = 1;
                }
                iter++;

            }

        }


//      	long endTime = System.currentTimeMillis() - startTime;




        autovalores = new Complejo[sizeA[0]];

        /**Hasta aqui el codigo ha hallado los autovalores, sin embargo,
         * si hay autovalores complejos, estos se veran representados como bloques
         * de 2*2 y po lo tanto hay que tratar cada bloque por separado, por eso el codigo
         * de abajo.*/

        for (int i = 0; i < sizeA[0]; i++) {
            String i_i = Integer.toString(i) + "," + Integer.toString(i); //Defino la posicion del autovalor


            double belowLambda =0;
            if (i<sizeA[0]-1) {
                belowLambda = redondearDecimales(A.getPos(i+1,i), 2);
            }
            if ((i<sizeA[0]-1) && (belowLambda!=0)) {

                //Defino posiciones
                double a=A.getPos(i,i);
                double b=A.getPos(i,i+1);
                double c=A.getPos(i+1,i);
                double d=A.getPos(i+1,i+1);

                Complejo lambdaR = new Complejo((a+d)/2,0);
                Complejo lambdaI= Complejo.csqrt(((a+d)*(a+d)-4*(a*d-c*b))/4);

                Complejo lambda1 = Complejo.suma(lambdaR,lambdaI);
                lambda1.redondearComplejo();

                Complejo lambda2 = Complejo.resta(lambdaR,lambdaI);
                lambda2.redondearComplejo();


                autovalores[i]=lambda1;
                i=i+1;
                autovalores[i]=lambda2;
            }else{

                //El autovalor ha de ser real
                Complejo lambda = new Complejo(A.getPos(i,i),0);
                lambda.redondearComplejo();
                autovalores[i] = lambda; //Acceso a la posicion del autovalor y guardo en la lista de autovalores.
            }
        }
        return autovalores;
    }

    public Complejo[] raicesPolinomicasQR(String coef) {

        Complejo[] raices;

        String[] coefs = coef.split(","); //Separo los coeficientes y los pongo en un arreglo
        double[] coefsnum = new double[coefs.length];

        for (int i = 0; i < coefs.length; i++) {
            coefsnum[i] = Double.parseDouble(coefs[i]);
        }

        if (coefsnum.length==1){
            Complejo raiz= new Complejo(coefsnum[0],0);
            raices = new Complejo[]{raiz};
        }else if (coefsnum.length==2){
            Complejo raiz= new Complejo(-coefsnum[1]/coefsnum[0],0);
            raices = new Complejo[]{raiz};
        }else {

            Matrix A = coefsMatrix(coef);
            raices = iteracionQRShiftedWilkinson(A, coef);
        }
        return raices;
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

    public double[] roundDecimals(double[] decimals,int numDecimals){
        for (int i=0;i<decimals.length;i++){
            decimals[i]=redondearDecimales(decimals[i],numDecimals);
        }
        return decimals;
    }

    /**Devuelve el valor de un polinomio evaluado en un punto*/
    public double evalPol(String coef, double punto) {

        String[] coefs = coef.split(",");
        double[] coefsnum = new double[coefs.length];

        for (int i = 0; i < coefs.length; i++) {
            coefsnum[i] = Double.parseDouble(coefs[i]);
        }


        int exponente = 0;
        double suma = 0;

        for (int contador = coefsnum.length - 1; contador >= 0; contador--) {

            suma = suma + coefsnum[contador] * Math.pow(punto, exponente);
            exponente = exponente + 1;
        }

        return suma;
    }


    public double evalPolComplejo(String coef, Complejo punto) {

        String[] coefs = coef.split(",");
        double[] coefsnum = new double[coefs.length];

        for (int i = 0; i < coefs.length; i++) {
            coefsnum[i] = Double.parseDouble(coefs[i]);
        }


        int exponente = 0;

        Complejo accum = new Complejo(0,0);

        for (int contador = coefsnum.length - 1; contador >= 0; contador--) {

            accum = accum.suma((punto.potencia(exponente)).producto(coefsnum[contador]));
            exponente = exponente + 1;
        }

        return accum.modulo();
    }


    /**Devuelve el valor sign de un numero*/
    public double sign(double Sigma) {

        double sigma = Sigma;
        if (sigma < 0) {
            sigma = -1;
        } else {
            sigma = 1;
        }
        return sigma;
    }



    /**Funcion que transforma los coeficientes en la matriz A.
     * La cantidad de coeficientes de ser mayor a 2*/
    public Matrix coefsMatrix(String coefs){

        Matrix A;
        String[] coefsString = coefs.split(",");
        int n=coefsString.length;
        double[] coefsDouble = new double[n];
        double[][] elementsA;

        elementsA=new double[n-1][n-1];
        for (int i = 0; i < n; i++) {
            coefsDouble[i] = Double.parseDouble(coefsString[i]);
        }

        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-1; j++) {
                if (j == i - 1) {
                    elementsA[i][j] = 1;
                } else if (j == n - 2) {
                    elementsA[i][j] = -coefsDouble[n - i - 1] / coefsDouble[0];
                } else {
                    elementsA[i][j] = 0;
                }
            }
        }

        A= new Matrix(elementsA);
        return A;
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





    public boolean isPolyCreated(){
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