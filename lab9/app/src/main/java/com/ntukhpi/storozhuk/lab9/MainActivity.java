package com.ntukhpi.storozhuk.lab9;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_CONTACTS},
                1);
        final ListView contactsList = (ListView) findViewById(R.id.contacts);
        final List<Map<String, Object>> data = getContacts();
        contactsList.setAdapter(new SimpleAdapter(this,
                data,
                android.R.layout.simple_list_item_2,
                new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY, ContactsContract.CommonDataKinds.Phone.NUMBER},
                new int[]{android.R.id.text1, android.R.id.text2}
        ));
        contactsList.setOnItemClickListener((parent, view, position, id) -> {
            final Map<String, Object> item =
                    (Map<String, Object>) parent.getAdapter().getItem(position);
            final String phoneNumber = (String) item.get(ContactsContract.CommonDataKinds.Phone.NUMBER);
            Uri number = Uri.parse("tel:"+phoneNumber);
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(callIntent);
        });
    }

    private List<Map<String, Object>> getContacts() {
        final String[] wantedColumns = {
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        final String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY;
        final List<Map<String, Object>> data = new ArrayList<>();
        final ContentResolver resolver = getContentResolver();
        final Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, wantedColumns, null, null,
                sortOrder);
        if (cursor != null) {
            try {
                while (cursor.move(1)) {
                    final Map<String, Object> item = new HashMap<>();
                    item.put(ContactsContract.CommonDataKinds.Phone._ID, cursor.getString(
                            cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone._ID)));
                    item.put(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY, cursor.getString(
                            cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY)));
                    item.put(ContactsContract.CommonDataKinds.Phone.NUMBER, cursor.getString(
                            cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    data.add(item);
                }
            } finally {
                cursor.close();
            }
        }
        return data;
    }
}