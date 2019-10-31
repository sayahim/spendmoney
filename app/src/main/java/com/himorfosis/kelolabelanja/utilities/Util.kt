package com.himorfosis.kelolabelanja.utilities

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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

        fun numberFormat(nominal: String): String {

            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

            return formatRupiah.format(java.lang.Double.valueOf(nominal))

        }

    }


}