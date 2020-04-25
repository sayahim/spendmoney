package com.himorfosis.kelolabelanja.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.auth.repo.AuthViewModel
import com.himorfosis.kelolabelanja.dialog.DialogInfo
import com.himorfosis.kelolabelanja.dialog.DialogLoading
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.network.config.Network
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.auth.model.LoginResponse
import com.himorfosis.kelolabelanja.network.config.ConnectionDetector
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.preferences.AccountPref
import com.himorfosis.kelolabelanja.utilities.preferences.AppPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class Login : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        initializeUI()
        val token = DataPreferences.account.getString(AccountPref.TOKEN)
        isLog("token : $token")

    }

    private fun initializeUI() {

        login_btn.onClick {
            if (ConnectionDetector.isConnectingToInternet(this@Login)) {
                val email = email_ed.text.toString()
                val password = password_ed.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    pushLogin(email, password)
                } else {
                    toast(getString(R.string.please_complete_data))
                }
            } else {
                dialogInfo(
                        getString(R.string.disconnect),
                        getString(R.string.disconnect_message)
                )
            }

        }

        register_tv.onClick {
            startActivity(intentFor<Register>())
        }

        close_login_img.onClick {
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
