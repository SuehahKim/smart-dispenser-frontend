// app/src/main/java/com/example/dispenser/data/local/TokenManager.kt
package com.example.dispenser.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore("auth_prefs")

class TokenManager(private val context: Context) {
    companion object {
        private val KEY_ACCESS = stringPreferencesKey("access")
        private val KEY_REFRESH = stringPreferencesKey("refresh")
        private val KEY_USER_EMAIL = stringPreferencesKey("user_email")
        private val KEY_USER_ID = stringPreferencesKey("user_id")
    }

    fun saveTokens(access: String, refresh: String) = runBlocking {
        context.dataStore.edit {
            it[KEY_ACCESS] = access
            it[KEY_REFRESH] = refresh
        }
    }

    fun saveUserInfo(email: String?, id: String?) = runBlocking {
        context.dataStore.edit {
            email?.let { e -> it[KEY_USER_EMAIL] = e }
            id?.let { v -> it[KEY_USER_ID] = v }
        }
    }

    fun getAccessToken(): String? = runBlocking {
        context.dataStore.data.first()[KEY_ACCESS]
    }

    fun getRefreshToken(): String? = runBlocking {
        context.dataStore.data.first()[KEY_REFRESH]
    }

    fun getUserEmail(): String? = runBlocking {
        context.dataStore.data.first()[KEY_USER_EMAIL]
    }

    fun getUserId(): String? = runBlocking {
        context.dataStore.data.first()[KEY_USER_ID]
    }

    fun clear() = runBlocking {
        context.dataStore.edit { it.clear() }
    }
}
