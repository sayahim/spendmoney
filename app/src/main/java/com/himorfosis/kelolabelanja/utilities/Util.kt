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
import okhttp3.RequestBody

class Util {

    companion object {

        fun log(tag: String, message: String) {
            Log.e(tag, message)
        }

        fun numberFormatMoney(nominal: String): String {
            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
            return formatRupiah.format(nominal.toDouble())
        }

        fun createRequestString(data: String?): RequestBody {
            return RequestBody.create(okhttp3.MultipartBody.FORM, data!!)
        }


        fun convertImageDrawable(c: Context, ImageName: String): Int {
            return c.resources.getIdentifier(ImageName, "drawable", c.packageName)
        }

    }


}