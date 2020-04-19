package com.himorfosis.kelolabelanja.category.repo

import androidx.lifecycle.MutableLiveData
import com.himorfosis.kelolabelanja.network.config.Network
import com.himorfosis.kelolabelanja.network.repository.BaseRepository
import com.himorfosis.kelolabelanja.network.services.CategoryService
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.response.CategoryResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CategoryRepo: BaseRepository(){

    val service = Network.createService(CategoryService::class.java)
    private val disposable = CompositeDisposable()

    fun category(): MutableLiveData<StateNetwork<List<CategoryResponse>>> {
        val data = MutableLiveData<StateNetwork<List<CategoryResponse>>>()
        service.category()
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

    fun categoryTypeFinance(type: String): MutableLiveData<StateNetwork<List<CategoryResponse>>> {
        val data = MutableLiveData<StateNetwork<List<CategoryResponse>>>()
        service.categoryTypeFinance(type)
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