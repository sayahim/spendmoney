package com.himorfosis.kelolabelanja.app

import android.app.Application
import androidx.core.content.res.TypedArrayUtils.getText
import com.bumptech.glide.Glide
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.network.config.Network
import com.himorfosis.kelolabelanja.utilities.preferences.*

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        account = AppPreferences(this, AccountPref.KEY)
        backpressed = AppPreferences(this, BackpressedPref.KEY)
        DataPreferences.invoke(this)

    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(applicationContext).onTrimMemory(Glide.TRIM_MEMORY_RUNNING_LOW)
    }

    companion object {
        lateinit var account: AppPreferences
        lateinit var picker: AppPreferences
        lateinit var backpressed: AppPreferences

        fun findInAccount(key: String): String? {
            return account.getString(key)
        }
    }
}