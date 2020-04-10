package com.himorfosis.kelolabelanja.homepage.home.model

data class HomeGroupDataModel(
        var date : String,
        var totalSpending : Int,
        var totalIncome : Int,
        var financialEntitiy: MutableList<FinanceDataModel>
)