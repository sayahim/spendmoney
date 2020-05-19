package com.himorfosis.kelolabelanja.homepage.profile

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.auth.Login
import com.himorfosis.kelolabelanja.auth.repo.AuthViewModel
import com.himorfosis.kelolabelanja.dialog.DialogLoading
import com.himorfosis.kelolabelanja.homepage.profile.adapter.ProfileListAdapter
import com.himorfosis.kelolabelanja.homepage.profile.model.ProfileListModel
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.profile.ProfileEdit
import com.himorfosis.kelolabelanja.service.ImageService
import com.himorfosis.kelolabelanja.service.PicassoCircleTransformation
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.preferences.AccountPref
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast

class ProfileFragment : Fragment() {

    var adapterProfileList = ProfileListAdapter()

    companion object {
        private val TAG = "ProfileFragment"
        private lateinit var loadingDialog: DialogLoading
        private lateinit var viewModel: AuthViewModel

        val PROFIL_EDIT = "Profile Edit"
        val SETTING = "Setting"
        val ABOUT = "About App"
        val SHARE = "Share Info App"
        val RATE = "Rate Us"
        val SUGGESTION = "Suggestion Development"
        val LOGOUT = "Log Out"

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
        val getImage = DataPreferences.account.getString(AccountPref.IMAGE)

        if (getImage!!.isNotEmpty()) {
//            Picasso.with(requireContext())
//                    .load(getImage)
//                    .error(R.drawable.ic_broken_image)
//                    .into(profile_user_img)

            Picasso.with(requireContext())
                    .load(getImage)
                    .error(R.drawable.ic_person_black)
                    .transform(PicassoCircleTransformation())
                    .into(profile_user_img)

        } else {
            profile_img.setImageResource(R.drawable.ic_person_black)
        }

        setAdapter()
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        adapterProfileList.addAll(profileDataList())
        adapterProfileList.setOnclick(object : ProfileListAdapter.OnClickItem {
            override fun onItemClicked(data: ProfileListModel) {

                if (data.name == PROFIL_EDIT) {
                    startActivity(intentFor<ProfileEdit>())
//                } else if (data.name == SETTING) {
//                } else if (data.name == ABOUT) {
//                } else if (data.name == SHARE) {
//                } else if (data.name == RATE) {
//                } else if (data.name == SUGGESTION) {
                } else if (data.name == LOGOUT) {
                    logoutUser()
                } else {
                    toast(data.name)
                }

            }
        })

    }

    private fun logoutUser() {

        isLoading()
        isLog("User logout...")
        viewModel.logoutPush()
        viewModel.logoutResponse.observe(this, Observer {
            loadingDialog.dismiss()
            when (it) {
                is StateNetwork.OnSuccess -> {
                    isLog("Logout Success")
                    logoutSuccess()
                }
                is StateNetwork.OnError -> {
                    logoutSuccess()
                }
                else -> {
                    logoutSuccess()
                }
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

        var dataList: MutableList<ProfileListModel> = ArrayList()

        dataList.add(ProfileListModel(1, PROFIL_EDIT))
        dataList.add(ProfileListModel(2, SETTING))
        dataList.add(ProfileListModel(3, ABOUT))
        dataList.add(ProfileListModel(4, SHARE))
        dataList.add(ProfileListModel(5, RATE))
        dataList.add(ProfileListModel(6, SUGGESTION))
        dataList.add(ProfileListModel(7, LOGOUT))

        return dataList
    }

    private fun isLoading() {
        loadingDialog = DialogLoading(requireContext())
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    private fun isLog(message: String) {
        Util.log(TAG, message)
    }

}