package com.ntukhpi.storozhuk.lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GradientDrawable gradient = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.gradient);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gradient.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        } else {
            gradient.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        }
        System.out.println("onPause()");
    }
}