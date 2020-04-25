package com.himorfosis.kelolabelanja.network.services

import com.himorfosis.kelolabelanja.category.model.AssetsModel
import com.himorfosis.kelolabelanja.category.model.CategoryCreateResponse
import com.himorfosis.kelolabelanja.category.model.CategoryResponse
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
    @POST("category/userCreate")
    fun userCreateCategory(
            @Field("title") title: String?,
            @Field("assets") assets: String?,
            @Field("user_id") user_id: String?,
            @Field("type_category") type_category: String?
    ): Observable<CategoryCreateResponse>

    @FormUrlEncoded
    @DELETE("category/delete")
    fun deleteCategory(
            @Field("id") id: String?
    ): Observable<CategoryResponse>

    @GET("assets/all")
    fun assets(): Observable<List<AssetsModel>>

}