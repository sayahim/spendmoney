package com.himorfosis.kelolabelanja.homepage.statistict;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.himorfosis.kelolabelanja.R;
import com.himorfosis.kelolabelanja.homepage.statistict.adapter.FinancialProgressAdapter;
import com.himorfosis.kelolabelanja.homepage.statistict.model.FinancialProgressModel;
import com.himorfosis.kelolabelanja.utilities.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PieChartView extends Fragment {

    String TAG = "PieChartView";

    private PieChart pieChart;
    FinancialProgressAdapter adapterFinancial;
    RecyclerView recyclerView;
    LinearLayout selectMonthClick_ll;
    TextView month_selected_tv;

    private float[] yData = {66.76f, 44.32f};
    private String[] xData = {"Mitch", "Jessica" , "Mohammad" , "Kelsey", "Sam", "Robert", "Ashley"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.pie_chart_view, container, false);

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        recyclerView = view.findViewById(R.id.recycler_report_cart);
        pieChart = view.findViewById(R.id.pie_chart);
        pieChart = view.findViewById(R.id.pie_chart);
        selectMonthClick_ll = view.findViewById(R.id.select_month_click_ll);
        month_selected_tv = view.findViewById(R.id.month_selected_tv);

        setDataDateToday();

        setTablayoutAction(view);

        setPieChart();

        selectMonthClick_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });

    }

    private void setDataDateToday() {

        SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");

//        SimpleDateFormat dateMonth = new SimpleDateFormat("MM");
//        SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");

        String month = date.format(new Date());

        String[] monthArray = new String[ ]{"", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

        String monthConnvert = month.substring(month.indexOf(".") + 1);
        String bulan = monthConnvert.substring(0, monthConnvert.indexOf("."));

        Integer intMonth = Integer.parseInt(bulan);

        month_selected_tv.setText(monthArray[intMonth]);

    }

    private void setPieChart() {

        pieChart.getDescription().setText("Data teratas");
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
//        pieChart.setHoleRadius(30f);
//        pieChart.setTransparentCircleAlpha(10);
//        pieChart.setCenterText("Super Cool Chart");
//        pieChart.setCenterTextSize(10);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!

        pieChart.setRotationEnabled(true);
        PieDataSet pieDataSet = new PieDataSet(getData(),"");
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
//        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.animateXY(50, 50);
        pieChart.invalidate();

        setAdapterFinancial();

    }

    private void setAdapterFinancial() {

        adapterFinancial = new FinancialProgressAdapter(getDataProgress());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapterFinancial);

    }

    private ArrayList getData(){

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(945f, "Makanan"));
        entries.add(new PieEntry(530f, "Hiburan"));
        entries.add(new PieEntry(1143f, "Tiket"));
        entries.add(new PieEntry(1050f, "Jalan"));
        entries.add(new PieEntry(750f, "Developer"));
        return entries;

    }

    private ArrayList getDataProgress() {

        ArrayList<FinancialProgressModel> data = new ArrayList<>();
        data.add(new FinancialProgressModel("Makanan", 100, 100));
        data.add(new FinancialProgressModel("Hiburan", 70, 100));
        data.add(new FinancialProgressModel("Tiket", 30, 100));
        data.add(new FinancialProgressModel("Developer", 50, 100));

        return data;

    }

    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
//        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Employee Sales");
//        pieDataSet.setSliceSpace(2);
//        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
//        colors.add(Color.GRAY);
//        colors.add(Color.BLUE);
//        colors.add(Color.RED);
//        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
//        colors.add(Color.MAGENTA);

//        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
//        PieData pieData = new PieData(pieDataSet);
//        pieChart.setData(pieData);
//        pieChart.invalidate();

    }


    private void setTablayoutAction(View view) {

        FrameLayout tab_one_fl = view.findViewById(R.id.tab_one_fl);
        FrameLayout tab_two_fl = view.findViewById(R.id.tab_two_fl);
        TextView titletab_one_tv = view.findViewById(R.id.titletab_one_tv);
        TextView titletab_two_tv = view.findViewById(R.id.titletab_two_tv);

        tab_one_fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                titletab_one_tv.setTextColor(getResources().getColor(R.color.white));
                tab_one_fl.setBackgroundResource(R.drawable.border_blue_dark);

                // unselected

                titletab_two_tv.setTextColor(getResources().getColor(R.color.blue_dark));
                tab_two_fl.setBackgroundResource(0);

            }
        });

        tab_two_fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                titletab_two_tv.setTextColor(getResources().getColor(R.color.white));
                tab_two_fl.setBackgroundResource(R.drawable.border_blue_dark);

                // unselected
                titletab_one_tv.setTextColor(getResources().getColor(R.color.blue_dark));
                tab_one_fl.setBackgroundResource(0);

            }
        });



    }


}
