package com.himorfosis.kelolabelanja.homepage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class HomeViewModel(application: Application) : AndroidViewModel(application){

//    var listSpending: LiveData<List<SpendingEntitiy>>
//    private val spendingDatabase: SpendingDatabase


//    init {
//        spendingDatabase = SpendingDatabase.getDatabase(this.getApplication())
////        listSpending = spendingDatabase.spendingDao().getAllSpending()
//    }
//
//    fun getListSpendingToday(): LiveData<List<SpendingEntitiy>> {
//        return listSpending
//    }
//
//    fun addSpending(data: SpendingEntitiy) {
//        addAsynTask(spendingDatabase).execute(data)
//    }
//
//
//    class addAsynTask(db: SpendingDatabase) : AsyncTask<SpendingEntitiy, Void, Void>() {
//        private var spendingDatabase = db
//        override fun doInBackground(vararg params: SpendingEntitiy): Void? {
//            spendingDatabase.spendingDao().insertSpending(params[0])
//            return null
//        }
//
//    }
//

}