package com.himorfosis.kelolabelanja.app

import android.app.Application
import com.bumptech.glide.Glide
import com.himorfosis.kelolabelanja.network.config.Network
import com.himorfosis.kelolabelanja.utilities.preferences.*

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
//        sharedPref = AppPreferences(this)
        account = AppPreferences(this, AccountPref.KEY)
//        picker = AppPreferences(this, PickerPref.KEY)
        backpressed = AppPreferences(this, BackpressedPref.KEY)
        DataPreferences.invoke(this)

//        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
//        Network.serviceWithToken()
    }

//    @SuppressLint("HardwareIds")
//    fun storeDeviceId() {
//        sharedPref.setString(
//                "deviceId",
//                Settings.Secure.getString(baseContext.contentResolver, ANDROID_ID)
//        )
//    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(applicationContext).onTrimMemory(Glide.TRIM_MEMORY_RUNNING_LOW)
    }


    companion object {
//        lateinit var sharedPref: AppPreferences
        lateinit var account: AppPreferences
        lateinit var picker: AppPreferences
        lateinit var backpressed: AppPreferences

        fun findInAccount(key: String): String? {
            return account.getString(key)
        }

        fun findInPicker(key: String): String? {
            return picker.getString(key)
        }

        fun findInBackpressd(): String? {
            return backpressed.getString(BackpressedPref.DATA)
        }


    }

}