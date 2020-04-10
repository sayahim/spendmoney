package com.himorfosis.kelolabelanja.details.category.model

import com.himorfosis.kelolabelanja.financial.model.FinancialEntitiy


data class FinancialPerCategoryModel (
        var date : String,
        var totalMoney : Int,
        var financialEntitiy: MutableList<FinancialEntitiy>
)