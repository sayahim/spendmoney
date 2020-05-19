package com.himorfosis.kelolabelanja.profile.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.himorfosis.kelolabelanja.network.config.Network
import com.himorfosis.kelolabelanja.network.repository.BaseRepository
import com.himorfosis.kelolabelanja.network.services.UserService
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.profile.model.UserModel
import com.himorfosis.kelolabelanja.profile.model.UserUpdateRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody

class ProfileRepo:BaseRepository() {

    val service = Network.createService(UserService::class.java)
    private val disposable = CompositeDisposable()


    fun updateUserProfile(item: UserUpdateRequest): MutableLiveData<StateNetwork<UserModel>> {
        val data = MutableLiveData<StateNetwork<UserModel>>()
        service.updateProfileUser(item.body, item.map)
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

    fun updateProfileWithoutImage(map: Map<String, RequestBody>): MutableLiveData<StateNetwork<UserModel>> {

        val data = MutableLiveData<StateNetwork<UserModel>>()
        service.updateProfileWithoutImage(map)
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

    private fun isLog(msg: String) {
        Log.e("ProfileRepo", msg)
    }

}