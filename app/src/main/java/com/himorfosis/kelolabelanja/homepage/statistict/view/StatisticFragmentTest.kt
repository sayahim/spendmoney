package com.himorfosis.kelolabelanja.homepage.statistict.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.utilities.Util.Companion.log
import java.util.*

class StatisticFragmentTest: Fragment() {

    var TAG = "StatisticFragmentTest"

    companion object {

        private val pieChart: PieChart? = null
        var dataListChart: MutableList<PieEntry> = ArrayList()


        fun newInstance(): StatisticFragmentTest {
            return StatisticFragmentTest()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pie_chart_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPieChart()


    }

//    private fun setDataSpendingOnChart() {
//        statistictViewModel.setDataStatisticChart(context, dataStatistict)
//        statistictViewModel.getDataStatisticChart().observe(this, Observer { response: List<PieEntry?>? ->
//            log(TAG, "setDataSpendingOnChart$response")
//            if (response != null) {
//                dataListChart = response
//
////                Collections.reverse(dataListChart);
//                setPieChart()
//                setAdapterFinancial()
//            }
//        })
//    }

    private fun setPieChart() {

        log(TAG, "setPieChart")

        pieChart!!.description.text = "Data teratas"
        pieChart.isRotationEnabled = true
        val pieDataSet = PieDataSet(dataListChart.toList(), "")
        pieDataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieChart.animateXY(50, 50)
        pieChart.invalidate()

    }

}