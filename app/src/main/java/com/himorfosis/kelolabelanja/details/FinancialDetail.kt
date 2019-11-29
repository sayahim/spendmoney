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
import com.himorfosis.kelolabelanja.database.db.DatabaseDao
import com.himorfosis.kelolabelanja.database.db.Database
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_spending_detail.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.toast

class SpendingDetail : AppCompatActivity() {

    lateinit var databaseDao: DatabaseDao

    var getId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spending_detail)

        setToolbar()

        setDatabase()

        getDataDetail()

    }

    private fun getDataDetail() {

        val data = intent

        getId = data.getIntExtra("id", 0)

        val details = databaseDao.getDetailSpending(getId)

        category_tv.text = details.category_name
        date_tv.text = Util.convertCalendarMonth(details.date)
        note_tv.text = details.note

        if (details.nominal != "") {

            nominal_tv.text = Util.numberFormatMoney(details.nominal)

        } else {

            nominal_tv.text = "Rp 0"

        }

    }

    private fun setDatabase() {

        databaseDao = Room.databaseBuilder(this, Database::class.java, Database.DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .spendingDao()

    }

    private fun deleteDataSelected() {

        databaseDao.deleteDataFinancialItem(getId)

        toast("Hapus Data Sukses")

        startActivity(Intent(this, HomepageActivity::class.java))

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

           deleteDataSelected()

        }


    }


}
