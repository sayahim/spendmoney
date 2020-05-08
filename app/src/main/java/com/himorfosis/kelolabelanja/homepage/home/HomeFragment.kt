package com.himorfosis.kelolabelanja.homepage.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.app.MyApp
import com.himorfosis.kelolabelanja.financial.InputFinancial
import com.himorfosis.kelolabelanja.financial.model.FinanceCreateResponse
import com.himorfosis.kelolabelanja.financial.repo.FinancialViewModel
import com.himorfosis.kelolabelanja.homepage.home.adapter.HomeGroupAdapter
import com.himorfosis.kelolabelanja.homepage.home.model.HomepageResponse
import com.himorfosis.kelolabelanja.homepage.home.repo.HomeViewModel
import com.himorfosis.kelolabelanja.month_picker.DialogMonthPicker
import com.himorfosis.kelolabelanja.network.config.ConnectionDetector
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.utilities.date.DateSet
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.preferences.AccountPref
import com.himorfosis.kelolabelanja.utilities.preferences.AppPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.PickerPref
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.month_selected_tv
import kotlinx.android.synthetic.main.home_fragment.select_month_click_ll
import kotlinx.android.synthetic.main.layout_status_failure.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.intentFor
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    lateinit var financeViewModel: FinancialViewModel
    lateinit var homeViewModel: HomeViewModel
    lateinit var adapterHome: HomeGroupAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        loadingStart()
        initializeUI()
        setAdapter()
        fetchDataFinanceUser()

        val id  = DataPreferences.account.getString(AccountPref.ID)
        isLog("id : $id" )
    }

    private fun initializeUI() {

        layout_home.requestFocus()
        financeViewModel = ViewModelProvider(this).get(FinancialViewModel::class.java)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        add_finance_img.onClick {
            startActivity(intentFor<InputFinancial>())
        }

        select_month_click_ll.onClick {
            dialogMonthPicker()
        }

        month_selected_tv.text = DateSet.getMonthSelected()

    }

    private fun setAdapter() {
        adapterHome = HomeGroupAdapter()
        recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterHome
        }

    }

    private fun dialogMonthPicker() {

        val dialog = DialogMonthPicker(context!!)
        dialog.show(childFragmentManager, "dialog")
        dialog.setOnclick(object : DialogMonthPicker.OnClickItem {
            override fun onItemClicked(data: Boolean) {
                if (data) {

                    val getYearSelected = DataPreferences.picker.getString(PickerPref.YEAR)
                    val getMonthSelected = DataPreferences.picker.getString(PickerPref.MONTH)
                    val dateYear = SimpleDateFormat("yyyy")
                    val year = dateYear.format(Date())

                    isLog("get month : $getMonthSelected")
                    isLog("get year : $getYearSelected")

                    // show data
                    if (getYearSelected == year) {
                        val thisMonth = DateSet.convertMonthName(getMonthSelected!!)
                        month_selected_tv.text = thisMonth
                    } else {
                        val thisMonth = DateSet.convertMonthName(getMonthSelected!!)
                        month_selected_tv.text = "$thisMonth $getYearSelected"
                    }

                    adapterHome.clear()
                    loadingStart()
                    fetchDataFinanceUser()
                }
            }
        })

    }

    private fun fetchDataFinanceUser() {

        if (ConnectionDetector.isConnectingToInternet(requireContext())) {
            homeViewModel.financeUserFetch()
            homeViewModel.financeUserResponse.observe(viewLifecycleOwner, Observer {
                loadingStop()
                when (it) {
                    is StateNetwork.OnSuccess -> {
                        if (it.data.data.isNotEmpty()) {
                            onSuccessFetchHomepage(it.data)
                        } else {
                            onFailure(
                                    getString(R.string.data_transaction_not_available),
                                    getString(R.string.data_transaction_not_available_message))
                        }
                    }
                    is StateNetwork.OnError -> {
                        if (it.status == 401) {
                            DataPreferences.account.clear()
                        }
                        onFailure(it.error, it.message)
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


    private fun onSuccessFetchHomepage(data: HomepageResponse) {
        title_status_tv.visibility = View.GONE
        description_status_tv.visibility = View.GONE
        add_finance_img.visibility = View.VISIBLE

        adapterHome.addAll(data.data)
        total_income_month.text = Util.numberFormatMoney(data.totalFinanceUser.total_income.toString())
        total_spend_month.text = Util.numberFormatMoney(data.totalFinanceUser.total_spend.toString())
    }

    private fun setToolbar() {
        titleBar_tv.text = "Home"
    }

    private fun isLog(message: String) {
        Util.log(TAG, message)
    }

    private fun onFailure(title: String, description: String) {
        loadingStop()
        title_status_tv.visibility = View.VISIBLE
        description_status_tv.visibility = View.VISIBLE

        title_status_tv.text = title
        description_status_tv.text = description
        isLog("Response Failed")
    }

    private fun loadingStart() {
        loading_shimmer.startShimmerAnimation()
        loading_shimmer.visibility = View.VISIBLE
    }
    private fun loadingStop() {
        loading_shimmer.visibility = View.GONE
    }


}