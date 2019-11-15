package com.himorfosis.kelolabelanja.financial

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.financial.adapter.FinancialCategoryAdapter
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy
import com.himorfosis.kelolabelanja.database.spending.DatabaseDao
import com.himorfosis.kelolabelanja.database.spending.Database
import com.himorfosis.kelolabelanja.homepage.HomepageActivity
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_input_financial.*
import kotlinx.android.synthetic.main.layout_input_data.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class InputFinancial : AppCompatActivity() {

    var TAG = "InputFinancial"

    lateinit var financialCategoryAdapter: FinancialCategoryAdapter

    var recycler_category = null
    var getNominal: String? = null

    // data
    var listCategory :List<CategoryEntity> = ArrayList<CategoryEntity>()

    // database
    lateinit var databaseDao: DatabaseDao
    lateinit var typeDataFinancial: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_financial)

        setToolbar()

        setDatabase()

        // set data category to list
        setDataCategorySpending()

        setAdapterCategory()

        setActionClick()

        setActionSearchCategory()

        setTabLayout()

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

    }

    private fun setActionSearchCategory() {

        search_category_et.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                val query = search_category_et.text.toString().toLowerCase(Locale.getDefault())

                Util.log(TAG, "query : $query")

                financialCategoryAdapter.filter.filter(query)

                // visible delete search query
                delete_search_btn.visibility = View.VISIBLE

            }
        })

    }


    private fun setTabLayout() {

        spend_ll.setOnClickListener {

            spend_tv.setTextColor(resources.getColor(R.color.text_blue_dark))
            income_tv.setTextColor(resources.getColor(R.color.text_hint))

            // set indicator
            income_indicator.visibility = View.INVISIBLE
            spend_indicator.visibility = View.VISIBLE

            search_category_et.setText("")
            delete_search_btn.visibility = View.INVISIBLE

            // delete cache data
            Util.deleteData("category", this)

            deleteTextSearch()

            hideLayoutInputData()

            // set data category to list
            setDataCategorySpending()

        }

        income_ll.setOnClickListener {

            income_tv.setTextColor(resources.getColor(R.color.text_blue_dark))
            spend_tv.setTextColor(resources.getColor(R.color.text_hint))

            // set indicator
            income_indicator.visibility = View.VISIBLE
            spend_indicator.visibility = View.INVISIBLE

            // delete cache data
            Util.deleteData("category", this)

            deleteTextSearch()

            hideLayoutInputData()

            // set data category to list
            setDataCategoryIncome()

        }

    }

    private fun hideLayoutInputData() {

        frame_input_data.visibility = View.GONE
//        frame_search.visibility = View.GONE

    }

    private fun showLayoutInputData() {

        frame_input_data.visibility = View.VISIBLE
        frame_search.visibility = View.GONE

    }

    private fun deleteTextSearch() {

        search_category_et.setText("")
        delete_search_btn.visibility = View.INVISIBLE

    }

    private fun setActionClick() {

        delete_search_btn.setOnClickListener {

            deleteTextSearch()

        }

        save_btn.setOnClickListener {

            val getNominal = nominal_et.text.toString()
            var note_str = note_et.text.toString()
            val getIdSelected = Util.getData("category", "selected", this)

            if (note_str.equals("")) {

                note_str = "-"

            }

            Util.log(TAG, "id selected " + getIdSelected)
            Util.log(TAG, "nominal " + getNominal)
            Util.log(TAG, "note " + note_str)

            if (getNominal != null && getIdSelected != null) {

                val time = SimpleDateFormat("HH:mm")
                val date = SimpleDateFormat("yyyy.MM.dd")
                val dateNow = date.format(Date())
                val timeNow = time.format(Date())

                Util.log(TAG, "date : " + dateNow)
                Util.log(TAG, "time : " + timeNow)
                Util.log(TAG, "getIdSelected : " + getIdSelected)

//                val listCategory = databaseDao.getAllCategory()

                for (i in 0 until listCategory.size) {

                    val item = listCategory.get(i)

                    if (item.id == getIdSelected.toInt()) {

                        insertToDatabase(FinancialEntitiy(null, getIdSelected.toInt(), item.name, item.image_category, typeDataFinancial, getNominal, note_str, dateNow, timeNow))

                        break

                    }

                }


            } else {

                toast("Harap Lengkapi Data")

            }

        }

    }


    private fun insertToDatabase(data: FinancialEntitiy) {

        databaseDao.insertSpending(data)

        toast("Data Berhasil Tersimpan")

        startActivity(Intent(this, HomepageActivity::class.java))

    }

    private fun setDataCategorySpending() {

        Util.saveData("category", "selected", "0", this)

        typeDataFinancial = "spend"

        listCategory = Util.getDataCategorySpending();

        setAdapterCategory()

    }

    private fun setDataCategoryIncome() {

        Util.saveData("category", "selected", "0", this)

        typeDataFinancial = "income"

        listCategory = Util.getDataCategoryIncome()

        setAdapterCategory()

    }

    private fun setAdapterCategory() {

        Util.log(TAG, "adapter")

        var recycler_category = findViewById<RecyclerView>(R.id.recycler_category)

//        val listCategory = databaseDao.getAllCategory()

        financialCategoryAdapter = FinancialCategoryAdapter(this) { item ->
            actionCallbackAdapter(item)

        }

        recycler_category.apply {

            financialCategoryAdapter.addAll(listCategory)
            layoutManager = GridLayoutManager(this@InputFinancial, 4)
            adapter = financialCategoryAdapter

        }
        if (listCategory.size == 0) {

            status_data_tv.visibility = View.VISIBLE

        } else {

            status_data_tv.visibility = View.INVISIBLE


        }

    }

    fun actionCallbackAdapter(item: CategoryEntity) {

        Util.log(TAG, "callback : " + item.name)

        showLayoutInputData()

        Util.saveData("category", "selected", item.id.toString(), this)

        setAdapterCategory()

    }

    private fun setDatabase() {

        databaseDao = Room.databaseBuilder(this, Database::class.java, Database.DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .spendingDao()

        Util.deleteData("category", this)

    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)

        backBar_btn.setOnClickListener {

            startActivity(Intent(this, HomepageActivity::class.java))

        }

        titleBar_tv.setText("Input Kategori")

    }

    override fun onBackPressed() {

        val selected = Util.getData("category", "selected", this)

        Util.log(TAG, "category selected : " + selected)

        if (selected == null) {

            startActivity(Intent(this, HomepageActivity::class.java))

        } else {

            frame_input_data.visibility = View.GONE
            Util.deleteData("category", this)

        }


    }

}
