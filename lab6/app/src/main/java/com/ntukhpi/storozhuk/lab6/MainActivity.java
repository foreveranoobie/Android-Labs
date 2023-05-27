package com.ntukhpi.storozhuk.lab6;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private ActionBarDrawerToggle actionBar;
    FirstFragment firstFragment;
    SecondFragment secondFragment;
    CommonData commonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        commonData = new CommonData();
        DrawerLayout drawerLayout = findViewById(R.id.mainDrawer);
        actionBar = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBar);
        actionBar.syncState();
        firstFragment = new FirstFragment();
        firstFragment.setCommonData(commonData);
        secondFragment = new SecondFragment();
        secondFragment.setCommonData(commonData);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(item -> {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (item.getTitle().toString().equals("Fragment 1")) {
                ft.replace(R.id.fragmentLayout, firstFragment);
            } else {
                EditText editText = findViewById(R.id.editTextNumberDecimal1);
                float firstNumber;
                if (editText.getText().toString().isEmpty()) {
                    firstNumber = 0;
                } else {
                    firstNumber = Float.parseFloat(editText.getText().toString());
                }
                float secondNumber;
                editText = findViewById(R.id.editTextNumberDecimal2);
                if (editText.getText().toString().isEmpty()) {
                    secondNumber = 0;
                } else {
                    secondNumber = Float.parseFloat(editText.getText().toString());
                }
                secondFragment.setNumbers(firstNumber, secondNumber);
                commonData.setOperation(((String) ((Spinner) findViewById(R.id.spinner)).getSelectedItem()).charAt(0));
                ft.replace(R.id.fragmentLayout, secondFragment);
            }
            ft.commit();
            return true;
        });
        navigationView.bringToFront();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentLayout, firstFragment);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBar.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}