@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.dispenser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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


@Composable
fun ManufacturingScreen(
    onBack: () -> Unit,
    onHome: () -> Unit
) {
    var progress by remember { mutableStateOf(0f) }
    var isPaused by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    //진행바 자동 증가 (5초 동안 100%)
    LaunchedEffect(Unit) {
        while (progress < 1f) {
            if (!isPaused) {
                progress += 0.02f       // 5초 동안 100% → 0.02씩 증가 (약 50번)
            }
            delay(100)                  // 0.1초마다 업데이트
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(onClick = onHome) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "홈"
                        )
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
            // 제목
            Text(
                text = "제조중",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 40.dp)
            )
            Spacer(modifier = Modifier.height(60.dp))


            // ✅ 진행 바
            LinearProgressIndicator(
                progress = progress.coerceIn(0f, 1f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.LightGray
            )

            Spacer(modifier = Modifier.height(80.dp))

            // ✅ 일시정지 / 재생 버튼
            Button(
                onClick = { isPaused = !isPaused },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor =  MaterialTheme.colorScheme.primary)
            ) {
                Text(if (isPaused) "재생" else "일시정지")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ✅ 종료 버튼
            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0))
            ) {
                Text("종료",color = Color.Black)
            }
        }
    }
}
