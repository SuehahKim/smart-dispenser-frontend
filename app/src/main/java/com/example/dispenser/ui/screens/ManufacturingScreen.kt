@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.dispenser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.navigation.NavController
import com.example.dispenser.navigation.Screen

@Composable
fun ManufacturingScreen(
    navController: NavController,
    onBack: () -> Unit,
    onHome: () -> Unit
) {
    var progress by remember { mutableStateOf(0f) }
    var isPaused by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (progress < 1f) {
            if (!isPaused) {
                progress += 0.02f
                if (progress >= 1f) {
                    progress = 1f
                    // 완료 시 제조완료 화면으로 이동
                    navController.navigate(Screen.ManufacturingComplete.route) {
                        popUpTo(Screen.Manufacturing.route) { inclusive = true }
                        launchSingleTop = true
                    }
                    break
                }
            }
            delay(100)
        }
    }

    // ✅ 진행바 증가
    LaunchedEffect(Unit) {
        while (progress < 1f) {
            if (!isPaused) progress += 0.02f
            delay(100)
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = {}) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.height(80.dp))

            Text("제조중", fontSize = 36.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 40.dp))

            Spacer(Modifier.height(80.dp))

            LinearProgressIndicator(
                progress = progress.coerceIn(0f, 1f),
                modifier = Modifier.fillMaxWidth().height(12.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.LightGray
            )

            Spacer(Modifier.height(80.dp))

            Button(
                onClick = { isPaused = !isPaused },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(if (isPaused) "재생" else "일시정지")
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0))
            ) {
                Text("종료", color = Color.Black)
            }
        }
    }
}
