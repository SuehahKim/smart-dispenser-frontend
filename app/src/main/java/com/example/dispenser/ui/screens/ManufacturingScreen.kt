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
import com.example.dispenser.navigation.Screen
import androidx.navigation.NavController

@Composable
fun ManufacturingScreen(
    navController: NavController,
    onBack: () -> Unit,
    onHome: () -> Unit
) {
    var progress by remember { mutableStateOf(0f) }
    var isPaused by remember { mutableStateOf(false) }
    var isCompleted by remember { mutableStateOf(false) }

    // 진행바
    LaunchedEffect(Unit) {
        while (progress < 1f) {
            if (!isPaused) {
                progress += 0.02f
                if (progress >= 1f) {
                    progress = 1f
                    isCompleted = true
                }
            }
            delay(100)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    if (isCompleted) {
                        IconButton(onClick = onHome) {
                            Icon(imageVector = Icons.Default.Home, contentDescription = "홈")
                        }
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            // ✅ 제목
            Text(
                text = if (isCompleted) "제조완료" else "제조중",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            Spacer(modifier = Modifier.height(60.dp))

            // ✅ 진행 바
            LinearProgressIndicator(
                progress = progress.coerceIn(0f, 1f),
                modifier = Modifier.fillMaxWidth().height(12.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.LightGray
            )

            Spacer(modifier = Modifier.height(80.dp))

            if (isCompleted) {
                Button(
                    onClick = {
                        navController.navigate(Screen.StockCheck.route + "?origin=manufacturing") {
                            popUpTo(Screen.MemberHome.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("잔량확인", color = Color.White)
                }

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = { /* 즐겨찾기 추가 로직 */ },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("즐겨찾기에 추가", color = Color.White)
                }

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = { navController.navigate(Screen.MemberHome.route) },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("다시 만들기", color = Color.White)
                }
            } else {
                // 제조중 상태 버튼들
                Button(
                    onClick = { isPaused = !isPaused },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(if (isPaused) "재생" else "일시정지")
                }

                Spacer(modifier = Modifier.height(12.dp))

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
}
