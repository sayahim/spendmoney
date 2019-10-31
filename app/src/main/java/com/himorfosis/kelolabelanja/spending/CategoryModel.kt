package com.himorfosis.kelolabelanja.spending

import androidx.room.ColumnInfo

data class CategoryModel (
        @ColumnInfo(name = "id") var id: Int,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "image_category") var image_category: Int,
        @ColumnInfo(name = "selected") var selected: Int
)
