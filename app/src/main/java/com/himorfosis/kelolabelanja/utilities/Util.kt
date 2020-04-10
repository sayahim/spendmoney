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
import kotlin.collections.ArrayList
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.view.View

class Util {

    companion object {

        fun log(tag: String, message: String) {
            Log.e(tag, message)
        }

        // get data shared preference String
        fun getData(DBNAME: String, Tablekey: String, context: Context): String {

            val settings: SharedPreferences = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE)
            val text: String?
            text = settings.getString(Tablekey, null)
            return text
        }

        fun getDataInt(DBNAME: String, Tablekey: String, context: Context): Int {

            val settings: SharedPreferences = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE)
            val text: Int?
            text = settings.getInt(Tablekey, 0)
            return text
        }

        fun numberFormatMoney(nominal: String): String {

            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

            return formatRupiah.format(nominal.toDouble())

        }

        fun numberFormat(nominal: String): String {

            val formatRupiah = NumberFormat.getInstance()
            return formatRupiah.format(nominal.toDouble())

        }

        fun convertImageDrawable(c: Context, ImageName: String): Int {

            val id = c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName())

            return id
        }

    }


}