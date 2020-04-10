package com.himorfosis.kelolabelanja.homepage.home.repo

import androidx.lifecycle.MutableLiveData
import com.himorfosis.kelolabelanja.app.MyApp
import com.himorfosis.kelolabelanja.financial.model.FinanceCreateResponse
import com.himorfosis.kelolabelanja.financial.model.FinancialEntitiy
import com.himorfosis.kelolabelanja.homepage.home.model.FinanceDataModel
import com.himorfosis.kelolabelanja.homepage.home.model.HomeGroupDataModel
import com.himorfosis.kelolabelanja.homepage.home.model.HomepageResponse
import com.himorfosis.kelolabelanja.network.config.Network
import com.himorfosis.kelolabelanja.network.repository.BaseRepository
import com.himorfosis.kelolabelanja.network.services.FinanceService
import com.himorfosis.kelolabelanja.network.services.HomepageService
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.date.DateSet
import com.himorfosis.kelolabelanja.utilities.preferences.AccountPref
import com.himorfosis.kelolabelanja.utilities.preferences.PickerPref
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

object HomeLiveData:BaseRepository() {

    private val service = Network.createService(HomepageService::class.java)
    private val disposable = CompositeDisposable()

    fun fetchFinanceUser(): MutableLiveData<StateNetwork<HomepageResponse>> {

        val userId = MyApp.account.getString(AccountPref.ID)
//        val monthPicker = MyApp.account.getString(PickerPref.MONTH)
//        val yearPicker = MyApp.account.getString(PickerPref.YEAR)

        val data = MutableLiveData<StateNetwork<HomepageResponse>>()
        service.homepageFetch(userId, "2020-04-01")
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