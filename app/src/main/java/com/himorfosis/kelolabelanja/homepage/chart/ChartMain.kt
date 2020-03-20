package com.himorfosis.kelolabelanja.homepage.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.himorfosis.kelolabelanja.homepage.chart.adapter.ChartPagerAdapter
import com.himorfosis.kelolabelanja.R
import kotlinx.android.synthetic.main.view_pager_layout.*
import kotlinx.android.synthetic.main.toolbar_title.*

class ChartMain: Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_pager_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()

        chart_pager.adapter = ChartPagerAdapter(childFragmentManager)
        chart_tablayout.setupWithViewPager(chart_pager)

    }

    private fun setToolbar() {

        titleBar_tv.text = "Laporan"
    }

}