package com.himorfosis.kelolabelanja.auth.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.response.LoginResponse

class AuthViewModel : ViewModel() {

    var registerResponse = MutableLiveData<StateNetwork<String>>()
    var loginResponse = MutableLiveData<StateNetwork<LoginResponse>>()
    var logoutResponse = MutableLiveData<StateNetwork<String>>()

    fun registerPush(name: String, email: String, password: String) {
        registerResponse = AuthRepo.registerUser(name, email, password)
    }

    fun loginPush(email: String, password: String) {
        loginResponse = AuthRepo.loginUser(email, password)
    }

    fun logoutPush(idUser: String?) {
        logoutResponse = AuthRepo.logoutUser(idUser)
    }

}