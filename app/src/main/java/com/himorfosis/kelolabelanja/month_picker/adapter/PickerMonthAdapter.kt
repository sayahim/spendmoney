package com.himorfosis.kelolabelanja.month_picker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.item_calendar_month.view.*
import java.util.ArrayList

class PickerMonthAdapter : RecyclerView.Adapter<PickerMonthAdapter.ViewHolder>() {

    private var listData: MutableList<String> = ArrayList()
    lateinit var onClickItem: OnClickItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_month, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = listData[position]

        val getMonthSelected = Util.getData("picker", "month", holder.itemView.context)
        val monthPosition = position + 1

//        if (getMonthSelected != null) {
//
//            if (getMonthSelected == monthPosition.toString())
//        }
        holder.itemView.setOnClickListener {

            onClickItem.onItemClicked(data)

        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val month_tv = itemView.item_month_tv
        val bg_month_ll = itemView.item_bg_month


        //val title_tv = itemView.title_tv
    }

    private fun add(data: String) {
        listData.add(data)
        notifyItemInserted(listData.size - 1)
    }

    fun addAll(posItems: Array<String>) {
        for (response in posItems) {
            add(response)
        }
    }


    interface OnClickItem {
        fun onItemClicked(data: String)

    }

    fun setOnclick(onClickItem: OnClickItem) {
        this.onClickItem = onClickItem

    }

}