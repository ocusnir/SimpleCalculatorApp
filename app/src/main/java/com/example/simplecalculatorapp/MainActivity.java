package com.example.simplecalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.os.VibrationEffect;
import android.os.Vibrator;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultView, solutionView;
    MaterialButton buttonC,buttonOpenBracket, buttonCloseBracket;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonDot, buttonPlus, buttonMinus, buttonPlusMinus, buttonDevide, buttonMultiply, buttonPercent, buttonEqual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleColorOfStatusBarIcons(MainActivity.this);

        resultView = findViewById(R.id.answer);
        solutionView = findViewById(R.id.solution);

        assignID(buttonC, R.id.button_c);
        assignID(buttonOpenBracket, R.id.button_open_and_close_brackets);
        assignID(buttonCloseBracket, R.id.button_open_and_close_brackets);
        assignID(button0, R.id.button_0);
        assignID(button1, R.id.button_1);
        assignID(button2, R.id.button_2);
        assignID(button3, R.id.button_3);
        assignID(button4, R.id.button_4);
        assignID(button5, R.id.button_5);
        assignID(button6, R.id.button_6);
        assignID(button7, R.id.button_7);
        assignID(button8, R.id.button_8);
        assignID(button9, R.id.button_9);
        assignID(buttonPercent, R.id.button_percent);
        assignID(buttonEqual, R.id.button_equal);
        assignID(buttonDot, R.id.button_dot);
        assignID(buttonPlus, R.id.button_plus);
        assignID(buttonPlusMinus, R.id.button_plus_minus);
        assignID(buttonMinus, R.id.button_minus);
        assignID(buttonDevide, R.id.button_devide);
        assignID(buttonMultiply, R.id.button_multiply);
    }

    void assignID (MaterialButton button, int id){
        button  = findViewById(id);
        button.setOnClickListener(this);
    }
    public void toggleColorOfStatusBarIcons(Activity activity){
        // Adapt Colors of Status Bar icons
            if(Build.VERSION.SDK_INT >= 23){
                View decor = activity.getWindow().getDecorView();
                if(decor.getSystemUiVisibility() != View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) {
                        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
    }

    @Override
    public void onClick(View view) {
        MaterialButton button  = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionView.getText().toString();
        solutionView.setMovementMethod(new ScrollingMovementMethod()); // Making our solution view scrollable
        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//        if(buttonText.equals("c")){
//            solutionView.setText("");
//            resultView.setText("0");
//            return;
//        }
        final VibrationEffect vibrationEffect2;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {

            // create vibrator effect with the constant EFFECT_CLICK
            vibrationEffect2 = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE);

            // it is safe to cancel other vibrations currently taking place
            vibrator.cancel();
            vibrator.vibrate(vibrationEffect2);
        }
        if(buttonText.equals("=")){
            solutionView.setText(resultView.getText());
            resultView.setText("");

            return;
        }

        if(buttonText.equals("c")){
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
        }else{
            dataToCalculate = dataToCalculate + buttonText;
        }

        solutionView.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);
        if(!finalResult.equals("Error")){
            resultView.setText(finalResult) ;
        }

    }

    String getResult(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data,"Javascript", 1, null).toString();
            return finalResult;
        }catch(Exception e){
            return "Error";
    }

    }
}