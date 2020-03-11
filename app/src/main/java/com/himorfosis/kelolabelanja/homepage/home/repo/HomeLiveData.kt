package com.himorfosis.kelolabelanja.homepage.home.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.himorfosis.kelolabelanja.database.db.AppDatabase
import com.himorfosis.kelolabelanja.database.db.DatabaseDao
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy
import com.himorfosis.kelolabelanja.utilities.Util
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeLiveData {

    companion object {

        private val TAG = "HomeLiveData"

        // rx java
        val compositeDisposable = CompositeDisposable()

        // database
        private var notesDatabase: AppDatabase? = null

        private var INSTANCE: HomeLiveData? = null
        fun getInstance() = INSTANCE
                ?: HomeLiveData().also {
                    INSTANCE = it
                }
    }

    private fun setLocalDatabase(context: Context) {

        notesDatabase = AppDatabase.getInstance(context)

    }

    fun fetchFinancialsUser(context: Context):MutableLiveData<FinancialEntitiy> {

        setLocalDatabase(context)

        var dataResponse = MutableLiveData<FinancialEntitiy>()

        val month = Util.getData("picker", "month", context)
        val year = Util.getData("picker", "year", context)

        val dateStart:String = "01-$year-$month"
        val dateFinish:String = "32-$year-$month"

//        compositeDisposable.add(notesDatabase!!.databaseDao().getDataFinancialDate(dateStart, dateFinish)
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//
//                    listDataNote.clear()
//
//                    // add data to adapter
//                    noteAdapter.addAll(it)
//
//                    dataResponse.value =
//
//                })

        return dataResponse

    }

}