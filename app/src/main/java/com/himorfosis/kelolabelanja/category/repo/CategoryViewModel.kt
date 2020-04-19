package com.himorfosis.kelolabelanja.category.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.response.CategoryResponse

class CategoryViewModel: ViewModel() {

    var categoryRepo = CategoryRepo()
    var categoryResponse = MutableLiveData<StateNetwork<List<CategoryResponse>>>()
    var categoryTypeFinanceResponse = MutableLiveData<StateNetwork<List<CategoryResponse>>>()

    fun categoryFetch() {
        categoryResponse = categoryRepo.category()
    }

    fun categoryTypeFinancePush(type: String) {
        categoryTypeFinanceResponse = categoryRepo.categoryTypeFinance(type)
    }

}