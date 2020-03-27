package com.himorfosis.kelolabelanja.data_sample

import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy

object FinancialsData {

    val SPEND_TYPE = "spend"
    val INCOME_TYPE = "income"

    fun getFinancialSample(): List<FinancialEntitiy> {

        var data: List<FinancialEntitiy> = ArrayList<FinancialEntitiy>()

        data = listOf(
// spend
//                CategoryEntity(1, "Makanan", "ic_eat"),
//                CategoryEntity(2, "Belanja", "ic_belanja"),
//                CategoryEntity(3, "Pakaian", "ic_hanger"),
//                CategoryEntity(4, "Dapur", "ic_baker"),
//                CategoryEntity(7, "Kesehatan", "ic_medical"),


// income
//                CategoryEntity(1, "Gaji", "ic_money"),
//                CategoryEntity(3, "Penjualan", "ic_payment"),

                FinancialEntitiy(null, 1, "Makanan", "ic_eat", SPEND_TYPE, "10000", "", "", "" ),
                FinancialEntitiy(null, 1, "Makanan", "ic_eat", SPEND_TYPE, "5000", "", "", "" ),
                FinancialEntitiy(null, 2, "Belanja", "ic_belanja", SPEND_TYPE, "100000", "", "", "" ),
                FinancialEntitiy(null, 1, "Gaji", "ic_money", INCOME_TYPE, "5000000", "", "", "" ),
                FinancialEntitiy(null, 2, "Belanja", "ic_belanja", SPEND_TYPE, "100000", "", "", "" ),
                FinancialEntitiy(null, 1, "Makanan", "ic_eat", SPEND_TYPE, "10000", "", "", "" ),
                FinancialEntitiy(null, 2, "Belanja", "ic_belanja", SPEND_TYPE, "10000", "", "", "" ),
                FinancialEntitiy(null, 1, "Makanan", "ic_eat", SPEND_TYPE, "100000", "", "", "" ),
                FinancialEntitiy(null, 2, "Belanja", "ic_belanja", SPEND_TYPE, "150000", "", "", "" ),
                FinancialEntitiy(null, 1, "Makanan", "ic_eat", SPEND_TYPE, "15000", "", "", "" ),
                FinancialEntitiy(null, 4, "Belanja", "ic_baker", SPEND_TYPE, "200000", "", "", "" ),
                FinancialEntitiy(null, 4, "Belanja", "ic_baker", SPEND_TYPE, "150000", "", "", "" ),
                FinancialEntitiy(null, 3, "Pakaian", "ic_hanger", SPEND_TYPE, "300000", "", "", "" ),
                FinancialEntitiy(null, 3, "Pakaian", "ic_hanger", SPEND_TYPE, "200000", "", "", "" ),
                FinancialEntitiy(null, 4, "Dapur", "ic_baker", SPEND_TYPE, "200000", "", "", "" ),
                FinancialEntitiy(null, 4, "Kesehatan", "ic_medical", SPEND_TYPE, "100000", "", "", "" ),
                FinancialEntitiy(null, 3, "Penjualan", "ic_payment", INCOME_TYPE, "200000", "", "", "" )

        )

        return data

    }

}