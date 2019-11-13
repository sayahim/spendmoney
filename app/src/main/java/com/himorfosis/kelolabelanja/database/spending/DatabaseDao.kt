package com.himorfosis.kelolabelanja.database.spending

import androidx.room.*
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy

@Dao
interface DatabaseDao {

    @Query("SELECT * FROM financial_db")
    fun getAllSpending(): List<FinancialEntitiy>

    @Query("SELECT * FROM financial_db WHERE category_id =:category_id")
    fun getSelectedCategory(category_id: String): List<FinancialEntitiy>

    @Query("SELECT * FROM financial_db WHERE date =:date ")
    fun getTodaySpending(date: String): List<FinancialEntitiy>


    @Query("SELECT * FROM financial_db WHERE date =:date ")
    fun getMonthSpending(date: String): List<FinancialEntitiy>

    @Query("SELECT * FROM financial_db WHERE id =:id ")
    fun getDetailSpending(id: Int): FinancialEntitiy

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpending(financial_db: FinancialEntitiy)

    @Query("DELETE FROM financial_db WHERE id =:id")
    fun deleteSpendingItem(id: Int?)

    @Update
    fun updateSpendingItem(financial_db: FinancialEntitiy)

    // report

    @Query("SELECT * FROM financial_db WHERE date =:date ")
    fun getReportSpendToday(date: String): List<FinancialEntitiy>

    @Query("SELECT * FROM financial_db WHERE date =:date ")
    fun getReportFinanceMounth(date: String): MutableList<FinancialEntitiy>

    // category

    @Insert
    fun insertCategory(category_db: CategoryEntity)

    @Query("SELECT * FROM category_db")
    fun getAllCategory(): List<CategoryEntity>

    @Query("SELECT * FROM category_db WHERE id =:id")
    fun getSelectedCategory(id: Int?): List<CategoryEntity>


}