package com.himorfosis.kelolabelanja.reports.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.data_sample.FinancialsData
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.homepage.chart.repo.ReportViewModel
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.reports.adapter.CustomMarker
import com.himorfosis.kelolabelanja.reports.adapter.ReportsAdapter
import com.himorfosis.kelolabelanja.reports.adapter.ReportsDetailAdapter
import com.himorfosis.kelolabelanja.reports.repo.ReportsViewModel
import com.himorfosis.kelolabelanja.state.HomeState
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_report_detail.*
import kotlinx.android.synthetic.main.layout_status_failure.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick


class ReportDetailActivity : AppCompatActivity() {

    private val TAG = "ReportDetailActivity"
    lateinit var reportsAdapter: ReportsDetailAdapter
    lateinit var reportsViewModel: ReportsViewModel
    lateinit var viewModel: ReportViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_detail)

        setToolbar()
        reportsViewModel = ViewModelProviders.of(this)[ReportsViewModel::class.java]
        viewModel = ViewModelProviders.of(this)[ReportViewModel::class.java]
        setAdapter()
        fetchReportDetailFinancePerCategory()
//        fetchDataReports()
        costumChart()
        isLog("")

    }

    private fun fetchReportDetailFinancePerCategory() {

        val getIdCategory = intent.getStringExtra("id_category")
        isLog("id category : $getIdCategory")
        viewModel.fectchReportFinanceCategoryDetail(getIdCategory)
        viewModel.reportFinanceCategoryDetailResponse.observe(this, Observer {
            when (it) {
                is StateNetwork.OnSuccess -> {
                    if (it.data.isNotEmpty()) {
                        reportsAdapter.addAll(it.data)
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

//    private fun fetchReportDataByTypeFinance(typeFinance: String) {
//
//        reportsViewModel.fetchReportFinancials(typeFinance)
//        reportsViewModel.fetchReportFinancialsResponse.observe(this, Observer {
//
//            if (it != null) {
//                Util.log(TAG, "data not null")
//
//                if (it.isNotEmpty()) {
//
//                    Util.log(TAG, "data available")
//
//                    reportsAdapter.addAll(it, it[0].total_nominal_category.toLong())
////                    status_report_tv.visibility = View.GONE
//                }
//            } else {
//                Util.log(TAG, "data null")
////                status_report_tv.text = StatusData.notFound
////                status_report_tv.visibility = View.VISIBLE
//            }
//        })
//
//    }

    private fun setAdapter() {

        reportsAdapter = ReportsDetailAdapter()
        recycler_report_detail.apply {
            layoutManager = LinearLayoutManager(this@ReportDetailActivity)
            adapter = reportsAdapter
        }

    }

    private fun costumChart() {

        // set data
        val entries: ArrayList<Entry> = ArrayList()
        entries.add(Entry(0f, 4f))
        entries.add(Entry(5f, 1f))
        entries.add(Entry(10f, 2f))
        entries.add(Entry(15f, 4f))
        entries.add(Entry(20f, 10f))
        entries.add(Entry(25f, 5f))
        entries.add(Entry(30f, 20f))


        // handle view chart
        line_chart_view.setTouchEnabled(false)
        line_chart_view.setPinchZoom(false)
        line_chart_view.setDrawGridBackground(false)

        line_chart_view.xAxis.setDrawGridLines(false)
        line_chart_view.axisRight.setDrawGridLines(false)
        line_chart_view.axisLeft.setDrawGridLines(false)

        line_chart_view.xAxis.setDrawAxisLine(false)

        line_chart_view.xAxis.setDrawGridLinesBehindData(false)
        line_chart_view.axisRight.setDrawGridLinesBehindData(false)
        line_chart_view.axisLeft.setDrawGridLinesBehindData(false)

        line_chart_view.axisRight.isEnabled = false
        line_chart_view.axisLeft.isEnabled = false

        line_chart_view.xAxis.setCenterAxisLabels(false)

        line_chart_view.xAxis.position = XAxis.XAxisPosition.BOTTOM
//        val rightYAxis: YAxis = line_chart_view.axisRight
//        rightYAxis.isEnabled = false
        line_chart_view.legend.isEnabled = false
        line_chart_view.highlightValues(null)

        line_chart_view.axisLeft.setDrawLabels(false)
        line_chart_view.axisRight.setDrawLabels(false)
//        line_chart_view.xAxis.setDrawLabels(false)
        line_chart_view.description.text = ""


        // show data in line
        val dataSet = LineDataSet(entries, "Customized values")
        dataSet.color = ContextCompat.getColor(this, R.color.text_black_primary)
        dataSet.setDrawCircleHole(true)
        dataSet.setCircleColor(R.color.text_black_primary)

        dataSet.valueTextColor = ContextCompat.getColor(this, R.color.text_black_second)
        dataSet.setDrawHorizontalHighlightIndicator(false)
        dataSet.setDrawVerticalHighlightIndicator(false)
        dataSet.setDrawHighlightIndicators(false)
//        dataSet.highLightColor(R.color.black)

        // costum view line
        val markerView = CustomMarker(this@ReportDetailActivity, R.layout.maker_view)
        line_chart_view.marker = markerView

        // Setting Data
        val data = LineData(dataSet)
        line_chart_view.data = data
        line_chart_view.animateX(0)

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
        title_status_tv.text = getString(R.string.data_transaction_not_available)
        description_status_tv.text = getString(R.string.data_transaction_not_available_message)
    }

    private fun isLog(message: String) {
        Util.log(TAG, message)
    }

    private fun actionBackpressed() {
        finish()
    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)

        backBar_btn.onClick {
            actionBackpressed()
        }

        titleBar_tv.text = "Detail Reports"
    }

    override fun onBackPressed() {
        actionBackpressed()
    }

}
