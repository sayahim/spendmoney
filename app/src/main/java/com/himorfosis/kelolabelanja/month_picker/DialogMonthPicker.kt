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
import com.himorfosis.kelolabelanja.app.MyApp
import com.himorfosis.kelolabelanja.month_picker.adapter.PickerMonthAdapter
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.date.DateSet
import com.himorfosis.kelolabelanja.utilities.preferences.AppPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.PickerPref
import kotlinx.android.synthetic.main.calendar_month_picker.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class DialogMonthPicker(internal val context: Context) : DialogFragment() {

    val adapterPicker = PickerMonthAdapter()
    lateinit var onClickItem: OnClickItem
    private var yearSelected = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calendar_month_picker, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val getYearSelected = DataPreferences.picker.getString(PickerPref.YEAR)

        if (getYearSelected!!.isNotEmpty()) {
            yearSelected = getYearSelected.toInt()
            year_selected_tv.text = yearSelected.toString()
        } else {
            yearSelected = DateSet.getYearNow()
            year_selected_tv.text = yearSelected.toString()
        }

        previous_year_ll.onClick {
            yearSelected -= 1
            year_selected_tv.text = yearSelected.toString()
            refreshDataPicker(yearSelected.toString())
        }

        next_year_ll.onClick {
            yearSelected += 1
            year_selected_tv.text = yearSelected.toString()
            refreshDataPicker(yearSelected.toString())
        }

        recycler_month.apply {
            layoutManager = GridLayoutManager(context, 4)
            setHasFixedSize(true)
            adapter = adapterPicker
        }

        adapterPicker.addAll(resources.getStringArray(R.array.month_list), getYearSelected.toString())

        adapterPicker.setOnclick(object : PickerMonthAdapter.OnClickItem {
            override fun onItemClicked(dataMonth: String) {
                DataPreferences.picker.saveString(PickerPref.YEAR, yearSelected.toString())
                DataPreferences.picker.saveString(PickerPref.MONTH, dataMonth)
                dismiss()
                onClickItem.onItemClicked(true)
            }
        })

    }

    private fun refreshDataPicker(yearSelected: String) {
        adapterPicker.removeListAdapter()
        adapterPicker.addAll(resources.getStringArray(R.array.month_list), yearSelected)
    }

    interface OnClickItem {
        fun onItemClicked(data: Boolean)

    }

    fun setOnclick(onClickItem: OnClickItem) {
        this.onClickItem = onClickItem
    }

}