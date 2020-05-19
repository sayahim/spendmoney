package com.himorfosis.kelolabelanja.financial.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himorfosis.kelolabelanja.financial.model.*
import com.himorfosis.kelolabelanja.network.state.StateNetwork

class FinancialViewModel: ViewModel() {

    val financialRepo = FinancialRepo()
    var financeCreateResponse = MutableLiveData<StateNetwork<FinanceCreateResponse>>()
    var financeUpdateResponse = MutableLiveData<StateNetwork<FinanceCreateResponse>>()
    var deleteResponse = MutableLiveData<StateNetwork<FinanceStatusModel>>()

    fun createFinanceUser(data: FinanceCreateModel) {
        financeCreateResponse = financialRepo.createFinance(data)
    }

    fun editFinanceUser(data: FinanceUpdateModel) {
        financeUpdateResponse = financialRepo.editFinance(data)
    }

    fun deleteFinanceUser(id: String) {
        deleteResponse = financialRepo.deleteFinanceUser(id)
    }
}