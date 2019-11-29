package com.himorfosis.kelolabelanja.details.category.model

import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy

data class FinancialPerCategoryModel (

        var date : String,
        var totalMoney : Int,
        var financialEntitiy: MutableList<FinancialEntitiy>

)