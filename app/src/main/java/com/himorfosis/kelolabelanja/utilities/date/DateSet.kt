package com.himorfosis.kelolabelanja.utilities.date

import android.content.Context
import android.os.Build
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.PickerPref
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateSet {

    fun getDateToday(): String {

        val date = SimpleDateFormat("yyyy-MM-dd")
        val today = date.format(Date())
        return today.toString()
    }

    fun getDateNow(): String {

        val date = SimpleDateFormat("dd")
        val today = date.format(Date())
        return today.toString()
    }

    fun getDateMonthStartToday(): String {

        val date = SimpleDateFormat("yyyy-MM-")
        val today = date.format(Date())
        return today.toString() + "01"
    }

    fun convertTimestamp(dateTime: Long): String {

        val date = Date(dateTime)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return format.format(date)

//        var dateFinal: String
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val current = LocalDateTime.now()
//            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//            dateFinal =  current.format(formatter)
//        } else {
//            var date = Date()
//            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//            dateFinal = formatter.format(date)
//        }

//        return dateFinal

    }

    fun getMonthSelected(): String? {

        var monthValue: String?
        val dateYear = SimpleDateFormat("yyyy")
        val yearToday = dateYear.format(Date())

        val monthSelected = DataPreferences.picker.getString(PickerPref.MONTH)
        val yearSelected = DataPreferences.picker.getString(PickerPref.YEAR)

        // show data
        if (yearSelected == yearToday) {
            val thisMonth = convertMonthName(monthSelected)
            monthValue = thisMonth
        } else {
            val thisMonth = convertMonthName(monthSelected!!)
            monthValue = "$thisMonth  $yearSelected"
        }

        return monthValue
    }

    fun convertDateName(date: String): String {

        val dateToday = SimpleDateFormat("yyyy-MM-dd")
        val getDateToday = dateToday.format(Date())

        var nameDateFinal: String

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

    fun convertDateSpecific(date: String): String {

        val dateToday = SimpleDateFormat("yyyy-MM-dd")
        val getDateToday = dateToday.format(Date())

        var nameDateFinal = "-"

        try {

            if (date == getDateToday) {
                nameDateFinal = "Hari Ini"

            } else {

                val cal = Calendar.getInstance()
                val monthArray = arrayOf("", "Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des")

                val dateMonth = date.substring(date.indexOf("-") + 1)
                val tanggal = dateMonth.substring(dateMonth.lastIndexOf("-") + 1)
                val bulan = dateMonth.substring(0, dateMonth.indexOf("-"))
                val tahun = date.substring(0, date.indexOf("-"))

//                val intDay = cal.get(Calendar.DAY_OF_WEEK)
                val intMonth = Integer.parseInt(bulan)
                val nameMonth = monthArray[intMonth]
                nameDateFinal = "$tanggal $nameMonth $tahun"
            }


        } catch (e: Exception) {
            Util.log("convertDateSpecific", "message : $e.message")
        }

        return nameDateFinal

    }

    fun convertMonthName(month: String?) :String? {

        val monthArray = arrayOf("", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")
        var intMonth:Int

        if (month!![0].equals("0")) {
            val monthValue = month.replaceBefore("0", "")
            intMonth = Integer.parseInt(monthValue)
        } else {
            intMonth = Integer.parseInt(month)
        }
        return monthArray[intMonth]

    }


}