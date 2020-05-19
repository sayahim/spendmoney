package com.himorfosis.kelolabelanja.utilities.preferences

import android.content.Context

object DataPreferences {

    lateinit var default: AppPreferences
    lateinit var backpressed: AppPreferences
    lateinit var picker: AppPreferences
    lateinit var account: AppPreferences
    lateinit var category: AppPreferences

    fun invoke(context: Context) {
        default = AppPreferences(context)
        backpressed = AppPreferences(context, "BACKPRESSED")
        account = AppPreferences(context, "ACCOUNT")
        picker = AppPreferences(context, "PICKER")
        category = AppPreferences(context, "category")
    }

    fun clearAllPreferences() {
        backpressed.clear()
//        picker.clear()
        account.clear()
        category.clear()
    }

}