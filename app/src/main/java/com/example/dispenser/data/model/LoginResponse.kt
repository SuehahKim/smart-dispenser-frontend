package com.example.dispenser.data.model

data class LoginResponse(
    val accessToken : String,
    val refreshToken : String,
    val id : String?,
    val email: String?,
    val role: String?
)
