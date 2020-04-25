package com.himorfosis.kelolabelanja.reports.model

class ReportCategoryDetailsModel(
        val id: Int,
        val id_category: Int,
        val id_user: Int,
        val code: String,
        val type_financial: String,
        val nominal: Int,
        val note: String,
        val created_at: Long,
        val updated_at: Long,
        val category: Category
) {
    data class Category(
            val id: Int,
            val title: String,
            val type_category: String,
            val image_category: String,
            val id_user_category: Int,
            val image_category_url: String,
            val created_at: Long,
            val updated_at: Long
    )
}
