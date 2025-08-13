@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.dispenser.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dispenser.data.model.HistoryDto
import com.example.dispenser.navigation.Screen
import com.example.dispenser.ui.popups.RecipeConfirmDialog
import com.example.dispenser.viewmodel.HistoryViewModel

@Composable
fun HistoryScreen(
    navController: NavController,
    onBack: () -> Unit,
    onHome: () -> Unit,
    viewModel: HistoryViewModel = viewModel()
) {
    // 팝업 상태
    var showRecipeDialog by remember { mutableStateOf(false) }

    // UI 상태 구독
    val ui by viewModel.ui.collectAsState()

    // 최초 로딩
    LaunchedEffect(Unit) { viewModel.loadAll() }

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
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                ui.loading && ui.items.isEmpty() -> {
                    // 첫 로딩 스피너
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                !ui.loading && ui.items.isEmpty() -> {
                    // 데이터 없음
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(ui.error ?: "기록이 없어요.")
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(ui.items) { item: HistoryDto ->
                            val ingredientsText =
                                item.ingredientsNames.joinToString(" · ") { it.name }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        // 항목 클릭 시 팝업
                                        showRecipeDialog = true
                                    },
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.cardElevation(6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(20.dp)
                                ) {
                                    val iconImage =
                                        if (item.favorite) Icons.Filled.Star else Icons.Filled.StarBorder
                                    Icon(
                                        imageVector = iconImage,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .padding(top = 2.dp)
                                            .height(32.dp)
                                    )
                                    Spacer(Modifier.padding(8.dp))
                                    Column {
                                        Text(
                                            text = item.recipeName.ifBlank { "레시피 #${item.recipeId}" },
                                            style = MaterialTheme.typography.headlineSmall,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Spacer(Modifier.height(6.dp))
                                        Text(
                                            text = buildString {
                                                append("상태: ${item.status}  |  요청: ${item.requestedAt}")
                                                item.completedAt?.let { append("  |  완료: $it") }
                                            },
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = Color.Gray,
                                            textAlign = TextAlign.Start
                                        )
                                        if (ingredientsText.isNotBlank()) {
                                            Spacer(Modifier.height(6.dp))
                                            Text(
                                                text = "재료: $ingredientsText",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = Color.Gray
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // 페이징 footer
                        if (!ui.endReached) {
                            item {
                                LaunchedEffect(ui.page) {
                                    if (!ui.loading) viewModel.loadAll(next = true)
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    if (ui.loading) {
                                        LinearProgressIndicator(
                                            modifier = Modifier
                                                .fillMaxWidth(0.5f)
                                                .height(6.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 에러 메시지(있으면 하단에 추가 노출)
            ui.error?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        // 레시피 확인 팝업 → 예 버튼 시 제조중 화면으로 이동
        RecipeConfirmDialog(
            showDialog = showRecipeDialog,
            onConfirm = {
                showRecipeDialog = false
                navController.navigate(Screen.Manufacturing.route) {
                    popUpTo(Screen.MemberHome.route) { inclusive = false }
                    launchSingleTop = true
                }
            },
            onDismissAndBack = { showRecipeDialog = false },
            onRetry = { /* 필요 시 다시듣기 구현 */ }
        )
    }
}
