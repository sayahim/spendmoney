package com.himorfosis.kelolabelanja.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy
import android.content.Context
import androidx.room.Room
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity

@Database(entities = [FinancialEntitiy::class, CategoryEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun databaseDao(): DatabaseDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                                    AppDatabase::class.java, "financial_db")
                            .build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }

}