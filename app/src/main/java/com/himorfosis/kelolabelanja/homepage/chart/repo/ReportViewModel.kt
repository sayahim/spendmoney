package com.himorfosis.kelolabelanja.homepage.chart.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.PieEntry
import com.himorfosis.kelolabelanja.homepage.chart.model.ReportCategoryModel
import com.himorfosis.kelolabelanja.homepage.chart.model.ReportCategoryRequest
import com.himorfosis.kelolabelanja.network.state.StateNetwork

class ReportViewModel: ViewModel() {

    private var reportRepo = ReportRepo()
    var reportFinanceCategoryResponse = MutableLiveData<StateNetwork<List<ReportCategoryModel>>>()
    var chartReportFinancePerCategoryResponse = MutableLiveData<List<PieEntry>>()

    fun fetchReportFinanceCategory(data: String) {
        reportFinanceCategoryResponse = reportRepo.financialsPerCategory(data)
    }

    fun fetchchartReportFinancePerCategory(data : List<ReportCategoryModel>) {
        chartReportFinancePerCategoryResponse = reportRepo.chartReportFinancePerCategory(data)
    }

}