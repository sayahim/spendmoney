package com.himorfosis.kelolabelanja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(object : Thread() {
            override fun run() {

                startActivity(Intent(this@SplashScreen, HomepageActivity::class.java))

            }

        }, 1000)

    }
}
