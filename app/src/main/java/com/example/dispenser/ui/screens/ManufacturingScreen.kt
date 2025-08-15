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
import androidx.navigation.NavController
import com.example.dispenser.navigation.Screen
import kotlinx.coroutines.delay

/**
 * 제조중 화면
 *
 * @param manufactureId 서버에서 발급된 제조 ID.
 * @param statusProvider 서버 상태 조회 함수: REQUESTED / PROCESSING / SUCCESS / FAIL
 */
@Composable
fun ManufacturingScreen(
    navController: NavController,
    onBack: () -> Unit,
    onHome: () -> Unit,
    manufactureId: Long? = null,
    statusProvider: suspend (Long) -> String = { "PROCESSING" }
) {
    var isPaused by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf<String?>(null) }   // REQUESTED/PROCESSING/SUCCESS/FAIL
    var error  by remember { mutableStateOf<String?>(null) }

    val pollIntervalMs = 1500L

    // ---- 서버 폴링 모드 (manufactureId가 있을 때) ----
    LaunchedEffect(manufactureId) {
        if (manufactureId == null) return@LaunchedEffect
        status = "REQUESTED"
        error = null

        while (true) {
            if (!isPaused) {
                try {
                    val s = statusProvider(manufactureId)
                    status = s
                    error = null

                    if (s == "SUCCESS") {
                        navController.navigate(Screen.ManufacturingComplete.route) {
                            popUpTo(Screen.Manufacturing.route) { inclusive = true }
                            launchSingleTop = true
                        }
                        break
                    }
                    if (s == "FAIL") {
                        // 실패 시 멈춤 (필요하면 실패 화면으로 이동하도록 변경 가능)
                        break
                    }
                } catch (e: Exception) {
                    error = e.message ?: "네트워크 오류"
                }
            }
            delay(pollIntervalMs)
        }
    }

    // ---- 로컬 타이머 모드 (manufactureId 없을 때도 기존 흐름 유지) ----
    LaunchedEffect(manufactureId) {
        if (manufactureId != null) return@LaunchedEffect
        var ticks = 0
        while (ticks < 40) { // 약 4초
            if (!isPaused) ticks++
            delay(100)
        }
        navController.navigate(Screen.ManufacturingComplete.route) {
            popUpTo(Screen.Manufacturing.route) { inclusive = true }
            launchSingleTop = true
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = {}) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.height(80.dp))

            Text(
                "제조중",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            Spacer(Modifier.height(24.dp))

            // ▶ 막대바 대신 로딩 스피너 (일시정지면 '아무 문구 없이' 스피너만 멈춤)
            val showSpinner = !isPaused && status != "FAIL"
            if (showSpinner) {
                CircularProgressIndicator()
            } else {
                Spacer(Modifier.height(4.dp)) // 일시정지 시엔 공간만 살짝 유지
            }

            Spacer(Modifier.height(16.dp))

            // ▶ 상태 문구: 일시정지일 때는 표시하지 않음
            if (!isPaused) {
                Text(
                    text = when {
                        status == "FAIL" -> "제조에 실패했습니다."
                        status == "SUCCESS" -> "제조가 완료되었습니다."
                        status == "REQUESTED" -> "제조 요청 처리 중…"
                        status == "PROCESSING" || status == null -> "제조가 진행 중입니다…"
                        else -> "상태 조회 중…"
                    },
                    style = MaterialTheme.typography.titleMedium
                )
            }

            error?.let {
                Spacer(Modifier.height(8.dp))
                Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(Modifier.height(80.dp))

            // ▶ 버튼들: 기존 유지
            Button(
                onClick = { isPaused = !isPaused },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(if (isPaused) "재생" else "일시정지")
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0))
            ) {
                Text("종료", color = Color.Black)
            }
        }
    }
}
