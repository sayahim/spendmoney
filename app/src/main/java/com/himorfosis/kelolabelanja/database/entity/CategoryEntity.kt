package com.himorfosis.kelolabelanja.database.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "category_db")
data class CategoryEntity(

        @PrimaryKey(autoGenerate = true) var id: Int,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "image_category") var image_category: String,
        @ColumnInfo(name = "selected") var selected: Int
)
