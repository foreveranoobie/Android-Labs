package com.ntukhpi.storozhuk.lab5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private String[] randomStrings;
    private AppBarConfiguration mAppBarConfiguration;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        randomStrings = getResources().getStringArray(R.array.random_strings);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        ListView listView = findViewById(R.id.listView);
        DataAdapter adapter = new DataAdapter(this, R.layout.index, generateStrings());
        listView.setAdapter(adapter);
        listView.setForegroundGravity(Gravity.CENTER);
        listView.getLayoutParams().width = (int) (getWidestView(getApplicationContext(), adapter) * 1.05);
    }

    @SuppressLint("NewApi")
    public void sortTextViews(MenuItem menuItem) {
        ListView listView = findViewById(R.id.listView);
        List<TextView[]> textViews = new LinkedList<>(getTextViews(listView));
        if (menuItem.getItemId() == R.id.sort1) {
            textViews.sort((firstTexts, secondTexts) -> {
                return firstTexts[2].getText().toString().compareTo(secondTexts[2].getText().toString());
            });
        } else {
            textViews.sort((firstTexts, secondTexts) -> {
                return -1 * firstTexts[2].getText().toString().compareTo(secondTexts[2].getText().toString());
            });
        }
        List<String[]> updatedTexts = new ArrayList<>(15);
        String[] newTextView;
        int index;
        for (TextView[] array : textViews) {
            newTextView = new String[3];
            index = 0;
            for (TextView textView : array) {
                newTextView[index++] = textView.getText().toString();
            }
            updatedTexts.add(newTextView);
        }
        DataAdapter adapter = new DataAdapter(this, R.layout.index, updatedTexts);
        listView.setAdapter(adapter);
    }

    public void removeFromFirstTextsNumericBySort(MenuItem menuItem) {
        removeTextViewsByNumber(menuItem.getItemId() == R.id.command1);
    }

    public void removeFromFirstTextsNumeric(View view) {
        removeTextViewsByNumber(true);
    }

    public void removeTextViewsByNumber(boolean isFirst) {
        int controlTextView = isFirst ? 0 : 2;
        ListView listView = findViewById(R.id.listView);
        List<TextView[]> textViewsOnLayouts = new LinkedList<>(getTextViews(listView));
        int index;
        List<String[]> updatedTexts = new ArrayList();
        String[] texts;
        for (TextView[] textViews : textViewsOnLayouts) {
            if (!textViews[controlTextView].getText().toString().matches("[a-zA-Z]*[0-9]+[a-zA-Z]*")) {
                texts = new String[3];
                index = 0;
                for (TextView textView : textViews) {
                    texts[index++] = textView.getText().toString();
                }
                updatedTexts.add(texts);
            }
        }
        DataAdapter adapter = new DataAdapter(this, R.layout.index, updatedTexts);
        listView.setAdapter(adapter);
    }

    private List<TextView[]> getTextViews(ListView listView) {
        Adapter adapter = listView.getAdapter();
        LinearLayout layout;
        List<TextView[]> textViews = new LinkedList<>();
        for (int i = listView.getFirstVisiblePosition(); i < listView.getLastVisiblePosition() + 1; i++) {
            layout = (LinearLayout) adapter.getView(i, null, listView);
            iterateOverLayouts(layout, textViews);
        }
        return textViews;
    }

    private void iterateOverLayouts(ViewGroup layout, List<TextView[]> textViewsOnLayout) {
        TextView[] textViews = null;
        if (layout.getId() != R.id.secondRelative) {
            for (int i = 0; i < layout.getChildCount(); i++) {
                if (layout.getChildAt(i).getId() == R.id.firstLayout) {
                    LinearLayout firstLayout = (LinearLayout) layout.getChildAt(i);
                    textViews = new TextView[3];
                    for (int textViewIndex = 0; textViewIndex < firstLayout.getChildCount(); textViewIndex++) {
                        textViews[textViewIndex] = (TextView) firstLayout.getChildAt(textViewIndex);
                    }
                } else if (layout.getChildAt(i).getId() == R.id.secondLayout) {
                    textViews[2] = (TextView) ((LinearLayout) layout.getChildAt(i)).getChildAt(0);
                    textViewsOnLayout.add(textViews);
                    return;
                } else {
                    iterateOverLayouts((ViewGroup) layout.getChildAt(i), textViewsOnLayout);
                }
            }
        }
    }

    private List<String[]> generateStrings() {
        Random rand = new Random();
        List<String[]> arrays = new ArrayList<>();
        String[] array;
        for (int i = 0; i < 5; i++) {
            array = new String[3];
            for (int textViewIndex = 0; textViewIndex < 3; textViewIndex++) {
                array[textViewIndex] = randomStrings[rand.nextInt(randomStrings.length)];
            }
            arrays.add(array);
        }
        return arrays;
    }

    public int getWidestView(Context context, Adapter adapter) {
        int maxWidth = 0;
        View view = null;
        FrameLayout fakeParent = new FrameLayout(context);
        for (int i = 0, count = adapter.getCount(); i < count; i++) {
            view = adapter.getView(i, view, fakeParent);
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int width = view.getMeasuredWidth();
            if (width > maxWidth) {
                maxWidth = width;
            }
        }
        return maxWidth;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}