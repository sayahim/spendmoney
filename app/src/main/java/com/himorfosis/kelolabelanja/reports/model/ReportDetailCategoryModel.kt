package com.himorfosis.kelolabelanja.reports.model

data class ReportDetailCategoryModel(
    val totalNominalReport: Int,
    val reportDay: List<ReportDay>,
    val `data`: List<Data>
) {
    data class ReportDay(
        val day: Int,
        val total: Int,
        val percent: Int
    )

    data class Data(
        val id: String,
        val id_category: String,
        val type_finance: String,
        val nominal: Int,
        val note: String,
        val date: Long,
        val title: String,
        val image_category: String,
        val image_category_url: String
    )
}