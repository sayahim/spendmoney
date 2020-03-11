package com.himorfosis.kelolabelanja.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.month_picker.adapter.PickerMonthAdapter
import com.himorfosis.kelolabelanja.utilities.Util

class DialogShow {

    class AlertDialogValidation(internal var context: Context, internal var getMessage: String, internal var getPositive: String, internal var getNegative: String) : DialogFragment() {

        lateinit var onItemClick: OnDialogClickCallback

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.dialog_validation, container)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val messageTv = view.findViewById<TextView>(R.id.messageTv)
            val nope_btn = view.findViewById<Button>(R.id.nopeBtn)
            val yes_btn = view.findViewById<Button>(R.id.yesBtn)

            messageTv.text = getMessage
            yes_btn.text = getPositive
            nope_btn.text = getNegative

            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            nope_btn.setOnClickListener {
                dismiss()
            }


            yes_btn.setOnClickListener {
                dismiss()
                onItemClick.onYesClicked()

            }

        }

        interface OnDialogClickCallback {
            fun onYesClicked()
        }

        fun onItemClick(onItemClick: OnDialogClickCallback) {
            this.onItemClick = onItemClick
        }

    }

    class AlertMessage(internal var context: Context, internal var getMessage: String) : DialogFragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.dialog_validation, container)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val messageTv = view.findViewById<TextView>(R.id.messageTv)

            messageTv.text = getMessage

            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        }

    }

    class MonthPicker(internal var context: Context) : DialogFragment() {

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

            val adapterPicker = PickerMonthAdapter()

            recyclerView.apply {

                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = adapterPicker
            }

            adapterPicker.addAll(resources.getStringArray(R.array.month_list))

            adapterPicker.setOnclick(object : PickerMonthAdapter.OnClickItem{
                override fun onItemClicked(data: String) {

                }
            })


//            val messageTv = view.findViewById<TextView>(R.id.messageTv)
//
//            messageTv.text = getMessage
//
//            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        }

    }


}