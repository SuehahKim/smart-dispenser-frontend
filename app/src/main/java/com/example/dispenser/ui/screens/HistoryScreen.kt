@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.dispenser.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// 기록 모델
data class HistoryItem(val id: Int, val name: String, val timestamp: String)

/**
 * 사용 이력 화면
 * 일부 항목은 즐겨찾기 상태를 반영해 꽉 찬 별, 나머지는 빈 별로 표시
 *
 * @param onBack 뒤로가기 클릭 시 호출
 * @param onHome 홈 클릭 시 호출
 */
@Composable
fun HistoryScreen(
    onBack: () -> Unit,
    onHome: () -> Unit
) {
    // 더미 기록 10개
    val history = remember {
        listOf(
            HistoryItem(1,  "떡볶이소스",      "2025-07-17 11:23 사용"),
            HistoryItem(2,  "비빔국수소스",   "2025-07-17 10:45 사용"),
            HistoryItem(3,  "제육볶음소스",   "2025-07-16 18:10 사용"),
            HistoryItem(4,  "스위트칠리소스", "2025-07-15 14:05 사용"),
            HistoryItem(5,  "스파이시간장",    "2025-07-14 09:30 사용"),
            HistoryItem(6,  "갈릭버터소스",   "2025-07-13 20:20 사용"),
            HistoryItem(7,  "머스터드소스",   "2025-07-12 12:00 사용"),
            HistoryItem(8,  "발사믹비네그레트", "2025-07-11 16:45 사용"),
            HistoryItem(9,  "토마토베이스",   "2025-07-10 08:15 사용"),
            HistoryItem(10, "BBQ소스",        "2025-07-09 19:55 사용")
        )
    }
    // 예시: 즐겨찾기된 기록 id
    val favoriteHistoryIds = setOf(1, 3, 5)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* 빈 블록 */ },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                actions = {
                    IconButton(onClick = onHome) {
                        Icon(Icons.Filled.Home, contentDescription = "홈으로")
                    }
                }
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(history) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(20.dp)
                        ) {
                            val iconImage = if (item.id in favoriteHistoryIds) Icons.Filled.Star else Icons.Filled.StarBorder
                            Icon(
                                imageVector = iconImage,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = item.timestamp,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Gray,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
