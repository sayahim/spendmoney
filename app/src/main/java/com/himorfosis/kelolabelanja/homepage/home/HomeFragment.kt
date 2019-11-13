package com.himorfosis.kelolabelanja.homepage.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.database.spending.DatabaseDao
import com.himorfosis.kelolabelanja.database.spending.Database
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy
import com.himorfosis.kelolabelanja.financial.InputFinancial
import com.himorfosis.kelolabelanja.homepage.home.adapter.HomeGroupAdapter
import com.himorfosis.kelolabelanja.homepage.home.model.HomeGroupDataModel
import com.himorfosis.kelolabelanja.month_picker.MonthPickerAdapter
import com.himorfosis.kelolabelanja.month_picker.MonthPickerLiveData
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"

    private var listDataFinancial: MutableList<FinancialEntitiy> = ArrayList()
    private var listPerDayData: MutableList<HomeGroupDataModel> = ArrayList()

    lateinit var databaseDao: DatabaseDao

    lateinit var adapterReportsGroup: HomeGroupAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionClick()

        setLocalDatabase()

        setDataDateToday()

        getAllDataSelectedMonth()

        setAdapterGroup()

//        setCategoryDB()

    }

    private fun setActionClick() {

        add_ll.setOnClickListener {

            startActivity(Intent(context, InputFinancial::class.java))

        }

        select_month_click_ll.setOnClickListener {

            setShowMonthPicker()

        }

    }

    private fun setDataDateToday() {

        val date = SimpleDateFormat("yyyy.MM.dd")

        val dateMonth = SimpleDateFormat("MM")
        val dateYear = SimpleDateFormat("yyyy")

        val today = date.format(Date())
        val yearToday = dateYear.format(Date())
        val monthToday = dateMonth.format(Date())

        Util.saveData("picker", "month", monthToday, requireContext())
        Util.saveData("picker", "year", yearToday, requireContext())

        val thisMonth = Util.convertCalendarMonth(today)

        month_selected_tv.text = thisMonth

    }

    private fun setAdapterGroup() {

        if (listPerDayData.size != 0) {

            adapterReportsGroup = HomeGroupAdapter(requireContext())

            recycler.apply {

                // sorted list
                var sortedListDescending = listPerDayData.sortedWith(compareByDescending { it.date })

                // add data in adapter
                adapterReportsGroup.addAll(sortedListDescending)

                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                adapter = adapterReportsGroup

//                addOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                        super.onScrollStateChanged(recyclerView, newState)
//
//                        val totalItemCount = recyclerView.layoutManager!!.itemCount
//                        if ( totalItemCount == lastVisibleItemPosition + 1) {
//
//                            add_ll.visibility = View.INVISIBLE
//
//                        } else {
//
//                            add_ll.visibility = View.VISIBLE
//
//                        }
//                    }
//
//                })

            }

        }
    }

    private val lastVisibleItemPosition: Int get() = linearLayoutManager.findLastVisibleItemPosition()

    private fun getAllDataSelectedMonth() {

        val month = Util.getData("picker", "month",  requireContext())
        val year = Util.getData("picker", "year",  requireContext())

        Util.log(TAG, "month selected : $month")

        listDataFinancial.clear()
        listPerDayData.clear()

        var monthOnYear = "$year.$month"

        val dayOfMonth = 32

        var thisMonth : String

        for (x in 1 until dayOfMonth) {

            if (x < 10) {

                    thisMonth = "$monthOnYear.0$x"

            } else {

                thisMonth = "$monthOnYear.$x"

            }

            Util.log(TAG, "this month : $thisMonth")

            val data = databaseDao.getReportFinanceMounth(thisMonth)

            if (data.size != 0) {

                listDataFinancial.addAll(data)

                listPerDayData.add(HomeGroupDataModel(thisMonth, 0, 0, data))

            }

        }

        var totalSpend_int = 0
        var totalIncome_int = 0

        if (listDataFinancial.size == 0) {

            status_tv.text = "Tidak Ada Transaksi \n Ketuk + Tambah untuk menambakan satu"
            status_tv.visibility = View.VISIBLE

        } else{

            status_tv.visibility = View.INVISIBLE

            for (i in 0 until listDataFinancial.size) {

                val item = listDataFinancial[i]

                if (item.type == ("spend")) {

                    // type spending
                    totalSpend_int += item.nominal!!.toInt()

                } else {

                    // income
                    totalIncome_int += item.nominal!!.toInt()
                }


            }

        }

        // set text data
        total_spend_today.text = Util.numberFormatMoney(totalSpend_int.toString())
        total_income_month.text = Util.numberFormatMoney(totalIncome_int.toString())


    }


    fun setLocalDatabase() {

        databaseDao = Room.databaseBuilder(requireContext(), Database::class.java, Database.DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .spendingDao()

    }

    private fun setShowMonthPicker() {

        MonthPickerLiveData.setMonthPicker(requireContext())

        MonthPickerLiveData.getDataMonthPicker().observe(this, androidx.lifecycle.Observer{ monthPicker ->

            Util.log(TAG, "month picker : $monthPicker")

            if (monthPicker != null) {

                val getYearSelected = Util.getData("picker", "year", requireContext())
                val dateYear = SimpleDateFormat("yyyy")
                val year = dateYear.format(Date())

                if (getYearSelected.equals(year)) {

                    val thisMonth = Util.convertCalendarMonth("$monthPicker.01")
                    month_selected_tv.text = thisMonth

                } else {

                    val thisMonth = Util.convertCalendarMonth("$monthPicker.01")
                    month_selected_tv.text = "$thisMonth  $getYearSelected"

                }

                // remove data adapter
                adapterReportsGroup.removeListAdapter()

                // get data month on year selected
                getAllDataSelectedMonth()

                setAdapterGroup()

            }

        })

    }



}