package com.example.dispenser.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

private val KEY_ACCESS = stringPreferencesKey("access_token")
private val KEY_REFRESH = stringPreferencesKey("refresh_token")

class TokenManager(private val context: Context) {

    suspend fun saveTokens(accessToken: String?, refreshToken: String?) {
        context.dataStore.edit { prefs ->
            accessToken?.let { prefs[KEY_ACCESS] = it }
            refreshToken?.let { prefs[KEY_REFRESH] = it }
        }
    }

    suspend fun getAccessToken(): String? =
        context.dataStore.data.map { it[KEY_ACCESS] }.first()

    suspend fun getRefreshToken(): String? =
        context.dataStore.data.map { it[KEY_REFRESH] }.first()

    suspend fun clear() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_ACCESS)
            prefs.remove(KEY_REFRESH)
        }
    }
}
