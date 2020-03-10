package com.himorfosis.kelolabelanja.month_picker.java;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.himorfosis.kelolabelanja.R;
import com.himorfosis.kelolabelanja.utilities.Util;
import com.himorfosis.kelolabelanja.utilities.UtilJava;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MonthPickerJavaAdapter extends RecyclerView.Adapter<MonthPickerJavaAdapter.ViewHolder> {

    String TAG = "MonthPickerJavaAdapter";

    private Context context;
    private List<String> listData = new ArrayList<String>();
    private String yearSelected = "";
    private AdapterCallback callback;


    public MonthPickerJavaAdapter(Context context, AdapterCallback callback) {
        this.context = context;
        this.callback = callback;

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView month_tv;
        LinearLayout bg_month_ll;

        public ViewHolder(View view) {
            super(view);

            month_tv = view.findViewById(R.id.item_month_tv);
            bg_month_ll = view.findViewById(R.id.item_bg_month);

        }

    }

    @NonNull
    @Override
    public MonthPickerJavaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_month, parent, false);

        return new MonthPickerJavaAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MonthPickerJavaAdapter.ViewHolder holder, int position) {

        String getMonthSelected = UtilJava.getData("picker", "month", context);
        String getYearSelected = UtilJava.getData("picker", "year", context);

        Integer monthPosition = position + 1;

        String data = listData.get(position);

        if (data != null) {

            holder.month_tv.setText(data);

            if (getMonthSelected.equals(String.valueOf(monthPosition)) && yearSelected.equals(getYearSelected)) {

                holder.bg_month_ll.setBackgroundResource(R.drawable.circle_gold);

                Log.e(TAG, "year selected : " + getYearSelected);
                Log.e(TAG, "year position : " + yearSelected);
                Log.e(TAG, "month selected : " + getMonthSelected);
                Log.e(TAG, "month position : " + monthPosition);

            } else {

                holder.bg_month_ll.setBackgroundResource(0);

            }

            holder.itemView.setOnClickListener(v -> {

                UtilJava.saveData("picker", "year", yearSelected, context);
                UtilJava.saveData("picker", "month", monthPosition.toString(), context);

                holder.bg_month_ll.setBackgroundResource(R.drawable.circle_gold);

                if (monthPosition < 9) {

                    callback.onItemSelected(yearSelected + "-0" + String.valueOf(monthPosition));

                } else {

                    callback.onItemSelected(yearSelected + "-" + String.valueOf(monthPosition));

                }

            });

        }

    }

    public interface AdapterCallback {

        void onItemSelected(String monthSelected);

    }

    public void add(String data) {

        listData.add(data);

        notifyItemInserted(listData.size() - 1);

    }

    public void addAll(String[] posItems, String year) {

        yearSelected = year;

        for (int i = 0; i < posItems.length; i++) {

            add(posItems[i]);
        }

    }

    public void removeListAdapter() {

        this.listData.clear();
        notifyDataSetChanged();

    }
}
