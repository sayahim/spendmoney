package com.himorfosis.kelolabelanja.homepage.chart.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.PieEntry
import com.himorfosis.kelolabelanja.homepage.chart.model.ReportCategoryModel
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.reports.model.ReportDetailCategoryModel

class ReportViewModel: ViewModel() {

    private var reportRepo = ReportRepo()
    var reportFinanceCategoryResponse = MutableLiveData<StateNetwork<List<ReportCategoryModel>>>()
    var chartReportFinancePerCategoryResponse = MutableLiveData<List<PieEntry>>()
    var reportFinanceCategoryDetailResponse = MutableLiveData<StateNetwork<ReportDetailCategoryModel>>()

    fun fetchReportFinanceCategory(type: String) {
        reportFinanceCategoryResponse = reportRepo.financialsPerCategory(type)
    }

    fun fetchchartReportFinancePerCategory(data : List<ReportCategoryModel>) {
        chartReportFinancePerCategoryResponse = reportRepo.chartReportFinancePerCategory(data)
    }

    fun fectchReportFinanceCategoryDetail(id: String) {
        reportFinanceCategoryDetailResponse = reportRepo.reportFinanceCategoryDetail(id)
    }

}