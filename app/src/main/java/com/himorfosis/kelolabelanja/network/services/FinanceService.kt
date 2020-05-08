package com.himorfosis.kelolabelanja.network.services

import com.himorfosis.kelolabelanja.financial.model.FinanceCreateResponse
import com.himorfosis.kelolabelanja.financial.model.FinanceStatusModel
import io.reactivex.Observable
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FinanceService {

//    id_user:wOyR/wPw870hcUZJApjDXA
//    id_category:Oqb4YLW2jU1+tXzZ9TWj0A
//    type_financial:spend
//    nominal:20000
//    note:pisang goreng

    @FormUrlEncoded
    @POST("financials/create")
    fun financialsCreate(
            @Field("id_user") id_user: String?,
            @Field("id_category") id_category: String?,
            @Field("type_financial") type_financial: String?,
            @Field("nominal") nominal: String?,
            @Field("note") note: String?
    ): Observable<FinanceCreateResponse>

    @FormUrlEncoded
    @POST("financials/financialsUser")
    fun financialsUserFetch(
            @Field("user_id") user_id: String?,
            @Field("date_start") date_start: String?,
            @Field("date_end") date_end: String?,
            @Field("type_finance") type_finance: String?
    ): Observable<List<FinanceCreateResponse>>

    @FormUrlEncoded
    @POST("financials/delete")
    fun deleteFinance(
            @Field("id") id: String?
    ): Observable<FinanceStatusModel>

}