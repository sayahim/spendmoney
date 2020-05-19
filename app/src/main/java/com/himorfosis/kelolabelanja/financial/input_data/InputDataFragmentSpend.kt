package com.himorfosis.kelolabelanja.financial.input_data

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
import com.himorfosis.kelolabelanja.category.model.CategoryResponse
import com.himorfosis.kelolabelanja.data_sample.FinancialsData
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.date.DateSet
import com.himorfosis.kelolabelanja.utilities.preferences.*
import kotlinx.android.synthetic.main.category_fragment.*
import kotlinx.android.synthetic.main.fragment_input_financial.*
import kotlinx.android.synthetic.main.layout_status_failure.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast

class InputDataFragmentSpend : Fragment() {

    companion object {
        lateinit var viewModelCategory: CategoryViewModel
        lateinit var viewModelFinance: FinancialViewModel
        lateinit var adapterCategory: FinancialCategoryAdapter
        lateinit var loadingDialog : DialogLoading
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_input_financial, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelCategory = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModelFinance = ViewModelProvider(this).get(FinancialViewModel::class.java)
        hideKeyboard(requireContext(), view)
        setAdapter()
        initializeUI()
        fetchDataCategory()

    }

    override fun onStart() {
        super.onStart()
        isLog("spend start")
        DataPreferences.backpressed.saveString(BackpressedPref.DATA, getString(R.string.spend))
    }

    override fun onDestroy() {
        super.onDestroy()
        isLog("spend destroy")
        DataPreferences.backpressed.saveString(BackpressedPref.DATA, getString(R.string.spend))
    }

    private fun initializeUI() {

        adapterCategory.setOnclick(object : FinancialCategoryAdapter.AdapterOnClickItem {
            override fun onItemClicked(data: CategoryResponse) {
                // refresh adapter
                showInputDataFinance(data.id)
            }
        })

    }

    fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun showInputDataFinance(idCategory: String) {
        val inputDataDialog = InputFinanceBottomDialog(requireContext())
        inputDataDialog.show()
        InputFinanceBottomDialog.setOnclick(object : InputFinanceBottomDialog.Companion.OnClickItemDialog {
            override fun onItemClicked(data: InputDataModel) {

                val userId = DataPreferences.account.getString(AccountPref.ID)
//                val datetime = "${data.date} ${DateSet.getTimeNow()}"

                val datetime =
                        if (data.date.isEmpty()) DateSet.getDateTimeNow()
                        else "${data.date} ${DateSet.getTimeNow()}"

                isLog("nominal : ${data.nominal}")
                isLog("note : ${data.note}")
                isLog("date : ${data.date}")
                isLog("datetime : $datetime")
                isLog("id category : $idCategory")
                isLog("date set : ${DateSet.getTimeNow()}")

                pushFinanceData(FinanceCreateModel(
                        userId, idCategory, FinancialsData.SPEND_TYPE, data.nominal, data.note, datetime))
            }
        })
    }

    private fun pushFinanceData(data: FinanceCreateModel) {

        if (ConnectionDetector.isConnectingToInternet(requireContext())) {
            isLoading()
            viewModelFinance.createFinanceUser(data)
            viewModelFinance.financeCreateResponse.observe(this, Observer {
                loadingDialog.dismiss()
                when (it) {
                    is StateNetwork.OnSuccess -> {
                        toast(getString(R.string.success))
                        startActivity(intentFor<HomepageActivity>())
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

    private fun fetchDataCategory() {

        isLog("fetchDataCategory")
        if (ConnectionDetector.isConnectingToInternet(requireContext())) {
            viewModelCategory.categoryTypeFinancePush(CategoryData.SPEND)
            viewModelCategory.categoryTypeFinanceResponse.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is StateNetwork.OnSuccess -> {
                        if (it.data.isNotEmpty()) {
                            adapterCategory.submitList(it.data)
                        } else {
                            onFailure(
                                    getString(R.string.data_not_available),
                                    getString(R.string.data_not_available_message)
                            )
                        }                    }
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

    fun onFailure(title: String, message: String) {
        loading_category_shimmer.visibility = View.GONE
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE
        title_status_tv.text = title
        description_status_tv.text = message
    }

    private fun dialogInfo(title: String?, message: String) {
        DialogInfo(requireContext(), title.toString(), message).show()
    }

    private fun isLoading() {
        loadingDialog = DialogLoading(requireContext())
        loadingDialog.setCancelable(false)
        loadingDialog.show()
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