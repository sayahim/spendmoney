package com.himorfosis.kelolabelanja.homepage.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.database.entity.SpendingEntitiy
import com.himorfosis.kelolabelanja.utilities.Util

class HomeAdapter(var context: Context, var listSpending: List<SpendingEntitiy>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

//    private var listSpending: List<SpendingEntitiy> = spendingEntitiy

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_home_spending, parent, false)
        return ViewHolder(v)

    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {

        var data = listSpending.get(position)

        holder.name_tv.text = data.category_name
        holder.time_tv.text = data.time
        holder.nominal_tv.text = data.nominal

        holder.category_img.setImageResource(data.category_image)

        Util.log("adapter : ", data.category_name)
        Util.log("adapter : ", data.id.toString())

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return listSpending.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val name_tv = itemView.findViewById(R.id.name_tv) as TextView
            val time_tv = itemView.findViewById(R.id.time_tv) as TextView
            val nominal_tv = itemView.findViewById(R.id.nominal_tv) as TextView
            val category_img = itemView.findViewById(R.id.category_img) as ImageView

    }

}