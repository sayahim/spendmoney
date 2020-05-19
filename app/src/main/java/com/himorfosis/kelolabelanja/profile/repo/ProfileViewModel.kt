package com.himorfosis.kelolabelanja.profile.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.profile.model.UserModel
import com.himorfosis.kelolabelanja.profile.model.UserUpdateRequest
import okhttp3.RequestBody

class ProfileViewModel : ViewModel() {

    private val profileRepo = ProfileRepo()
    var responseUpdateProfile = MutableLiveData<StateNetwork<UserModel>>()

    fun updateUserProfile(item: UserUpdateRequest) {
        responseUpdateProfile = profileRepo.updateUserProfile(item)
    }

    fun updateProfileWithoutImage(data: Map<String, RequestBody>) {
        responseUpdateProfile = profileRepo.updateProfileWithoutImage(data)
    }

}