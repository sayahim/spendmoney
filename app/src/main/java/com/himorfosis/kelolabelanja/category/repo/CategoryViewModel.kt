package com.himorfosis.kelolabelanja.category.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himorfosis.kelolabelanja.category.model.AssetsModel
import com.himorfosis.kelolabelanja.category.model.CategoryCreateRequest
import com.himorfosis.kelolabelanja.category.model.CategoryCreateResponse
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.category.model.CategoryResponse

class CategoryViewModel: ViewModel() {

    var categoryRepo = CategoryRepo()
    var categoryResponse = MutableLiveData<StateNetwork<List<CategoryResponse>>>()
    var categoryTypeFinanceResponse = MutableLiveData<StateNetwork<List<CategoryResponse>>>()
    var assetsResponse = MutableLiveData<StateNetwork<List<AssetsModel>>>()
    var userCreateCategoryResponse = MutableLiveData<StateNetwork<CategoryCreateResponse>>()

    fun categoryFetch() {
        categoryResponse = categoryRepo.category()
    }

    fun categoryTypeFinancePush(type: String) {
        categoryTypeFinanceResponse = categoryRepo.categoryTypeFinance(type)
    }

    fun userCreateCategoryPush(it: CategoryCreateRequest) {
        userCreateCategoryResponse = categoryRepo.userCreateCategory(it)
    }

    fun assetsFetch() {
        assetsResponse = categoryRepo.assets()
    }

}