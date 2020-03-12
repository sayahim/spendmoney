package com.himorfosis.kelolabelanja.month_picker

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.utilities.Util
import java.text.SimpleDateFormat
import java.util.*

class MonthPickerLiveData {

    companion object {

        private val dateSelected = MutableLiveData<String>()

        val TAG = "MonthPickerLiveData"

        // Called on app launch
        fun setMonthPicker(context: Context) {

            val getYearSelected = Util.getData("picker", "year", context)

            val layoutDialog = LayoutInflater.from(context).inflate(R.layout.calendar_month_picker, null)

            val dialogView = Dialog(context)
            dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogView.setCancelable(true)
            dialogView.setContentView(layoutDialog)

            val recyclerView = dialogView.findViewById(R.id.recycler_month) as RecyclerView
            val previousYear = dialogView.findViewById(R.id.previous_year_ll) as LinearLayout
            val nextYear = dialogView.findViewById(R.id.next_year_ll) as LinearLayout
            val yearSelected_tv = dialogView.findViewById(R.id.year_selected_tv) as TextView

            yearSelected_tv.text = getYearSelected

            val alertAdapter = MonthPickerAdapter(context) { item ->

                Util.log(TAG, "item callback $item")

                // set data callback
                dateSelected.value = item

                dialogView.dismiss()
            }

            recyclerView.apply {

                alertAdapter.addAll(resources.getStringArray(R.array.month_list), getYearSelected)
                layoutManager = GridLayoutManager(context, 4)
                adapter = alertAdapter

            }

            var yearSelected = getYearSelected.toInt()

            previousYear.setOnClickListener {

                yearSelected -= 1

                yearSelected_tv.text = yearSelected.toString()

                Util.log(TAG, "year selected $yearSelected")

                alertAdapter.removeListAdapter()


                recyclerView.apply {

                    alertAdapter.addAll(resources.getStringArray(R.array.month_list), yearSelected.toString())
                    layoutManager = GridLayoutManager(context, 4)
                    adapter = alertAdapter

                }

            }

            nextYear.setOnClickListener {

                yearSelected += 1

                yearSelected_tv.text = yearSelected.toString()

                Util.log(TAG, "year selected $yearSelected")

                alertAdapter.removeListAdapter()

                recyclerView.apply {

                    alertAdapter.addAll(resources.getStringArray(R.array.month_list), yearSelected.toString())
                    layoutManager = GridLayoutManager(context, 4)
                    adapter = alertAdapter

                }

            }

            dialogView.show()

        }

        fun getDataMonthPicker(): LiveData<String> {

            return dateSelected
        }

    }
}