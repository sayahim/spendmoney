package com.himorfosis.kelolabelanja.month_picker.java;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.himorfosis.kelolabelanja.R;
import com.himorfosis.kelolabelanja.utilities.UtilJava;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MonthPickerJavaLiveData {

    String TAG = "MonthPickerJavaLiveData";

    private Integer yearSelected = 0;

    private static MonthPickerJavaLiveData monthPickerJavaLiveData;

//    MutableLiveData<String> dateSelected = new MutableLiveData<String>();

    public static MonthPickerJavaLiveData getInstance() {

        if (monthPickerJavaLiveData == null){
            monthPickerJavaLiveData = new MonthPickerJavaLiveData();
        }
        return monthPickerJavaLiveData;

    }

//    public void setDateSelected(MutableLiveData<String> dateSelected) {
    public MutableLiveData<String> setDateSelected(Context context) {

        MutableLiveData<String> dateSelected = new MutableLiveData<>();

        String getYearSelected = UtilJava.getData("picker", "year", context);

        yearSelected = Integer.valueOf(getYearSelected);

//        Integer yearSelected = 1;
        View view =  LayoutInflater.from(context).inflate(R.layout.calendar_month_picker, null);

        AlertDialog.Builder builder = new AlertDialog.Builder((context));
        builder.setCancelable(false);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_month);
        final TextView yearSelected_tv = view.findViewById(R.id.year_selected_tv);
        final LinearLayout previousYear = view.findViewById(R.id.previous_year_ll);
        final LinearLayout nextYear = view.findViewById(R.id.next_year_ll);

        yearSelected_tv.setText(getYearSelected);

        MonthPickerJavaAdapter adapter = new MonthPickerJavaAdapter(context, new MonthPickerJavaAdapter.AdapterCallback() {
            @Override
            public void onItemSelected(String monthSelected) {

                Log.e(TAG, "callback : " + monthSelected);

                dateSelected.setValue(monthSelected);

                alertDialog.dismiss();
            }
        });

        adapter.addAll(context.getResources().getStringArray(R.array.month_list), getYearSelected);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        recyclerView.setAdapter(adapter);

        previousYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yearSelected -= 1;

                yearSelected_tv.setText(String.valueOf(yearSelected));

                adapter.removeListAdapter();

                adapter.addAll(context.getResources().getStringArray(R.array.month_list), yearSelected.toString());
                recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
                recyclerView.setAdapter(adapter);

            }
        });

        nextYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yearSelected += 1;

                yearSelected_tv.setText(String.valueOf(yearSelected));

                adapter.removeListAdapter();

                adapter.addAll(context.getResources().getStringArray(R.array.month_list), yearSelected.toString());
                recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
                recyclerView.setAdapter(adapter);

            }
        });

        alertDialog.show();

        return dateSelected;

    }


//    public MutableLiveData<String> getDateSelected() {
//        return dateSelected;
//    }
}
