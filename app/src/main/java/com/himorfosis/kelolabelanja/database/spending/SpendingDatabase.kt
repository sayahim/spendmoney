package com.himorfosis.kelolabelanja.database.spending

import androidx.room.Database
import androidx.room.RoomDatabase
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import com.himorfosis.kelolabelanja.database.entity.SpendingEntitiy

@Database(entities = [SpendingEntitiy::class, CategoryEntity::class], version = 4, exportSchema = true)
abstract class SpendingDatabase : RoomDatabase() {

    abstract fun spendingDao(): SpendingDao

    companion object {

        var DB_NAME = "spending_db"

    }


}