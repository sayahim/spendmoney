package com.himorfosis.kelolabelanja.homepage.statistict.view;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.himorfosis.kelolabelanja.R;
import com.himorfosis.kelolabelanja.homepage.report.model.ReportsSpendingModel;
import com.himorfosis.kelolabelanja.homepage.statistict.adapter.StatisticChartAdapter;
import com.himorfosis.kelolabelanja.homepage.statistict.model.ChartModel;
import com.himorfosis.kelolabelanja.homepage.statistict.model.FinancialProgressStatisticModel;
import com.himorfosis.kelolabelanja.homepage.statistict.model.StatistictModel;
import com.himorfosis.kelolabelanja.homepage.statistict.repo.GrafikRepo;
import com.himorfosis.kelolabelanja.homepage.statistict.viewmodel.StatistictViewModel;
import com.himorfosis.kelolabelanja.month_picker.java.MonthPickerJavaViewModel;
import com.himorfosis.kelolabelanja.utilities.Util;
import com.himorfosis.kelolabelanja.utilities.UtilJava;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PieChartView extends Fragment {

    String TAG = "PieChartView";

    private PieChart pieChart;
    StatisticChartAdapter adapterFinancial;
    RecyclerView recyclerView;
    LinearLayout selectMonthClick_ll,layout_chart_ll;
    TextView month_selected_tv, status_tv, status_deskripsi_tv;

    StatistictViewModel statistictViewModel;
    List<ChartModel> dataStatistict = new ArrayList<>();
    List<PieEntry> dataListChart = new ArrayList<>();

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

        setID(view);

        setDataDateToday();

//        setTablayoutAction(view);

        getDataSpendingReport();

        selectMonthClick_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setShowMonthPicker();

            }
        });

    }

    private void setID(View view) {

        recyclerView = view.findViewById(R.id.recycler_report_cart);
        pieChart = view.findViewById(R.id.pie_chart);
        selectMonthClick_ll = view.findViewById(R.id.select_month_click_ll);
        month_selected_tv = view.findViewById(R.id.month_selected_tv);
        status_tv = view.findViewById(R.id.status_tv);
        status_deskripsi_tv = view.findViewById(R.id.status_deskripsi_tv);
        layout_chart_ll = view.findViewById(R.id.layout_chart_ll);

        statistictViewModel = ViewModelProviders.of(this).get(StatistictViewModel.class);

    }

    private void getDataSpendingReport() {

        dataStatistict.clear();
        dataListChart.clear();

        statistictViewModel.setDataSpending(getContext());

        statistictViewModel.getDataSpending().observe(this, response -> {

            Util.log(TAG, "getDataSpendingReport" + response);

            if (response != null) {

                status_tv.setVisibility(View.INVISIBLE);
                status_deskripsi_tv.setVisibility(View.INVISIBLE);
                layout_chart_ll.setVisibility(View.VISIBLE);

                dataStatistict = response;

                setDataSpendingOnChart();

            } else {

                layout_chart_ll.setVisibility(View.INVISIBLE);
                status_tv.setText("Tidak Ada Transaksi");
                status_deskripsi_tv.setText("Untuk bulan ini, tidak ada data yang dapat di tampilkan. Silahkan pilih bulan lain atau tambahkan transaksi");
                status_tv.setVisibility(View.VISIBLE);
                status_deskripsi_tv.setVisibility(View.VISIBLE);

            }

        });

    }

    private void setDataSpendingOnChart() {

        statistictViewModel.setDataStatisticChart(getContext(), dataStatistict);

        statistictViewModel.getDataStatisticChart().observe(this, response -> {

            Util.log(TAG, "setDataSpendingOnChart" + response);

            if (response != null) {

                dataListChart = response;

//                Collections.reverse(dataListChart);

                setPieChart();

                setAdapterFinancial();

            }

        });

    }

    private void setDataDateToday() {

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateMonth = new SimpleDateFormat("MM");
        SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");

        String month = date.format(new Date());
        String yearToday = dateYear.format(new Date());
        String monthToday = dateMonth.format(new Date());

        String dateName = Util.convertCalendarMonth(month);

        month_selected_tv.setText(dateName);

        UtilJava.saveData("picker", "month", monthToday, getContext());
        UtilJava.saveData("picker", "year", yearToday, getContext());

        String getMonth = UtilJava.getData("picker", "month", getContext());

        Log.e(TAG, "get month : " + getMonth);

    }

    private void setShowMonthPicker() {

        MonthPickerJavaViewModel data = new MonthPickerJavaViewModel();

        data.setDataMonth(getContext());

        data.getDataMonth().observe(getActivity(), monthPicker -> {

            if (monthPicker != null) {

                UtilJava.log(TAG, "callback : " + monthPicker);

                String getYearSelected = UtilJava.getData("picker", "year", getContext());

                SimpleDateFormat date = new SimpleDateFormat("yyyy");

                String year = date.format(new Date());


                if (getYearSelected.equals(year)) {

                    month_selected_tv.setText(UtilJava.convertCalendarMonth(monthPicker + "-0-1"));

                } else {

                    String thisMonth = UtilJava.convertCalendarMonth(monthPicker + "-0-1");
                    month_selected_tv.setText(thisMonth + getYearSelected);

                }

                // remove data adapter
                adapterFinancial.removeAdapter();

                // get data month on year selected
                getDataSpendingReport();


            }

        });

    }

    private void setPieChart() {

        Util.log(TAG, "setPieChart");

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
        PieDataSet pieDataSet = new PieDataSet(dataListChart,"");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

//        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
//        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.animateXY(50, 50);
        pieChart.invalidate();

//        setAdapterFinancial();

    }

    private void setAdapterFinancial() {

        // sort by descending

        Collections.sort(dataStatistict, (o1, o2) ->
                o1.getTotal_nominal_category() - o2.getTotal_nominal_category());

        Collections.reverse(dataStatistict);

        adapterFinancial = new StatisticChartAdapter(getContext(), dataStatistict);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapterFinancial);

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
