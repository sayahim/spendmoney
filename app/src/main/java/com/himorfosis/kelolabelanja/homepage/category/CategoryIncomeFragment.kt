package com.himorfosis.kelolabelanja.homepage.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.adapter.CategoryListAdapter
import com.himorfosis.kelolabelanja.data_sample.CategoryData
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import kotlinx.android.synthetic.main.category_fragment.*
import org.jetbrains.anko.support.v4.toast

class CategoryIncomeFragment : Fragment() {

    var adapterCategory = CategoryListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.category_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()

        getDataCategory()

    }

    private fun getDataCategory() {

        adapterCategory.addAll(CategoryData.getDataCategoryIncome())

    }

    private fun setAdapter() {

        adapterCategory = CategoryListAdapter()

        recycler_category.apply {

            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterCategory
        }

        adapterCategory.setOnclick(object : CategoryListAdapter.OnClickItem {
            override fun onItemClicked(data: CategoryEntity) {

                toast("Deleted category " + data.name)

            }
        })

    }

}