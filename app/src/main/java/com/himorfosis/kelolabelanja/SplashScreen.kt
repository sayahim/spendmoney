package com.himorfosis.kelolabelanja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.network.config.Network
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.preferences.AppPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.PickerPref
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Network.build()

        Handler().postDelayed(object : Thread() {
            override fun run() {

                setDataDateToday()
                startActivity(Intent(this@SplashScreen, HomepageActivity::class.java))
            }

        }, 1000)

    }

    private fun setDataDateToday() {

        val date = SimpleDateFormat("yyyy-MM-dd")

        val dateMonth = SimpleDateFormat("MM")
        val dateYear = SimpleDateFormat("yyyy")

        val today = date.format(Date())
        val yearToday = dateYear.format(Date())
        val monthToday = dateMonth.format(Date())

        preferences = AppPreferences(this, PickerPref.KEY)
        preferences.saveString(PickerPref.MONTH, monthToday)
        preferences.saveString(PickerPref.YEAR, yearToday)
//        Util.saveData("picker", "month", monthToday, this)
//        Util.saveData("picker", "year", yearToday, this)

    }

    companion object {
        lateinit var preferences: AppPreferences
    }

}
