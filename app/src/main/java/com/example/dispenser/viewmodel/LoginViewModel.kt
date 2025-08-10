package com.example.dispenser.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispenser.data.api.GuestLoginRequest
import com.example.dispenser.data.api.RetrofitClient
import com.example.dispenser.data.local.TokenHolder
import com.example.dispenser.data.local.TokenManager
import com.example.dispenser.data.model.LoginRequest
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    // ✅ Application Context로 TokenManager 직접 생성 (팩토리 불필요)
    private val tokenManager = TokenManager(app.applicationContext)

    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> = _loginSuccess
    val loginMessage = mutableStateOf<String?>(null)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val resp = RetrofitClient.authService.login(LoginRequest(email, password))
                if (resp.isSuccessful) {
                    val body = resp.body() ?: run {
                        loginMessage.value = "응답이 비어 있습니다"
                        return@launch
                    }

                    // ✅ 토큰 저장 + 인터셉터용 메모리 탑재
                    tokenManager.saveTokens(body.accessToken, body.refreshToken)
                    TokenHolder.accessToken = body.accessToken

                    Log.d("Login", "ACCESS TOKEN = ${body.accessToken}")
                    Log.d("Login", "REFRESH TOKEN = ${body.refreshToken}")

                    _loginSuccess.value = true
                    loginMessage.value = "로그인 성공"
                    Log.d("Login", "access=${body.accessToken.take(12)}..., refresh=${body.refreshToken?.take(12)}...")
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

    fun guestLogin(uuid: String) {
        viewModelScope.launch {
            try {
                Log.d("GuestLogin", "UUID = $uuid")

                val resp = RetrofitClient.authService.guestLogin(GuestLoginRequest(uuid))
                if (resp.isSuccessful) {
                    val body = resp.body() ?: run {
                        loginMessage.value = "게스트 응답 비어있음"
                        return@launch
                    }

                    // ✅ 토큰 저장 + 인터셉터용 메모리 탑재
                    tokenManager.saveTokens(body.accessToken, body.refreshToken)
                    TokenHolder.accessToken = body.accessToken

                    _loginSuccess.value = true
                    loginMessage.value = "게스트 로그인 성공"
                    Log.d("GuestLogin", "access=${body.accessToken.take(12)}, refresh=${body.refreshToken?.take(12)}")
                } else {
                    loginMessage.value = "게스트 서버 오류: ${resp.code()}"
                    Log.e("GuestLogin", "서버 오류: ${resp.code()} ${resp.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                loginMessage.value = "게스트 예외: ${e.message}"
                Log.e("GuestLogin", "예외", e)
            }
        }
    }

    fun resetLoginSuccess() { _loginSuccess.value = false }
}
