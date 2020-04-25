package com.himorfosis.kelolabelanja.auth

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.auth.repo.AuthViewModel
import com.himorfosis.kelolabelanja.dialog.DialogInfo
import com.himorfosis.kelolabelanja.dialog.DialogLoading
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.network.config.ConnectionDetector
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setToolbar()
        initializeUI()
    }

    private fun initializeUI() {

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        loadingDialog = DialogLoading(this)
        loadingDialog.setCancelable(false)

        register_btn.onClick {

            if (ConnectionDetector.isConnectingToInternet(this@Register)) {

                val name = name_ed.text.toString()
                val email = email_ed.text.toString()
                val password = password_ed.text.toString()
                val confirm_pass = password_confirm_ed.text.toString()
                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirm_pass.isNotEmpty()) {
                    if (password == confirm_pass) {
                        registerPush(name, email, password)
                    } else {
                        dialogInfo(getString(R.string.password_not_match), getString(R.string.password_not_match_message))
                    }
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

    }

    private fun registerPush(name: String, email: String, password: String) {

        loadingDialog.show()

        viewModel.registerPush(name, email, password)
        viewModel.registerResponse.observe(this, Observer {
            loadingDialog.dismiss()

            when (it) {
                is StateNetwork.OnSuccess -> registerSuccess()
                is StateNetwork.OnError -> dialogInfo(it.error, it.message)
                else -> toast(getString(R.string.failed_server_connection))
            }

        })

    }


    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, android.R.color.white)))
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)

        titleBar_tv.text = getString(R.string.register)

        backBar_btn.setOnClickListener {
            startActivity(intentFor<HomepageActivity>())
        }

    }


    private fun isLog(message: String) {
        Util.log("Register", message)
    }

    private fun registerSuccess() {
        toast("Register Sukses")
        startActivity(intentFor<Login>())
    }

    private fun dialogInfo(title: String?, message: String?) {
        DialogInfo(this, title.toString(), message.toString()).show()
    }

    companion object {
        private lateinit var viewModel: AuthViewModel
        private lateinit var loadingDialog: DialogLoading

    }
}
