package com.example.dispenser.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Context 확장 프로퍼티로 DataStore 정의
private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

// JWT 토큰 키 정의
private val TOKEN_KEY = stringPreferencesKey("jwt_token")

class TokenManager(private val context: Context) {

    // 토큰 저장
    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    // 토큰 불러오기
    suspend fun getToken(): String? {
        return context.dataStore.data
            .map { prefs -> prefs[TOKEN_KEY] }
            .first()
    }

    // 토큰 삭제
    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }
}
