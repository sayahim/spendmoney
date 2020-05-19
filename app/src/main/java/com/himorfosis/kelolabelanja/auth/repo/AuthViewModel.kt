package com.himorfosis.kelolabelanja.auth.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himorfosis.kelolabelanja.auth.model.LoginRequest
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.auth.model.LoginModel
import com.himorfosis.kelolabelanja.auth.model.RegisterRequest

class AuthViewModel : ViewModel() {

    private var authRepo = AuthRepo()
    var registerResponse = MutableLiveData<StateNetwork<String>>()
    var loginResponse = MutableLiveData<StateNetwork<LoginModel>>()
    var logoutResponse = MutableLiveData<StateNetwork<String>>()

    fun registerPush(item: RegisterRequest) {
        registerResponse = authRepo.registerUser(item)
    }

    fun loginPush(item: LoginRequest) {
        loginResponse = authRepo.loginUser(item)
    }

    fun logoutPush() {
        logoutResponse = authRepo.logoutUser()
    }

}