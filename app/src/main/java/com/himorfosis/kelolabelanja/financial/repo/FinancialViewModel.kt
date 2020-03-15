package com.himorfosis.kelolabelanja.financial.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy

class FinancialViewModel: ViewModel() {

    var financialUserResponse = MutableLiveData<MutableList<FinancialEntitiy>>()

    fun fetchAllFinancials(context: Context) {

        financialUserResponse = FinancialLiveData.getInstance().fetchAllFinanceFatabase(context)

    }

}