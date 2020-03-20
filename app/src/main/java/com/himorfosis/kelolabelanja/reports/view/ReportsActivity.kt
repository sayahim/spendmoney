package com.himorfosis.kelolabelanja.reports.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.reports.adapter.ReportsAdapter
import com.himorfosis.kelolabelanja.reports.repo.ReportsViewModel
import kotlinx.android.synthetic.main.reports_activity.*

class ReportsActivity : AppCompatActivity() {

    lateinit var reportsAdapter: ReportsAdapter

    // view model
    lateinit var reportsViewModel: ReportsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reports_activity)

        setToolbar()

        setAdapter()

        setViewModel()

        fetchDataReports()

    }

    private fun setViewModel() {

        reportsViewModel = ViewModelProviders.of(this)[ReportsViewModel::class.java]
    }

    private fun fetchDataReports() {

        reportsViewModel.fetchReportsSample()
        reportsViewModel.sampleDataResponse.observe(this, Observer {

            if (it.isNotEmpty()) {

                reportsAdapter.addAll(it, 100)

            }

        })

    }

    private fun setAdapter() {

        reportsAdapter = ReportsAdapter()

        recycler_reports.apply {

            layoutManager = LinearLayoutManager(this@ReportsActivity)
            adapter = reportsAdapter

        }

    }

    private fun setToolbar() {


    }

}