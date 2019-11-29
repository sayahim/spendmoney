package com.himorfosis.kelolabelanja.homepage.home.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.himorfosis.kelolabelanja.database.db.Database
import com.himorfosis.kelolabelanja.database.db.DatabaseDao
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy
import com.himorfosis.kelolabelanja.homepage.home.model.HomeGroupDataModel
import com.himorfosis.kelolabelanja.utilities.Util
import java.util.ArrayList

class HomeRepo {

    companion object {

        private val TAG = "HomeRepo"

        private var listDataFinancial: MutableList<FinancialEntitiy> = ArrayList()
        private var listPerDayData: MutableList<HomeGroupDataModel> = ArrayList()

        private val dataFinancial = MutableLiveData<MutableList<HomeGroupDataModel>>()
        private val total_spend_month = MutableLiveData<String>()
        private val total_income_month = MutableLiveData<String>()


        private lateinit var databaseDao: DatabaseDao


        private fun setDatabaseLocal(context: Context) {

            databaseDao = Room.databaseBuilder(context, Database::class.java, Database.DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                    .spendingDao()

        }

        fun setDataFinancialDatabase(context: Context) {

            listDataFinancial.clear()
            listPerDayData.clear()

            setDatabaseLocal(context)

            val month = Util.getData("picker", "month", context)
            val year = Util.getData("picker", "year", context)

            var monthOnYear = "$year-$month"

            val dayOfMonth = 32

            var thisMonth: String

            for (x in 1 until dayOfMonth) {

                if (x < 10) {

                    thisMonth = "$monthOnYear-0$x"

                } else {

                    thisMonth = "$monthOnYear-$x"

                }

//            Util.log(TAG, "this month : $thisMonth")

                val data = databaseDao.getDataFinanceMonth(thisMonth)

                if (data.isNotEmpty()) {

                    var totalSpending = 0
                    var totalIncome = 0

                    for (x in 0 until data.size) {

                        val item = data[x]

                        if (item.type == "income") {

                            totalIncome += item.nominal.toInt()

                        } else {

                            totalSpending += item.nominal.toInt()

                        }

                    }

                    listDataFinancial.addAll(data)
                    listPerDayData.add(HomeGroupDataModel(thisMonth, totalSpending, totalIncome, data))

                }

            }

            var totalSpend_int = 0
            var totalIncome_int = 0

            for (i in 0 until listDataFinancial.size) {

                val item = listDataFinancial[i]

                if (item.type == ("spend")) {

                    if (item.nominal != "") {

                        // type spending
                        totalSpend_int += item.nominal.toInt()

                    }

                } else {

                    if (item.nominal != "") {

                        // income
                        totalIncome_int += item.nominal.toInt()

                    }
                }

            }

            Util.log(TAG, "total spend : $totalSpend_int")
            Util.log(TAG, "total income : $totalIncome_int")
            Util.log(TAG, "listDataFinancial : ${listDataFinancial.size}")

            if (listPerDayData.isNotEmpty()) {

                dataFinancial.value = listPerDayData

            } else {

                dataFinancial.value = null

            }

            if (totalSpend_int != 0) {

                total_spend_month.value = totalSpend_int.toString()

            } else {

                total_spend_month.value = null

            }

            if (totalIncome_int != 0) {

                total_income_month.value = totalIncome_int.toString()

            } else {

                total_income_month.value = null
            }


        }

        fun getDataFinancialDatabase(): LiveData<MutableList<HomeGroupDataModel>> {

            return dataFinancial

        }

        fun getTotalIncomeMonth(): LiveData<String> {

            return total_income_month

        }

        fun getTotalSpendMonth(): LiveData<String> {

            return total_spend_month
        }

    }
}