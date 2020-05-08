package com.himorfosis.kelolabelanja.financial.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.himorfosis.kelolabelanja.financial.model.*
import com.himorfosis.kelolabelanja.network.config.Network
import com.himorfosis.kelolabelanja.network.repository.BaseRepository
import com.himorfosis.kelolabelanja.network.services.FinanceService
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FinancialRepo: BaseRepository() {


    val service = Network.createService(FinanceService::class.java)
    private val disposable = CompositeDisposable()

    fun createFinance(item: FinanceCreateModel):MutableLiveData<StateNetwork<FinanceCreateResponse>> {

        var  data = MutableLiveData<StateNetwork<FinanceCreateResponse>>()
        service.financialsCreate(
                        item.id_user, item.id_category,
                        item.type_financial, item.nominal,
                        item.note)
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

    fun fetchFinanceUsers(item: FinanceUserFetchModel):MutableLiveData<StateNetwork<List<FinanceCreateResponse>>> {

        var  data = MutableLiveData<StateNetwork<List<FinanceCreateResponse>>>()
        service.financialsUserFetch(
                        item.user_id,
                        item.date_start, item.date_end,
                        item.type_finance)
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

    fun deleteFinanceUser(id: String):MutableLiveData<StateNetwork<FinanceStatusModel>> {

        var  data = MutableLiveData<StateNetwork<FinanceStatusModel>>()
        service.deleteFinance(id)
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

}