package com.himorfosis.kelolabelanja.details.category.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy
import com.himorfosis.kelolabelanja.homepage.report.model.ReportsSpendingModel
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.item_financial_category_details.view.*
import java.util.ArrayList

class FinancialCategoryAdapter(private val context: Context)
    : RecyclerView.Adapter<FinancialCategoryAdapter.ViewHolder>() {

    private var listData: MutableList<FinancialEntitiy> = ArrayList<FinancialEntitiy>()

    override fun getItemCount() = listData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_financial_category_details, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = listData[position]

        if (data != null) {

            holder.time_tv.text = data.time
            holder.nominal_tv.text = Util.numberFormatMoney(data.nominal)

            if (data.note.equals("-")) {

                holder.description_tv.text = "Tanpa Catatan"

            } else {
                holder.description_tv.text = data.note

            }

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time_tv = itemView.time_tv
        val description_tv = itemView.description_tv
        val nominal_tv = itemView.nominal_tv

    }

    private fun add(financialEntitiy: FinancialEntitiy) {

        listData.add(financialEntitiy)

        notifyItemInserted(listData.size - 1)

    }

    fun addAll(posItems: List<FinancialEntitiy>) {
        for (response in posItems) {

            add(response)

        }
    }

    fun removeListAdapter() {

        listData.clear()

        notifyDataSetChanged()

    }

}