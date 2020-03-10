package com.himorfosis.kelolabelanja.network

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.himorfosis.kelolabelanja.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Server :Application() {

    companion object {
        lateinit var account: SharedPreferences
        private var builder: Retrofit.Builder? = null

        fun findInAccount(key: String): String? {
            return account.getString(key, null)
        }

        fun <T> createService(service: Class<T>): T {
            return builder!!.build().create(service)
        }
    }

    override fun onCreate() {
        super.onCreate()
//        account = getSharedPreferences("akun", Context.MODE_PRIVATE)

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val client = OkHttpClient.Builder()
        client.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build()
            chain.proceed(request)
        }
        client.readTimeout(60, TimeUnit.SECONDS)
        client.connectTimeout(60, TimeUnit.SECONDS)

        builder = Retrofit.Builder().baseUrl(BuildConfig.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
        builder?.client(client.build())

    }



}