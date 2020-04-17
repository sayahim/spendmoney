package com.himorfosis.kelolabelanja.homepage.home.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himorfosis.kelolabelanja.homepage.home.model.HomepageResponse
import com.himorfosis.kelolabelanja.network.state.StateNetwork

class HomeViewModel:ViewModel() {

    private var homeRepo = HomeRepo()
    var financeUserResponse = MutableLiveData<StateNetwork<HomepageResponse>>()

    fun financeUserFetch() {
        financeUserResponse = homeRepo.fetchFinanceUser()
    }

}