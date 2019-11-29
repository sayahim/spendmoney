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
    fun getDataFinanceMonth(date: String): MutableList<FinancialEntitiy>

    @Query("SELECT * FROM financial_db WHERE date BETWEEN :first AND :last")
    fun getDataFinancial(first: String, last: String): List<FinancialEntitiy>

    @Query("SELECT * FROM financial_db WHERE id =:id ")
    fun getDetailSpending(id: Int): FinancialEntitiy

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpending(financial_db: FinancialEntitiy)

    @Query("DELETE FROM financial_db WHERE id =:id")
    fun deleteSpendingItem(id: Int?)

    @Update
    fun updateSpendingItem(financial_db: FinancialEntitiy)

    // report

    @Query("SELECT * FROM financial_db WHERE date =:date  AND type =:type AND category_id =:category_id")
    fun getReportSpending(date: String, type: String, category_id: Int): List<FinancialEntitiy>

    @Query("SELECT * FROM financial_db WHERE date BETWEEN :firstDate AND :lastDate  AND type =:type AND category_id =:category_id")
    fun reportDataSpending(firstDate: String, lastDate: String, type: String, category_id: Int): List<FinancialEntitiy>

    @Query("SELECT * FROM financial_db WHERE date =:date ")
    fun getReportFinanceMonth(date: String): MutableList<FinancialEntitiy>

    // category

    @Insert
    fun insertCategory(category_db: CategoryEntity)

    @Query("SELECT * FROM category_db")
    fun getAllCategory(): List<CategoryEntity>

    @Query("SELECT * FROM category_db WHERE id =:id")
    fun getSelectedCategory(id: Int?): List<CategoryEntity>


}