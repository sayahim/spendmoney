package com.himorfosis.kelolabelanja.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_selected")
data class CategorySelectedEntity (

        @PrimaryKey(autoGenerate = true) var id: Int,
        @ColumnInfo(name = "category_user_id") var category_user_id: Int,
        @ColumnInfo(name = "category_id") var category_id: Int

)