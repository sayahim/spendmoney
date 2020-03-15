package com.himorfosis.kelolabelanja.homepage.statistict.viewmodel;

import android.content.Context;

import com.github.mikephil.charting.data.PieEntry;
import com.himorfosis.kelolabelanja.database.db.DatabaseDao;
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy;
import com.himorfosis.kelolabelanja.homepage.statistict.model.ChartModel;
import com.himorfosis.kelolabelanja.utilities.Util;
import com.himorfosis.kelolabelanja.utilities.UtilJava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

public class StatistictRepo {

    String TAG = "DaftarRekeningLiveData";

    private static StatistictRepo statistictRepo;
    private static String getMonth, getYear;

    public static StatistictRepo getInstance(Context context) {

        if (statistictRepo == null) {

            statistictRepo = new StatistictRepo();
        }


        return statistictRepo;

    }


    public MutableLiveData<List<PieEntry>> setDataStatisticToChart(Context context, List<ChartModel> dataStatistict) {

        MutableLiveData<List<PieEntry>> dataChart = new MutableLiveData<>();

        List<PieEntry> dataReports = new ArrayList<>();

        int nominalAllCategory = Util.getDataInt("report", "nominal_all_category", context);

        Collections.sort(dataStatistict, (o1, o2) ->
                o1.getTotal_nominal_category() - o2.getTotal_nominal_category());

        Collections.reverse(dataStatistict);

        Integer loopSize = 0;

        if (dataStatistict.size() > 4) {

            loopSize = 4;

        } else {

            loopSize = dataStatistict.size();

        }

        for (int x = 0; x < loopSize; x++) {

            ChartModel data = dataStatistict.get(x);

            // count to get persen
            Double progressPersen = new Double(data.getTotal_nominal_category()) / new Double(nominalAllCategory);
            Double persen = progressPersen * 100.0;

            Util.log(TAG, "loop data " + x + " : " + persen);

            float convertFloatNominalCategory = persen.intValue();

            dataReports.add(new PieEntry(convertFloatNominalCategory, data.getCategory_name()));

        }

        if (!dataReports.isEmpty()) {

            // set data value
            dataChart.setValue(dataReports);

        } else {

            dataChart.setValue(null);

        }

        return dataChart;
    }
//
//    public MutableLiveData<List<ChartModel>> setDataSpending(Context context) {
//
//        MutableLiveData<List<ChartModel>> dataSpending = new MutableLiveData<>();
//
//        List<ChartModel> dataReportsSpend = new ArrayList<>();
//
//        String monthOnYear = getYear + "-" + getMonth;
//        Integer nominalMax = 0;
//        Integer totalNominalAllCategory = 0;
//
//        for (int totalCategoryId = 0; totalCategoryId < 20; totalCategoryId++) {
//
//            List<FinancialEntitiy> financialDatabase = databaseDao.reportDataSpending(monthOnYear + "-01", monthOnYear + "-32", "spend", totalCategoryId);
//
//            if (!financialDatabase.isEmpty()) {
//
//                int categoryId = 0;
//                String categoryName = "-";
//                String categoryImage = "-";
//                int totalNominalPerCategory = 0;
//
//                for (int i = 0; i < financialDatabase.size(); i++) {
//
//                    FinancialEntitiy element = financialDatabase.get(i);
//
//                    categoryId = element.getCategory_id();
//                    categoryName = element.getCategory_name();
//                    categoryImage = element.getCategory_image();
//
//                    int convertNominalToInt = Integer.valueOf(element.getNominal());
//
//                    totalNominalPerCategory += convertNominalToInt;
//
//                    if (totalNominalPerCategory > nominalMax) {
//
//                        nominalMax = totalNominalPerCategory;
//
//                    }
//
//                }
//
//                // count total nominal all category
//                totalNominalAllCategory += totalNominalPerCategory;
//
//                ChartModel data = new ChartModel();
//
//                data.setId_category(categoryId);
//                data.setCategory_image(categoryImage);
//                data.setCategory_name(categoryName);
//                data.setTotal_nominal_category(totalNominalPerCategory);
//
//                dataReportsSpend.add(data);
//
//
//            }
//
//        }
//
//        if (!dataReportsSpend.isEmpty()) {
//
//            Util.log(TAG, "nominalMax : " + nominalMax);
//
//            Util.saveDataInt("report", "nominal_all_category", totalNominalAllCategory, context);
//            Util.saveDataInt("report", "chart", nominalMax, context);
//
//            dataSpending.setValue(dataReportsSpend);
//
//        } else {
//
//            dataSpending.setValue(null);
//
//        }
//
//        return dataSpending;
//    }

//    public MutableLiveData<List<ChartModel>> setDataIncomeUser(Context context) {
//
//        MutableLiveData<List<ChartModel>> dataSpending = new MutableLiveData<>();
//
//        List<ChartModel> dataReportIncome = new ArrayList<>();
//
//        String monthOnYear = getYear + "-" + getMonth;
//        Integer maxNominal = 0;
//        Integer totalNominalAllCategory = 0;
//
//        for (int totalCategoryId = 0; totalCategoryId < 20; totalCategoryId++) {
//
//            List<FinancialEntitiy> financialDatabase = databaseDao.reportDataSpending(monthOnYear + "-01", monthOnYear + "-32", "income", totalCategoryId);
//
//            if (!financialDatabase.isEmpty()) {
//
//                int categoryId = 0;
//                String categoryName = "-";
//                String categoryImage = "-";
//                int totalNominalPerCategory = 0;
//
//                for (int i = 0; i < financialDatabase.size(); i++) {
//
//                    FinancialEntitiy element = financialDatabase.get(i);
//
//                    categoryId = element.getCategory_id();
//                    categoryName = element.getCategory_name();
//                    categoryImage = element.getCategory_image();
//
//                    int convertNominalToInt = Integer.valueOf(element.getNominal());
//
//                    totalNominalPerCategory += convertNominalToInt;
//
//                    if (convertNominalToInt > maxNominal) {
//
//                        maxNominal = convertNominalToInt;
//
//                    }
//
//                }
//
//                // count total nominal all category
//                totalNominalAllCategory += totalNominalPerCategory;
//
//                ChartModel data = new ChartModel();
//
//                data.setId_category(categoryId);
//                data.setCategory_image(categoryImage);
//                data.setCategory_name(categoryName);
//                data.setTotal_nominal_category(totalNominalPerCategory);
//
//                dataReportIncome.add(data);
//
//            }
//
//        }
//
//        if (!dataReportIncome.isEmpty()) {
//
//            Util.saveDataInt("report", "nominal_all_category", totalNominalAllCategory, context);
//            Util.saveDataInt("report", "chart", maxNominal, context);
//            dataSpending.setValue(dataReportIncome);
//
//        } else {
//
//            dataSpending.setValue(null);
//
//        }
//
//        return dataSpending;
//    }

}
