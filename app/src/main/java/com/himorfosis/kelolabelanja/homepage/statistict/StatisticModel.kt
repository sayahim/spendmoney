package com.himorfosis.kelolabelanja.homepage.statistict

import androidx.room.ColumnInfo

data class StatisticModel (

        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "value") var value: Int

        )