package com.himorfosis.kelolabelanja.utilities

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

object DateSet {

    @JvmStatic fun getMonthSelected(context: Context): String {

        var monthValue: String

        val dateYear = SimpleDateFormat("yyyy")

        val yearToday = dateYear.format(Date())

        val monthSelected = Util.getData("picker", "month", context)
        val yearSelected = Util.getData("picker", "year", context)

        // show data
        if (yearSelected == yearToday) {
            val thisMonth = Util.convertMonthName(monthSelected)
            monthValue = thisMonth
        } else {
            val thisMonth = Util.convertMonthName(monthSelected)
            monthValue = "$thisMonth  $yearSelected"
        }

        return monthValue
    }


}