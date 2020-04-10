package com.himorfosis.kelolabelanja.homepage.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.homepage.home.model.HomeGroupDataModel
import com.himorfosis.kelolabelanja.homepage.home.model.HomepageResponse
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.date.DateSet
import kotlinx.android.synthetic.main.item_group_home.view.*

class HomeGroupAdapter : RecyclerView.Adapter<HomeGroupAdapter.ViewHolder>() {

    private val TAG = "HomeGroupAdapter"

    private var listData: MutableList<HomepageResponse.Data> = ArrayList()
    private lateinit var adapterReports: HomeAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_group_home, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = listData[position]

        if (data != null) {

            Util.log(TAG, "date : " + data.date)

            holder.day_date.text = DateSet.convertDateSpecific(data.date)

            if (data.spend != 0) {
                holder.spend_tv.visibility = View.VISIBLE
                holder.spend_tv.text = "Out : " +  Util.numberFormatMoney(data.spend.toString())
            } else {
                holder.spend_tv.visibility = View.INVISIBLE
            }

            if (data.income != 0) {
                holder.income_tv.visibility = View.VISIBLE
                holder.income_tv.text = "In : " + Util.numberFormatMoney(data.income.toString())
            } else {
                holder.income_tv.visibility = View.INVISIBLE
            }

            if (data.financePerDay.isNotEmpty()) {

                holder.recycler_home_adapter.apply {
                    adapterReports = HomeAdapter()
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = adapterReports
                }

                // sorted list
//                var sortedListDescending = data.financialEntitiy.sortedWith(compareByDescending { it.time })
                adapterReports.addAll(data.financePerDay)

            }

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val recycler_home_adapter = itemView.recycler_home_adapter
        val day_date = itemView.day_date
        val spend_tv = itemView.spend_tv
        val income_tv = itemView.income_tv

    }

    private fun add(data: HomepageResponse.Data) {

        listData.add(data)
        notifyItemInserted(listData.size - 1)

    }

    fun addAll(item: List<HomepageResponse.Data>) {
        for (response in item) {
            add(response)
        }
    }

    fun removeListAdapter() {

        if (listData.isNotEmpty()) {

            listData.clear()
            notifyDataSetChanged()
        }

    }


}