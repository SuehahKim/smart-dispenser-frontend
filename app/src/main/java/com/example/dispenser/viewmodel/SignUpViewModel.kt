package com.example.dispenser.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispenser.data.api.RetrofitClient
import com.example.dispenser.data.model.SignUpRequest
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    // ✅ 메시지 상태값 추가
    val signUpMessage = mutableStateOf<String?>(null)

    fun signUp(email: String, password: String, passwordConfirm: String, onSuccess: () -> Unit ) {
        viewModelScope.launch {
            try {
                val request = SignUpRequest(email, password, passwordConfirm)
                val response = RetrofitClient.authService.signUp(request)

                if (response.isSuccessful) {

                    Log.d("회원가입", "성공")
                    onSuccess()
                    // 성공 시 메시지를 띄우고 싶으면 이 줄 추가:
                    // signUpMessage.value = body.message ?: "회원가입 성공"
                } else {
                    val errorJson = response.errorBody()?.string()
                    val errorMessage = extractErrorMessage(errorJson)
                    signUpMessage.value = errorMessage ?: "회원가입 실패 (${response.code()})"
                }
            } catch (e: Exception) {
                val error = "통신 오류: ${e.message}"
                Log.e("회원가입", error)
                signUpMessage.value = error
            }
        }
    }

    private fun extractErrorMessage(json: String?): String? {
        return try {
            val jsonObject = org.json.JSONObject(json ?: return null)
            jsonObject.getString("message")
        } catch (e: Exception) {
            null
        }
    }

}
