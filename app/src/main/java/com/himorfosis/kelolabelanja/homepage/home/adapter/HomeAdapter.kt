package com.himorfosis.kelolabelanja.homepage.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity
import com.himorfosis.kelolabelanja.database.entity.SpendingEntitiy
import com.himorfosis.kelolabelanja.details.SpendingDetail
import com.himorfosis.kelolabelanja.utilities.Util

class HomeAdapter(var context: Context, val adapterCallback : (SpendingEntitiy) -> Unit) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var listReportFinancial: MutableList<SpendingEntitiy>? = ArrayList<SpendingEntitiy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_home_spending, parent, false)
        return ViewHolder(v)

    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {

        var data = listReportFinancial!!.get(position)

        holder.time_tv.text = data.time
        holder.nominal_tv.text = Util.numberFormatMoney(data.nominal.toString())

        if (data.note.equals("")) {

            holder.name_tv.text = data.category_name

        } else {

            holder.name_tv.text = data.note

        }

        val imageAssets = Util.convertImageDrawable(context, data.category_image)

        holder.category_img.setImageResource(imageAssets)

        holder.itemView.setOnClickListener {

//            adapterCallback(data)

            val intent = Intent(context, SpendingDetail::class.java)
            intent.putExtra("id", data.id)
            context.startActivity(intent)

        }

        Util.log("adapter : ", data.category_name)
        Util.log("adapter : ", data.id.toString())

    }

    private fun add(spendingEntitiy: SpendingEntitiy) {

        listReportFinancial!!.add(spendingEntitiy)

        notifyItemInserted(listReportFinancial!!.size - 1)

    }

    fun addAll(posItems: List<SpendingEntitiy>) {
        for (response in posItems) {

            add(response)

        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return listReportFinancial!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val name_tv = itemView.findViewById(R.id.name_tv) as TextView
            val time_tv = itemView.findViewById(R.id.time_tv) as TextView
            val nominal_tv = itemView.findViewById(R.id.nominal_tv) as TextView
            val category_img = itemView.findViewById(R.id.category_img) as ImageView

    }

}