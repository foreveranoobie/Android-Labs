package com.ntukhpi.storozhuk.lab7;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    boolean isDescSorted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        initBase();
        Cursor cursor = database.query(dbHelper.getTableName(), null, null, null, null, null, null);
        initTable(cursor);
    }

    private void initTable(Cursor cursor) {
        TableLayout table = findViewById(R.id.tableLayout);
        table.setColumnShrinkable(0, true);
        TableRow tableRow;
        TextView textView;
        if (cursor.moveToFirst()) {
            do {
                tableRow = new TableRow(this);
                textView = initDefaultTextView();
                textView.setText(cursor.getString(1));
                tableRow.addView(textView);
                textView = initDefaultTextView();
                textView.setText(cursor.getString(2));
                tableRow.addView(textView);
                textView = initDefaultTextView();
                textView.setText(cursor.getString(3));
                tableRow.addView(textView);
                textView = initDefaultTextView();
                textView.setText(String.valueOf(cursor.getInt(4)));
                tableRow.addView(textView);
                textView = initDefaultTextView();
                textView.setText(cursor.getString(5));
                tableRow.addView(textView);
                tableRow.setBackgroundResource(R.drawable.border);
                table.addView(tableRow);
            } while (cursor.moveToNext());
        }
    }

    public void sortByYear(View view) {
        String sorting = isDescSorted ? "" : "DESC";
        isDescSorted = !isDescSorted;
        Cursor cursor = database.query(dbHelper.getTableName(), null, null, null, null, null, "year " + sorting);
        TableLayout table = findViewById(R.id.tableLayout);
        for (int i = 1; i < 6; i++) {
            table.removeViewAt(1);
        }
        initTable(cursor);
    }

    private TextView initDefaultTextView() {
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.border);
        return textView;
    }

    private void initBase() {
        dbHelper.onUpgrade(database, 1, 1);
        ContentValues contentValues = new ContentValues();
        String[] members = this.getResources().getStringArray(R.array.members);
        try {
            for (int i = 0; i < 5; i++) {
                contentValues.put("id", Integer.parseInt(members[i * 6]));
                contentValues.put("first_name", members[(i * 6) + 1]);
                contentValues.put("middle_name", members[(i * 6) + 2]);
                contentValues.put("last_name", members[(i * 6) + 3]);
                contentValues.put("year", Integer.parseInt(members[(i * 6) + 4]));
                contentValues.put("city", members[(i * 6) + 5]);
                database.insert(dbHelper.getTableName(), null, contentValues);
                contentValues.clear();
            }
        } catch (SQLiteConstraintException ex) {
            System.out.println(ex.getMessage());
        }
    }
}