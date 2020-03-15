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

    @JvmStatic fun convertDateName(date: String): String {

        val dateToday = SimpleDateFormat("yyyy-MM-dd")
        val getDateToday = dateToday.format(Date())

        var nameDateFinal = "-"

        if (date.equals(getDateToday)) {

            nameDateFinal = "Hari Ini"

        } else {

            val cal = Calendar.getInstance()
            val daysArray = arrayOf("", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")
            val monthArray = arrayOf("", "Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des")

            val dateMonth = date.substring(date.indexOf("-") + 1)
            val tanggal = dateMonth.substring(dateMonth.lastIndexOf("-") + 1)
            val bulan = dateMonth.substring(0, dateMonth.indexOf("-"))
            val tahun = date.substring(0, date.indexOf("-"))

            val intDay = cal.get(Calendar.DAY_OF_WEEK)
            val intMonth = Integer.parseInt(bulan)

            val nameMonth = monthArray[intMonth]
            val nameDay = daysArray[intDay]

            nameDateFinal = "$nameDay, $tanggal $nameMonth $tahun"

        }

        return nameDateFinal

    }

    @JvmStatic fun convertDateSpecific(date: String): String {

        val dateToday = SimpleDateFormat("yyyy-MM-dd")
        val getDateToday = dateToday.format(Date())

        var nameDateFinal = "-"

        try {


            if (date.equals(getDateToday)) {

                nameDateFinal = "Hari Ini"

            } else {

                val cal = Calendar.getInstance()
                val monthArray = arrayOf("", "Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des")

                val dateMonth = date.substring(date.indexOf("-") + 1)
                val tanggal = dateMonth.substring(dateMonth.lastIndexOf("-") + 1)
                val bulan = dateMonth.substring(0, dateMonth.indexOf("-"))
                val tahun = date.substring(0, date.indexOf("-"))

                val intDay = cal.get(Calendar.DAY_OF_WEEK)
                val intMonth = Integer.parseInt(bulan)
                val nameMonth = monthArray[intMonth]

                nameDateFinal = "$tanggal $nameMonth $tahun"

            }


        } catch (e: Exception) {
            Util.log("convertDateSpecific", "message : $e.message")
        }

        return nameDateFinal

    }

    @JvmStatic fun convertMonthName(month: String) :String {

        val monthArray = arrayOf("", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")

        var intMonth:Int

        if (month[0].equals("0")) {
            val monthValue = month.replaceBefore("0", "")
            intMonth = Integer.parseInt(monthValue)
        } else {
            intMonth = Integer.parseInt(month)
        }
        return monthArray[intMonth]

    }


}