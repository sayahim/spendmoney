package com.himorfosis.kelolabelanja.app

import android.app.Application
import com.himorfosis.kelolabelanja.network.config.Network
import com.himorfosis.kelolabelanja.utilities.preferences.AccountPref
import com.himorfosis.kelolabelanja.utilities.preferences.AppPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.PickerPref

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        sharedPref = AppPreferences(this)
        account = AppPreferences(this, AccountPref.KEY)
        picker = AppPreferences(this, PickerPref.KEY)
//        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
        Network.serviceWithToken()
    }

//    @SuppressLint("HardwareIds")
//    fun storeDeviceId() {
//        sharedPref.setString(
//                "deviceId",
//                Settings.Secure.getString(baseContext.contentResolver, ANDROID_ID)
//        )
//    }

    companion object {
        lateinit var sharedPref: AppPreferences
        lateinit var account: AppPreferences
        lateinit var picker: AppPreferences

        fun findInAccount(key: String): String? {
            return account.getString(key)
        }

        fun findInPicker(key: String): String? {
            return picker.getString(key)
        }

        fun deleteAllPreferences() {
            picker.deleteData()
            account.deleteData()
        }
    }

}