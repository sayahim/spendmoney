package com.himorfosis.kelolabelanja.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_user")
data class CategoryUserEntity (

        @PrimaryKey(autoGenerate = true) var category_user_id: Int,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "image_category") var image_category: String,
        @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var image_assest: Byte,
        @ColumnInfo(name = "user_id") var user_id: Int

)