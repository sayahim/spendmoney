package com.himorfosis.kelolabelanja.auth.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himorfosis.kelolabelanja.network.state.StateNetwork
import com.himorfosis.kelolabelanja.auth.model.LoginResponse

class AuthViewModel : ViewModel() {

    private var authRepo = AuthRepo()
    var registerResponse = MutableLiveData<StateNetwork<String>>()
    var loginResponse = MutableLiveData<StateNetwork<LoginResponse>>()
    var logoutResponse = MutableLiveData<StateNetwork<String>>()

    fun registerPush(name: String, email: String, password: String) {
        registerResponse = authRepo.registerUser(name, email, password)
    }

    fun loginPush(email: String, password: String) {
        loginResponse = authRepo.loginUser(email, password)
    }

    fun logoutPush(idUser: String?) {
        logoutResponse = authRepo.logoutUser(idUser)
    }

}