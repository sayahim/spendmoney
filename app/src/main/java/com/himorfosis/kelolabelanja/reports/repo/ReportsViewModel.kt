package com.himorfosis.kelolabelanja.reports.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himorfosis.kelolabelanja.reports.model.ReportsDataModel

class ReportsViewModel: ViewModel() {

    var sampleDataResponse = MutableLiveData<List<ReportsDataModel>>()
    var fetchSpendDataResponse = MutableLiveData<MutableList<ReportsDataModel>>()
    var fetchIncomeDataResponse = MutableLiveData<MutableList<ReportsDataModel>>()
    var fetchReportFinancialsResponse = MutableLiveData<List<ReportsDataModel>>()

    fun fetchReportsSample() {
        sampleDataResponse = ReportsLiveData.getInstance().fetchSampleDataReports()
    }

    fun fetchReportFinancials(typeFinance : String) {
        fetchReportFinancialsResponse = ReportsLiveData.getInstance().fetchReportFinancials(typeFinance)
    }


}