package com.zoftcode.yupana;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.text.Html;
import android.text.TextUtils;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class calcuScreen extends AppCompatActivity implements View.OnClickListener{

    private ViewFlipper vf;

    private Calculator mCalculator;

    static boolean isDegree = true;

    static CharSequence stringAns;

    //private String editorText = "", resultText = "", stringToParse = "", stringAns = "";

    private CharSequence editorText = "", resultText = "", stringToParse = "";

    private Button  btn7,   btn8,   btn9,   btnDel, btnAc,
                    btn4,   btn5,   btn6,   btnMult,btnDiv,
                    btn1,   btn2,   btn3,   btnMas, btnMenos,
                    btn0,   bntDot, bntX10E,btnAns, btnEqual;

    private Button  btnSquare,  btnCube,    btnNPower,  btnInvPower,    btnFact,
                    btnSqrt,    btnCrt,     btnNRoot,   btnOpenPar,     btnClosePar,
                    btnPi,      btnExp,     btnX10,     btnComma,       btnForward;


    private Button  btnSin,     btnCos,     btnTan,     btnLog10,   btnDeg,
                    btnInvSin,  btnInvCos,  btnInvTan,  btnLog_n,   btnLn,
                    btnBackward,     btnAbs,     btnRnd,     btnPermu,   btnCombi;


    private TextView editorDisplay, resultDisplay;

    private ArrayList<Integer> display,toParse;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcu_screen);

        display = new ArrayList<Integer>();
        toParse = new ArrayList<Integer>();

        mCalculator = new Calculator();


        editorDisplay = (TextView) findViewById(R.id.editorDisplay);
        resultDisplay = (TextView) findViewById(R.id.resultDisplay);

        //Casting de todos los elementos del teclado


        //Parte numeros
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnDel = (Button) findViewById(R.id.btnDEL);
        btnAc = (Button) findViewById(R.id.btnAC);

        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btnMult = (Button) findViewById(R.id.btnMult);
        btnDiv = (Button) findViewById(R.id.btnDiv);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btnMas = (Button) findViewById(R.id.btnMas);
        btnMenos = (Button) findViewById(R.id.btnMenos);

        btn0 = (Button) findViewById(R.id.btn0);
        bntDot = (Button) findViewById(R.id.btnPunto);
        bntX10E = (Button) findViewById(R.id.btnX10E);
        btnAns = (Button) findViewById(R.id.btnANS);
        btnEqual = (Button) findViewById(R.id.btnIgual);

        //Parte uno (calculadora)

        btnSquare = (Button) findViewById(R.id.btnSquare);
        btnCube = (Button) findViewById(R.id.btnCube);
        btnNPower = (Button) findViewById(R.id.btnNPower);
        btnInvPower = (Button) findViewById(R.id.btnInvPow);
        btnFact = (Button) findViewById(R.id.btnFact);

        btnSqrt = (Button) findViewById(R.id.btnSqrt);
        btnCrt = (Button) findViewById(R.id.btnCrt);
        btnNRoot = (Button) findViewById(R.id.btnNRoot);
        btnOpenPar = (Button) findViewById(R.id.btnOpenPar);
        btnClosePar = (Button) findViewById(R.id.btnClosePar);

        btnPi = (Button) findViewById(R.id.btnPi);
        btnExp = (Button) findViewById(R.id.btnExp);
        btnX10 = (Button) findViewById(R.id.btnX10);
        btnComma = (Button) findViewById(R.id.btnComma);
        btnForward = (Button) findViewById(R.id.btnForward);

        //Parte dos (calculadora)
        btnSin = (Button) findViewById(R.id.btnSin);
        btnCos = (Button) findViewById(R.id.btnCos);
        btnTan = (Button) findViewById(R.id.btnTan);
        btnLog10 = (Button) findViewById(R.id.btnLog10);
        btnDeg = (Button) findViewById(R.id.btnDeg);

        btnInvSin = (Button) findViewById(R.id.btnInvSin);
        btnInvCos = (Button) findViewById(R.id.btnInvCos);
        btnInvTan = (Button) findViewById(R.id.btnInvTan);
        btnLog_n = (Button) findViewById(R.id.btnLog_n);
        btnLn = (Button) findViewById(R.id.btnLn);

        btnBackward = (Button) findViewById(R.id.btnBackward);
        btnAbs = (Button) findViewById(R.id.btnAbs);
        btnRnd = (Button) findViewById(R.id.btnRnd);
        btnCombi = (Button) findViewById(R.id.btnCombin);
        btnPermu = (Button) findViewById(R.id.btnPermut);





        /////////////////////////////////////////



        //Habilitacion del ClickListener


        //Parte números
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnAc.setOnClickListener(this);

        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btnMas.setOnClickListener(this);
        btnMenos.setOnClickListener(this);

        btn0.setOnClickListener(this);
        bntDot.setOnClickListener(this);
        bntX10E.setOnClickListener(this);
        btnAns.setOnClickListener(this);
        btnEqual.setOnClickListener(this);

        //Parte uno (calculadora)
        btnSquare.setOnClickListener(this);
        btnCube.setOnClickListener(this);
        btnNPower.setOnClickListener(this);
        btnInvPower.setOnClickListener(this);
        btnFact.setOnClickListener(this);

        btnSqrt.setOnClickListener(this);
        btnCrt.setOnClickListener(this);
        btnNRoot.setOnClickListener(this);
        btnOpenPar.setOnClickListener(this);
        btnClosePar.setOnClickListener(this);

        btnPi.setOnClickListener(this);
        btnExp.setOnClickListener(this);
        btnX10.setOnClickListener(this);
        btnComma.setOnClickListener(this);


        //Parte dos (calculadora)
        btnSin.setOnClickListener(this);
        btnCos.setOnClickListener(this);
        btnTan.setOnClickListener(this);
        btnLog10.setOnClickListener(this);
        btnDeg.setOnClickListener(this);

        btnInvSin.setOnClickListener(this);
        btnInvCos.setOnClickListener(this);
        btnInvTan.setOnClickListener(this);
        btnLog_n.setOnClickListener(this);
        btnLn.setOnClickListener(this);

        btnAbs.setOnClickListener(this);
        btnRnd.setOnClickListener(this);
        btnPermu.setOnClickListener(this);
        btnCombi.setOnClickListener(this);

        ///////////////////////////////////////FLIPPER////////////////////////////



        vf = (ViewFlipper) findViewById(R.id.viewFlipper);

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vf.getDisplayedChild() == 0) {
                    vf.setInAnimation(inFromRightAnimation());
                    vf.setOutAnimation(outToLeftAnimation());
                    vf.showNext();
                }
            }
        });

        btnBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vf.getDisplayedChild()==1) {
                    vf.setInAnimation(inFromLeftAnimation());
                    vf.setOutAnimation(outToRightAnimation());
                    vf.showPrevious();
                }

            }
        });




    }



    @Override
    public void onClick(View v) {

        Button btn = (Button) v;
        String data = btn.getText().toString();


        if (data.equals("AC")){
            editorText = "";
            resultText = "";
            stringToParse = "";

            display.clear();
            toParse.clear();

            editorDisplay.setText(editorText);
            resultDisplay.setText(resultText);
        }
        else if (data.equals("DEL")){

            editorText = editorDisplay.getText().toString();
            if (editorText.length()>0) {

                int borrarDisplay=display.get(display.size()-1);
                int borrarToParse=toParse.get(toParse.size()-1);

                editorText = editorText.subSequence(0,editorText.length()-borrarDisplay);
                stringToParse = stringToParse.subSequence(0,stringToParse.length()-borrarToParse);

                display.remove(display.size()-1);
                toParse.remove(toParse.size()-1);

                editorDisplay.setText(editorText);
            }
        }

        else if (data.equals("Rad")) {
            btnDeg.setText("Deg");
            //Poner un toast
            //tvDeg.setText("Rad");
            isDegree = false;
        }
        else if (data.equals("Deg")) {
            btnDeg.setText("Rad");
            //Poner un toast
            //tvDeg.setText("Deg");
            isDegree = true;
        }else if (data.equals("=")){


            if(stringToParse.length()>0){
                stringToParse= fixSignoInicial(stringToParse);


                CharSequence resultText = mCalculator.getResult((String) stringToParse);

                stringAns = resultText;

                resultDisplay.setText(resultText);
            }
        }
        else{
            obtainInputValues(data);
        }



    }

    @TargetApi(Build.VERSION_CODES.N)
    private void obtainInputValues(String data){


        if (data.equals("7")) {
            cambiarCadena(1,1);
            editorText = TextUtils.concat(editorText, "7");
            stringToParse = TextUtils.concat(stringToParse, "7");

        } else if (data.equals("8")) {
            cambiarCadena(1, 1);
            editorText = TextUtils.concat(editorText, "8");
            stringToParse = TextUtils.concat(stringToParse, "8");

        } else if (data.equals("9")) {
            cambiarCadena(1,1);
            editorText = TextUtils.concat(editorText, "9");
            stringToParse = TextUtils.concat(stringToParse, "9");

        } else if (data.equals("4")) {
            cambiarCadena(1,1);
            editorText = TextUtils.concat(editorText, "4");
            stringToParse = TextUtils.concat(stringToParse, "4");

        } else if (data.equals("5")) {
            cambiarCadena(1,1);
            editorText = TextUtils.concat(editorText, "5");
            stringToParse = TextUtils.concat(stringToParse, "5");

        } else if (data.equals("6")) {
            cambiarCadena(1,1);
            editorText = TextUtils.concat(editorText, "6");
            stringToParse = TextUtils.concat(stringToParse, "6");

        } else if (data.equals("x")) {
            cambiarCadena(1,1);
            editorText = TextUtils.concat(editorText, "*");
            stringToParse = TextUtils.concat(stringToParse, "*");

            //Btn Dividir
        } else if (data.equals(getResources().getString(R.string.btnDiv))) {
            cambiarCadena(1, 1);
            editorText = TextUtils.concat(editorText, "/");
            stringToParse = TextUtils.concat(stringToParse, "/");

        }   else if (data.equals("1")) {
            cambiarCadena(1,1);
            editorText = TextUtils.concat(editorText, "1");
            stringToParse = TextUtils.concat(stringToParse, "1");

        } else if (data.equals("2")) {
            cambiarCadena(1,1);
            editorText = TextUtils.concat(editorText, "2");
            stringToParse = TextUtils.concat(stringToParse, "2");

        } else if (data.equals("3")) {
            cambiarCadena(1,1);
            editorText = TextUtils.concat(editorText, "3");
            stringToParse = TextUtils.concat(stringToParse, "3");

        } else if (data.equals("+")) {
            cambiarCadena(1,1);
            editorText = TextUtils.concat(editorText, "+");
            stringToParse = TextUtils.concat(stringToParse, "+");

        } else if (data.equals("-")) {
            cambiarCadena(1,1);
            editorText = TextUtils.concat(editorText, "-");
            stringToParse = TextUtils.concat(stringToParse, "-");

        } else if (data.equals("0")) {
            cambiarCadena(1,1);
            editorText = TextUtils.concat(editorText, "0");
            stringToParse = TextUtils.concat(stringToParse, "0");

        } else if (data.equals(".")) {
            cambiarCadena(1,1);
            editorText = TextUtils.concat(editorText, ".");
            stringToParse = TextUtils.concat(stringToParse, ".");

            //Btn X10Powered
        } else if (data.equals(getResources().getString(R.string.btnPorDiezPowered))) {
            cambiarCadena(1,4);
            editorText = TextUtils.concat(editorText, "E");
            stringToParse = TextUtils.concat(stringToParse, "*10^");

        } else if (data.equals("Ans")) {
            cambiarCadena(3,3);
            editorText = TextUtils.concat(editorText, "Ans");
            stringToParse = TextUtils.concat(stringToParse, "Ans");



        /*Botones de la parte 1*/

        } else if (data.equals(getResources().getString(R.string.btnSquare))) {
            cambiarCadena(2,2);
            editorText = TextUtils.concat(editorText, "^2");
            stringToParse = TextUtils.concat(stringToParse, "^2");

        } else if (data.equals(getResources().getString(R.string.btnCube))) {
            cambiarCadena(2,2);
            editorText = TextUtils.concat(editorText, "^3");
            stringToParse = TextUtils.concat(stringToParse, "^3");

            //Btn N Power
        } else if (data.equals(getResources().getString(R.string.btnNPower))) {
            cambiarCadena(2, 2);
            editorText = TextUtils.concat(editorText, "^(");
            stringToParse = TextUtils.concat(stringToParse, "^(");

            //Btn InvPower
        } else if (data.equals(getResources().getString(R.string.btnInvPower))){
            cambiarCadena(5,5);
            editorText = TextUtils.concat(editorText,"^(-1)");
            stringToParse = TextUtils.concat(stringToParse, "^(-1)");

            //Btn Factorial
        } else if (data.equals("x!")) {
            cambiarCadena(1,1);
            editorText = TextUtils.concat(editorText, "!");
            stringToParse = TextUtils.concat(stringToParse, "!");

            //Btn Sqrt
        } else if (data.equals(getResources().getString(R.string.btnSqrt))) {
            cambiarCadena(5,5);
            editorText = TextUtils.concat(editorText, "sqrt(");
            stringToParse = TextUtils.concat(stringToParse, "sqrt(");

            //Btn Crt
        } else if (data.equals(getResources().getString(R.string.btnCrt))) {
            cambiarCadena(4,4);
            editorText = TextUtils.concat(editorText, "crt(");
            stringToParse = TextUtils.concat(stringToParse, "crt(");

            //Btn N Root
        } else if (data.equals(getResources().getString(R.string.btnNRoot))) {
            cambiarCadena(4,4);
            editorText = TextUtils.concat(editorText, "nrt(");
            stringToParse = TextUtils.concat(stringToParse, "nrt(");

        } else if (data.equals("(")) {
            cambiarCadena(1,1);
                editorText = TextUtils.concat(editorText,"(");
                stringToParse = TextUtils.concat(stringToParse, "(");
        } else if (data.equals(")")) {
            cambiarCadena(1,1);
            editorText = TextUtils.concat(editorText, ")");
            stringToParse = TextUtils.concat(stringToParse, ")");

        } else if(data.equals(getResources().getString(R.string.btnPi))){
            cambiarCadena(1,2);
            editorText = TextUtils.concat(editorText, Html.fromHtml("&#928;"));
            stringToParse = TextUtils.concat(stringToParse, "pi");

        } else if(data.equals(getResources().getString(R.string.btnExp))){
            cambiarCadena(3, 3);
            editorText = TextUtils.concat(editorText, "e^(");
            stringToParse = TextUtils.concat(stringToParse, "e^(");

        } else if (data.equals(getResources().getString(R.string.btn10Power))) {
            cambiarCadena(4,4);
            editorText = TextUtils.concat(editorText, "10^(");
            stringToParse = TextUtils.concat(stringToParse, "10^(");

        }else if(data.equals(",")){
            cambiarCadena(1,1);
            editorText = TextUtils.concat(editorText, Html.fromHtml(","));
            stringToParse = TextUtils.concat(stringToParse, ",");


            /**Botones de la parte dos*/
        } else if (data.equals("sin")) {
            cambiarCadena(4,4);
            editorText = TextUtils.concat(editorText,"sin(");
            stringToParse = TextUtils.concat(stringToParse, "sin(");

        } else if (data.equals("cos")) {
            cambiarCadena(4, 4);
            editorText = TextUtils.concat(editorText, "cos(");
            stringToParse = TextUtils.concat(stringToParse, "cos(");

        } else if (data.equals("tan")) {
            cambiarCadena(4,4);
            editorText = TextUtils.concat(editorText,"tan(");
            stringToParse = TextUtils.concat(stringToParse, "tan(");

        }   else if (data.equals(getResources().getString(R.string.btnLog10))){
            cambiarCadena(4, 4);
            editorText = TextUtils.concat(editorText, "log(");
            stringToParse = TextUtils.concat(stringToParse, "log(");

        } else if(data.equals(getResources().getString(R.string.btnInvSin))){
            cambiarCadena(5,5);
            editorText = TextUtils.concat(editorText, "asin(");
            stringToParse = TextUtils.concat(stringToParse, "asin(");

        } else if(data.equals(getResources().getString(R.string.btnInvCos))){
            cambiarCadena(5, 5);
            editorText = TextUtils.concat(editorText, "acos(");
            stringToParse = TextUtils.concat(stringToParse, "acos(");

        } else if(data.equals(getResources().getString(R.string.btnInvTan))){
                editorText = TextUtils.concat(editorText, "atan(");
                stringToParse = TextUtils.concat(stringToParse, "atan(");

            //Btn Log_n
        } else if (data.equals(getResources().getString(R.string.btnLog_n))) {
            cambiarCadena(5,5);
            editorText = TextUtils.concat(editorText, "logn(");
            stringToParse = TextUtils.concat(stringToParse, "logn(");


        } else if (data.equals("ln")) {
                cambiarCadena(3, 3);
                editorText = TextUtils.concat(editorText, "ln(");
                stringToParse = TextUtils.concat(stringToParse, "ln(");

        } else if (data.equals("Abs")) {
            cambiarCadena(4,4);
            editorText = TextUtils.concat(editorText, "Abs(");
            stringToParse = TextUtils.concat(stringToParse, "Abs(");

        } else if (data.equals("Rnd")){
            cambiarCadena(3,3);
            editorText = TextUtils.concat(editorText, ("Rnd"));
            stringToParse = TextUtils.concat(stringToParse, "Rnd");;

        } else if (data.equals("C(n,r)")) {
            cambiarCadena(2,2);
            editorText = TextUtils.concat(editorText, "C(");
            stringToParse = TextUtils.concat(stringToParse, "C(");

        } else if (data.equals("P(n,r)")) {
            cambiarCadena(2,2);
            editorText = TextUtils.concat(editorText, "P(");
            stringToParse = TextUtils.concat(stringToParse, "P(");
        }


        editorDisplay.setText(editorText);
    }


    public void cambiarCadena(int addDisplay,int addToParse){
        display.add(addDisplay);
        toParse.add(addToParse);
    }

    public CharSequence fixSignoInicial(CharSequence cadena){
        if(String.valueOf(cadena.charAt(0)).equals("+")){
            // CharSequence cero = "0";
            cadena = TextUtils.concat("0",cadena);
        }
        return cadena;
    }



    ///////////////////////METODOS PARA EL FLIPPER////////////////

    private Animation inFromRightAnimation() {

        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,  +1.0f,
                Animation.RELATIVE_TO_PARENT,  0.0f,
                Animation.RELATIVE_TO_PARENT,  0.0f,
                Animation.RELATIVE_TO_PARENT,   0.0f );

        inFromRight.setDuration(200);
        inFromRight.setInterpolator(new AccelerateInterpolator());

        return inFromRight;

    }

    private Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(200);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    private Animation inFromLeftAnimation() {
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromLeft.setDuration(200);
        inFromLeft.setInterpolator(new AccelerateInterpolator());
        return inFromLeft;
    }

    private Animation outToRightAnimation() {
        Animation outtoRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoRight.setDuration(200);
        outtoRight.setInterpolator(new AccelerateInterpolator());
        return outtoRight;
    }

}