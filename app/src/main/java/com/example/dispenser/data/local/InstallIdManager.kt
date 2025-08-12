package com.example.dispenser.data.local

import android.content.Context
import java.util.UUID

class InstallIdManager(private val context: Context) {

    private val prefs by lazy {
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    fun getOrCreate(): String {
        val existing = prefs.getString("install_uuid", null)
        if (!existing.isNullOrBlank()) return existing

        val newId = UUID.randomUUID().toString()
        prefs.edit().putString("install_uuid", newId).apply()
        return newId
    }

    fun get(): String? = prefs.getString("install_uuid", null)
}
