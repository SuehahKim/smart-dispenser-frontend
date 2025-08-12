@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.dispenser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StartLoggedInScreen(
    userEmail: String?,
    userId: String?,
    onMakeSauce: () -> Unit,
    onLogout: () -> Unit,
) {
    val infoText = when {
        !userEmail.isNullOrBlank() -> "로그인: $userEmail"
        !userId.isNullOrBlank() -> "로그인 ID: $userId"
        else -> "로그인 정보 확인 중"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 제목 — 가운데 정렬
            Text(
                text = "스마트 디스펜서",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // 정보 박스
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text = infoText,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 14.dp)
                )
            }

            Spacer(Modifier.height(10.dp))

            // 양념 제조하기 버튼 — 가로폭 줄임
            Button(
                onClick = onMakeSauce,
                modifier = Modifier
                    .fillMaxWidth(0.9f) // ← 가로폭 줄임
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "양념 제조하기",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(40.dp))

            // 로그아웃 버튼 — 가로폭 줄임
            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth(0.9f) // ← 가로폭 줄임
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "로그아웃",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
