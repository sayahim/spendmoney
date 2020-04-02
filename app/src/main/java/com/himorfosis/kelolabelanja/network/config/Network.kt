package com.himorfosis.kelolabelanja.network.config

import com.himorfosis.kelolabelanja.BuildConfig
import com.himorfosis.kelolabelanja.network.services.ClientService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object Network {

    private val retrofitBuilder = Retrofit.Builder()

    fun service(){
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

        val builder = Retrofit.Builder().baseUrl(BuildConfig.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        builder.client(client.build())
        return builder.build().create(ClientService::class)
    }

    fun serviceWithToken(): ClientService {
        val token = MedivApp.findInAccount("token")
        val client = OkHttpClient.Builder()
        client.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            chain.proceed(request)
        }
        client.readTimeout(60, TimeUnit.SECONDS)
        client.connectTimeout(60, TimeUnit.SECONDS)

        val builder = Retrofit.Builder().baseUrl(BuildConfig.baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
        builder.client(client.build())
        return builder.build().create(ClientInterface::class.java)
    }

    fun <T> createService(service: Class<T>): T {
        return retrofitBuilder.build().create(service)
    }

}