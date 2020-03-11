package com.himorfosis.kelolabelanja.homepage.report.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.dialog.DialogShow
import com.himorfosis.kelolabelanja.homepage.report.adapter.ReportsIncomeAdapter
import com.himorfosis.kelolabelanja.homepage.repo.ReportsRepo
import com.himorfosis.kelolabelanja.month_picker.MonthPickerLiveData
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.report_income_fragment.*
import kotlinx.android.synthetic.main.report_income_fragment.month_selected_tv
import kotlinx.android.synthetic.main.report_income_fragment.recycler_reports
import kotlinx.android.synthetic.main.report_income_fragment.select_month_click_ll
import kotlinx.android.synthetic.main.report_income_fragment.status_data_tv
import kotlinx.android.synthetic.main.report_income_fragment.status_deskripsi_tv
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

        getDataSelectedRepo()

    }

    private fun setActionClick() {

        select_month_click_ll.setOnClickListener {

//            setShowMonthPicker()

            dialogMonthPicker()

        }

    }

    private fun dialogMonthPicker() {

        val dialog = DialogShow.MonthPicker(context!!)
        dialog.show(childFragmentManager, "dialog")

        dialog.setOnclick(object: DialogShow.MonthPicker.OnClickItem {
            override fun onItemClicked(data: Boolean) {
                if (data) {
                    // reload data
                }
            }
        })

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

                    showNoticeDataIsEmpty()

                } else {

                    // show data
                    status_data_tv.visibility = View.INVISIBLE
                    status_deskripsi_tv.visibility = View.INVISIBLE
                    frame_data_income.visibility = View.VISIBLE

                    // sorted list
                    var listData = response.sortedWith(compareByDescending { it.total_nominal_category })

                    recycler_reports.apply {

                        layoutManager = LinearLayoutManager(requireContext())
                        reportsIncomeAdapter.addAll(listData)
                        adapter = reportsIncomeAdapter

                    }

                }

            } else {

                showNoticeDataIsEmpty()

            }

        })

    }

    private fun showNoticeDataIsEmpty() {

        frame_data_income.visibility = View.INVISIBLE
        status_data_tv.visibility = View.VISIBLE
        status_deskripsi_tv.visibility = View.VISIBLE

        status_data_tv.text = "Tidak Ada Transaksi"
        status_deskripsi_tv.text = "Untuk bulan ini, tidak ada data yang dapat di tampilkan. Silahkan pilih bulan lain atau tambahkan transaksi"

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

}