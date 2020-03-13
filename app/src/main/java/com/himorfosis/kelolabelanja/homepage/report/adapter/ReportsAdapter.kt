package com.himorfosis.kelolabelanja.homepage.report.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy
import kotlinx.android.synthetic.main.item_progress_financial.view.*

class ReportsAdapter : RecyclerView.Adapter<ReportsAdapter.ViewHolder>() {

    private var listData: MutableList<FinancialEntitiy> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_progress_financial, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = listData[position]

//        holder.title_category_tv.text = data.category_name
//        holder.total_nominal_tv.text = "Rp " + data.total_nominal_category.toString()

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val total_nominal_tv = itemView.total_nominal_tv
        val title_category_tv = itemView.title_category_tv
        val total_progress = itemView.total_progress
        val category_image = itemView.category_img
    }

    private fun add(data: FinancialEntitiy) {

        listData.add(data)
        notifyItemInserted(listData.size - 1)
    }

    fun addAll(posItems: MutableList<FinancialEntitiy>) {
        for (response in posItems) {
            add(response)
        }
    }

    fun removeAdapter() {

        if (listData.isNotEmpty()) {
            listData.clear()
            notifyDataSetChanged()
        }
    }

}