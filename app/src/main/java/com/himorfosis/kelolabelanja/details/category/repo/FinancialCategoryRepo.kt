package com.himorfosis.kelolabelanja.details.category.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.himorfosis.kelolabelanja.database.db.Database
import com.himorfosis.kelolabelanja.database.db.DatabaseDao
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy
import com.himorfosis.kelolabelanja.details.category.model.FinancialPerCategoryModel
import com.himorfosis.kelolabelanja.utilities.Util
import java.util.ArrayList

class FinancialCategoryRepo {

    companion object {

        private var listDataFinancial: MutableList<FinancialEntitiy> = ArrayList()
        private var listPerDayData: MutableList<FinancialPerCategoryModel> = ArrayList()

        private val dataFinancialCategory = MutableLiveData<MutableList<FinancialPerCategoryModel>>()

        private lateinit var databaseDao: DatabaseDao


        private fun setDatabaseLocal(context: Context) {

            databaseDao = Room.databaseBuilder(context, Database::class.java, Database.DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                    .spendingDao()

        }

        fun setDataFinancialCategoryDatabase(context: Context, type: String, categoryId: String) {

            setDatabaseLocal(context)

            val month = Util.getData("picker", "month", context)
            val year = Util.getData("picker", "year", context)

            listDataFinancial.clear()
            listPerDayData.clear()

            var monthOnYear = "$year-$month"

            val dayOfMonth = 32

            var thisMonth: String

            for (x in 1 until dayOfMonth) {

                if (x < 10) {

                    thisMonth = "$monthOnYear-0$x"

                } else {

                    thisMonth = "$monthOnYear-$x"

                }

                val data = databaseDao.reportDataDetailFinancial(type, categoryId.toInt(), thisMonth)

                if (data.isNotEmpty()) {

                    var totalNominal = 0

                    for (y in 0 until data.size) {

                        val item = data[y]

                        totalNominal += item.nominal.toInt()

                    }

                    listDataFinancial.addAll(data)
                    listPerDayData.add(FinancialPerCategoryModel(thisMonth, totalNominal, data))

                }

            }

            if (listPerDayData.isNotEmpty()) {

                dataFinancialCategory.value = listPerDayData

            } else {

                dataFinancialCategory.value = null

            }

        }

        fun getDataFinancialCategoryDatabase(): LiveData<MutableList<FinancialPerCategoryModel>> {

            return dataFinancialCategory

        }

    }

}