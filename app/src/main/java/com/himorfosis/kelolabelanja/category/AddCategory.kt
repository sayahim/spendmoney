package com.himorfosis.kelolabelanja.category

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.spending.SpendingActivity
import kotlinx.android.synthetic.main.toolbar_detail.*

class AddCategory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        setToolbar()

    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)

        backBar_btn.setOnClickListener {

            startActivity(Intent(this, SpendingActivity::class.java))

        }

        titleBar_tv.setText("Pilih Kategori")

    }

}
