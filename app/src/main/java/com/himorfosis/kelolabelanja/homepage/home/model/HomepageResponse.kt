package com.himorfosis.kelolabelanja.homepage.home.model

data class HomepageResponse(
        val totalFinanceUser: TotalFinanceUser,
        val data: List<Data>
) {
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
                val id: Int,
                val id_category: Int,
                val id_user: Int,
                val code: String,
                val type_financial: String,
                val nominal: Int,
                val note: String,
                val created_at: String,
                val updated_at: String,
                val category: Category
        )

        data class Category(
                val title: String,
                val description: String,
                val type_category: String,
                val image_category_url: String
        )
    }
}