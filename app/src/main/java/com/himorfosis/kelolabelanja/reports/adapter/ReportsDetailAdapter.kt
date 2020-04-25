package com.himorfosis.kelolabelanja.reports.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.data_sample.CategoryData
import com.himorfosis.kelolabelanja.reports.model.ReportCategoryDetailsModel
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.date.DateSet
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_report_detail.view.*

class ReportsDetailAdapter : RecyclerView.Adapter<ReportsDetailAdapter.ViewHolder>() {

    private var listData: MutableList<ReportCategoryDetailsModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_report_detail, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = listData[position]
        val context = holder.itemView.context

        if (data.note != null) {
            holder.name_tv.text = data.category.title
        } else {
            holder.name_tv.text = data.note
        }

        holder.date_tv.text = DateSet.convertTimestamp(data.updated_at)

        if (data.type_financial == CategoryData.INCOME) {
            holder.nominal_tv.setTextColor(ContextCompat.getColor(context, R.color.green))
            if (data.nominal != null) {
                holder.nominal_tv.text = Util.numberFormatMoney(data.nominal.toString())
            } else {
                holder.nominal_tv.text = "Rp0"
            }

        } else {

            holder.nominal_tv.setTextColor(ContextCompat.getColor(context, R.color.text_black_primary))
            if (data.nominal != null) {
                holder.nominal_tv.text = Util.numberFormatMoney(data.nominal.toString())
            } else {
                holder.nominal_tv.text = "Rp0"
            }

        }

        Picasso.with(holder.itemView.context)
                .load(data.category.image_category_url)
                .error(R.drawable.ic_broken_image)
                .into(holder.category_img)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name_tv = itemView.name_tv
        val nominal_tv = itemView.nominal_tv
        val category_img = itemView.category_img
        val date_tv = itemView.date_tv
    }

    private fun add(data: ReportCategoryDetailsModel) {
        listData.add(data)
        notifyItemInserted(listData.size - 1)
    }

    fun addAll(posItems: List<ReportCategoryDetailsModel>) {
        for (response in posItems) {
            add(response)
        }
    }

}