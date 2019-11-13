package com.himorfosis.kelolabelanja.homepage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class HomeViewModel(application: Application) : AndroidViewModel(application){

//    var listSpending: LiveData<List<FinancialEntitiy>>
//    private val spendingDatabase: Database
//
//
//    init {
//        spendingDatabase = Database.getDatabase(this.getApplication())
////        listSpending = spendingDatabase.databaseDao().getAllSpending()
//    }
//
//    fun getListSpendingToday(): LiveData<List<FinancialEntitiy>> {
//        return listSpending
//    }
//
//    fun addSpending(data: FinancialEntitiy) {
//        addAsynTask(spendingDatabase).execute(data)
//    }
//
//
//    class addAsynTask(db: Database) : AsyncTask<FinancialEntitiy, Void, Void>() {
//        private var spendingDatabase = db
//        override fun doInBackground(vararg params: FinancialEntitiy): Void? {
//            spendingDatabase.databaseDao().insertSpending(params[0])
//            return null
//        }
//
//    }


}