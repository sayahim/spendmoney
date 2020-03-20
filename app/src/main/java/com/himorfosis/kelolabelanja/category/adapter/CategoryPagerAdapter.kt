package com.himorfosis.kelolabelanja.category.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.himorfosis.kelolabelanja.homepage.category.CategoryIncomeFragment
import com.himorfosis.kelolabelanja.homepage.category.CategorySpendFragment

class CategoryPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private val pages = listOf(
            CategorySpendFragment(),
            CategoryIncomeFragment()
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