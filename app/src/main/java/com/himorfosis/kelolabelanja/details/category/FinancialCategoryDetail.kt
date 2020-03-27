package com.himorfosis.kelolabelanja.details.category

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.database.db.DatabaseDao
import com.himorfosis.kelolabelanja.details.category.adapter.FinancialCategoryGroupAdapter
import com.himorfosis.kelolabelanja.details.category.model.FinancialPerCategoryModel
import com.himorfosis.kelolabelanja.details.category.repo.FinancialCategoryRepo
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_financial_category_detail.*
import kotlinx.android.synthetic.main.toolbar_detail.*

class FinancialCategoryDetail : AppCompatActivity() {

    val TAG = "FinancialCategoryDetail"

    lateinit var databaseDao: DatabaseDao
    lateinit var getCategoryID: String
    lateinit var getTitleCategory: String
    lateinit var getPeriod: String
    lateinit var getType: String

    lateinit var adapterFinancialCategory: FinancialCategoryGroupAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_financial_category_detail)

        getParsingData()

        setToolbar()

        getDataDetailPerCategory()

    }


    private fun getParsingData() {

        val data = intent

        getCategoryID = data.getStringExtra("category")
        getTitleCategory = data.getStringExtra("title_category")
        getType = data.getStringExtra("type")

        Util.log(TAG, "category : $getCategoryID")
        Util.log(TAG, "type : $getType")

    }

    private fun getDataDetailPerCategory() {

        FinancialCategoryRepo.setDataFinancialCategoryDatabase(this, getType, getCategoryID)

        FinancialCategoryRepo.getDataFinancialCategoryDatabase().observe(this, Observer { response ->

            Util.log(TAG, "response : $response")

            if (response != null) {

                setAdapterCategoryFinancialDetail(response)

            } else {



            }

        })

    }

    private fun setAdapterCategoryFinancialDetail(listData: MutableList<FinancialPerCategoryModel>) {

        adapterFinancialCategory = FinancialCategoryGroupAdapter(this)

        recycler_financial_category.apply {

            // sorted list
            var sortedListDescending = listData.sortedWith(compareByDescending { it.date })

            // add data in adapter
            adapterFinancialCategory.addAll(sortedListDescending)

            layoutManager = LinearLayoutManager(this@FinancialCategoryDetail)
            setHasFixedSize(true)
            adapter = adapterFinancialCategory

        }

    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)

        action_btn.visibility = View.INVISIBLE

        titleBar_tv.text = "Detail Ketegori $getTitleCategory"

        backBar_btn.setOnClickListener {

            finish()

        }

    }


}
