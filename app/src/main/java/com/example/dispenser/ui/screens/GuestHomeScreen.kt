@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.dispenser.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * 비회원 전용 홈 화면 (임시)
 *
 * @param onLogout 뒤로가기나 홈 클릭 시 호출 (Welcome 화면으로)
 */
@Composable
fun GuestHomeScreen(
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* 빈 블록이라도 반드시 필요합니다 */ },
                navigationIcon = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Home, contentDescription = "홈으로")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))

                // 음성 검색 카드
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 5.dp,
                            color = Color(0xFFB0C7D9),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Mic,
                            contentDescription = "음성으로 검색하기",
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("음성으로 검색하기")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 직접 검색 필드에도 5dp 테두리 추가
                var query by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = { Text("직접 검색하기...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(
                            width = 5.dp,
                            color = Color(0xFFB0C7D9),
                            shape = RoundedCornerShape(8.dp)
                        )
                )

                Spacer(modifier = Modifier.height(50.dp))

                // 버튼들의 색상을 primary 로 복원
                val buttonShape = RoundedCornerShape(8.dp)
                val buttonHeight = 56.dp





                Button(
                    onClick = { /* 잔량 확인 */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight),
                    shape = buttonShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("잔량확인")
                }
            }
        }
    )
}
