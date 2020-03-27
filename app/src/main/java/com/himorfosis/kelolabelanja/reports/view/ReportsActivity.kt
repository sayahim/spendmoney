package com.himorfosis.kelolabelanja.reports.view

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.data_sample.FinancialsData
import com.himorfosis.kelolabelanja.month_picker.DialogMonthPicker
import com.himorfosis.kelolabelanja.reports.adapter.ReportsAdapter
import com.himorfosis.kelolabelanja.reports.model.ReportsDataModel
import com.himorfosis.kelolabelanja.reports.repo.ReportsViewModel
import com.himorfosis.kelolabelanja.state.StatusData
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.reports_activity.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReportsActivity : AppCompatActivity() {

    lateinit var reportsAdapter: ReportsAdapter

    // view model
    lateinit var reportsViewModel: ReportsViewModel

    private val TAG = "ReportsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reports_activity)

        setToolbar()
        setAdapter()
        setViewModel()
        fetchDataReports()

    }

    private fun setViewModel() {
        reportsViewModel = ViewModelProviders.of(this)[ReportsViewModel::class.java]
    }

    private fun fetchDataReports() {

        val getType = intent.getStringExtra("type")

        Util.log(TAG, "type : $getType")

        if (getType == FinancialsData.SPEND_TYPE) {
            fetchReportDataByTypeFinance(FinancialsData.SPEND_TYPE)
        } else if (getType == FinancialsData.INCOME_TYPE) {
            fetchReportDataByTypeFinance(FinancialsData.INCOME_TYPE)
        } else {
            status_report_tv.text = StatusData.notFound
            status_report_tv.visibility = View.VISIBLE
        }

    }

    private fun fetchReportDataByTypeFinance(typeFinance: String) {

        reportsViewModel.fetchReportFinancials(typeFinance)
        reportsViewModel.fetchReportFinancialsResponse.observe(this, Observer {

            if (it != null) {

                if (it.isNotEmpty()) {

                    var collectingData : MutableList<ReportsDataModel> = ArrayList<ReportsDataModel>()

                    for (position in 0 until it.size) {
                        if (position < 5) {
                            collectingData.add(it[position])
                        } else {
                            break
                        }
                    }

                    reportsAdapter.addAll(collectingData, collectingData[0].total_nominal_category.toLong())
                    status_report_tv.visibility = View.GONE
                }
            } else {
                status_report_tv.text = StatusData.notFound
                status_report_tv.visibility = View.VISIBLE
            }
        })

    }

    private fun setAdapter() {

        reportsAdapter = ReportsAdapter()

        recycler_reports.apply {
            layoutManager = LinearLayoutManager(this@ReportsActivity)
            adapter = reportsAdapter
        }

        reportsAdapter.setOnclick(object : ReportsAdapter.OnClickItem {
            override fun onItemClicked(data: ReportsDataModel) {
                startActivity(Intent(this@ReportsActivity, ReportDetailActivity::class.java))
            }
        })

    }

    private fun dialogMonthPicker() {

        val dialog = DialogMonthPicker(this)
        dialog.show(supportFragmentManager, "dialog")
        dialog.setOnclick(object : DialogMonthPicker.OnClickItem {
            override fun onItemClicked(data: Boolean) {
                if (data) {
                    setTitleToolbar()
                    // reload data
//                    getDataMonthSelected()

                }
            }
        })

    }

    private fun setTitleToolbar() {

        val getYearSelected = Util.getData("picker", "year", this@ReportsActivity)
        val getMonthSelected = Util.getData("picker", "month", this@ReportsActivity)
        val dateYear = SimpleDateFormat("yyyy")
        val year = dateYear.format(Date())

        // show data
        if (getYearSelected == year) {
            val thisMonth = Util.convertMonthName(getMonthSelected)
            titleBar_tv.text = "Report $thisMonth"

        } else {
            val thisMonth = Util.convertMonthName(getMonthSelected)
            titleBar_tv.text = "Report $thisMonth $getYearSelected"
        }
    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)

        setTitleToolbar()

        action_btn.visibility = View.VISIBLE
        action_btn.setImageResource(R.drawable.ic_date_gray)

        action_btn.setOnClickListener {

            dialogMonthPicker()
        }

        backBar_btn.setOnClickListener {
            finish()
        }
    }

}