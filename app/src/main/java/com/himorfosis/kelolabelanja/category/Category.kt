package com.himorfosis.kelolabelanja.category

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.database.db.DatabaseDao
import com.himorfosis.kelolabelanja.database.db.Database
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.toolbar_detail.*
import android.view.WindowManager
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import com.himorfosis.kelolabelanja.financial.adapter.FinancialCategoryAdapter
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity


class Category : AppCompatActivity() {

    var TAG = "Category"

    lateinit var databaseDao: DatabaseDao

    lateinit var financialCategoryAdapter: FinancialCategoryAdapter

    lateinit var typeDataFinancial: String

    var recycler_category = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        setToolbar()

        setDatabase()

        setAdapterCategory()

        setActionSearchCategory()

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)


    }


    private fun setActionSearchCategory() {

//        search_category_et.addTextChangedListener(object : TextWatcher {
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
////                val query = search_category_et.getText().toString().toLowerCase(Locale.getDefault())
//                val query = search_category_et.text.toString().toLowerCase(Locale.getDefault())
//
//                Util.log(TAG, "query : $query")
//
//                financialCategoryAdapter.getFilter().filter(query)
//
//                // visible delete search query
//                delete_search_btn.visibility = View.VISIBLE
//
//            }
//        })

    }


    private fun setAdapterCategory() {

        var recycler_category = findViewById<RecyclerView>(R.id.recycler_category)

//        val listCategory = databaseDao.getAllCategory()

        val listCategory = listOf(

                CategoryEntity(1, "Makanan", "ic_food_black"),
                CategoryEntity(2, "Belanja", "ic_shopping_bag_black"),
                CategoryEntity(3, "Hiburan", "ic_ticket_black"),
                CategoryEntity(4, "Transportasi", "ic_bus_black"),

                CategoryEntity(5, "Pendidikan", "ic_mortarboard_black"),
                CategoryEntity(6, "Keluarga", "ic_family_black"),
                CategoryEntity(7, "Elektronik", "ic_photo_camera_black"),
                CategoryEntity(8, "Pendidikan", "ic_other_black"),

                CategoryEntity(9, "Pendidikan", "ic_mortarboard_black"),
                CategoryEntity(10, "Keluarga", "ic_family_black"),
                CategoryEntity(11, "Elektronik", "ic_photo_camera_black"),
                CategoryEntity(12, "Pendidikan", "ic_other_black"),

                CategoryEntity(13, "Pendidikan", "ic_mortarboard_black"),
                CategoryEntity(14, "Keluarga", "ic_family_black"),
                CategoryEntity(15, "Elektronik", "ic_photo_camera_black"),
                CategoryEntity(16, "Pendidikan", "ic_other_black"),

                CategoryEntity(17, "Pendidikan", "ic_mortarboard_black"),
                CategoryEntity(18, "Keluarga", "ic_family_black"),
                CategoryEntity(19, "Elektronik", "ic_photo_camera_black"),
                CategoryEntity(20, "Pendidikan", "ic_other_black"),

                CategoryEntity(21, "Pendidikan", "ic_mortarboard_black"),
                CategoryEntity(22, "Keluarga", "ic_family_black"),
                CategoryEntity(23, "Elektronik", "ic_photo_camera_black"),
                CategoryEntity(24, "Pendidikan", "ic_other_black")

        )

        financialCategoryAdapter = FinancialCategoryAdapter(this, { item ->
            actionCallbackAdapter(item)

        })

        recycler_category.apply {

            financialCategoryAdapter.addAll(listCategory)
            layoutManager = GridLayoutManager(this@Category, 4)
            adapter = financialCategoryAdapter

        }

    }

    

    fun actionCallbackAdapter(item: CategoryEntity) {

//        Util.log(TAG, "click : " + item.name)
//
//        frame_input_data.visibility = View.VISIBLE
//        frame_search.visibility = View.GONE
//
//        Util.saveData("category", "selected", item.id.toString(), this)
//
//        setAdapterCategory()

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

        titleBar_tv.setText("Pilih Kategori")

    }

    override fun onBackPressed() {

//        val selected = Util.getData("category", "selected",  this)
//
//        Util.log(TAG, "category selected : " + selected)
//
//        if (selected.equals("")) {
//
//            startActivity(Intent(this, HomepageActivity::class.java))
//
//        } else {
//
//            frame_input_data.visibility = View.GONE
//            Util.deleteData("category", this)
//
//        }


    }

}
