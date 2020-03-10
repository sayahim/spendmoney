package com.himorfosis.kelolabelanja.homepage.report.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy
import com.himorfosis.kelolabelanja.database.db.Database
import com.himorfosis.kelolabelanja.database.db.DatabaseDao
import com.himorfosis.kelolabelanja.homepage.report.adapter.ReportsSpendingAdapter
import com.himorfosis.kelolabelanja.homepage.model.ReportFinanceModel
import com.himorfosis.kelolabelanja.homepage.repo.ReportsRepo
import com.himorfosis.kelolabelanja.month_picker.MonthPickerLiveData
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.reports_spending_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class ReportsSpendingFragment : Fragment() {

    var TAG = "ReportsSpendingFragment"

    lateinit var reportsAdapter: ReportsSpendingAdapter
    private var listDataFinancial: MutableList<FinancialEntitiy> = ArrayList()
    private var listDataReport: MutableList<ReportFinanceModel> = ArrayList()

    lateinit var databaseDao: DatabaseDao

//    private var spendingDataRespons : MutableList<ReportsSpendingModel> = ArrayList()

//    private val spendingDataResponse by lazy {
//        ReportsRepo.getDataSpending()
//    }

    companion object {

        fun newInstance(): ReportsFragment {
            return ReportsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.reports_spending_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setActionClick()

        setLocalDatabase()

        setDataDateToday()

        getDataSpendingUser()

    }

    private fun setActionClick() {

        select_month_click_ll.setOnClickListener {

            setShowMonthPicker()

        }

    }

    private fun getDataSpendingUser() {

        ReportsRepo.setDataSpending(requireContext())

        ReportsRepo.getDataSpending().observe(this, androidx.lifecycle.Observer { response ->

            Util.log(TAG, "response : $response")

            if (response != null) {

                reportsAdapter = ReportsSpendingAdapter(requireContext())

                // clear cache
                reportsAdapter.removeListAdapter()

                if (response.isEmpty()) {

                    showNoticeDataIsEmpty()

                } else {

                    // show data
                    status_data_tv.visibility = View.INVISIBLE
                    status_deskripsi_tv.visibility = View.INVISIBLE
                    frame_data_spending.visibility = View.VISIBLE

                    // sorted list
                    var listData = response.sortedWith(compareByDescending { it.total_nominal_category })

                    recycler_reports.apply {

                        layoutManager = LinearLayoutManager(requireContext())
                        reportsAdapter.addAll(listData)
                        adapter = reportsAdapter

                    }

                }

            } else {

                showNoticeDataIsEmpty()

            }

        })

    }

    private fun showNoticeDataIsEmpty() {

        frame_data_spending.visibility = View.INVISIBLE
        status_data_tv.visibility = View.VISIBLE
        status_deskripsi_tv.visibility = View.VISIBLE

        status_data_tv.text = "Tidak Ada Transaksi"
        status_deskripsi_tv.text = "Untuk bulan ini, tidak ada data yang dapat di tampilkan. Silahkan pilih bulan lain atau tambahkan transaksi"


    }

    fun setLocalDatabase() {

        databaseDao = Room.databaseBuilder(requireContext(), Database::class.java, Database.DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .spendingDao()

    }

    private fun setDataDateToday() {

        val date = SimpleDateFormat("yyyy-MM-dd")

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

    private fun setShowMonthPicker() {

        MonthPickerLiveData.setMonthPicker(requireContext())

        MonthPickerLiveData.getDataMonthPicker().observe(this, androidx.lifecycle.Observer{ monthPicker ->

            Util.log(TAG, "month picker : $monthPicker")

            if (monthPicker != null) {

                val getYearSelected = Util.getData("picker", "year", requireContext())
                val dateYear = SimpleDateFormat("yyyy")
                val year = dateYear.format(Date())

                if (getYearSelected.equals(year)) {

                    val thisMonth = Util.convertCalendarMonth("$monthPicker-01")
                    month_selected_tv.text = thisMonth

                } else {

                    val thisMonth = Util.convertCalendarMonth("$monthPicker-01")
                    month_selected_tv.text = "$thisMonth  $getYearSelected"

                }

                getDataSpendingUser()

            }

        })

    }

}