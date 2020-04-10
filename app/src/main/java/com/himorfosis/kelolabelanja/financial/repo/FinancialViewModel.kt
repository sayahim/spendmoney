package com.himorfosis.kelolabelanja.financial.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himorfosis.kelolabelanja.financial.model.FinanceCreateModel
import com.himorfosis.kelolabelanja.financial.model.FinanceCreateResponse
import com.himorfosis.kelolabelanja.financial.model.FinanceUserFetchModel
import com.himorfosis.kelolabelanja.network.state.StateNetwork

class FinancialViewModel: ViewModel() {

    var financeUserResponse = MutableLiveData<StateNetwork<List<FinanceCreateResponse>>>()
    var financeCreateResponse = MutableLiveData<StateNetwork<FinanceCreateResponse>>()

    fun fetchUserFinancials(data: FinanceUserFetchModel) {
        financeUserResponse = FinancialRepo.fetchFinanceUsers(data)
    }

    fun createFinanceUser(data: FinanceCreateModel) {
        financeCreateResponse = FinancialRepo.createFinance(data)
    }

}