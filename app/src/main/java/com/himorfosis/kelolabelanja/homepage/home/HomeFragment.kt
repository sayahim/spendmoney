package com.himorfosis.kelolabelanja.homepage.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.Category
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import com.himorfosis.kelolabelanja.database.spending.SpendingDao
import com.himorfosis.kelolabelanja.database.spending.SpendingDatabase
import com.himorfosis.kelolabelanja.database.entity.SpendingEntitiy
import com.himorfosis.kelolabelanja.details.SpendingDetail
import com.himorfosis.kelolabelanja.financial.InputFinancial
import com.himorfosis.kelolabelanja.homepage.home.adapter.HomeAdapter
import com.himorfosis.kelolabelanja.homepage.home.adapter.HomeGroupAdapter
import com.himorfosis.kelolabelanja.homepage.home.model.HomeGroupDataModel
import com.himorfosis.kelolabelanja.spending.SpendingActivity
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"

//    private var listDataSpending: MutableList<SpendingEntitiy> = ArrayList()
    private var listDataFinancial: MutableList<SpendingEntitiy> = ArrayList()
    private var listPerDayData: MutableList<HomeGroupDataModel> = ArrayList()

    lateinit var spendingDao: SpendingDao

    lateinit var adapterReports: HomeAdapter
    lateinit var adapterReportsGroup: HomeGroupAdapter
    lateinit var linearLayoutManager: LinearLayoutManager


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

//        setAdapter()

        setAdapterGroup()

        selectedMonthShow()

//        setCategoryDB()

    }

    private fun selectedMonthShow() {

        val date = SimpleDateFormat("yyyy.MM.dd")
        val today = date.format(Date())

        val thisMonth = Util.convertCalendarMonth(today)
        val dateNow = Util.convertDateName(today)


        month_selected_tv.setText(thisMonth)

    }

    private fun setAdapterGroup() {

        if (listPerDayData.size != 0) {

            adapterReportsGroup = HomeGroupAdapter(requireContext())

            recycler.apply {

                // sorted list
                var sortedListDescending = listPerDayData.sortedWith(compareByDescending { it.date })

                // add data in adapter
                adapterReportsGroup.addAll(sortedListDescending)

                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                adapter = adapterReportsGroup

                addOnScrollListener(object : RecyclerView.OnScrollListener() {

                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)

                        val totalItemCount = recyclerView.layoutManager!!.itemCount
                        if ( totalItemCount == lastVisibleItemPosition + 1) {

                            add_ll.visibility = View.INVISIBLE

                        } else {

                            add_ll.visibility = View.VISIBLE

                        }
                    }

                })

            }

        }
    }

    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()


    private fun setAdapter() {

        if (listDataFinancial.size != 0) {

            status_tv.visibility = View.INVISIBLE

            adapterReports = HomeAdapter(requireContext())

            recycler.apply {

                // sorted list

//                var sortedListAscending = listDataSpending.sortedWith(compareBy({ it.date }))
                var sortedListDescending = listDataFinancial.sortedWith(compareByDescending { it.date })

                adapterReports.addAll(sortedListDescending)

                layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                adapter = adapterReports

            }

        } else {

            status_tv.visibility = View.VISIBLE
            status_tv.setText("Tidak Ada Transaksi" + "\nKetuk + Tambah untuk menambahkan")

        }

    }

    fun getAllDataToday() {

        val date = SimpleDateFormat("yyyy.MM.dd")
        val dateMonth = SimpleDateFormat("yyyy.MM")
        val today = date.format(Date())
        val month = dateMonth.format(Date())

        Util.log(TAG, "today : " + today)
        Util.log(TAG, "month : " + month)

        val dayOfMonth = 32

//        listDataSpending = spendingDao.getReportFinanceMounth(today)

        for (x in 1 until dayOfMonth) {

            var thisMonth : String

            if (x < 10) {

                thisMonth = "$month.0$x"

            } else {

                thisMonth = "$month.$x"

            }

//            Util.log(TAG, thisMonth)
            Util.log(TAG, "this month : $thisMonth")

            val data = spendingDao.getReportFinanceMounth(thisMonth)

            if (data.size != 0) {

//                listDataFinancial.addAll(data)

                Util.log(TAG, "data : $data")
                Util.log(TAG, "data size : ${data.size}")

                listPerDayData.add(HomeGroupDataModel(thisMonth, 0, 0, data))

            }

        }

//        Util.log(TAG, "list data : " + listDataSpending.size)

        var totalSpend_int = 0

        for (i in 0 until listDataFinancial.size) {

            val item = listDataFinancial.get(i)

            totalSpend_int = totalSpend_int + item.nominal!!.toInt()

        }

        total_spend_today.setText(Util.numberFormatMoney(totalSpend_int.toString()))

    }

    fun setLocalDatabase() {

        spendingDao = Room.databaseBuilder(requireContext(), SpendingDatabase::class.java, SpendingDatabase.DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .spendingDao()

    }

    private fun setCategoryDB() {

        val checkDataCategory = spendingDao.getAllCategory()

        if (checkDataCategory.size == 0) {

            Util.log(TAG, "Menyimpan data category ...")

            val listCategory = listOf(

                    CategoryEntity(1, "Makanan", "ic_food_black"),
                    CategoryEntity(2, "Belanja", "ic_shopping_bag_black"),
                    CategoryEntity(3, "Hiburan", "ic_ticket_black"),
                    CategoryEntity(4, "Transportasi", "ic_bus_black"),

                    CategoryEntity(5, "Pendidikan", "ic_mortarboard_black"),
                    CategoryEntity(6, "Keluarga", "ic_family_black"),
                    CategoryEntity(7, "Elektronik", "ic_photo_camera_black"),
                    CategoryEntity(8, "Lainnya", "ic_other_black")

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

            startActivity(Intent(context, InputFinancial::class.java))

        }

    }


}