package com.himorfosis.kelolabelanja.homepage.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.homepage.report.adapter.ReportsIncomeAdapter
import com.himorfosis.kelolabelanja.homepage.report.adapter.ReportsSpendingAdapter
import com.himorfosis.kelolabelanja.homepage.report.repo.ReportsRepo
import com.himorfosis.kelolabelanja.homepage.statistict.model.FinancialProgressStatisticModel
import com.himorfosis.kelolabelanja.month_picker.MonthPickerLiveData
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.report_income_fragment.*
import kotlinx.android.synthetic.main.report_income_fragment.month_selected_tv
import kotlinx.android.synthetic.main.report_income_fragment.recycler_reports
import kotlinx.android.synthetic.main.report_income_fragment.select_month_click_ll
import kotlinx.android.synthetic.main.report_income_fragment.status_data_tv
import kotlinx.android.synthetic.main.reports_spending_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class ReportsIncomeFragment : Fragment() {

    private val TAG = "ReportsIncomeFragment"

    lateinit var reportsIncomeAdapter: ReportsIncomeAdapter

    companion object {

        fun newInstance(): ReportsFragment {
            return ReportsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.report_income_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        status_data_tv.visibility = View.VISIBLE

        setActionClick()

        setDataDateToday()

//        getDataIncome()

//        setAdapterFinancial()

        getDataSelectedRepo()

    }

    private fun setActionClick() {

        select_month_click_ll.setOnClickListener {

            setShowMonthPicker()

        }

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

    private fun getDataSelectedRepo() {

        ReportsRepo.setDataIncome(requireContext())

        ReportsRepo.getDataIncome().observe(this, androidx.lifecycle.Observer { response ->

            Util.log(TAG, "response : $response")

            if (response != null) {

                reportsIncomeAdapter = ReportsIncomeAdapter(requireContext())

                // clear cache
                reportsIncomeAdapter.removeListAdapter()

                if (response.isEmpty()) {

                    frame_data_spending.visibility = View.INVISIBLE
                    status_data_tv.visibility = View.VISIBLE
                    status_data_tv.setText("Tidak Ada Transaksi Di Bulan Ini")

                } else {

                    // show data
                    status_data_tv.visibility = View.INVISIBLE
                    frame_data_spending.visibility = View.VISIBLE

                    // sorted list
                    var listData = response.sortedWith(compareByDescending { it.total_nominal_category })

                    recycler_reports.apply {

                        layoutManager = LinearLayoutManager(requireContext())
                        reportsIncomeAdapter.addAll(listData)
                        adapter = reportsIncomeAdapter

                    }

                }

            } else {

                frame_data_spending.visibility = View.INVISIBLE
                status_data_tv.visibility = View.VISIBLE
                status_data_tv.setText("Tidak Ada Transaksi Di Bulan Ini")

            }

        })

    }

    private fun getDataIncome(): List<FinancialProgressStatisticModel> {

        return listOf(

//                FinancialProgressStatisticModel("Gaji", 100),
//                FinancialProgressStatisticModel("Penjualan", 60),
//                FinancialProgressStatisticModel("Bonus", 20)

        )

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

                getDataSelectedRepo()

            }

        })

    }

    private fun setAdapterFinancial() {

        // get data spending
        val data = getDataIncome()

        reportsIncomeAdapter = ReportsIncomeAdapter(requireContext())

        if (data.isEmpty()) {

            frame_data_income.visibility = View.INVISIBLE
            status_data_tv.visibility = View.VISIBLE
            status_data_tv.setText("Tidak Ada Transaksi Di Bulan Ini")

        } else {

            // show frame data
            frame_data_income.visibility = View.VISIBLE

            // sorted list
            var listData = data.sortedWith(compareByDescending { it.total_nominal_category })

            recycler_reports.apply {

                layoutManager = LinearLayoutManager(requireContext())
//                reportsIncomeAdapter.addAll(listData)
                adapter = reportsIncomeAdapter

            }

        }

    }
}