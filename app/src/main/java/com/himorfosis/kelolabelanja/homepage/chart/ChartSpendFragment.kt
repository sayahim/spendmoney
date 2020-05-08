package com.himorfosis.kelolabelanja.homepage.chart

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
import com.himorfosis.kelolabelanja.homepage.chart.repo.ReportViewModel
import com.himorfosis.kelolabelanja.network.config.ConnectionDetector
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.reports.view.ReportDetailActivity
import com.himorfosis.kelolabelanja.reports.view.ReportsActivity
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.date.DateSet
import kotlinx.android.synthetic.main.chart_fragment.*
import kotlinx.android.synthetic.main.layout_status_failure.*
import org.jetbrains.anko.sdk27.coroutines.onClick
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
        loading_chart_shimmer.startShimmerAnimation()

        see_all_report_tv.onClick {
            startActivity(intentFor<ReportsActivity>(
                    "type" to FinancialsData.SPEND_TYPE
            ))
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
                        "id_category" to data.id
                ))

            }
        })

    }

    private fun fetchReportCategory() {

        if (activity != null) {

            isLog("fetch report category spend")
            if (ConnectionDetector.isConnectingToInternet(requireContext())) {
                reportViewModel.fetchReportFinanceCategory("spend")
                reportViewModel.reportFinanceCategoryResponse.observe(viewLifecycleOwner, Observer {
                    isLoadingStop()
                    when (it) {
                        is StateNetwork.OnSuccess -> {
                            if (it.data.isNotEmpty()) {
                                layout_chart_ll.visibility = View.VISIBLE
                                fetchChartReport(it.data)
                                val list = it.data.sortedWith(compareByDescending { it.total_nominal.toInt() })
                                adapterReportChart.addAll(list)
                            } else {
                                onFailure(
                                        getString(R.string.data_not_available),
                                        getString(R.string.data_not_available_message))
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
            } else {
                onDisconnect()
            }

        }
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
            }
        })

    }

    private fun isLoadingStop() {
        loading_chart_shimmer.visibility = View.GONE
    }

    private fun onFailure(title: String, description: String) {
        isLoadingStop()
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE

        title_status_tv.text = title
        description_status_tv.text = description
        isLog("Response Failed")
    }

    fun onDisconnect() {
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE
        title_status_tv.text = getString(R.string.disconnect)
        description_status_tv.text = getString(R.string.disconnect_message)
    }

    fun isLog(message: String) {
        Util.log("Chart spend", message)
    }

}