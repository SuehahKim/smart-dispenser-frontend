package com.example.dispenser.data.api

import com.example.dispenser.data.model.LoginRequest
import com.example.dispenser.data.model.LoginResponse
import com.example.dispenser.data.model.SignUpRequest
import com.example.dispenser.data.model.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // 🔁 토큰 재발급 (백엔드 경로가 /refresh 또는 /reissue면 여기를 바꿔주세요)
    @POST("/api/auth/refresh")
    suspend fun refresh(@Body request: RefreshRequest): Response<RefreshResponse>

    //게스트로그인
    @POST("/api/auth/guestLogin")
    suspend fun guestLogin(@Body request: GuestLoginRequest): Response<LoginResponse>
}

// 같은 파일에 모델 정의(파일 추가 없이 사용)
data class RefreshRequest(val refreshToken: String)
data class RefreshResponse(
    val accessToken: String,
    val refreshToken: String? = null
)

//게스트
data class GuestLoginRequest(val uuid: String)



