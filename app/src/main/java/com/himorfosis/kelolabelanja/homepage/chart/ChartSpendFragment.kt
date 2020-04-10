package com.himorfosis.kelolabelanja.homepage.chart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.data_sample.FinancialsData
import com.himorfosis.kelolabelanja.homepage.chart.adapter.ReportChartAdapter
import com.himorfosis.kelolabelanja.homepage.chart.model.ChartCategoryModel
import com.himorfosis.kelolabelanja.homepage.chart.repo.ChartViewModel
import com.himorfosis.kelolabelanja.reports.view.ReportDetailActivity
import com.himorfosis.kelolabelanja.reports.view.ReportsActivity
import com.himorfosis.kelolabelanja.utilities.date.DateSet
import kotlinx.android.synthetic.main.chart_fragment.*
import org.jetbrains.anko.support.v4.toast

class ChartSpendFragment : Fragment() {

    private val TAG = "ChartSpendFragment"

    // adapter
    lateinit var adapterReportChart: ReportChartAdapter

    lateinit var chartViewModel: ChartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chart_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeUI()
        setAdapter()
        getDataFinancialsCategory()

    }

    private fun initializeUI() {


        chartViewModel = ViewModelProvider(this).get(ChartViewModel::class.java)
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
            override fun onItemClicked(data: ChartCategoryModel) {
                val intent = Intent(context, ReportDetailActivity::class.java)
                intent.putExtra("type", FinancialsData.SPEND_TYPE)
                startActivity(intent)
            }
        })

    }

    private fun getDataFinancialsCategory() {

        chartViewModel.setChartFinancialsByCategory(FinancialsData.SPEND_TYPE)
        chartViewModel.fetchChartFinancialsByCategoryResponse.observe(this, Observer {

            if (it.isNotEmpty()) {
                setDataToShowOnCart(it)
                adapterReportChart.addAll(it, it[0].total_nominal_category!!.toLong())
            } else {
                toast("Data Tidak Tersedia")
            }

        })

    }

    private fun setDataToShowOnCart(listData: List<ChartCategoryModel>) {

        chartViewModel.setShowDataFinancialsCategoryOnChart(listData)
        chartViewModel.showDataFinancialsCategoryOnChart.observe(this, Observer {

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

            } else {

            }

        })

    }


}