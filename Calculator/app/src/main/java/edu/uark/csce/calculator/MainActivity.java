package edu.uark.csce.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    private TextView CalDisplay;
    private static final String DIGITS = "0123456789.";
    DecimalFormat df = new DecimalFormat("0.######E0");
    private Boolean userHasPressedDigit = false;

    private double newOperand;
    private double ongoingOperand;
    private String ongoingOperator;

    // ongoingOperator types
    private static final String ADD = "+";
    private static final String SUBTRACT = "-";
    private static final String MULTIPLY = "*";
    private static final String DIVIDE = "/";


    private static final String BACK = "B";
    private static final String CLEAR = "C";

    private static final String TOGGLESIGN = "+/-";

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newOperand = 0;
        ongoingOperand = 0;
        ongoingOperator = "";

        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        CalDisplay = (TextView) findViewById(R.id.textViewDisplay);

        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);
        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.buttonSubtract).setOnClickListener(this);
        findViewById(R.id.buttonMultiply).setOnClickListener(this);
        findViewById(R.id.buttonDivide).setOnClickListener(this);
        findViewById(R.id.buttonToggleSign).setOnClickListener(this);
        findViewById(R.id.buttonDecimalPoint).setOnClickListener(this);
        findViewById(R.id.buttonEquals).setOnClickListener(this);
        findViewById(R.id.buttonBack).setOnClickListener(this);
        findViewById(R.id.buttonClear).setOnClickListener(this);
     


    }
    @Override
    public void onClick(View v) {

        String buttonPressed = ((Button) v).getText().toString();
//        Log.e("Button Pressed",buttonPressed);

        //if button pressed is a digit or .
        if (DIGITS.contains(buttonPressed)) 
        {
            //case when didg
            if (userHasPressedDigit) 
            {
                //if no decimal point is inserted again append the 
                if (!(buttonPressed.equals(".") && CalDisplay.getText().toString().contains("."))) {
                    // ERROR PREVENTION
                    // Eliminate entering multiple decimals
                    CalDisplay.append(buttonPressed);
                }
            } 
            else 
            {
                // ERROR PREVENTION
                // This will avoid error if only the decimal is hit before an ongoingOperator, by placing a leading zero before the decimal
                if (buttonPressed.equals("."))
                    CalDisplay.setText(0 + buttonPressed);
                else
                    CalDisplay.setText(buttonPressed);
                userHasPressedDigit = true;
            }

        }
        //if B is pressed
        else if(buttonPressed.equals("B")){
            if(CalDisplay.getText().length()>1)
                CalDisplay.setText(CalDisplay.getText().subSequence(0,CalDisplay.getText().length()-1));
            else {
                performOperation(buttonPressed);
                CalDisplay.setText(df.format(getResult()));
                userHasPressedDigit = false;
//                Log.e("B Pressed",buttonPressed);
            }
        }
        //if any other button is pressed
        else {
            // operation was pressed
            if (userHasPressedDigit) {
                setOperand(Double.parseDouble(CalDisplay.getText().toString()));
                userHasPressedDigit = false;
            }
            performOperation(buttonPressed);
            CalDisplay.setText(df.format(getResult()));
        }
    }

    public void setOperand(double operand) {
        newOperand = operand;
    }

    public double getResult() {
        return newOperand;
    }


    public String toString() {
        return Double.toString(newOperand);
    }

    protected double performOperation(String operator) {

        switch (operator) {
            case CLEAR:
                newOperand = 0;
                ongoingOperator = "";
                ongoingOperand = 0;
                break;
            case BACK:
                newOperand = 0;
                ongoingOperator = "";
                ongoingOperand = 0;
                break;
            case TOGGLESIGN:
                newOperand = -newOperand;
                break;
            default:
                performOngoingOperation();
                ongoingOperator = operator;
                ongoingOperand = newOperand;
                break;
        }

        return newOperand;
    }

    private void performOngoingOperation() {

        switch (ongoingOperator) {
            case ADD:
                newOperand = ongoingOperand + newOperand;
                break;
            case SUBTRACT:
                newOperand = ongoingOperand - newOperand;
                break;
            case MULTIPLY:
                newOperand = ongoingOperand * newOperand;
                break;
            case DIVIDE:
                if (newOperand != 0) {
                    newOperand = ongoingOperand / newOperand;
                }
                break;
        }

    }
}
