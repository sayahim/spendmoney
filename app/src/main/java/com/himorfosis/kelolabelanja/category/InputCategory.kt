package com.himorfosis.kelolabelanja.category

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.data_sample.CategoryData
import kotlinx.android.synthetic.main.activity_add_category.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class InputCategory : AppCompatActivity() {

    private var typeFinanceSelected = CategoryData.SPEND

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        setToolbar()
        initializeUI()
    }

    private fun initializeUI(){

        type_spend_rb.isChecked = true
        save_category_btn.onClick {
            pushCategoryCreate()
        }

        type_income_rb.onClick {
            onSelectedTypeFinance(CategoryData.INCOME)
        }

        type_spend_rb.onClick {
            onSelectedTypeFinance(CategoryData.SPEND)
        }

    }

    private fun fetchParseData() {

    }

    private fun onSelectedTypeFinance(typeFinance: String) {
        if(CategoryData.SPEND == typeFinance) {
            type_spend_rb.isChecked = true
            type_income_rb.isChecked = false
            typeFinanceSelected = CategoryData.SPEND
        } else {
            type_spend_rb.isChecked = false
            type_income_rb.isChecked = true
            typeFinanceSelected = CategoryData.INCOME


        }
    }

    private fun pushCategoryCreate() {

        val title = title_category_ed.text.toString()
        val description = description_category_ed.text.toString()

        if (title != "" && description != "") {

        } else {

        }

    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)

        backBar_btn.setOnClickListener {
//            startActivity(Intent(this, Category::class.java))
        }

        titleBar_tv.setText("Pilih Kategori")

    }

}
