package com.example.dispenser.data.api

import com.example.dispenser.data.model.SignUpRequest
import com.example.dispenser.data.model.SignUpResponse
import com.example.dispenser.data.model.LoginRequest
import com.example.dispenser.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST



interface AuthService {
    @POST("/api/auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

}
