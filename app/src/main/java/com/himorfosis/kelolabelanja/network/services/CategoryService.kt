package com.himorfosis.kelolabelanja.network.services

import com.himorfosis.kelolabelanja.response.CategoryResponse
import io.reactivex.Observable
import retrofit2.http.*

interface CategoryService {

    @GET("category/all")
    fun category(): Observable<List<CategoryResponse>>

    @FormUrlEncoded
    @POST("category/create")
    fun createCategory(
            @Field("user_id") user_id: String?,
            @Field("title") title: String?,
            @Field("description") description: String?,
            @Field("type_category") type_category: String?,
            @Field("image") image: String?
    ): Observable<CategoryResponse>

    @FormUrlEncoded
    @POST("category/update")
    fun updateCategory(
            @Field("id") id: String?,
            @Field("title") title: String?,
            @Field("description") description: String?,
            @Field("image") image: String?
    ): Observable<CategoryResponse>

    @FormUrlEncoded
    @POST("category/type_finance/")
    fun categoryTypeFinance(
            @Field("type") type_finance: String?
    ): Observable<List<CategoryResponse>>

    @FormUrlEncoded
    @DELETE("category/delete")
    fun deleteCategory(
            @Field("id") id: String?
    ): Observable<CategoryResponse>

}