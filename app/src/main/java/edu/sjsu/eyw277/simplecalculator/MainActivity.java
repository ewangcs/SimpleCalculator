package edu.sjsu.eyw277.simplecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mResultTextView;
    private Calculator mCalculator;

    private boolean mUserTyping = false;
    private boolean mError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalculator = new Calculator();

        mResultTextView = (TextView) findViewById(R.id.tv_result);
    }

    public void digitButtonClick(View view) {

        if (mError) {
            return;
        }

        String digit = ((Button) view).getText().toString();
        String textInDisplay = mResultTextView.getText().toString();

        if (mUserTyping) {
            if (textInDisplay.equals("0") && digit.equals("0")) {
                return;
            } else if (textInDisplay.equals("0")) {
                setDisplay(digit);
            } else if (textInDisplay.length() < 7) {
                setDisplay(textInDisplay + digit);
            } else {
                setDisplay("OVERFLOW!");
                mError = true;
            }
        } else {
            setDisplay(digit);
        }
        mUserTyping = true;
    }

    public void operationButtonClick(View view) {

        String symbol = ((Button) view).getText().toString();
        boolean skip = false;
        if (mError && !symbol.equals("C")) {
            return;
        }
        if (symbol.equals("C")) {
            mUserTyping = false;
            mError = false;
            mCalculator.reset();
            setDisplay(0);
            return;
        } else if (mUserTyping) {
            mCalculator.setAccumulator(getDisplay());
            mUserTyping = false;
            mCalculator.performOperation(symbol);
        } else if (!symbol.equals("=")) {
            mCalculator.replaceOperator(symbol);
        } else {
            mError = true;
            setDisplay("ERROR!");
            return;
        }

        String result = mCalculator.getResult();
        if (result.equals("ERROR!") || result.equals("OVERFLOW!")) {
            mError = true;
        }
        setDisplay(mCalculator.getResult());
//        if (mCalculator.getAccumulator() != null) {
//            setDisplay(mCalculator.getAccumulator());
//        } else {
//            setDisplay("ERROR");
//        }

    }

    private void setDisplay(int num) {
        mResultTextView.setText(String.valueOf(num));
    }

    private void setDisplay(String str) {
        mResultTextView.setText(str);
    }

    private int getDisplay() {
        return Integer.valueOf(mResultTextView.getText().toString());
    }
}
