package com.himorfosis.kelolabelanja.homepage.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.homepage.report.adapter.ReportsAdapter
import com.himorfosis.kelolabelanja.homepage.statistict.model.FinancialProgressModel
import com.himorfosis.kelolabelanja.month_picker.MonthPickerLiveData
import com.himorfosis.kelolabelanja.utilities.AlertMonthAdapter
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.reports_spending_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class ReportsSpendingFragment : Fragment() {

    var TAG = "ReportsSpendingFragment"

    lateinit var reportsAdapter: ReportsAdapter


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

        setDataDateToday()

        setAdapterFinancial()


    }

    private fun setActionClick() {

        select_month_click_ll.setOnClickListener {



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

    private fun getDataSpending(): List<FinancialProgressModel> {

        return listOf(

                FinancialProgressModel("Makanan", 100, 100),
                FinancialProgressModel("Hiburan", 70, 100),
                FinancialProgressModel("Tiket", 30, 100),
                FinancialProgressModel("Developer", 50, 100)

        )

    }

    private fun setAdapterFinancial() {

        Util.log(TAG, "adapter")

        // get data spending
        val data = getDataSpending()

        reportsAdapter = ReportsAdapter(requireContext())

        if (data.isEmpty()) {

            frame_data_spending.visibility = View.INVISIBLE
            status_data_tv.visibility = View.VISIBLE
            status_data_tv.setText("Tidak Ada Transaksi Di Bulan Ini")


        } else {

            // show frame data
            frame_data_spending.visibility = View.VISIBLE

            // sorted list
            var listData = data.sortedWith(compareByDescending { it.total_nominal_category })

            recycler_reports.apply {

                layoutManager = LinearLayoutManager(requireContext())
                reportsAdapter.addAll(listData)
                adapter = reportsAdapter

            }

        }

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

//                // remove data adapter
//                adapterReportsGroup.removeListAdapter()
//
//                // get data month on year selected
//                getAllDataSelectedMonth()
//
//                setAdapterGroup()

            }

        })

    }

}