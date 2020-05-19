package com.himorfosis.kelolabelanja.financial

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.model.CategoryResponse
import com.himorfosis.kelolabelanja.category.repo.CategoryViewModel
import com.himorfosis.kelolabelanja.data_sample.CategoryData
import com.himorfosis.kelolabelanja.data_sample.FinancialsData
import com.himorfosis.kelolabelanja.dialog.DialogInfo
import com.himorfosis.kelolabelanja.dialog.DialogLoading
import com.himorfosis.kelolabelanja.dialog.EditFinanceBottomDialog
import com.himorfosis.kelolabelanja.financial.adapter.FinancialCategoryAdapter
import com.himorfosis.kelolabelanja.financial.model.FinanceCreateModel
import com.himorfosis.kelolabelanja.financial.model.FinanceUpdateModel
import com.himorfosis.kelolabelanja.financial.model.InputDataModel
import com.himorfosis.kelolabelanja.financial.repo.FinancialViewModel
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.network.config.ConnectionDetector
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.utilities.date.DateSet
import com.himorfosis.kelolabelanja.utilities.preferences.AccountPref
import com.himorfosis.kelolabelanja.utilities.preferences.CategoryPref
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import kotlinx.android.synthetic.main.activity_financial_edit.*
import kotlinx.android.synthetic.main.layout_status_failure.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

class FinancialEdit : AppCompatActivity() {

    companion object {
        lateinit var viewModelCategory: CategoryViewModel
        lateinit var viewModelFinance: FinancialViewModel
        lateinit var loadingDialog: DialogLoading
        lateinit var adapterCategory: FinancialCategoryAdapter

        lateinit var id: String
        lateinit var nominal: String
        lateinit var date: String
        lateinit var note: String
        lateinit var image: String
        lateinit var id_category: String
        lateinit var type_finance: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_financial_edit)

        viewModelCategory = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModelFinance = ViewModelProvider(this).get(FinancialViewModel::class.java)
        initUI()

    }

    private fun initUI() {
        setToolbar()
        setAdapter()
        fetchParseData()
        fetchDataCategory(type_finance)
        id_category.let {
            showInputDataFinance(it)
        }
        adapterCategory.setOnclick(object : FinancialCategoryAdapter.AdapterOnClickItem {
            override fun onItemClicked(data: CategoryResponse) {
                showInputDataFinance(data.id)
            }
        })

    }

    private fun fetchParseData() {
        id = intent.getStringExtra("id")
        id_category = intent.getStringExtra("id_category")
        nominal = intent.getStringExtra("nominal")
        date = intent.getStringExtra("date")
        note = intent.getStringExtra("note")
        image = intent.getStringExtra("image")
        type_finance = intent.getStringExtra("type_finance")
        DataPreferences.category.saveString(CategoryPref.SELECTED, id_category)

        isLog("id : $id")
        isLog("id_category : $id_category")
        isLog("nominal : $nominal")
        isLog("note : $note")
        isLog("type_finance : $type_finance")
        isLog("date : $date")

    }

    private fun setAdapter() {
        adapterCategory = FinancialCategoryAdapter()
        category_finance_recycler.apply {
            layoutManager = GridLayoutManager(this@FinancialEdit, 3)
            adapter = adapterCategory
        }

    }

    private fun showInputDataFinance(idCategory: String) {
        val inputDataDialog = EditFinanceBottomDialog(
                this@FinancialEdit, nominal, date, note)
        inputDataDialog.show()
        EditFinanceBottomDialog.setOnclick(object : EditFinanceBottomDialog.Companion.OnClickItemDialog {
            override fun onItemClicked(data: InputDataModel) {

                val datetime =
                        if (data.date.isEmpty()) DateSet.convertTimestamp(date.toLong())
                        else "${data.date} ${DateSet.getTimeNow()}"

                pushFinanceData(FinanceUpdateModel(
                        id, idCategory, type_finance, data.nominal, data.note, datetime))
            }
        })
    }

    private fun pushFinanceData(data: FinanceUpdateModel) {
        isLog("push finance data")
        if (ConnectionDetector.isConnectingToInternet(this@FinancialEdit)) {
            isLoading()
            viewModelFinance.editFinanceUser(data)
            viewModelFinance.financeUpdateResponse.observe(this, Observer {
                loadingDialog.dismiss()
                isLog("get response")
                when (it) {
                    is StateNetwork.OnSuccess -> {
                        toast(getString(R.string.success))
                        startActivity(Intent(this, HomepageActivity::class.java))
                    }
                    is StateNetwork.OnError -> {
                        dialogInfo(
                                it.error,
                                it.message)
                    }

                    is StateNetwork.OnFailure -> {
                        dialogInfo(
                                getString(R.string.failed_server_connection),
                                getString(R.string.failed_server_connection_message))
                    }
                }
            })

        } else {

            dialogInfo(
                    getString(R.string.disconnect),
                    getString(R.string.disconnect_message))
        }

    }

    private fun fetchDataCategory(typeFinance: String) {

        isLog("fetchDataCategory")
        isLog("type : $typeFinance")
        if (ConnectionDetector.isConnectingToInternet(this)) {
            viewModelCategory.categoryTypeFinancePush(typeFinance)
            viewModelCategory.categoryTypeFinanceResponse.observe(this, Observer {
                when (it) {
                    is StateNetwork.OnSuccess -> {
                        if (it.data.isNotEmpty()) {
                            isLog("fetch category success")
                            isLog("size : ${it.data.size}")
                            adapterCategory.submitList(it.data)

                        } else {
                            onFailure(
                                    getString(R.string.data_not_available),
                                    getString(R.string.data_not_available_message)
                            )
                        }
                    }
                    is StateNetwork.OnError -> {
                        isLog("fetch category failed")
                        onFailure(
                                it.error,
                                it.message)
                    }

                    is StateNetwork.OnFailure -> {
                        onFailure(
                                getString(R.string.failed_server_connection),
                                getString(R.string.failed_server_connection_message))
                    }
                }
            })
        } else {
            onDisconnect()
        }

    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_detail)

        titleBar_tv.text = "Edit Financial"
        backBar_btn.onClick {
            finish()
        }

    }


    private fun dialogInfo(title: String?, message: String) {
        DialogInfo(this, title.toString(), message.toString()).show()
    }

    private fun isLoading() {
        loadingDialog = DialogLoading(this@FinancialEdit)
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    fun onDisconnect() {
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE
        title_status_tv.text = getString(R.string.disconnect)
        description_status_tv.text = getString(R.string.disconnect_message)
    }

    fun onFailure(title: String, message: String) {
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE
        title_status_tv.text = title
        description_status_tv.text = message
    }

    private fun isLog(msg: String) {
        Log.e("Financial Edit", msg)
    }

}
