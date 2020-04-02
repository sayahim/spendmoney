package com.himorfosis.kelolabelanja.network.config

import com.himorfosis.kelolabelanja.BuildConfig
import com.himorfosis.kelolabelanja.app.MyApp
import com.himorfosis.kelolabelanja.network.services.ClientService
import com.himorfosis.kelolabelanja.utilities.preferences.AccountPref
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object Network {

    private val retrofitBuilder = Retrofit.Builder()

    fun build() {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(httpLoggingInterceptor)
        } else {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.NONE
                client.addInterceptor(this)
            }
        }

        val token = MyApp.findInAccount(AccountPref.TOKEN)
        client.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("Accept", "application/json;charset=UTF-8")
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            chain.proceed(request)
        }

        client.connectTimeout(2, TimeUnit.MINUTES)
        client.readTimeout(2, TimeUnit.MINUTES)

        retrofitBuilder.baseUrl(BuildConfig.baseURL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    fun <T> createService(service: Class<T>): T {
        return retrofitBuilder.build().create(service)
    }

}