package com.example.dispenser.data.api

import com.example.dispenser.data.local.TokenHolder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://54.180.115.162/" // 기존 그대로

    // 🔹 헤더 자동 부착용 초미니 인터셉터
    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val access = TokenHolder.accessToken
        val req = if (!access.isNullOrBlank()) {
            original.newBuilder()
                .addHeader("Authorization", "Bearer $access")
                .build()
        } else original
        chain.proceed(req)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client) // 🔸 여기만 추가
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authService: AuthService = retrofit.create(AuthService::class.java)
}
