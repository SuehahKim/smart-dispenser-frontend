package com.example.dispenser.data.model

data class SignUpRequest(
    val email: String,
    val password: String,
    val passwordConfirm: String
)
