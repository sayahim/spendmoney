package com.himorfosis.kelolabelanja.financial.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.himorfosis.kelolabelanja.financial.input_data.InputDataFragmentIncome
import com.himorfosis.kelolabelanja.financial.input_data.InputDataFragmentSpend

class FinancePagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val pages = listOf(
            InputDataFragmentSpend(),
            InputDataFragmentIncome()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): String? {
        return when(position){
            0 -> "Pengeluaran"
            else -> "Pendapatan"
        }
    }


}