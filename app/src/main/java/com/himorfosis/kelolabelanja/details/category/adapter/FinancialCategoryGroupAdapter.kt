package com.himorfosis.kelolabelanja.details.category.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.details.category.model.FinancialPerCategoryModel
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.date.DateSet

class FinancialCategoryGroupAdapter (var context: Context) : RecyclerView.Adapter<FinancialCategoryGroupAdapter.ViewHolder>() {

    private val TAG = "HomeGroupAdapter"

    private var listReportFinancial: MutableList<FinancialPerCategoryModel> = ArrayList()
    private lateinit var adapterDetails: FinancialCategoryAdapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinancialCategoryGroupAdapter.ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_financial_category_group, parent, false)
        return ViewHolder(v)

    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: FinancialCategoryGroupAdapter.ViewHolder, position: Int) {

        var data = listReportFinancial[position]


        if (data != null) {

            holder.day_date.text = DateSet.convertDateName(data.date)

            if (data.financialEntitiy.size != 0) {

                holder.recycler_category.apply {

                    adapterDetails = FinancialCategoryAdapter(context)

                    // sorted list
//                    var sortedListDescending = data.financialEntitiy.sortedWith(compareByDescending { it.time })
//
//                    adapterDetails.addAll(sortedListDescending)

                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = adapterDetails

                }

                holder.total_nominal_tv.text = "Total " + Util.numberFormatMoney( data.totalMoney.toString())
            }

        }

    }

    private fun add(financialPerCategoryModel: FinancialPerCategoryModel) {

        listReportFinancial.add(financialPerCategoryModel)

        notifyItemInserted(listReportFinancial.size - 1)

    }

    fun addAll(posItems: List<FinancialPerCategoryModel>) {
        for (response in posItems) {

            add(response)

        }
    }

    fun removeListAdapter() {

        listReportFinancial.clear()
        notifyDataSetChanged()

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return listReportFinancial.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val recycler_category = itemView.findViewById(R.id.recycler_category) as RecyclerView
        val day_date = itemView.findViewById(R.id.day_date) as TextView
        val total_nominal_tv = itemView.findViewById(R.id.total_nominal_tv) as TextView

    }
}