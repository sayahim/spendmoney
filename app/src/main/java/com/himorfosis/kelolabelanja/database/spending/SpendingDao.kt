package com.himorfosis.kelolabelanja.database.spending

import androidx.room.*
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import com.himorfosis.kelolabelanja.database.entity.SpendingEntitiy

@Dao
interface SpendingDao {

    @Query("SELECT * FROM spending_db")
    fun getAllSpending(): List<SpendingEntitiy>

    @Query("SELECT * FROM spending_db WHERE category_id =:category_id")
    fun getSelectedCategory(category_id: String): List<SpendingEntitiy>

    @Query("SELECT * FROM spending_db WHERE date =:date ")
    fun getTodaySpending(date: String): List<SpendingEntitiy>


    @Query("SELECT * FROM spending_db WHERE date =:date ")
    fun getMonthSpending(date: String): List<SpendingEntitiy>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpending(spending_db: SpendingEntitiy)

    @Query("DELETE FROM spending_db WHERE id =:id")
    fun deleteSpendingItem(id: Int?)

    @Update
    fun updateSpendingItem(spending_db: SpendingEntitiy)

    // report

    @Query("SELECT * FROM spending_db WHERE date =:date ")
    fun getReportSpendToday(date: String): List<SpendingEntitiy>

    // category

    @Insert
    fun insertCategory(category_db: CategoryEntity)

    @Query("SELECT * FROM category_db")
    fun getAllCategory(): List<CategoryEntity>

    @Query("SELECT * FROM category_db WHERE id =:id")
    fun getSelectedCategory(id: Int?): List<CategoryEntity>


}