package com.ntukhpi.storozhuk.lab7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "people";
    private final String table = "users";

    private final String key_id = "id";
    private final String firstName = "first_name";
    private final String middleName = "middle_name";
    private final String lastName = "last_name";
    private final String year = "year";
    private final String city = "city";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("create table %s (%s integer primary key," +
                        " %s text, %s text, %s text, %s integer, %s text)", table,
                key_id, firstName, middleName, lastName, year, city));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table);
        onCreate(db);
    }

    public String getTableName(){
        return table;
    }
}
