package com.himorfosis.kelolabelanja.details

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.himorfosis.kelolabelanja.R
import kotlinx.android.synthetic.main.toolbar_detail.*

class FinancialCategoryDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_financial_category_detail)

        setToolbar()

    }

    private fun getParsingData() {

        val data = intent

        val getCategoryType = data.getStringExtra("category")
        val getPeriod = data.getStringExtra("period")

    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)

        delete_btn.visibility = View.INVISIBLE

        titleBar_tv.text = "Detail"

        backBar_btn.setOnClickListener {

            startActivity(Intent(this, FinancialCategoryDetail::class.java))

        }

//                delete_btn.setOnClickListener {
//
//
//                    showAlertChoice()
//
//                }

    }


}
