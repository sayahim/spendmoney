package com.himorfosis.kelolabelanja.homepage.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import com.himorfosis.kelolabelanja.database.spending.SpendingDao
import com.himorfosis.kelolabelanja.database.spending.SpendingDatabase
import com.himorfosis.kelolabelanja.database.entity.SpendingEntitiy
import com.himorfosis.kelolabelanja.homepage.home.adapter.HomeAdapter
import com.himorfosis.kelolabelanja.spending.SpendingActivity
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"

    private var listDataSpending: List<SpendingEntitiy> = ArrayList()

    lateinit var spendingDao: SpendingDao

    private var adapter: HomeAdapter? = null

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickAction()

        setLocalDatabase()

        getAllDataToday()

        setAdapter()

        setCategoryDB()

    }

    private fun setAdapter() {

        if (listDataSpending.size >= 1) {

            status_tv.visibility = View.INVISIBLE

            recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
            recycler.setHasFixedSize(true)

            adapter.apply {

                adapter = HomeAdapter(requireContext(), listDataSpending)
                recycler.adapter = adapter

            }


        }

    }

    fun getAllDataToday() {

        val date = SimpleDateFormat("yyyy.MM.dd")
        val today = date.format(Date())

        Util.log(TAG, "today : " + today)

        listDataSpending = spendingDao.getReportSpendToday(today)

        Util.log(TAG, "list data : " + listDataSpending.size)

        var totalSpend_int = 0

        for (i in 0 until listDataSpending.size) {

            val item = listDataSpending.get(i)

            totalSpend_int = totalSpend_int + item.nominal.toInt()

        }

        total_spend_today.setText(Util.numberFormat(totalSpend_int.toString()))

    }

    fun setLocalDatabase() {

        spendingDao = Room.databaseBuilder(requireContext(), SpendingDatabase::class.java, SpendingDatabase.DB_NAME).allowMainThreadQueries().build().spendingDao()

    }

    private fun setCategoryDB() {

        val checkDataCategory = spendingDao.getAllCategory()

        if (checkDataCategory.size == 0) {

            Util.log(TAG, "Menyimpan data category ...")

            val listCategory = listOf(

                    CategoryEntity(1, "Makanan", R.drawable.ic_food_black, 0),
                    CategoryEntity(2, "Belanja", R.drawable.ic_shopping_bag_black, 0),
                    CategoryEntity(3, "Hiburan", R.drawable.ic_ticket_black, 0),
                    CategoryEntity(4, "Transportasi", R.drawable.ic_bus_black, 0),

                    CategoryEntity(5, "Pendidikan", R.drawable.ic_mortarboard_black, 0),
                    CategoryEntity(6, "Keluarga", R.drawable.ic_family_black, 0),
                    CategoryEntity(7, "Elektronik", R.drawable.ic_photo_camera_black, 0),
                    CategoryEntity(8, "Lainnya", R.drawable.ic_other_black, 0)

            )

            for (i in 0 until listCategory.size) {

                var data = listCategory.get(i)

                spendingDao.insertCategory(data)

            }

        } else {

            Util.log(TAG, "Data category sudah tersimpan ...")

        }

    }

    private fun setClickAction() {

        add_ll.setOnClickListener {

            startActivity(Intent(context, SpendingActivity::class.java))

        }

    }


}