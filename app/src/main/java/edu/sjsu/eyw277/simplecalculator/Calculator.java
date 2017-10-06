package edu.sjsu.eyw277.simplecalculator;

import android.util.Log;

/**
 * Created by aa001 on 9/21/2017.
 */

public class Calculator {
    private Integer accumulator = null;
    private Integer previousResult;
    private String previousOperator = "";

    public void setAccumulator(Integer number) {
        previousResult = accumulator;
        accumulator = number;
    }

//    public Integer getAccumulator() {
//        return accumulator;
//    }

    public String getResult() {
        if (accumulator == null) {
            return "ERROR!";
        } else if (Math.abs(accumulator) > 9999999) {
            return "OVERFLOW!";
        }
        return String.valueOf(accumulator);
    }

    public void performOperation(String symbol) {
        if (!previousOperator.equals("")) {
            accumulator = calculate(previousOperator, previousResult, accumulator);
        }
        previousOperator = symbol.equals("=") ? "" : symbol;
        Log.d("Result", String.valueOf(accumulator));
    }

    public void replaceOperator(String symbol) {
        if (symbol.equals("C")) {
            performOperation(symbol);
        } else {
            previousOperator = symbol;
        }
    }

    public void reset() {
        accumulator = null;
        previousOperator = "";
        previousResult = 0;
    }

    private Integer calculate(String op, int a, int b) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    return null;
                }
                double res = 1.0 * a / b;
                return res >= 0 ? (int) (res + 0.5) : (int) (res - 0.5);
            default:
                return null;
        }
    }

}
