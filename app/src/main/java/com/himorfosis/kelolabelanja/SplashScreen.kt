package com.himorfosis.kelolabelanja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.network.config.Network
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.preferences.AppPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.PickerPref
import kotlinx.android.synthetic.main.home_fragment.*
import org.jetbrains.anko.intentFor
import java.text.SimpleDateFormat
import java.util.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Network.build()
        setDataDateToday()

        Handler().postDelayed(object : Thread() {
            override fun run() {
                startActivity(intentFor<HomepageActivity>())
            }
        }, 1000)

    }

    private fun setDataDateToday() {

        val dateMonth = SimpleDateFormat("MM")
        val dateYear = SimpleDateFormat("yyyy")

        val yearToday = dateYear.format(Date())
        val monthToday = dateMonth.format(Date())

        DataPreferences.picker.saveString(PickerPref.MONTH, monthToday)
        DataPreferences.picker.saveString(PickerPref.YEAR, yearToday)

    }

}
