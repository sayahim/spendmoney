package com.himorfosis.kelolabelanja.homepage.chart

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.himorfosis.kelolabelanja.homepage.chart.adapter.ChartPagerAdapter
import kotlinx.android.synthetic.main.chart_fragment.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import kotlinx.android.synthetic.main.chart_viewpager.*

class ChartReport: Fragment() {

    companion object {

        fun newInstance(): ChartFragment {
            return ChartFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chart_viewpager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chart_pager.adapter = ChartPagerAdapter(childFragmentManager)
        chart_tablayout.setupWithViewPager(chart_pager)

    }
}