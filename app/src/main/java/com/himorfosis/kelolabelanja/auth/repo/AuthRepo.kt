package com.himorfosis.kelolabelanja.auth.repo

import androidx.lifecycle.MutableLiveData
import com.himorfosis.kelolabelanja.app.MyApp
import com.himorfosis.kelolabelanja.network.config.Network
import com.himorfosis.kelolabelanja.network.repository.BaseRepository
import com.himorfosis.kelolabelanja.network.services.AuthService
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.response.LoginResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

object AuthRepo: BaseRepository() {

    val service = Network.createService(AuthService::class.java)
    private val disposable = CompositeDisposable()

    fun loginUser(getEmail: String?, getPassword: String?): MutableLiveData<StateNetwork<LoginResponse>> {
        val data = MutableLiveData<StateNetwork<LoginResponse>>()
        service.login(getEmail, getPassword)
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

    fun registerUser(getName: String?, getEmail: String?, getPassword: String?): MutableLiveData<StateNetwork<String>> {
        val data = MutableLiveData<StateNetwork<String>>()
        service.register(getName, getEmail, getPassword, getPassword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe({
                    data.value = StateNetwork.OnSuccess(messageStatus(it.toString()))
                }, {
                    data.value = errorResponse(it)
                }).let {
                    disposable.add(it)
                }
        return data
    }

    fun logoutUser(idUser: String?):MutableLiveData<StateNetwork<String>> {

        val data = MutableLiveData<StateNetwork<String>>()
        service.logout(idUser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe({
                    data.value = StateNetwork.OnSuccess(messageStatus(it.toString()))
                }, {
                    data.value = errorResponse(it)
                }).let {
                    disposable.add(it)
                }
        return data

    }

}