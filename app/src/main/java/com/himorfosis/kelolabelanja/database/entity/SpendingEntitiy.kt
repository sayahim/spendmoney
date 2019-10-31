package com.himorfosis.kelolabelanja.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "spending_db")
data class SpendingEntitiy (

        @PrimaryKey(autoGenerate = true) var id: Int?,
        @ColumnInfo(name = "category_id") var category_id: Int,
        @ColumnInfo(name = "category_name") var category_name: String,
        @ColumnInfo(name = "category_image") var category_image: Int,
        @ColumnInfo(name = "nominal") var nominal: String,
        @ColumnInfo(name = "note") var note: String,
        @ColumnInfo(name = "date") var date: String,
        @ColumnInfo(name = "time") var time: String

)
