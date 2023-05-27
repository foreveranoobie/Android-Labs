package com.ntukhpi.storozhuk.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Spinner spinner = findViewById(R.id.operationSpinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[]{"*", "/"});
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();
    }

    public void calculateOperation(View view) {
        ((TextView) findViewById(R.id.errorsView)).setText("");
        EditText firstNumber = findViewById(R.id.editTextNumberSigned);
        EditText secondNumber = findViewById(R.id.editTextNumberSigned2);
        if (firstNumber.getText().toString().isEmpty() || secondNumber.getText().toString().isEmpty()) {
            ((TextView) findViewById(R.id.errorsView)).setText("Numbers fields shouldn't be empty");
        } else if (!secondNumber.getText().toString().equals("0")) {
            Intent intent = new Intent(this, SecondPage.class);
            intent.putExtra("firstNumber", firstNumber.getText().toString());
            intent.putExtra("secondNumber", secondNumber.getText().toString());
            intent.putExtra("operation", ((Spinner) findViewById(R.id.operationSpinner)).getSelectedItem().toString());
            startActivity(intent);
        } else {
            ((TextView) findViewById(R.id.errorsView)).setText("Zero division is prohibited");
        }
    }
}