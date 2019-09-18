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
    DecimalFormat df = new DecimalFormat("@###########");
    private Boolean userHasPressedDigit = false;

    CalculatorLogic CalLogic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        CalLogic = new CalculatorLogic();

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
        Log.e("Button Pressed",buttonPressed);
        // String digits = "0123456789.";

        //if button pressed is a digit or .
        if (DIGITS.contains(buttonPressed)) {

            // digit was pressed
            if (userHasPressedDigit) {

//				Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();

                if (buttonPressed.equals(".") && CalDisplay.getText().toString().contains(".")) {
                    // ERROR PREVENTION
                    // Eliminate entering multiple decimals
//					Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                } else {

                    CalDisplay.append(buttonPressed);
//					Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                }

            } else {

                if (buttonPressed.equals(".")) {
                    // ERROR PREVENTION
                    // This will avoid error if only the decimal is hit before an operator, by placing a leading zero before the decimal
                    CalDisplay.setText(0 + buttonPressed);
                } else {
                    CalDisplay.setText(buttonPressed);
                }

                userHasPressedDigit = true;
            }

        }
        else if(buttonPressed.equals("B")){
            if(CalDisplay.getText().length()>1)
                CalDisplay.setText(CalDisplay.getText().subSequence(0,CalDisplay.getText().length()-1));
            else {
                CalLogic.performOperation(buttonPressed);
                CalDisplay.setText(df.format(CalLogic.getResult()));
                userHasPressedDigit = false;
                Log.e("B Pressed",buttonPressed);
            }
        }
        //if any other button is pressed
        else {
            // operation was pressed
            if (userHasPressedDigit) {

                CalLogic.setOperand(Double.parseDouble(CalDisplay.getText().toString()));
                userHasPressedDigit = false;
            }

            CalLogic.performOperation(buttonPressed);
            CalDisplay.setText(df.format(CalLogic.getResult()));

        }

    }
}
