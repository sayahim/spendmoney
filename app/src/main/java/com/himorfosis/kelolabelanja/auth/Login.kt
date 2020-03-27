package com.himorfosis.kelolabelanja.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn.setOnClickListener {

            val email = email_ed.text.toString()
            val password = password_ed.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                startActivity(Intent(this, HomepageActivity::class.java))

            } else {

                toast("Harap Isi Data")
            }

        }

        register_tv.setOnClickListener {

            toast("Register")

        }

    }

}
