package com.himorfosis.kelolabelanja.month_picker

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.month_picker.adapter.PickerMonthAdapter
import com.himorfosis.kelolabelanja.utilities.Util

class DialogMonthPicker(internal val context: Context): DialogFragment() {

    val adapterPicker = PickerMonthAdapter()
    lateinit var onClickItem: OnClickItem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calendar_month_picker, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val recyclerView = view.findViewById(R.id.recycler_month) as RecyclerView
        val previousYear = view.findViewById(R.id.previous_year_ll) as LinearLayout
        val nextYear = view.findViewById(R.id.next_year_ll) as LinearLayout
        val yearSelected_tv = view.findViewById(R.id.year_selected_tv) as TextView

        val getYearSelected = Util.getData("picker", "year", context)
        val getMonthSelected = Util.getData("picker", "month", context)

        var yearSelected = 0

        if (getYearSelected != null) {

            yearSelected = getYearSelected.toInt()
            yearSelected_tv.text = yearSelected.toString()

        } else {

            yearSelected = 2020
            yearSelected_tv.text = yearSelected.toString()

        }

        previousYear.setOnClickListener {

            yearSelected -= 1

            yearSelected_tv.text = yearSelected.toString()
            refreshDataPicker(yearSelected.toString())

        }

        nextYear.setOnClickListener {

            yearSelected += 1

            yearSelected_tv.text = yearSelected.toString()
            refreshDataPicker(yearSelected.toString())

        }

        recyclerView.apply {

            layoutManager = LinearLayoutManager(context)
            layoutManager = GridLayoutManager(context, 4)
            setHasFixedSize(true)
            adapter = adapterPicker
        }

        adapterPicker.addAll(resources.getStringArray(R.array.month_list), getYearSelected)

        adapterPicker.setOnclick(object : PickerMonthAdapter.OnClickItem {
            override fun onItemClicked(dataMonth: String) {

                Util.saveData("picker", "month", dataMonth, context)
                Util.saveData("picker", "year", yearSelected.toString(), context)

                dismiss()

                onClickItem.onItemClicked(true)

            }
        })

    }

    private fun refreshDataPicker(yearSelected: String) {

//            Util.saveData("picker", "year", yearSelected, context)

        adapterPicker.removeListAdapter()

        // refresh adapter
        adapterPicker.addAll(resources.getStringArray(R.array.month_list), yearSelected)
    }

    interface OnClickItem {
        fun onItemClicked(data: Boolean)

    }

    fun setOnclick(onClickItem: OnClickItem) {
        this.onClickItem = onClickItem

    }

}