package com.himorfosis.kelolabelanja.financial.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.himorfosis.kelolabelanja.database.db.AppDatabase
import com.himorfosis.kelolabelanja.database.db.StateDB
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy

class FinancialLiveData {

    companion object {

        private val TAG = "FinancialRepo"

        private var appDatabase: AppDatabase? = null

        private var INSTANCE: FinancialLiveData? = null
        fun getInstance() = INSTANCE
                ?: FinancialLiveData().also {
                    INSTANCE = it

                }
    }

    fun setLocalDatabase(context: Context) {
        appDatabase = AppDatabase.getInstance(context)

    }

    fun destroyDatabase() {
        AppDatabase.destroyInstance()
    }

    fun createFinanceDatabase(context: Context, data: FinancialEntitiy):MutableLiveData<String> {

        var  dataResponse = MutableLiveData<String>()

        if (data != null) {

            setLocalDatabase(context)
            appDatabase!!.dbDao().insertSpending(data)

            dataResponse.value = StateDB.onSuccess.status

        } else {
            dataResponse.value = null
        }

        destroyDatabase()

        return dataResponse

    }

    fun fetchAllFinanceFatabase(context: Context):MutableLiveData<MutableList<FinancialEntitiy>> {

        var dataResponse = MutableLiveData<MutableList<FinancialEntitiy>>()

        setLocalDatabase(context)

        val listData = appDatabase!!.dbDao().getAllDataFinance()

        if (listData.isEmpty()) {
            dataResponse.value = listData
        } else {
            dataResponse.value = null
        }

        destroyDatabase()

        return dataResponse
    }

    fun fetchFinanceByDateDatabase() {



    }


}