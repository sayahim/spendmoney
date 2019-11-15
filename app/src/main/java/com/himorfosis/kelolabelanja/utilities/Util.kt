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

        fun log(tag: String, message: String) {

            Log.e(tag, message)

        }

        fun saveData(DBNAME: String, Tablekey: String, value: String, context: Context) {

            val settings: SharedPreferences = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor
            editor = settings.edit()
            editor.putString(Tablekey, value)
            editor.commit()
        }


        // get data shared preference String

        fun getData(DBNAME: String, Tablekey: String, context: Context): String {

            val settings: SharedPreferences = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE)
            val text: String?
            text = settings.getString(Tablekey, null)
            return text
        }

        // delete data table shared preference String

        fun deleteData(DBNAME: String, context: Context) {

            val settings: SharedPreferences = context.getSharedPreferences(DBNAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor
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

        fun convertDateName(date: String): String {

            val dateToday = SimpleDateFormat("yyyy.MM.dd")
            val getDateToday = dateToday.format(Date())

            var nameDateFinal = ""

            if (date.equals(getDateToday)) {

                nameDateFinal = "Hari Ini"

            } else {

                val cal = Calendar.getInstance()
                val daysArray = arrayOf("", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")
                val monthArray = arrayOf("", "Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des")

                val dateMonth = date.substring(date.indexOf(".") + 1)
                val tanggal = dateMonth.substring(dateMonth.lastIndexOf(".") + 1)
                val bulan = dateMonth.substring(0, dateMonth.indexOf("."))
                val tahun = date.substring(0, date.indexOf("."))

                val intDay = cal.get(Calendar.DAY_OF_WEEK)
                val intMonth = Integer.parseInt(bulan)

                val nameMonth = monthArray[intMonth]
                val nameDay = daysArray[intDay]

                nameDateFinal = "$nameDay, $tanggal $nameMonth $tahun"

            }

            return nameDateFinal

        }

        fun convertCalendarMonth(month: String): String {

            val monthArray = arrayOf("", "Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")

            val dateMonth = month.substring(month.indexOf(".") + 1)
            val bulan = dateMonth.substring(0, dateMonth.indexOf("."))

            val intMonth = Integer.parseInt(bulan)

            return monthArray[intMonth]

        }

        fun getDataListMonth(): MutableList<String> {

            var dataMonth: MutableList<String> = ArrayList<String>()
            dataMonth.add("Januari")
            dataMonth.add("Februari")
            dataMonth.add("Maret")
            dataMonth.add("April")
            dataMonth.add("Mei")
            dataMonth.add("Juni")
            dataMonth.add("Juli")
            dataMonth.add("Agustus")
            dataMonth.add("September")
            dataMonth.add("Oktober")
            dataMonth.add("November")
            dataMonth.add("Desember")

            return dataMonth

        }

        fun getDataCategoryIncome(): List<CategoryEntity> {

            var data: List<CategoryEntity> = ArrayList<CategoryEntity>()

            data = listOf(

                    CategoryEntity(1, "Gaji", "ic_money"),
                    CategoryEntity(2, "Persewaan", "ic_store"),
                    CategoryEntity(3, "Penjualan", "ic_payment"),
                    CategoryEntity(4, "Hadiah", "ic_trophy")
            )

            return data

        }

        fun getDataCategorySpending(): List<CategoryEntity> {

            var data: List<CategoryEntity> = ArrayList<CategoryEntity>()

            data = listOf(

                    CategoryEntity(1, "Makanan", "ic_eat"),
                    CategoryEntity(2, "Belanja", "ic_belanja"),
                    CategoryEntity(3, "Hiburan", "ic_acoustic_guitar"),
                    CategoryEntity(4, "Kendaraan", "ic_scooter"),

                    CategoryEntity(5, "Tiket", "ic_ticket"),
                    CategoryEntity(6, "Keluarga", "ic_family"),
                    CategoryEntity(7, "Elektronik", "ic_laptop"),
                    CategoryEntity(8, "Pendidikan", "ic_toga"),

                    CategoryEntity(9, "Rumah", "ic_house"),
                    CategoryEntity(10, "Buku", "ic_open_book"),
                    CategoryEntity(11, "Traveling", "ic_mountains"),
                    CategoryEntity(12, "Utilitas", "ic_utilities"),

                    CategoryEntity(13, "Kesehatan", "ic_medical"),
                    CategoryEntity(14, "Pajak", "ic_tax"),
                    CategoryEntity(15, "Kopi", "ic_coffee"),
                    CategoryEntity(16, "Peliharaan", "ic_cat"),

                    CategoryEntity(17, "Furnitur", "ic_sofa"),
                    CategoryEntity(18, "Sumbangan", "ic_payment"),
                    CategoryEntity(19, "Dapur", "ic_baker"),
                    CategoryEntity(20, "Tanaman", "ic_plant")

            )

            return data

        }

        fun createMonthSelectedDialog(context: Context): DatePickerDialog {

            Util.log("create month", "hiya")

            val dpd = DatePickerDialog(context, null, 2019, 1, 24)

            try {
                val datePickerDialogFields = dpd.javaClass.declaredFields
                for (datePickerDialogField in datePickerDialogFields) {

                    if (datePickerDialogField.name == "mDatePicker") {

                        datePickerDialogField.isAccessible = true
                        val datePicker = datePickerDialogField.get(dpd) as DatePicker
                        val datePickerFields = datePickerDialogField.type.declaredFields
                        for (datePickerField in datePickerFields) {
                            Log.i("test", datePickerField.name)
                            if ("mDaySpinner" == datePickerField.name) {
                                datePickerField.isAccessible = true
                                val dayPicker = datePickerField.get(datePicker)
                                (dayPicker as View).visibility = View.GONE
                            }
                        }
                    }
                }
            } catch (ex: Exception) {
            }

            return dpd
        }

    }


}