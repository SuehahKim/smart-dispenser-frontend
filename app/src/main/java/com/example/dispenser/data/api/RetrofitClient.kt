package com.example.dispenser.data.api

import android.content.Context
import com.example.dispenser.data.local.TokenHolder
import com.example.dispenser.data.local.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "http://13.124.43.117/"
    // ★ 서버 주소 확인 (슬래시 유지)
    private const val BASE_URL = "http://13.124.43.117/"

    @Volatile private var retrofit: Retrofit? = null
    private lateinit var tokenManager: TokenManager

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
    /**
     * 앱 시작 시 한 번만 호출하세요.
     * 예) Application.onCreate() 또는 MainActivity.onCreate()
     */
    fun init(context: Context) {
        if (!::tokenManager.isInitialized) {
            tokenManager = TokenManager(context.applicationContext)
        }
        if (retrofit == null) {
            retrofit = buildRetrofit()
        }
    }

    /** 내부에서만 사용 */
    private fun buildRetrofit(): Retrofit {
        // 1) 로깅
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // 2) Authorization 헤더 자동 첨부
        val authInterceptor = Interceptor { chain ->
            val original = chain.request()
            val access = TokenHolder.accessToken ?: tokenManager.getAccessToken()
            val req = if (!access.isNullOrBlank()) {
                original.newBuilder()
                    .addHeader("Authorization", "Bearer $access")
                    .build()
            } else original
            chain.proceed(req)
        }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()
        // 3) refresh 전용 Retrofit (인증/인증기 없음 — 무한루프 방지)
        val refreshRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val refreshApi = refreshRetrofit.create(AuthRefreshApi::class.java)

        // 4) 메인 OkHttp: 인터셉터 + Authenticator 장착
        val client = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .authenticator(TokenAuthenticator(tokenManager, refreshApi)) // ← 401 자동 갱신
            .build()

    private val retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
        .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /** 필요 시 서비스 꺼내서 사용 */
    private fun retrofit(): Retrofit {
        checkNotNull(retrofit) { "RetrofitClient.init(context)를 먼저 호출하세요." }
        return retrofit!!
    }

    val authService: AuthService = retrofit.create(AuthService::class.java)
    val historyService: HistoryService = retrofit.create(HistoryService::class.java)
    // 네가 쓰던 방식 유지
    val authService: AuthService by lazy { retrofit().create(AuthService::class.java) }

    // 다른 API도 이렇게:
    // val memberApi: MemberApi by lazy { retrofit().create(MemberApi::class.java) }
}
