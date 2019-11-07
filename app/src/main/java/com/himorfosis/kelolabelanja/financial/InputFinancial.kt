package com.himorfosis.kelolabelanja.financial

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.CategoryAdapter
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import com.himorfosis.kelolabelanja.database.entity.SpendingEntitiy
import com.himorfosis.kelolabelanja.database.spending.SpendingDao
import com.himorfosis.kelolabelanja.database.spending.SpendingDatabase
import com.himorfosis.kelolabelanja.homepage.HomepageActivity
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.toast
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.Collections.replaceAll



class InputFinancial : AppCompatActivity() {

    var TAG = "InputFinancial"

    lateinit var categoryAdapter: CategoryAdapter

    var recycler_category = null
    var getNominal: String? = null


    // database
    lateinit var spendingDao: SpendingDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_financial)

        setToolbar()

        setDatabase()

        setAdapterCategory()

        setActionClick()

        setActionSearchCategory()

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

                categoryAdapter.getFilter().filter(query)

                // visible delete search query
                delete_search_btn.visibility = View.VISIBLE

            }
        })

    }

    private fun setActionClick() {

        val save_btn = findViewById<Button>(R.id.save_btn)
        val nominal_et = findViewById<EditText>(R.id.nominal_et)
        val note_et = findViewById<EditText>(R.id.note_et)



        delete_search_btn.setOnClickListener {

            search_category_et.setText("")

            delete_search_btn.visibility = View.INVISIBLE

        }

//        nominal_et.addTextChangedListener(object : TextWatcher {
//
//            override fun afterTextChanged(s: Editable) {}
//
//            override fun beforeTextChanged(s: CharSequence, start: Int,
//                                           count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int,
//                                       before: Int, count: Int) {
//
//
//                var char = s.toString()
//
//                if (char == null) {
//
//                    getNominal = ""
//                    nominal_et.setText("")
//
//                } else if (char.equals(getNominal)) {
//
//                    // do nothing
//
//                } else {
//
//                    val nominal = nominal_et.text.toString().replace(".", "")
//
//                    Util.log(TAG, "nominal : " + nominal)
//                    Util.log(TAG, "number format : " + Util.numberFormat(nominal))
//                    Util.log(TAG, "char : " + char)
//
//                    val nominalNumberFormat = Util.numberFormat(nominal)
//
//                    nominal_et.setText(nominalNumberFormat)
//
//                    getNominal = char
//
//                }
//
//
//            }
//        })

        save_btn.setOnClickListener {

            //            val nominal_str = nominal_et.text.toString()
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

                val listCategory = spendingDao.getAllCategory()

                for (i in 0 until listCategory.size) {

                    val item = listCategory.get(i)

                    if (item.id == getIdSelected.toInt()) {

                        insertToDatabase(SpendingEntitiy(null, getIdSelected.toInt(), item.name, item.image_category, getNominal, note_str, dateNow, timeNow))

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


    private fun setAdapterCategory() {

        Util.log(TAG, "adapter")

        var recycler_category = findViewById<RecyclerView>(R.id.recycler_category)

//        val listCategory = spendingDao.getAllCategory()

        val listCategory = listOf(

                CategoryEntity(1, "Makanan", "ic_food_black", 0),
                CategoryEntity(2, "Belanja", "ic_shopping_bag_black", 0),
                CategoryEntity(3, "Hiburan", "ic_ticket_black", 0),
                CategoryEntity(4, "Transportasi", "ic_bus_black", 0),

                CategoryEntity(5, "Pendidikan", "ic_mortarboard_black", 0),
                CategoryEntity(6, "Keluarga", "ic_family_black", 0),
                CategoryEntity(7, "Elektronik", "ic_photo_camera_black", 0),
                CategoryEntity(8, "Pendidikan", "ic_other_black", 0),

                CategoryEntity(9, "Pendidikan", "ic_mortarboard_black", 0),
                CategoryEntity(10, "Keluarga", "ic_family_black", 0),
                CategoryEntity(11, "Elektronik", "ic_photo_camera_black", 0),
                CategoryEntity(12, "Pendidikan", "ic_other_black", 0),

                CategoryEntity(13, "Pendidikan", "ic_mortarboard_black", 0),
                CategoryEntity(14, "Keluarga", "ic_family_black", 0),
                CategoryEntity(15, "Elektronik", "ic_photo_camera_black", 0),
                CategoryEntity(16, "Pendidikan", "ic_other_black", 0),

                CategoryEntity(17, "Pendidikan", "ic_mortarboard_black", 0),
                CategoryEntity(18, "Keluarga", "ic_family_black", 0),
                CategoryEntity(19, "Elektronik", "ic_photo_camera_black", 0),
                CategoryEntity(20, "Pendidikan", "ic_other_black", 0),

                CategoryEntity(21, "Pendidikan", "ic_mortarboard_black", 0),
                CategoryEntity(22, "Keluarga", "ic_family_black", 0),
                CategoryEntity(23, "Elektronik", "ic_photo_camera_black", 0),
                CategoryEntity(24, "Pendidikan", "ic_other_black", 0)

        )

        categoryAdapter = CategoryAdapter(this, { item ->
            actionCallbackAdapter(item)

        })

        recycler_category.apply {

            categoryAdapter.addAll(listCategory)
            layoutManager = GridLayoutManager(this@InputFinancial, 4)
            adapter = categoryAdapter

        }

    }

    fun actionCallbackAdapter(item: CategoryEntity) {

        Util.log(TAG, "click : " + item.name)

        frame_input_data.visibility = View.VISIBLE
        frame_search.visibility = View.GONE

        Util.saveData("category", "selected", item.id.toString(), this)

        setAdapterCategory()

    }

    private fun setDatabase() {

        spendingDao = Room.databaseBuilder(this, SpendingDatabase::class.java, SpendingDatabase.DB_NAME)
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
