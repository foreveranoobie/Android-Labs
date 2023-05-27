package com.ntukhpi.storozhuk.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SecondPage extends AppCompatActivity {
    private float firstNumber;
    private float secondNumber;
    private String operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        Bundle extras = getIntent().getExtras();
        ((TextView) findViewById(R.id.expressionView)).setText(extras.getString("firstNumber") + " " + extras.getString("operation")
                + " " + extras.getString("secondNumber"));
        firstNumber = Float.parseFloat(extras.getString("firstNumber"));
        secondNumber = Float.parseFloat(extras.getString("secondNumber"));
        operation = extras.getString("operation");
    }

    public void calculate(View view) {
        TextView result = findViewById(R.id.result);
        if (operation.equals("*")) {
            result.setText(String.valueOf(firstNumber * secondNumber));
        } else {
            result.setText(String.valueOf(firstNumber / secondNumber));
        }
    }

    public void returnToFirstPage(View view){
        this.finish();
    }
}