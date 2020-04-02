package com.himorfosis.kelolabelanja.network.services

import com.himorfosis.kelolabelanja.auth.model.LoginModel
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ClientService {

    @FormUrlEncoded
    @POST("api/login")
    fun loginUser(
            @Field("email") email: String?,
            @Field("password") password: String?
    ): Observable<LoginModel>

}