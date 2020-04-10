package com.himorfosis.kelolabelanja.network.services

import com.himorfosis.kelolabelanja.auth.model.LoginModel
import com.himorfosis.kelolabelanja.response.LoginResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @FormUrlEncoded
    @POST("login")
    fun login(
            @Field("email") email: String?,
            @Field("password") password: String?
    ): Observable<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
            @Field("name") name: String?,
            @Field("email") email: String?,
            @Field("password") password: String?,
            @Field("password_confirm") password_confirm: String?
    ): Observable<ResponseBody>

    @FormUrlEncoded
    @POST("logout")
    fun logout(
            @Field("user_id") user_id: String?
    ): Observable<ResponseBody>

}