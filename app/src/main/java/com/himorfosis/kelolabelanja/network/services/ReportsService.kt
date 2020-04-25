package com.himorfosis.kelolabelanja.network.services

import com.himorfosis.kelolabelanja.homepage.chart.model.ReportCategoryModel
import com.himorfosis.kelolabelanja.reports.model.ReportCategoryDetailsModel
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ReportsService {

    @FormUrlEncoded
    @POST("reports/report_category")
    fun reportsFinancePerCategory(
            @Field("user_id") user_id: String,
            @Field("date_start") date_start: String,
            @Field("date_today") date_today: String,
            @Field("type_finance") type_finance: String
    ): Observable<List<ReportCategoryModel>>

    @FormUrlEncoded
    @POST("reports/report_category_detail")
    fun reportsFinancePerCategoryDetail(
            @Field("user_id") user_id: String,
            @Field("date_start") date_start: String,
            @Field("date_today") date_today: String,
            @Field("id_category") id_category: String
    ): Observable<List<ReportCategoryDetailsModel>>

}