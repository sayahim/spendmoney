package com.himorfosis.kelolabelanja.database.db

import androidx.room.*
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy

@Dao
interface DatabaseDao {

    // data financial

    @Query("SELECT * FROM financials WHERE category_id =:category_id")
    fun getSelectedCategory(category_id: String): List<FinancialEntitiy>

    @Query("SELECT * FROM financials WHERE date =:date ")
    fun getDataFinanceMonth(date: String): MutableList<FinancialEntitiy>

    @Query("SELECT * FROM financials")
    fun getAllDataFinance(): MutableList<FinancialEntitiy>

    @Query("SELECT * FROM financials WHERE date  BETWEEN:dateStart AND :dateFinish ")
    fun getDataFinanceByDate(dateStart: String, dateFinish: String): MutableList<FinancialEntitiy>

    @Query("SELECT * FROM financials WHERE date BETWEEN :first AND :last")
    fun getDataFinancial(first: String, last: String): List<FinancialEntitiy>

    @Query("SELECT * FROM financials WHERE id =:id ")
    fun getDetailDataFinancial(id: Int): FinancialEntitiy

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpending(financial_db: FinancialEntitiy)

    @Query("DELETE FROM financials WHERE id =:id")
    fun deleteDataFinancialItem(id: Int?)

    @Update
    fun updateSpendingItem(financial_db: FinancialEntitiy)

    // report

    @Query("SELECT * FROM financials WHERE date =:date  AND type =:type AND category_id =:category_id")
    fun getReportSpending(date: String, type: String, category_id: Int): List<FinancialEntitiy>

    @Query("SELECT * FROM financials WHERE type =:type AND category_id =:category_id And date BETWEEN :firstDate AND :lastDate")
    fun reportDataSpending(firstDate: String, lastDate: String, type: String, category_id: Int): List<FinancialEntitiy>

    @Query("SELECT * FROM financials WHERE date =:date ")
    fun getReportFinanceMonth(date: String): MutableList<FinancialEntitiy>

    @Query("SELECT * FROM financials WHERE type =:type AND category_id =:category_id And date =:date")
    fun reportDataDetailFinancial(type: String, category_id: Int, date: String): MutableList<FinancialEntitiy>

    // category

    @Insert
    fun insertCategory(category_db: CategoryEntity)

    @Query("SELECT * FROM category")
    fun getAllCategory(): List<CategoryEntity>

    @Query("SELECT * FROM category WHERE id =:id")
    fun getSelectedCategory(id: Int?): List<CategoryEntity>

    @Query("SELECT * FROM category WHERE id =:id")
    fun getImageSelectedCategory(id: Int?): List<CategoryEntity>

}