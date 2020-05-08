package com.himorfosis.kelolabelanja.reports.view

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.homepage.chart.adapter.ReportChartAdapter
import com.himorfosis.kelolabelanja.homepage.chart.model.ReportCategoryModel
import com.himorfosis.kelolabelanja.homepage.chart.repo.ReportViewModel
import com.himorfosis.kelolabelanja.month_picker.DialogMonthPicker
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.reports.adapter.ReportsAdapter
import com.himorfosis.kelolabelanja.state.HomeState
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.date.DateSet
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.PickerPref
import kotlinx.android.synthetic.main.layout_status_failure.*
import kotlinx.android.synthetic.main.reports_activity.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.intentFor
import java.text.SimpleDateFormat
import java.util.*

class ReportsActivity : AppCompatActivity() {

    lateinit var adapterReport: ReportsAdapter
    lateinit var viewModel: ReportViewModel
    private val TAG = "ReportsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reports_activity)

        viewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
        setToolbar()
        setAdapter()
        fetchReportFinanceCategory()

    }

    private fun fetchReportFinanceCategory() {
        val typeFinance = intent.getStringExtra("type")
        viewModel.fetchReportFinanceCategory(typeFinance)
        viewModel.reportFinanceCategoryResponse.observe(this, Observer {
            when (it) {
                is StateNetwork.OnSuccess -> {
                    if (it.data.isNotEmpty()) {
                        val listData = it.data.sortedWith(compareByDescending { it.total_nominal.toLong() })
                        adapterReport.addAll(listData)
                    } else {
                        onDataEmpty()
                    }
                }
                is StateNetwork.OnError -> onFailure(it.error, it.message)
                is StateNetwork.OnFailure -> {
                    onFailure(
                            getString(R.string.check_connection),
                            getString(R.string.check_connection_message))
                }
            }
        })
    }

    private fun setAdapter() {
        adapterReport = ReportsAdapter()
        recycler_reports.apply {
            layoutManager = LinearLayoutManager(this@ReportsActivity)
            adapter = adapterReport
        }

        adapterReport.setOnclick(object : ReportsAdapter.OnClickItem {
            override fun onItemClicked(data: ReportCategoryModel) {
                startActivity(intentFor<ReportDetailActivity>(
                        "id_category" to data.id
                ))
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

    private fun onFailure(title: String, description: String) {
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE

        title_status_tv.text = title
        description_status_tv.text = description
        isLog("Response Failed")
    }

    private fun onDataEmpty() {
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE
        title_status_tv.text = getString(R.string.data_not_available)
        description_status_tv.text = getString(R.string.data_not_available_message)
    }

    private fun setTitleToolbar() {

        val getYearSelected = DataPreferences.picker.getString(PickerPref.YEAR)
        val getMonthSelected = DataPreferences.picker.getString(PickerPref.MONTH)
        val dateYear = SimpleDateFormat("yyyy")
        val year = dateYear.format(Date())

        // show data
        if (getYearSelected == year) {
            val thisMonth = DateSet.convertMonthName(getMonthSelected!!)
            titleBar_tv.text = "Report $thisMonth"

        } else {
            val thisMonth = DateSet.convertMonthName(getMonthSelected!!)
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
            actionBackpressed()
        }
    }

    private fun isLog(message: String) {
        Util.log(TAG, message)
    }

    private fun actionBackpressed() {
        startActivity(intentFor<HomepageActivity>(
                "home" to HomeState.REPORT
        ))
    }

    override fun onBackPressed() {
        actionBackpressed()
    }

}