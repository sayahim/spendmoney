package com.himorfosis.kelolabelanja.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.himorfosis.kelolabelanja.R;

import java.util.List;

public class AlertMonthAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> list;

    public AlertMonthAdapter(Context context, List<String> objects) {
        super(context, R.layout.item_alert_month, objects);
        this.context = context;
        list = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater layoutInflater;

            layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_alert_month, null);

        }

        View v = convertView;

        final String data = list.get(position);

        if (data != null) {

            TextView nama = (TextView) v.findViewById(R.id.name);

            nama.setText(data);

        }

        return v;

    }

}
