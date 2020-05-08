package com.himorfosis.kelolabelanja.homepage.home.repo

import androidx.lifecycle.MutableLiveData
import com.himorfosis.kelolabelanja.homepage.home.model.HomepageResponse
import com.himorfosis.kelolabelanja.network.config.Network
import com.himorfosis.kelolabelanja.network.repository.BaseRepository
import com.himorfosis.kelolabelanja.network.services.HomepageService
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.date.DateSet
import com.himorfosis.kelolabelanja.utilities.preferences.AccountPref
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.PickerPref
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class HomeRepo:BaseRepository() {

    private val service = Network.createService(HomepageService::class.java)
    private val disposable = CompositeDisposable()

    fun fetchFinanceUser(): MutableLiveData<StateNetwork<HomepageResponse>> {

        val userId = DataPreferences.account.getString(AccountPref.ID)
        val monthPicker = DataPreferences.picker.getString(PickerPref.MONTH)
        val yearPicker = DataPreferences.picker.getString(PickerPref.YEAR)

        val data = MutableLiveData<StateNetwork<HomepageResponse>>()
        service.homepageFetch(
                        userId,
                        "$yearPicker-$monthPicker-01",
                        "$yearPicker-$monthPicker-${DateSet.dateMaxOnMonth(yearPicker, monthPicker)}"
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe({
                    data.value = StateNetwork.OnSuccess(it)
                }, {
                    data.value = errorResponse(it)
                }).let {
                    disposable.add(it)
                }
        return data

    }
    private fun isLog(message: String) {
        Util.log("HomeRepo", message)
    }

}