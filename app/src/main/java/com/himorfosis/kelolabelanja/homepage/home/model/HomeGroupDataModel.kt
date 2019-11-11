package com.himorfosis.kelolabelanja.homepage.home.model

import com.himorfosis.kelolabelanja.database.entity.SpendingEntitiy

data class HomeGroupDataModel(

        var date : String,
        var totalSpending : Int,
        var totalIncome : Int,
        var spendingEntitiy: MutableList<SpendingEntitiy>

)