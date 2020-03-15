package com.himorfosis.kelolabelanja.homepage.statistict.viewmodel;

import android.content.Context;

import com.github.mikephil.charting.data.PieEntry;
import com.himorfosis.kelolabelanja.homepage.statistict.model.ChartModel;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatistictViewModel extends ViewModel {

    private StatistictRepo statistictRepo;
    private MutableLiveData<List<ChartModel>> dataSpending;
    private MutableLiveData<List<ChartModel>> dataIncome;
    private MutableLiveData<List<PieEntry>> dataStatisticChart;

    public MutableLiveData<List<ChartModel>> getDataSpending() {
        return dataSpending;
    }

    public void setDataSpending(Context context) {

        statistictRepo = StatistictRepo.getInstance(context);
//        dataSpending = statistictRepo.setDataSpending(context);
    }

    public MutableLiveData<List<ChartModel>> getDataIncome() {
        return dataIncome;
    }

    public void setDataIncome(Context context) {

        statistictRepo = StatistictRepo.getInstance(context);
//        dataIncome = statistictRepo.setDataIncomeUser(context);
    }

    public MutableLiveData<List<PieEntry>> getDataStatisticChart() {
        return dataStatisticChart;
    }

    public void setDataStatisticChart(Context context, List<ChartModel> dataStatistic) {

        dataStatisticChart = statistictRepo.setDataStatisticToChart(context, dataStatistic);

    }
}
