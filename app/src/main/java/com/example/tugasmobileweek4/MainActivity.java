package com.example.tugasmobileweek4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView resultTv, solutionTv;
    Button buttonBracketOpen, buttonBracketClose, buttonAC, buttonDot;
    Button buttonDivide, buttonMultiply, buttonSubstract, buttonAddition, buttonEqual, buttonPercent;
    Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);
        setID(buttonBracketOpen, R.id.button_brack_open);
        setID(buttonBracketClose, R.id.button_brack_close);
        setID(buttonAC, R.id.button_ac);
        setID(buttonDot, R.id.button_dot);
        setID(buttonDivide, R.id.button_divide);
        setID(buttonMultiply, R.id.button_multiply);
        setID(buttonSubstract, R.id.button_substract);
        setID(buttonAddition, R.id.button_addition);
        setID(buttonEqual, R.id.button_equal);
        setID(buttonPercent, R.id.button_percent);
        setID(button0, R.id.button_0);
        setID(button1, R.id.button_1);
        setID(button2, R.id.button_2);
        setID(button3, R.id.button_3);
        setID(button4, R.id.button_4);
        setID(button5, R.id.button_5);
        setID(button6, R.id.button_6);
        setID(button7, R.id.button_7);
        setID(button8, R.id.button_8);
        setID(button9, R.id.button_9);
    }

    void setID(Button btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        String calculateString = solutionTv.getText().toString();

        if(buttonText.equals("AC")){
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }if(buttonText.equals("=")){
            solutionTv.setText(resultTv.getText());
            return;
        }else{
            calculateString += buttonText;
        }

        solutionTv.setText(calculateString);
        String result = getCalculationResult(calculateString);
        if(!result.equals("Err")){
            Double resultValue = Double.parseDouble(result);
            BigDecimal bd = new BigDecimal(resultValue);
            Double roundOff = bd.setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();

            String finalResult = roundOff.toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0", "");
            }
            resultTv.setText(finalResult);
        }
    }

    String getCalculationResult(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String result = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            return result;
        }catch(Exception e){
            return "Err";
        }
    }
}