package com.himorfosis.kelolabelanja.auth

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.auth.repo.AuthViewModel
import com.himorfosis.kelolabelanja.dialog.DialogInfo
import com.himorfosis.kelolabelanja.dialog.DialogLoading
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.network.config.Network
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.response.LoginResponse
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.preferences.AccountPref
import com.himorfosis.kelolabelanja.utilities.preferences.AppPreferences
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class Login : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initializeUI()
    }

    private fun initializeUI() {

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        login_btn.setOnClickListener {

            val email = email_ed.text.toString()
            val password = password_ed.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                pushLogin(email, password)
            } else {
                toast(getString(R.string.please_complete_data))
            }
        }

        register_tv.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        close_login_img.setOnClickListener {
            closeActivityLogin()
        }

    }

    private fun pushLogin(email: String, password: String) {

        isLoading()

        viewModel.loginPush(email, password)
        viewModel.loginResponse.observe(this, Observer {
            loadingDialog.dismiss()
            when (it) {
                is StateNetwork.OnSuccess ->
                        loginSuccess(it.data)
                is StateNetwork.OnError ->
                        dialogInfo(it.error, it.message)
                is StateNetwork.OnFailure ->
                        dialogInfo(getString(R.string.failed_server_connection),
                        getString(R.string.failed_server_connection_message))
            }

        })

    }

    private fun loginSuccess(response: LoginResponse) {

        val preferences = AppPreferences(this, AccountPref.KEY)
        preferences.saveString(AccountPref.ID, response.id)
        preferences.saveString(AccountPref.TOKEN, response.token)
        preferences.saveString(AccountPref.NAME, response.name)
        preferences.saveString(AccountPref.EMAIL, response.email)
        preferences.saveString(AccountPref.PHONE_NUMBER, response.phone_number)
        preferences.saveString(AccountPref.IMAGE, response.image)

        // refresh network to get token
        Network.build()

        closeActivityLogin()
    }


    private fun closeActivityLogin() {
        startActivity(intentFor<HomepageActivity>())

    }

    private fun dialogInfo(title: String?, message: String) {
        DialogInfo(this, title.toString(), message).show()
    }

    private fun isLog(message: String) {
        Util.log(TAG, message)
    }

    override fun onBackPressed() {
        closeActivityLogin()
    }

    private fun isLoading() {
        loadingDialog = DialogLoading(this)
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    companion object {
        private val TAG = "Login"
        private lateinit var viewModel: AuthViewModel
        private lateinit var loadingDialog: DialogLoading

    }

}
