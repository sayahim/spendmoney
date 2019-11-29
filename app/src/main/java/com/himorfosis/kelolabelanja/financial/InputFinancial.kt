package com.himorfosis.kelolabelanja.financial

import android.app.DatePickerDialog
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
import com.himorfosis.kelolabelanja.database.db.DatabaseDao
import com.himorfosis.kelolabelanja.database.db.Database
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_input_financial.*
import kotlinx.android.synthetic.main.layout_input_data.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.toast
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import java.text.DecimalFormat
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class InputFinancial : AppCompatActivity() {

    var TAG = "InputFinancial"

    lateinit var financialCategoryAdapter: FinancialCategoryAdapter

    var getTypeInputData = "income"

    // data
    var listCategory: List<CategoryEntity> = ArrayList<CategoryEntity>()

    // database
    lateinit var databaseDao: DatabaseDao
    lateinit var typeDataFinancial: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_financial)

        setToolbar()

        setDatabase()

        setDataCategorySpending()

        setAdapterCategory()

        setActionClick()

//        setSearchCategory()

        setNumberFormatNominal()

        setNoteLength()

        setTabLayout()

    }

    private fun setNumberFormatNominal() {

        val decimalFormat = DecimalFormat("#,###,###,###,##")
        decimalFormat.isDecimalSeparatorAlwaysShown = true
        val decimalFormatNotEdit = DecimalFormat("#,###")
        var hasFractionalPart = false

        nominal_et.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                nominal_et.removeTextChangedListener(this)

                try {

                    var nominalSize = nominal_et.text.toString().length

                    var originalString = s.toString()

                    originalString = originalString.replace(decimalFormat.decimalFormatSymbols.groupingSeparator.toString(), "")

                    var number: Number = decimalFormat.parse(originalString)

                    var selectionNominal = nominal_et.selectionStart

                    if (hasFractionalPart) {

                        nominal_et.setText(decimalFormat.format(number))

                    } else {

                        nominal_et.setText(decimalFormatNotEdit.format(number))

                    }

                    val endSize = nominal_et.text.toString().length

                    var setIndicatorEditText = (selectionNominal + (endSize - nominalSize))

                    if (setIndicatorEditText in 1..endSize) {

                        nominal_et.setSelection(setIndicatorEditText)

                    } else {

                        nominal_et.setSelection(endSize - 1)

                    }

                } catch (e : java.lang.NumberFormatException) {
                    // do nothing?
                }

                nominal_et.addTextChangedListener(this)

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {


            }

        })

    }



    private fun setNoteLength() {

        note_et.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                var noteSize = note_et.text.toString().length

                note_length_tv.setText("$noteSize/50")

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {


            }

        })

    }

    private fun setSearchCategory() {

        note_length_tv.addTextChangedListener(object : TextWatcher {

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

            // set data category to list
            setDataCategorySpending()

        }

        income_ll.setOnClickListener {

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

            var getNominal = nominal_et.text.toString()
            var getNote = note_et.text.toString()
            var getIdSelected = Util.getData("category", "selected", this)

            if (getNote == "") {

                getNote = "-"

            }

            getNominal = getNominal.replace(".", "")

            Util.log(TAG, "id selected $getIdSelected")
            Util.log(TAG, "nominal :$getNominal:")
            Util.log(TAG, "note $getNote")

            if (getNominal == "" || getIdSelected == "") {

                toast("Harap Lengkapi Data")

            } else {

                val time = SimpleDateFormat("HH:mm")
                val date = SimpleDateFormat("yyyy-MM-dd")
                val dateNow = date.format(Date())
                val timeNow = time.format(Date())

                Util.log(TAG, "date : $dateNow")
                Util.log(TAG, "time : $timeNow")
                Util.log(TAG, "getIdSelected : $getIdSelected")

//                val listCategory = databaseDao.getAllCategory()

                for (i in listCategory.indices) {

                    val item = listCategory[i]

                    if (item.id == getIdSelected.toInt()) {

                        insertIntoDatabase(FinancialEntitiy(null, getIdSelected.toInt(), item.name, item.image_category, typeDataFinancial, getNominal, getNote, dateNow, timeNow))

                        break

                    }

                }

            }

        }

        select_date_ll.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dateDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                val dateSelected = "$dayOfMonth.${monthOfYear + 1}.$year"

                Util.log(TAG, "date selected : $dateSelected")


            }, year, month, day)
            dateDialog.show()

        }

    }


    private fun insertIntoDatabase(data: FinancialEntitiy) {

        databaseDao.insertSpending(data)

        toast("Data Berhasil Tersimpan")

        startActivity(Intent(this, HomepageActivity::class.java))

    }

    private fun setDataCategorySpending() {

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        spend_tv.setTextColor(resources.getColor(R.color.text_blue_dark))
        income_tv.setTextColor(resources.getColor(R.color.text_second))

        // set indicator
        income_indicator.visibility = View.INVISIBLE
        spend_indicator.visibility = View.VISIBLE

        search_category_et.setText("")
        delete_search_btn.visibility = View.INVISIBLE

        // delete cache data
        Util.saveData("category", "selected", "0", this)

        deleteTextSearch()

        hideLayoutInputData()

        Util.saveData("category", "selected", "0", this)

        typeDataFinancial = "spend"

        listCategory = Util.getDataCategorySpending()

        setAdapterCategory()

    }

    private fun setDataCategoryIncome() {

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        income_tv.setTextColor(resources.getColor(R.color.text_blue_dark))
        spend_tv.setTextColor(resources.getColor(R.color.text_second))

        // set indicator
        income_indicator.visibility = View.VISIBLE
        spend_indicator.visibility = View.INVISIBLE

        // delete cache data
        Util.saveData("category", "selected", "0", this)

        deleteTextSearch()

        hideLayoutInputData()

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

        Util.saveData("category", "selected", "0", this)

    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)

        backBar_btn.setOnClickListener {

            startActivity(Intent(this, HomepageActivity::class.java))

        }

        titleBar_tv.text = "Input Data"

    }

    override fun onBackPressed() {

        getTypeInputData = "spend"

        if (getTypeInputData == "spend") {

            setDataCategorySpending()

        } else {

            val selected = Util.getData("category", "selected", this)

            if (selected == null) {

                startActivity(Intent(this, HomepageActivity::class.java))

            } else {

                hideLayoutInputData()
                Util.deleteData("category", this)

            }

        }


    }

}
