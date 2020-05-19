package com.himorfosis.kelolabelanja.network.services

import com.himorfosis.kelolabelanja.profile.model.UserModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserService {

//    @Multipart
//    @POST("users/update")
//    fun updateProfileWithoutImage(
//            @Field("id") id: String?,
//            @Field("name") email: String?,
//            @Field("born") born: String?,
//            @Field("gender") gender: String?,
//            @Field("phone") phone: String?
//    ): Observable<UserModel>

    @Multipart
    @POST("users/update")
    fun updateProfileWithoutImage(
            @PartMap text: Map<String, @JvmSuppressWildcards RequestBody?>
    ): Observable<UserModel>

    @Multipart
    @POST("users/update")
    fun updateProfileUser(
            @Part image: MultipartBody.Part?,
            @PartMap text: Map<String, @JvmSuppressWildcards RequestBody?>
    ): Observable<UserModel>

}