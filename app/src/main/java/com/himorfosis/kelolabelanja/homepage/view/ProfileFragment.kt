package com.himorfosis.kelolabelanja.homepage.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.profile.ProfileAdapter
import com.himorfosis.kelolabelanja.profile.adapter.ProfileListAdapter
import com.himorfosis.kelolabelanja.profile.model.ProfileListModel
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.toolbar_title.*

class ProfileFragment : Fragment() {

    var adapterProfileList = ProfileListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleBar_tv.text = "Profil"

        setAdapter()

        adapterProfileList.addAll(profileDataList())

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
        dataList.add(ProfileListModel(6, "suggestion Development"))
        dataList.add(ProfileListModel(7, "Log Out"))

        return dataList
    }

}