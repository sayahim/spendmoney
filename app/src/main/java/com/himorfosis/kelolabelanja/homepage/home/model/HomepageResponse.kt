package com.himorfosis.kelolabelanja.homepage.home.model

import java.io.Serializable

data class HomepageResponse(
        val totalFinanceUser: TotalFinanceUser,
        val data: List<Data>
    ): Serializable {
    data class TotalFinanceUser(
            val total_income: Int,
            val total_spend: Int
    )

    data class Data(
            val date: String,
            val income: Int,
            val spend: Int,
            val financePerDay: List<FinancePerDay>
    ) {
        data class FinancePerDay(
                val id: String,
                val id_category: String,
                val code: String,
                val type_financial: String,
                val nominal: Long,
                val note: String,
                val date: Long,
                val image_category: String,
                val image_category_url: String,
                val titleCategory: String,
                val created_at: Long,
                val updated_at: Long
        ): Serializable

    }
}