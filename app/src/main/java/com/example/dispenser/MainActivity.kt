package com.example.dispenser

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.dispenser.data.local.TokenHolder
import com.example.dispenser.data.local.TokenManager
import com.example.dispenser.navigation.NavGraph
import com.example.dispenser.navigation.Screen
import com.example.dispenser.ui.screens.StartLoggedInScreen
import com.example.dispenser.ui.theme.DispenserTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DispenserTheme {
                val context = LocalContext.current
                val tokenManager = remember { TokenManager(context) }

                var userEmail by remember { mutableStateOf<String?>(null) }
                var userId by remember { mutableStateOf<String?>(null) }
                var startNavRoute by remember { mutableStateOf<String?>(null) }
                var showLoggedInStart by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    val access = tokenManager.getAccessToken() // ← DataStore에 저장된 것만 검사
                    userEmail = tokenManager.getUserEmail()
                    userId = tokenManager.getUserId()

                    TokenHolder.accessToken = access

                    val isLoggedIn = isValidJwt(access)
                    Log.d("MainActivity", "storedAccess?=${!access.isNullOrBlank()} isLoggedIn=$isLoggedIn")

                    if (isLoggedIn) {
                        showLoggedInStart = true
                        startNavRoute = Screen.MemberHome.route
                    } else {
                        showLoggedInStart = false
                        startNavRoute = Screen.Welcome.route
                    }
                }

                if (showLoggedInStart) {
                    StartLoggedInScreen(
                        userEmail = userEmail,
                        userId = userId,
                        onMakeSauce = {
                            showLoggedInStart = false
                            startNavRoute = Screen.MemberHome.route
                        },
                        onLogout = {
                            tokenManager.clear()
                            TokenHolder.accessToken = null
                            showLoggedInStart = false
                            startNavRoute = Screen.Welcome.route
                        }
                    )
                } else {
                    if (startNavRoute != null) {
                        NavGraph(startDestination = startNavRoute!!)
                    }
                }
            }
        }
    }

    private fun isValidJwt(token: String?): Boolean {
        if (token.isNullOrBlank()) return false
        val dotCount = token.count { it == '.' }
        return dotCount >= 2 && token.length >= 40
    }
}
