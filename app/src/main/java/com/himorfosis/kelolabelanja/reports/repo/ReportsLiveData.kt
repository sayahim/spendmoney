package com.himorfosis.kelolabelanja.reports.repo

import androidx.lifecycle.MutableLiveData
import com.himorfosis.kelolabelanja.reports.model.ReportsDataModel

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