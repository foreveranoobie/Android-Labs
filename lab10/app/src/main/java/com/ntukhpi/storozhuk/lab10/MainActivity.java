package com.ntukhpi.storozhuk.lab10;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private RequestTask requestTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doRequest(View view) {
        String url = ((EditText) findViewById(R.id.input)).getText().toString();
        requestTask = new RequestTask(findViewById(R.id.text));
        requestTask.execute(url);
    }
}