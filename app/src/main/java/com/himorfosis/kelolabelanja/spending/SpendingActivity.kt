package com.himorfosis.kelolabelanja.spending

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.Category
import com.himorfosis.kelolabelanja.database.spending.SpendingDao
import com.himorfosis.kelolabelanja.database.spending.SpendingDatabase
import com.himorfosis.kelolabelanja.database.entity.SpendingEntitiy
import com.himorfosis.kelolabelanja.homepage.HomepageActivity
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_spending.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class SpendingActivity : AppCompatActivity() {

    var TAG = "SpendingActivity"

    // database
    lateinit var spendingDao: SpendingDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spending)

        setToolbar()

        setDatabase()

        setAdapterCategory()

        setClickAction()

    }

    private fun setDatabase() {

        Util.deleteData("category", this)

        spendingDao = Room.databaseBuilder(this, SpendingDatabase::class.java, SpendingDatabase.DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .spendingDao()

    }

    private fun setAdapterCategory() {

        Util.log(TAG, "adapter")

        val listCategory = spendingDao.getAllCategory()

        val CategoryAdapter = SpendingCategoryAdapter(applicationContext, listCategory, {

            Util.log(TAG, "callback")

            val getIdSelected = Util.getData("category", "selected", this)
            Util.log(TAG, "id selected " + getIdSelected)

            // handle reload fragment
            if (getIdSelected != null) {

                if (getIdSelected.equals("8")) {

                     startActivity(Intent(this, Category::class.java))

                } else {

                    setAdapterCategory()

                }

            }

        })

        recycler_category.apply {

            layoutManager = GridLayoutManager(this@SpendingActivity, 4)
            adapter = CategoryAdapter

        }

    }

    private fun setClickAction() {


        save_btn.setOnClickListener {

            val nominal_str = nominal_et.text.toString()
            var note_str = note_et.text.toString()
            val getIdSelected = Util.getData("category", "selected", this)

            if (note_str.equals("")) {

                note_str = "-"

            }

            Util.log(TAG, "id selected " + getIdSelected)
            Util.log(TAG, "nominal " + nominal_str)
            Util.log(TAG, "note " + note_str)

            if (!nominal_str.equals("") && getIdSelected != null) {

                val time = SimpleDateFormat("HH:mm")
                val date = SimpleDateFormat("yyyy.MM.dd")
                val dateNow = date.format(Date())
                val timeNow = time.format(Date())

                Util.log(TAG, "date : " + dateNow)
                Util.log(TAG, "time : " + timeNow)
                Util.log(TAG, "getIdSelected : " + getIdSelected)

                val listCategory = spendingDao.getAllCategory()

                for (i in 0 until listCategory.size) {

                    val item = listCategory.get(i)

                    if (item.id == getIdSelected.toInt()) {

                        insertToDatabase(SpendingEntitiy(null, getIdSelected.toInt(), item.name, item.image_category, nominal_str, note_str, dateNow, timeNow))

                        break

                    }

                }


            } else {

                toast("Harap Lengkapi Data")

            }

        }

    }

    private fun insertToDatabase(data: SpendingEntitiy) {

        spendingDao.insertSpending(data)

        toast("Data Berhasil Tersimpan")

        startActivity(Intent(this, HomepageActivity::class.java))

    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)

        backBar_btn.setOnClickListener {

            startActivity(Intent(this, HomepageActivity::class.java))

        }

        titleBar_tv.setText("Input Pengeluaran")

    }

}
