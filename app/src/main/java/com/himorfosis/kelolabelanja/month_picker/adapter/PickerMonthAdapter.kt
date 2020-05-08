package com.himorfosis.kelolabelanja.month_picker.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.app.MyApp
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.PickerPref
import kotlinx.android.synthetic.main.item_calendar_month.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*

class PickerMonthAdapter : RecyclerView.Adapter<PickerMonthAdapter.ViewHolder>() {

    private var listData: MutableList<String> = ArrayList()
    lateinit var onClickItem: OnClickItem
    private var yearSelected = ""
    val getMonthSelected = DataPreferences.picker.getString(PickerPref.MONTH)
    val getYearSelected = DataPreferences.picker.getString(PickerPref.YEAR)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_month, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = listData[position]

        val monthPosition = position + 1
        val monthValue: String = if (monthPosition < 10) "0$monthPosition" else monthPosition.toString()

        data.let {

            holder.month_tv.text = data

            if (getMonthSelected == monthValue && yearSelected == getYearSelected) {
                // set background
                holder.bg_month_ll.setBackgroundResource(R.drawable.circle_gold)
                holder.month_tv.setTextColor(holder.itemView.context.resources.getColor(R.color.text_black_primary))
            } else {
                // delete background
                holder.bg_month_ll.setBackgroundResource(0)
            }

            holder.itemView.onClick {

                // delete background
                holder.bg_month_ll.setBackgroundResource(0)
                // set background
                holder.bg_month_ll.setBackgroundResource(R.drawable.circle_gold)
                holder.month_tv.setTextColor(holder.itemView.context.resources.getColor(R.color.text_black_primary))

                val monthSelected = position + 1
                val monthValueSelected: String = if (monthPosition < 10) "0$monthSelected" else monthSelected.toString()

                isLog("month selected : $monthValueSelected")
                onClickItem.onItemClicked(monthValueSelected)
            }

        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val month_tv = itemView.item_month_tv
        val bg_month_ll = itemView.item_bg_month
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

    private fun isLog(msg: String) {
        Log.e("PickerMonthAdapter", msg)
    }

}