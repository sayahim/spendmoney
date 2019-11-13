package com.himorfosis.kelolabelanja.details

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.database.spending.DatabaseDao
import com.himorfosis.kelolabelanja.database.spending.Database
import com.himorfosis.kelolabelanja.homepage.HomepageActivity
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_spending_detail.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.toast

class SpendingDetail : AppCompatActivity() {

    lateinit var databaseDao: DatabaseDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spending_detail)

        setToolbar()

        setDatabase()

        getDataDetail()

    }

    private fun getDataDetail() {

        val data = intent

        val getId = data.getIntExtra("id", 0)

        val details = databaseDao.getDetailSpending(getId)

        nominal_tv.setText(Util.numberFormatMoney(details.nominal!!.toString()))
        category_tv.setText(details.category_name)
        date_tv.setText(details.date)
        note_tv.setText(details.note)

    }

    private fun setDatabase() {

        databaseDao = Room.databaseBuilder(this, Database::class.java, Database.DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .spendingDao()

    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)

        delete_btn.visibility = View.VISIBLE

        titleBar_tv.setText("Detail")

        backBar_btn.setOnClickListener {

            startActivity(Intent(this, HomepageActivity::class.java))

        }

        delete_btn.setOnClickListener {

            toast("delete")

        }


    }


}
