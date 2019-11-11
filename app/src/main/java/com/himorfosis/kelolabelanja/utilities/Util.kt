package com.himorfosis.kelolabelanja.utilities

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import android.graphics.drawable.Drawable
import java.text.SimpleDateFormat

class Util {

    companion object {

        val regularOpenSans = "regular_opensans.ttf"

        fun log(tag: String, message: String) {

            Log.e(tag, message)

        }

        fun saveData(DBNAME: String, Tablekey: String, value: String, context: Context) {

            val settings: SharedPreferences
            val editor: SharedPreferences.Editor
            settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE)
            editor = settings.edit()
            editor.putString(Tablekey, value)
            editor.commit()
        }


        // get data shared preference String

        fun getData(DBNAME: String, Tablekey: String, context: Context): String? {

            val settings: SharedPreferences
            val text: String?
            settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE)
            text = settings.getString(Tablekey, null)
            return text
        }

        // delete data table shared preference String

        fun deleteData(DBNAME: String, context: Context) {

            val settings: SharedPreferences
            val editor: SharedPreferences.Editor
            settings = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE)
            editor = settings.edit()
            editor.clear()
            editor.commit()
        }

        fun numberFormatMoney(nominal: String): String {

            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

            return formatRupiah.format(java.lang.Double.valueOf(nominal))

        }

        fun numberFormat(nominal: String): String {

            val formatRupiah = NumberFormat.getInstance()
            return formatRupiah.format(java.lang.Double.valueOf(nominal))

        }

        fun convertImageDrawable(c: Context, ImageName: String): Int {

            val id = c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName())

            return id
        }

        fun convertDateName(date:String): String {

            val cal = Calendar.getInstance()
            val daysArray = arrayOf("", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")
            val monthArray = arrayOf("", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")

            val dateMonth = date.substring(date.indexOf(".") + 1)
            val tanggal = dateMonth.substring(dateMonth.lastIndexOf(".") + 1)
            val bulan = dateMonth.substring(0, dateMonth.indexOf("."))
            val tahun = date.substring(0, date.indexOf("."))

            val intDay = cal.get(Calendar.DAY_OF_WEEK)
            val intMonth = Integer.parseInt(bulan)

            val nameMonth = monthArray[intMonth]
            val nameDay = daysArray[intDay]

            val dateNameFinal = nameDay +", "+tanggal+" "+ nameMonth +" "+ tahun

//            Util.log("month", "bulan : " + monthArray)
//            Util.log("month", "hari : " + nameDay)
//            Util.log("month", "dateNameFinal " + dateNameFinal)

            return  dateNameFinal

        }

        fun convertCalendarMonth(month:String) : String {

            val monthArray = arrayOf("", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")

            val dateMonth = month.substring(month.indexOf(".") + 1)
            val bulan = dateMonth.substring(0, dateMonth.indexOf("."))

            val intMonth = Integer.parseInt(bulan)

            val nameMonth = monthArray[intMonth]

//            Util.log("month", "bulan : " + monthArray)

            return nameMonth

        }



    }


}