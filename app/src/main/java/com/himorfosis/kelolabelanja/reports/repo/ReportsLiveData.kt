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

        var data: List<ReportsDataModel> = ArrayList<ReportsDataModel>()

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
        var listReportCategory: MutableList<ReportsDataModel> = ArrayList<ReportsDataModel>()

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
                listReportCategory.add(ReportsDataModel(
                        item.category_id, item.category_image, item.category_name, totalNominalCategory))
            }
        }


        return listReportCategory
    }

    private fun fetchSpendDataFinancialsByCategory(): MutableList<ReportsDataModel> {

        Util.log(TAG, "fetchSpendDataFinancialsByCategory")

        val listDataFinancials = FinancialsData.getFinancialSample()
        var listReportCategory: MutableList<ReportsDataModel> = ArrayList<ReportsDataModel>()

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
                listReportCategory.add(ReportsDataModel(
                        category.id, category.image_category, category.name, totalNominalCategory))

            }

        }

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


//    fun setDataIncome(context: Context) {
//
//        setDatabaseLocal(context)
//
//        // clear cache
//        dataReportsIncome.clear()
//
//        val month = Util.getData("picker", "month", context)
//        val year = Util.getData("picker", "year", context)
//
//        var monthOnYear = "$year-$month"
//        var maxNominal = 0
//
//        for (totalCategoryId in 0 until 20) {
//
//            val dataSpending = databaseDao.reportDataSpending("$monthOnYear-01", "$monthOnYear-32", "income", totalCategoryId)
//
//            Util.log(TAG, "data spending : $dataSpending")
//
//            if (dataSpending.isNotEmpty()) {
//
//                var categoryId = 0
//                var categoryName = "-"
//                var categoryImage = "-"
//                var totalNominalPerCategory = 0
//
//                // looping data to collecting in category
//                for (element in dataSpending) {
//
//                    categoryId = element.category_id
//                    categoryName = element.category_name
//                    categoryImage = element.category_image
//                    totalNominalPerCategory += element.nominal.toInt()
//
//                    if (totalNominalPerCategory > maxNominal) {
//
//                        maxNominal = totalNominalPerCategory
//
//                    }
//
//                }
//
//                dataReportsIncome.add(ReportsDataModel(categoryId, categoryName, categoryImage, totalNominalPerCategory))
//
//            }
//
//        }
//
//        if (dataReportsIncome.isNotEmpty()) {
//
//            Util.saveDataInt("report", "income", maxNominal, context)
//            dataIncome.value = dataReportsIncome
//
//        } else{
//
//            dataIncome.value = null
//
//        }
//
//    }


}