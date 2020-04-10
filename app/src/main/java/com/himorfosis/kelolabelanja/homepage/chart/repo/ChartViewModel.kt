package com.himorfosis.kelolabelanja.homepage.chart.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.PieEntry
import com.himorfosis.kelolabelanja.homepage.chart.model.ChartCategoryModel

class ChartViewModel : ViewModel() {


    // response
    var fetchChartFinancialsByCategoryResponse = MutableLiveData<List<ChartCategoryModel>>()
    var showDataFinancialsCategoryOnChart = MutableLiveData<List<PieEntry>>()

    fun setChartFinancialsByCategory(typeCategoryFinancials: String) {

        fetchChartFinancialsByCategoryResponse = ChartRepo.getInstance().fetchChartFinancialsByCategory(typeCategoryFinancials)
    }

    fun setShowDataFinancialsCategoryOnChart(dataCategoryFinance: List<ChartCategoryModel>) {

        showDataFinancialsCategoryOnChart = ChartRepo.getInstance().showDataFinancialsCategoryOnChart(dataCategoryFinance)
    }

}