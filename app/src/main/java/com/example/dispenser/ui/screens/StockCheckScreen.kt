// 파일 최상단에 OptIn 선언
@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.dispenser.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class StockItem(val id: Int, val name: String, val percentage: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockCheckScreen(
    onBack: () -> Unit,
    onHome: () -> Unit
) {
    val stockList = remember {
        listOf(
            StockItem(1, "고추장 가루", "30%"),
            StockItem(2, "고춧가루", "70%"),
            StockItem(3, "간장", "10%"),
            StockItem(4, "설탕", "50%"),
            StockItem(5, "다시다", "100%")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* 필요하면 제목 추가 가능 */ },
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
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // 상단 타이틀 (크기 키우고 위치 조정)
                Text(
                    text = "잔량",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    ),
                    modifier = Modifier.padding(bottom = 0.5.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(420.dp)
                        .background(
                            color = Color(0xFFFAF3EB),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = Color(0xFFE0E0E0),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(vertical = 24.dp, horizontal = 24.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    stockList.forEach { item ->
                        Text(
                            text = "${item.name} ${item.percentage}",
                            style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // 버튼들
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { onBack() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("확인")
                    }

                    Button(
                        onClick = { /* 다시 듣기 버튼 동작 */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                    ) {
                        Text("다시 듣기", color = Color.Black)
                    }
                }
            }
        }
    )
}
