package com.himorfosis.kelolabelanja.month_picker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.item_calendar_month.view.*
import java.text.SimpleDateFormat
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

        val getMonthSelected = Util.getData("picker", "month", holder.itemView.context)
        val getYearSelected = Util.getData("picker", "year", holder.itemView.context)

        holder.month_tv.text = data

        if (getMonthSelected == null) {

            val date = SimpleDateFormat("yyyy-MM-dd")

            val dateMonth = SimpleDateFormat("MM")
            val dateYear = SimpleDateFormat("yyyy")

            val today = date.format(Date())
            val yearToday = dateYear.format(Date())
            val monthToday = dateMonth.format(Date())

            Util.log(TAG, "today : $today")
            Util.log(TAG, "year : $yearToday")
            Util.log(TAG, "month : $monthToday")

            val monthPosition = position +1
            Util.log(TAG, "month potition : $monthPosition")

        } else {

            val monthPosition = position +1

            if (getMonthSelected == monthPosition.toString() && yearSelected == getYearSelected) {

                // set background
                holder.bg_month_ll.setBackgroundResource(R.drawable.circle_gold)
                holder.month_tv.setBackgroundResource(R.color.text_black)

            } else {

                // delete background
                holder.bg_month_ll.setBackgroundResource(0)
            }

            if (getMonthSelected.toInt() == position+1) {
                // set background
                holder.bg_month_ll.setBackgroundResource(R.drawable.circle_gold)
                holder.month_tv.setBackgroundResource(R.color.text_black)

            }
        }

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