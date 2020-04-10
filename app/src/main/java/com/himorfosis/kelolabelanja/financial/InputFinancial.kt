package com.himorfosis.kelolabelanja.financial

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.app.MyApp
import com.himorfosis.kelolabelanja.category.model.CategoryEntity
import com.himorfosis.kelolabelanja.data_sample.CategoryData
import com.himorfosis.kelolabelanja.financial.adapter.FinancialCategoryAdapter
import com.himorfosis.kelolabelanja.dialog.InputFinanceBottomDialog
import com.himorfosis.kelolabelanja.financial.adapter.FinancePagerAdapter
import com.himorfosis.kelolabelanja.financial.model.FinancialEntitiy
import com.himorfosis.kelolabelanja.financial.model.InputDataModel
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_input_financial.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.toast
import kotlin.collections.ArrayList


class InputFinancial : AppCompatActivity() {

    var TAG = "InputFinancial"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_financial)

        setToolbar()
        finance_pager.adapter = FinancePagerAdapter(supportFragmentManager)
        finance_tablayout.setupWithViewPager(finance_pager)

    }


//    private fun deleteTextSearch() {
//        search_category_et.setText("")
//        delete_search_btn.visibility = View.INVISIBLE
//    }

//    private fun setActionClick() {
//
//        delete_search_btn.setOnClickListener {
//            deleteTextSearch()
//        }
//
//    }


//    private fun insertIntoDatabase(data: FinancialEntitiy) {
//
//        toast("Data Berhasil Tersimpan")
//        startActivity(Intent(this, HomepageActivity::class.java))
//
//    }

//    private fun setDataCategorySpending() {
//
//        getTypeInputData = "spend"
//
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
//
//        spend_tv.setTextColor(resources.getColor(R.color.text_blue_dark))
//        income_tv.setTextColor(resources.getColor(R.color.text_second))
//
//        // set indicator
//        income_indicator.visibility = View.INVISIBLE
//        spend_indicator.visibility = View.VISIBLE
//
//        search_category_et.setText("")
//        delete_search_btn.visibility = View.INVISIBLE
//
//        deleteTextSearch()
//
//        listCategory = CategoryData.getDataCategorySpending()
//
//        setAdapterCategory()
//
//    }

//    private fun setDataCategoryIncome() {
//
//        getTypeInputData = "income"
//
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
//
//        income_tv.setTextColor(resources.getColor(R.color.text_blue_dark))
//        spend_tv.setTextColor(resources.getColor(R.color.text_second))
//
//        // set indicator
//        income_indicator.visibility = View.VISIBLE
//        spend_indicator.visibility = View.INVISIBLE
//
//        // delete cache data
//        Util.saveData("category", "selected", "0", this)
//
//        deleteTextSearch()
//
//        listCategory = CategoryData.getDataCategoryIncome()
//
//        setAdapterCategory()
//
//    }

//    private fun setAdapterCategory() {
//
//        Util.log(TAG, "adapter")
//
//        financialCategoryAdapter = FinancialCategoryAdapter()
//
//        recycler_category.apply {
//
//            financialCategoryAdapter.addAll(listCategory)
//            layoutManager = GridLayoutManager(this@InputFinancial, 3)
//            adapter = financialCategoryAdapter
//
//        }
//        if (listCategory.isEmpty()) {
//
//            status_data_tv.visibility = View.VISIBLE
//
//        } else {
//
//            status_data_tv.visibility = View.INVISIBLE
//        }
//
//        // adapter callback
//        financialCategoryAdapter.setOnclick(object : FinancialCategoryAdapter.AdapterOnClickItem {
//            override fun onItemClicked(data: CategoryEntity) {
//
//                Util.saveData("category", "selected", data.id.toString(), this@InputFinancial)
//
//                showInputDataFinance(data)
//
//                reloadDataAdapter()
//
//            }
//        })
//
//    }
    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)

        titleBar_tv.text = "Input Data Keuangan"

        backBar_btn.setOnClickListener {

            actionBackpressed()
        }

    }

    override fun onBackPressed() {
        actionBackpressed()
    }

    private fun actionBackpressed() {

        val typeFinance = MyApp.findInBackpressd()

        isLog(typeFinance!!)

        if (typeFinance == getString(R.string.income)) {
            finance_pager.currentItem = 0
//            setDataCategorySpending()
        } else {
            startActivity(Intent(this, HomepageActivity::class.java))
        }
    }

    private fun isLog(message: String) {
        Util.log("Input Finance", message)
    }

}
