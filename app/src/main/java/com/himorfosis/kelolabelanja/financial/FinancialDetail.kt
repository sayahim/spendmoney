package com.himorfosis.kelolabelanja.financial

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.dialog.DialogDeleteData
import com.himorfosis.kelolabelanja.dialog.DialogLoading
import com.himorfosis.kelolabelanja.financial.repo.FinancialViewModel
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.homepage.home.model.HomepageResponse
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.date.DateSet
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_spending_detail.*
import kotlinx.android.synthetic.main.alert_layout_delete_data.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import kotlinx.android.synthetic.main.toolbar_detail.action_btn
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class FinancialDetail : AppCompatActivity() {

    lateinit var viewModel: FinancialViewModel
    lateinit var loadingDialog: DialogLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spending_detail)

        setToolbar()
        viewModel = ViewModelProvider(this).get(FinancialViewModel::class.java)
        setShowDataFinancialDetail()
        initUI()

    }

    private fun initUI() {
        edit_btn.onClick {
            toast("on progress")
        }
    }

    private fun setShowDataFinancialDetail() {

        val nominal = intent.getStringExtra("nominal")
        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val note = intent.getStringExtra("note")
        val image = intent.getStringExtra("image")

        Picasso.with(this@FinancialDetail)
                .load(image)
                .error(R.drawable.ic_broken_image)
                .into(category_detail_img)

        nominal_tv.text = Util.numberFormatMoney(nominal)
        category_tv.text = ": $title"
        date_tv.text = ": " + DateSet.convertTimestamp(date.toLong())
        if (note.isNotEmpty()) {
            note_tv.text = ": $note"
        } else {
            note_tv.text = ": " + getString(R.string.note_empty)
        }

    }

    private fun pushDeleteFinance() {

        isLoading()
        val id = intent.getStringExtra("id")
        viewModel.deleteFinanceUser(id)
        viewModel.deleteResponse.observe(this, Observer {
            loadingDialog.dismiss()
            when(it) {
                is StateNetwork.OnSuccess -> {
                    toast("Sukses Delete Data")
                    startActivity(intentFor<HomepageActivity>())
                } else -> {
                    toast("Gagal Delete Data")
                }
            }
        })

    }

    private fun dialogDeleteData() {
        var dialog = DialogDeleteData("Data ini akan terhapus secara permanen")
        dialog.show(supportFragmentManager, "dialog")
        dialog.isCancelable = false

        dialog.setOnclick(object : DialogDeleteData.DialogDeleteCallback {
            override fun onAcceptClicked() {
                pushDeleteFinance()
            }
        })

    }

    private fun isLoading() {
        loadingDialog = DialogLoading(this)
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)
        action_btn.visibility = View.VISIBLE
        action_btn.setImageResource(R.drawable.ic_delete_black)

        titleBar_tv.text = "Detail"
        backBar_btn.onClick {
            finish()
        }

        action_btn.onClick {
            dialogDeleteData()
        }
    }

    private fun isLog(msg: String) {
        Util.log("FanancialDetail", msg)
    }

}
