package com.himorfosis.kelolabelanja.homepage.chart.repo

import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.PieEntry
import com.himorfosis.kelolabelanja.homepage.chart.model.ReportCategoryModel
import com.himorfosis.kelolabelanja.network.config.Network
import com.himorfosis.kelolabelanja.network.repository.BaseRepository
import com.himorfosis.kelolabelanja.network.services.ReportsService
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.reports.model.ReportCategoryDetailRequest
import com.himorfosis.kelolabelanja.reports.model.ReportCategoryDetailsModel
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.date.DateSet
import com.himorfosis.kelolabelanja.utilities.preferences.AccountPref
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.PickerPref
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ReportRepo:BaseRepository() {

    val service = Network.createService(ReportsService::class.java)
    private val disposable = CompositeDisposable()

    fun financialsPerCategory(type_finance: String): MutableLiveData<StateNetwork<List<ReportCategoryModel>>> {
        val response = MutableLiveData<StateNetwork<List<ReportCategoryModel>>>()
        val userId = DataPreferences.account.getString(AccountPref.ID)
        val monthPicker = DataPreferences.picker.getString(PickerPref.MONTH)
        val yearPicker = DataPreferences.picker.getString(PickerPref.YEAR)

        isLog("yearPicker : $yearPicker")
        isLog("monthPicker : $monthPicker")
        isLog("date start : $yearPicker-$monthPicker-01")
        isLog("date today : $yearPicker-$monthPicker-${DateSet.getDateNow()}")

        service.reportsFinancePerCategory(
                                userId!!,
                        "$yearPicker-$monthPicker-01",
                        "$yearPicker-$monthPicker-${DateSet.getDateNow()}",
                                type_finance)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe({
                    response.value = StateNetwork.OnSuccess(it)
                }, {
                    response.value = errorResponse(it)
                }).let {
                    disposable.add(it)
                }
        return response
    }

    fun reportFinanceCategoryDetail(id_category: String): MutableLiveData<StateNetwork<List<ReportCategoryDetailsModel>>> {
        val response = MutableLiveData<StateNetwork<List<ReportCategoryDetailsModel>>>()
        val userId = DataPreferences.account.getString(AccountPref.ID)
        val monthPicker = DataPreferences.picker.getString(PickerPref.MONTH)
        val yearPicker = DataPreferences.picker.getString(PickerPref.YEAR)

        service.reportsFinancePerCategoryDetail(
                        userId!!,
                        "$yearPicker-$monthPicker-01",
                        "$yearPicker-$monthPicker-${DateSet.getDateNow()}",
                        id_category)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe({
                    response.value = StateNetwork.OnSuccess(it)
                }, {
                    response.value = errorResponse(it)
                }).let {
                    disposable.add(it)
                }
        return response
    }

    fun chartReportFinancePerCategory(listData : List<ReportCategoryModel>): MutableLiveData<List<PieEntry>> {

        var response = MutableLiveData<List<PieEntry>>()
        val listChart: MutableList<PieEntry> = ArrayList()

        listData.forEach {
            listChart.add(PieEntry(it.total_percentage.toFloat(), it.title))
        }

        if (listChart.isNotEmpty()) {
            response.value = listChart
        } else {
            response.value = null
        }

        return response
    }

    private fun isLog(message: String) {
        Util.log("Report repo", message)
    }
}