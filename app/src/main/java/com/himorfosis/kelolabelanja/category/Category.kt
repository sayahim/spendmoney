package com.himorfosis.kelolabelanja.category

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.R.attr.layoutManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.database.spending.SpendingDao
import com.himorfosis.kelolabelanja.database.spending.SpendingDatabase
import com.himorfosis.kelolabelanja.spending.SpendingActivity
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_spending.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.toast
import com.himorfosis.kelolabelanja.R.mipmap.ic_launcher
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import java.util.*


class Category : AppCompatActivity() {

    var TAG = "Category"

    lateinit var spendingDao: SpendingDao

    lateinit var categoryAdapter: CategoryAdapter

    var recycler_category = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        setToolbar()

        setDatabase()

        setAdapterCategory()

        setActionClick()

        setActionSearchCategory()

    }

    private fun setActionSearchCategory() {

        search_category_et.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

//                val query = search_category_et.getText().toString().toLowerCase(Locale.getDefault())
                val query = search_category_et.text.toString().toLowerCase(Locale.getDefault())

                Util.log(TAG, "query : $query")

                categoryAdapter.getFilter().filter(query)


            }
        })

    }

    private fun setActionClick() {

        add_category.setOnClickListener {

            toast("Add Category")

        }

    }

    private fun setAdapterCategory() {

        Util.log(TAG, "adapter")

        var recycler_category = findViewById<RecyclerView>(R.id.recycler_category)

        val listCategory = spendingDao.getAllCategory()

        categoryAdapter = CategoryAdapter(this)

        recycler_category.apply {

            categoryAdapter.addAll(listCategory)
            layoutManager = GridLayoutManager(this@Category, 4)
            adapter = categoryAdapter

        }


    }


    private fun setDatabase() {

        spendingDao = Room.databaseBuilder(this, SpendingDatabase::class.java, SpendingDatabase.DB_NAME).allowMainThreadQueries().build().spendingDao()

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

        setImage(R.drawable.ic_food_black)

    }

    private fun setImage(img: Int): ByteArray {

//        val dao = DatabaseHelper.instance.getImageTestDao()
//        val imageTest = ImageTest()
//        imageTest.data = getBytesFromImageMethod(image)//TODO
//        dao.updsertByReplacement(imageTest)

        val bmp = BitmapFactory.decodeResource(resources, img)
        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        return byteArray

    }

//    fun getImage():Bitmap?{
//
////        val dao = DatabaseHelper.instance.getImageTestDao()
////        val imageByteArray = dao.getAll()
////        return loadImageFromBytes(imageByteArray[0].data)
//        //change accordingly
//
//    }

//    fun setImageShow(byteArray: ByteArray) {
//
//        val bmp = BitmapFactory.decodeByteArray(byteArray, 0, length)
//        val image = findViewById<View>(R.id.imageView1) as ImageView
//        image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(), image.getHeight(), false))
//
//    }

}
