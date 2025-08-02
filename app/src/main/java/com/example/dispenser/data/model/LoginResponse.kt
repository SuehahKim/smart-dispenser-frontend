package com.example.dispenser.data.model

data class LoginResponse(
    val success: Boolean,
    val token: String?, // 서버 구조에 따라 다를 수 있음
    val message: String?
)
