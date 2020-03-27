package com.himorfosis.kelolabelanja.homepage.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.database.entity.FinancialEntitiy
import com.himorfosis.kelolabelanja.financial.FinancialDetail
import com.himorfosis.kelolabelanja.utilities.Util

class HomeAdapter(var context: Context) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var listReportFinancial: MutableList<FinancialEntitiy>? = ArrayList<FinancialEntitiy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_home_spending, parent, false)
        return ViewHolder(v)

    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {

        var data = listReportFinancial!![position]

        if (data.note.equals("-")) {

            holder.name_tv.text = data.category_name

        } else {

            holder.name_tv.text = data.note

        }

        if (data.type == "income") {

            holder.nominal_tv.setTextColor(ContextCompat.getColor(context, R.color.green))

            if (data.nominal != "") {

                holder.nominal_tv.text = Util.numberFormatMoney(data.nominal)

            } else{

                holder.nominal_tv.text = "Rp0"
            }

        } else {

            holder.nominal_tv.setTextColor(ContextCompat.getColor(context, R.color.text_black_primary))

            if (data.nominal != "") {

                holder.nominal_tv.text =  Util.numberFormatMoney(data.nominal)

            } else {

                holder.nominal_tv.text = "Rp0"
            }

        }

        val imageAssets = Util.convertImageDrawable(context, data.category_image)

        holder.category_img.setImageResource(imageAssets)

        holder.itemView.setOnClickListener {

            val intent = Intent(context, FinancialDetail::class.java)
            intent.putExtra("id", data.id)
            context.startActivity(intent)

        }


    }

    private fun add(financialEntitiy: FinancialEntitiy) {

        listReportFinancial!!.add(financialEntitiy)

        notifyItemInserted(listReportFinancial!!.size - 1)

    }

    fun addAll(posItems: List<FinancialEntitiy>) {
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

            val name_tv = itemView.findViewById(R.id.name_tv) as TextView
//            val time_tv = itemView.findViewById(R.id.time_tv) as TextView
            val nominal_tv = itemView.findViewById(R.id.nominal_tv) as TextView
            val category_img = itemView.findViewById(R.id.category_img) as ImageView

    }

}