package com.himorfosis.kelolabelanja.financial.input_data

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.category.repo.CategoryViewModel
import com.himorfosis.kelolabelanja.data_sample.CategoryData
import com.himorfosis.kelolabelanja.dialog.DialogInfo
import com.himorfosis.kelolabelanja.dialog.DialogLoading
import com.himorfosis.kelolabelanja.dialog.InputFinanceBottomDialog
import com.himorfosis.kelolabelanja.financial.adapter.FinancialCategoryAdapter
import com.himorfosis.kelolabelanja.financial.model.FinanceCreateModel
import com.himorfosis.kelolabelanja.financial.model.InputDataModel
import com.himorfosis.kelolabelanja.financial.repo.FinancialViewModel
import com.himorfosis.kelolabelanja.homepage.activity.HomepageActivity
import com.himorfosis.kelolabelanja.network.config.ConnectionDetector
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.response.CategoryResponse
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.preferences.AccountPref
import com.himorfosis.kelolabelanja.utilities.preferences.AppPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.BackpressedPref
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import kotlinx.android.synthetic.main.fragment_input_financial.*
import kotlinx.android.synthetic.main.layout_status_failure.*
import org.jetbrains.anko.support.v4.toast

class InputDataFragmentIncome: Fragment() {

    companion object {
        lateinit var viewModelCategory: CategoryViewModel
        lateinit var viewModelFinance: FinancialViewModel
        lateinit var adapterCategory: FinancialCategoryAdapter
        lateinit var preferences: AppPreferences
        lateinit var loadingDialog: DialogLoading
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_input_financial, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelCategory = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModelFinance = ViewModelProvider(this).get(FinancialViewModel::class.java)
        setAdapter()
        initializeUI()

    }

    override fun onStart() {
        super.onStart()
        preferences = AppPreferences(requireContext(), BackpressedPref.KEY)
        preferences.saveString(BackpressedPref.DATA, getString(R.string.income))
    }

    private fun initializeUI() {

        getDataCategory()
        adapterCategory.setOnclick(object : FinancialCategoryAdapter.AdapterOnClickItem {
            override fun onItemClicked(data: CategoryResponse) {
                showInputDataFinance(data)
            }
        })

    }

    private fun showInputDataFinance(category: CategoryResponse) {
        val inputDataDialog = InputFinanceBottomDialog(requireContext())
        inputDataDialog.show()
        InputFinanceBottomDialog.setOnclick(object : InputFinanceBottomDialog.Companion.OnClickItemDialog {
            override fun onItemClicked(data: InputDataModel) {

                isLog("nominal : ${data.nominal}")
                isLog("note : ${data.note}")
                isLog("date : ${data.date}")
                isLog("id cat : ${category.id}")

                val userId = DataPreferences.account.getString(AccountPref.ID)

                pushFinanceData(FinanceCreateModel(
                        userId, category.id, category.type_category, data.nominal, data.note))
            }
        })
    }

    private fun pushFinanceData(data: FinanceCreateModel) {

        isLoading()

        viewModelFinance.createFinanceUser(data)
        viewModelFinance.financeCreateResponse.observe(this, Observer {
            loadingDialog.dismiss()
            when (it) {
                is StateNetwork.OnSuccess -> {
                    toast(getString(R.string.success))
                    startActivity(Intent(requireContext(), HomepageActivity::class.java))
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

    }

    private fun getDataCategory() {

        if (ConnectionDetector.isConnectingToInternet(requireContext())) {
            viewModelCategory.categoryTypeFinancePush(CategoryData.INCOME)
            viewModelCategory.categoryTypeFinanceResponse.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is StateNetwork.OnSuccess -> {
                        if (it.data.isNotEmpty()) {
                            adapterCategory.addAll(it.data)
                        } else {
                            onFailure(
                                    getString(R.string.data_not_available),
                                    getString(R.string.data_not_available_message)
                            )
                        }
                    }
                    is StateNetwork.OnError -> {
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
            onFailure(
                    getString(R.string.disconnect),
                    getString(R.string.disconnect_message))
        }

    }

    private fun isLoading() {
        loadingDialog = DialogLoading(requireContext())
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    fun onFailure(title: String, message: String) {
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE
        title_status_tv.text = title
        description_status_tv.text = message
    }

    private fun dialogInfo(title: String?, message: String) {
        DialogInfo(requireContext(), title.toString(), message.toString()).show()
    }

    private fun setAdapter() {
        adapterCategory = FinancialCategoryAdapter()
        category_finance_recycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = adapterCategory
        }

    }

    private fun isLog(message: String) {
        Util.log("Input Data Fragment", message)
    }

}