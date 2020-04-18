package com.himorfosis.kelolabelanja.homepage.profile

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
import com.himorfosis.kelolabelanja.auth.Login
import com.himorfosis.kelolabelanja.auth.repo.AuthViewModel
import com.himorfosis.kelolabelanja.dialog.DialogLoading
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.homepage.profile.adapter.ProfileListAdapter
import com.himorfosis.kelolabelanja.homepage.profile.model.ProfileListModel
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.preferences.AccountPref
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.support.v4.toast

class ProfileFragment : Fragment() {

    var adapterProfileList = ProfileListAdapter()

    companion object {
        private val TAG = "ProfileFragment"
        private lateinit var loadingDialog: DialogLoading
        private lateinit var viewModel: AuthViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeUI()

    }

    private fun initializeUI() {

        titleBar_tv.text = "Profil"
        name_user_tv.text = DataPreferences.account.getString(AccountPref.NAME)
        email_user_tv.text = DataPreferences.account.getString(AccountPref.EMAIL)

        setAdapter()
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        adapterProfileList.addAll(profileDataList())
        adapterProfileList.setOnclick(object : ProfileListAdapter.OnClickItem {
            override fun onItemClicked(data: ProfileListModel) {
                if (data.name == "Log Out") {
                    logoutUser()
                } else {
                    toast(data.name)
                }
            }
        })

    }

    private fun logoutUser() {

        isLoading()

        val idUser = MyApp.findInAccount(AccountPref.ID)
        isLog("id user $idUser")
        isLog("User logout...")

        viewModel.logoutPush(idUser)
        viewModel.logoutResponse.observe(this, Observer {
            loadingDialog.dismiss()
            when(it) {
                is StateNetwork.OnSuccess -> {
                    isLog("Logout Success")
                    logoutSuccess()}
                is StateNetwork.OnError -> {logoutSuccess()}
                else -> {logoutSuccess()}
            }
        })

    }

    private fun logoutSuccess() {
        DataPreferences.clearAllPreferences()
        startActivity(Intent(context, Login::class.java))
    }

    private fun setAdapter() {

        adapterProfileList = ProfileListAdapter()
        recycler_profile.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterProfileList
        }

    }

    private fun profileDataList(): MutableList<ProfileListModel> {

        var dataList: MutableList<ProfileListModel> = ArrayList<ProfileListModel>()

        dataList.add(ProfileListModel(1, "Personal Detail"))
        dataList.add(ProfileListModel(2, "Setting"))
        dataList.add(ProfileListModel(3, "About App"))
        dataList.add(ProfileListModel(4, "Share Info App"))
        dataList.add(ProfileListModel(5, "Rate Us"))
        dataList.add(ProfileListModel(6, "Suggestion Development"))
        dataList.add(ProfileListModel(7, "Log Out"))

        return dataList
    }

    private fun isLoading() {
        loadingDialog = DialogLoading(requireContext())
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    private fun isLog(message:String) {
        Util.log(TAG, message)
    }

}