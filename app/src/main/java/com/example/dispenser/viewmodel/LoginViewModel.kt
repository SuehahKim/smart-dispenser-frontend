package com.example.dispenser.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispenser.data.api.RetrofitClient
import com.example.dispenser.data.local.TokenManager
import com.example.dispenser.data.model.LoginRequest
import kotlinx.coroutines.launch

class LoginViewModel(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> = _loginSuccess
    val loginMessage = mutableStateOf<String?>(null)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val resp = RetrofitClient.authService.login(LoginRequest(email, password))
                if (resp.isSuccessful) {
                    val body = resp.body()
                    if (body == null) {
                        loginMessage.value = "응답이 비어 있습니다"
                        return@launch
                    }

                    // ✅ access + refresh 둘 다 저장 (덮어쓰기 방지)
                    tokenManager.saveTokens(body.accessToken, body.refreshToken)
                    com.example.dispenser.data.local.TokenHolder.accessToken = body.accessToken


                    _loginSuccess.value = true
                    loginMessage.value = "로그인 성공"
                    Log.d("Login", "access=${body.accessToken.take(12)}..., refresh=${body.refreshToken.take(12)}...")
                } else {
                    loginMessage.value = "서버 오류: ${resp.code()}"
                    Log.e("Login", "서버 오류: ${resp.code()} ${resp.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                loginMessage.value = "예외 발생: ${e.message}"
                Log.e("Login", "예외 발생", e)
            }
        }
    }

    fun resetLoginSuccess() { _loginSuccess.value = false }
}
