package com.himorfosis.kelolabelanja.utilities.preferences

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context, preference: String = context.packageName) {

    private val sp: SharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE)

    fun deleteData() {
        val editor: SharedPreferences.Editor = sp.edit()
        editor.clear()
        editor.apply()
    }

    fun getString(field: String): String? {
        return sp.getString(field, "")
    }

    fun saveString(field: String, value: String? = "") {
        sp.edit().putString(field, value).apply()
    }

    fun getBoolean(field: String): Boolean? {
        return sp.getBoolean(field, false)
    }

    fun saveBoolean(field: String, value: Boolean) {
        sp.edit().putBoolean(field, value).apply()
    }


}