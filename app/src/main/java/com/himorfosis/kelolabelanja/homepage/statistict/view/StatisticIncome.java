package com.himorfosis.kelolabelanja.homepage.statistict.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.himorfosis.kelolabelanja.R;
import com.himorfosis.kelolabelanja.homepage.statistict.adapter.StatisticChartAdapter;
import com.himorfosis.kelolabelanja.homepage.statistict.model.ChartModel;
import com.himorfosis.kelolabelanja.homepage.statistict.viewmodel.StatistictViewModel;
import com.himorfosis.kelolabelanja.month_picker.DialogMonthPicker;
import com.himorfosis.kelolabelanja.utilities.DateSet;
import com.himorfosis.kelolabelanja.utilities.Util;
import com.himorfosis.kelolabelanja.utilities.UtilJava;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StatisticIncome extends Fragment {

    String TAG = "StatisticIncome";

    private PieChart pieChart;
    StatisticChartAdapter adapterFinancial;
    RecyclerView recyclerView;
    LinearLayout selectMonthClick_ll, layout_chart_ll;
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

        setViewModelStatistict();

        setShowOnAdapterFinancial();

        month_selected_tv.setText(DateSet.getMonthSelected(requireContext()));

        getDataReportIncome();

        selectMonthClick_ll.setOnClickListener(v ->
               dialogMonthPicker()
        );

    }

    private void setID(View view) {

        recyclerView = view.findViewById(R.id.recycler_report_cart);
        pieChart = view.findViewById(R.id.pie_chart);
        selectMonthClick_ll = view.findViewById(R.id.select_month_click_ll);
        month_selected_tv = view.findViewById(R.id.month_selected_tv);
        status_tv = view.findViewById(R.id.status_tv);
        status_deskripsi_tv = view.findViewById(R.id.status_deskripsi_tv);
        layout_chart_ll = view.findViewById(R.id.layout_chart_ll);

    }

    private void setViewModelStatistict() {

        statistictViewModel = ViewModelProviders.of(this).get(StatistictViewModel.class);
    }

    private void dialogMonthPicker() {

        DialogMonthPicker dialog = new DialogMonthPicker(getContext());
        dialog.show(getChildFragmentManager(), "dialog");

        dialog.setOnclick(data -> {
            String getYearSelected = Util.getData("picker", "year", requireContext());
            String getMonthSelected = Util.getData("picker", "month", requireContext());
            SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");
            String year = dateYear.format(new Date());

            Util.log(TAG, "get month : $getMonthSelected");
            Util.log(TAG, "get year : $getYearSelected");

            // show data
            if (getYearSelected.equals("year")) {
                String thisMonth = Util.convertMonthName(getMonthSelected);
                month_selected_tv.setText(thisMonth);
            } else {
                String thisMonth = Util.convertMonthName(getMonthSelected);
                month_selected_tv.setText(thisMonth + " " + getYearSelected);
            }

            // reload data

        });

    }

    private void getDataReportIncome() {

        dataStatistict.clear();
        dataListChart.clear();

        statistictViewModel.setDataIncome(getContext());

        statistictViewModel.getDataIncome().observe(this, response -> {

            Util.log(TAG, "getDataSpendingReport" + response);

            if (response != null) {

                status_tv.setVisibility(View.INVISIBLE);
                status_deskripsi_tv.setVisibility(View.INVISIBLE);
                layout_chart_ll.setVisibility(View.VISIBLE);

                dataStatistict = response;

                setDataReportIncomeOnChart();

            } else {

                layout_chart_ll.setVisibility(View.INVISIBLE);
                status_tv.setText("Tidak Ada Transaksi");
                status_deskripsi_tv.setText("Untuk bulan ini, tidak ada data yang dapat di tampilkan. Silahkan pilih bulan lain atau tambahkan transaksi");
                status_tv.setVisibility(View.VISIBLE);
                status_deskripsi_tv.setVisibility(View.VISIBLE);

            }

        });

    }

    private void setDataReportIncomeOnChart() {

        statistictViewModel.setDataStatisticChart(getContext(), dataStatistict);

        statistictViewModel.getDataStatisticChart().observe(this, response -> {

            Util.log(TAG, "setDataSpendingOnChart" + response);

            if (response != null) {

                dataListChart = response;

                setShowOnPieChart();

                setShowOnAdapterFinancial();

            }

        });

    }

    private void setShowOnPieChart() {

        Util.log(TAG, "setPieChart");

        pieChart.getDescription().setText("Data teratas");


        pieChart.setRotationEnabled(true);
        PieDataSet pieDataSet = new PieDataSet(dataListChart, "");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.animateXY(50, 50);
        pieChart.invalidate();

    }

    private void setShowOnAdapterFinancial() {

        // sort by descending

        Collections.sort(dataStatistict, (o1, o2) ->
                o1.getTotal_nominal_category() - o2.getTotal_nominal_category());

        Collections.reverse(dataStatistict);

        adapterFinancial = new StatisticChartAdapter(getContext(), dataStatistict);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapterFinancial);

    }

}
