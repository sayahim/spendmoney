package com.himorfosis.kelolabelanja.month_picker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.item_calendar_month.view.*
import java.util.*

class PickerMonthAdapter : RecyclerView.Adapter<PickerMonthAdapter.ViewHolder>() {

    private var listData: MutableList<String> = ArrayList()
    lateinit var onClickItem: OnClickItem
    private var yearSelected = ""
    private val TAG = "PickerMonthAdapter"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_month, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = listData[position]

        holder.month_tv.text = data

        var monthValue = "0"

        if (position < 10) {

            val monthPosition = position + 1
            monthValue += monthPosition.toString()

        }

        val getMonthSelected = Util.getData("picker", "month", holder.itemView.context)
        val getYearSelected = Util.getData("picker", "year", holder.itemView.context)

        Util.log(TAG, "monthValue $monthValue")
        Util.log(TAG, "getYearSelected $getMonthSelected")

        if (getMonthSelected == monthValue && yearSelected == getYearSelected) {

            // set background
            holder.bg_month_ll.setBackgroundResource(R.drawable.circle_gold)
            holder.month_tv.setTextColor(holder.itemView.context.resources.getColor(R.color.text_black_primary))

        } else {

            // delete background
            holder.bg_month_ll.setBackgroundResource(0)
        }


        holder.itemView.setOnClickListener {

            // delete background
            holder.bg_month_ll.setBackgroundResource(0)

            // set background
            holder.bg_month_ll.setBackgroundResource(R.drawable.circle_gold)
            holder.month_tv.setTextColor(holder.itemView.context.resources.getColor(R.color.text_black_primary))

            Util.log(TAG, "data click : " + data)
            Util.log(TAG, "position : " + position)

            var monthValue = "0"

            if (position < 10) {

                val monthPosition = position + 1
                monthValue += monthPosition.toString()
            } else {
                val monthPosition = position + 1
                monthValue = monthPosition.toString()
            }

            onClickItem.onItemClicked(monthValue)

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

    fun addAll(posItems: Array<String>, getYear: String) {
        for (response in posItems) {
            add(response)
        }

        yearSelected = getYear
    }

    fun removeListAdapter() {

        listData.clear()

        notifyDataSetChanged()

    }

    interface OnClickItem {
        fun onItemClicked(data: String)

    }

    fun setOnclick(onClickItem: OnClickItem) {
        this.onClickItem = onClickItem

    }

}