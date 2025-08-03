package com.example.dispenser.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispenser.data.api.RetrofitClient
import com.example.dispenser.data.local.TokenManager
import com.example.dispenser.data.model.LoginRequest
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class LoginViewModel(
    private val tokenManager: TokenManager // ✅ 주입 받기
) : ViewModel() {

    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> = _loginSuccess

    val loginMessage = mutableStateOf<String?>(null)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(email, password)
                val response = RetrofitClient.authService.login(request)

                if (response.isSuccessful) {
                    val body = response.body()

                        _loginSuccess.value = true
                        loginMessage.value = "로그인 성공"
                        Log.d("로그인 토큰", "${body?.accessToken ?: ""}")

                        // ✅ JWT 토큰 저장
                        tokenManager.saveToken(body?.accessToken ?: "")
                        tokenManager.saveToken(body?.refreshToken ?: "")

                } else {
                    loginMessage.value = "서버 오류: ${response.code()}"
                    Log.e("로그인", "서버 오류: ${response.code()}")
                }
            } catch (e: Exception) {
                loginMessage.value = "예외 발생: ${e.message}"
                Log.e("로그인", "예외 발생: ${e.message}")
            }
        }
    }

    fun resetLoginSuccess() {
        _loginSuccess.value = false
    }
}
