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
    DecimalFormat df1 = new DecimalFormat("@###########");
    DecimalFormat df2 = new DecimalFormat("0.######E0");

    private Boolean userHasPressedDigit = false;

    private double newOperand;
    private double ongoingOperand;
    private String ongoingOperator;

    // ongoingOperator types
    private static final String ADD = "+";
    private static final String SUB = "-";
    private static final String MUL = "*";
    private static final String DIV = "/";
    private static final String BACK = "B";
    private static final String CLEAR = "C";
    private static final String SIGN = "+/-";

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalDisplay = (TextView) findViewById(R.id.textViewDisplay);

        newOperand = 0;
        ongoingOperand = 0;
        ongoingOperator = "";

        findViewById(R.id.b0).setOnClickListener(this);
        findViewById(R.id.b1).setOnClickListener(this);
        findViewById(R.id.b2).setOnClickListener(this);
        findViewById(R.id.b3).setOnClickListener(this);
        findViewById(R.id.b4).setOnClickListener(this);
        findViewById(R.id.b5).setOnClickListener(this);
        findViewById(R.id.b6).setOnClickListener(this);
        findViewById(R.id.b7).setOnClickListener(this);
        findViewById(R.id.b8).setOnClickListener(this);
        findViewById(R.id.b9).setOnClickListener(this);
        findViewById(R.id.bAdd).setOnClickListener(this);
        findViewById(R.id.bSub).setOnClickListener(this);
        findViewById(R.id.bMul).setOnClickListener(this);
        findViewById(R.id.bDiv).setOnClickListener(this);
        findViewById(R.id.bSign).setOnClickListener(this);
        findViewById(R.id.bPoint).setOnClickListener(this);
        findViewById(R.id.bEquals).setOnClickListener(this);
        findViewById(R.id.bBack).setOnClickListener(this);
        findViewById(R.id.bClear).setOnClickListener(this);
     


    }
    @Override
    public void onClick(View v) {

        String buttonPressed = ((Button) v).getText().toString();

        //if button pressed is a digit or .
        if ("0123456789.".contains(buttonPressed)) {
            //one digit has been pressed
            if (userHasPressedDigit) {
                //if no decimal point is inserted again append the digit
                if (!(buttonPressed.equals(".") && CalDisplay.getText().toString().contains("."))) {
                    CalDisplay.append(buttonPressed);
                }
            } 
            else {
                // This will avoid error if only the decimal is hit before an ongoingOperator, by placing a leading zero before the decimal
                if (buttonPressed.equals(".")) {
                    CalDisplay.setText(0 + buttonPressed);
                    userHasPressedDigit = true;
                }
                //putting zero as the first digit is not allowed
                else if(buttonPressed.equals("0"))
                    userHasPressedDigit = false;
                //any other digit is okay
                else {
                    CalDisplay.setText(buttonPressed);
                    userHasPressedDigit = true;
                }
            }

        }

        //if Back button is pressed is pressed
        else if(buttonPressed.equals("B")){
            //if there are still digits in the screen, delete them
            if(CalDisplay.getText().length()>1)
                CalDisplay.setText(CalDisplay.getText().subSequence(0,CalDisplay.getText().length()-1));
            else {
                doOperation(buttonPressed);
                if(Math.abs(newOperand)>999999999 || Math.abs(newOperand)<0.000001 && newOperand!=0)
                    CalDisplay.setText(df2.format(newOperand));
                else
                    CalDisplay.setText(df1.format(newOperand));
                userHasPressedDigit = false;
            }
        }
        //if any other button is pressed
        else {
            // operation was pressed
            if (userHasPressedDigit) {
                newOperand = Double.parseDouble(CalDisplay.getText().toString());
                userHasPressedDigit = false;
            }
            doOperation(buttonPressed);
            if(Math.abs(newOperand)>999999999 || Math.abs(newOperand)<0.000001 && newOperand!=0)
                CalDisplay.setText(df2.format(newOperand));
            else
                CalDisplay.setText(df1.format(newOperand));
        }
    }

    protected void doOperation(String operator) {

        switch (operator) {
            case CLEAR:
            case BACK:
                newOperand = 0;
                ongoingOperator = "";
                ongoingOperand = 0;
                break;
            case SIGN:
                newOperand = -newOperand;
                break;
            default:
                evaluateOngoingOperation();
                ongoingOperator = operator;
                ongoingOperand = newOperand;
                break;
        }
    }

    protected void evaluateOngoingOperation() {

        switch (ongoingOperator) {
            case ADD:
                newOperand = ongoingOperand + newOperand;
                break;
            case SUB:
                newOperand = ongoingOperand - newOperand;
                break;
            case MUL:
                newOperand = ongoingOperand * newOperand;
                break;
            case DIV:
                if (newOperand != 0)
                    newOperand = ongoingOperand / newOperand;
                break;
        }

    }
}
