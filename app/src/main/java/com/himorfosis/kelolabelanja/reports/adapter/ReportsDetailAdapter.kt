package com.himorfosis.kelolabelanja.reports.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.data_sample.CategoryData
import com.himorfosis.kelolabelanja.financial.FinancialDetail
import com.himorfosis.kelolabelanja.reports.model.ReportDetailCategoryModel
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.date.DateSet
import kotlinx.android.synthetic.main.item_report_detail.view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick

class ReportsDetailAdapter : RecyclerView.Adapter<ReportsDetailAdapter.ViewHolder>() {

    private var listData: MutableList<ReportDetailCategoryModel.Data> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_report_detail, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val context = holder.itemView.context
        var data = listData[position]

        holder.bindView(data)

        holder.itemView.onClick {
            context.startActivity(context.intentFor<FinancialDetail>(
                    "id" to data.id,
                    "nominal" to data.nominal.toString(),
                    "id_category" to data.id_category,
                    "date" to data.date.toString(),
                    "note" to data.note,
                    "title" to data.title,
                    "type_finance" to data.type_finance,
                    "image" to data.image_category_url
            ))

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name_tv = itemView.name_tv
        val nominal_tv = itemView.nominal_tv
        val category_img = itemView.category_img
        val date_tv = itemView.date_tv

        fun bindView(data: ReportDetailCategoryModel.Data) {
            val context = itemView.context

            if (data.note.isEmpty() || data.note == null) {
                name_tv.text = data.title
            } else {
                name_tv.text = data.note
            }

            date_tv.text = DateSet.convertTimestamp(data.date)

            if (data.type_finance == CategoryData.INCOME) {
                nominal_tv.setTextColor(ContextCompat.getColor(context, R.color.green))
                if (data.nominal != null) {
                    nominal_tv.text = Util.numberFormatMoney(data.nominal.toString())
                } else {
                    nominal_tv.text = "Rp0"
                }
            } else {
                nominal_tv.setTextColor(ContextCompat.getColor(context, R.color.text_black_primary))
                if (data.nominal != null) {
                    nominal_tv.text = Util.numberFormatMoney(data.nominal.toString())
                } else {
                    nominal_tv.text = "Rp0"
                }

            }

            data.image_category_url.let {
                Glide.with(itemView.context)
                        .load(it)
                        .thumbnail(0.1f)
                        .error(R.drawable.ic_broken_image)
                        .into(category_img)
            }

        }

    }

    private fun add(data: ReportDetailCategoryModel.Data) {
        listData.add(data)
        notifyItemInserted(listData.size - 1)
    }

    fun addAll(posItems: List<ReportDetailCategoryModel.Data>) {
        for (response in posItems) {
            add(response)
        }
    }

}