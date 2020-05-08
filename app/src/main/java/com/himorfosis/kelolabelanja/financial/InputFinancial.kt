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
import com.himorfosis.kelolabelanja.utilities.preferences.BackpressedPref
import com.himorfosis.kelolabelanja.utilities.preferences.CategoryPref
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
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
        DataPreferences.category.saveString(CategoryPref.SELECTED, "")

    }

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

        val typeFinance = DataPreferences.backpressed.getString(BackpressedPref.DATA)
        isLog(typeFinance!!)
        if (typeFinance == getString(R.string.income)) {
            DataPreferences.backpressed.saveString(BackpressedPref.DATA, getString(R.string.spend))
            finance_pager.currentItem = 0
        } else {
            finish()
        }
    }

    private fun isLog(message: String) {
        Util.log("Input Finance", message)
    }

}
