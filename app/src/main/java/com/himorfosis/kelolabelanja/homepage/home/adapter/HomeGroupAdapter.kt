package com.himorfosis.kelolabelanja.homepage.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.details.SpendingDetail
import com.himorfosis.kelolabelanja.homepage.home.model.HomeGroupDataModel
import com.himorfosis.kelolabelanja.utilities.Util

class HomeGroupAdapter (var context: Context) : RecyclerView.Adapter<HomeGroupAdapter.ViewHolder>() {

    private val TAG = "HomeGroupAdapter"

    private var listReportFinancial: MutableList<HomeGroupDataModel>? = ArrayList()
    private lateinit var adapterReports: HomeAdapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeGroupAdapter.ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_group_home, parent, false)
        return ViewHolder(v)

    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: HomeGroupAdapter.ViewHolder, position: Int) {

        var data = listReportFinancial!![position]

        holder.day_date.text = data.date

        Util.log(TAG, "date : " + data.date)
        Util.log(TAG, "list : " + data.financialEntitiy.size)

        if (data.financialEntitiy.size != 0) {

            holder.recycler_home_adapter.apply {

                adapterReports = HomeAdapter(context)

                // sorted list
                var sortedListDescending = data.financialEntitiy.sortedWith(compareByDescending { it.time })

                adapterReports.addAll(sortedListDescending)

                layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = adapterReports

            }
        }

    }

    private fun add(homeGroupDataModel: HomeGroupDataModel) {

        listReportFinancial!!.add(homeGroupDataModel)

        notifyItemInserted(listReportFinancial!!.size - 1)

    }

    fun addAll(posItems: List<HomeGroupDataModel>) {
        for (response in posItems) {

            add(response)

        }
    }

    fun removeListAdapter() {

        listReportFinancial!!.clear()
        notifyDataSetChanged()

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return listReportFinancial!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val recycler_home_adapter = itemView.findViewById(R.id.recycler_home_adapter) as RecyclerView
        val day_date = itemView.findViewById(R.id.day_date) as TextView

    }
}