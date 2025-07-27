@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.dispenser.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dispenser.navigation.Screen   // ✅ 네비게이션 Screen import
import com.example.dispenser.ui.popups.RecipeConfirmDialog  // ✅ 팝업 import

// 기록 모델
data class HistoryItem(val id: Int, val name: String, val timestamp: String)

@Composable
fun HistoryScreen(
    navController: NavController,
    onBack: () -> Unit,
    onHome: () -> Unit
) {
    // ✅ 팝업 상태
    var showRecipeDialog by remember { mutableStateOf(false) }

    // ✅ 더미 이력 데이터
    val history = remember {
        listOf(
            HistoryItem(1, "떡볶이소스", "2025-07-17 11:23 사용"),
            HistoryItem(2, "비빔국수소스", "2025-07-17 10:45 사용"),
            HistoryItem(3, "제육볶음소스", "2025-07-16 18:10 사용"),
            HistoryItem(4, "스위트칠리소스", "2025-07-15 14:05 사용"),
            HistoryItem(5, "스파이시간장", "2025-07-14 09:30 사용"),
            HistoryItem(6, "갈릭버터소스", "2025-07-13 20:20 사용"),
            HistoryItem(7, "머스터드소스", "2025-07-12 12:00 사용"),
            HistoryItem(8, "발사믹비네그레트", "2025-07-11 16:45 사용"),
            HistoryItem(9, "토마토베이스", "2025-07-10 08:15 사용"),
            HistoryItem(10, "BBQ소스", "2025-07-09 19:55 사용")
        )
    }

    val favoriteHistoryIds = setOf(1, 3, 5)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
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
                                )
                                .clickable {
                                    // ✅ 떡볶이소스 클릭 시 팝업 표시
                                    if (item.name == "떡볶이소스") {
                                        showRecipeDialog = true
                                    }
                                },
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(20.dp)
                            ) {
                                val iconImage =
                                    if (item.id in favoriteHistoryIds) Icons.Filled.Star else Icons.Filled.StarBorder
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
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        text = item.timestamp,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.Gray,
                                        textAlign = TextAlign.Start
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ✅ 레시피 확인 팝업 → 예 버튼 시 제조중 화면으로 이동
            RecipeConfirmDialog(
                showDialog = showRecipeDialog,
                onConfirm = {
                    showRecipeDialog = false
                    navController.navigate(Screen.Manufacturing.route) {  // ✅ 제조중 화면 이동
                        popUpTo(Screen.MemberHome.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onDismissAndBack = { showRecipeDialog = false },
                onRetry = { /* 다시듣기 로직 필요 시 구현 */ }
            )
        }
    )
}
