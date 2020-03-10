package com.himorfosis.kelolabelanja.model

import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy

data class HomeGroupDataModel(

        var date : String,
        var totalSpending : Int,
        var totalIncome : Int,
        var financialEntitiy: MutableList<FinancialEntitiy>

)