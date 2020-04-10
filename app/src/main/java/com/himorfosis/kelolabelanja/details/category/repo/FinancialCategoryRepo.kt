package com.himorfosis.kelolabelanja.details.category.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.himorfosis.kelolabelanja.app.MyApp
import com.himorfosis.kelolabelanja.details.category.model.FinancialPerCategoryModel
import com.himorfosis.kelolabelanja.financial.model.FinancialEntitiy
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.preferences.PickerPref
import java.util.ArrayList

class FinancialCategoryRepo {

    companion object {

        private var listDataFinancial: MutableList<FinancialEntitiy> = ArrayList()
        private var listPerDayData: MutableList<FinancialPerCategoryModel> = ArrayList()

        private val dataFinancialCategory = MutableLiveData<MutableList<FinancialPerCategoryModel>>()

        fun setDataFinancialCategoryDatabase(context: Context, type: String, categoryId: String) {

            val month = MyApp.picker.getString(PickerPref.MONTH)
            val year = MyApp.picker.getString(PickerPref.YEAR)

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

//                val data = databaseDao.reportDataDetailFinancial(type, categoryId.toInt(), thisMonth)
//
//                if (data.isNotEmpty()) {
//
//                    var totalNominal = 0
//
//                    for (y in 0 until data.size) {
//
//                        val item = data[y]
//
//                        totalNominal += item.nominal.toInt()
//
//                    }
//
//                    listDataFinancial.addAll(data)
//                    listPerDayData.add(FinancialPerCategoryModel(thisMonth, totalNominal, data))
//
//                }

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