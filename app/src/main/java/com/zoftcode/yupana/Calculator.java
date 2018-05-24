package com.zoftcode.yupana;

/**
 * Created by ferynando7 on 15/05/17.
 */

import android.util.Log;

import com.fathzer.soft.javaluator.Constant;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Parameters;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Created by Fernando Z on 16/04/2017.
 */
public class Calculator {

    final Function sqrt = new Function("sqrt", 1);
    final Function crt = new Function("crt",1);
    final Function nrt = new Function("nrt", 2);
    final Function Combination = new Function("C", 2);
    final Function Permutation = new Function("P", 2);
    final Function Abs = new Function("Abs", 1);
    final Function Log_n = new Function("logn", 2);
    final Function Log_10 = new Function("log", 1);
    final Function sin = new Function("sin",1);
    final Function cos = new Function("cos",1);
    final Function tan = new Function("tan",1);
    final Function asin = new Function("asin",1);
    final Function acos = new Function("acos",1);
    final Function atan = new Function("atan",1);

    final Constant Rnd = new Constant("Rnd");
    final Constant Ans = new Constant("Ans");
    final Constant E = new Constant("E");

    final Operator Factorial = new Operator("!",1, Operator.Associativity.LEFT,3);

    DoubleEvaluator evaluator;
    Parameters params;

    Double result;
    //String resultToDisplay;
    CharSequence resultToDisplay;


    public Calculator() {
        addFunctions();

        evaluator = new DoubleEvaluator(params) {


            @Override
            protected Double evaluate(Operator operator, Iterator<Double> operands, Object evaluationContext) {
                if (operator == Factorial){
                    return factorial(operands.next());
                } else {
                    return super.evaluate(operator, operands, evaluationContext);
                }
            }

            @Override
            protected Double evaluate(Constant constant, Object evaluationContext) {

                if (constant == Rnd) {
                    return Math.random();
                }else if(constant == Ans) {
                    return Double.parseDouble((String)calcuScreen.stringAns);
                }else
                    return super.evaluate(constant, evaluationContext);
            }

            @Override
            protected Double evaluate(Function function, Iterator<Double> arguments, Object evaluationContext) {


                if (function == sqrt) {
                    return Math.sqrt(arguments.next());

                } else if (function == crt){
                    return Math.pow(arguments.next(), (double) 1/3);

                } else if (function == nrt) {
                    double argIdentifier = 0;
                    ArrayList<Double> args = new ArrayList<Double>();
                    while (arguments.hasNext()) {
                        argIdentifier = (Double) arguments.next();
                        args.add(argIdentifier);
                    }
                    double exponente = args.get(0);
                    double base = args.get(1);

                    return Math.pow(base, 1/exponente);
                } else if (function == Combination) {
                    double argIdentifier = 0;
                    ArrayList<Double> args = new ArrayList<Double>();
                    while (arguments.hasNext()) {
                        argIdentifier = (Double) arguments.next();
                        args.add(argIdentifier);
                    }
                    double n = args.get(0);
                    double r = args.get(1);

                    return (factorial(n)) / (factorial(r) * factorial(n - r));

                }else if(function == Permutation){
                    double argIdentifier = 0;
                    ArrayList<Double> args = new ArrayList<Double>();
                    while (arguments.hasNext()) {
                        argIdentifier = (Double) arguments.next();
                        args.add(argIdentifier);
                    }
                    double n = args.get(0);
                    double r = args.get(1);
                    return (factorial(n))/factorial(n - r);

                }else if (function==Abs){
                    double x= (Double) arguments.next();
                    if (x<0){
                        x=-x;
                    }
                    return x;
                } else if (function==Log_n){
                    double argIdentifier = 0;
                    ArrayList<Double> args = new ArrayList<Double>();
                    while (arguments.hasNext()) {
                        argIdentifier = (Double) arguments.next();
                        args.add(argIdentifier);
                    }
                    double base = args.get(0);
                    double x = args.get(1);
                    return Math.log(x)/Math.log(base);

                } else if (function==Log_10){
                    return Math.log(arguments.next())/Math.log(10);

                } else if(function==sin){
                    double param = (Double) arguments.next();
                    if (calcuScreen.isDegree){
                        param = Math.toRadians(param);
                    }
                    return Math.sin(param);

                } else if(function==cos){
                    Double param = (Double) arguments.next();
                    if (calcuScreen.isDegree){
                        param = Math.toRadians(param);
                    }
                    return Math.cos(param);

                } else if(function==tan){
                    Double param = (Double) arguments.next();
                    if (calcuScreen.isDegree){
                        param = Math.toRadians(param);
                    }
                    return Math.tan(param);

                } else if(function==asin){
                    Double param = (Double) arguments.next();
                    double angleRad = Math.asin(param);
                    if (calcuScreen.isDegree) {
                        angleRad = Math.toDegrees(angleRad);
                    }
                    return angleRad;

                } else if(function==acos){
                    Double param = (Double) arguments.next();
                    double angleRad = Math.acos(param);
                    if (calcuScreen.isDegree) {
                        angleRad = Math.toDegrees(angleRad);
                    }
                    return angleRad;

                } else if(function==atan) {
                    Double param = (Double) arguments.next();
                    double angleRad = Math.atan(param);
                    if (calcuScreen.isDegree) {
                        angleRad = Math.toDegrees(angleRad);
                    }
                    return angleRad;
                }
                return super.evaluate(function, arguments, evaluationContext);
            }
        };



    }

    public void addFunctions() {
        params = DoubleEvaluator.getDefaultParameters();
        //Functions
        params.add(sqrt);
        params.add(crt);
        params.add(nrt);
        params.add(Combination);
        params.add(Permutation);
        params.add(Abs);
        params.add(Log_10);
        params.add(Log_n);
        params.add(sin);
        params.add(cos);
        params.add(tan);
        params.add(asin);
        params.add(acos);
        params.add(atan);
        //Constants
        params.add(Rnd);
        params.add(Ans);
        //Operators
        params.add(Factorial);
    }


    public double factorial(double n) {
        if (n>=0){
            if (n == 0 || n == 1) {
                return 1;
            } else {
                return n*factorial(n - 1);
            }
        }else{
            return 1/0;
        }
    }


    /// /  public String getResult(String currentDisplay, String expressionUsedForParsing) {
    //public String getResult(String expression){
    public CharSequence getResult(String expression){


        //Tries to parse the information as it is entered, if the parser can't handle it, the word error is shown on screen
        try {
            //System.out.println("Displayed Output " + expressionUsedForParsing);
            Log.i("antes", "yes");

            result = evaluator.evaluate(expression);

            Log.i("Evaluar",String.valueOf(result));
            //Log.d("Ans=", calcuScreen.stringAns.toString());
            double resultRounded = redondearDecimales(result,10);

            String resultString = String.valueOf(resultRounded);

            //currentSum = convertToRadians(currentSum);

            //resultToDisplay = String.valueOf(result);
            resultToDisplay = resultString;

            //previousSum = currentSum;
        }catch (Exception e) {

            Log.i("Exception", "yES");
            resultToDisplay = "Error";
        }
        return resultToDisplay;
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

