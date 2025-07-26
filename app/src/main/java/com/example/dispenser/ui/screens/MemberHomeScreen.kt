@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.dispenser.ui.screens

import android.service.autofill.FillEventHistory
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberHomeScreen(
    onBack: () -> Unit,
    onHome: () -> Unit,
    onFavorites: () -> Unit,
    onHistory: () -> Unit,
    onStockCheck: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* 빈 블록이라도 반드시 필요합니다 */ },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                actions = {
                    IconButton(onClick = onHome) {
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
                Spacer(modifier = Modifier.height(60.dp))

                // 음성 검색 카드
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            5.dp,
                            Color(0xFFB0C7D9),
                            RoundedCornerShape(8.dp)
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
                        Spacer(Modifier.height(8.dp))
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
                            5.dp,
                            Color(0xFFB0C7D9),
                            RoundedCornerShape(8.dp)
                        )
                )

                Spacer(modifier = Modifier.height(50.dp))

                // 버튼들의 색상을 primary 로 복원
                val buttonShape = RoundedCornerShape(8.dp)
                val buttonHeight = 56.dp

                Button(
                    onClick = onFavorites,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight),
                    shape = buttonShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(Icons.Default.Star, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("즐겨찾기")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onHistory,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight),
                    shape = buttonShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(Icons.Default.History, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("사용이력")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onStockCheck,
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
