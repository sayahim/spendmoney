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
import com.himorfosis.kelolabelanja.database.entity.CategoryEntity


class Util {

    companion object {

        val regularOpenSans = "regular_opensans.ttf"

        @JvmStatic fun log(tag: String, message: String) {

            Log.e(tag, message)

        }

        @JvmStatic fun saveData(DBNAME: String, Tablekey: String, value: String, context: Context) {

            val settings: SharedPreferences = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor
            editor = settings.edit()
            editor.putString(Tablekey, value)
            editor.commit()
        }


        // get data shared preference String

        @JvmStatic fun getData(DBNAME: String, Tablekey: String, context: Context): String {

            val settings: SharedPreferences = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE)
            val text: String?
            text = settings.getString(Tablekey, null)
            return text
        }

        // delete data table shared preference String

        @JvmStatic fun deleteData(DBNAME: String, context: Context) {

            val settings: SharedPreferences = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor
            editor = settings.edit()
            editor.clear()
            editor.commit()
        }

        @JvmStatic fun saveDataInt(DBNAME: String, Tablekey: String, value: Int, context: Context) {

            val settings: SharedPreferences = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor
            editor = settings.edit()
            editor.putInt(Tablekey, value)
            editor.commit()
        }

        @JvmStatic fun getDataInt(DBNAME: String, Tablekey: String, context: Context): Int {

            val settings: SharedPreferences = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE)
            val text: Int?
            text = settings.getInt(Tablekey, 0)
            return text
        }

        @JvmStatic fun deleteDataInt(DBNAME: String, context: Context) {

            val settings: SharedPreferences = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor
            editor = settings.edit()
            editor.clear()
            editor.commit()
        }

        @JvmStatic fun numberFormatMoney(nominal: String): String {

            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

            return formatRupiah.format(nominal.toDouble())

        }

        fun numberFormat(nominal: String): String {

            val formatRupiah = NumberFormat.getInstance()
            return formatRupiah.format(nominal.toDouble())

        }

        @JvmStatic fun convertImageDrawable(c: Context, ImageName: String): Int {

            val id = c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName())

            return id
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
                log("convertDateSpecific", "message : $e.message")
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


}