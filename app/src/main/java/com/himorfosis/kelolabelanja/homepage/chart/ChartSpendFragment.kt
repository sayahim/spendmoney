package com.himorfosis.kelolabelanja.homepage.chart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.data_sample.FinancialsData
import com.himorfosis.kelolabelanja.homepage.chart.adapter.ReportChartAdapter
import com.himorfosis.kelolabelanja.homepage.chart.model.ReportCategoryModel
import com.himorfosis.kelolabelanja.homepage.chart.model.ReportCategoryRequest
import com.himorfosis.kelolabelanja.homepage.chart.repo.ReportViewModel
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.reports.view.ReportDetailActivity
import com.himorfosis.kelolabelanja.reports.view.ReportsActivity
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.date.DateSet
import kotlinx.android.synthetic.main.chart_fragment.*
import kotlinx.android.synthetic.main.layout_status_failure.*
import org.jetbrains.anko.support.v4.intentFor

class ChartSpendFragment : Fragment() {

    // adapter
    lateinit var adapterReportChart: ReportChartAdapter
    lateinit var reportViewModel: ReportViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chart_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeUI()
        setAdapter()
        fetchReportCategory()

    }

    private fun initializeUI() {

        reportViewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
        show_data_month_tv.text = "Data Bulan " + DateSet.getMonthSelected()

        see_all_report_tv.setOnClickListener {
            val post = Intent(requireContext(), ReportsActivity::class.java)
            post.putExtra("type", FinancialsData.SPEND_TYPE)
            startActivity(post)
        }

    }

    private fun setAdapter() {

        adapterReportChart = ReportChartAdapter()
        recycler_report_cart.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterReportChart
        }

        adapterReportChart.setOnclick(object : ReportChartAdapter.OnClickItem {
            override fun onItemClicked(data: ReportCategoryModel) {
                startActivity(intentFor<ReportDetailActivity>(
                        "type" to FinancialsData.SPEND_TYPE
                ))

            }
        })

    }

    private fun fetchReportCategory() {

        reportViewModel.fetchReportFinanceCategory(FinancialsData.SPEND_TYPE)
        reportViewModel.reportFinanceCategoryResponse.observe(viewLifecycleOwner, Observer {
            progress_chart.visibility = View.GONE
            when(it) {
                is StateNetwork.OnSuccess -> {
                    if (it.data.isNotEmpty()) {
                        layout_chart_ll.visibility = View.VISIBLE
                        fetchChartReport(it.data)
                        adapterReportChart.addAll(it.data)
                    } else {
                        onError(
                                getString(R.string.data_not_available),
                                getString(R.string.data_not_available_message)
                        )
                    }

                }

                is StateNetwork.OnError -> onError(it.error, it.message)
                is StateNetwork.OnFailure -> onFailureNetwork()
            }

        })

    }

    private fun fetchChartReport(it: List<ReportCategoryModel>) {

        reportViewModel.fetchchartReportFinancePerCategory(it)
        reportViewModel.chartReportFinancePerCategoryResponse.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {

                pie_chart.description.text = "Data Teratas"
                pie_chart.isRotationEnabled = true

                val pieDataSet = PieDataSet(it, "")
                pieDataSet.setColors(*ColorTemplate.MATERIAL_COLORS)

                val pieData = PieData(pieDataSet)
                pie_chart.data = pieData
                pie_chart.animateXY(50, 50)
                pie_chart.invalidate()

                layout_chart_ll.visibility = View.VISIBLE
                progress_chart.visibility = View.GONE
            }
        })

    }

    fun onError(title: String, message: String) {
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE
        title_status_tv.text = title
        description_status_tv.text = message
    }

    fun onFailureNetwork() {
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE
        title_status_tv.text = getString(R.string.check_connection)
        description_status_tv.text = getString(R.string.check_connection_message)
    }

    fun onDisconnect() {
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE
        title_status_tv.text = getString(R.string.disconnect)
        description_status_tv.text = getString(R.string.disconnect_message)
    }

    fun isLog(message: String) {
        Util.log("Chart", message)
    }

}