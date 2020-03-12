package com.himorfosis.kelolabelanja.homepage.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.model.HomeGroupDataModel
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.item_group_home.view.*

class HomeGroupAdapter (var context: Context) : RecyclerView.Adapter<HomeGroupAdapter.ViewHolder>() {

    private val TAG = "HomeGroupAdapter"

    private var listData: MutableList<HomeGroupDataModel> = ArrayList()
    private lateinit var adapterReports: HomeAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_group_home, parent, false)
        return ViewHolder(v)

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return listData.size
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = listData[position]

        if (data != null) {

            Util.log(TAG, "date : " + data.date)

            holder.day_date.text = Util.convertDateSpecific(data.date)

            if (data.totalSpending != 0) {

                holder.spend_tv.visibility = View.VISIBLE
                holder.spend_tv.text = "Out : " +  Util.numberFormatMoney(data.totalSpending.toString())

            } else {

                holder.spend_tv.visibility = View.INVISIBLE

            }

            if (data.totalIncome != 0) {

                holder.income_tv.visibility = View.VISIBLE
                holder.income_tv.text = "In : " + Util.numberFormatMoney(data.totalIncome.toString())

            } else {

                holder.income_tv.visibility = View.INVISIBLE

            }


            if (data.financialEntitiy.size != 0) {

                holder.recycler_home_adapter.apply {

                    adapterReports = HomeAdapter(context)
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = adapterReports

                }

                // sorted list
                var sortedListDescending = data.financialEntitiy.sortedWith(compareByDescending { it.time })
                adapterReports.addAll(sortedListDescending)

            }

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val recycler_home_adapter = itemView.recycler_home_adapter
        val day_date = itemView.day_date
        val spend_tv = itemView.spend_tv
        val income_tv = itemView.income_tv

    }

    private fun add(homeGroupDataModel: HomeGroupDataModel) {

        listData.add(homeGroupDataModel)
        notifyItemInserted(listData.size - 1)

    }

    fun addAll(posItems: List<HomeGroupDataModel>) {
        for (response in posItems) {
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