package com.himorfosis.kelolabelanja.financial.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himorfosis.kelolabelanja.financial.model.FinanceCreateModel
import com.himorfosis.kelolabelanja.financial.model.FinanceCreateResponse
import com.himorfosis.kelolabelanja.financial.model.FinanceStatusModel
import com.himorfosis.kelolabelanja.financial.model.FinanceUserFetchModel
import com.himorfosis.kelolabelanja.network.state.StateNetwork

class FinancialViewModel: ViewModel() {

    val financialRepo = FinancialRepo()
    var financeUserResponse = MutableLiveData<StateNetwork<List<FinanceCreateResponse>>>()
    var financeCreateResponse = MutableLiveData<StateNetwork<FinanceCreateResponse>>()
    var deleteResponse = MutableLiveData<StateNetwork<FinanceStatusModel>>()

    fun createFinanceUser(data: FinanceCreateModel) {
        financeCreateResponse = financialRepo.createFinance(data)
    }

    fun deleteFinanceUser(id: String) {
        deleteResponse = financialRepo.deleteFinanceUser(id)
    }
}