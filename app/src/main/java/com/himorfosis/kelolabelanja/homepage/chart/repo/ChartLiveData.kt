package com.himorfosis.kelolabelanja.homepage.chart.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.PieEntry
import com.himorfosis.kelolabelanja.data_sample.CategoryData
import com.himorfosis.kelolabelanja.data_sample.FinancialsData
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy
import com.himorfosis.kelolabelanja.homepage.chart.model.ChartCategoryModel
import com.himorfosis.kelolabelanja.reports.model.ReportsDataModel
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.Util.Companion.getDataInt
import com.himorfosis.kelolabelanja.utilities.Util.Companion.log
import kotlin.collections.ArrayList

class ChartLiveData {

    companion object {

        private val TAG = "ChartLiveData"

        private var INSTANCE: ChartLiveData? = null
        fun getInstance() = INSTANCE
                ?: ChartLiveData().also {
                    INSTANCE = it
                }

    }

    private fun collectingDataByCategory() {

        val dataFinancialsUser = FinancialsData.getFinancialSample()
        val dataCategoryFinancialsUser = CategoryData.getDataCategoryIncome()


        var setCategoryList: MutableList<FinancialEntitiy> = ArrayList<FinancialEntitiy>()

        for (category in dataCategoryFinancialsUser) {

            for (item in dataFinancialsUser) {

                // check type financials
                if (item.type == FinancialsData.INCOME_TYPE) {


                }

            }

        }

    }

    private fun fetchIncomeDataFinancialsByCategory(): List<ChartCategoryModel> {

        val listDataFinancials = FinancialsData.getFinancialSample()

        var listChartCategory: MutableList<ChartCategoryModel> = ArrayList<ChartCategoryModel>()

        // get category income
        val listDataIncomeCategory = CategoryData.getDataCategoryIncome()

        for (item in listDataFinancials) {

            var totalNominalCategory = 0

            for (category in listDataIncomeCategory) {

                // check type financials
                if (item.type == FinancialsData.INCOME_TYPE) {

                    if (item.category_id == category.id) {

                        totalNominalCategory += item.nominal.toInt()
                    }

                }
            }

            // check total nominal per category
            if (totalNominalCategory != 0) {


                // save data in list
                listChartCategory.add(ChartCategoryModel(
                        item.category_id, item.category_image, item.category_name, totalNominalCategory))

            }

        }

        return listChartCategory
    }

    private fun fetchSpendDataFinancialsByCategory(): List<ChartCategoryModel> {

        Util.log(TAG, "fetchSpendDataFinancialsByCategory")

        val listDataFinancials = FinancialsData.getFinancialSample()

        var listChartCategory: MutableList<ChartCategoryModel> = ArrayList<ChartCategoryModel>()

        // get category spend
        val listDataSpendCategory = CategoryData.getDataCategorySpending()

        // check data category
        for (category in listDataSpendCategory) {

            var totalNominalCategory = 0

            // check data financials user
            for (item in listDataFinancials) {

                if (item.type == FinancialsData.SPEND_TYPE) {

                    if (item.category_id == category.id) {

                        Util.log(TAG, "cat name : ${category.name}")

                        totalNominalCategory += item.nominal.toInt()
                    }

                }
            }

            // check total nominal per category
            if (totalNominalCategory > 0) {

                Util.log(TAG, "nominal : $totalNominalCategory")

                // save data in list
                listChartCategory.add(ChartCategoryModel(
                        category.id, category.image_category, category.name, totalNominalCategory))

            }

        }

        return listChartCategory

    }

    fun fetchChartFinancialsByCategory(typeCategoryFinancials: String): MutableLiveData<List<ChartCategoryModel>> {

        var dataResponse = MutableLiveData<List<ChartCategoryModel>>()

        var dataCategory: List<ChartCategoryModel> = ArrayList()

        if (typeCategoryFinancials == FinancialsData.SPEND_TYPE) {

            dataCategory = fetchSpendDataFinancialsByCategory()

        } else if (typeCategoryFinancials == FinancialsData.INCOME_TYPE) {

            dataCategory = fetchIncomeDataFinancialsByCategory()

        } else {
            // wrong type category
            log(TAG, "WRONG TYPE CATEGORY")
        }

        if (dataCategory.isNotEmpty()) {
            // nominal terbesar
            var listData = dataCategory.sortedWith(compareByDescending { it.total_nominal_category })
            dataResponse.value = listData

        } else {

            dataResponse.value = null
        }

        return dataResponse

    }

    fun showDataFinancialsCategoryOnChart(dataCategoryFinance: List<ChartCategoryModel>): MutableLiveData<List<PieEntry>> {

        Util.log(TAG, "showDataFinancialsCategoryOnChart ")

        var dataResponse = MutableLiveData<List<PieEntry>>()

        val chartDataList: MutableList<PieEntry> = ArrayList()

        var totalNominalFinancials = 0

        var MaxCategoryFinance = 4

        // handle max category finance
        if (dataCategoryFinance.size < 4) {

            MaxCategoryFinance = dataCategoryFinance.size
        }

        // menghitung total nominal financials
        for (item in dataCategoryFinance) {

            totalNominalFinancials += item.total_nominal_category!!.toInt()
        }

        Util.log(TAG, "total category : $MaxCategoryFinance")

        for (position in 0 until MaxCategoryFinance) {

            val data = dataCategoryFinance[position]

            Util.log(TAG, "category name : ${data.category_name}")

            // count to get persen
            val progressPersen: Double = data.total_nominal_category!!.toDouble() / totalNominalFinancials.toDouble()
            val persen = progressPersen * 100.0

            val convertFloatNominalCategory = persen.toInt().toFloat()
            chartDataList.add(PieEntry(convertFloatNominalCategory, data.category_name))
        }

        if (chartDataList.isNotEmpty()) {

            dataResponse.value = chartDataList

        } else {

            dataResponse.value = null
        }

        return dataResponse

    }

    fun fetchReportData(dataCategoryFinance: List<ChartCategoryModel>): MutableLiveData<List<ReportsDataModel>> {

        var dataResponse = MutableLiveData<List<ReportsDataModel>>()

        val reportDataList: MutableList<ReportsDataModel> = ArrayList()

        var MaxCategoryFinance = 4

        // handle max category finance
        if (dataCategoryFinance.size < 4) {

            MaxCategoryFinance = dataCategoryFinance.size
        }

        for (position in 0 until MaxCategoryFinance) {

            val data = dataCategoryFinance[position]

            Util.log(TAG, "category name : ${data.category_name}")

            // count to get persen
//            val progressPersen: Double = data.total_nominal_category!!.toDouble() / totalNominalFinancials.toDouble()
//            val persen = progressPersen * 100.0

//            var id_category : Int,
//            var category_name : String,
//            var category_image : String,
//            var total_nominal_category : Int

//            reportDataList.add(ReportsDataModel(
//                    data.id_category, data.category_name, ))
        }

        return dataResponse

    }


}