package com.example.dispenser.data.api

import retrofit2.http.Body
import retrofit2.http.POST

// 네 백엔드 스펙에 맞게 필드명/경로만 다르면 바꿔주세요.
data class RefreshRequest(val refreshToken: String)
data class RefreshResponse(
    val accessToken: String,
    val refreshToken: String? = null // 서버가 줄 때도 있고 안 줄 때도 있음
)

interface AuthRefreshApi {
    @POST("/api/auth/refresh")
    suspend fun refresh(@Body body: RefreshRequest): RefreshResponse
}
