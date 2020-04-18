package com.himorfosis.kelolabelanja.category.model

data class AssetsModel(
    val id: String,
    val title: String,
    val assets: List<Asset>
) {
    data class Asset(
        val id: Int,
        val id_assets_category: Int,
        val image_assets: String,
        val image_assets_url: String
    )
}