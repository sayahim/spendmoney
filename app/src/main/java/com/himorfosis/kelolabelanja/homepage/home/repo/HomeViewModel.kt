package com.himorfosis.kelolabelanja.homepage.home.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himorfosis.kelolabelanja.financial.model.FinanceCreateResponse
import com.himorfosis.kelolabelanja.homepage.home.model.HomeGroupDataModel
import com.himorfosis.kelolabelanja.homepage.home.model.HomepageResponse
import com.himorfosis.kelolabelanja.network.state.StateNetwork

class HomeViewModel:ViewModel() {

    var financeUserResponse = MutableLiveData<StateNetwork<HomepageResponse>>()

    fun financeUserFetch() {
        financeUserResponse = HomeLiveData.fetchFinanceUser()
    }

}