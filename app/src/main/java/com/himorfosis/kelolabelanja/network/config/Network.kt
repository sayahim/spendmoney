package com.himorfosis.kelolabelanja.network.config

import com.himorfosis.kelolabelanja.BuildConfig
import com.himorfosis.kelolabelanja.app.MyApp
import com.himorfosis.kelolabelanja.network.services.ClientService
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.preferences.AccountPref
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.Exception
import java.util.concurrent.TimeUnit

object Network {

    private val retrofitBuilder = Retrofit.Builder()
    private val TAG = "Network"

    fun build() {

        isLog("start build")

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

        var token: String? = ""
        try {
            token = DataPreferences.account.getString(AccountPref.TOKEN)
        } catch (e: Exception) {
            Util.log("Network", "error : $e")
        }
        client.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            chain.proceed(request)
        }

        client.connectTimeout(90, TimeUnit.MINUTES)
        client.readTimeout(90, TimeUnit.MINUTES)

        retrofitBuilder.baseUrl(BuildConfig.baseURL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    private fun isLog(message: String) {
        Util.log(TAG, message)
    }

    fun <T> createService(service: Class<T>): T {
        return retrofitBuilder.build().create(service)
    }

}