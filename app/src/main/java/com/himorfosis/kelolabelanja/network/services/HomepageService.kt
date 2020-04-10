package com.himorfosis.kelolabelanja.network.services

import com.himorfosis.kelolabelanja.financial.model.FinanceCreateResponse
import com.himorfosis.kelolabelanja.homepage.home.model.HomepageResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface HomepageService {

    @FormUrlEncoded
    @POST("homepage")
    fun homepageFetch(
            @Field("user_id") user_id: String?,
            @Field("date_today") date_start: String?
    ): Observable<HomepageResponse>

}