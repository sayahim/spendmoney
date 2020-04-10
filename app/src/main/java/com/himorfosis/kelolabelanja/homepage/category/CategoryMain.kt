package com.himorfosis.kelolabelanja.homepage.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.adapter.CategoryPagerAdapter
import kotlinx.android.synthetic.main.toolbar_title.*
import kotlinx.android.synthetic.main.view_pager_layout.*

class CategoryMain : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_pager_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()

        chart_pager.adapter = CategoryPagerAdapter(childFragmentManager)
        chart_tablayout.setupWithViewPager(chart_pager)

    }

    private fun setToolbar() {
        titleBar_tv.text = "Kategori"
    }

}