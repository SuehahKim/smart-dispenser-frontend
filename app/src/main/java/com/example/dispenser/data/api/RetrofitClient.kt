package com.example.dispenser.data.api

import com.example.dispenser.data.local.TokenHolder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "http://13.124.43.117/"

    // JWT 헤더 자동 부착
    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val access = TokenHolder.accessToken
        val req = if (!access.isNullOrBlank()) {
            original.newBuilder()
                .addHeader("Authorization", "Bearer $access")
                .build()
        } else {
            original
        }
        chain.proceed(req)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    private val retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
        .build()

    val authService: AuthService = retrofit.create(AuthService::class.java)
    val historyService: HistoryService = retrofit.create(HistoryService::class.java)
}
