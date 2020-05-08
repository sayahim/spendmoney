package com.himorfosis.kelolabelanja.reports.repo

import androidx.lifecycle.MutableLiveData
import com.himorfosis.kelolabelanja.data_sample.CategoryData
import com.himorfosis.kelolabelanja.data_sample.FinancialsData
import com.himorfosis.kelolabelanja.reports.model.ReportsDataModel
import com.himorfosis.kelolabelanja.utilities.Util

class ReportsLiveData {

    companion object {

        private val TAG = ""

        private var INSTANCE: ReportsLiveData? = null
        fun getInstance() = INSTANCE
                ?: ReportsLiveData().also {
                    INSTANCE = it
                }

    }

    fun fetchSampleDataReports(): MutableLiveData<List<ReportsDataModel>> {

        var dataResponse = MutableLiveData<List<ReportsDataModel>>()
        // sorted list
        var listData = sampleDataReports().sortedWith(compareByDescending { it.total_nominal_category })
        dataResponse.value = listData

        return dataResponse
    }

    fun sampleDataReports():List<ReportsDataModel> {

        var data: List<ReportsDataModel>
        data = listOf(
                ReportsDataModel(1, "Makanan", "ic_eat", 100),
                ReportsDataModel(2, "Belanja", "ic_belanja", 70),
                ReportsDataModel(3, "Pakaian", "ic_hanger", 30),
                ReportsDataModel(4, "Dapur", "ic_baker", 80)
        )

        return data

    }

    private fun fetchIncomeDataFinancialsByCategory(): MutableList<ReportsDataModel> {

        val listDataFinancials = FinancialsData.getFinancialSample()
        var listReportCategory: MutableList<ReportsDataModel> = ArrayList()

        // get category income
        val listDataIncomeCategory = CategoryData.getDataCategoryIncome()

        for (item in listDataFinancials) {

            var totalNominalCategory = 0
            listDataIncomeCategory.forEach {
                // check type financials
                if (item.type == FinancialsData.INCOME_TYPE) {
                    if (item.category_id == it.id) {
                        totalNominalCategory += item.nominal.toInt()
                    }
                }
            }

            // check total nominal per category
            if (totalNominalCategory != 0) {
                // save data in list
                listReportCategory.add(ReportsDataModel(
                        item.category_id, item.category_image, item.category_name, totalNominalCategory))
            }
        }

        return listReportCategory
    }

    private fun fetchSpendDataFinancialsByCategory(): MutableList<ReportsDataModel> {

        Util.log(TAG, "fetchSpendDataFinancialsByCategory")

        val listDataFinancials = FinancialsData.getFinancialSample()
        var listReportCategory: MutableList<ReportsDataModel> = ArrayList()

        // get category spend
        val listDataSpendCategory = CategoryData.getDataCategorySpending()
        // check data category
        listDataSpendCategory.forEach {category ->
            var totalNominalCategory = 0
            listDataFinancials.forEach {
                if (it.type == FinancialsData.SPEND_TYPE) {
                    if (it.category_id == category.id) {
                        Util.log(TAG, "cat name : ${category.name}")
                        totalNominalCategory += it.nominal.toInt()
                    }
                }
            }

            // check total nominal per category
            if (totalNominalCategory > 0) {
                Util.log(TAG, "nominal : $totalNominalCategory")
                // save data in list
                listReportCategory.add(ReportsDataModel(
                        category.id, category.image_category, category.name, totalNominalCategory))
            }

        }
//        for (category in listDataSpendCategory) {
//            var totalNominalCategory = 0
//            // check data financials user
//            for (item in listDataFinancials) {
//                if (item.type == FinancialsData.SPEND_TYPE) {
//                    if (item.category_id == category.id) {
//                        Util.log(TAG, "cat name : ${category.name}")
//                        totalNominalCategory += item.nominal.toInt()
//                    }
//                }
//            }
//
//            // check total nominal per category
//            if (totalNominalCategory > 0) {
//
//                Util.log(TAG, "nominal : $totalNominalCategory")
//
//                // save data in list
//                listReportCategory.add(ReportsDataModel(
//                        category.id, category.image_category, category.name, totalNominalCategory))
//            }
//
//        }

        return listReportCategory

    }

    fun fetchReportFinancials(typeCategoryFinancials: String): MutableLiveData<List<ReportsDataModel>> {

        var dataResponse = MutableLiveData<List<ReportsDataModel>>()

        var dataCategory: List<ReportsDataModel> = ArrayList()
        if (typeCategoryFinancials == FinancialsData.SPEND_TYPE) {
            dataCategory = fetchSpendDataFinancialsByCategory()
        } else if (typeCategoryFinancials == FinancialsData.INCOME_TYPE) {
            dataCategory = fetchIncomeDataFinancialsByCategory()
        } else {
            // wrong type category
            Util.log(TAG, "WRONG TYPE CATEGORY")
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



}