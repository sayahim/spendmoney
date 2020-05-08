package com.himorfosis.kelolabelanja.homepage.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.data_sample.CategoryData
import com.himorfosis.kelolabelanja.financial.FinancialDetail
import com.himorfosis.kelolabelanja.homepage.home.model.HomepageResponse
import com.himorfosis.kelolabelanja.utilities.Util
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home.view.*
import org.jetbrains.anko.intentFor

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var listData: MutableList<HomepageResponse.Data.FinancePerDay>? = ArrayList<HomepageResponse.Data.FinancePerDay>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = listData!![position]
        val context = holder.itemView.context

        data.let { data->

            if (data.note.isEmpty()) {
                holder.name_tv.text = data.titleCategory
            } else {
                holder.name_tv.text = data.note
            }

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

            data.image_category_url.let {
                Glide.with(holder.itemView.context)
                        .load(it)
                        .thumbnail(0.1f)
                        .error(R.drawable.ic_broken_image)
                        .into(holder.category_img)
            }



            holder.itemView.setOnClickListener {

                context.startActivity(context.intentFor<FinancialDetail>(
                        "id" to data.id,
                        "nominal" to data.nominal.toString(),
                        "title" to data.titleCategory,
                        "date" to data.updated_at.toString(),
                        "note" to data.note,
                        "image" to data.image_category_url
                ))
            }

        }

    }

    private fun add(data: HomepageResponse.Data.FinancePerDay) {
        listData!!.add(data)
        notifyItemInserted(listData!!.size - 1)
    }

    fun addAll(item: List<HomepageResponse.Data.FinancePerDay>) {
        for (response in item) {
            add(response)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name_tv = itemView.name_tv

        //            val time_tv = itemView.findViewById(R.id.time_tv) as TextView
        val nominal_tv = itemView.nominal_tv
        val category_img = itemView.category_img

    }

    private fun isLog(message: String) {
        Util.log("HomeAdapter", message)
    }

}