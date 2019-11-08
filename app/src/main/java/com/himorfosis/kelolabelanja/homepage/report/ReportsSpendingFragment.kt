package com.himorfosis.kelolabelanja.homepage.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.CategoryAdapter
import com.himorfosis.kelolabelanja.homepage.report.adapter.ReportsAdapter
import com.himorfosis.kelolabelanja.homepage.statistict.adapter.FinancialProgressAdapter
import com.himorfosis.kelolabelanja.homepage.statistict.model.FinancialProgressModel
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.reports_spending_fragment.*
import java.util.ArrayList

class ReportsSpendingFragment : Fragment() {

    var TAG = "ReportsSpendingFragment"

    lateinit var reportsAdapter: ReportsAdapter

    companion object {

        fun newInstance(): ReportsFragment {
            return ReportsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.reports_spending_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapterFinancial()

    }

    private fun setAdapterFinancial() {

        Util.log(TAG, "adapter")

        val data = listOf(

                FinancialProgressModel("Makanan", 100, 100),
                FinancialProgressModel("Hiburan", 70, 100),
                FinancialProgressModel("Tiket", 30, 100),
                FinancialProgressModel("Developer", 50, 100)

        )

        reportsAdapter = ReportsAdapter(requireContext())

        recycler_reports.apply {

            layoutManager = LinearLayoutManager(requireContext())
            reportsAdapter.addAll(data)
            adapter = reportsAdapter

        }

        Util.log(TAG, "data list : " + data.size)

        if (data.size != 0) {

            status_data_tv.visibility = View.VISIBLE

        } else {

            status_data_tv.visibility = View.INVISIBLE
            
        }

    }


}