@file:Suppress("DEPRECATION")

package com.himorfosis.kelolabelanja.homepage.chart.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.himorfosis.kelolabelanja.homepage.chart.ChartFragment
import com.himorfosis.kelolabelanja.homepage.report.view.ReportsIncomeFragment
import com.himorfosis.kelolabelanja.homepage.report.view.ReportsSpendingFragment

class ChartPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    // sebuah list yang menampung objek Fragment
    private val pages = listOf(
            ChartFragment(),
            ReportsIncomeFragment(),
            ReportsSpendingFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    // judul untuk tabs
    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Semua"
            1 -> "Pengeluaran"
            else -> "Pendapatan"
        }
    }

}