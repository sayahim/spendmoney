@file:Suppress("DEPRECATION")

package com.himorfosis.kelolabelanja.homepage.chart.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.himorfosis.kelolabelanja.homepage.chart.ChartSpendFragment
import com.himorfosis.kelolabelanja.homepage.report.view.ReportsIncomeFragment
import com.himorfosis.kelolabelanja.homepage.report.view.ReportsSpendingFragment

class ChartPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val pages = listOf(
            ChartSpendFragment(),
            ReportsIncomeFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Pengeluaran"
            else -> "Pendapatan"
        }
    }

}