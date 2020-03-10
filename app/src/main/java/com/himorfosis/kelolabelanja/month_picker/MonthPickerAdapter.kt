package com.himorfosis.kelolabelanja.month_picker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.item_calendar_month.view.*
import java.util.ArrayList

class MonthPickerAdapter(private val context: Context, val adapterCallback: (String) -> Unit)
    : RecyclerView.Adapter<MonthPickerAdapter.ViewHolder>() {

    private val TAG = "MonthPickerAdapter"

    private var listData: MutableList<String>? = ArrayList()
    private var yearSelected = ""

    val getMonthSelected = Util.getData("picker", "month", context)
    val getYearSelected = Util.getData("picker", "year", context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_calendar_month, parent, false))
    }

    override fun getItemCount() = listData!!.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val month_tv = itemView.item_month_tv as TextView
        val bg_month_ll = itemView.item_bg_month as LinearLayout

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val monthPosition = position + 1

        var data = listData!![position]

        holder.month_tv.text = data

        if (getMonthSelected.equals(monthPosition.toString()) && yearSelected.equals(getYearSelected)) {

            // set background
            holder.bg_month_ll.setBackgroundResource(R.drawable.circle_gold)

        } else {

            // delete background
            holder.bg_month_ll.setBackgroundResource(0)

        }

        holder.itemView.setOnClickListener {

            Util.saveData("picker", "year", yearSelected, context)
            Util.saveData("picker", "month", monthPosition.toString(), context)

            holder.bg_month_ll.setBackgroundResource(R.drawable.circle_gold)

            if (monthPosition < 9) {

                adapterCallback("$yearSelected-0$monthPosition")

            } else{

                adapterCallback("$yearSelected-$monthPosition")

            }

        }


    }


    private fun add(data: String) {

        listData!!.add(data)

        notifyItemInserted(listData!!.size - 1)
    }

    fun addAll(posItems: Array<String>, year: String) {

        Util.log(TAG, "year add : $year")

        yearSelected = year

        for (response in posItems) {

            add(response)
        }
    }

    fun removeListAdapter() {

        listData!!.clear()

        notifyDataSetChanged()

    }

    interface AdapterOnClick {
        fun onClick(item: Any)
    }

}