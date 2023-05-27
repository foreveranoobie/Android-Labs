package com.ntukhpi.storozhuk.lab5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class DataAdapter extends ArrayAdapter<String[]> {
    int resource;

    public DataAdapter(Context context, int resource, List<String[]> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        if (convertView == null) {
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, itemView, true);
        } else {
            itemView = (LinearLayout) convertView;
        }
        TextView itemText1 = itemView
                .findViewById(R.id.textView2);
        TextView itemText2 = itemView
                .findViewById(R.id.textView3);
        TextView itemText3 = itemView
                .findViewById(R.id.textView6);
        String[] strings = getItem(position);
        itemText1.setText(strings[0]);
        itemText2.setText(strings[1]);
        itemText3.setText(strings[2]);
        return itemView;
    }
}
