package com.himorfosis.kelolabelanja.homepage.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.himorfosis.kelolabelanja.database.db.Database
import com.himorfosis.kelolabelanja.database.db.DatabaseDao
import com.himorfosis.kelolabelanja.homepage.model.ReportsIncomeModel
import com.himorfosis.kelolabelanja.homepage.model.ReportsSpendingModel
import com.himorfosis.kelolabelanja.utilities.Util
import java.util.*

class ReportsRepo {

    companion object {

        private val TAG = "ReportsRepo"

        private val dataSpending = MutableLiveData<MutableList<ReportsSpendingModel>>()
        private val dataIncome = MutableLiveData<MutableList<ReportsIncomeModel>>()
        private var dataReportsSpend: MutableList<ReportsSpendingModel> = ArrayList()
        private var dataReportsIncome: MutableList<ReportsIncomeModel> = ArrayList()
        private val imageCategory = MutableLiveData<String>()

        private lateinit var databaseDao: DatabaseDao

        private fun setDatabaseLocal(context: Context) {

            databaseDao = Room.databaseBuilder(context, Database::class.java, Database.DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                    .spendingDao()

        }


        fun getDataSpending(): LiveData<MutableList<ReportsSpendingModel>> {

            return dataSpending

        }

        fun setDataSpending(context: Context) {

            setDatabaseLocal(context)

            // clear cache
            dataReportsSpend.clear()

            val month = Util.getData("picker", "month", context)
            val year = Util.getData("picker", "year", context)

            var monthOnYear = "$year-$month"
            var maxNominal = 0

            for (totalCategoryId in 0 until 20) {

                val dataSpending = databaseDao.reportDataSpending("$monthOnYear-01", "$monthOnYear-32", "spend", totalCategoryId)

                Util.log(TAG, "data spending : $dataSpending")

                if (dataSpending.isNotEmpty()) {

                    var categoryId = 0
                    var categoryName = "-"
                    var categoryImage = "-"
                    var totalNominalPerCategory = 0

                    // looping data to collecting in category
                    for (element in dataSpending) {

                        categoryId = element.category_id
                        categoryName = element.category_name
                        categoryImage = element.category_image
                        totalNominalPerCategory += element.nominal.toInt()

                        if (totalNominalPerCategory > maxNominal) {

                            maxNominal = totalNominalPerCategory

                        }

                    }

                    dataReportsSpend.add(ReportsSpendingModel(categoryId, categoryImage, categoryName, totalNominalPerCategory))

                }

            }

            if (dataReportsSpend.isNotEmpty()) {

                Util.saveDataInt("report", "spend", maxNominal, context)
                dataSpending.value = dataReportsSpend

            } else{

                dataSpending.value = null

            }


        }

        fun setDataIncome(context: Context) {

            setDatabaseLocal(context)

            // clear cache
            dataReportsIncome.clear()

            val month = Util.getData("picker", "month", context)
            val year = Util.getData("picker", "year", context)

            var monthOnYear = "$year-$month"
            var maxNominal = 0

            for (totalCategoryId in 0 until 20) {

                val dataSpending = databaseDao.reportDataSpending("$monthOnYear-01", "$monthOnYear-32", "income", totalCategoryId)

                Util.log(TAG, "data spending : $dataSpending")

                if (dataSpending.isNotEmpty()) {

                    var categoryId = 0
                    var categoryName = "-"
                    var categoryImage = "-"
                    var totalNominalPerCategory = 0

                    // looping data to collecting in category
                    for (element in dataSpending) {

                        categoryId = element.category_id
                        categoryName = element.category_name
                        categoryImage = element.category_image
                        totalNominalPerCategory += element.nominal.toInt()

                        if (totalNominalPerCategory > maxNominal) {

                            maxNominal = totalNominalPerCategory

                        }

                    }

                    dataReportsIncome.add(ReportsIncomeModel(categoryId, categoryName, categoryImage, totalNominalPerCategory))

                }

            }

            if (dataReportsIncome.isNotEmpty()) {

                Util.saveDataInt("report", "income", maxNominal, context)
                dataIncome.value = dataReportsIncome

            } else{

                dataIncome.value = null

            }

        }

        fun getDataIncome(): LiveData<MutableList<ReportsIncomeModel>> {

            return dataIncome

        }


    }

}