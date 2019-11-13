package com.himorfosis.kelolabelanja.database.spending

import androidx.room.Database
import androidx.room.RoomDatabase
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy

@Database(entities = [FinancialEntitiy::class, CategoryEntity::class], version = 1, exportSchema = true)
abstract class Database : RoomDatabase() {

    abstract fun spendingDao(): DatabaseDao

    companion object {

        var DB_NAME = "spending_db"

    }

}