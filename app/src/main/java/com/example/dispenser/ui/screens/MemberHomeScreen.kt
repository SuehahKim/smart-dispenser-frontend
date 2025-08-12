@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.dispenser.ui.screens

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

/**
 * 회원 전용 홈 화면
 *
 * @param onBack 뒤로가기 (Welcome으로)
 * @param onHome 홈 아이콘 (Welcome으로)
 * @param onConnectDevice 기기 연결하기 버튼 클릭
 * @param onFavorites 즐겨찾기 버튼 클릭
 * @param onHistory 사용이력 버튼 클릭
 * @param onStockCheck 잔량확인 버튼 클릭
 */
@Composable
fun MemberHomeScreen(
    onBack: () -> Unit,
    onHome: () -> Unit,
    onConnectDevice: () -> Unit,
    onFavorites: () -> Unit,
    onHistory: () -> Unit,
    onStockCheck: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* 제목이 필요 없으면 비워두세요 */ },
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
                        Spacer(Modifier.height(8.dp))
                        Text("음성으로 검색하기")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 직접 검색 필드
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

                val buttonShape = RoundedCornerShape(8.dp)
                val buttonHeight = 56.dp
                val buttonColors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )

                // 기기 연결하기 버튼
                Button(
                    onClick = onConnectDevice,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight),
                    shape = buttonShape,
                    colors = buttonColors
                ) {
                    Icon(Icons.Default.QrCodeScanner, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("기기 연결하기")
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 즐겨찾기 버튼
                Button(
                    onClick = onFavorites,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight),
                    shape = buttonShape,
                    colors = buttonColors
                ) {
                    Icon(Icons.Default.Star, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("즐겨찾기")
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 사용이력 버튼
                Button(
                    onClick = onHistory,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight),
                    shape = buttonShape,
                    colors = buttonColors
                ) {
                    Icon(Icons.Default.History, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("사용이력")
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 잔량확인 버튼
                Button(
                    onClick = onStockCheck,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight),
                    shape = buttonShape,
                    colors = buttonColors
                ) {
                    Text("잔량확인")
                }
            }
        }
    )
}
